package com.realite.boucledor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.realite.boucledor.R;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class EntryListAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private List<InputEntry> entryList = new ArrayList<InputEntry>();
    private final Context context;

    public EntryListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Object getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return entryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout itemView;
        if (convertView == null) {
            itemView = (RelativeLayout) layoutInflater.inflate(
                    R.layout.entry_list_item, parent, false);

        } else {
            itemView = (RelativeLayout) convertView;
        }

        TextView itemDateText = (TextView) itemView.findViewById(R.id.entryDateView);
        TextView rawAmtText = (TextView) itemView.findViewById(R.id.entryRawAmtView);
        TextView netAmtText = (TextView) itemView.findViewById(R.id.entryNetAmtView);
        TextView typeText = (TextView) itemView.findViewById(R.id.entryTypeView);

        InputEntry entry = entryList.get(position);
        itemDateText.setText(DateUtil.formatDateTimeForDisplay(context, entry.getInputDate().toString()));
        rawAmtText.setText(entry.getRawAmt() + "€");
        netAmtText.setText(entry.getNetAmt() + "€");
        typeText.setText(entry.getType());
        return itemView;
    }

    public void updateEntries(List<InputEntry> newEntries) {
        entryList = newEntries;
        notifyDataSetChanged();
    }
}
