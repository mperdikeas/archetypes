package mjb44.mvc;

public class DAO {

    public DAO() {
        System.out.printf("\n\n**** DAO created\n");
    }

    public LoginBean checkCredentials(String username, String password) {
        if ( ("mperdikeas".equals(username)) && ("foo".equals(password)) )
            return new LoginBean(username, "Menelaus Perdikeas");
        else
            return null;
    }

}
