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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_GROUP_DISCOUNT;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_HALL;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_MANUAL_DISCOUNT;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_PAYMENT;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_SECTION;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_SUMMARY;
import static ua.com.servio.statisticservio.common.Consts.BAND_NAME_USER;

public class SummaryStaticFragment extends Fragment implements FragmentStartSync{
    private static  final int LAYOUT = R.layout.fragment_summary_static;

    private static final String SUMMARY_STATIC_FIELDS_KEY = "summary_static_fields_key";
    private static final String GROUP_DISCOUNT_FIELDS_KEY = "group_discount_fields_key";
    private static final String MANUAL_DISCOUNT_FIELDS_KEY = "manual_discount_fields_key";
    private static final String PAYMENT_FIELDS_KEY = "payment_fields_key";
    private static final String SECTION_FIELDS_KEY = "section_fields_key";
    private static final String HALL_FIELDS_KEY = "hall_fields_key";
    private static final String USER_FIELDS_KEY = "user_fields_key";

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
    private List<Field> summaryStaticFields, groupDiscountFields, manualDiscountFields,
            paymentDiscountFields, sectionDiscountFields, hallDiscountFields, userDiscountFields;

    private DecimalFormat precision = new DecimalFormat("#.##");
    private LayoutInflater inflater;

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

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        RelativeLayout paymentCap = (RelativeLayout) view.findViewById(R.id.paymentcap);
        final ImageView paymentCapImage = (ImageView) view.findViewById(R.id.paymentcap_image);
        final LinearLayout paymentCapContainer = (LinearLayout) view.findViewById(R.id.payment_container);

        paymentCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paymentCapContainer.getVisibility()==View.VISIBLE){
                    paymentCapContainer.setVisibility(View.GONE);
                    paymentCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    paymentCapContainer.setVisibility(View.VISIBLE);
                    paymentCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });

        RelativeLayout sectionCap = (RelativeLayout) view.findViewById(R.id.sectioncap);
        final ImageView sectionCapImage = (ImageView) view.findViewById(R.id.sectioncap_image);
        final LinearLayout sectionCapContainer = (LinearLayout) view.findViewById(R.id.sectioncap_container);

        sectionCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sectionCapContainer.getVisibility()==View.VISIBLE){
                    sectionCapContainer.setVisibility(View.GONE);
                    sectionCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    sectionCapContainer.setVisibility(View.VISIBLE);
                    sectionCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });

        RelativeLayout hallCap = (RelativeLayout) view.findViewById(R.id.hallcap);
        final ImageView hallCapImage = (ImageView) view.findViewById(R.id.hallcap_image);
        final LinearLayout hallCapContainer = (LinearLayout) view.findViewById(R.id.hallcap_container);

        hallCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hallCapContainer.getVisibility()==View.VISIBLE){
                    hallCapContainer.setVisibility(View.GONE);
                    hallCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    hallCapContainer.setVisibility(View.VISIBLE);
                    hallCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });

        RelativeLayout userCap = (RelativeLayout) view.findViewById(R.id.usercap);
        final ImageView userCapImage = (ImageView) view.findViewById(R.id.usercap_image);
        final LinearLayout userCapContainer = (LinearLayout) view.findViewById(R.id.usercap_container);

        userCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userCapContainer.getVisibility()==View.VISIBLE){
                    userCapContainer.setVisibility(View.GONE);
                    userCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    userCapContainer.setVisibility(View.VISIBLE);
                    userCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if (summaryStaticFields != null) {
            savedInstanceState.putParcelableArrayList(SUMMARY_STATIC_FIELDS_KEY, new ArrayList<>(summaryStaticFields));
        }
        if (groupDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(GROUP_DISCOUNT_FIELDS_KEY, new ArrayList<>(groupDiscountFields));
        }
        if (manualDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(MANUAL_DISCOUNT_FIELDS_KEY, new ArrayList<>(manualDiscountFields));
        }
        if (paymentDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(PAYMENT_FIELDS_KEY, new ArrayList<>(paymentDiscountFields));
        }
        if (sectionDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(SECTION_FIELDS_KEY, new ArrayList<>(sectionDiscountFields));
        }
        if (hallDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(HALL_FIELDS_KEY, new ArrayList<>(hallDiscountFields));
        }
        if (userDiscountFields != null) {
            savedInstanceState.putParcelableArrayList(USER_FIELDS_KEY, new ArrayList<>(userDiscountFields));
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SUMMARY_STATIC_FIELDS_KEY)) {
                summaryStaticFields = savedInstanceState.<Field>getParcelableArrayList(SUMMARY_STATIC_FIELDS_KEY);
                initDataToCap(summaryStaticFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(GROUP_DISCOUNT_FIELDS_KEY)) {
                groupDiscountFields = savedInstanceState.<Field>getParcelableArrayList(GROUP_DISCOUNT_FIELDS_KEY);
                initGroupDiscount(groupDiscountFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(MANUAL_DISCOUNT_FIELDS_KEY)) {
                manualDiscountFields = savedInstanceState.<Field>getParcelableArrayList(MANUAL_DISCOUNT_FIELDS_KEY);
                initManualDiscount(manualDiscountFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(PAYMENT_FIELDS_KEY)) {
                paymentDiscountFields = savedInstanceState.<Field>getParcelableArrayList(PAYMENT_FIELDS_KEY);
                initPayment(paymentDiscountFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(SECTION_FIELDS_KEY)) {
                sectionDiscountFields = savedInstanceState.<Field>getParcelableArrayList(SECTION_FIELDS_KEY);
                initSection(sectionDiscountFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(HALL_FIELDS_KEY)) {
                hallDiscountFields = savedInstanceState.<Field>getParcelableArrayList(HALL_FIELDS_KEY);
                initHall(hallDiscountFields);
                noDataView.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }

            if (savedInstanceState.containsKey(USER_FIELDS_KEY)) {
                userDiscountFields = savedInstanceState.<Field>getParcelableArrayList(USER_FIELDS_KEY);
                initUser(userDiscountFields);
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

                        List <Band> bands = downloadResponse.getBands();
                        for(Band band :bands ) {
                            switch (band.getBandName()) {
                                case BAND_NAME_SUMMARY:
                                    summaryStaticFields = band.getFields();
                                    initDataToCap(summaryStaticFields);
                                    break;
                                case BAND_NAME_GROUP_DISCOUNT:
                                    groupDiscountFields = band.getFields();
                                    initGroupDiscount(groupDiscountFields);
                                    break;
                                case BAND_NAME_MANUAL_DISCOUNT:
                                    manualDiscountFields = band.getFields();
                                    initManualDiscount(manualDiscountFields);
                                    break;
                                case BAND_NAME_PAYMENT:
                                    paymentDiscountFields = band.getFields();
                                    initPayment(paymentDiscountFields);
                                    break;
                                case BAND_NAME_SECTION:
                                    sectionDiscountFields = band.getFields();
                                    initSection(sectionDiscountFields);
                                    break;
                                case BAND_NAME_HALL:
                                    hallDiscountFields = band.getFields();
                                    initHall(hallDiscountFields);
                                    break;
                                case BAND_NAME_USER:
                                    userDiscountFields = band.getFields();
                                    initUser(userDiscountFields);
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
        billOpenedTotalCap.setText(precision.format(billOpenedTotal).replace(".",","));
        sumQuestCap.setText(precision.format(sumQuest).replace(".",","));
        billTotalReturnCap.setText(precision.format(billTotalReturn).replace(".",","));
        billCountReturnCap.setText(precision.format(billCountReturn.intValue()));
        averageSumGuestCap.setText(precision.format(averageSumGuest).replace(".",","));
        averageSumBillCap.setText(precision.format(averageSumBill).replace(".",","));
        averageQuestCountCap.setText(String.valueOf(averageQuestCount.intValue()));
        decreaseCountCap.setText(precision.format(decreaseCount).replace(".",","));
        decreaseSumCap.setText(precision.format(decreaseSum).replace(".",","));
        tax1Cap.setText(precision.format(tax1).replace(".",","));
        tax2Cap.setText(precision.format(tax2).replace(".",","));
    }

    private void initGroupDiscount(List<Field> fields){

        LinearLayout groupdiscountcapContent = (LinearLayout) view.findViewById(R.id.groupdiscountcap_content);

        Double billCount = 0.0;
        Double discountSum = 0.0;
        Double bonusSum = 0.0;
        for(Field field : fields){

            View convertView = inflater.inflate(R.layout.group_discount_item, null);

            TextView groupDiscountName = (TextView) convertView.findViewById(R.id.groupdiscount_name);
            groupDiscountName.setText(field.getDiscountGroupName());
            TextView groupDiscountBills = (TextView) convertView.findViewById(R.id.groupdiscount_bills);
            groupDiscountBills.setText(field.getBillCount());
            TextView groupDiscountDiscont = (TextView) convertView.findViewById(R.id.groupdiscount_discont);
            groupDiscountDiscont.setText(field.getDiscountSum());
            TextView groupDiscountPaid = (TextView) convertView.findViewById(R.id.groupdiscount_paid);
            groupDiscountPaid.setText(field.getBonusSum());

            billCount = billCount + stringToDouble(field.getBillCount());
            discountSum = discountSum + stringToDouble(field.getDiscountSum());
            bonusSum = bonusSum + stringToDouble(field.getBonusSum());

            groupdiscountcapContent.addView(convertView);
        }

        TextView discTotalBills = (TextView) view.findViewById(R.id.groupdiscount_total_bills);
        discTotalBills.setText(precision.format(billCount).replace(".",","));
        TextView discTotal = (TextView) view.findViewById(R.id.groupdiscount_total_discont);
        discTotal.setText(precision.format(discountSum).replace(".",","));
        TextView discTotalPaid = (TextView) view.findViewById(R.id.groupdiscount_total_paid);
        discTotalPaid.setText(precision.format(bonusSum).replace(".",","));

    }

    private void initManualDiscount(List<Field> fields){

        LinearLayout manualdiscountcapContent = (LinearLayout) view.findViewById(R.id.manualdiscountcap_content);

        Double billCount = 0.0;
        Double bonusSum = 0.0;
        for(Field field : fields){

            View convertView = inflater.inflate(R.layout.manual_discount_item, null);

            TextView manualDiscountName = (TextView) convertView.findViewById(R.id.manualdiscount_name);
            manualDiscountName.setText(field.getDiscountPercent());
            TextView manualDiscountBills = (TextView) convertView.findViewById(R.id.manualdiscount_bills);
            manualDiscountBills.setText(field.getBillCount());
            TextView manualDiscountDiscont = (TextView) convertView.findViewById(R.id.manualdiscount_sum);
            manualDiscountDiscont.setText(field.getDiscountSum());

            billCount = billCount + stringToDouble(field.getBillCount());
            bonusSum = bonusSum + stringToDouble(field.getDiscountSum());

            manualdiscountcapContent.addView(convertView);
        }

        TextView discTotalBills = (TextView) view.findViewById(R.id.manualdiscount_total_bills);
        discTotalBills.setText(precision.format(billCount).replace(".",","));
        TextView discTotalSum = (TextView) view.findViewById(R.id.manualdiscount_total_sum);
        discTotalSum.setText(precision.format(bonusSum).replace(".",","));

    }

    private void initPayment(List<Field> fields){

        LinearLayout paymencapContent = (LinearLayout) view.findViewById(R.id.paymentcap_content);

        Double accrualItem = 0.0;
        Double discontItem = 0.0;
        Double paymentItem = 0.0;
        Double accrualFiscal = 0.0;
        Double discontFiscal = 0.0;
        Double paymentFiscal = 0.0;
        Double accrualTotal = 0.0;
        Double discontTotal = 0.0;
        Double paymentTotal = 0.0;

        String id = null;

        for(Field field : fields){

            View convertView = inflater.inflate(R.layout.payment_item, null);

            LinearLayout paymentCap = (LinearLayout) convertView.findViewById(R.id.payment_cap);
            TextView paymentTitle = (TextView) convertView.findViewById(R.id.payment_title);
            TextView paymentTypeName = (TextView) convertView.findViewById(R.id.paymenttype_name);
            TextView paymentTypeFiscal = (TextView) convertView.findViewById(R.id.paymenttype_fiscal);
            TextView paymentTypeAccrual = (TextView) convertView.findViewById(R.id.paymenttype_accrual);
            TextView paymentTypeDiscont = (TextView) convertView.findViewById(R.id.paymenttype_discont);
            TextView paymentTypePayment = (TextView) convertView.findViewById(R.id.paymenttype_payment);

            LinearLayout paymentTypeItem = (LinearLayout) convertView.findViewById(R.id.payment_total);
            TextView paymentTotalTitle = (TextView) convertView.findViewById(R.id.payment_total_titla);
            TextView paymentTypeAccrualTotal = (TextView) convertView.findViewById(R.id.paymenttype_accrual_total);
            TextView paymentTypeDiscontTotal  = (TextView) convertView.findViewById(R.id.paymenttype_discont_total);
            TextView paymentTypePaymentTotal  = (TextView) convertView.findViewById(R.id.paymenttype_payment_total);

            LinearLayout paymentTypeFiscalLine = (LinearLayout) convertView.findViewById(R.id.payment_fiscal_line);
            TextView paymentFiscalTitle = (TextView) convertView.findViewById(R.id.payment_fiscal_titla);
            TextView paymentTypeAccrualFiscal = (TextView) convertView.findViewById(R.id.paymenttype_accrual_fiscal);
            TextView paymentTypeDiscontFiscal  = (TextView) convertView.findViewById(R.id.paymenttype_discont_fiscal);
            TextView paymentTypePaymentFiscal  = (TextView) convertView.findViewById(R.id.paymenttype_payment_fiscal);

            Double accrual = stringToDouble(field.getAccrual());
            Double discont = stringToDouble(field.getDiscount());
            Double payment = stringToDouble(field.getPayment());

            accrualTotal = accrualTotal + accrual;
            discontTotal = discontTotal + discont;
            paymentTotal = paymentTotal + payment;

            if (id == null) {
                paymentCap.setVisibility(View.VISIBLE);
                paymentTitle.setText(field.getBaseExternalName());
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    paymentCap.setVisibility(View.VISIBLE);
                    paymentTitle.setText(field.getBaseExternalName());
                    accrualItem = 0.0;
                    discontItem = 0.0;
                    paymentItem = 0.0;
                    accrualFiscal = 0.0;
                    discontFiscal = 0.0;
                    paymentFiscal = 0.0;
                }
            }

            accrualItem = accrualItem + accrual;
            discontItem = discontItem + discont;
            paymentItem = paymentItem + payment;

            accrualFiscal = accrualFiscal + accrual;
            discontFiscal = discontFiscal + discont;
            paymentFiscal = paymentFiscal + payment;

            paymentTypeName.setText(field.getPaymentTypeName());
            paymentTypeFiscal.setText(field.getPaymentFiscalTypeName());
            paymentTypeAccrual.setText(field.getAccrual());
            paymentTypeDiscont.setText(field.getDiscount());
            paymentTypePayment.setText(field.getPayment());

            Field nextField;
            try {
                nextField = fields.get(fields.indexOf(field)+1);
            } catch (Exception e) {
                nextField = null;
            }

            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {

                paymentTypeFiscalLine.setVisibility(View.VISIBLE);
                paymentFiscalTitle.setText(
                        getActivity().getString(R.string.by)+" " + field.getPaymentFiscalTypeName());
                paymentTypeAccrualFiscal.setText(
                        precision.format(accrualFiscal).replace(".",","));
                paymentTypeDiscontFiscal.setText(
                        precision.format(discontFiscal).replace(".",","));
                paymentTypePaymentFiscal.setText(
                        precision.format(paymentFiscal).replace(".",","));

                paymentTypeItem.setVisibility(View.VISIBLE);
                paymentTotalTitle.setText(getActivity().getString(R.string.by)+" " + field.getBaseExternalName());
                paymentTypeAccrualTotal.setText(
                        precision.format(accrualItem).replace(".",","));
                paymentTypeDiscontTotal.setText(
                        precision.format(discontItem).replace(".",","));
                paymentTypePaymentTotal.setText(
                        precision.format(paymentItem).replace(".",","));

            }else if(nextField != null &&
                    !field.getPaymentFiscalTypeName().equals(nextField.getPaymentFiscalTypeName())){
                paymentTypeFiscalLine.setVisibility(View.VISIBLE);
                paymentFiscalTitle.setText(
                        getActivity().getString(R.string.by)+" " + field.getPaymentFiscalTypeName());
                paymentTypeAccrualFiscal.setText(
                        precision.format(accrualFiscal).replace(".",","));
                paymentTypeDiscontFiscal.setText(
                        precision.format(discontFiscal).replace(".",","));
                paymentTypePaymentFiscal.setText(
                        precision.format(paymentFiscal).replace(".",","));
                accrualFiscal = 0.0;
                discontFiscal = 0.0;
                paymentFiscal = 0.0;
            }
            id = field.getBaseExternalID();
            paymencapContent.addView(convertView);
        }

        TextView paymentTotalAccrual = (TextView) view.findViewById(R.id.payment_total_accrual);
        paymentTotalAccrual.setText(precision.format(accrualTotal).replace(".",","));
        TextView paymentTotalDiscont = (TextView) view.findViewById(R.id.payment_total_discont);
        paymentTotalDiscont.setText(precision.format(discontTotal).replace(".",","));
        TextView paymentTotalPayment = (TextView) view.findViewById(R.id.payment_total_payment);
        paymentTotalPayment.setText(precision.format(paymentTotal).replace(".",","));


    }

    private void initSection(List<Field> fields){

        LinearLayout sectioncapContent = (LinearLayout) view.findViewById(R.id.sectioncap_content);

        Double amountItem = 0.0;
        Double accrualItem = 0.0;
        Double discontItem = 0.0;
        Double paymentItem = 0.0;
        Double amountTotal = 0.0;
        Double accrualTotal = 0.0;
        Double discontTotal = 0.0;
        Double paymentTotal = 0.0;

        String id = null;

        for(Field field : fields){

            View convertView = inflater.inflate(R.layout.section_item, null);

            LinearLayout sectionCap = (LinearLayout) convertView.findViewById(R.id.section_cap);
            TextView sectionTitle = (TextView) convertView.findViewById(R.id.section_title);
            TextView sectionName = (TextView) convertView.findViewById(R.id.section_name);
            TextView sectionAmount = (TextView) convertView.findViewById(R.id.section_amount);
            TextView sectionAccrual = (TextView) convertView.findViewById(R.id.section_accrual);
            TextView sectionDiscont = (TextView) convertView.findViewById(R.id.section_discont);
            TextView sectionPayment = (TextView) convertView.findViewById(R.id.section_payment);

            LinearLayout sectionTotal = (LinearLayout) convertView.findViewById(R.id.section_total);
            TextView sectionNameItem = (TextView) convertView.findViewById(R.id.section_name_item);
            TextView sectionAmountItem = (TextView) convertView.findViewById(R.id.section_amount_item);
            TextView sectionAccrualItem  = (TextView) convertView.findViewById(R.id.section_accrual_item);
            TextView sectionDiscontItem  = (TextView) convertView.findViewById(R.id.section_discont_item);
            TextView sectionPaymentItem  = (TextView) convertView.findViewById(R.id.section_payment_item);

            Double amount = stringToDouble(field.getAmount());
            Double accrual = stringToDouble(field.getAccrual());
            Double discont = stringToDouble(field.getDiscount());
            Double payment = stringToDouble(field.getPayment());

            amountTotal = amountTotal + amount;
            accrualTotal = accrualTotal + accrual;
            discontTotal = discontTotal + discont;
            paymentTotal = paymentTotal + payment;

            if (id == null) {
                sectionCap.setVisibility(View.VISIBLE);
                sectionTitle.setText(field.getBaseExternalName());
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    sectionCap.setVisibility(View.VISIBLE);
                    sectionTitle.setText(field.getBaseExternalName());
                    amountItem = 0.0;
                    accrualItem = 0.0;
                    discontItem = 0.0;
                    paymentItem = 0.0;
                }
            }

            amountItem = amountItem + amount;
            accrualItem = accrualItem + accrual;
            discontItem = discontItem + discont;
            paymentItem = paymentItem + payment;

            sectionName.setText(field.getSectionName());
            sectionAmount.setText(field.getAmount());
            sectionAccrual.setText(field.getAccrual());
            sectionDiscont.setText(field.getDiscount());
            sectionPayment.setText(field.getPayment());

            Field nextField;
            try {
                nextField = fields.get(fields.indexOf(field)+1);
            } catch (Exception e) {
                nextField = null;
            }

            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {

                sectionTotal.setVisibility(View.VISIBLE);
                sectionNameItem.setText(getActivity().getString(R.string.by)+" " + field.getBaseExternalName());
                sectionAmountItem.setText(
                        precision.format(amountItem).replace(".",","));
                sectionAccrualItem.setText(
                        precision.format(accrualItem).replace(".",","));
                sectionDiscontItem.setText(
                        precision.format(discontItem).replace(".",","));
                sectionPaymentItem.setText(
                        precision.format(paymentItem).replace(".",","));
            }
            id = field.getBaseExternalID();
            sectioncapContent.addView(convertView);
        }

        TextView sectioncapTotalAmount = (TextView) view.findViewById(R.id.sectioncap_total_amount);
        sectioncapTotalAmount.setText(precision.format(amountTotal).replace(".",","));
        TextView sectioncapTotalAccrual = (TextView) view.findViewById(R.id.sectioncap_total_accrual);
        sectioncapTotalAccrual.setText(precision.format(accrualTotal).replace(".",","));
        TextView sectioncapTotalDiscont = (TextView) view.findViewById(R.id.sectioncap_total_discont);
        sectioncapTotalDiscont.setText(precision.format(discontTotal).replace(".",","));
        TextView sectioncapTotalPayment = (TextView) view.findViewById(R.id.sectioncap_total_payment);
        sectioncapTotalPayment.setText(precision.format(paymentTotal).replace(".",","));
    }

    private void initHall(List<Field> fields){

        LinearLayout hallcapContent = (LinearLayout) view.findViewById(R.id.hallcap_content);

        Double billItem = 0.0;
        Double guestItem = 0.0;
        Double paymentItem = 0.0;
        Double sumGuestItem = 0.0;
        Double sumBillItem = 0.0;
        Double billTotal = 0.0;
        Double guestTotal = 0.0;
        Double paymentTotal = 0.0;
        Double sumGuestTotal = 0.0;
        Double sumBillTotal = 0.0;

        String id = null;

        for(Field field : fields) {

            View convertView = inflater.inflate(R.layout.hall_item, null);

            LinearLayout hallCap = (LinearLayout) convertView.findViewById(R.id.hall_cap);
            TextView hallTitle = (TextView) convertView.findViewById(R.id.hall_title);
            TextView hallName = (TextView) convertView.findViewById(R.id.hall_name);
            TextView hallBills = (TextView) convertView.findViewById(R.id.hall_bills);
            TextView hallGuests = (TextView) convertView.findViewById(R.id.hall_guests);
            TextView hallPayment = (TextView) convertView.findViewById(R.id.hall_payment);
            TextView hallSumbill = (TextView) convertView.findViewById(R.id.hall_sumbill);
            TextView hallSumguest = (TextView) convertView.findViewById(R.id.hall_sumguest);

            LinearLayout sectionTotal = (LinearLayout) convertView.findViewById(R.id.hall_total);
            TextView hallNameItem = (TextView) convertView.findViewById(R.id.hall_name_item);
            TextView hallBillsItem = (TextView) convertView.findViewById(R.id.hall_bills_item);
            TextView hallGuestsItem  = (TextView) convertView.findViewById(R.id.hall_guests_item);
            TextView hallPaymentItem  = (TextView) convertView.findViewById(R.id.hall_payment_item);
            TextView hallSumguestItem  = (TextView) convertView.findViewById(R.id.hall_sumguest_item);
            TextView hallSumbillItem  = (TextView) convertView.findViewById(R.id.hall_sumbill_item);

            Double bill = stringToDouble(field.getBillCount());
            Double guest = stringToDouble(field.getGuestCount());
            Double payment = stringToDouble(field.getPayment());
            Double sumGuest = stringToDouble(field.getSumGuest());
            Double sumBill = stringToDouble(field.getSumBill());

            billTotal = billTotal + bill;
            guestTotal = guestTotal + guest;
            paymentTotal = paymentTotal + payment;
            sumGuestTotal = sumGuestTotal + sumGuest;
            sumBillTotal = sumBillTotal + sumBill;

            if (id == null) {
                hallCap.setVisibility(View.VISIBLE);
                hallTitle.setText(field.getBaseExternalName());
            } else {
                if (!field.getBaseExternalID().equals(id)) {
                    hallCap.setVisibility(View.VISIBLE);
                    hallTitle.setText(field.getBaseExternalName());
                    billItem = 0.0;
                    guestItem = 0.0;
                    paymentItem = 0.0;
                    sumGuestItem = 0.0;
                    sumBillItem = 0.0;
                }
            }

            billItem = billItem + bill;
            guestItem = guestItem + guest;
            paymentItem = paymentItem + payment;
            sumGuestItem = sumGuestItem + sumGuest;
            sumBillItem = sumBillItem + sumBill;

            hallName.setText(field.getPlaceGroupShort());
            hallBills.setText(field.getBillCount());
            hallGuests.setText(field.getGuestCount());
            hallPayment.setText(field.getPayment());
            hallSumbill.setText(field.getSumGuest());
            hallSumguest.setText(field.getSumBill());


            Field nextField;
            try {
                nextField = fields.get(fields.indexOf(field)+1);
            } catch (Exception e) {
                nextField = null;
            }

            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {

                sectionTotal.setVisibility(View.VISIBLE);
                hallNameItem.setText(getActivity().getString(R.string.by)+" " + field.getBaseExternalName());
                hallBillsItem.setText(
                        precision.format(billItem).replace(".",","));
                hallGuestsItem.setText(
                        precision.format(guestItem).replace(".",","));
                hallPaymentItem.setText(
                        precision.format(paymentItem).replace(".",","));
                hallSumguestItem.setText(
                        precision.format(sumGuestItem).replace(".",","));
                hallSumbillItem.setText(
                        precision.format(sumBillItem).replace(".",","));
            }
            id = field.getBaseExternalID();
            hallcapContent.addView(convertView);
        }

        TextView hallcapBillsTotal = (TextView) view.findViewById(R.id.hallcap_bills_total);
        hallcapBillsTotal.setText(precision.format(billTotal).replace(".",","));
        TextView hallcapGuestsTotal = (TextView) view.findViewById(R.id.hallcap_guests_total);
        hallcapGuestsTotal.setText(precision.format(guestTotal).replace(".",","));
        TextView hallcapPaymentTotal = (TextView) view.findViewById(R.id.hallcap_payment_total);
        hallcapPaymentTotal.setText(precision.format(paymentTotal).replace(".",","));
        TextView hallcapSumguestTotal = (TextView) view.findViewById(R.id.hallcap_sumguest_total);
        hallcapSumguestTotal.setText(precision.format(sumGuestTotal).replace(".",","));
        TextView hallcapSumbillTotal = (TextView) view.findViewById(R.id.hallcap_sumbill_total);
        hallcapSumbillTotal.setText(precision.format(sumBillTotal).replace(".",","));


    }

    private void initUser(List<Field> fields) {

        LinearLayout usercapContent = (LinearLayout) view.findViewById(R.id.usercap_content);

        Double billItem = 0.0;
        Double guestItem = 0.0;
        Double paymentItem = 0.0;
        Double sumGuestItem = 0.0;
        Double sumBillItem = 0.0;
        Double billTotal = 0.0;
        Double guestTotal = 0.0;
        Double paymentTotal = 0.0;
        Double sumGuestTotal = 0.0;
        Double sumBillTotal = 0.0;

        String id = null;

        for (Field field : fields) {

            View convertView = inflater.inflate(R.layout.user_item, null);

            LinearLayout userCap = (LinearLayout) convertView.findViewById(R.id.user_cap);
            TextView userTitle = (TextView) convertView.findViewById(R.id.user_title);
            TextView userName = (TextView) convertView.findViewById(R.id.user_name);
            TextView userBills = (TextView) convertView.findViewById(R.id.user_bills);
            TextView userGuests = (TextView) convertView.findViewById(R.id.user_guests);
            TextView userPayment = (TextView) convertView.findViewById(R.id.user_payment);
            TextView userSumbill = (TextView) convertView.findViewById(R.id.user_sumbill);
            TextView userSumguest = (TextView) convertView.findViewById(R.id.user_sumguest);

            LinearLayout userTotal = (LinearLayout) convertView.findViewById(R.id.user_total);
            TextView userNameItem = (TextView) convertView.findViewById(R.id.user_name_item);
            TextView userBillsItem = (TextView) convertView.findViewById(R.id.user_bills_item);
            TextView userGuestsItem = (TextView) convertView.findViewById(R.id.user_guests_item);
            TextView userPaymentItem = (TextView) convertView.findViewById(R.id.user_payment_item);
            TextView userSumguestItem = (TextView) convertView.findViewById(R.id.user_sumguest_item);
            TextView userSumbillItem = (TextView) convertView.findViewById(R.id.user_sumbill_item);


            Double bill = stringToDouble(field.getBillCount());
            Double guest = stringToDouble(field.getGuestCount());
            Double payment = stringToDouble(field.getPayment());
            Double sumGuest = stringToDouble(field.getSumGuest());
            Double sumBill = stringToDouble(field.getSumBill());


            billTotal = billTotal + bill;
            guestTotal = guestTotal + guest;
            paymentTotal = paymentTotal + payment;
            sumGuestTotal = sumGuestTotal + sumGuest;
            sumBillTotal = sumBillTotal + sumBill;

            if (id == null) {
                userCap.setVisibility(View.VISIBLE);
                userTitle.setText(field.getBaseExternalName());
            } else {
                if (!field.getBaseExternalID().equals(id)) {
                    userCap.setVisibility(View.VISIBLE);
                    userTitle.setText(field.getBaseExternalName());
                    billItem = 0.0;
                    guestItem = 0.0;
                    paymentItem = 0.0;
                    sumGuestItem = 0.0;
                    sumBillItem = 0.0;
                }
            }

            billItem = billItem + bill;
            guestItem = guestItem + guest;
            paymentItem = paymentItem + payment;
            sumGuestItem = sumGuestItem + sumGuest;
            sumBillItem = sumBillItem + sumBill;

            userName.setText(field.getUserName());
            userBills.setText(field.getBillCount());
            userGuests.setText(field.getGuestCount());
            userPayment.setText(field.getPayment());
            userSumbill.setText(field.getSumGuest());
            userSumguest.setText(field.getSumBill());


            Field nextField;
            try {
                nextField = fields.get(fields.indexOf(field)+1);
            } catch (Exception e) {
                nextField = null;
            }

            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {

                userTotal.setVisibility(View.VISIBLE);
                userNameItem.setText(getActivity().getString(R.string.by) + " " + field.getBaseExternalName());
                userBillsItem.setText(
                        precision.format(billItem).replace(".", ","));
                userGuestsItem.setText(
                        precision.format(guestItem).replace(".", ","));
                userPaymentItem.setText(
                        precision.format(paymentItem).replace(".", ","));
                userSumguestItem.setText(
                        precision.format(sumGuestItem).replace(".", ","));
                userSumbillItem.setText(
                        precision.format(sumBillItem).replace(".", ","));
            }
            id = field.getBaseExternalID();
            usercapContent.addView(convertView);
        }

            TextView usercapBillsTotal = (TextView) view.findViewById(R.id.usercap_bills_total);
            usercapBillsTotal.setText(precision.format(billTotal).replace(".", ","));
            TextView usercapGuestsTotal = (TextView) view.findViewById(R.id.usercap_guests_total);
            usercapGuestsTotal.setText(precision.format(guestTotal).replace(".", ","));
            TextView usercapPaymentTotal = (TextView) view.findViewById(R.id.usercap_payment_total);
            usercapPaymentTotal.setText(precision.format(paymentTotal).replace(".", ","));
            TextView usercapSumguestTotal = (TextView) view.findViewById(R.id.usercap_sumguest_total);
            usercapSumguestTotal.setText(precision.format(sumGuestTotal).replace(".", ","));
            TextView usercapSumbillTotal = (TextView) view.findViewById(R.id.usercap_sumbill_total);
            usercapSumbillTotal.setText(precision.format(sumBillTotal).replace(".", ","));

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
