package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.util.List;

public class GetMostFollowedUsers implements Command {
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
        List <Utilizator> allUsers = user.getAllUsers();
        for (int i = 0; i < allUsers.size() - 1; i++) {
            for (int j = i + 1; j < allUsers.size(); j++) {
                if (allUsers.get(i).getNrFollowers() < allUsers.get(j).getNrFollowers()) {
                    Utilizator aux = allUsers.get(i);
                    allUsers.set(i, allUsers.get(j));
                    allUsers.set(j, aux);
                }
            }
        }
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < allUsers.size(); i++) {
            if(i==5) break;
            System.out.print("{'username':'" + allUsers.get(i).getUsername() + "','number_of_followers':'" + allUsers.get(i).getNrFollowers() + "'}");
            if (i != 4 && i != allUsers.size() -1 ) {
                System.out.print(",");
            }
        }
        System.out.println(" ]}");
    }
}
