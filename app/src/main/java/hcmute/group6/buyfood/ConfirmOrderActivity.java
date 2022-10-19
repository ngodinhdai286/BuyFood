package hcmute.group6.buyfood;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hcmute.group6.buyfood.base.BaseData;
import hcmute.group6.buyfood.card.PlaceMyOrderCard;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.dialog.EditPaymentMethodDialog;
import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.utils.Utils;


public class ConfirmOrderActivity extends AppCompatActivity {

    public FrameLayout placeMyOrderContainerFrame;
    public PlaceMyOrderCard placeMyOrderCard;
    public ImageView imgReturn;
    public TextView txtEditDeliverTo, txtEditPaymentMethod, txtDeliverTo;
    private DishEntity dishEntity;
    private DBManager dbManager;

    private final Integer EDIT_DELIVER_TO_REQUEST = 1;

    private void mapping() {
        placeMyOrderContainerFrame = findViewById(R.id.confirmOrderActivity_placeMyOrderContainer);
        placeMyOrderCard = new PlaceMyOrderCard(ConfirmOrderActivity.this);
        imgReturn = findViewById(R.id.confirmOrder_imgReturn);
        txtEditDeliverTo = findViewById(R.id.confirmOrderActivity_txtEditDeliverTo);
        txtDeliverTo = findViewById(R.id.confirmOrderActivity_txtDeliverTo);
        txtEditPaymentMethod = findViewById(R.id.confirmOrderActivity_txtEditPaymentMethod);
        dishEntity = (DishEntity) getIntent().getSerializableExtra("dishEntity");
        dbManager = new DBManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        mapping();

        setCard();
        setEvent();
        setUpInitData();
    }

    private void setCard() {
        placeMyOrderContainerFrame.removeAllViews();
        placeMyOrderContainerFrame.addView(placeMyOrderCard.getView());
    }

    private void setEvent() {
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtEditDeliverTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openEditDeliverToDialog();
                Intent intent = new Intent(ConfirmOrderActivity.this, MapsActivity.class);
                intent.putExtra("location", txtDeliverTo.getText().toString());
                startActivityForResult(intent, EDIT_DELIVER_TO_REQUEST);
            }
        });

        txtEditPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditPaymentMethodDialog();
            }
        });

        placeMyOrderCard.txtPlaceMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer quantity = Integer.valueOf(placeMyOrderCard.txtQuantity.getText().toString());
                    placeMyOrderCard.txtQuantity.setText(quantity.toString());
                    Integer deliveryCharge = quantity * 2;
                    Float subPrice = quantity*dishEntity.getPrice();
                    Float total = subPrice + 2;
                    dbManager.QueryData("INSERT INTO [Order] VALUES " +
                            "(null, '"+txtDeliverTo.getText().toString()+"', 'COD', "+dishEntity.getId()+", '"+BaseData.userEntity.getEmail()+"', "+total.toString()+")");

                    Intent intent = new Intent(ConfirmOrderActivity.this, OrderSuccessActivity.class);
                    startActivity(intent);
                    finish();

                    // check if dish exists already in favorite dish list of user
                    Cursor checkExistingDishCursor = dbManager.GetData("SELECT * FROM [Favorite] " +
                            "WHERE [email] = '" + BaseData.userEntity.getEmail() + "' AND [dish_id] = " + dishEntity.getId());

                    if (!checkExistingDishCursor.moveToNext()) {
                        String query = "INSERT INTO [Favorite] VALUES ('" + BaseData.userEntity.getEmail() + "', " + dishEntity.getId() + ")";
                        dbManager.QueryData(query);
                    }

                    String query = "INSERT INTO [Notification] " +
                            "VALUES (null, '"+BaseData.userEntity.getEmail()+"', 'Success for "+dishEntity.getName()+"!', 'Your booking has been confirmed', 1)";
                    dbManager.QueryData(query);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(ConfirmOrderActivity.this, "Something went wrong. Please check again!", Toast.LENGTH_LONG).show();
                }

            }
        });

        placeMyOrderCard.txtQuantityInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer quantity = Integer.valueOf(placeMyOrderCard.txtQuantity.getText().toString());
                quantity++;
                placeMyOrderCard.txtQuantity.setText(quantity.toString());

                Float subPrice = quantity * dishEntity.getPrice();
//                Integer deliveryCharge = quantity * 2;
                placeMyOrderCard.txtSubTotalPrice.setText(subPrice + " $");
                placeMyOrderCard.txtTotalPrice.setText((subPrice + 2) + " $");
            }
        });

        placeMyOrderCard.txtQuantityDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer quantity = Integer.valueOf(placeMyOrderCard.txtQuantity.getText().toString());
                if (quantity > 1)
                    quantity--;
                placeMyOrderCard.txtQuantity.setText(quantity.toString());

                Float subPrice = quantity*dishEntity.getPrice();
//                Integer deliveryCharge = quantity * 2;
                placeMyOrderCard.txtSubTotalPrice.setText(subPrice + " $");
                placeMyOrderCard.txtTotalPrice.setText((subPrice + 2) + " $");
            }
        });
    }

//    Mở hộp thoại Edit Delivery
    private void openEditDeliverToDialog() {
        AlertDialog dialog = (new AlertDialog.Builder(this)).create();
        ConstraintLayout dialogLayout = (ConstraintLayout) View.inflate(this, R.layout.dialog_edit_delivery_to, null);
        EditText edtTxtDeliverTo = dialogLayout.findViewById(R.id.dialogEditDeliveryTo_edtTxtDeliveryTo);
        edtTxtDeliverTo.setText(txtDeliverTo.getText());
        dialog.setView(dialogLayout);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                txtDeliverTo.setText(edtTxtDeliverTo.getText());
                dialog.dismiss();

                dbManager.QueryData("UPDATE [User] SET [address] = '" + txtDeliverTo.getText().toString() + "' " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "'");

                Cursor userCursor = dbManager.GetData("SELECT * FROM [User] " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "'");

                if (userCursor.moveToNext())
                    BaseData.userEntity = Utils.userMapping(userCursor);
            }
        });
        dialog.show();
    }

//    Mở hộp thoại Edit Payment
    private void openEditPaymentMethodDialog() {
        Dialog dialog = (new AlertDialog.Builder(this)).create();
        EditPaymentMethodDialog editPaymentMethodDialog = new EditPaymentMethodDialog(ConfirmOrderActivity.this, (AlertDialog) dialog);

    }

    private void setUpInitData() {
//        Integer deliveryCharge = placeMyOrderCard.txtDeliveryCharge;
        txtDeliverTo.setText(BaseData.userEntity.getAddress());
        placeMyOrderCard.txtSubTotalPrice.setText(dishEntity.getPrice() + " $");
        placeMyOrderCard.txtTotalPrice.setText((dishEntity.getPrice() + 2) + " $");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_DELIVER_TO_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                String deliverTo = data.getStringExtra("deliverTo");

                txtDeliverTo.setText(deliverTo);

                dbManager.QueryData("UPDATE [User] SET [address] = '" + txtDeliverTo.getText().toString() + "' " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "'");

                Cursor userCursor = dbManager.GetData("SELECT * FROM [User] " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "'");

                if (userCursor.moveToNext())
                    BaseData.userEntity = Utils.userMapping(userCursor);
            }
        }
    }
}