package ua.com.servio.statisticservio.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.activity.BasicActivity;
import ua.com.servio.statisticservio.adapter.GroupDiscountAdapter;
import ua.com.servio.statisticservio.adapter.ManualDiscountAdapter;
import ua.com.servio.statisticservio.model.json.Band;
import ua.com.servio.statisticservio.model.json.DownloadResponse;
import ua.com.servio.statisticservio.model.json.Field;
import ua.com.servio.statisticservio.model.json.UploadResponse;
import ua.com.servio.statisticservio.service.sync.SyncService;
import ua.com.servio.statisticservio.service.sync.SyncServiceFactory;
import ua.com.servio.statisticservio.utils.ActivityUtils;
import ua.com.servio.statisticservio.utils.NetworkUtils;

import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_GROUP_DISCOUNT;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_MANUAL_DISCOUNT;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_SUMMARY;

public class SummaryStaticFragment extends Fragment implements FragmentStartSync{
    private static  final int LAYOUT = R.layout.fragment_summary_static;


    private ActivityUtils activityUtils;
    private NetworkUtils networkUtils;
    private ProgressDialog dialogLoad;
    private View view;
    private ScrollView content;
    private TextView noDataView;
    private TextView guestCountCap, billCountCap, billOpenedCountCap, billOpenedTotalCap,
            sumQuestCap, billTotalReturnCap, billCountReturnCap, averageSumGuestCap,
            averageSumBillCap, averageQuestCountCap, decreaseCountCap, decreaseSumCap,
            tax1Cap, tax2Cap;

    private DecimalFormat precision = new DecimalFormat("#.##");

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

        noDataView = (TextView) view.findViewById(R.id.no_data);
        noDataView.setVisibility(View.VISIBLE);

        content = (ScrollView) view.findViewById(R.id.content);
        content.setVisibility(View.GONE);

        guestCountCap = (TextView) view.findViewById(R.id.guestcount_cap);
        billCountCap = (TextView) view.findViewById(R.id.billcount_cap);
        billOpenedCountCap = (TextView) view.findViewById(R.id.billopenedcount_cap);
        billOpenedTotalCap = (TextView) view.findViewById(R.id.billopenedtotal_cap);
        sumQuestCap = (TextView) view.findViewById(R.id.sumguest_cap);
        billTotalReturnCap = (TextView) view.findViewById(R.id.billtotalreturn_cap);
        billCountReturnCap = (TextView) view.findViewById(R.id.billcountreturn_cap);
        averageSumGuestCap = (TextView) view.findViewById(R.id.average_sumguest_cap);
        averageSumBillCap = (TextView) view.findViewById(R.id.average_sumbill_cap);
        averageQuestCountCap = (TextView) view.findViewById(R.id.average_guestcount_cap);
        decreaseCountCap = (TextView) view.findViewById(R.id.decreasecount_cap);
        decreaseSumCap = (TextView) view.findViewById(R.id.decreasesum_cap);
        tax1Cap = (TextView) view.findViewById(R.id.tax1_cap);
        tax2Cap = (TextView) view.findViewById(R.id.tax2_cap);

