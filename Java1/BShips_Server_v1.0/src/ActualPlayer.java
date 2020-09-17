public class ActualPlayer implements Player{

    private final ClientHandler client;

    public ActualPlayer(ClientHandler client) {
        this.client = client;
    }

    @Override
    public void sendData(DataPackage dataPackage) {
        client.sendData(dataPackage);
    }
}
