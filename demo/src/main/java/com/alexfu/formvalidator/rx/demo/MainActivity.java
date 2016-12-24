package com.alexfu.formvalidator.rx.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.rules.EmailRule;
import com.alexfu.formvalidator.rules.MinLengthRule;
import com.alexfu.formvalidator.rx.RxValidator;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
  private Button validateButton;
  private EditText firstNameInput, lastNameInput, emailInput;
  private RxValidator validator = new RxValidator();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setUpViews();
    setUpValidator();
  }

  private void setUpValidator() {
    validator.addRule(firstNameInput, new MinLengthRule(1, "First name cannot be empty."));
    validator.addRule(lastNameInput, new MinLengthRule(1, "Last name cannot be empty."));
    validator.addRule(emailInput, new EmailRule("Invalid email."));

    // Form validation

    validateButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        validator.validate().subscribe(subscriber());
      }
    });

    // Input validation

    validator.observe().subscribe(subscriber());
  }

  private Subscriber<ValidationResult> subscriber() {
    return new Subscriber<ValidationResult>() {
      @Override public void onCompleted() {
        // No op
      }

      @Override public void onError(Throwable error) {
        error.printStackTrace();
      }

      @Override public void onNext(ValidationResult result) {
        if (result.isValid()) {
          result.view.setError(null);
        } else {
          result.view.setError(result.errors.get(0));
        }
      }
    };
  }

  private void setUpViews() {
    firstNameInput = (EditText) findViewById(R.id.first_name_input);
    lastNameInput = (EditText) findViewById(R.id.last_name_input);
    emailInput = (EditText) findViewById(R.id.email_input);
    validateButton = (Button) findViewById(R.id.button_validate);
  }
}
