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

public class ManualDiscountAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final List<Field> fieldList;

    public ManualDiscountAdapter(Context context, List<Field> fieldList) {
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

            convertView = inflater.inflate(R.layout.manual_discount_item, null);

            Field field = getField(position);

            TextView manualDiscountName = (TextView) convertView
                    .findViewById(R.id.manualdiscount_name);
            manualDiscountName.setText(field.getDiscountPercent());
            TextView manualDiscountBills = (TextView) convertView
                    .findViewById(R.id.manualdiscount_bills);
            manualDiscountBills.setText(field.getBillCount());
            TextView manualDiscountDiscont = (TextView) convertView
                    .findViewById(R.id.manualdiscount_sum);
            manualDiscountDiscont.setText(field.getDiscountSum());

        }

        return convertView;
    }

    private Field getField(int position){
        return  fieldList.get(position);

    }


}
