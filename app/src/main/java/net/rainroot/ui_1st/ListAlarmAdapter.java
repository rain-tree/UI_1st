package net.rainroot.ui_1st;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListAlarmAdapter implements ListAdapter {
    private LayoutInflater inflater;
    private ArrayList<ListAlarmitem> data;
    private int layout;

    public ListAlarmAdapter(Context context, int layout,ArrayList<ListAlarmitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    public int getCount(){
        return data.size();
    }
    public String getItem(int position){
        return data.get(position).getName();
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }
        ListAlarmitem listviewitem = data.get(position);

        ImageView icon=(ImageView)convertView.findViewById(R.id.imgview);
        icon.setImageResource(listviewitem.getIcon());
        TextView name = (TextView)convertView.findViewById(R.id.message);
        name.setText(listviewitem.getName());

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        Log.e(TAG,"aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }
}
