package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.Posts.Postare;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetPostsFollowing implements Command {
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
        List <Utilizator> following = new ArrayList<Utilizator>();
        try(BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts3 = line.split(",");
                if (parts3[0].equals(username)) {
                    Utilizator user = new Utilizator(parts3[1], "");
                    following.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (following.size() == 0) {
            System.out.println("{'status':'error','message':'You are not following anyone'}");
            return;
        }
        List <Postare> posts = new ArrayList<Postare>();
        try(BufferedReader br = new BufferedReader(new FileReader("posts.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                for (int i = 0; i < following.size(); i++) {
                    if (parts4[0].equals(following.get(i).getUsername())) {
                        int id=Integer.parseInt(parts4[2]);
                        Postare post = new Postare(parts4[0], parts4[1], parts4[3],id);
                        posts.add(post);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < posts.size(); i++) {
            for (int j = i + 1; j < posts.size(); j++) {
                if (posts.get(i).getDate().compareTo(posts.get(j).getDate()) < 0) {
                    Postare aux = posts.get(i);
                    posts.set(i, posts.get(j));
                    posts.set(j, aux);
                }
                if (posts.get(i).getDate().compareTo(posts.get(j).getDate()) == 0) {
                    if (posts.get(i).getIdx() < posts.get(j).getIdx()) {
                        Postare aux = posts.get(i);
                        posts.set(i, posts.get(j));
                        posts.set(j, aux);
                    }
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (Postare post : posts) {
            System.out.print("{'post_id':'" + post.getIdx() + "','post_text':" + post.getMesaj() + ",'post_date':'" + post.getDate() + "','username':'" + post.getAuthor() + "'}");
            if (posts.indexOf(post) != posts.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }
}
