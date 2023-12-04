package TemaTest.CommonUse;

import java.io.*;

public class Cleanup {
  public void clear(File file){
    try {
      PrintWriter writer = new PrintWriter(file);
      writer.print("");
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("{'status':'error','message':'Could not write to file'}");
      return;
    }
  }
}
