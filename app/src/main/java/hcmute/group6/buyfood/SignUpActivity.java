package hcmute.group6.buyfood;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.group6.buyfood.database.DBManager;

public class SignUpActivity extends AppCompatActivity {

    TextView txtCreateAccount, txtAlreadyHaveAccount;
    EditText edtName, edtEmail, edtPassword;
    DBManager dbManager;

    private void mapping() {
        dbManager = new DBManager(this);
        txtCreateAccount = findViewById(R.id.signUpActivity_txtCreateAccount);
        edtName = findViewById(R.id.signUpActivity_edtName);
        edtEmail = findViewById(R.id.signUpActivity_edtEmail);
        edtPassword = findViewById(R.id.signUpActivity_edtPassword);
        txtAlreadyHaveAccount = findViewById(R.id.signUpActivity_txtAlreadyHaveAccount);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mapping();

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createAccount()) {
                    Toast.makeText(SignUpActivity.this, "Create account successfully. You can login now!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                    Toast.makeText(SignUpActivity.this, "Cannot create account. Please check again!", Toast.LENGTH_SHORT).show();
            }
        });

        txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Boolean createAccount() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        try {
            String query = "INSERT INTO [User] VALUES('"+email+"', '"+password+"', '"+name+"', 'pic_avatar4', '')";
            dbManager.QueryData(query);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}