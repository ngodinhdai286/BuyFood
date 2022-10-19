package hcmute.group6.buyfood.card;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import hcmute.group6.buyfood.MainActivity;
import hcmute.group6.buyfood.NotificationActivity;
import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.SearchActivity;

public class HeaderCard extends BaseCard{
    private int layout = R.layout.card_header;
    private View view;
    private Context context;

    public EditText edtTextSearch;
    public ConstraintLayout titleLayout;
    public ImageView imgFilter, imgNoti;

    @Override
    protected void mapping() {
        edtTextSearch = view.findViewById(R.id.headerCard_edtTextSearch);
        titleLayout = view.findViewById(R.id.headerCard_titleLayout);
        imgFilter = view.findViewById(R.id.headerCard_imgFilter);
        imgNoti = view.findViewById(R.id.headerCard_imgNoti);
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    protected void setListenerEvent() {
        edtTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (context instanceof MainActivity) {
                        Intent intent = new Intent(context, SearchActivity.class);
                        context.startActivity(intent);
                        titleLayout.requestFocus();
                    }

                }
            }
        });

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity) {
                    Intent intent = new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                    titleLayout.requestFocus();
                }
            }
        });

        imgNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotificationActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public HeaderCard(Context context) {
        this.context = context;
        view = View.inflate(this.context, layout, null);
        onCreate();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }
}
