import org.apache.shiro.crypto.hash.Sha256Hash;
import java.io.*;
public class FooMain {

    private static String hash256(String input, String salt, int iterations) {
        Sha256Hash hash = new Sha256Hash(input,salt, iterations);
        return hash.toHex();
    }

    static final int NO_HARDCODEDUSERS=4;

    public static void main(String args[]) throws Exception {
        String input = args[0];
        String salt  = args[1];
        int iterations = Integer.valueOf(args[2]);
        PrintWriter pw  =new PrintWriter ( new File ( args[3] ) ) ;
        int users      = Integer.valueOf(args[4]);
        Sha256Hash hash = new Sha256Hash(input,salt, iterations);
        // System.out.printf(hash.toHex());
        
        pw.printf("DELETE FROM ss_users_roles;\n");
        pw.printf("DELETE FROM ss_roles_system_roles;\n");
        pw.printf("DELETE FROM ss_system_roles_privileges;\n");
        pw.printf("DELETE FROM ss_users;\n");
        pw.printf("DELETE FROM ss_roles;\n");
        pw.printf("DELETE FROM ss_system_roles;\n");
        pw.printf("DELETE FROM ss_system_roles_privileges;\n");
        pw.printf("DELETE FROM ss_system_privileges;\n");
        pw.printf("DELETE FROM ss_system_elements;\n");
        pw.printf("DELETE FROM ss_subscribers;\n");
        pw.printf("\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_subscribers(subs_id, enst_id) VALUES (1, 0);\n");


        pw.printf("\nINSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_nickname, user_is_administrator, user_registration_date)\nVALUES (1, 1, 0, 'bill', 'a603d7fea04d4ae942b9828c41c0f19761d8868a50046c53664a7b69bf357286', 'foo', 'Bill', 'Gates', 'Βασιλάκης', false, now());\n");
        pw.printf("\nINSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_nickname, user_is_administrator, user_registration_date)\nVALUES (2, 1, 0, 'james', 'd50ffcd0b397b3ddf3814b9256c8f3149ee5517d85b13bc9c967217a828483c0', 'foo', 'James', 'Stewart', 'jamie', false, now());\n");
        pw.printf("\nINSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_nickname, user_is_administrator, user_registration_date)\nVALUES (3, 1, 0, 'mary', '553277b52de90ca45ed8a689a24019ba1eb31bee9555806c0cb8a1cc06c4feb5', 'foo', 'Mary', 'Bones', 'maryb', false, now());\n");
        pw.printf("\nINSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_nickname, user_is_administrator, user_registration_date)\nVALUES (4, 1, 0, 'jack', '256179882f18c58616eeb27e28d04ccb31ccc6bb44a1a977a314415201682a2f', 'foo', 'Jack', 'Paranoid', 'jackrip', false, now());\n\n\n");



        for (int i_ = NO_HARDCODEDUSERS ; i_ < NO_HARDCODEDUSERS+users ; i_++) {
            int i = i_ - NO_HARDCODEDUSERS+1;
            System.out.println(String.format("working on user %d", i));
            String core     = "user"+i;
            String username = "user"+i+"@test.gr";
            String password = core;
            pw.printf("INSERT INTO ss_users(user_id, subs_id, enst_id, user_email, user_password, user_password_salt, user_firstname, user_surname, user_nickname, user_is_administrator, user_registration_date) VALUES (%d, 1, 0, '%s', '%s', 'foo', 'User%d', 'Something', 'usie', false, now());\n", i_+1, username, hash256(password, "foo", iterations), i);
        }

        pw.printf("INSERT INTO ss_system_elements      (syel_id, syet_id, modu_id, syel_description) VALUES(1, 1, NULL, NULL);\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_system_privileges    (sypr_id, syel_id, sypr_name) VALUES ( 1, 1, 'can-delete-cashflows');\n");
        pw.printf("INSERT INTO ss_system_privileges    (sypr_id, syel_id, sypr_name) VALUES ( 2, 1, 'can-create-cashflows');\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (1, 'deleter'  , NULL);\n");
        pw.printf("INSERT INTO ss_system_roles         (syro_id, syro_name, syro_description) VALUES (2, 'creator' , NULL);\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (1,  1);\n");
        pw.printf("INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (2,  1);\n");
        pw.printf("INSERT INTO ss_system_roles_privileges (syro_id, sypr_id) VALUES (2,  2);\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_roles (role_id, subs_id, enst_id, role_caption) VALUES (1, 1, 0, 'active-user-role' );\n");
        pw.printf("\n");
        pw.printf("INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (1, 1);\n");
        pw.printf("INSERT INTO ss_roles_system_roles (role_id, syro_id) VALUES (1, 2);\n");
        pw.printf("\n");
        for (int i_ = NO_HARDCODEDUSERS ; i_ < users ; i_++) {
            pw.printf("INSERT INTO ss_users_roles(user_id, role_id) VALUES (%d, 1);\n", i_);
        }

        pw.printf("\n\nINSERT INTO ss_users_roles(user_id, role_id) VALUES (1, 1);\n");
        pw.printf("INSERT INTO ss_users_roles(user_id, role_id) VALUES (2, 1);\n");

        pw.close();
    }
}
