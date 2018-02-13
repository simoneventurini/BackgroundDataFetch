package com.serte.backgroundfetch;


/**
 * Created by s.venturini on 21/01/2018.
 */

public interface Callback<T> {

    void dataLoaded(WorkResult<T> result);

}
