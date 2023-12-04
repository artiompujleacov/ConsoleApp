package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;

public class GetFollowers implements Command {
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
        if(args.length == 3) {
            System.out.println("{'status':'error','message':'No username to list followers was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        String username2 = parts3[1];
        try(BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                if (parts4[0].equals(username2)) {
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
                if (parts4[1].equals(username2)) {
                    if (!first) {
                        System.out.print(", ");
                    }
                    System.out.print("'" + parts4[0] + "'");
                    first = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("]}");
    }
}
