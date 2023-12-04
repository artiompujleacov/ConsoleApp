package TemaTest.User;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;
import TemaTest.CommonUse.*;

public class UnfollowByUsername implements Command {
    public void execute(String[] args) {
        String username,password = "";
        String[] parts,parts2, parts3, parts4;
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
            System.out.println("{'status':'error','message':'No username to unfollow was provided'}");
            return;
        }
        parts4 = args[3].split("[\\s']+");
        try(BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                parts3 = line.split(",");
                if (parts3[0].equals(username) && parts3[1].equals(parts4[1])) {
                    exists = 1;
                    RewriteFile rf = new RewriteFile();
                    rf.rewriteFile("following.csv", line);
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        if(exists==0){
            System.out.println("{'status':'error','message':'The username to unfollow was not valid'}");
            return;
        }
    }
}