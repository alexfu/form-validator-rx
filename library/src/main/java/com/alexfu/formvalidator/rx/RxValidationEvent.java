package com.alexfu.formvalidator.rx;

public class RxValidationEvent {
    public final RxValidationState state;

    public RxValidationEvent(RxValidationState state) {
        this.state = state;
    }
}
