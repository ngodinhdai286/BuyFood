package hcmute.group6.buyfood;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.group6.buyfood.base.BaseData;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.entity.RestaurantEntity;
import hcmute.group6.buyfood.fragment.AccountFragment;
import hcmute.group6.buyfood.utils.Utils;

public class DishDetailActivity extends AppCompatActivity {

    TextView txtBuy, txtDishName, txtRating, txtQuantitySold, txtDescription, txtRestaurantName;
    ImageView imgReturn, imgDish, imgFavorite, imgRemoveFavorite, imgLocation;

    private DBManager dbManager;
    private DishEntity dishEntity;
    private RestaurantEntity restaurantEntity;

    private void mapping() {
        txtBuy = findViewById(R.id.dishDetailActivity_txtBuy);
        imgReturn = findViewById(R.id.dishDetailActivity_imgReturn);
        imgLocation = findViewById(R.id.dishDetailActivity_imgLocation);
        txtDishName = findViewById(R.id.dishDetailActivity_txtDishName);
        txtRating = findViewById(R.id.dishDetailActivity_txtRating);
        txtQuantitySold = findViewById(R.id.dishDetailActivity_txtQuantitySold);
        txtDescription = findViewById(R.id.dishDetailActivity_txtDescription);
        imgDish = findViewById(R.id.dishDetailActivity_dishImg);
        imgFavorite = findViewById(R.id.dishDetailActivity_imgFavorite);
        imgRemoveFavorite = findViewById(R.id.dishDetailActivity_imgRemoveFavorite);
        dbManager = new DBManager(this);
        txtRestaurantName = findViewById(R.id.dishDetailActivity_txtRestaurantName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);
        mapping();

        setUpInitData();
        setEventListener();
    }

    private void setUpInitData() {
        DishEntity dishEntity = (DishEntity) getIntent().getSerializableExtra("dishEntity");
        this.dishEntity = dishEntity;
        int id = getResources().getIdentifier(dishEntity.getPicture(), "drawable", getPackageName());
        imgDish.setImageResource(id);
        txtDishName.setText(dishEntity.getName());
        txtRating.setText(dishEntity.getRating().toString() + " Rating");
        txtQuantitySold.setText(dishEntity.getQuantitySold().toString() + "+ Over");
        txtDescription.setText(dishEntity.getDescription() + dishEntity.getDescription() + dishEntity.getDescription() + dishEntity.getDescription());

        // Lấy ra nhà hàng có món ăn này
        Cursor resCursor = dbManager.GetData("SELECT * FROM [Restaurant] " +
                "WHERE [id] = " + dishEntity.getRestaurantId().toString());

        if (resCursor.moveToNext())
            restaurantEntity = Utils.restaurantMapping(resCursor);
        txtRestaurantName.setText(restaurantEntity.getName());

    }

    private void setEventListener() {
        txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishDetailActivity.this, hcmute.group6.buyfood.ConfirmOrderActivity.class);
                intent.putExtra("dishEntity", dishEntity); //Truyền thông tin món ăn sang cho PlaceMyOrderCard
                startActivity(intent);
            }
        });

        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishDetailActivity.this, hcmute.group6.buyfood.RestaurantDetailActivity.class);
                intent.putExtra("restaurantEntity", restaurantEntity);
                startActivity(intent);
            }
        });

        txtRestaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishDetailActivity.this, hcmute.group6.buyfood.RestaurantDetailActivity.class);
                intent.putExtra("restaurantEntity", restaurantEntity);
                startActivity(intent);
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if dish exists already in favorite dish list of user
                Cursor checkExistingDishCursor = dbManager.GetData("SELECT * FROM [Favorite] " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "' AND [dish_id] = " + dishEntity.getId());

                if (checkExistingDishCursor.moveToNext()) {
                    Toast.makeText(DishDetailActivity.this, "This dish has existed in your favorite list", Toast.LENGTH_SHORT).show();
                    return;
                }

                String query = "INSERT INTO [Favorite] VALUES ('" + BaseData.userEntity.getEmail() + "', " + dishEntity.getId() + ")";
                dbManager.QueryData(query);
                Toast.makeText(DishDetailActivity.this, "Add to favorite list successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        imgRemoveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if dish exists already in favorite dish list of user
                Cursor checkExistingDishCursor = dbManager.GetData("SELECT * FROM [Favorite] " +
                        "WHERE [email] = '" + BaseData.userEntity.getEmail() + "' AND [dish_id] = " + dishEntity.getId());

                if (!checkExistingDishCursor.moveToNext()) {
                    Toast.makeText(DishDetailActivity.this, "This dish does not exist in your favorite list", Toast.LENGTH_SHORT).show();
                    return;
                }

                String query = "DELETE FROM [Favorite] WHERE [email] = '" + BaseData.userEntity.getEmail() + "' AND [dish_id] = " + dishEntity.getId();
                dbManager.QueryData(query);

                AccountFragment accountFragment = (AccountFragment) getSupportFragmentManager().findFragmentById(R.id.accountFragment);
                Toast.makeText(DishDetailActivity.this, "Remove dish from favorite list successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}