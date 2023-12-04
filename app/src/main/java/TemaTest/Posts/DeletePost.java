package TemaTest.Posts;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.CommonUse.RewriteFile;

import java.io.*;

public class DeletePost implements Command {
    public void execute(String[] args) {
        String username="";
        String password="";
        String[] parts,parts2, parts3, parts4;
        int exists=0;
        if (Identifier.checkCredentials(args)) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        parts = args[1].split("[\\s']+");
        parts2 = args[2].split("[\\s']+");
        password=parts2[1];
        username=parts[1];
        if (!LoginFailed.checkLogin(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return;
        }
        if (args.length == 3 ) {
            System.out.println("{'status':'error','message':'No identifier was provided'}");
            return;
        }
        parts4 = args[3].split("[\\s']+");
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                parts3 = line.split(",");
                int idx = Integer.parseInt(parts3[2]);
                if (parts3[0].equals(username) && idx == Integer.parseInt(parts4[1])) {
                    exists=1;
                    RewriteFile rf = new RewriteFile();
                    rf.rewriteFile("posts.csv", line);
                    System.out.println("{'status':'ok','message':'Post deleted successfully'}");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        if(exists==0){
            System.out.println("{'status':'error','message':'The identifier was not valid'}");
            return;
        }
    }
}
