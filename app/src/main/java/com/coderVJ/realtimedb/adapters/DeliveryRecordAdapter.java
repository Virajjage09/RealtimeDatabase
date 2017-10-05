package com.coderVJ.realtimedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.coderVJ.realtimedb.R;
import com.coderVJ.realtimedb.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viraj Jage on 11/109/17.
 */

public class DeliveryRecordAdapter extends BaseAdapter {

    List<User> userList = new ArrayList<User>();
    Context mContext;
    LayoutInflater mInflater;
    User user;

    public DeliveryRecordAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.userList = userList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.size();
    }

    @Override
    public long getItemId(int i) {
        return userList.size();
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        View view = convertView;
        listview_itemHolder holder = null;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.row_delivery_records_adapter, parent,false);
            holder = new listview_itemHolder();
            holder.txtCustName = (TextView) view.findViewById(R.id.custName);
            holder.txtCustMob = (TextView) view.findViewById(R.id.custMob);
            holder.txtCustEmail = (TextView) view.findViewById(R.id.custEmail);
            holder.txtCustAddress = (TextView) view.findViewById(R.id.custAddress);

            view.setTag(holder);
        } else {
            holder = (listview_itemHolder) view.getTag();
        }

        if (userList.size() > 0 && userList != null) {
                holder.txtCustName.setText(userList.get(postion).getName());
                holder.txtCustMob.setText(userList.get(postion).getMobileno());
                holder.txtCustEmail.setText(userList.get(postion).getEmail());
                holder.txtCustAddress.setText(userList.get(postion).getAddress());
        }

        return view;
    }

    static class listview_itemHolder {
        TextView txtCustName;
        TextView txtCustMob;
        TextView txtCustEmail;
        TextView txtCustAddress;
    }
}
