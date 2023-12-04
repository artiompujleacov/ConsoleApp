package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;

public class FollowByUsername implements Command {
    public void execute(String[] args) {
        String username = "";
        String password = "";
        String[] parts,parts2, parts3, parts4;
        int exists = 0;
        if (Identifier.checkCredentials(args)) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        parts = args[1].split("[\\s']+");
        parts2 = args[2].split("[\\s']+");
        password = parts2[1];
        username = parts[1];
        if (!LoginFailed.checkLogin(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return;
        }
        if (Identifier.checkIdentifier(args)) {
            System.out.println("{'status':'error','message':'No username to follow was provided'}");
            return;
        }
        parts4 = args[3].split("[\\s']+");
        try(BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                parts3 = line.split(",");
                if (parts3[0].equals(username) && parts3[1].equals(parts4[1])) {
                    exists = 1;
                    System.out.println("{'status':'error','message':'The username to follow was not valid'}");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                parts3 = line.split(",");
                if (parts4[1].equals(parts3[0])) {
                    if(parts4[1].equals(username)){
                        System.out.println("{'status':'error','message':'You cannot follow yourself'}");
                        return;
                    }
                    exists = 1;
                    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("following.csv", true)))) {
                        out.println(username + "," + parts4[1]);
                    } catch (IOException e) {
                        System.out.println("{'status':'error','message':'Could not write to file'}");
                        return;
                    }
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }
        if(exists==0){
            System.out.println("{'status':'error','message':'The username to follow was not valid'}");
            return;
        }
    }
}