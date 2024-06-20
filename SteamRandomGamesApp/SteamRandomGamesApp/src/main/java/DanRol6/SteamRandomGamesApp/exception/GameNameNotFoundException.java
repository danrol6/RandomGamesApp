package DanRol6.SteamRandomGamesApp.exception;

public class GameNameNotFoundException extends Exception{
    //If a name of a game searched for is not found
    public GameNameNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
