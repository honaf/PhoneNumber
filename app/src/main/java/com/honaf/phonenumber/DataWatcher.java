package com.honaf.phonenumber;

import java.util.Observable;
import java.util.Observer;

/**
 */
public abstract class DataWatcher implements Observer {
    @Override
    public void update(Observable observable, Object data) {
            notifyUpdate(data);
    }

    public abstract void notifyUpdate(Object data);
}
