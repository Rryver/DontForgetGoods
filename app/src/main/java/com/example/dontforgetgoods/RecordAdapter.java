package com.example.dontforgetgoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dontforgetgoods.model.Product;
import com.example.dontforgetgoods.model.Record;
import com.example.dontforgetgoods.utils.DateUtils;
import com.example.dontforgetgoods.utils.ProductService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class RecordAdapter extends BaseExpandableListAdapter {

    private final List<Record> records;
    private final Map<Long, List<Product>> productMap;

    private static LayoutInflater inflater = null;

    public RecordAdapter(Context context, List<Record> records, Map<Long, List<Product>> productMap) {
        this.records = records;
        this.productMap = productMap;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateProducts(Long recordId, List<Product> products) {
        productMap.put(recordId, products);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return records.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Product> products = productMap.get(records.get(groupPosition).getId());
        if (products != null) {
            return products.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return records.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return productMap.get(records.get(groupPosition).getId());
    }

    @Override
    public long getGroupId(int groupPosition) {
        return records.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        List<Product> products = productMap.get(records.get(groupPosition).getId());
        if (products != null) {
            return products.get(childPosition).getId();
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.records_item, null);
        }

        TextView title = vi.findViewById(R.id.record_title);
        title.setText(records.get(groupPosition).getTitle());

        TextView createdDate = vi.findViewById(R.id.record_date);
        Timestamp timestamp = DateUtils.toTimestampFromUnix(records.get(groupPosition).getCreatedAt());
        createdDate.setText(DateUtils.toStringDate(timestamp, DateUtils.DATE_FORMAT));
        return vi;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        List<Product> products = productMap.get(records.get(groupPosition).getId());
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.products_item, null);
        }

        TextView title = vi.findViewById(R.id.product_title);
        title.setText(products.get(childPosition).getTitle());

        CheckBox isDoneCheckBox = vi.findViewById(R.id.product_is_done);
        isDoneCheckBox.setChecked(products.get(childPosition).getIsDone());
        isDoneCheckBox.setOnClickListener(v -> {
            products.get(childPosition).setIsDone(!products.get(childPosition).getIsDone());
            ProductService productService = new ProductService(this);
            productService.updateProductStatus(products.get(childPosition).getId());
        });

        return vi;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
