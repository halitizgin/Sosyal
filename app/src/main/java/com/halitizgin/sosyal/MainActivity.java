package com.halitizgin.sosyal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailText, passwordText;
    CheckBox rememberCheck;
    SharedPreferences sharedPreferences;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        }
        sharedPreferences = this.getSharedPreferences("com.halitizgin.sosyal", Context.MODE_PRIVATE);

        String rememberEmail = sharedPreferences.getString("RememberEmail", null);
        emailText = findViewById(R.id.emailText);
        emailText.setText(rememberEmail);
        passwordText = findViewById(R.id.passwordText);
        rememberCheck = findViewById(R.id.rememberCheck);
    }

    public void signIn(View view)
    {
        final ProgressDialog signInProgress = showLoading(this, "Giriş yapılıyor!", "Lütfen bekleyin", false);
        mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            if (rememberCheck.isChecked())
                            {
                                sharedPreferences.edit().putString("RememberEmail", emailText.getText().toString()).apply();
                            }
                            signInProgress.dismiss();
                            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        signInProgress.dismiss();
                        showAlert(MainActivity.this, "Giriş yapılırken bir hata oluştu!", e.getLocalizedMessage(), "TAMAM").show();
                    }
                });
    }

    public void signUp(View view)
    {
        final ProgressDialog signUpProgress = showLoading(this, "Üye olunuyor!", "Lütfen bekleyin!", false);
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            signUpProgress.dismiss();
                            Toast.makeText(MainActivity.this, "Başarıyla üye oldunuz!", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        signUpProgress.dismiss();
                        showAlert(MainActivity.this, "Üye olunurken bir hata oluştu!", e.getLocalizedMessage(), "TAMAM").show();
                    }
                });
    }

    public ProgressDialog showLoading(Context context, String title, String description, Boolean isCancelable)
    {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(title);
        progress.setMessage(description);
        progress.setCancelable(isCancelable); // disable dismiss by tapping outside of the dialog
        progress.show();
        return progress;
    }

    public AlertDialog.Builder showAlert(Context context, String title, String message, String buttonTitle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(buttonTitle, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder;
    }
}
