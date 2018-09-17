package ua.com.servio.statisticservio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.model.json.Field;

public class GroupDiscountAdapter  extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<Field> fieldList;

    public GroupDiscountAdapter(Context context, List<Field> fieldList) {
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

            convertView = inflater.inflate(R.layout.group_discount_item, null);

            Field field = getField(position);

            TextView groupDiscountName = (TextView) convertView
                    .findViewById(R.id.groupdiscount_name);
            groupDiscountName.setText(field.getDiscountGroupName());
            TextView groupDiscountBills = (TextView) convertView
                    .findViewById(R.id.groupdiscount_bills);
            groupDiscountBills.setText(field.getBillCount());
            TextView groupDiscountDiscont = (TextView) convertView
                    .findViewById(R.id.groupdiscount_discont);
            groupDiscountDiscont.setText(field.getDiscountSum());
            TextView groupDiscountPaid = (TextView) convertView
                    .findViewById(R.id.groupdiscount_paid);
            groupDiscountPaid.setText(field.getBonusSum());

        }

        return convertView;
    }

    private Field getField(int position){
        return  fieldList.get(position);

    }


}
