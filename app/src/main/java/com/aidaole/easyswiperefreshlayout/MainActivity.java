package com.aidaole.easyswiperefreshlayout;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aidaole.easyswiperefreshlayout.fragments.DefaultFragment;
import com.aidaole.easyswiperefreshlayout.fragments.FixedHeaderFragment;
import com.aidaole.easyswiperefreshlayout.fragments.MoveHeaderFragment;
import com.aidaole.easyswiperefreshlayout.fragments.NestedListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String TAG = "EasyRefreshLayout";

    private BottomNavigationView mBottomNavigations;

    private Fragment mCurFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigations = findViewById(R.id.bottom_navigations);
        mBottomNavigations.setOnItemSelectedListener(this);
        showFragment(DefaultFragment.class.getSimpleName());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        boolean selected = false;
        if (menuItem.getItemId() == R.id.action_default) {
            showFragment(DefaultFragment.class.getSimpleName());
            selected = true;
        } else if (menuItem.getItemId() == R.id.action_rocket_fragment) {
            showFragment(MoveHeaderFragment.class.getSimpleName());
            selected = true;
        } else if (menuItem.getItemId() == R.id.action_earth_fragment) {
            showFragment(FixedHeaderFragment.class.getSimpleName());
            selected = true;
        } else if (menuItem.getItemId() == R.id.action_nest_fragment) {
            showFragment(NestedListFragment.class.getSimpleName());
            selected = true;
        }
        return selected;
    }

    private void showFragment(String fragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            if (DefaultFragment.class.getSimpleName().equals(fragmentTag)) {
                fragment = DefaultFragment.newInstance();
            } else if (MoveHeaderFragment.class.getSimpleName().equals(fragmentTag)) {
                fragment = MoveHeaderFragment.newInstance();
            } else if (FixedHeaderFragment.class.getSimpleName().equals(fragmentTag)) {
                fragment = FixedHeaderFragment.newInstance();
            } else if (NestedListFragment.class.getSimpleName().equals(fragmentTag)) {
                fragment = NestedListFragment.newInstance();
            }
            ft.add(R.id.content, fragment, fragmentTag);
        }
        if (mCurFragment != null) {
            ft.hide(mCurFragment);
        }
        ft.show(fragment);
        mCurFragment = fragment;
        ft.commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
