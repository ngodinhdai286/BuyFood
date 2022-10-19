package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import hcmute.group6.buyfood.R;


public class DishButtonCard extends BaseCard{
    private int layout = R.layout.card_dish_button;
    private View view;
    private Context context;

    public TextView txtBtn, txtDishName, txtDishPrice;
    public ConstraintLayout container;
    public ImageView img;

    @Override
    protected void mapping() {
        txtBtn = view.findViewById(R.id.dishButtonCard_txtBtn);
        container = view.findViewById(R.id.dishButtonCard_container);
        txtDishPrice = view.findViewById(R.id.dishButtonCard_txtDishPrice);
        txtDishName = view.findViewById(R.id.dishButtonCard_txtDishName);
        img = view.findViewById(R.id.dishButtonCard_img);
    }

    public DishButtonCard(Context context) {
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
