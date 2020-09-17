public class Lobby extends Thread {

    private boolean started = false;
    private String name;

    public Lobby() {
        this(null);
    }

    public Lobby(String name) {
        if (!name.equals("")) {
            this.name = name;
        } else {
            this.name = "Unnamed Lobby";
        }
    }

    public DataPackage convertToDataPackage() {
        return new LobbyDataPackage(this);
    }

    @Override
    public void run() {

    }
}
