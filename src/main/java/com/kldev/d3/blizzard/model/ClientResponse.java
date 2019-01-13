package com.kldev.d3.blizzard.model;

public class ClientResponse<R> {

    private int status;
    private R result;

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
