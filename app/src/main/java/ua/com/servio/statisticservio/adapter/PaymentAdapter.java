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

public class PaymentAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<ItemField> fieldList;
    private DecimalFormat precision = new DecimalFormat("#.##");

    public PaymentAdapter(Context context, List<ItemField> fieldList) {
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

            convertView = inflater.inflate(R.layout.payment_item, null);

            ItemField field = getItemField(position);

            if(field.getShowTitle()){
                LinearLayout paymentCap = (LinearLayout) convertView
                        .findViewById(R.id.payment_cap);
                paymentCap.setVisibility(View.VISIBLE);
                TextView paymentTitle = (TextView) convertView
                        .findViewById(R.id.payment_title);
                paymentTitle.setText(field.getTitle());
            }

            TextView paymentTypeName = (TextView) convertView
                    .findViewById(R.id.paymenttype_name);
            paymentTypeName.setText(field.getPaymentTypeName());
            TextView paymentTypeFiscal = (TextView) convertView
                    .findViewById(R.id.paymenttype_fiscal);
            paymentTypeFiscal.setText(field.getPaymentFiscalTypeName());
            TextView paymentTypeAccrual = (TextView) convertView
                    .findViewById(R.id.paymenttype_accrual);
            paymentTypeAccrual.setText(
                    String.valueOf(field.getParam1()).replace(".",","));
            TextView paymentTypeDiscont = (TextView) convertView
                    .findViewById(R.id.paymenttype_discont);
            paymentTypeDiscont.setText(
                    String.valueOf(field.getParam2()).replace(".",","));
            TextView paymentTypePayment = (TextView) convertView
                    .findViewById(R.id.paymenttype_payment);
            paymentTypePayment.setText(
                    String.valueOf(field.getParam3()).replace(".",","));

            if(field.getShowTotal()){

                LinearLayout paymentTotal = (LinearLayout) convertView
                        .findViewById(R.id.payment_total);
                paymentTotal.setVisibility(View.VISIBLE);

                TextView paymentTotalTitle = (TextView) convertView
                        .findViewById(R.id.payment_total_titla);
                paymentTotalTitle.setText(context.getString(R.string.by)+" " + field.getTitle());

                TextView paymentTypeAccrualTotal = (TextView) convertView
                        .findViewById(R.id.paymenttype_accrual_total);
                paymentTypeAccrualTotal.setText(
                        precision.format(field.getParam1Total()).replace(".",","));
                TextView paymentTypeDiscontTotal  = (TextView) convertView
                        .findViewById(R.id.paymenttype_discont_total);
                paymentTypeDiscontTotal.setText(
                        precision.format(field.getParam2Total()).replace(".",","));
                TextView paymentTypePaymentTotal  = (TextView) convertView
                        .findViewById(R.id.paymenttype_payment_total);
                paymentTypePaymentTotal.setText(
                        precision.format(field.getParam3Total()).replace(".",","));

            }

        }
        return convertView;
    }

    private ItemField getItemField(int position){
        return  fieldList.get(position);

    }


}
