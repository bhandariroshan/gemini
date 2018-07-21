package com.example.rbhandari.datasyncapplication.requesthandler;

public interface OnEventListener<T> {
    public void onSuccess(T object);
    public void onFailure(Exception e);
}
