package cd;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private final String filename;
    public CsvWriter(String filename){ this.filename = filename; }
    public void writeLine(String line){
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}