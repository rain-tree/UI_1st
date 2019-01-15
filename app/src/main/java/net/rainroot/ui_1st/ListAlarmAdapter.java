package net.rainroot.ui_1st;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAlarmAdapter  {
    private LayoutInflater inflater;
    private ArrayList<ListAlarmitem> data;
    private int layout;

    public ListAlarmAdapter(Context context, int layout,ArrayList<ListAlarmitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
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
}
