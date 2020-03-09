package com.umld.animals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.umld.animals.R;
import com.umld.animals.model.AnimalModel;
import com.umld.animals.viewmodel.ListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    @BindView(R.id.animalRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.listError)
    TextView listError;

    @BindView(R.id.animalList)
    RecyclerView animalList;

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    private ListViewModel viewModel;
    private AnimalListAdapter listAdapter = new AnimalListAdapter();

//    @BindView(R.id.listFAB)
//    FloatingActionButton listFAB;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class); // 21

        viewModel.animals.observe(getViewLifecycleOwner(), (List<AnimalModel> list) -> {        // this instead getViewLifecycleOwner
            if (list != null) {
                animalList.setVisibility(View.VISIBLE);
                listAdapter.updateAnimalList(list);
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), loading-> {
            loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
            if(loading) {
                listError.setVisibility(View.GONE);
                animalList.setVisibility(View.GONE);
            }
        });

        viewModel.loadError.observe(getViewLifecycleOwner(), error-> {
            listError.setVisibility(error ? View.VISIBLE : View.GONE);
        });


        viewModel.refresh();

        if (animalList != null) {
            animalList.setLayoutManager(new GridLayoutManager(getContext(), 2));
            animalList.setAdapter(listAdapter);
        }

        refreshLayout.setOnRefreshListener(() -> {
            animalList.setVisibility(View.GONE);
            listError.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            viewModel.refresh();
            refreshLayout.setRefreshing(false);
        });
    }
}
