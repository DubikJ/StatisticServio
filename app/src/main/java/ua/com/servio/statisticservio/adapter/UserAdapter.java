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

public class UserAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<ItemField> fieldList;
    private DecimalFormat precision = new DecimalFormat("#.##");

    public UserAdapter(Context context, List<ItemField> fieldList) {
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

            convertView = inflater.inflate(R.layout.user_item, null);

            ItemField field = getItemField(position);

            if(field.getShowTitle()){
                LinearLayout userCap = (LinearLayout) convertView
                        .findViewById(R.id.user_cap);
                userCap.setVisibility(View.VISIBLE);
                TextView userTitle = (TextView) convertView
                        .findViewById(R.id.user_title);
                userTitle.setText(field.getTitle());
            }

            TextView userName = (TextView) convertView
                    .findViewById(R.id.user_name);
            userName.setText(field.getPaymentTypeName());
            TextView userBills = (TextView) convertView
                    .findViewById(R.id.user_bills);
            userBills.setText(
                    String.valueOf(field.getParam1()).replace(".",","));
            TextView userGuests = (TextView) convertView
                    .findViewById(R.id.user_guests);
            userGuests.setText(
                    String.valueOf(field.getParam2()).replace(".",","));
            TextView userPayment = (TextView) convertView
                    .findViewById(R.id.user_payment);
            userPayment.setText(
                    String.valueOf(field.getParam3()).replace(".",","));
            TextView userSumbill = (TextView) convertView
                    .findViewById(R.id.user_sumbill);
            userSumbill.setText(
                    String.valueOf(field.getParam4()).replace(".",","));
            TextView userSumguest = (TextView) convertView
                    .findViewById(R.id.user_sumguest);
            userSumguest.setText(
                    String.valueOf(field.getParam5()).replace(".",","));

            if(field.getShowTotal()){

                LinearLayout userTotal = (LinearLayout) convertView
                        .findViewById(R.id.user_total);
                userTotal.setVisibility(View.VISIBLE);

                TextView userNameItem = (TextView) convertView
                        .findViewById(R.id.user_name_item);
                userNameItem.setText(context.getString(R.string.by)+" " + field.getTitle());

                TextView userBillsItem = (TextView) convertView
                        .findViewById(R.id.user_bills_item);
                userBillsItem.setText(
                        precision.format(field.getParam1Total()).replace(".",","));
                TextView userGuestsItem  = (TextView) convertView
                        .findViewById(R.id.user_guests_item);
                userGuestsItem.setText(
                        precision.format(field.getParam2Total()).replace(".",","));
                TextView userPaymentItem  = (TextView) convertView
                        .findViewById(R.id.user_payment_item);
                userPaymentItem.setText(
                        precision.format(field.getParam3Total()).replace(".",","));
                TextView userSumguestItem  = (TextView) convertView
                        .findViewById(R.id.user_sumguest_item);
                userSumguestItem.setText(
                        precision.format(field.getParam4Total()).replace(".",","));
                TextView userSumbillItem  = (TextView) convertView
                        .findViewById(R.id.user_sumbill_item);
                userSumbillItem.setText(
                        precision.format(field.getParam5Total()).replace(".",","));
            }

        }
        return convertView;
    }

    private ItemField getItemField(int position){
        return  fieldList.get(position);

    }


}
