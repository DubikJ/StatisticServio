package ua.com.servio.statisticservio.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.app.StatServApplication;
import ua.com.servio.statisticservio.fragment.DownloadForecastFragment;
import ua.com.servio.statisticservio.fragment.FragmentStartSync;
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

    private static final String DATE_START_KEY = "date_start_key";
    private static final String DATE_END_KEY = "date_end_key";
    private static final String OBJECTVIEW_KEY = "abjectview_key";
    private static final String OBJECTTYPE_KEY = "abjecttype_key";
    private static final String SELECTED_FRAGMENT_KEY = "selected_fragment_key";
    private boolean doubleBackToExitPressedOnce = false;

    @Inject
    ActivityUtils activityUtils;

    @Inject
    NetworkUtils networkUtils;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout containerView;
    private TextView noDataView;
    private FragmentManager mFragmentManager;
    private Fragment selectedFragment;
    private ProgressDialog dialogLoad;
    private Calendar dateStart = Calendar.getInstance();
    private Calendar dateEnd = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat dateFormatterQuery = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
    private int objectView, objectType;
    private List<String> listObjectView = new ArrayList<>();
    private List<String> listObjectType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ((StatServApplication) getApplication()).getComponent().inject(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        containerView = (FrameLayout) findViewById(R.id.containerView);
        containerView.setVisibility(View.GONE);
        noDataView = (TextView) findViewById(R.id.no_selectedorder);
        noDataView.setVisibility(View.VISIBLE);

        mFragmentManager = getSupportFragmentManager();
        initToobar();
        initNavigationView();
        initDialogLoad();
        initParams();
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

    public String getDateStart() {
        return dateFormatterQuery.format(dateStart.getTime());
    }

    public String getDateEnd() {
        return dateFormatterQuery.format(dateEnd.getTime());
    }

    public String getObjectView() {
        return String.valueOf(objectView);
    }

    public String getObjectType() {
        return String.valueOf(objectType);
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
            showFilter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initParams(){

        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);

        dateEnd.set(Calendar.HOUR_OF_DAY, 0);
        dateEnd.set(Calendar.MINUTE, 0);
        dateEnd.set(Calendar.SECOND, 0);

        listObjectView.add(getString(R.string.objectview_0));
        listObjectView.add(getString(R.string.objectview_1));

        listObjectType.add(getString(R.string.objecttype_0));
        listObjectType.add(getString(R.string.objecttype_1));
        listObjectType.add(getString(R.string.objecttype_2));
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
                        selectedFragment = new SummaryStaticFragment();
                        xfragmentTransaction.replace(R.id.containerView, selectedFragment).commit();
                        getSupportActionBar().setSubtitle(R.string.summary_static);
                        showFilter();
                        break;
                    case R.id.nav_sales:
                        selectedFragment = new SalesFragment();
                        xfragmentTransaction.replace(R.id.containerView, selectedFragment).commit();
                        getSupportActionBar().setSubtitle(R.string.sales);
                        showFilter();
                        break;
                    case R.id.nav_report_manager:
                        selectedFragment = new ReportManagerFragment();
                        xfragmentTransaction.replace(R.id.containerView, selectedFragment).commit();
                        getSupportActionBar().setSubtitle(R.string.report_manager);
                        showFilter();
                        break;
                    case R.id.nav_download_forecast:
                        selectedFragment = new DownloadForecastFragment();
                        xfragmentTransaction.replace(R.id.containerView, selectedFragment).commit();
                        getSupportActionBar().setSubtitle(R.string.download_forecast);
                        showFilter();
                        break;
                    case R.id.nav_settings:
//                        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
//                        getSupportActionBar().setSubtitle("");
                        break;
                    case R.id.nav_exit:
                        logout();
                        break;
                }

                noDataView.setVisibility(View.GONE);
                containerView.setVisibility(View.VISIBLE);

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initDialogLoad() {

        dialogLoad = new ProgressDialog(this); // this = YourActivity
        dialogLoad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoad.setMessage(getString(R.string.loading_title));
        dialogLoad.setIndeterminate(true);
        dialogLoad.setCanceledOnTouchOutside(false);

    }

    public void showFilter(){

        if(selectedFragment==null){
            activityUtils.showMessage(getString(R.string.no_selectedorder), BasicActivity.this);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getResources().getString(R.string.filter));
        final View viewInflated = LayoutInflater.from(this).inflate(R.layout.activity_filter, null);

        final EditText dateStartET = (EditText) viewInflated.findViewById(R.id.dateStart);
        dateStartET.setFocusable(false);
        dateStartET.setText(dateFormatter.format(dateStart.getTime()));
        dateStartET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.showDatePicket(BasicActivity.this,
                        dateStart.get(Calendar.YEAR),
                        dateStart.get(Calendar.MONTH),
                        dateStart.get(Calendar.DAY_OF_MONTH),
                        new ActivityUtils.DatePicketSet() {
                            @Override
                            public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                                dateStart.set(Calendar.YEAR, year);
                                dateStart.set(Calendar.MONTH, monthOfYear);
                                dateStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dateStartET.setText(dateFormatter.format(dateStart.getTime()));
                            }
                        });
            }
        });

        final EditText dateEndET = (EditText) viewInflated.findViewById(R.id.dateEnd);
        dateEndET.setFocusable(false);
        dateEndET.setText(dateFormatter.format(dateEnd.getTime()));
        dateEndET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.showDatePicket(BasicActivity.this,
                        dateEnd.get(Calendar.YEAR),
                        dateEnd.get(Calendar.MONTH),
                        dateEnd.get(Calendar.DAY_OF_MONTH),
                        new ActivityUtils.DatePicketSet() {
                            @Override
                            public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                                dateEnd.set(Calendar.YEAR, year);
                                dateEnd.set(Calendar.MONTH, monthOfYear);
                                dateEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dateEndET.setText(dateFormatter.format(dateEnd.getTime()));
                            }
                        });
            }
        });

        Spinner objectViewS = (Spinner) viewInflated.findViewById(R.id.objectview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_with_spinner,listObjectView);

        adapter.setDropDownViewResource(R.layout.item_with_spinner);
        objectViewS.setAdapter(adapter);
        objectViewS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                objectView = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner objectTypeS = (Spinner) viewInflated.findViewById(R.id.objecttype);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                R.layout.item_with_spinner,listObjectType);

        adapterType.setDropDownViewResource(R.layout.item_with_spinner);
        objectTypeS.setAdapter(adapterType);
        objectTypeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                objectType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(viewInflated);

        builder.setPositiveButton(getResources().getString(R.string.questions_answer_form), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.questions_answer_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        final AlertDialog alertQuestion = builder.create();
        alertQuestion.show();
        alertQuestion.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dateStart.getTimeInMillis() > dateEnd.getTimeInMillis() ){
                    activityUtils.showLongToast(BasicActivity.this, getString(R.string.compare_period_error));
                    return;
                }

                alertQuestion.dismiss();
                ((FragmentStartSync) selectedFragment).startSync();

            }
        });
        alertQuestion.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putLong(DATE_START_KEY, dateStart.getTimeInMillis());
        savedInstanceState.putLong(DATE_END_KEY, dateEnd.getTimeInMillis());
        savedInstanceState.putInt(OBJECTVIEW_KEY, objectView);
        savedInstanceState.putInt(OBJECTTYPE_KEY, objectType);

        if(selectedFragment!=null) {
            getSupportFragmentManager().putFragment(savedInstanceState,
                    SELECTED_FRAGMENT_KEY, selectedFragment);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(DATE_START_KEY)) {
            dateStart.setTimeInMillis(savedInstanceState.getLong(DATE_START_KEY));
        }

        if (savedInstanceState.containsKey(DATE_END_KEY)) {
            dateEnd.setTimeInMillis(savedInstanceState.getLong(DATE_END_KEY));
        }

        if (savedInstanceState.containsKey(OBJECTVIEW_KEY)) {
            objectView = savedInstanceState.getInt(OBJECTVIEW_KEY);
        }

        if (savedInstanceState.containsKey(OBJECTTYPE_KEY)) {
            objectType = savedInstanceState.getInt(OBJECTTYPE_KEY);
        }

        if (savedInstanceState.containsKey(SELECTED_FRAGMENT_KEY)) {
            selectedFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                    SELECTED_FRAGMENT_KEY);
            noDataView.setVisibility(View.GONE);
            containerView.setVisibility(View.VISIBLE);

        }

    }

}
