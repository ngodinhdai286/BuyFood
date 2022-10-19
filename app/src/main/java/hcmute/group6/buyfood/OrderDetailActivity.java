package hcmute.group6.buyfood;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.entity.OrderEntity;


public class OrderDetailActivity extends AppCompatActivity {
    public ImageView imgReturn, imgDish;
    public TextView txtDishName, txtRating, txtQuantitySold, txtDeliverTo;

    private void mapping() {
        imgReturn = findViewById(R.id.orderDetailActivity_imgReturn);
        imgDish = findViewById(R.id.orderDetailActivity_imgDish);
        txtDishName = findViewById(R.id.orderDetailActivity_txtDishName);
        txtRating = findViewById(R.id.orderDetailActivity_txtRating);
        txtQuantitySold = findViewById(R.id.orderDetailActivity_txtQuantitySold);
        txtDeliverTo = findViewById(R.id.orderDetailActivity_txtDeliverTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
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
        OrderEntity orderEntity = (OrderEntity) getIntent().getSerializableExtra("orderEntity");
        DishEntity dishEntity = (DishEntity) getIntent().getSerializableExtra("dishEntity");

        int id = this.getResources().getIdentifier(dishEntity.getPicture(), "drawable", this.getPackageName());
        imgDish.setImageResource(id);
        txtDishName.setText(dishEntity.getName());
        txtRating.setText(dishEntity.getRating() + " Rating");
        txtQuantitySold.setText(orderEntity.getAmount()  +  " $");
        txtDeliverTo.setText(orderEntity.getDeliverTo());
    }
}