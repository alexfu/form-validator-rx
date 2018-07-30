package com.alexfu.formvalidator.rx;

import android.widget.TextView;

import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.Validator;
import com.alexfu.formvalidator.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * A {@link Validator} that provides Rx bindings.
 */
public class RxValidator {
    final Validator validator = new Validator();

    public void addRule(TextView view, List<ValidationRule> rules) {
        validator.addRule(view, rules);
    }

    public void addRule(TextView view, ValidationRule... rules) {
        validator.addRule(view, rules);
    }

    public void removeRule(TextView view, ValidationRule rule) {
        validator.removeRule(view, rule);
    }

    public void removeAllRules(TextView view) {
        validator.removeAllRules(view);
    }

    public void validate() {
        validator.validate();
    }

    public void validate(TextView view) {
        validator.validate(view);
    }

    public Observable<RxValidationEvent> observable() {
        return Observable.create(new Action1<Emitter<RxValidationEvent>>() {
            @Override public void call(final Emitter<RxValidationEvent> emitter) {
                validator.setCallback(new Validator.Callback() {
                    private final List<ValidationResult> validationErrors = new ArrayList<>();

                    @Override public void onFieldValidated(ValidationResult result) {
                        if (!result.isValid()) {
                            validationErrors.add(result);
                        }
                        emitter.onNext(new RxFieldValidationEvent(result));
                    }

                    @Override public void onBeginFormValidation() {
                        validationErrors.clear();
                    }

                    @Override public void onFormValidated() {
                        emitter.onNext(new RxFormValidationEvent(validationErrors.isEmpty(), validationErrors));
                    }
                });
            }
        }, Emitter.BackpressureMode.BUFFER);
    }
}
