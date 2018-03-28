package com.naufalrzld.myasynctaskloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Naufal on 28/03/2018.
 */

public class WeatherAdapter extends BaseAdapter {
    private ArrayList<WeatherItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<WeatherItems> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final WeatherItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public WeatherItems getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.weather_items, null);
            holder.textViewNamaKota= (TextView)view.findViewById(R.id.textKota);
            holder.textViewTemperature = (TextView)view.findViewById(R.id.textTemp);
            holder.textViewDescription = (TextView)view.findViewById(R.id.textDesc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewNamaKota.setText(mData.get(position).getNama());
        holder.textViewTemperature.setText(mData.get(position).getTemperature());
        holder.textViewDescription.setText(mData.get(position).getDescription());
        return view;
    }

    private static class ViewHolder {
        TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;
    }
}
