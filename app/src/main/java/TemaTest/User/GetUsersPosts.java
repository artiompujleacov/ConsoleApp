package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.Posts.Postare;

import java.io.*;
import java.util.List;

public class GetUsersPosts implements Command {
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
            System.out.println("{'status':'error','message':'No username to list posts was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        String user = parts3[1];
        try(BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                if(parts4[0].equals(user)) {
                    exists = 1;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        if(exists == 0) {
            System.out.println("{'status':'error','message':'The username to list posts was not valid'}");
            return;
        }
        exists = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                if(parts4[0].equals(username) && parts4[1].equals(user)) {
                    exists = 1;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        if(exists == 0) {
            System.out.println("{'status':'error','message':'The username to list posts was not valid'}");
            return;
        }
        Utilizator u = new Utilizator(user, "");
        u.loadPostsFromFile();
        List<Postare> userPosts = u.getPostari();
        for(int i = 0; i < userPosts.size() - 1; i++) {
            for(int j = i + 1; j < userPosts.size(); j++) {
                if(userPosts.get(i).getDate().compareTo(userPosts.get(j).getDate()) < 0) {
                    Postare aux = userPosts.get(i);
                    userPosts.set(i, userPosts.get(j));
                    userPosts.set(j, aux);
                }
                else if(userPosts.get(i).getDate().compareTo(userPosts.get(j).getDate()) == 0) {
                    if(userPosts.get(i).getIdx() < userPosts.get(j).getIdx()) {
                        Postare aux = userPosts.get(i);
                        userPosts.set(i, userPosts.get(j));
                        userPosts.set(j, aux);
                    }
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < userPosts.size(); i++) {
            Postare post = userPosts.get(i);
            System.out.print("{'post_id':'" + post.getIdx() + "', 'post_text':" + post.getMesaj() + ", 'post_date':'" + post.getDate() + "'}");
            if (i != userPosts.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }
}
