package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weiming on 11/11/16.
 */

public class LoginTokenResponse {
    @SerializedName("token")
    String token;

    public LoginTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
