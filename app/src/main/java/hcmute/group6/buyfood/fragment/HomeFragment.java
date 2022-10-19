package hcmute.group6.buyfood.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import hcmute.group6.buyfood.R;
import hcmute.group6.buyfood.card.HeaderCard;
import hcmute.group6.buyfood.card.WelcomeCard;


public class HomeFragment extends Fragment {
    private View view;

    public ScrollView scrollView;

    public WelcomeCard welcomeCard;
    public HeaderCard headerCard;
    public FrameLayout containerHomeFrame, headerContainer;

    int oldScrollY = 0;

    public void mapping() {
        scrollView = view.findViewById(R.id.homeFragment_scrollView);
        containerHomeFrame = view.findViewById(R.id.containerHomeFrame);
        headerContainer = view.findViewById(R.id.homeFragment_headerContainerFrame);
        welcomeCard = new WelcomeCard(getActivity());
        headerCard = new HeaderCard(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapping();
        setCard();
        setEvent();
        scrollView.requestFocus();
    }

    private void setCard() {
        // set header
        headerContainer.removeAllViews();
        headerContainer.addView(headerCard.getView());

        // set welcome card
        containerHomeFrame.removeAllViews();
        containerHomeFrame.addView(welcomeCard.getView());
    }

    private void setEvent() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int interval = 15;
                int scrollY = scrollView.getScrollY();
                if (getActivity() != null) {
                    TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);

                    if (scrollY - oldScrollY > interval) {
                        tabLayout.getTabAt(0).setIcon(null);
                        tabLayout.getTabAt(1).setIcon(null);
                        tabLayout.getTabAt(2).setIcon(null);
                        tabLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                    } else if (oldScrollY - scrollY > interval) {
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_orders);
                        tabLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                        ;
                    }
                }

                oldScrollY = scrollY;
            }
        });


    }
}
