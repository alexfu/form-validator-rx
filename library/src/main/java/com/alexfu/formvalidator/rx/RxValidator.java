package com.alexfu.formvalidator.rx;

import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alexfu.formvalidator.AbsValidator;
import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.ValidationTask;
import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.List;
import java.util.Map;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;
import rx.functions.Func1;

/**
 * A {@link Validator} that provides Rx bindings. Validation happens on the io
 * thread.
 */
public class RxValidator extends AbsValidator<Observable<ValidationResult>> {
  private final Map<View, Observable<TextView>> textWatchers;

  public RxValidator() {
    textWatchers = new ArrayMap<>();
  }

  /**
   * Observe validation results from on-the-fly validations.
   */
  public Observable<ValidationResult> observe() {
    return Observable.merge(textWatchers.values())
        .flatMap(new Func1<TextView, Observable<ValidationResult>>() {
          @Override public Observable<ValidationResult> call(TextView view) {
            return validate(view);
          }
        });
  }

  @Override public void addRule(TextView view, List<ValidationRule> rules) {
    super.addRule(view, rules);
    watchForTextChanges(view);
  }

  @Override public void addRule(final TextView view, ValidationRule... rules) {
    super.addRule(view, rules);
    watchForTextChanges(view);
  }

  @Override
  protected Observable<ValidationResult> performValidate(ValidationTask[] tasks) {
    return Observable.from(tasks).map(map())
        .compose(new AsyncTransformer<ValidationResult>());
  }

  @Override
  protected Observable<ValidationResult> performValidate(ValidationTask task) {
    return Observable.just(task).map(map())
        .compose(new AsyncTransformer<ValidationResult>());
  }

  private void watchForTextChanges(final TextView view) {
    Observable<TextView> observable = Observable.fromEmitter(new Action1<Emitter<TextView>>() {
      @Override public void call(final Emitter<TextView> emitter) {
        final TextWatcher watcher = new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Ignore
          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Ignore
          }

          @Override public void afterTextChanged(Editable editable) {
            emitter.onNext(view);
          }
        };

        emitter.setCancellation(new Cancellable() {
          @Override public void cancel() throws Exception {
            view.removeTextChangedListener(watcher);
          }
        });

        view.addTextChangedListener(watcher);
      }
    }, Emitter.BackpressureMode.DROP);

    textWatchers.put(view, observable);
  }

  private Func1<ValidationTask, ValidationResult> map() {
    return new Func1<ValidationTask, ValidationResult>() {
      @Override public ValidationResult call(ValidationTask task) {
        return task.validate();
      }
    };
  }
}
