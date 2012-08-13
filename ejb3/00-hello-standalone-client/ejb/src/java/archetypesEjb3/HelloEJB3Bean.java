package archetypesEjb3;

import javax.ejb.Stateless;
import javax.ejb.Remote;

@Stateless
@Remote(IHelloEJB3.class)
public class HelloEJB3Bean implements IHelloEJB3 {
    @Override
    public String sayMessage(String msg) { return "I was asked to relay the message: \""+msg+"\""; }
}