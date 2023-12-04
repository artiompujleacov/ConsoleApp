package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;

public class LikePost implements Command {
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
        int idfile;
        if (!LoginFailed.checkLogin(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return;
        }
        if(Identifier.checkIdentifier(args)) {
            System.out.println("{'status':'error','message':'No post identifier to like was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        int idsearched=Integer.parseInt(parts3[1]);
        if(idsearched < 1){
            System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("liked.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                idfile=Integer.parseInt(parts4[2]);
                if (idfile == idsearched && parts4[0].equals(username)){
                    System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                idfile=Integer.parseInt(parts4[2]);
                if (idfile == idsearched && parts4[0] != username){
                    exists = 1;
                    Postare post = new Postare(parts4[0], parts4[1], parts4[3], idfile);
                    File f=new File("liked.csv");
                    post.addLike(post,f, username);
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                } else{
                    System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    if (exists == 0) {
            System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
        }
    }
}
