package hcmute.group6.buyfood;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.group6.buyfood.card.DishCard;
import hcmute.group6.buyfood.card.HeaderCard;
import hcmute.group6.buyfood.card.RestaurantCard;
import hcmute.group6.buyfood.card.SearchResultCard;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.entity.RestaurantEntity;
import hcmute.group6.buyfood.utils.Utils;

public class SearchActivity extends AppCompatActivity {
    private DBManager dbManager;
    private Context context;
    public HeaderCard headerCard;
    FrameLayout headerContainerFragment, mainContainerFrame;

    TextView txtSearch, txtRestaurant, txtDish, txt15Mins, txt40Mins, txtAll;
    EditText edtTextSearch;

    Boolean findRestaurantChk = false, findDishChk = false, find15MinsChk = false,
            find40MinsChk = false, findAllChk = true;

    private void mapping() {
        headerContainerFragment = findViewById(R.id.headerContainerFragment_Search);
        mainContainerFrame = findViewById(R.id.mainContainerFrame_search);
        txtSearch = findViewById(R.id.txtSearch);
        dbManager = new DBManager(this);
        headerCard = new HeaderCard(SearchActivity.this);
        this.context = this;
        txtRestaurant = findViewById(R.id.searchActivity_txtRestaurant);
        txtDish = findViewById(R.id.searchActivity_txtDish);
        txt15Mins = findViewById(R.id.searchActivity_txt15Mins);
        txt40Mins = findViewById(R.id.searchActivity_txt40Mins);
        txtAll = findViewById(R.id.searchActivity_txtAll);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mapping();
        setupHeader();
        setEvent();

        edtTextSearch = findViewById(R.id.headerCard_edtTextSearch);
        edtTextSearch.requestFocus();
    }

