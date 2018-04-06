

/*
 * Created by nikhil joshi
 */

package com.niks.pdf.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.niks.pdf.R;
import com.niks.pdf.network.DownloadFileTask;
import com.niks.pdf.network.DropboxClientFactory;
import com.niks.pdf.ui.fragment.PDFViewerFragment;

import java.io.File;
import java.util.List;

import static com.niks.pdf.util.Constants.ACCESS_TOKEN;
import static com.niks.pdf.util.Constants.TAG;


public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.ViewHolder> {

    private int rowLayout;
    private Context mContext;
    //TODO: delete
    private final String API_KEY = "";
    private List<Metadata> mListFolderResult;

    public PdfListAdapter(List<Metadata> listFolderResult, int rowLayout, Context context) {
        this.mListFolderResult = listFolderResult;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void clearData(){
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name =  mListFolderResult.get(position).getName();
        System.out.println("--------------"+name);
        viewHolder.title.setText(name);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListFolderResult.get(position).getPathDisplay();
                fileDownload((FileMetadata) mListFolderResult.get(position));
            }
        });
    }



    private void fileDownload(FileMetadata metadata)
    {

        DropboxClientFactory.init(ACCESS_TOKEN);

        new DownloadFileTask(mContext, DropboxClientFactory.getClient(), new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {

                Log.e(TAG, "downloaded file."+result.getAbsolutePath().toString());


                replaceFragment(result.getName());
            }

            @Override
            public void onError(Exception e) {

                Log.e(TAG, "Failed to download file.", e);

            }
        }).execute(metadata);

    }

    private void replaceFragment(String name) {
        Fragment navFragment = new PDFViewerFragment();
        Bundle args = new Bundle();
        args.putString("filename", name);
        navFragment.setArguments(args);

        if (navFragment != null) {
            FragmentTransaction transaction =  ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListFolderResult.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView pubDate;
        ImageView image;

        public ViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}