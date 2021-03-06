package com.realite.boucledor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import java.util.Collection;
import java.util.List;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<InputEntry> entryList = new ArrayList<>();
    private final Context context;
    private static final String EURO = " €";

    public EntryListAdapter(Context context, List<InputEntry> defaultEntries) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.entryList = defaultEntries;
    }

    @Override
    public EntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout itemView = (RelativeLayout) layoutInflater.inflate(
                    R.layout.entry_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InputEntry entry = entryList.get(position);
        holder.idText.setText(String.valueOf(entry.getId()));
        holder.rawAmtText.setText(entry.getRawAmt() + EURO);
        holder.netAmtText.setText(entry.getNetAmt() + EURO);
        holder.typeText.setText(entry.getType());
        holder.itemDateText.setText(DateUtil.formatDateTimeForDisplay
                (context, entry.getInputDate().toString()));
        holder.sessionsText.setText(String.valueOf(entry.getSessions()));
    }

    @Override
    public long getItemId(int position) {
        return entryList.get(position).getId();
    }

    @Override
    public int getItemCount() {
       return entryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemDateText;
        public TextView rawAmtText;
        public TextView netAmtText;
        public TextView typeText;
        public TextView idText;
        public TextView sessionsText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.idText = (TextView) itemView.findViewById(R.id.entryidHidden);
            this.itemDateText = (TextView) itemView.findViewById(R.id.entryDateView);
            this.rawAmtText = (TextView) itemView.findViewById(R.id.entryRawAmtView);
            this.netAmtText = (TextView) itemView.findViewById(R.id.entryNetAmtView);
            this.typeText = (TextView) itemView.findViewById(R.id.entryTypeView);
            this.sessionsText = (TextView) itemView.findViewById(R.id.entrySessionsNb);
        }
    }

    public int removeFromSwipe(int position) {
        int removedId = entryList.get(position).getId();
        entryList.remove(position);
        notifyDataSetChanged();
        return removedId;
    }

}
