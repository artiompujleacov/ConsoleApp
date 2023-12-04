package TemaTest.Comment;
import TemaTest.CommonUse.Likeable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Comentariu implements Likeable {
    private String author;
    private String content;
    private int likes;
    public int idx;
    private String date;

    public Comentariu(String author, String content) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        int max=1;
        try (BufferedReader br = new BufferedReader(new FileReader("comments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                    max++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.idx=max;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.date = dateFormat.format(new Date());
    }

    public Comentariu(String author, String content,String date,int idx) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.date=date;
        this.idx=idx;
    }

    public void addComent(Comentariu c,File f,String username,int idx){
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
            out.println(c.getAuthor() + "," + c.getContent() + "," + c.idx + "," + c.getDate() + "," + idx);
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not write to file'}");
            return;
        }
    }

    public int findNrLikes(Comentariu c){
        int likes=0;
        try (BufferedReader br = new BufferedReader(new FileReader("likecomments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[1]) == c.idx) {
                    likes++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return likes;
    }

    public String getDate(){
        return this.date;
    }
    public String getContent() {
        return content;
    }
    public String getAuthor() {
        return author;
    }
    public int GetLikes() {
        return likes;
    }
    public void addLike(String username,int id) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("likecomments.csv", true)))) {
            out.println(username + "," + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addLike(){}
}
