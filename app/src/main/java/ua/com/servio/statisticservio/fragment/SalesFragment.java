package ua.com.servio.statisticservio.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.activity.BasicActivity;
import ua.com.servio.statisticservio.model.json.Band;
import ua.com.servio.statisticservio.model.json.DownloadResponse;
import ua.com.servio.statisticservio.model.json.Field;
import ua.com.servio.statisticservio.model.json.UploadResponse;
import ua.com.servio.statisticservio.service.sync.SyncService;
import ua.com.servio.statisticservio.service.sync.SyncServiceFactory;
import ua.com.servio.statisticservio.utils.ActivityUtils;
import ua.com.servio.statisticservio.utils.NetworkUtils;

import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_SALES;

public class SalesFragment extends Fragment implements FragmentStartSync{
    private static  final int LAYOUT = R.layout.fragment_sales;

    private static final String SALES_FIELDS_KEY = "sales_fields_key";

    private ActivityUtils activityUtils;
    private NetworkUtils networkUtils;
    private ProgressDialog dialogLoad;
    private View view;
    private LinearLayout content;
    private TextView noDataView;
    private List<Field> salesFields;

    private DecimalFormat precision = new DecimalFormat("#.##");
    private LayoutInflater inflater;

    public static SalesFragment getInstance() {

        Bundle args = new Bundle();
        SalesFragment fragment = new SalesFragment();
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

        content = (LinearLayout) view.findViewById(R.id.content);
        content.setVisibility(View.GONE);

        initStartData();
        return view;
    }

