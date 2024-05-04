package DanRol6.SteamRandomGamesApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SteamRandomGamesController {

    @GetMapping("/index")
    public String goHome(){
        return "index";
    }
}
