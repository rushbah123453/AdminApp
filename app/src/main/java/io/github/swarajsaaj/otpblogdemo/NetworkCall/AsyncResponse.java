package io.github.swarajsaaj.otpblogdemo.NetworkCall;

import org.json.JSONException;

/**
 * Created by rushabh on 12/8/18.
 */

public interface AsyncResponse {

    void AsyncFinnished(String output) throws JSONException;

}
