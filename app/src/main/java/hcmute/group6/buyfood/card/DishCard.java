package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hcmute.group6.buyfood.R;


public class DishCard extends BaseCard{
    private int layout = R.layout.card_dish;
    private View view;
    private Context context;

    public LinearLayout container;
    public ImageView img;
    public TextView txtName, txtPrice;

    @Override
    protected void mapping() {
        container = view.findViewById(R.id.dishCard_container);
        img = view.findViewById(R.id.dishCard_img);
        txtName = view.findViewById(R.id.dishCard_txtName);
        txtPrice = view.findViewById(R.id.dishCard_txtPrice);
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    protected void setListenerEvent() {

    }

    public DishCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        container.setOnClickListener(listener);
    }
}
