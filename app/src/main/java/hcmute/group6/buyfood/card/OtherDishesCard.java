package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import hcmute.group6.buyfood.MainActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.database.DBManager;
import hcmute.group6.buyfood.utils.Utils;

public class OtherDishesCard extends BaseCard{
    DBManager dbManager;

    private int layout = R.layout.card_other_dishes;
    private View view;
    private Context context;
    private BaseCard srcCard;

    public ImageView imgReturn;
    public FrameLayout containerHomeFrame;
    public GridLayout gvContainer;

    @Override
    protected void mapping() {
        dbManager = new DBManager(this.context);
        imgReturn = view.findViewById(R.id.otherDishesCard_imgReturn);
        containerHomeFrame = ((MainActivity) context).findViewById(R.id.containerHomeFrame);
        gvContainer = view.findViewById(R.id.otherDishesCard_gvContainer);
    }

    @Override
    public View getView() {
        return this.view;
    }

    public OtherDishesCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    public OtherDishesCard(Context context, BaseCard srcCard) {
        this.context = context;
        this.srcCard = srcCard;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        Utils.setupOtherDishes(gvContainer, dbManager, this.context);
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
}
