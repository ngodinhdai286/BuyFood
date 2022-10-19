package hcmute.group6.buyfood.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hcmute.group6.buyfood.fragment.AccountFragment;
import hcmute.group6.buyfood.fragment.HomeFragment;
import hcmute.group6.buyfood.fragment.OrdersFragment;


public class ContainerViewPagerAdapter extends FragmentStatePagerAdapter {
    public ContainerViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new HomeFragment();
        else if (position == 1)
            return new AccountFragment();
        else if (position == 2)
            return new OrdersFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Home";
        else if (position == 1)
            return "Account";
        else if (position == 2)
            return "Orders";

        return "Home";
    }
}
