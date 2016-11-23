package a.b.c;

import com.google.gson.annotations.SerializedName;

public enum LoginStatus {

    @SerializedName("ok")
    OK,

    @SerializedName("fail")
    FAIL,

    @SerializedName("alrlc")
    ALREADY_LOGGED_CORRECT,

    @SerializedName("alrle")
    ALREADY_LOGGED_ERROR;    

    public final NoAddedValueJSONWrapper<LoginStatus> wrapper() {
        return new NoAddedValueJSONWrapper<LoginStatus>(this);
    }
}

