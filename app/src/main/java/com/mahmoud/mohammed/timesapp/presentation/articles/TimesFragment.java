package com.mahmoud.mohammed.timesapp.presentation.articles;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mahmoud.mohammed.timesapp.R;
import com.mahmoud.mohammed.timesapp.base.BaseFragment;
import com.mahmoud.mohammed.timesapp.data.models.Article;
import com.mahmoud.mohammed.timesapp.data.models.Response;
import com.mahmoud.mohammed.timesapp.di.ViewModelFactory;
import com.mahmoud.mohammed.timesapp.presentation.TimesListViewModel;
import com.mahmoud.mohammed.timesapp.presentation.adapters.ItemClickListener;
import com.mahmoud.mohammed.timesapp.presentation.adapters.TimesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;


public class TimesFragment extends BaseFragment implements ItemClickListener<Article> {

    TimesListViewModel viewModel;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    TimesAdapter adapter;
    @BindView(R.id.times_recycler)

    RecyclerView articlesRecyclerView;
   // private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
      //  dialog = new ProgressDialog(getActivity());
   //     dialog.setTitle("Loading");
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(TimesListViewModel.class);
        viewModel.loadTimes();
        viewModel.response().observe(this, this::processResponse);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_times, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
        // Inflate the layout for this fragment
    }

    private void initViews() {
        adapter.setItemClickListenr(this);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articlesRecyclerView.setAdapter(adapter);
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data.articles);
                break;

            default:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderErrorState(Throwable error) {
       // dialog.dismiss();
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void renderLoadingState() {

        //dialog.show();
    }

    private void renderDataState(List<Article> data) {
    //    dialog.dismiss();
        adapter.updateTimes(data);

    }

    @Override
    public void onItemClick(int position, Article model) {
        navigateToDetailsScreen(model);
    }
}
