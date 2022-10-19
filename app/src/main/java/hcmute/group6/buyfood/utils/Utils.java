package hcmute.group6.buyfood.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hcmute.group6.buyfood.DishDetailActivity;
import hcmute.group6.buyfood.RestaurantDetailActivity;
import hcmute.group6.buyfood.card.DishCard;
import hcmute.group6.buyfood.card.RestaurantCard;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.entity.DishEntity;
import hcmute.group6.buyfood.entity.NotificationEntity;
import hcmute.group6.buyfood.entity.OrderEntity;
import hcmute.group6.buyfood.entity.RestaurantEntity;
import hcmute.group6.buyfood.entity.UserEntity;

public class Utils {
    public static void setUpPopularDishes(ViewGroup container, DBManager dbManager, Context context) {
        container.removeAllViews();

        Cursor dishData = dbManager.GetData("SELECT * FROM [Dish];");

        int i = 0;
        while (dishData.moveToNext() && i < 19) {
            DishEntity dishEntity = Utils.dishMapping(dishData);

            DishCard dishCard = new DishCard(context);
            int id = context.getResources().getIdentifier(dishEntity.getPicture(), "drawable", context.getPackageName());
            dishCard.img.setImageResource(id);
            dishCard.txtName.setText(dishEntity.getName());
            dishCard.txtPrice.setText("$" + dishEntity.getPrice().toString());
            dishCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DishDetailActivity.class);
                    intent.putExtra("dishEntity", dishEntity);
                    context.startActivity(intent);
                }
            });
            container.addView(dishCard.getView());

            i++;
        }
    }


    public static void setupOtherDishes(ViewGroup container, DBManager dbManager, Context context) {
        container.removeAllViews();

        Cursor dishData = dbManager.GetData("SELECT * FROM [Dish];");
        for (int i = 0; i < 15; i++)
            dishData.moveToNext();

        int i = 0;
        while (dishData.moveToNext() && i < 10) {
            DishEntity dishEntity = Utils.dishMapping(dishData);

            DishCard dishCard = new DishCard(context);
            int id = context.getResources().getIdentifier(dishEntity.getPicture(), "drawable", context.getPackageName());
            dishCard.img.setImageResource(id);
            dishCard.txtName.setText(dishEntity.getName());
            dishCard.txtPrice.setText("$" + dishEntity.getPrice().toString());
            dishCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DishDetailActivity.class);
                    intent.putExtra("dishEntity", dishEntity);
                    context.startActivity(intent);
                }
            });
            container.addView(dishCard.getView());

            i++;
        }

    }

    public static void setupNearestRestaurant(ViewGroup container, DBManager dbManager, Context context) {
        container.removeAllViews();

        Cursor restaurantData = dbManager.GetData("SELECT [picture], [name], [estimated_time], " +
                "[id], [description], [rating], [address] FROM [Restaurant];");
        int i = 0;
        while (restaurantData.moveToNext() && i < 15) {
            RestaurantEntity restaurantEntity = new RestaurantEntity();
            restaurantEntity.setName(restaurantData.getString(1));
            restaurantEntity.setPicture(restaurantData.getString(0));
            restaurantEntity.setEstimatedTime(restaurantData.getInt(2));
            restaurantEntity.setId(restaurantData.getInt(3));
            restaurantEntity.setDescription(restaurantData.getString(4));
            restaurantEntity.setRating(restaurantData.getInt(5));
            restaurantEntity.setAddress(restaurantData.getString(6));

            RestaurantCard restaurantCard = new RestaurantCard(context);

            int id = context.getResources().getIdentifier(restaurantEntity.getPicture(), "drawable", context.getPackageName());
            restaurantCard.img.setImageResource(id);

            restaurantCard.txtName.setText(restaurantEntity.getName());
            restaurantCard.txtEstimatedTime.setText(restaurantEntity.getEstimatedTime().toString() + " mins");
            restaurantCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RestaurantDetailActivity.class);
                    intent.putExtra("restaurantEntity", restaurantEntity);
                    context.startActivity(intent);
                }
            });

            container.addView(restaurantCard.getView());

            i++;
        }

    }

    public static List<DishEntity> getDishesByRestaurantId(Integer restaurantId, DBManager dbManager) {
        List<DishEntity> dishEntities = new ArrayList<>();
        Cursor restaurantsData = dbManager.GetData("SELECT * FROM [Dish] " +
                "WHERE restaurant_id = " + restaurantId);

        while (restaurantsData.moveToNext()) {
            DishEntity dishEntity = new DishEntity();
            dishEntity.setId(restaurantsData.getInt(0));
            dishEntity.setName(restaurantsData.getString(1));
            dishEntity.setPrice(restaurantsData.getFloat(2));
            dishEntity.setPicture(restaurantsData.getString(3));
            dishEntity.setDescription(restaurantsData.getString(4));
            dishEntity.setRating(restaurantsData.getInt(5));
            dishEntity.setQuantitySold(restaurantsData.getInt(6));
            dishEntity.setRestaurantId(restaurantsData.getInt(7));

            dishEntities.add(dishEntity);
        }
        return dishEntities;
    }

    public static List<OrderEntity> getOrdersByEmail(String email, DBManager dbManager) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        Cursor orderCursor = dbManager.GetData("SELECT * FROM [Order] " +
                "WHERE [email] = '" + email + "'");

        while (orderCursor.moveToNext()) {
            OrderEntity orderEntity = Utils.orderMapping(orderCursor);
            orderEntities.add(orderEntity);
        }

        return orderEntities;
    }

    public static DishEntity dishMapping(Cursor data) {
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(data.getInt(0));
        dishEntity.setName(data.getString(1));
        dishEntity.setPrice(data.getFloat(2));
        dishEntity.setPicture(data.getString(3));
        dishEntity.setDescription(data.getString(4));
        dishEntity.setRating(data.getInt(5));
        dishEntity.setQuantitySold(data.getInt(6));
        dishEntity.setRestaurantId(data.getInt(7));

        return dishEntity;
    }

    public static RestaurantEntity restaurantMapping(Cursor data) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(data.getInt(0));
        restaurantEntity.setName(data.getString(1));
        restaurantEntity.setDescription(data.getString(2));
        restaurantEntity.setRating(data.getInt(3));
        restaurantEntity.setPicture(data.getString(4));
        restaurantEntity.setAddress(data.getString(5));
        restaurantEntity.setEstimatedTime(data.getInt(6));

        return restaurantEntity;
    }

    public static UserEntity userMapping(Cursor data) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(data.getString(0));
        userEntity.setPassword(data.getString(1));
        userEntity.setFullname(data.getString(2));
        userEntity.setAvatar(data.getString(3));
        userEntity.setAddress(data.getString(4));

        return userEntity;
    }

    public static OrderEntity orderMapping(Cursor data) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(data.getInt(0));
        orderEntity.setDeliverTo(data.getString(1));
        orderEntity.setPaymentMethod(data.getString(2));
        orderEntity.setDishId(data.getInt(3));
        orderEntity.setEmail(data.getString(4));
        orderEntity.setAmount(data.getFloat(5));

        return orderEntity;
    }

    public static NotificationEntity notificationMapping(Cursor data) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(data.getInt(0));
        notificationEntity.setEmail(data.getString(1));
        notificationEntity.setTitle(data.getString(2));
        notificationEntity.setContent(data.getString(3));
        if (data.getInt(4) == 0)
            notificationEntity.setIsNew(false);
        else
            notificationEntity.setIsNew(true);

        return notificationEntity;
    }

    public static void writeToFile(String data, Context context) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(data);
        outputStreamWriter.close();

    }

    public static String readFromFile(Context context) throws IOException {

        String ret = "";

        InputStream inputStream = context.openFileInput("config.txt");

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append("\n").append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }


        return ret;
    }
}
