package com.example.mvvmtrialapp2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtrialapp2.R;
import com.example.mvvmtrialapp2.databinding.ListItemBinding;
import com.example.mvvmtrialapp2.model.Address;
import com.example.mvvmtrialapp2.viewmodel.CityViewModel;

public class CityAdapter extends ListAdapter<Address, RecyclerView.ViewHolder> {

    private LifecycleOwner owner;

    public CityAdapter(@NonNull DiffUtil.ItemCallback<Address> diffCallback,LifecycleOwner _owner) {
        super(diffCallback);
        owner = _owner;
    }

    //ビューホルダーオブジェクトを生成
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //DataBindingインスタンス生成
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);

        //RootViewを取得
        View rootView = binding.getRoot();

        //RootViewにbindingを設定
        rootView.setTag(binding);

        return new RecyclerView.ViewHolder (rootView) {};
    }

    //データの割り当て
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListItemBinding binding = (ListItemBinding)holder.itemView.getTag();
        binding.setLifecycleOwner(owner);
        binding.setAddress(getItem(position));
        binding.executePendingBindings();
    }

    //変化を検知するインナークラス
    public static class CountryDiff extends DiffUtil.ItemCallback<Address> {

        //変化後、インスタンスが同じかどうかを判定
        @Override
        public boolean areItemsTheSame(@NonNull Address oldItem, @NonNull Address newItem) {
            return oldItem == newItem;
        }

        //変化後、インスタンスの中身が同じかどうかを判定
        @Override
        public boolean areContentsTheSame(@NonNull Address oldItem, @NonNull Address newItem) {
            return oldItem.getAddress3().equals(newItem.getAddress3());
        }
    }
}
