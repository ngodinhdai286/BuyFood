package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hcmute.group6.buyfood.R;

public class RestaurantCard extends BaseCard{
    private int layout = R.layout.card_restaurant;
    private View view;
    private Context context;

    public ImageView img;
    public TextView txtEstimatedTime, txtName;
    LinearLayout container;

    @Override
    protected void mapping() {
        img = view.findViewById(R.id.restaurantCard_img);
        txtName = view.findViewById(R.id.restaurantCard_txtName);
        txtEstimatedTime = view.findViewById(R.id.restaurantCard_txtEstimatedTime);
        container = view.findViewById(R.id.restaurantCard_container);
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    protected void setListenerEvent() {

    }

    public RestaurantCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        container.setOnClickListener(listener);
    }
}
