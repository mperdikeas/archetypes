package a.b.c;

import com.google.gson.annotations.SerializedName;

public enum LoginResponse {

    public LoginStatus loginStatus;
    public String      ssoSessionId;

    public LoginResponse(LoginStatus loginStatus, String ssoSessionId) {
        this.loginStatus  = loginStatus;
        this.ssoSessionId = ssoSessionId;
    }
}

