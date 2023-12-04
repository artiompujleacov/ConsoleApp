package TemaTest.CommonUse;
import java.io.*;

public class RewriteFile {
    public void rewriteFile(String filename, String content) {
        try {
            File tempFile = new File("myTempFile.csv");
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(content)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("{'status':'error','message':'Could not write to file'}");
            return;
        }
    }
}
