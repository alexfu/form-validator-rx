package com.alexfu.formvalidator.rx;

import com.alexfu.formvalidator.ValidationResult;
import java.util.List;

/**
 * Copyright Leafly, 7/30/18
 */
public class RxFormValidationEvent implements RxValidationEvent {
    private final boolean isValid;
    private final List<ValidationResult> validationResults;

    public RxFormValidationEvent(boolean isValid, List<ValidationResult> validationResults) {
        this.isValid = isValid;
        this.validationResults = validationResults;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }
}
