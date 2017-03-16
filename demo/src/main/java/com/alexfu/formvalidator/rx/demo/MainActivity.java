package com.alexfu.formvalidator.rx.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexfu.formvalidator.ValidationResult;
import com.alexfu.formvalidator.rules.EmailRule;
import com.alexfu.formvalidator.rules.MinLengthRule;
import com.alexfu.formvalidator.rx.RxFieldValidationEvent;
import com.alexfu.formvalidator.rx.RxValidationEvent;
import com.alexfu.formvalidator.rx.RxValidationState;
import com.alexfu.formvalidator.rx.RxValidator;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private Button validateButton;
    private EditText firstNameInput, lastNameInput, emailInput;
    RxValidator validator = new RxValidator();
    List<ValidationResult> validationErrors = new ArrayList<>();

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
                validator.validate();
            }
        });

        // Input validation

        validator.observable().subscribe(subscriber());
    }

    private Subscriber<RxValidationEvent> subscriber() {
        return new Subscriber<RxValidationEvent>() {
            @Override public void onCompleted() {
                // No op
            }

            @Override public void onError(Throwable error) {
                error.printStackTrace();
            }

            @Override public void onNext(RxValidationEvent event) {
                if (event.state == RxValidationState.BEGIN) {
                    validationErrors.clear();
                }

                if (event.state == RxValidationState.FIELD && event instanceof RxFieldValidationEvent) {
                    ValidationResult result = ((RxFieldValidationEvent) event).result;
                    if (result.isValid()) {
                        result.view.setError(null);
                    } else {
                        result.view.setError(result.errors.get(0));
                        validationErrors.add(result);
                    }
                }

                if (event.state == RxValidationState.END) {
                    if (validationErrors.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Form is valid!", Toast.LENGTH_SHORT).show();
                    }
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
