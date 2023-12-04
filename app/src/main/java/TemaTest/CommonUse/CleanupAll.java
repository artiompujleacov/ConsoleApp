package TemaTest.CommonUse;

import java.io.*;
public class CleanupAll implements Command {
    @Override
    public void execute(String[] args) {
        Cleanup c = new Cleanup();
        c.clear(new File("users.csv"));
        c.clear(new File("posts.csv"));
        c.clear(new File("following.csv"));
        c.clear(new File("liked.csv"));
        c.clear(new File("comments.csv"));
        c.clear(new File("likecomments.csv"));
        c.clear(new File("myTempFile.csv"));
        System.out.println("{'status':'ok','message':'“Operation executed successfully'}");
    }
}
