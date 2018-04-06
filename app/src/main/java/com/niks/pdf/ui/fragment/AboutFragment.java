/*
 * Created by nikhil joshi
 */

package com.niks.pdf.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niks.pdf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class AboutFragment extends Fragment {

    @BindView(R.id.developersView)
    View developersView;
    @BindView(R.id.licensesView)
    View licensesView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        String packageName = getActivity().getPackageName();
        String translator = getResources().getString(R.string.translator);

        SharedPreferences prefs = getContext().getSharedPreferences(packageName + "_preferences", getActivity().MODE_PRIVATE);
        return view;
    }
}
