package DanRol6.SteamRandomGamesApp.service;

import DanRol6.SteamRandomGamesApp.model.GameModel;
import DanRol6.SteamRandomGamesApp.util.ReadFromFile;
import DanRol6.SteamRandomGamesApp.util.WriteToFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private SteamAPIService steamAPIService;
    private ReadFromFile readFromFile = new ReadFromFile("ListOfSteamGames.json");



    public String fetchAndStoreGames() throws IOException {

        //Calls the Steam API and stores the results in a String
        String storedSteamGames = steamAPIService.fetchSteamGamesFromAPI();

        //Locally saves the result of the API
        WriteToFile writeToFile = new WriteToFile(storedSteamGames, "ListOfSteamGames.json");
        writeToFile.write();

        //Gets the information from the file locally and displays it
        System.out.println(storedSteamGames);
        return storedSteamGames;
    }

    public List<GameModel> getAllGames() {
        // Gets the information from the file locally
        String storedSteamGames = readFromFile.read();

        //Gets the JSON Object
        JSONObject jsonObject = new JSONObject(storedSteamGames);

        //Gets "applist" from the JSON object
        JSONObject appList = jsonObject.getJSONObject("applist");

        //Gets "apps" from the appList object
        JSONArray apps = appList.getJSONArray("apps");

        //Variable will store the game objects which will contain the game information
        List<GameModel> listOfGames = new ArrayList<>();

        //Add each game object to the list
        for (int i = 0; i < apps.length(); i++) {
            GameModel gameModel = new GameModel();
            JSONObject steamGameObject = apps.getJSONObject(i);
            gameModel.setId(steamGameObject.getLong("appid"));
            gameModel.setName(steamGameObject.getString("name"));
            listOfGames.add(gameModel);
        }

        return listOfGames;
    }

    public String getGameByIndex(int index) {
        List<GameModel> listOfGames = getAllGames();

        //If the index is out of bounds will return corresponding error.
        if (index < 0 || index > listOfGames.size()) {
            throw new IndexOutOfBoundsException("Index specified is out of bounds");
        }

        //Try to get the index of the list of games, if available, will return the game ID and the game name
        return ("ID: " + listOfGames.get(index).getId() + " Name: " + listOfGames.get(index).getName());
    }

}
