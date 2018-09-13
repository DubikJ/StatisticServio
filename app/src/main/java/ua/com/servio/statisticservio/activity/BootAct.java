package ua.com.servio.statisticservio.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.app.StatServApplication;
import ua.com.servio.statisticservio.model.json.AuthPerson;
import ua.com.servio.statisticservio.model.json.AuthResult;
import ua.com.servio.statisticservio.service.sync.SyncService;
import ua.com.servio.statisticservio.service.sync.SyncServiceFactory;
import ua.com.servio.statisticservio.utils.ActivityUtils;
import ua.com.servio.statisticservio.utils.NetworkUtils;
import ua.com.servio.statisticservio.utils.SharedStorage;

import static ua.com.servio.statisticservio.common.Consts.LOGIN;
import static ua.com.servio.statisticservio.common.Consts.PASSWORD;
import static ua.com.servio.statisticservio.common.Consts.SERVER;

public class BootAct extends Activity {

    private static final String PASSWORD_KEY = "password_key";
    private static final String LOGIN_KEY = "login_key";

    @Inject
    ActivityUtils activityUtils;

    @Inject
    NetworkUtils networkUtils;

    private AlertDialog alertQuestion;
    private EditText loginET, passwordET;
    private ProgressDialog dialogLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((StatServApplication) getApplication()).getComponent().inject(this);

        if (TextUtils.isEmpty(SharedStorage.getString(this, SERVER, ""))) {
            showQuestion();
        }else{
            showActivity();
        }

        initLoadAnimation();

    }

    @Override
    public void onBackPressed() {
        activityUtils.showQuestion(BootAct.this, getString(R.string.action_exit),
                getString(R.string.questions_exit),
                new ActivityUtils.QuestionAnswer() {
                    @Override
                    public void onPositiveAnsver() {
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


    private void startSync(){

        if (!networkUtils.checkEthernet()) {
            activityUtils.showMessage(getString(R.string.error_internet_connecting), BootAct.this);
            return;
        }

        dialogLoad.show();

        SyncService syncService = SyncServiceFactory.createService(
                SyncService.class, BootAct.this);

        Observable observable = syncService.authPerson(new AuthPerson(
                Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID),
                loginET.getText().toString(),
                passwordET.getText().toString()
        ));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthResult>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(AuthResult authResult) {
                        dialogLoad.cancel();
                        if(authResult == null) {
                            activityUtils.showMessage(getString(R.string.error_no_data), BootAct.this);
                            return;
                        }
//                        if(!TextUtils.isEmpty(authResult.getError())) {
//                            activityUtils.showMessage(authResult.getError(), BootAct.this);
//                            return;
//                        }
                        alertQuestion.dismiss();
                        SharedStorage.setString(BootAct.this, SERVER, authResult.getServiceLink());
                        SharedStorage.setString(BootAct.this, LOGIN, loginET.getText().toString());
                        SharedStorage.setString(BootAct.this, PASSWORD, passwordET.getText().toString());

                        showActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogLoad.cancel();
                        if (!networkUtils.checkEthernet()) {
                            activityUtils.showMessage(getString(R.string.error_internet_connecting), BootAct.this);
                            return;
                        }
                        activityUtils.showMessage(e.getMessage(), BootAct.this);
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }


    private void showQuestion(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getResources().getString(R.string.welcome));
        final View viewInflated = LayoutInflater.from(this).inflate(R.layout.activity_boot, null);

        loginET = (EditText) viewInflated.findViewById(R.id.login);

        String login = SharedStorage.getString(this, LOGIN, "");
        if (TextUtils.isEmpty(login)) {
            TelephonyManager tel = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                login = tel.getLine1Number();
            }
        }
        loginET.setText(login);
        passwordET = (EditText) viewInflated.findViewById(R.id.password);
        passwordET.setText(SharedStorage.getString(this, PASSWORD, ""));

        builder.setView(viewInflated);

        builder.setPositiveButton(getResources().getString(R.string.questions_answer_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.action_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        alertQuestion = builder.create();
        alertQuestion.show();
        alertQuestion.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        alertQuestion.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean cancel = false;
                View focusView = null;

                String loginText = String.valueOf(loginET.getText());

                if (loginText==null || loginText.isEmpty()) {

                    loginET.setError(getString(R.string.error_field_required));
                    focusView = loginET;
                    cancel = true;
                }
                if (cancel) {

                    focusView.requestFocus();

                } else {
                    startSync();
                }

            }
        });
        alertQuestion.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void showActivity(){

        Intent intent = new Intent(BootAct.this, BasicActivity.class);
        startActivity(intent);
        finish();
        BootAct.this.finish();

    }

    private void initLoadAnimation() {

        dialogLoad = new ProgressDialog(this); // this = YourActivity
        dialogLoad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoad.setMessage("Loading. Please wait...");
        dialogLoad.setIndeterminate(true);
        dialogLoad.setCanceledOnTouchOutside(false);

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString(LOGIN_KEY, loginET.getText().toString());
        savedInstanceState.putString(PASSWORD_KEY, passwordET.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(LOGIN_KEY)) {
            loginET.setText(savedInstanceState.getString(LOGIN_KEY));
        }

        if (savedInstanceState.containsKey(PASSWORD_KEY)) {
            passwordET.setText(savedInstanceState.getString(PASSWORD_KEY));
        }

    }
}