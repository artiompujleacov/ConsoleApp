package TemaTest.Comment;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.Posts.Postare;

import java.io.*;

public class CommentPost implements Command {
    public void execute(String[] args) {
        String username, password = "";
        int exists = 0;
        if (Identifier.checkCredentials(args)) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        String[] parts = args[1].split("[\\s']+");
        String[] parts2 = args[2].split("[\\s']+");
        password = parts2[1];
        username = parts[1];
        if (!LoginFailed.checkLogin(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return;
        }
        if(args.length < 5){
            System.out.println("{'status':'error','message':'No text provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        int idsearched = Integer.parseInt(parts3[1]);
        if (idsearched < 1) {
            System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
            return;
        }
        String[] parts4 = args[4].split("-text", 2);
        String messageWithQuotes = parts4[1];
        String message = messageWithQuotes.substring(2, messageWithQuotes.length() - 1);

        if (message.length() > 300) {
            System.out.println("{'status':'error','message':'Comment text length exceeded'}");
            return;
        }
        Comentariu c = new Comentariu(username,message);
        c.addComent(c,new File("comments.csv"),username,idsearched);
        System.out.println("{'status':'ok','message':'Comment added successfully'}");
    }

}
