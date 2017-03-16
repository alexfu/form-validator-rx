package com.alexfu.formvalidator.rx;

import com.alexfu.formvalidator.ValidationResult;

public class RxFieldValidationEvent extends RxValidationEvent {
    public final ValidationResult result;

    public RxFieldValidationEvent(ValidationResult result) {
        super(RxValidationState.FIELD);
        this.result = result;
    }
}
