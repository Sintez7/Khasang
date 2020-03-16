package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultBaseInput implements BaseInputStrategy {

    private MainApp mainApp;

    DefaultBaseInput (MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public String getInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public void changeInput(BaseInputStrategy inputStrategy) {
        mainApp.setBaseInput(inputStrategy);
    }
}
