package com.indocyber.itsmeandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 *@author Muhammad Faisal
 *@Version 1.0
 */

public class ApiResponse<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("trace")
    private String trace;

    @SerializedName("content")
    private T content;

    public ApiResponse(int code, String status, String message, String trace, T content) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
