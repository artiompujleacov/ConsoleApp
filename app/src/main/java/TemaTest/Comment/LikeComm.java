package TemaTest.Comment;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;

public class LikeComm implements Command {
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
        if (Identifier.checkIdentifier(args)) {
            System.out.println("{'status':'error','message':'No comment identifier to like was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        int idsearched = Integer.parseInt(parts3[1]);
        if (idsearched < 1) {
            System.out.println("{'status':'error','message':'The comment identifier to like was not valid'}");
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader("likecomments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                int id = Integer.parseInt(parts4[1]);
                if (id == idsearched && parts4[0].equals(username)) {
                    System.out.println("{'status':'error','message':'The comment identifier to like was not valid'}");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                int id = Integer.parseInt(parts4[2]);
                if (id == idsearched) {
                    exists = 1;
                    Comentariu comentariu = new Comentariu(parts4[0], parts4[1],parts4[3],id);
                    comentariu.addLike(username,idsearched);
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exists == 0) {
            System.out.println("{'status':'error','message':'The comment identifier to like was not valid'}");
            return;
        }
    }
}