package hcmute.group6.buyfood.card;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import hcmute.group6.buyfood.MainActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.utils.Utils;

public class NearestRestaurantCard extends BaseCard{

    private int layout = R.layout.card_nearest_restaurant;
    private View view;
    private Context context;
    private BaseCard srcCard;
    private DBManager dbManager;

    public ImageView imgReturn;
    public FrameLayout containerHomeFrame;
    public GridLayout gvContainer;

    @Override
    protected void mapping() {
        dbManager = new DBManager(this.context);
        imgReturn = view.findViewById(R.id.imgReturn);
        containerHomeFrame = ((MainActivity) context).findViewById(R.id.containerHomeFrame);
        gvContainer = view.findViewById(R.id.nearestRestaurantCard_gvContainer);
    }

    public NearestRestaurantCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    public NearestRestaurantCard(Context context, BaseCard srcCard) {
        this.context = context;
        this.srcCard = srcCard;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    protected void onCreate() {
        mapping();
        setListenerEvent();

        Utils.setupNearestRestaurant(gvContainer, dbManager, this.context);
    }

    @Override
    protected void setListenerEvent() {
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView scrollView = ((MainActivity) context).findViewById(R.id.homeFragment_scrollView);
                scrollView.setScrollY(0);
                containerHomeFrame.removeAllViews();
                containerHomeFrame.addView(srcCard.getView());
            }
        });
    }


    private void setupNearestRestaurant() {
        gvContainer.removeAllViews();

        Cursor restaurantData = dbManager.GetData("SELECT [picture], [name], [estimated_time] FROM [Restaurant];");
        int i=0;
        while (restaurantData.moveToNext() && i<9) {
            String name = restaurantData.getString(1);
            String estimatedTime = restaurantData.getString(2);
            String picName = restaurantData.getString(0);
            RestaurantCard restaurantCard = new RestaurantCard(context);

            int id = this.context.getResources().getIdentifier(picName, "drawable", this.context.getPackageName());
            restaurantCard.img.setImageResource(id);

            restaurantCard.txtName.setText(name);
            restaurantCard.txtEstimatedTime.setText(estimatedTime + " mins");
            gvContainer.addView(restaurantCard.getView());

            i++;
        }

    }
}
