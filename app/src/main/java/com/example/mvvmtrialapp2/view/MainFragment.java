package com.example.mvvmtrialapp2.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmtrialapp2.R;
import com.example.mvvmtrialapp2.databinding.FragmentMainBinding;
import com.example.mvvmtrialapp2.model.Address;
import com.example.mvvmtrialapp2.viewmodel.CityViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMainBinding binding;
    private CityViewModel viewModel;
    private CityAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);

        //DataBindingインスタンス生成
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewModelインスタンス生成
        viewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);

        //SearchViewにリスナ設定
        binding.svZipcode.setOnQueryTextListener(new searchViewListener());

        //RecyclerViewの表示設定
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        adapter = new CityAdapter(new CityAdapter.CountryDiff(),getActivity());
        binding.recyclerView.setAdapter(adapter);

        //LiveDataを監視
        viewModel.getAddress().observe(getViewLifecycleOwner(),new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {

                //変更があるたびに、差分データを更新
                //プログレスバーを非表示
                adapter.submitList(addresses);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    //SearchViewのリスナークラス
    private class searchViewListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {

            //7文字の場合
            //検索処理
            //プログレスバーを表示
            if(query.length() == 7) {
                viewModel.searchAddress(query);
                binding.progressBar.setVisibility(View.VISIBLE);
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            //０文字の場合
            //空にする
            if(newText.length() == 0) {
                viewModel.clearAddress();
            }
            return false;
        }
    }
}