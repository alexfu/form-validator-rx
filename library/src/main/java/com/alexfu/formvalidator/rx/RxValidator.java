package com.alexfu.formvalidator.rx;

import com.alexfu.formvalidator.AbsValidator;
import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.ValidationTask;
import com.alexfu.formvalidator.Validator;

import rx.Observable;
import rx.functions.Func1;

/**
 * A {@link Validator} that provides Rx bindings. Validation doesn't happen on
 * any particular {@link rx.Scheduler}.
 */
public class RxValidator extends AbsValidator<Observable<ValidationResult>> {
  @Override
  protected Observable<ValidationResult> performValidate(ValidationTask[] tasks) {
    return Observable.from(tasks).map(map());
  }

  @Override
  protected Observable<ValidationResult> performValidate(ValidationTask task) {
    return Observable.just(task).map(map());
  }

  private Func1<ValidationTask, ValidationResult> map() {
    return new Func1<ValidationTask, ValidationResult>() {
      @Override public ValidationResult call(ValidationTask task) {
        return task.validate();
      }
    };
  }
}