    private void setEvent() {
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainContainerFrame.removeAllViews();
                SearchResultCard searchResultCard = new SearchResultCard(SearchActivity.this);
                mainContainerFrame.addView(searchResultCard.getView());

                searchResultCard.gvContainer.removeAllViews();
                String keyword = headerCard.edtTextSearch.getText().toString();
                if (findAllChk) {
                    findDish(keyword, searchResultCard);
                    findRestaurant(keyword, searchResultCard);
                } else if (findRestaurantChk)
                    findRestaurant(keyword, searchResultCard);
                else if (findDishChk)
                    findDish(keyword, searchResultCard);
                else if (find15MinsChk){
                    find15Mins(keyword, searchResultCard);
                } else if (find40MinsChk) {
                    find40Mins(keyword, searchResultCard);
                }


            }
        });

        txtRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRestaurant.setTextColor(Color.parseColor("#FF0015"));
                txtDish.setTextColor(Color.parseColor("#DA6317"));
                txt15Mins.setTextColor(Color.parseColor("#DA6317"));
                txt40Mins.setTextColor(Color.parseColor("#DA6317"));
                txtAll.setTextColor(Color.parseColor("#DA6317"));

                findRestaurantChk = true;
                findDishChk = false;
                find15MinsChk = false;
                find40MinsChk = false;
                findAllChk = false;
            }
        });

        txtDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRestaurant.setTextColor(Color.parseColor("#DA6317"));
                txtDish.setTextColor(Color.parseColor("#FF0015"));
                txt15Mins.setTextColor(Color.parseColor("#DA6317"));
                txt40Mins.setTextColor(Color.parseColor("#DA6317"));
                txtAll.setTextColor(Color.parseColor("#DA6317"));

                findRestaurantChk = false;
                findDishChk = true;
                find15MinsChk = false;
                find40MinsChk = false;
                findAllChk = false;
            }
        });

        txt15Mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRestaurant.setTextColor(Color.parseColor("#DA6317"));
                txtDish.setTextColor(Color.parseColor("#DA6317"));
                txt15Mins.setTextColor(Color.parseColor("#FF0015"));
                txt40Mins.setTextColor(Color.parseColor("#DA6317"));
                txtAll.setTextColor(Color.parseColor("#DA6317"));

                findRestaurantChk = false;
                findDishChk = false;
                find15MinsChk = true;
                find40MinsChk = false;
                findAllChk = false;
            }
        });

        txt40Mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRestaurant.setTextColor(Color.parseColor("#DA6317"));
                txtDish.setTextColor(Color.parseColor("#DA6317"));
                txt15Mins.setTextColor(Color.parseColor("#DA6317"));
                txt40Mins.setTextColor(Color.parseColor("#FF0015"));
                txtAll.setTextColor(Color.parseColor("#DA6317"));

                findRestaurantChk = false;
                findDishChk = false;
                find15MinsChk = false;
                find40MinsChk = true;
                findAllChk = false;
            }
        });

        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRestaurant.setTextColor(Color.parseColor("#DA6317"));
                txtDish.setTextColor(Color.parseColor("#DA6317"));
                txt15Mins.setTextColor(Color.parseColor("#DA6317"));
                txt40Mins.setTextColor(Color.parseColor("#DA6317"));
                txtAll.setTextColor(Color.parseColor("#FF0015"));

                findRestaurantChk = false;
                findDishChk = false;
                find15MinsChk = false;
                find40MinsChk = false;
                findAllChk = true;
            }
        });
    }

    private void setupHeader() {
        headerContainerFragment.removeAllViews();
        headerContainerFragment.addView(headerCard.getView());
   }

   private void findDish(String keyword, SearchResultCard searchResultCard) {
       Cursor dishData = dbManager.GetData("SELECT * FROM [Dish] " +
               "WHERE [name] LIKE '%"+keyword+"%'");

       while (dishData.moveToNext()) {
           DishEntity dishEntity = Utils.dishMapping(dishData);
           DishCard dishCard = new DishCard(context);
           int id = context.getResources().getIdentifier(dishEntity.getPicture(), "drawable", context.getPackageName());
           dishCard.img.setImageResource(id);
           dishCard.txtName.setText(dishEntity.getName());
           dishCard.txtPrice.setText("$" + dishEntity.getPrice().toString());
           dishCard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, hcmute.group6.buyfood.DishDetailActivity.class);
                   intent.putExtra("dishEntity", dishEntity);
                   context.startActivity(intent);
               }
           });
           searchResultCard.gvContainer.addView(dishCard.getView());
       }
   }

   private void findRestaurant(String keyword, SearchResultCard searchResultCard) {
       Cursor restaurantData = dbManager.GetData("SELECT * FROM [Restaurant] " +
               "WHERE [name] LIKE '%"+keyword+"%'");

       while (restaurantData.moveToNext()) {
           RestaurantEntity restaurantEntity = Utils.restaurantMapping(restaurantData);
           RestaurantCard restaurantCard = new RestaurantCard(context);
           int id = context.getResources().getIdentifier(restaurantEntity.getPicture(), "drawable", context.getPackageName());
           restaurantCard.img.setImageResource(id);

           restaurantCard.txtName.setText(restaurantEntity.getName());
           restaurantCard.txtEstimatedTime.setText(restaurantEntity.getEstimatedTime().toString() + " mins");
           restaurantCard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, hcmute.group6.buyfood.RestaurantDetailActivity.class);
                   intent.putExtra("restaurantEntity", restaurantEntity);
                   context.startActivity(intent);
               }
           });
           searchResultCard.gvContainer.addView(restaurantCard.getView());
       }
   }

   private void find15Mins(String keyword, SearchResultCard searchResultCard) {
       Cursor restaurantData = dbManager.GetData("SELECT * FROM [Restaurant] " +
               "WHERE [name] LIKE '%"+keyword+"%' AND [estimated_time] <= 15");

       while (restaurantData.moveToNext()) {
           RestaurantEntity restaurantEntity = Utils.restaurantMapping(restaurantData);
           RestaurantCard restaurantCard = new RestaurantCard(context);
           int id = context.getResources().getIdentifier(restaurantEntity.getPicture(), "drawable", context.getPackageName());
           restaurantCard.img.setImageResource(id);

           restaurantCard.txtName.setText(restaurantEntity.getName());
           restaurantCard.txtEstimatedTime.setText(restaurantEntity.getEstimatedTime().toString() + " mins");
           restaurantCard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, hcmute.group6.buyfood.RestaurantDetailActivity.class);
                   intent.putExtra("restaurantEntity", restaurantEntity);
                   context.startActivity(intent);
               }
           });
           searchResultCard.gvContainer.addView(restaurantCard.getView());
       }
   }

   private void find40Mins(String keyword, SearchResultCard searchResultCard) {
       Cursor restaurantData = dbManager.GetData("SELECT * FROM [Restaurant] " +
               "WHERE [name] LIKE '%"+keyword+"%' AND [estimated_time] <= 40");

       while (restaurantData.moveToNext()) {
           RestaurantEntity restaurantEntity = Utils.restaurantMapping(restaurantData);
           RestaurantCard restaurantCard = new RestaurantCard(context);
           int id = context.getResources().getIdentifier(restaurantEntity.getPicture(), "drawable", context.getPackageName());
           restaurantCard.img.setImageResource(id);

           restaurantCard.txtName.setText(restaurantEntity.getName());
           restaurantCard.txtEstimatedTime.setText(restaurantEntity.getEstimatedTime().toString() + " mins");
           restaurantCard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, hcmute.group6.buyfood.RestaurantDetailActivity.class);
                   intent.putExtra("restaurantEntity", restaurantEntity);
                   context.startActivity(intent);
               }
           });
           searchResultCard.gvContainer.addView(restaurantCard.getView());
       }
   }
}