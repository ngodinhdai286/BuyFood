package hcmute.group6.buyfood.card;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import hcmute.group6.buyfood.DishDetailActivity;
import hcmute.group6.buyfood.MainActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.utils.Utils;

public class PopularDishesCard extends BaseCard{

    private int layout = R.layout.card_popular_dishes;
    private View view;
    private Context context;
    private BaseCard srcCard;
    private DBManager dbManager;

    public ImageView imgReturn;
    public FrameLayout containerHomeFrame;
    public GridLayout gvContainer;

    @Override
    protected void mapping() {
        imgReturn = view.findViewById(R.id.imgReturn_popularDishes);
        containerHomeFrame = ((MainActivity) context).findViewById(R.id.containerHomeFrame);
        gvContainer = view.findViewById(R.id.popularDishesCard_gvContainer);
        dbManager = new DBManager(this.context);
    }

    public PopularDishesCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    public PopularDishesCard(Context context, BaseCard srcCard) {
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
        super.onCreate();

        Utils.setUpPopularDishes(gvContainer, dbManager, this.context);
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

    private void setupPopularDishes() {
        gvContainer.removeAllViews();

        Cursor dishData = dbManager.GetData("SELECT [picture], [name], [price] FROM [Dish];");
        int i=0;
        while (dishData.moveToNext() && i<19) {
            String picName = dishData.getString(0);
            String name = dishData.getString(1);
            String price = dishData.getString(2);

            DishCard dishCard = new DishCard(context);
            int id = this.context.getResources().getIdentifier(picName, "drawable", this.context.getPackageName());
            dishCard.img.setImageResource(id);
            dishCard.txtName.setText(name);
            dishCard.txtPrice.setText("$" + price);
            dishCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DishDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            gvContainer.addView(dishCard.getView());

            i++;
        }

    }
}
