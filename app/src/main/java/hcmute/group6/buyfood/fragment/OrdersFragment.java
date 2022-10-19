package hcmute.group6.buyfood.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import hcmute.group6.buyfood.OrderDetailActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.base.BaseData;
import hcmute.group6.buyfood.card.DishButtonCard;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.entity.OrderEntity;
import hcmute.group6.buyfood.utils.Utils;


public class OrdersFragment extends Fragment {
    private View view;

    public LinearLayout container;
    private DBManager dbManager;

    public void mapping() {
        container = view.findViewById(R.id.orderFragment_container);
        dbManager = new DBManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_orders, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mapping();
        setupOrders();
    }

    private void setEvent() {

    }

    private void setupOrders() {
        container.removeAllViews();
        List<OrderEntity> orderEntities = Utils.getOrdersByEmail(BaseData.userEntity.getEmail(), dbManager);

        if (orderEntities.size() == 0) {
            TextView txtNoti = new TextView(getActivity());
            txtNoti.setText("You didn't place any order!");
            txtNoti.setPadding(60, 20, 20, 20);
            container.addView(txtNoti);
        }

        for (OrderEntity orderEntity: orderEntities) {
            DishButtonCard dishButtonCard = new DishButtonCard(getActivity());
            dishButtonCard.txtBtn.setText("Detail");

            // get dish info
            Cursor dishCursor = dbManager.GetData("SELECT * FROM [Dish] " +
                    "WHERE [id] = " + orderEntity.getDishId());
            dishCursor.moveToNext();
            DishEntity dishEntity = Utils.dishMapping(dishCursor);

            int id = getActivity().getResources().getIdentifier(dishEntity.getPicture(), "drawable", getActivity().getPackageName());
            dishButtonCard.img.setImageResource(id);
            dishButtonCard.txtDishPrice.setText("$"+orderEntity.getAmount());
            dishButtonCard.txtDishName.setText(dishEntity.getName());

            dishButtonCard.txtBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("orderEntity", orderEntity);
                    intent.putExtra("dishEntity", dishEntity);
                    startActivity(intent);
                }
            });
            container.addView(dishButtonCard.getView(), 0);
        }
    }
}
