package hcmute.group6.buyfood;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import hcmute.group6.buyfood.base.BaseData;
import hcmute.group6.buyfood.card.NotificationCard;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.entity.NotificationEntity;
import hcmute.group6.buyfood.utils.Utils;

public class NotificationActivity extends AppCompatActivity {

    public ImageView imgReturn;
    public LinearLayout container;
    private DBManager dbManager;

    private void mapping() {
        imgReturn = findViewById(R.id.notificationActivity_imgReturn);
        container = findViewById(R.id.notificationActivity_container);
        dbManager = new DBManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mapping();
        setEventListener();
        setUpInitData();
    }

    private void setEventListener() {
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpInitData() {
        container.removeAllViews();
        Cursor notiCursor = dbManager.GetData("SELECT * FROM [Notification] " +
                "WHERE [email] = '"+ BaseData.userEntity.getEmail() +"'");

        if (!notiCursor.moveToNext()) {
            TextView txtNoti = new TextView(this);
            txtNoti.setText("You do not have any notification!");
            txtNoti.setPadding(20, 20, 20, 20);
            container.addView(txtNoti);
        }
        else {
            do {
                NotificationEntity notificationEntity = Utils.notificationMapping(notiCursor);
                NotificationCard notificationCard = new NotificationCard(this);
                notificationCard.txtTitle.setText(notificationEntity.getTitle());
                notificationCard.txtContent.setText(notificationEntity.getContent());

                container.addView(notificationCard.getView(), 0);
            } while (notiCursor.moveToNext());
        }
    }
}