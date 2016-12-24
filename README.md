# form-validator-rx

Adds Rx capabilities on top of [form-validator](https://github.com/alexfu/form-validator).

# Usage

```
EmailRule emailRule = new EmailRule("Invalid email address.");

RxValidator validator = new RxValidator();
validator.addRule(myEditText, emailRule); // Add rules to your EditText
validator.validate()
  .subscribe(new Subscriber<ValidationResult>() {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable error) {

    }

    @Override public void onNext(ValidationResult result) {
      // Check result for each input field
    }
  });
```

To react to on-the-fly validations:

```
validator.observe()
  .subscribe(new Subscriber<ValidationResult>() {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable error) {

    }

    @Override public void onNext(ValidationResult result) {
      // Check result of input field
    }
  });
```
