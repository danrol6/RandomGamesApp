package DanRol6.SteamRandomGamesApp.controller;

import DanRol6.SteamRandomGamesApp.exception.GameNameNotFoundException;
import DanRol6.SteamRandomGamesApp.exception.IDNotFoundException;
import DanRol6.SteamRandomGamesApp.model.GameModel;
import DanRol6.SteamRandomGamesApp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GameController {

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
            String gameName = gameService.getGameById(id);
            if (gameName == null) {
                throw new IDNotFoundException("The ID you are searching for does not exist");
            }
            return ("ID:" + id + " Name:" + gameName);
        } catch (IDNotFoundException e) {
            System.out.println(e.getMessage());
            return "ID Not found";
        }
    }

    @GetMapping("/games/name/{name}")
    public List<GameModel> getGameByName(@PathVariable("name") String name) {
        //Gets the list of games found
        List<GameModel> gamesFound = gameService.getGameByName(name);
        System.out.println(gamesFound);

        try {
            //If no games were found, it will throw an error instead stating that No games were found.
            if (gamesFound.isEmpty()) {
                throw new GameNameNotFoundException("No games were found with that name");
            }

            return gamesFound;

        } catch (GameNameNotFoundException e) {
            System.out.println(e.getMessage());
            return gamesFound;
        }
    }
}
