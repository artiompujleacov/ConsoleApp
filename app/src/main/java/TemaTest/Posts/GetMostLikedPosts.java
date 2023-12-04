package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.util.List;

public class GetMostLikedPosts implements Command {
    @Override
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
        Postare post = new Postare();
        List<Postare[]> allPosts = post.getAllPosts();
        for (int i = 0; i < allPosts.size(); i++) {
            for (int j = i + 1; j < allPosts.size(); j++) {
                if (allPosts.get(i)[0].getLikes() < allPosts.get(j)[0].getLikes()) {
                    Postare[] aux = allPosts.get(i);
                    allPosts.set(i, allPosts.get(j));
                    allPosts.set(j, aux);
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < allPosts.size(); i++) {
            if(i==5) break;
            Postare postEntry = allPosts.get(i)[0];
            System.out.print("{'post_id':'" + postEntry.getIdx() + "','post_text':" + postEntry.getMesaj()
                    + ",'post_date':'" + postEntry.getDate() + "','username':'" + postEntry.getAuthor()
                    + "','number_of_likes':'" + postEntry.getLikes() + "'}");

            if (i < allPosts.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println(" ]}");
    }
}
