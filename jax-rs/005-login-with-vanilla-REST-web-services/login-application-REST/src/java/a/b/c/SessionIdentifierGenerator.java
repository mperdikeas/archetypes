package a.b.c;

// http://stackoverflow.com/a/41156/274677
import java.security.SecureRandom;
import java.math.BigInteger;

public final class SessionIdentifierGenerator {
    public SessionIdentifierGenerator() {
        System.out.printf("SessionIdentifierGenerator created\n");
    }
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}
