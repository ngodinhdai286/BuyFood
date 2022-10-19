package hcmute.group6.buyfood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hcmute.group6.buyfood.adapter.ContainerViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPagerContainer;

    private void mapping() {

        tabLayout = findViewById(R.id.tabLayout);
        viewPagerContainer = findViewById(R.id.viewPagerContainer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        setupTabLayout();

        setEvent();

//        Intent intent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
//        startActivity(intent);
    }

    private void setEvent(){

    }

    private void setupTabLayout() {
        ContainerViewPagerAdapter containerViewPagerAdapter = new ContainerViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerContainer.setAdapter(containerViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerContainer);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_orders);
    }

}