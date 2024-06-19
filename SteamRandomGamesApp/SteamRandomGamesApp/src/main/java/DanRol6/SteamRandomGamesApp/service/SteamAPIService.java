package DanRol6.SteamRandomGamesApp.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SteamAPIService {
    private static final String API_URL = "";

    //Create a connection to the API by creating a connection.
    //If the connection is good then it calls the readAPIResponse method which reads the information returned from the API
    public void fetchSteamGamesFromAPI() throws IOException {
        URL url = new URL(API_URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connection is made. If code is 200 then connection is good.
        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            readAPIResponse(conn);
        }
    }

    //Reads the response from the API and appends the entire JSON information obtained from the server
    //to a string builder
    private void readAPIResponse(HttpURLConnection connection) throws IOException {
        //Create a buffered reader to read the input stream from the HTTP Connection
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //Holds the response
        String inputLine;
        StringBuilder response = new StringBuilder();

        //Reads each value and appends it to a stringbuilder
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        //Closes the connection
        in.close();

        //Locally saves the result of the API
        WriteToFile(response.toString());

    }


    //Save JSON object to txt file locally
    public void WriteToFile(String data) {
        String filePath = "ListOfSteamGames.json";

        try {
            //Create a filewriter object with a path
            FileWriter writer = new FileWriter(filePath);

            //write the content to the file
            writer.write(data);

            //Close the filewriter to release resources
            writer.close();

        } catch (IOException e) {
            //Handles the exception that might occur
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String ReadFile() {
        //Path to the file
        String filePath = "ListOfSteamGames.json";
        StringBuilder contentBuilder = new StringBuilder();

        try {
            //FileReader object with the specified path
            FileReader fileReader = new FileReader(filePath);

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

}
