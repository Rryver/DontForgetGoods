package com.example.clickapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clickapplication.model.Record;

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private Record[] records;
    private static LayoutInflater inflater = null;

    private final Object mLock = new Object();

    public RecordAdapter(Context context, Record[] records) {
        this.context = context;
        this.records = records;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return records.length;
    }

    @Override
    public Object getItem(int position) {
        return records[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.records_item, null);
        }
        TextView title = vi.findViewById(R.id.record_title);
        title.setText(records[position].getTitle());
        TextView createdDate = vi.findViewById(R.id.record_date);
        createdDate.setText(records[position].getCreated_at());
        return vi;
    }

}
