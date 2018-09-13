package ua.com.servio.statisticservio.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.activity.BasicActivity;
import ua.com.servio.statisticservio.model.json.DownloadResponse;
import ua.com.servio.statisticservio.model.json.UploadResponse;
import ua.com.servio.statisticservio.service.sync.SyncService;
import ua.com.servio.statisticservio.service.sync.SyncServiceFactory;
import ua.com.servio.statisticservio.utils.ActivityUtils;
import ua.com.servio.statisticservio.utils.NetworkUtils;

public class SummaryStaticFragment extends Fragment {
    private static  final int LAYOUT = R.layout.fragment_summary_static;


    private ActivityUtils activityUtils;
    private NetworkUtils networkUtils;
    private ProgressDialog dialogLoad;
    private View view;

    public static SummaryStaticFragment getInstance() {

        Bundle args = new Bundle();
        SummaryStaticFragment fragment = new SummaryStaticFragment();
        fragment.setArguments(args);
        return  fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        activityUtils = ((BasicActivity) getActivity()).getActivityUtils();
        networkUtils = ((BasicActivity) getActivity()).getNetworkUtils();
        dialogLoad = ((BasicActivity) getActivity()).getDialogLoad();

        return view;
    }


    private void startSync(){

        if (!networkUtils.checkEthernet()) {
            activityUtils.showMessage(getString(R.string.error_internet_connecting), getActivity());
            return;
        }

        dialogLoad.show();

        SyncService syncService = SyncServiceFactory.createService(
                SyncService.class, getActivity());

        Observable observable = syncService.getSummaryStatic(
                UploadResponse.builder().build());
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadResponse>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(DownloadResponse downloadResponse) {
                        dialogLoad.cancel();
                        if(downloadResponse == null) {
                            activityUtils.showMessage(getString(R.string.error_no_data), getActivity());
                            return;
                        }
                        if(downloadResponse.getBands().size()==0) {
                           // activityUtils.showMessage(authResult.getError(), BootAct.this);
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogLoad.cancel();
                        if (!networkUtils.checkEthernet()) {
                            activityUtils.showMessage(getString(R.string.error_internet_connecting), getActivity());
                            return;
                        }
                        activityUtils.showMessage(e.getMessage(), getActivity());
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }

}
