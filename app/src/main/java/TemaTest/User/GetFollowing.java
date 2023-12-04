package TemaTest.User;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetFollowing implements Command {
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

        try(BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                if (parts4[0].equals(username)) {
                    exists = 1;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(exists == 0) {
            System.out.println("{'status':'error','message':'The username to list followers was not valid'}");
            return;
        }
        System.out.print("{'status':'ok','message': [");
        try (BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                if (parts4[0].equals(username)) {
                    if (!first) {
                        System.out.print(", ");
                    }
                    System.out.print("'" + parts4[1] + "'");
                    first = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("]}");
    }
}
