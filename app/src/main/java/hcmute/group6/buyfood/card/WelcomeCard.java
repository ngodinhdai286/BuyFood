package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import hcmute.group6.buyfood.MainActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.utils.Utils;

public class WelcomeCard extends BaseCard{
    private DBManager dbManager;

    private WelcomeCard welcomeCard;
    private View view;
    private Context context;

    public TextView txtViewMoreNearRest, txtViewPopularDishes, txtViewMoreOthers, txtBuyNow;
    public FrameLayout containerHomeFrame;
    public LinearLayout popularDishesContainerLayout, otherDishesContainerLayout, nearestRestaurantContainerLayout;


    @Override
    protected void mapping() {
        dbManager = new DBManager(this.context);

        txtViewMoreNearRest = view.findViewById(R.id.txtViewMoreNearRest);
        txtViewPopularDishes = view.findViewById(R.id.txtViewPopularDishes);
        txtViewMoreOthers = view.findViewById(R.id.txtViewMoreOthers);
        txtBuyNow = view.findViewById(R.id.welcomeCard_txtBuyNow);
        containerHomeFrame = ((MainActivity) context).findViewById(R.id.containerHomeFrame);
        popularDishesContainerLayout = view.findViewById(R.id.welcomeCard_popularDishesContainerLayout);
        otherDishesContainerLayout = view.findViewById(R.id.welcomeCard_otherDishesContainerLayout);
        nearestRestaurantContainerLayout = view.findViewById(R.id.welcomeCard_nearestRestaurantContainerLayout);
    }

    public WelcomeCard(Context context) {
        this.context = context;
        welcomeCard = this;
        view = View.inflate(this.context, R.layout.card_welcome, null);
        onCreate();
    }

    @Override
    public View getView(){
        return this.view;
    }

    @Override
    protected void setListenerEvent() {
        txtViewMoreNearRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView scrollView = ((MainActivity) context).findViewById(R.id.homeFragment_scrollView);
                scrollView.setScrollY(0);

                NearestRestaurantCard nearestRestaurantCard = new NearestRestaurantCard(context, welcomeCard);
                containerHomeFrame.removeAllViews();
                containerHomeFrame.addView(nearestRestaurantCard.getView());
            }
        });

        txtViewPopularDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView scrollView = ((MainActivity) context).findViewById(R.id.homeFragment_scrollView);
                scrollView.setScrollY(0);

                PopularDishesCard popularDishesCard = new PopularDishesCard(context, welcomeCard);
                containerHomeFrame.removeAllViews();
                containerHomeFrame.addView(popularDishesCard.getView());
            }
        });

        txtViewMoreOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView scrollView = ((MainActivity) context).findViewById(R.id.homeFragment_scrollView);
                scrollView.setScrollY(0);

                OtherDishesCard otherDishesCard = new OtherDishesCard(context, welcomeCard);
                containerHomeFrame.removeAllViews();
                containerHomeFrame.addView(otherDishesCard.getView());
            }
        });

        txtBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtViewPopularDishes.callOnClick();
            }
        });
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        Utils.setupNearestRestaurant(nearestRestaurantContainerLayout, dbManager, this.context);
        Utils.setUpPopularDishes(popularDishesContainerLayout, dbManager, this.context);
        Utils.setupOtherDishes(otherDishesContainerLayout, dbManager, this.context);
    }

}
