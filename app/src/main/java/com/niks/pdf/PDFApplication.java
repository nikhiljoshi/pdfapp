/*
 * Created by nikhil joshi
 */

package com.niks.pdf;

import android.app.Application;

import com.niks.pdf.network.DownloadFilesListTask;


public class PDFApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadFilesListTask runner = new DownloadFilesListTask();
        runner.execute();
    }
}
