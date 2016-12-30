package com.honaf.phonenumber;

import java.util.Observable;

/**
 */
public class DataChanger extends Observable {
    private static DataChanger mInstance;
    private DataChanger(){

    }

    public synchronized static DataChanger getInstance(){
        if (mInstance == null){
            mInstance = new DataChanger();
        }
        return mInstance;
    }


    public void postStatus(Object entry){
        setChanged();
        notifyObservers(entry);
    }
}
