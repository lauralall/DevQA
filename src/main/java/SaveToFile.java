import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to write results to a .txt file
 */
public class SaveToFile {

    private BufferedWriter bufferedWriter;

    /**
     * Initiates BufferedWriter with the given filename
     * @param fileName results will be written to this file
     */
    public SaveToFile(String fileName){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write line of text to file
     * @param text line of text to write
     */
    public void write(String text) {
        try {
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close BufferedWriter
     */
    public void close() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}