package ua.com.servio.statisticservio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.com.servio.statisticservio.R;
import ua.com.servio.statisticservio.model.json.Field;

public class SalesAdapter extends RecyclerView.Adapter{

    private List<Field> list1;
    private List<Field> list;
    private LayoutInflater layoutInflater;

    public SalesAdapter(Context context, List<Field> list) {
        this.list = list;
        this.list1 = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder {

        TextView salesArticleName;
        TextView salesAmount;
        TextView salesSubtotal;
        TextView salesDiscount;
        TextView salesBasetotal;
        TextView salesTax1;

        public SalesViewHolder(View itemView) {
            super(itemView);
            this.salesArticleName = (TextView) itemView.findViewById(R.id.sales_article_name);
            this.salesAmount = (TextView) itemView.findViewById(R.id.sales_amount);
            this.salesSubtotal = (TextView) itemView.findViewById(R.id.sales_subtotal);
            this.salesDiscount = (TextView) itemView.findViewById(R.id.sales_discount);
            this.salesBasetotal = (TextView) itemView.findViewById(R.id.sales_basetotal);
            this.salesTax1 = (TextView) itemView.findViewById(R.id.sales_tax1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.sales_item, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Field selectedItem = getField(position);
        if (selectedItem != null) {
            ((SalesViewHolder) holder).salesArticleName.setText(selectedItem.getArticleName());
            ((SalesViewHolder) holder).salesAmount.setText(selectedItem.getAmount());
            ((SalesViewHolder) holder).salesSubtotal.setText(selectedItem.getSubTotal());
            ((SalesViewHolder) holder).salesDiscount.setText(selectedItem.getDiscount());
            ((SalesViewHolder) holder).salesBasetotal.setText(selectedItem.getBaseTotal());
            ((SalesViewHolder) holder).salesTax1.setText(selectedItem.getTax1());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private Field getField(int position) {
        return (Field) list.get(position);
    }

}
