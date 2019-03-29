package com.beautycoder.pflockscreen.security;

public class PFResult<T> {

    private PFSecurityError mError = null;
    private T mResult = null;

    PFResult(PFSecurityError mError) {
        this.mError = mError;
    }

    PFResult(T result) {
        mResult = result;
    }

    public PFSecurityError getError() {
        return mError;
    }

    public T getResult() {
        return mResult;
    }
}
