package mjb44.mvc;

public class HackInfo {

    public HackInfo(String ipAddress, String userAgent) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    private String ipAddress;
    private String userAgent;
    public String getIpAddress() {
        return this.ipAddress;
    }
    public String getUserAgent() {
        return this.userAgent;
    }
}
