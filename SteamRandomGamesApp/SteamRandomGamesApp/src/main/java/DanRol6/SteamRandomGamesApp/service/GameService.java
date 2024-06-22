package DanRol6.SteamRandomGamesApp.service;

import DanRol6.SteamRandomGamesApp.util.ReadFromFile;
import DanRol6.SteamRandomGamesApp.util.WriteToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GameService {

    @Autowired
    private SteamAPIService steamAPIService;
//    private final ReadFromFile readFromFile = new ReadFromFile("ListOfSteamGames.json");

    public String fetchAndStoreGames() throws IOException{

        //Calls the Steam API and stores the results in a String
        String storedSteamGames = steamAPIService.fetchSteamGamesFromAPI();

        //Locally saves the result of the API
        WriteToFile writeToFile = new WriteToFile(storedSteamGames, "ListOfSteamGames.json");
        writeToFile.write();

        //Gets the information from the file locally and displays it
        System.out.println(storedSteamGames);
        return storedSteamGames;
    }

}
