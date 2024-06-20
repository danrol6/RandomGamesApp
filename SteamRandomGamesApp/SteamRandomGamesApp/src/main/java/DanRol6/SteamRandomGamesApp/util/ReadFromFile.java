package DanRol6.SteamRandomGamesApp.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {
    private String fileName;

    public ReadFromFile(String fileName){
        this.fileName = fileName;
    }
    public String read(){
        StringBuilder contentBuilder = new StringBuilder();

        try {
            //FileReader object with the specified path
            FileReader fileReader = new FileReader(this.fileName);

            //wrap the fileRreader in a bufferedReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Read each line of the file
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            //Close the bufferedReader and FileReader
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.err.println("An error occured while reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        //Return contents as string
        return contentBuilder.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
