package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.CommonUse.RewriteFile;

import java.io.*;

public class UnlikePost implements Command {
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
        if(Identifier.checkIdentifier(args)) {
            System.out.println("{'status':'error','message':'No post identifier to unlike was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        int idsearched=Integer.parseInt(parts3[1]);
        if(idsearched < 1){
            System.out.println("{'status':'error','message':'No post identifier to unlike was provided'}");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("liked.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                int idfile=Integer.parseInt(parts4[2]);
                if (idfile == idsearched && parts4[0].equals(username)){
                    exists = 1;
                    RewriteFile rf = new RewriteFile();
                    rf.rewriteFile("liked.csv", line);
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(exists == 0){
            System.out.println("{'status':'error','message':'The post identifier to unlike was not valid'}");
            return;
        }

    }

}
