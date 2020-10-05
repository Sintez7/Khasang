package app;

import app.shared.DataPackage;

import java.net.SocketException;

public interface Player {
    void sendData(DataPackage dataPackage) throws SocketException;

    void sendData(String s);
}
