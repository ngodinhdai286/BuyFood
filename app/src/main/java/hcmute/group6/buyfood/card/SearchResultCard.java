package hcmute.group6.buyfood.card;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.SearchActivity;

public class SearchResultCard extends BaseCard{
    private int layout = R.layout.card_search_result;
    private View view;
    private Context context;

    public ImageView imgReturn;
    public GridLayout gvContainer;

    @Override
    protected void mapping() {
        imgReturn = view.findViewById(R.id.searchResultCard_imgReturn);
        gvContainer = view.findViewById(R.id.searchResultCard_gvContainer);
    }

    public SearchResultCard(Context context) {
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
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                ((SearchActivity)context).finish();
            }
        });
    }
}
