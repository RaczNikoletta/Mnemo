package com.example.fogas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.UserDataModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import okhttp3.internal.http2.Header;

public class MainMenu extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private Context cont;
    private View headerv;
    private View footerv;
    private Button loginBtn;
    private Button logOffBtn;
    private TextView userTv;
    private TextView titleTv;
    private Realm mainRealm;


    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        cont = this;


        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        goToFragment(new PracticeFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));

    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    //If Log off button clicked
    @Override
    public void onFooterClicked() {
        try {
            mainRealm = Realm.getDefaultInstance();
            mainRealm.executeTransaction(r -> {
                UserDataModel getlogged = mainRealm.where(UserDataModel.class)
                        .equalTo("loggedIn", true).findFirst();
                if (getlogged != null) {
                    getlogged.setLoggedIn(false);
                    startActivity(new Intent(this,MainMenu.class));
                    finish();
                } else {
                    //This should not happen
                    Toast.makeText(this, "Log off fail check database login variable", Toast.LENGTH_LONG).show();
                }

            });
        } catch (Throwable e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        } finally {
            if (mainRealm != null) {
                mainRealm.close();
            }


        }
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,headerCreator.class));

    }


    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }


    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case(0):
                goToFragment(new PracticeFragment(), false);
                break;
            case(1):
                goToFragment(new updateFragment(),false);
                break;
            case(2):
                goToFragment(new wordPracticeFragment(), false);
                break;
            default:
                goToFragment(new PracticeFragment(), false);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            headerv = mDuoMenuView.getHeaderView();
            footerv = mDuoMenuView.getFooterView();
            try {
                userTv = headerv.findViewById(R.id.userTv);
                titleTv = headerv.findViewById(R.id.titleTv);
                loginBtn = headerv.findViewById(R.id.loginBtn);
                logOffBtn = footerv.findViewById(R.id.logOffBtn);
            } catch (Throwable e) {
                Toast.makeText(cont, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void openActivity(View v) {
        startActivity(new Intent(this, Login.class));
    }


    @Override
    protected void onStart() {
        try {
            super.onStart();
            mainRealm = Realm.getDefaultInstance();
            mainRealm.executeTransaction(r -> {
                UserDataModel getlogged = mainRealm.where(UserDataModel.class)
                        .equalTo("loggedIn", true).findFirst();
                if(getlogged !=null)
                {
                    userTv.setText(getlogged.getUserName());
                    titleTv.setText(getlogged.getTitle());
                    loginBtn.setVisibility(View.GONE);
                    logOffBtn.setVisibility(View.VISIBLE);
                }else{
                    userTv.setText(R.string.VendegUser);
                    loginBtn.setVisibility(View.VISIBLE);
                    logOffBtn.setVisibility(View.GONE);
                }

            });
        }catch(Throwable e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }finally {
            if(mainRealm != null)
            {
                mainRealm.close();
            }
        }
    }


}