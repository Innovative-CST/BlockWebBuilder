package com.dragon.ide.listeners;

public interface ValueListener {
    void onSubmitted(String value);
    void onError(String error);
}
