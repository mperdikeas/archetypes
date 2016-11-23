package mjb44.mvc;


public class LoginBean {
    public LoginBean() {}
    public LoginBean(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    private String username;
    private String fullName;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Override
    public String toString() {
        return String.format("[%s]:[%s]"
                             , this.username
                             , this.fullName);
    }
}