        initStartData();
        return view;
    }

    private void initStartData(){

        RelativeLayout groupDiscountCap = (RelativeLayout) view.findViewById(R.id.groupdiscountcap);
        final ImageView groupDiscountCapImage = (ImageView) view.findViewById(R.id.groupdiscountcap_image);
        final LinearLayout groupDiscountCapContainer = (LinearLayout) view.findViewById(R.id.groupdiscountcap_container);

        groupDiscountCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupDiscountCapContainer.getVisibility()==View.VISIBLE){
                    groupDiscountCapContainer.setVisibility(View.GONE);
                    groupDiscountCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    groupDiscountCapContainer.setVisibility(View.VISIBLE);
                    groupDiscountCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });

        RelativeLayout manualDiscountCap = (RelativeLayout) view.findViewById(R.id.manualdiscountcap);
        final ImageView manualDiscountCapImage = (ImageView) view.findViewById(R.id.manualdiscountcapp_image);
        final LinearLayout manualDiscountCapContainer = (LinearLayout) view.findViewById(R.id.manualdiscountcap_container);

        manualDiscountCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manualDiscountCapContainer.getVisibility()==View.VISIBLE){
                    manualDiscountCapContainer.setVisibility(View.GONE);
                    manualDiscountCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    manualDiscountCapContainer.setVisibility(View.VISIBLE);
                    manualDiscountCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });
    }


    @Override
    public void startSync() {

        if (!networkUtils.checkEthernet()) {
            activityUtils.showMessage(getString(R.string.error_internet_connecting), getActivity());
            return;
        }

        dialogLoad.show();

        SyncService syncService = SyncServiceFactory.createService(
                SyncService.class, getActivity());

        Observable observable = syncService.getSummaryStatic(
                UploadResponse.builder()
                        .dateIn(((BasicActivity)getActivity()).getDateStart())
                        .dateOut(((BasicActivity)getActivity()).getDateEnd())
                        .build());

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DownloadResponse downloadResponse) {
                        if (downloadResponse == null) {
                            dialogLoad.cancel();
                            ((BasicActivity)getActivity()).showFilter();
                            activityUtils.showMessage(getString(R.string.error_no_data), getActivity());
                            noDataView.setVisibility(View.VISIBLE);
                            return;
                        }
                        if (!TextUtils.isEmpty(downloadResponse.getError())) {
                            dialogLoad.cancel();
                            ((BasicActivity)getActivity()).showFilter();
                             activityUtils.showMessage(downloadResponse.getError(), getActivity());
                            noDataView.setVisibility(View.VISIBLE);
                            return;
                        }
                        noDataView.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);

                        List<Band> bands = downloadResponse.getBands();
                        for(Band band :bands ) {
                            switch (band.getBandName()) {
                                case BAND_NAME_SUMMARY:
                                    initDataToCap(band.getFields());
                                    break;
                                case BAND_NAME_GROUP_DISCOUNT:
                                    initGroupDiscount(band.getFields());
                                    break;
                                case BAND_NAME_MANUAL_DISCOUNT:
                                    iniManualDiscount(band.getFields());
                                    break;
                            }
                        }

                        dialogLoad.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogLoad.cancel();
                        noDataView.setVisibility(View.VISIBLE);

                        ((BasicActivity)getActivity()).showFilter();

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

    private void initDataToCap(List<Field> fields){
        Double guestCount = 0.0;
        Double billCount = 0.0;
        Double billOpenedCount = 0.0;
        Double billOpenedTotal = 0.0;
        Double sumQuest = 0.0;
        Double billTotalReturn = 0.0;
        Double billCountReturn = 0.0;
        Double decreaseCount = 0.0;
        Double decreaseSum = 0.0;
        Double tax1 = 0.0;
        Double tax2 = 0.0;
        int i=0;
        for(Field field : fields){
            guestCount = guestCount + stringToDouble(field.getGuestCount());
            billCount = billCount + stringToDouble(field.getBillCount());
            billOpenedCount = billOpenedCount + stringToDouble(field.getBillOpenedCount());
            billOpenedTotal = billOpenedTotal + stringToDouble(field.getBillOpenedTotal());
            sumQuest = sumQuest + stringToDouble(field.getBillBaseTotal());
            billTotalReturn = billTotalReturn + stringToDouble(field.getBillTotalReturn());
            billCountReturn = billCountReturn + stringToDouble(field.getBillCountReturn());
            decreaseCount = decreaseCount + stringToDouble(field.getDecreaseCount());
            decreaseSum = decreaseSum + stringToDouble(field.getDecreaseSum());
            tax1 = tax1 + stringToDouble(field.getTax1());
            tax2 = tax2 + stringToDouble(field.getTax2());
            i++;
        }

        Double averageSumGuest = (guestCount == 0.0  ? 0.0 : sumQuest / guestCount);
        Double averageSumBill = (billCount == 0.0  ? 0.0 : sumQuest / billCount);
        Double averageQuestCount = guestCount / i;

        guestCountCap.setText(String.valueOf(guestCount.intValue()));
        billCountCap.setText(String.valueOf(billCount.intValue()));
        billOpenedCountCap.setText(String.valueOf(billOpenedCount.intValue()));
        billOpenedTotalCap.setText(precision.format(billOpenedTotal));
        sumQuestCap.setText(precision.format(sumQuest));
        billTotalReturnCap.setText(precision.format(billTotalReturn));
        billCountReturnCap.setText(precision.format(billCountReturn.intValue()));
        averageSumGuestCap.setText(precision.format(averageSumGuest));
        averageSumBillCap.setText(precision.format(averageSumBill));
        averageQuestCountCap.setText(String.valueOf(averageQuestCount.intValue()));
        decreaseCountCap.setText(precision.format(decreaseCount));
        decreaseSumCap.setText(precision.format(decreaseSum));
        tax1Cap.setText(precision.format(tax1));
        tax2Cap.setText(precision.format(tax2));
    }

    private void initGroupDiscount(List<Field> fields){
        Double billCount = 0.0;
        Double discountSum = 0.0;
        Double bonusSum = 0.0;
        for(Field field : fields){
            billCount = billCount + stringToDouble(field.getBillCount());
            discountSum = discountSum + stringToDouble(field.getDiscountSum());
            bonusSum = bonusSum + stringToDouble(field.getBonusSum());
        }


        GroupDiscountAdapter adapter = new GroupDiscountAdapter(getActivity(), fields);
        ListView groupDiscountCapContent = (ListView) view.findViewById(R.id.groupdiscountcap_content);
        groupDiscountCapContent.setAdapter(adapter);

        TextView discTotalBills = (TextView) view.findViewById(R.id.groupdiscount_total_bills);
        discTotalBills.setText(precision.format(billCount));
        TextView discTotal = (TextView) view.findViewById(R.id.groupdiscount_total_discont);
        discTotal.setText(precision.format(discountSum));
        TextView discTotalPaid = (TextView) view.findViewById(R.id.groupdiscount_total_paid);
        discTotalPaid.setText(precision.format(bonusSum));

    }

    private void iniManualDiscount(List<Field> fields){
        Double billCount = 0.0;
        Double bonusSum = 0.0;
        for(Field field : fields){
            billCount = billCount + stringToDouble(field.getBillCount());
            bonusSum = bonusSum + stringToDouble(field.getDiscountSum());
        }


        ManualDiscountAdapter adapter = new ManualDiscountAdapter(getActivity(), fields);
        ListView manualDiscountCapContent = (ListView) view.findViewById(R.id.manualdiscountcap_content);
        manualDiscountCapContent.setAdapter(adapter);

        TextView discTotalBills = (TextView) view.findViewById(R.id.manualdiscount_total_bills);
        discTotalBills.setText(precision.format(billCount));
        TextView discTotalSum = (TextView) view.findViewById(R.id.manualdiscount_total_sum);
        discTotalSum.setText(precision.format(bonusSum));

    }



    private Double stringToDouble(String number){
        if(TextUtils.isEmpty(number)) return 0.0;
        try{
            return Double.parseDouble(number.replace(",","."));
        }catch (NumberFormatException e){
            return 0.0;
        }
    }

}
