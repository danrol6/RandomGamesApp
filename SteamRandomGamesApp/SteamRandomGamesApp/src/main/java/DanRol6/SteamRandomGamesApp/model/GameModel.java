package DanRol6.SteamRandomGamesApp.model;

public class GameModel {
    private long id;
    private String name;
    private String description;
    public GameModel(){
        this.id = -1;
        this.name = "";
        this.description = "";
    }
    public GameModel(long id, String name){
        this.id = id;
        this.name = name;
        this.description = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
