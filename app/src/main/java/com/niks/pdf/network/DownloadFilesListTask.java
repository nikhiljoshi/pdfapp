/*
 * Created by nikhil joshi
 */

package com.niks.pdf.network;

import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.niks.pdf.model.Files;

import org.greenrobot.eventbus.EventBus;

import static com.niks.pdf.util.Constants.ACCESS_TOKEN;

public class DownloadFilesListTask extends AsyncTask<String, String, ListFolderResult> {


    @Override
    protected ListFolderResult doInBackground(String... params) {
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

        return result;
    }


    @Override
    protected void onPostExecute(ListFolderResult result) {
        // execution of result of Long time consuming operation
        Files.getInstance().setListFolderResult(result);
        EventBus.getDefault().post(new DownloadListener("Hello"));
    }


    @Override
    protected void onPreExecute() {

    }



}