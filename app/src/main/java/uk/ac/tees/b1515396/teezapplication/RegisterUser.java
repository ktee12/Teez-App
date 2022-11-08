package uk.ac.tees.b1515396.teezapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView header, registerUser;
    private ProgressBar progressBar;
    private EditText editTextFullName, editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        header = findViewById(R.id.tv_header_title);
        header.setOnClickListener(this);

        registerUser = findViewById(R.id.btn_registerUser);
        registerUser.setOnClickListener(this);

        editTextEmail = findViewById(R.id.etv_email);
        editTextFullName = findViewById(R.id.etv_fullName);
        editTextPassword = findViewById(R.id.etv_password);

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_title:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
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

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();

                                                progressBar.setVisibility(View.GONE);

                                                // Redirect to login layout!
                                            }
                                            else {
                                                Toast.makeText(RegisterUser.this, "Failed to register! Please try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }

                        else {
                            Toast.makeText(RegisterUser.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}