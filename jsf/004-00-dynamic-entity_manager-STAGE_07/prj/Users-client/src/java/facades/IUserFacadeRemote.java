package facades;
import entities.User;
import javax.ejb.Remote;

@Remote
public interface IUserFacadeRemote {
    public User find(Object name);
    public String boo(String msg);
}