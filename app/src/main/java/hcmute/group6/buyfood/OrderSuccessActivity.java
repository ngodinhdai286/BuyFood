package hcmute.group6.buyfood;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView txtOk;

    private void mapping() {
        txtOk = findViewById(R.id.orderSuccessActivity_txtOk);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        mapping();

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}