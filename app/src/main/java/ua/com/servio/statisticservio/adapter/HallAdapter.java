package ua.com.servio.statisticservio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.model.ItemField;

public class HallAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<ItemField> fieldList;
    private DecimalFormat precision = new DecimalFormat("#.##");

    public HallAdapter(Context context, List<ItemField> fieldList) {
        this.context = context;
        this.fieldList = fieldList;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fieldList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.hall_item, null);

            ItemField field = getItemField(position);

            if(field.getShowTitle()){
                LinearLayout hallCap = (LinearLayout) convertView
                        .findViewById(R.id.hall_cap);
                hallCap.setVisibility(View.VISIBLE);
                TextView hallTitle = (TextView) convertView
                        .findViewById(R.id.hall_title);
                hallTitle.setText(field.getTitle());
            }

            TextView hallName = (TextView) convertView
                    .findViewById(R.id.hall_name);
            hallName.setText(field.getPaymentTypeName());
            TextView hallBills = (TextView) convertView
                    .findViewById(R.id.hall_bills);
            hallBills.setText(
                    String.valueOf(field.getParam1()).replace(".",","));
            TextView hallGuests = (TextView) convertView
                    .findViewById(R.id.hall_guests);
            hallGuests.setText(
                    String.valueOf(field.getParam2()).replace(".",","));
            TextView hallPayment = (TextView) convertView
                    .findViewById(R.id.hall_payment);
            hallPayment.setText(
                    String.valueOf(field.getParam3()).replace(".",","));
            TextView hallSumbill = (TextView) convertView
                    .findViewById(R.id.hall_sumbill);
            hallSumbill.setText(
                    String.valueOf(field.getParam4()).replace(".",","));
            TextView hallSumguest = (TextView) convertView
                    .findViewById(R.id.hall_sumguest);
            hallSumguest.setText(
                    String.valueOf(field.getParam5()).replace(".",","));

            if(field.getShowTotal()){

                LinearLayout sectionTotal = (LinearLayout) convertView
                        .findViewById(R.id.hall_total);
                sectionTotal.setVisibility(View.VISIBLE);

                TextView hallNameItem = (TextView) convertView
                        .findViewById(R.id.hall_name_item);
                hallNameItem.setText(context.getString(R.string.by)+" " + field.getTitle());

                TextView hallBillsItem = (TextView) convertView
                        .findViewById(R.id.hall_bills_item);
                hallBillsItem.setText(
                        precision.format(field.getParam1Total()).replace(".",","));
                TextView hallGuestsItem  = (TextView) convertView
                        .findViewById(R.id.hall_guests_item);
                hallGuestsItem.setText(
                        precision.format(field.getParam2Total()).replace(".",","));
                TextView hallPaymentItem  = (TextView) convertView
                        .findViewById(R.id.hall_payment_item);
                hallPaymentItem.setText(
                        precision.format(field.getParam3Total()).replace(".",","));
                TextView hallSumguestItem  = (TextView) convertView
                        .findViewById(R.id.hall_sumguest_item);
                hallSumguestItem.setText(
                        precision.format(field.getParam4Total()).replace(".",","));
                TextView hallSumbillItem  = (TextView) convertView
                        .findViewById(R.id.hall_sumbill_item);
                hallSumbillItem.setText(
                        precision.format(field.getParam5Total()).replace(".",","));
            }

        }
        return convertView;
    }

    private ItemField getItemField(int position){
        return  fieldList.get(position);

    }


}
