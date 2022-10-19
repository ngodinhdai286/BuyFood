package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import hcmute.group6.buyfood.R;


public class NotificationCard extends BaseCard{
    private int layout = R.layout.card_notification;
    private View view;
    private Context context;

    public TextView txtTitle, txtContent;
    public ConstraintLayout container;
    public ImageView img;

    @Override
    protected void mapping() {
        container = view.findViewById(R.id.notificationCard_container);
        txtTitle = view.findViewById(R.id.notificationCard_txtTitle);
        txtContent = view.findViewById(R.id.notificationCard_txtContent);
        img = view.findViewById(R.id.notificationCard_img);
    }

    public NotificationCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    protected void setListenerEvent() {

    }

    public void setOnClickListener(View.OnClickListener listener) {
        container.setOnClickListener(listener);
    }
}
