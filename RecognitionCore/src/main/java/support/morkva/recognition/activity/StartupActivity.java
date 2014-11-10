package support.morkva.recognition.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;


import com.mayer.solution.Controller;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import support.morkva.recognition.R;
import support.morkva.recognition.adapter.TabsPagerAdapter;

/**
 * Created by irikhmayer on 06.11.2014.
 */
@EActivity(R.layout.activity_launcher)
@OptionsMenu(R.menu.launcher)
public class StartupActivity extends BasicActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    @ViewById
    protected ViewPager pager;

    private ActionBar actionBar;
    protected TabsPagerAdapter fragmentAdapter;

    public static void startClearTop(Context context) {
//        StartupActivity_.intent(context).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        actionBar = getActionBar();
        Controller.get();
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {
        fragmentAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(fragmentAdapter);
        pager.setOnPageChangeListener(this);
        if (actionBar != null) {

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                getActionBar().setDisplayHomeAsUpEnabled(true);
//            }
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab().setText(Controller.get().getNewOperator().getTestString()).setIcon(R.drawable.ic_crop).setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText(R.string.title_calculate).setIcon(R.drawable.ic_calculate).setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText(R.string.title_history).setIcon(R.drawable.ic_list).setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onPageSelected(int i) {
        actionBar.setSelectedNavigationItem(i);
    }

    @OptionsItem
    protected void actionSettingsSelected() {
        // Do nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Do nothing
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Do nothing
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Do nothing
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Do nothing
    }
}
