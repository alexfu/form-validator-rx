package com.alexfu.formvalidator.rx;

import com.alexfu.formvalidator.ValidationResult;

public class RxFieldValidationEvent implements RxValidationEvent {
    public final ValidationResult validationResult;

    public RxFieldValidationEvent(ValidationResult result) {
        validationResult = result;
    }

    @Override
    public boolean isValid() {
        return validationResult.isValid();
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}
