package DanRol6.SteamRandomGamesApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SteamAPIService {
    @Value("${api.key}")
    private String apiKey;

    //Create a connection to the API by creating a connection.
    //If the connection is good then it calls the readAPIResponse method which reads the information returned from the API
    public String fetchSteamGamesFromAPI() throws IOException {
        URL url = new URL(apiKey);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connection is made. If code is 200 then connection is good.
        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            return readAPIResponse(conn);
        }
    }

    //Reads the response from the API and appends the entire JSON information obtained from the server
    //to a string builder
    private String readAPIResponse(HttpURLConnection connection) throws IOException {
        //Create a buffered reader to read the input stream from the HTTP Connection
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //Holds the response
        String inputLine;
        StringBuilder response = new StringBuilder();

        //Reads each value and appends it to a String Builder
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        //Closes the connection
        in.close();

        return response.toString();

    }

}
