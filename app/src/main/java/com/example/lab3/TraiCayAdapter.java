package com.example.lab3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TraiCayAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TraiCay> traiCayList;

    public TraiCayAdapter(Context context, int layout, List<TraiCay> traiCayList) {
        this.context = context;
        this.layout = layout;
        this.traiCayList = traiCayList;
    }

    @Override
    public int getCount() {
        return this.traiCayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.traiCayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, null);

            holder = new ViewHolder();

            holder.txtTen = (TextView) convertView.findViewById(R.id.tenTextView);
            holder.txtMota = (TextView) convertView.findViewById(R.id.motaTextView);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TraiCay traiCay = this.traiCayList.get(position);

        holder.txtTen.setText(traiCay.getTen());
        holder.txtMota.setText(traiCay.getMota());

        if (traiCay.getHinh() != null) {
            Glide.with(context)
                    .load(traiCay.getHinh())
                    .into(holder.imgHinh);
        } else {
            holder.imgHinh.setImageResource(R.drawable.dau);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtTen;
        TextView txtMota;
        ImageView imgHinh;
    }
}
