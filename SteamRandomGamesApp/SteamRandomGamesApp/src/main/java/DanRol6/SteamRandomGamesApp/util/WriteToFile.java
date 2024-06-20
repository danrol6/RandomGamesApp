package DanRol6.SteamRandomGamesApp.util;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    private String data;
    private String fileName;

    public WriteToFile(String data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }

    public void write() {

        try {
            //Create a filewriter object with a path
            FileWriter writer = new FileWriter(this.fileName);

            //write the content to the file
            writer.write(this.data);

            //Close the filewriter to release resources
            writer.close();

        } catch (IOException e) {
            //Handles the exception that might occur
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
