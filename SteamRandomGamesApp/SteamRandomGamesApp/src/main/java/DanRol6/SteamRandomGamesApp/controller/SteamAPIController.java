package DanRol6.SteamRandomGamesApp.controller;

import DanRol6.SteamRandomGamesApp.service.SteamAPIService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
@RequestMapping("/api/listofgames")
public class SteamAPIController {

    @Autowired
    private SteamAPIService steamAPIService;

    //THIS WILL GET ALL THE DATA FROM THE STEAM API GAME LIST. BASICALLY IT REFRESHES THE DATABASE
    @GetMapping("/data")
    public ResponseEntity<String> getListOfGames() {
        try {
            //Calls the Steam API and stores the information into a Json file locally
            steamAPIService.fetchSteamGamesFromAPI();

            //Gets the information from the file locally and displays it
            String storedSteamGames = steamAPIService.ReadFile();
            System.out.println(storedSteamGames);
            return new ResponseEntity<>(storedSteamGames, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching data from the List of Games API", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/appindex/{index}")
    public ResponseEntity<String> getAppByIndex(@PathVariable("index") int index){
        try{
          System.out.println(index);

            // Gets the information from the file locally
            String storedSteamGames = steamAPIService.ReadFile();

            //Parse the JSON string into a JSONObject
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(storedSteamGames);

            // Get the "applist" object from the JSON object
            JSONObject appList = (JSONObject) jsonObject.get("applist");

            // Get the "apps" array from the "applist" object
            JSONArray appsArray = (JSONArray) appList.get("apps");

            // Get the app at the given index from the "apps" array
            JSONObject indexApp = (JSONObject) appsArray.get(index);

            // Get the "name" value from the given index item
            String name = (String) indexApp.get("name");

//            //Get the "appid" from the given index item
            long appid = (long) indexApp.get("appid");

            // Return the name with HTTP status OK
            return new ResponseEntity<>( "Game ID: " + appid + " Game Name: " + name, HttpStatus.OK);

        } catch (ParseException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching data from the list of games API", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appid/{id}")
    public ResponseEntity<String> getAppById(@PathVariable("id") long id) {
        try {

            System.out.println(id);

            // Gets the information from the file locally
            String storedSteamGames = steamAPIService.ReadFile();

            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(storedSteamGames);

            // Get the "applist" object from the JSON object
            JSONObject appList = (JSONObject) jsonObject.get("applist");

            // Get the "apps" array from the "applist" object
            JSONArray appsArray = (JSONArray) appList.get("apps");

            // Iterate through the apps array
            for (Object obj : appsArray) {
                JSONObject app = (JSONObject) obj;
                // Check if the appid matches the requested id
                long appid = (long) app.get("appid");
                if (appid == id) {
                    // If found, return the name associated with the appid
                    String name = (String) app.get("name");
                    return new ResponseEntity<>(name, HttpStatus.OK);
                }
            }

            // If no app with the specified appid is found, return an error response
            return new ResponseEntity<>("App not found", HttpStatus.NOT_FOUND);

        } catch (ParseException e) {
            e.printStackTrace();
            // Return an error response if an exception occurs
            return new ResponseEntity<>("Error fetching data from the List of Games API", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Long> getIdByName(@PathVariable("name") String name) {
        try {

            // Gets the information from the file locally
            String storedSteamGames = steamAPIService.ReadFile();

            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(storedSteamGames);

            // Get the "applist" object from the JSON object
            JSONObject appList = (JSONObject) jsonObject.get("applist");

            // Get the "apps" array from the "applist" object
            JSONArray appsArray = (JSONArray) appList.get("apps");

            // Iterate through the apps array
            for (Object obj : appsArray) {
                JSONObject app = (JSONObject) obj;
                // Check if the name matches the requested name
                String appName = (String) app.get("name");
                if (appName.equals(name)) {
                    // If found, return the appid associated with the name
                    long appid = (long) app.get("appid");
                    return new ResponseEntity<>(appid, HttpStatus.OK);
                }
            }

            // If no app with the specified name is found, return an error response
            return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);

        } catch (ParseException e) {
            e.printStackTrace();
            // Return an error response if an exception occurs
            return new ResponseEntity<>(-1L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
