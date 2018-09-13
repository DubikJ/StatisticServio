package ua.com.servio.statisticservio.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.app.StatServApplication;
import ua.com.servio.statisticservio.fragment.DownloadForecastFragment;
import ua.com.servio.statisticservio.fragment.ReportManagerFragment;
import ua.com.servio.statisticservio.fragment.SalesFragment;
import ua.com.servio.statisticservio.fragment.SummaryStaticFragment;
import ua.com.servio.statisticservio.utils.ActivityUtils;
import ua.com.servio.statisticservio.utils.NetworkUtils;
import ua.com.servio.statisticservio.utils.SharedStorage;

import static ua.com.servio.statisticservio.common.Consts.LOGIN;
import static ua.com.servio.statisticservio.common.Consts.PASSWORD;
import static ua.com.servio.statisticservio.common.Consts.SERVER;

public class BasicActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Inject
    ActivityUtils activityUtils;

    @Inject
    NetworkUtils networkUtils;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FragmentManager mFragmentManager;
    private ProgressDialog dialogLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ((StatServApplication) getApplication()).getComponent().inject(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mFragmentManager = getSupportFragmentManager();
        initToobar();
        initNavigationView();
        initDialogLoad();
    }

    public ActivityUtils getActivityUtils() {
        return activityUtils;
    }

    public NetworkUtils getNetworkUtils() {
        return networkUtils;
    }

    public ProgressDialog getDialogLoad() {
        return dialogLoad;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            doubleBackToExitPressedOnce = true;
            activityUtils.showShortToast(this, getString(R.string.double_press_exit));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToobar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nav_summary_static:
                        xfragmentTransaction.replace(R.id.containerView, new SummaryStaticFragment()).commit();
                        getSupportActionBar().setSubtitle(R.string.summary_static);
                        break;
                    case R.id.nav_sales:
                        xfragmentTransaction.replace(R.id.containerView, new SalesFragment()).commit();
                        getSupportActionBar().setSubtitle(R.string.sales);
                        break;
                    case R.id.nav_report_manager:
                        xfragmentTransaction.replace(R.id.containerView, new ReportManagerFragment()).commit();
                        getSupportActionBar().setSubtitle(R.string.report_manager);
                        break;
                    case R.id.nav_download_forecast:
                        xfragmentTransaction.replace(R.id.containerView, new DownloadForecastFragment()).commit();
                        getSupportActionBar().setSubtitle(R.string.download_forecast);
                        break;
                    case R.id.nav_settings:
//                        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
//                        getSupportActionBar().setSubtitle("");
                        break;
                    case R.id.nav_exit:
                        logout();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initDialogLoad() {

        dialogLoad = new ProgressDialog(this); // this = YourActivity
        dialogLoad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoad.setMessage("Loading. Please wait...");
        dialogLoad.setIndeterminate(true);
        dialogLoad.setCanceledOnTouchOutside(false);

    }

    private void logout() {

        activityUtils.showQuestion(
                BasicActivity.this,
                getString(R.string.action_exit),
                getString(R.string.questions_exit),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
                        SharedStorage.setString(BasicActivity.this, SERVER, "");
                        SharedStorage.setString(BasicActivity.this, LOGIN, "");
                        SharedStorage.setString(BasicActivity.this, PASSWORD, "");
                        Intent intent = new Intent(getBaseContext(), BootAct.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNegativeAnsver() {

                    }

                    @Override
                    public void onNeutralAnsver() {

                    }
                });
    }

}
