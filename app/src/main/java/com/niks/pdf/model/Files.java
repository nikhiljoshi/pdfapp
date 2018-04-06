/*
 * Created by nikhil joshi
 */

package com.niks.pdf.model;

import com.dropbox.core.v2.files.ListFolderResult;

public class Files {

    protected String name;
    public ListFolderResult listFolderResult;
    private static final Files ourInstance = new Files();

   public static Files getInstance() {
        return ourInstance;
    }

    private Files() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListFolderResult getListFolderResult() {
        return listFolderResult;
    }

    public void setListFolderResult(ListFolderResult listFolderResult) {
        this.listFolderResult = listFolderResult;
     }


}


