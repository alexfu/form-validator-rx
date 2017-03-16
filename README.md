# form-validator-rx

Adds Rx capabilities on top of [form-validator](https://github.com/alexfu/form-validator).

# Usage

```
EmailRule emailRule = new EmailRule("Invalid email address.");

RxValidator validator = new RxValidator();
validator.addRule(myEditText, emailRule); // Add rules to your EditText
validator.observable()
  .subscribe(new Subscriber<RxValidationEvent>() {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable error) {
    }

    @Override public void onNext(RxValidationEvent event) {
      // Do something with event
    }
  });
```

# Installation

```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  compile 'com.github.alexfu:form-validator-rx:1.0'
}
```
