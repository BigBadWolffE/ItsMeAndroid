package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 *@author Muhammad Faisal
 *@Version 1.0
 */

public class ApiResponse<T> {

    private int code;

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("trace")
    private String trace;

    @SerializedName("datas")
    private T content;

    public ApiResponse(int code, int status, String message, String trace, T content) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.trace = trace;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
