package TemaTest.Posts;
import TemaTest.Comment.Comentariu;
import TemaTest.CommonUse.Likeable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Postare implements Likeable {
    private String author;
    private String content;
    private String date;
    private int likes;
    private List<Comentariu> comments;
    public int idx;
    private int nrComments;

    public Postare(String author, String content) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
        int max = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
             max++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.idx = max;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.date = dateFormat.format(new Date());
    }

    public Postare() {
    }

    public Postare(String author, String content, String date, int idx) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
        this.date = date;
        this.idx = idx;
    }

    public void addLike(Postare post, File f, String s) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
            out.println(s + "," + post.author + "," + post.idx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.likes++;
    }

    public void addPost(Postare post) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("posts.csv", true)))) {
            out.println(post.author + "," + post.content + "," + post.idx + "," + post.date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findNrLikes(Postare post) {
        int nr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("liked.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(Integer.toString(post.idx))) {
                    nr++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nr;
    }

    public List<String[]> getAllComments() {
        List<String[]> allComments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[2]) == this.idx) {
                    String[] commentInfo = {parts[0], parts[1], parts[2], parts[3]};
                    allComments.add(commentInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allComments;
    }

    public int findNrLikesComm(String id,String username) {
        int nr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("likecomments.csv"))) {
            String line;
            int idx = Integer.parseInt(id);
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(Integer.toString(idx))) {
                    nr++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nr;
    }

    public int getNrComments(){
        int nr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[4]) == this.idx) {
                    nr++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nr;
    }

    public int getLikes() {
        return this.likes;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDate() {
        return this.date;
    }

    public String getMesaj() {
        return content;
    }

    public int getComments() {
        return nrComments;
    }

    public int GetLikes() {
        return likes;
    }

    public void addLike() {
    }

    public int getIdx() {
        return idx;
    }

    public List<Postare[]> getAllPosts() {
        List<Postare[]> allPosts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Postare[] post = {new Postare(parts[0], parts[1], parts[3], Integer.parseInt(parts[2]))};
                allPosts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Postare[] p : allPosts) {
            p[0].likes = findNrLikes(p[0]);
        }
        for(Postare[] p : allPosts){
            p[0].nrComments = getNrComments();
        }
        return allPosts;
    }
}