package com.lins.modulehome.test.ibeacon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lins.modulehome.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<IBeaconBean> mList;

    public ProductAdapter() {
        mList = new ArrayList<>();
    }

    public void addData(IBeaconBean bean) {
        mList.add(bean);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ibeacon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IBeaconBean bean = mList.get(position);
        holder.idTvDesc.setText(bean.toString());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTvDesc = itemView.findViewById(R.id.id_tv_desc);
        }
    }
}
