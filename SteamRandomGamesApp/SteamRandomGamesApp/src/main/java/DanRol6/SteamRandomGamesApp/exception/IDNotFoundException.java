package DanRol6.SteamRandomGamesApp.exception;

public class IDNotFoundException extends Exception{
    //If an ID of a game searched for is not found
    public IDNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
