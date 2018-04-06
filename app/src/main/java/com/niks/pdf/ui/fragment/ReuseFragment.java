/*
 * Created by nikhil joshi
 */

package com.niks.pdf.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.niks.pdf.R;

import static com.niks.pdf.util.Constants.ACCESS_TOKEN;


public class ReuseFragment extends Fragment {



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_maps, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
// Create Dropbox client

        new Thread(){
            @Override
            public void run() {
                super.run();
                DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
                DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
                // Get current account info
                FullAccount account = null;
                try {
                    account = client.users().getCurrentAccount();
                } catch (DbxException e) {
                    e.printStackTrace();
                }
                System.out.println(account.getName().getDisplayName());

                // Get current account info

                // Get files and folder metadata from Dropbox root directory
                ListFolderResult result = null;
                try {
                    result = client.files().listFolder("/pdfmarathi");
                    while (true) {
                        for (Metadata metadata : result.getEntries()) {
                            System.out.println(metadata.getPathLower());
                        }

                        if (!result.getHasMore()) {
                            break;
                        }

                        result = client.files().listFolderContinue(result.getCursor());
                    }
                } catch (DbxException e) {
                    e.printStackTrace();
                }


            }
        }.start();




    }
}

