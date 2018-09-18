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

public class SectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<ItemField> fieldList;
    private DecimalFormat precision = new DecimalFormat("#.##");

    public SectionAdapter(Context context, List<ItemField> fieldList) {
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

            convertView = inflater.inflate(R.layout.section_item, null);

            ItemField field = getItemField(position);

            if(field.getShowTitle()){
                LinearLayout sectionCap = (LinearLayout) convertView
                        .findViewById(R.id.section_cap);
                sectionCap.setVisibility(View.VISIBLE);
                TextView sectionTitle = (TextView) convertView
                        .findViewById(R.id.section_title);
                sectionTitle.setText(field.getTitle());
            }

            TextView sectionName = (TextView) convertView
                    .findViewById(R.id.section_name);
            sectionName.setText(field.getPaymentTypeName());
            TextView sectionAmount = (TextView) convertView
                    .findViewById(R.id.section_amount);
            sectionAmount.setText(
                    String.valueOf(field.getParam1()).replace(".",","));
            TextView sectionAccrual = (TextView) convertView
                    .findViewById(R.id.section_accrual);
            sectionAccrual.setText(
                    String.valueOf(field.getParam2()).replace(".",","));
            TextView sectionDiscont = (TextView) convertView
                    .findViewById(R.id.section_discont);
            sectionDiscont.setText(
                    String.valueOf(field.getParam3()).replace(".",","));
            TextView sectionPayment = (TextView) convertView
                    .findViewById(R.id.section_payment);
            sectionPayment.setText(
                    String.valueOf(field.getParam4()).replace(".",","));

            if(field.getShowTotal()){

                LinearLayout sectionTotal = (LinearLayout) convertView
                        .findViewById(R.id.section_total);
                sectionTotal.setVisibility(View.VISIBLE);

                TextView sectionNameItem = (TextView) convertView
                        .findViewById(R.id.section_name_item);
                sectionNameItem.setText(context.getString(R.string.by)+" " + field.getTitle());

                TextView sectionAmountItem = (TextView) convertView
                        .findViewById(R.id.section_amount_item);
                sectionAmountItem.setText(
                        precision.format(field.getParam1Total()).replace(".",","));
                TextView sectionAccrualItem  = (TextView) convertView
                        .findViewById(R.id.section_accrual_item);
                sectionAccrualItem.setText(
                        precision.format(field.getParam2Total()).replace(".",","));
                TextView sectionDiscontItem  = (TextView) convertView
                        .findViewById(R.id.section_discont_item);
                sectionDiscontItem.setText(
                        precision.format(field.getParam3Total()).replace(".",","));
                TextView sectionPaymentItem  = (TextView) convertView
                        .findViewById(R.id.section_payment_item);
                sectionPaymentItem.setText(
                        precision.format(field.getParam4Total()).replace(".",","));
            }

        }
        return convertView;
    }

    private ItemField getItemField(int position){
        return  fieldList.get(position);

    }


}
