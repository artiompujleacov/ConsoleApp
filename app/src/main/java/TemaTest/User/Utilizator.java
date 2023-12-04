package TemaTest.User;
import TemaTest.Comment.Comentariu;
import TemaTest.Posts.Postare;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilizator {
    private String username;
    private String password;
    private List<Postare> postari;
    private int nrFollowers;
    private List <Comentariu> comentarii;
    protected int totalLikes;


    protected Utilizator(String username, String password) {
        this.username = username;
        this.password = password;
        this.postari = new ArrayList<>();
        this.comentarii = new ArrayList<>();
    }

    protected Utilizator(){}

    protected void addUser(Utilizator user, File file) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(user.getUsername() + "," + user.getPassword());
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not write to file'}");
            return;
        }
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected List<Postare> getPostari() {
        return postari;
    }

    protected List<Comentariu> getComentarii() {return comentarii; }

    protected void loadPostsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String postUsername = parts[0];
                String postMessage = parts[1];
                int postId = Integer.parseInt(parts[2]);
                if (postUsername.equals(username)) {
                    Postare post = new Postare(postUsername, postMessage);
                    post.idx=postId;
                    postari.add(post);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("{'status':'error','message':'Could not read posts from file'}");
        }
    }

    protected void loadCommentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String commentUsername = parts[0];
                String commentMessage = parts[1];
                int commentId = Integer.parseInt(parts[2]);
                if (commentUsername.equals(username)) {
                    Comentariu comment = new Comentariu(commentUsername, commentMessage ,parts[3],commentId);
                    comment.idx=commentId;
                    comentarii.add(comment);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("{'status':'error','message':'Could not read comments from file'}");
        }
    }

    protected int findNrFollowers(){
        int nr=0;
        try (BufferedReader br = new BufferedReader(new FileReader("following.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[1].equals(this.username)){
                    nr++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nr;
    }

    protected List<Utilizator> getAllUsers() {
        List<Utilizator> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Utilizator user = new Utilizator(parts[0], parts[1]);
                user.nrFollowers=user.findNrFollowers();
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not read users from file'}");
        }
        return users;
    }

    protected int getNrFollowers() {
        return nrFollowers;
    }
}

