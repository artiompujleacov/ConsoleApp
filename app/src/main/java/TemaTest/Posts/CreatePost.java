package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.Posts.Postare;

public class CreatePost implements Command {
    public void execute(String[] args) {
        String username = "";
        String password = "";
        String message = "";
        String[] parts, parts2, parts4;
        int exists = 0;

        if (Identifier.checkCredentials(args)) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        parts = args[1].split("[\\s']+");
        parts2 = args[2].split("[\\s']+");
        password = parts2[1];
        username = parts[1];

        if (!LoginFailed.checkLogin(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return;
        }

        if (Identifier.checkIdentifier(args)) {
            System.out.println("{'status':'error','message':'No text provided'}");
            return;
        }

        parts4 = args[3].split("-text", 2);
        message = parts4[1];

        if (message.length() > 300) {
            System.out.println("{'status':'error','message':'Post text length exceeded'}");
            return;
        }

        Postare p = new Postare(username, message);
        p.addPost(p);

        System.out.println("{'status':'ok','message':'Post added successfully'}");
    }
}