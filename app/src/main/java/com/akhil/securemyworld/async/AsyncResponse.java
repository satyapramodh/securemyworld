package com.akhil.securemyworld.async;

/**
 * Created by akhil on 1/14/2017.
 */

public interface AsyncResponse<T> {
    void onPostTask(T output);
}
