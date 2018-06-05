package com.guerzonica.app.http.exceptions;
@Deprecated
public class TriesExceedException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Create an Exception instance
     *
     * @param response last HttpURLConnection response code
     */
    public TriesExceedException(Integer code) {
        super(code.toString());
    }

}
