package TemaTest.Posts;

import TemaTest.CommonUse.Command;
import TemaTest.CommonUse.Identifier;
import TemaTest.CommonUse.LoginFailed;

import java.io.*;
import java.util.List;

public class GetPostsDetails implements Command {
    public void execute(String[] args) {
        String username, password = "";
        Postare post=null;
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
            System.out.println("{'status':'error','message':'No post identifier was provided'}");
            return;
        }
        String[] parts3 = args[3].split("[\\s']+");
        int idsearched = Integer.parseInt(parts3[1]);
        if (idsearched < 1) {
            System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts5 = line.split(",");
                int id = Integer.parseInt(parts5[2]);
                if (id == idsearched) {
                    exists = 1;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exists == 0) {
            System.out.println("{'status':'error','message':'The post identifier was not valid'}");
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts4 = line.split(",");
                int id = Integer.parseInt(parts4[2]);
                if (id == idsearched) {
                    post = new Postare(parts4[0], parts4[1], parts4[3], id);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int numberOfLikes = post.findNrLikes(post);
        List<String[]> comments = post.getAllComments();
        for(int i=0;i<comments.size();i++){
            for(int j=i+1;j<comments.size();j++){
                if(comments.get(i)[1].compareTo(comments.get(j)[1])>0){
                    String[] aux=comments.get(i);
                    comments.set(i,comments.get(j));
                    comments.set(j,aux);
                }
                else if(comments.get(i)[1].compareTo(comments.get(j)[1])==0){
                    if(Integer.parseInt(comments.get(i)[0])>Integer.parseInt(comments.get(j)[0])){
                        String[] aux=comments.get(i);
                        comments.set(i,comments.get(j));
                        comments.set(j,aux);
                    }
                }
            }
        }
        System.out.print("{'status':'ok','message': [{'post_text':" + post.getMesaj() + ",'post_date':'" + post.getDate() + "','username':'" + post.getAuthor() + "','number_of_likes':'" + numberOfLikes + "','comments': [");
        for (int i = 0; i < comments.size(); i++) {
            System.out.print("{'comment_id':'" + comments.get(i)[2] + "','comment_text':'" + comments.get(i)[1] + "','comment_date':'" + comments.get(i)[3] + "','username':'" + comments.get(i)[0] + "','number_of_likes':'" + post.findNrLikesComm(comments.get(i)[2],comments.get(i)[0]) + "'}] ");

            // Add a comma if it's not the last comment
            if (i < comments.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}] }");
    }
}
