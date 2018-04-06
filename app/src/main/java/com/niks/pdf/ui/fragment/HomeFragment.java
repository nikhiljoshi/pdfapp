package com.niks.pdf.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.ProgressBar;

import com.dropbox.core.v2.files.ListFolderResult;
import com.niks.pdf.R;
import com.niks.pdf.model.Files;
import com.niks.pdf.ui.adapter.PdfListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.recyclerlist)
    RecyclerView mRecyclerView;
    private PdfListAdapter mPdfListAdapter;
    @BindView(R.id.swipecontainer)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    private FloatingActionButton fab;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                mPdfListAdapter.clearData();
                mPdfListAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListFolderResult listFolderResult = Files.getInstance().getListFolderResult();

        if (listFolderResult != null) {
            mPdfListAdapter = new PdfListAdapter(listFolderResult.getEntries(), R.layout.pdf_row, getActivity());
            mRecyclerView.setAdapter(mPdfListAdapter);
            mPdfListAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Subscribe
    public void onMessageEvent(DownloadListener event) {
        if(mPdfListAdapter!=null) {
            mPdfListAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}

