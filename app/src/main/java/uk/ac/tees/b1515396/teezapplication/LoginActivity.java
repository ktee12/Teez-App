package uk.ac.tees.b1515396.teezapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.tv_register);
        register.setOnClickListener(this);

        forgotPassword = findViewById(R.id.tv_forgotPassword);
        forgotPassword.setOnClickListener(this);

        buttonSignIn = findViewById(R.id.btn_signIn);
        buttonSignIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.etv_email);
        editTextPassword = findViewById(R.id.etv_password);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.tv_forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));

            case R.id.btn_signIn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Full name is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Verify the email
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){
                                //Let the user know
                                Toast.makeText(LoginActivity.this, "Sign in successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                            //If I want to implement authentication
//                            else {
//                                user.sendEmailVerification();
//                                Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
//                            }

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}