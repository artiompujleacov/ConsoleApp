package TemaTest.User;
import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;

import java.io.*;

public class CreateUser implements Command {
    @Override
    public void execute(String[] args) {
        String username = "";
        String password = "";

        if (args.length == 1) {
            System.out.println("{'status':'error','message':'Please provide username'}");
            return;
        }

        String argument = args[1];
        String[] parts = argument.split("[\\s']+");

        if (!parts[0].equals("-u")) {
            System.out.println("{'status':'error','message':'Please provide username'}");
            return;
        } else {
            username = parts[1];
        }

        if (Identifier.checkCredentials(args)) {
            System.out.println("{'status':'error','message':'Please provide password'}");
            return;
        }

        String argument2 = args[2];
        String[] parts2 = argument2.split("[\\s']+");

        if (!parts2[0].equals("-p")) {
            System.out.println("{'status':'error','message':'Please provide password'}");
            return;
        } else {
            password = parts2[1];
        }

        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts3 = line.split(",");
                if (parts3[0].equals(username)) {
                    System.out.println("{'status':'error','message':'User already exists'}");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read from file'}");
            return;
        }

        Utilizator u = new Utilizator(username, password);
        u.addUser(u, new File("users.csv"));
        System.out.println("{'status':'ok','message':'User created successfully'}");
    }
}
