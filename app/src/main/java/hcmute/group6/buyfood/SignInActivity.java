package hcmute.group6.buyfood;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.group6.buyfood.base.BaseData;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.utils.Utils;

public class SignInActivity extends AppCompatActivity {

    DBManager dbManager;

    TextView txtLogin, txtCreateAccount;
    EditText edtEmail, edtPassword;

    private void mapping() {
        dbManager = new DBManager(this);
        txtLogin = findViewById(R.id.txtLogin);
        edtEmail = findViewById(R.id.signInActivity_edtEmail);
        edtPassword = findViewById(R.id.signInActivity_edtPassword);
        txtCreateAccount = findViewById(R.id.signInActivity_txtCreateAccount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mapping();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogin()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    Toast.makeText(SignInActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(SignInActivity.this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, hcmute.group6.buyfood.SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean checkLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String query = "SELECT * FROM [User] WHERE email = '" + email + "' AND " +
                "password = '" + password + "';";

        Cursor res = dbManager.GetData(query);
        if (res.moveToNext()) {
            BaseData.userEntity = Utils.userMapping(res);
            return true;
        }

        return false;
    }
}