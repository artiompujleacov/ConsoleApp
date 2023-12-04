package TemaTest.User;

import TemaTest.Comment.Comentariu;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;
import TemaTest.Posts.Postare;

import java.util.List;

public class GetMostLikedUsers implements Command {
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
        Utilizator user = new Utilizator();
        List<Utilizator> allUsers = user.getAllUsers();
        for (Utilizator u : allUsers) {
            u.loadPostsFromFile();
            u.loadCommentsFromFile();
        }

        for (Utilizator u : allUsers) {
            for(Postare p : u.getPostari()){
                int likes = p.findNrLikes(p);
                u.totalLikes+=likes;
            }
        }
        for (Utilizator u : allUsers) {
            for(Comentariu c : u.getComentarii()){
                int likes = c.findNrLikes(c);
                u.totalLikes+=likes;
            }
        }
        for (int i = 0; i < allUsers.size() - 1; i++) {
            for (int j = i + 1; j < allUsers.size(); j++) {
                if (allUsers.get(i).totalLikes < allUsers.get(j).totalLikes) {
                    Utilizator aux = allUsers.get(i);
                    allUsers.set(i, allUsers.get(j));
                    allUsers.set(j, aux);
                }
                else if(allUsers.get(i).totalLikes == allUsers.get(j).totalLikes){
                    if(allUsers.get(i).getUsername().compareTo(allUsers.get(j).getUsername()) > 0){
                        Utilizator aux = allUsers.get(i);
                        allUsers.set(i, allUsers.get(j));
                        allUsers.set(j, aux);
                    }
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < allUsers.size(); i++) {
            if(i==5) break;
            if (i == allUsers.size() - 1 || i == 4) {
                System.out.print("{'username':'" + allUsers.get(i).getUsername() + "','number_of_likes':'" + allUsers.get(i).totalLikes + "'}");
            } else {
                System.out.print("{'username':'" + allUsers.get(i).getUsername() + "','number_of_likes':'" + allUsers.get(i).totalLikes + "'},");
            }
        }
        System.out.println("]}");
    }
}
