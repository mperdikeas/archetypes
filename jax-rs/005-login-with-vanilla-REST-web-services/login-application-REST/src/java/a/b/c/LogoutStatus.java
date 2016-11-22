package a.b.c;

import com.google.gson.annotations.SerializedName;

public enum LogoutStatus {

    @SerializedName("ok")
    OK,

    @SerializedName("fail-not-logged-in")
    FAIL_NOT_LOGGED_IN;

    public final NoAddedValueJSONWrapper<LogoutStatus> wrapper() {
        return new NoAddedValueJSONWrapper<LogoutStatus>(this);
    }
}