    private void initStartData(){

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if (salesFields != null) {
            savedInstanceState.putParcelableArrayList(SALES_FIELDS_KEY, new ArrayList<>(salesFields));
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SALES_FIELDS_KEY)) {
                salesFields = savedInstanceState.<Field>getParcelableArrayList(SALES_FIELDS_KEY);
                initData(salesFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        }
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

        Observable observable = syncService.getSales(
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

                        List <Band> bands = downloadResponse.getBands();
                        for(Band band :bands ) {
                            switch (band.getBandName()) {
                                case BAND_NAME_SALES:
                                    salesFields = band.getFields();
                                    initData(salesFields);
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

    private void initData(List<Field> fields){

//        LinearLayout hallcapContent = (LinearLayout) view.findViewById(R.id.hallcap_content);
//
//        Double billItem = 0.0;
//        Double guestItem = 0.0;
//        Double paymentItem = 0.0;
//        Double sumGuestItem = 0.0;
//        Double sumBillItem = 0.0;
//        Double billTotal = 0.0;
//        Double guestTotal = 0.0;
//        Double paymentTotal = 0.0;
//        Double sumGuestTotal = 0.0;
//        Double sumBillTotal = 0.0;
//
//        String id = null;
//
//        for(Field field : fields) {
//
//            View convertView = inflater.inflate(R.layout.hall_item, null);
//
//            LinearLayout hallCap = (LinearLayout) convertView.findViewById(R.id.hall_cap);
//            TextView hallTitle = (TextView) convertView.findViewById(R.id.hall_title);
//            TextView hallName = (TextView) convertView.findViewById(R.id.hall_name);
//            TextView hallBills = (TextView) convertView.findViewById(R.id.hall_bills);
//            TextView hallGuests = (TextView) convertView.findViewById(R.id.hall_guests);
//            TextView hallPayment = (TextView) convertView.findViewById(R.id.hall_payment);
//            TextView hallSumbill = (TextView) convertView.findViewById(R.id.hall_sumbill);
//            TextView hallSumguest = (TextView) convertView.findViewById(R.id.hall_sumguest);
//
//            LinearLayout sectionTotal = (LinearLayout) convertView.findViewById(R.id.hall_total);
//            TextView hallNameItem = (TextView) convertView.findViewById(R.id.hall_name_item);
//            TextView hallBillsItem = (TextView) convertView.findViewById(R.id.hall_bills_item);
//            TextView hallGuestsItem  = (TextView) convertView.findViewById(R.id.hall_guests_item);
//            TextView hallPaymentItem  = (TextView) convertView.findViewById(R.id.hall_payment_item);
//            TextView hallSumguestItem  = (TextView) convertView.findViewById(R.id.hall_sumguest_item);
//            TextView hallSumbillItem  = (TextView) convertView.findViewById(R.id.hall_sumbill_item);
//
//            Double bill = stringToDouble(field.getBillCount());
//            Double guest = stringToDouble(field.getGuestCount());
//            Double payment = stringToDouble(field.getPayment());
//            Double sumGuest = stringToDouble(field.getSumGuest());
//            Double sumBill = stringToDouble(field.getSumBill());
//
//            billTotal = billTotal + bill;
//            guestTotal = guestTotal + guest;
//            paymentTotal = paymentTotal + payment;
//            sumGuestTotal = sumGuestTotal + sumGuest;
//            sumBillTotal = sumBillTotal + sumBill;
//
//            if (id == null) {
//                hallCap.setVisibility(View.VISIBLE);
//                hallTitle.setText(field.getBaseExternalName());
//            } else {
//                if (!field.getBaseExternalID().equals(id)) {
//                    hallCap.setVisibility(View.VISIBLE);
//                    hallTitle.setText(field.getBaseExternalName());
//                    billItem = 0.0;
//                    guestItem = 0.0;
//                    paymentItem = 0.0;
//                    sumGuestItem = 0.0;
//                    sumBillItem = 0.0;
//                }
//            }
//
//            billItem = billItem + bill;
//            guestItem = guestItem + guest;
//            paymentItem = paymentItem + payment;
//            sumGuestItem = sumGuestItem + sumGuest;
//            sumBillItem = sumBillItem + sumBill;
//
//            hallName.setText(field.getPlaceGroupShort());
//            hallBills.setText(field.getBillCount());
//            hallGuests.setText(field.getGuestCount());
//            hallPayment.setText(field.getPayment());
//            hallSumbill.setText(field.getSumGuest());
//            hallSumguest.setText(field.getSumBill());
//
//
//            Field nextField;
//            try {
//                nextField = fields.get(fields.indexOf(field)+1);
//            } catch (Exception e) {
//                nextField = null;
//            }
//
//            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {
//
//                sectionTotal.setVisibility(View.VISIBLE);
//                hallNameItem.setText(getActivity().getString(R.string.by)+" " + field.getBaseExternalName());
//                hallBillsItem.setText(
//                        precision.format(billItem).replace(".",","));
//                hallGuestsItem.setText(
//                        precision.format(guestItem).replace(".",","));
//                hallPaymentItem.setText(
//                        precision.format(paymentItem).replace(".",","));
//                hallSumguestItem.setText(
//                        precision.format(sumGuestItem).replace(".",","));
//                hallSumbillItem.setText(
//                        precision.format(sumBillItem).replace(".",","));
//            }
//            id = field.getBaseExternalID();
//            hallcapContent.addView(convertView);
//        }
//
//        TextView hallcapBillsTotal = (TextView) view.findViewById(R.id.hallcap_bills_total);
//        hallcapBillsTotal.setText(precision.format(billTotal).replace(".",","));
//        TextView hallcapGuestsTotal = (TextView) view.findViewById(R.id.hallcap_guests_total);
//        hallcapGuestsTotal.setText(precision.format(guestTotal).replace(".",","));
//        TextView hallcapPaymentTotal = (TextView) view.findViewById(R.id.hallcap_payment_total);
//        hallcapPaymentTotal.setText(precision.format(paymentTotal).replace(".",","));
//        TextView hallcapSumguestTotal = (TextView) view.findViewById(R.id.hallcap_sumguest_total);
//        hallcapSumguestTotal.setText(precision.format(sumGuestTotal).replace(".",","));
//        TextView hallcapSumbillTotal = (TextView) view.findViewById(R.id.hallcap_sumbill_total);
//        hallcapSumbillTotal.setText(precision.format(sumBillTotal).replace(".",","));


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
