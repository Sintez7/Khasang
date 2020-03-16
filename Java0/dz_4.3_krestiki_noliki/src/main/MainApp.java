package main;

import games.CrossesZeroes.CrossesZeroes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {

    private static final String EXIT_COMMAND = "e";
    private static final String REFRESH_COMMAND = "r";
    private static final String DEV_MENU_COMMAND = "d";
    private static final String MAIN_HELP_COMMAND = "mh";
    private GameLibrary gameLibrary;
    private BaseInputStrategy input;

    MainApp() {
        gameLibrary = new GameLibrary();
        input = new DefaultBaseInput(this);
    }

    private void createGames(GameLibrary gameLibrary) {
        CrossesZeroes cz = new CrossesZeroes(gameLibrary);
    }

    public void setBaseInput(BaseInputStrategy inputStrategy) {
        input = inputStrategy;
    }

    public void start() {
        createGames(gameLibrary);
        startMainCycle();
    }

    public void startMainCycle() {
        boolean exit = false;

        do {
            String userChoice = getUserChoice();
            switch (userChoice) {
                case EXIT_COMMAND :
                    exit = true;
                    break;
                case REFRESH_COMMAND :
//                    update();
                    break;
                case DEV_MENU_COMMAND :
                    devMenu();
                    break;
                case MAIN_HELP_COMMAND :
//                    showMainHelp();
                    break;

                default:
            }
        } while (exit);
    }

    private void devMenu() {

    }

    private String getUserChoice() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String result = "";

        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
