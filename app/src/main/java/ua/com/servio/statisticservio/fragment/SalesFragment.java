package ua.com.servio.statisticservio.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import ua.com.servio.statisticservio.adapter.SalesAdapter;
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
    private LinearLayout content, salescapContent;
    private TextView noDataView, salesAmountTotal, salesSubtotalTotal,
            salesDiscountTotal, salesBasetotalTotal, salesTax1Total;

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
        salescapContent = (LinearLayout) view.findViewById(R.id.salescap_content);

        salesAmountTotal = (TextView) view.findViewById(R.id.salesitemcap_amount_total);
        salesSubtotalTotal = (TextView) view.findViewById(R.id.salesitemcap_subtotal_total);
        salesDiscountTotal = (TextView) view.findViewById(R.id.salesitemcap_discount_total);
        salesBasetotalTotal = (TextView) view.findViewById(R.id.salesitemcap_basetotal_total);
        salesTax1Total = (TextView) view.findViewById(R.id.salesitemcap_tax1_total);
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

        salescapContent.removeAllViews();

        Double amountItem = 0.0;
        Double subTotalItem = 0.0;
        Double discountItem = 0.0;
        Double baseTotalItem = 0.0;
        Double taxItem = 0.0;

        Double amountTotal = 0.0;
        Double subTotalTotal = 0.0;
        Double discountTotal = 0.0;
        Double baseTotalTotal = 0.0;
        Double taxTotal = 0.0;

        List<Field> fieldList = new ArrayList<>();

        for(Field field : fields) {

            Double amount = stringToDouble(field.getAmount());
            Double subTotal = stringToDouble(field.getSubTotal());
            Double discount = stringToDouble(field.getDiscount());
            Double baseTotal = stringToDouble(field.getBaseTotal());
            Double tax = stringToDouble(field.getTax1());

            amountTotal = amountTotal + amount;
            subTotalTotal = subTotalTotal + subTotal;
            discountTotal = discountTotal + discount;
            baseTotalTotal = baseTotalTotal + baseTotal;
            taxTotal = taxTotal + tax;

            amountItem = amountItem + amount;
            subTotalItem = subTotalItem + subTotal;
            discountItem = discountItem + discount;
            baseTotalItem = baseTotalItem + baseTotal;
            taxItem = taxItem + tax;


            fieldList.add(field);

            Field nextField;
            try {
                nextField = fields.get(fields.indexOf(field)+1);
            } catch (Exception e) {
                nextField = null;
            }

            if (nextField == null || !field.getBaseExternalID().equals(nextField.getBaseExternalID())) {

                showDataItem(field.getBaseExternalName(), fieldList,
                        amountItem, subTotalItem, discountItem, baseTotalItem, taxItem);

                amountItem = 0.0;
                subTotalItem = 0.0;
                discountItem = 0.0;
                baseTotalItem = 0.0;
                taxItem = 0.0;

                fieldList.clear();

            }

        }

        salesAmountTotal.setText(precision.format(amountTotal).replace(".",","));
        salesSubtotalTotal.setText(precision.format(subTotalTotal).replace(".",","));
        salesDiscountTotal.setText(precision.format(discountTotal).replace(".",","));
        salesBasetotalTotal.setText(precision.format(baseTotalTotal).replace(".",","));
        salesTax1Total.setText(precision.format(taxTotal).replace(".",","));



    }

    private void showDataItem(String title, List<Field> fields,
                              Double amountItem, Double subTotalItem,
                              Double discountItem, Double baseTotalItem, Double taxItem){

        View convertView = inflater.inflate(R.layout.sales_content, null);

        RelativeLayout salesCap = (RelativeLayout) convertView.findViewById(R.id.salesitemcap);
        final ImageView salesCapImage = (ImageView) convertView.findViewById(R.id.salesitemcap_image);
        final LinearLayout salesCapContainer = (LinearLayout) convertView.findViewById(R.id.salesitemcap_container);

        salesCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(salesCapContainer.getVisibility()==View.VISIBLE){
                    salesCapContainer.setVisibility(View.GONE);
                    salesCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_right));
                }else{
                    salesCapContainer.setVisibility(View.VISIBLE);
                    salesCapImage.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

                }
            }
        });

        TextView salesitemCapTitle = (TextView) convertView.findViewById(R.id.salesitemcap_title);
        salesitemCapTitle.setText(title);

        RecyclerView salesContent = (RecyclerView) convertView.findViewById(R.id.salesitemcap_content);
        salesContent.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        salesContent.setItemAnimator(new DefaultItemAnimator());
        salesContent.setHasFixedSize(true);

        SalesAdapter adapter = new SalesAdapter(getActivity(), fields);
        salesContent.setAdapter(adapter);

        TextView salesAmountItem = (TextView) convertView.findViewById(R.id.salesitemcap_amount_item);
        TextView salesSubtotalItem = (TextView) convertView.findViewById(R.id.salesitemcap_subtotal_item);
        TextView salesDiscountItem = (TextView) convertView.findViewById(R.id.salesitemcap_discount_item);
        TextView salesBasetotalItem = (TextView) convertView.findViewById(R.id.salesitemcap_basetotal_item);
        TextView salesTax1Item = (TextView) convertView.findViewById(R.id.salesitemcap_tax1_item);

        salesAmountItem.setText(precision.format(amountItem).replace(".",","));
        salesSubtotalItem.setText(precision.format(subTotalItem).replace(".",","));
        salesDiscountItem.setText(precision.format(discountItem).replace(".",","));
        salesBasetotalItem.setText(precision.format(baseTotalItem).replace(".",","));
        salesTax1Item.setText(precision.format(taxItem).replace(".",","));

        salescapContent.addView(convertView);

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
