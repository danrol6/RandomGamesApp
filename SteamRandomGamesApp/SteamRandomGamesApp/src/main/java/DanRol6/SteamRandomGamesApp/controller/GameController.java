package DanRol6.SteamRandomGamesApp.controller;

import DanRol6.SteamRandomGamesApp.exception.GameNameNotFoundException;
import DanRol6.SteamRandomGamesApp.exception.IDNotFoundException;
import DanRol6.SteamRandomGamesApp.model.GameModel;
import DanRol6.SteamRandomGamesApp.service.GameService;
import DanRol6.SteamRandomGamesApp.util.ReadFromFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private final ReadFromFile readFromFile = new ReadFromFile("ListOfSteamGames.json");
    @Autowired
    private GameService gameService;

    //Get the data from the Steam API Game List
    @GetMapping("/games")
    public String getListOfGames() {
        try {
            //Calls the Steam API and returns the results
            return gameService.fetchAndStoreGames();

        } catch (IOException e) {
            System.out.println("Error fetching data from the List of Games API" + e.getMessage());
            return "Error fetching data from the List of Games API";
        }
    }


    @GetMapping("/games/index/{index}")
    public String getGameByIndex(@PathVariable("index") int index) {
        //Try to get the index of the list of games, if available, will return the game ID and the game name
        //If the index is out of bounds will return corresponding error.
        try {
            return gameService.getGameByIndex(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return "Index specified is out of bounds";
        }
    }

    @GetMapping("/games/id/{id}")
    public String getGameById(@PathVariable("id") long id) {

        //Try will check to see if there is a game with the given ID. If it is it will return the
        //respective game ID. If it is not it will throw an error and return ID not found
        try {
            Optional<String> gameName = gameService.getGameById(id);
            if (gameName.isEmpty()) {
                throw new IDNotFoundException("The ID you are searching for does not exist");
            }
            return ("ID:" + id + " Name:" + gameName);
        } catch (IDNotFoundException e) {
            System.out.println(e.getMessage());
            return "ID Not found";
        }
    }

    @GetMapping("/games/name/{name}")
    public List<GameModel> getGameByName(@PathVariable("name") String name) throws GameNameNotFoundException {

        // Gets the information from the file locally
        String storedSteamGames = readFromFile.read();

        //Gets the JSON Object
        JSONObject jsonObject = new JSONObject(storedSteamGames);

        //Gets "applist" from the JSON object
        JSONObject appList = jsonObject.getJSONObject("applist");

        //Gets "apps" from the appList object
        JSONArray apps = appList.getJSONArray("apps");

        //List will store the games that are found
        List<GameModel> gameModels = new ArrayList<>();

        //Add each game object to the list
        for (int i = 0; i < apps.length(); i++) {
            JSONObject steamGameObject = apps.getJSONObject(i);
            GameModel gameModel = new GameModel();
            gameModel.setId(steamGameObject.getLong("appid"));
            gameModel.setName(steamGameObject.getString("name"));
            gameModels.add(gameModel);
        }

        //Loop will check to see if the name matches any of the names in the list. If it is it will return the
        //respective game object. If it is not it will throw an error
        List<GameModel> gamesFound = new ArrayList<>();
        for (int i = 0; i < gameModels.size(); i++) {
            GameModel gameModel = gameModels.get(i);
            if (gameModel.getName().equals(name)) {
                gamesFound.add(gameModel);
            }
        }
        if (gamesFound.isEmpty()) {
            throw new GameNameNotFoundException("No games were found with that name");
        }

        return gamesFound;
    }
}
