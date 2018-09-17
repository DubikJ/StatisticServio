package ua.com.servio.statisticservio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.model.json.Field;

public class PaymentAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<Field> fieldList;

    public PaymentAdapter(Context context, List<Field> fieldList) {
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

            Field field = getField(position);

            if(position==0 ||
                    (position>0 &&
                    !getField(position).getBaseExternalID().contains(field.getBaseExternalID())) ){
                LinearLayout paymentCap = (LinearLayout) convertView
                        .findViewById(R.id.payment_cap);
                paymentCap.setVisibility(View.VISIBLE);
                TextView paymentTitle = (TextView) convertView
                        .findViewById(R.id.payment_title);
                paymentTitle.setText(field.getBaseExternalName());
            }

            TextView paymentTypeName = (TextView) convertView
                    .findViewById(R.id.paymenttype_name);
            paymentTypeName.setText(field.getPaymentTypeName());
            TextView paymentTypeFiscal = (TextView) convertView
                    .findViewById(R.id.paymenttype_fiscal);
            paymentTypeFiscal.setText(field.getPaymentFiscalTypeName());
            TextView paymentTypeAccrual = (TextView) convertView
                    .findViewById(R.id.paymenttype_accrual);
            paymentTypeAccrual.setText(field.getAccrual());
            TextView paymentTypeDiscont = (TextView) convertView
                    .findViewById(R.id.paymenttype_discont);
            paymentTypeDiscont.setText(field.getDiscount());
            TextView paymentTypePayment = (TextView) convertView
                    .findViewById(R.id.paymenttype_payment);
            paymentTypePayment.setText(field.getPayment());

        }

        return convertView;
    }

    private Field getField(int position){
        return  fieldList.get(position);

    }


}
