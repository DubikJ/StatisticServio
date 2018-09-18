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
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.activity.BasicActivity;
import ua.com.servio.statisticservio.adapter.GroupDiscountAdapter;
import ua.com.servio.statisticservio.adapter.HallAdapter;
import ua.com.servio.statisticservio.adapter.ManualDiscountAdapter;
import ua.com.servio.statisticservio.adapter.PaymentAdapter;
import ua.com.servio.statisticservio.adapter.SectionAdapter;
import ua.com.servio.statisticservio.adapter.UserAdapter;
import ua.com.servio.statisticservio.model.ItemField;
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
        discTotalBills.setText(precision.format(billCount).replace(".",","));
        TextView discTotal = (TextView) view.findViewById(R.id.groupdiscount_total_discont);
        discTotal.setText(precision.format(discountSum).replace(".",","));
        TextView discTotalPaid = (TextView) view.findViewById(R.id.groupdiscount_total_paid);
        discTotalPaid.setText(precision.format(bonusSum).replace(".",","));

    }

    private void initManualDiscount(List<Field> fields){
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
        discTotalBills.setText(precision.format(billCount).replace(".",","));
        TextView discTotalSum = (TextView) view.findViewById(R.id.manualdiscount_total_sum);
        discTotalSum.setText(precision.format(bonusSum).replace(".",","));

    }

    private void initPayment(List<Field> fields){
        Double accrualItem = 0.0;
        Double discontItem = 0.0;
        Double paymentItem = 0.0;
        Double accrualTotal = 0.0;
        Double discontTotal = 0.0;
        Double paymentTotal = 0.0;

        List<ItemField> itemFields = new ArrayList<>();
        String id = null;

        ItemField fieldOld = null;

        for(Field field : fields){
            Double accrual = stringToDouble(field.getAccrual());
            Double discont = stringToDouble(field.getDiscount());
            Double payment = stringToDouble(field.getPayment());

            accrualTotal = accrualTotal + accrual;
            discontTotal = discontTotal + discont;
            paymentTotal = paymentTotal + payment;

            Boolean showTitle =false;

            if (id == null) {
                showTitle = true;
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    showTitle = true;
                    accrualItem = 0.0;
                    discontItem = 0.0;
                    paymentItem = 0.0;
                } else {
                    fieldOld.setShowTotal(false);
                }
            }

            accrualItem = accrualItem + accrual;
            discontItem = discontItem + discont;
            paymentItem = paymentItem + payment;

            ItemField itemField = new ItemField(field.getBaseExternalName(),
                    field.getPaymentTypeName(),
                    field.getPaymentFiscalTypeName(),
                    accrual,
                    discont,
                    payment,
                    accrualItem,
                    discontItem,
                    paymentItem,
                    showTitle,
                    true);

            itemFields.add(itemField);

            id = field.getBaseExternalID();

            fieldOld = itemField;

        }


        PaymentAdapter adapter = new PaymentAdapter(getActivity(), itemFields);
        ListView listView = (ListView) view.findViewById(R.id.paymentcap_content);
        listView.setAdapter(adapter);

        TextView paymentTotalAccrual = (TextView) view.findViewById(R.id.payment_total_accrual);
        paymentTotalAccrual.setText(precision.format(accrualTotal).replace(".",","));
        TextView paymentTotalDiscont = (TextView) view.findViewById(R.id.payment_total_discont);
        paymentTotalDiscont.setText(precision.format(discontTotal).replace(".",","));
        TextView paymentTotalPayment = (TextView) view.findViewById(R.id.payment_total_payment);
        paymentTotalPayment.setText(precision.format(paymentTotal).replace(".",","));


    }

    private void initSection(List<Field> fields){
        Double amountItem = 0.0;
        Double accrualItem = 0.0;
        Double discontItem = 0.0;
        Double paymentItem = 0.0;
        Double amountTotal = 0.0;
        Double accrualTotal = 0.0;
        Double discontTotal = 0.0;
        Double paymentTotal = 0.0;

        List<ItemField> itemFields = new ArrayList<>();
        String id = null;

        ItemField fieldOld = null;

        for(Field field : fields){
            Double amount = stringToDouble(field.getAmount());
            Double accrual = stringToDouble(field.getAccrual());
            Double discont = stringToDouble(field.getDiscount());
            Double payment = stringToDouble(field.getPayment());

            amountTotal = amountTotal + amount;
            accrualTotal = accrualTotal + accrual;
            discontTotal = discontTotal + discont;
            paymentTotal = paymentTotal + payment;

            Boolean showTitle = false;

            if (id == null) {
                showTitle = true;
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    showTitle = true;
                    amountItem = 0.0;
                    accrualItem = 0.0;
                    discontItem = 0.0;
                    paymentItem = 0.0;
                } else {
                    fieldOld.setShowTotal(false);
                }
            }

            amountItem = amountItem + amount;
            accrualItem = accrualItem + accrual;
            discontItem = discontItem + discont;
            paymentItem = paymentItem + payment;

            ItemField itemField = new ItemField(field.getBaseExternalName(),
                    field.getSectionName(),
                    field.getPaymentFiscalTypeName(),
                    amount,
                    accrual,
                    discont,
                    payment,
                    amountItem,
                    accrualItem,
                    discontItem,
                    paymentItem,
                    showTitle,
                    true);

            itemFields.add(itemField);

            id = field.getBaseExternalID();

            fieldOld = itemField;

        }


        SectionAdapter adapter = new SectionAdapter(getActivity(), itemFields);
        ListView listView = (ListView) view.findViewById(R.id.sectioncap_content);
        listView.setAdapter(adapter);

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

        List<ItemField> itemFields = new ArrayList<>();
        String id = null;

        ItemField fieldOld = null;

        for(Field field : fields){
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

            Boolean showTitle = false;

            if (id == null) {
                showTitle = true;
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    showTitle = true;
                    billItem = 0.0;
                    guestItem = 0.0;
                    paymentItem = 0.0;
                    sumGuestItem = 0.0;
                    sumBillItem = 0.0;
                } else {
                    fieldOld.setShowTotal(false);
                }
            }

            billItem = billItem + bill;
            guestItem = guestItem + guest;
            paymentItem = paymentItem + payment;
            sumGuestItem = sumGuestItem + sumGuest;
            sumBillItem = sumBillItem + sumBill;

            ItemField itemField = new ItemField(field.getBaseExternalName(),
                    "",
                    "",
                    bill,
                    guest,
                    payment,
                    sumGuest,
                    sumBill,
                    billItem,
                    guestItem,
                    paymentItem,
                    sumGuestItem,
                    sumBillItem,
                    showTitle,
                    true);

            itemFields.add(itemField);

            id = field.getBaseExternalID();

            fieldOld = itemField;

        }


        HallAdapter adapter = new HallAdapter(getActivity(), itemFields);
        ListView listView = (ListView) view.findViewById(R.id.hallcap_content);
        listView.setAdapter(adapter);

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

    private void initUser(List<Field> fields){
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

        List<ItemField> itemFields = new ArrayList<>();
        String id = null;

        ItemField fieldOld = null;

        for(Field field : fields){
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

            Boolean showTitle = false;

            if (id == null) {
                showTitle = true;
            }else {
                if (!field.getBaseExternalID().equals(id)) {
                    showTitle = true;
                    billItem = 0.0;
                    guestItem = 0.0;
                    paymentItem = 0.0;
                    sumGuestItem = 0.0;
                    sumBillItem = 0.0;
                } else {
                    fieldOld.setShowTotal(false);
                }
            }

            billItem = billItem + bill;
            guestItem = guestItem + guest;
            paymentItem = paymentItem + payment;
            sumGuestItem = sumGuestItem + sumGuest;
            sumBillItem = sumBillItem + sumBill;

            ItemField itemField = new ItemField(field.getBaseExternalName(),
                    field.getUserName(),
                    "",
                    bill,
                    guest,
                    payment,
                    sumGuest,
                    sumBill,
                    billItem,
                    guestItem,
                    paymentItem,
                    sumGuestItem,
                    sumBillItem,
                    showTitle,
                    true);

            itemFields.add(itemField);

            id = field.getBaseExternalID();

            fieldOld = itemField;

        }


        UserAdapter adapter = new UserAdapter(getActivity(), itemFields);
        ListView listView = (ListView) view.findViewById(R.id.usercap_content);
        listView.setAdapter(adapter);

        TextView usercapBillsTotal = (TextView) view.findViewById(R.id.usercap_bills_total);
        usercapBillsTotal.setText(precision.format(billTotal).replace(".",","));
        TextView usercapGuestsTotal = (TextView) view.findViewById(R.id.usercap_guests_total);
        usercapGuestsTotal.setText(precision.format(guestTotal).replace(".",","));
        TextView usercapPaymentTotal = (TextView) view.findViewById(R.id.usercap_payment_total);
        usercapPaymentTotal.setText(precision.format(paymentTotal).replace(".",","));
        TextView usercapSumguestTotal = (TextView) view.findViewById(R.id.usercap_sumguest_total);
        usercapSumguestTotal.setText(precision.format(sumGuestTotal).replace(".",","));
        TextView usercapSumbillTotal = (TextView) view.findViewById(R.id.usercap_sumbill_total);
        usercapSumbillTotal.setText(precision.format(sumBillTotal).replace(".",","));


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
