package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.util.List;

public class GetMostCommentedPosts implements Command {
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
        for (int i = 0; i < allPosts.size() - 1; i++) {
            for (int j = i + 1; j < allPosts.size(); j++) {
                if (allPosts.get(i)[0].getNrComments() < allPosts.get(j)[0].getNrComments()) {
                    Postare[] aux = allPosts.get(i);
                    allPosts.set(i, allPosts.get(j));
                    allPosts.set(j, aux);
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < allPosts.size(); i++) {
            if(i==5) break;
            System.out.print("{'post_id':'" + allPosts.get(i)[0].getIdx() + "','post_text':" + allPosts.get(i)[0].getMesaj() + ",'post_date':'" + allPosts.get(i)[0].getDate() + "','username':'" + allPosts.get(i)[0].getAuthor() + "','number_of_comments':'" + allPosts.get(i)[0].getNrComments() + "'}");
            if (i != 4 && i != allPosts.size() -1 ) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }
}
