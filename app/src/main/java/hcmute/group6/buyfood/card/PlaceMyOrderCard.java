package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import hcmute.group6.buyfood.R;

public class PlaceMyOrderCard extends BaseCard{
    private int layout = R.layout.card_place_my_order;
    private View view;
    private Context context;

    public TextView txtPlaceMyOrder, txtSubTotalPrice, txtTotalPrice, txtQuantity, txtQuantityInc, txtQuantityDes,txtDeliveryCharge;

    @Override
    protected void mapping() {
        txtPlaceMyOrder = view.findViewById(R.id.placeMyOrderCard_txtPlaceMyOrder);
        txtSubTotalPrice = view.findViewById(R.id.placeMyOrderCard_txtSubTotalPrice);
        txtDeliveryCharge = view.findViewById(R.id.placeMyOrderCard_txtDeliveryCharge);
        txtTotalPrice = view.findViewById(R.id.placeMyOrderCard_txtTotalPrice);
        txtQuantity = view.findViewById(R.id.placeMyOrderCard_txtQuantity);
        txtQuantityDes = view.findViewById(R.id.placeMyOrderCard_txtQuantityDes);
        txtQuantityInc = view.findViewById(R.id.placeMyOrderCard_txtQuantityInc);
    }

    public PlaceMyOrderCard(Context context) {
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

//        txtPlaceMyOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrderSuccessActivity.class);
//                context.startActivity(intent);
//                ((Activity)context).finish();
//            }
//        });

    }
}
