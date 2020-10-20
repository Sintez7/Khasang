package app;

import java.net.URL;
import java.util.ResourceBundle;

import app.shared.HitResponse;
import app.shared.TurnUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameController {

    private static final int SIZE = 10;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane playerGrid;

    @FXML
    private GridPane opponentGrid;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField chatInputTextField;

    private Cell[][] playerField = new Cell[SIZE][SIZE];

    public GameController(Main main) {
    }

    @FXML
    void initialize() {
        double width;
        double height;
        for (int i = 0; i < SIZE; i++) {
            height = playerGrid.getRowConstraints().get(i).getPrefHeight();
            for (int j = 0; j < SIZE; j++) {
                Cell cell = new Cell(j, i);
                width = playerGrid.getColumnConstraints().get(j).getPrefWidth();
                cell.setPrefSize(width, height);
                cell.getStyleClass().clear();
                cell.getStyleClass().add("-fx-background-color: transparent");
                cell.setOnMouseClicked(event -> {
                    System.err.println("cell clicked: x " + ((Cell) event.getSource()).x + " y: " + ((Cell) event.getSource()).y);
                });
                playerGrid.add(cell, j, i);
            }
        }
    }

    public void handleHitResponse(HitResponse temp) {

    }

    public void handleTurnUpdate(TurnUpdate temp) {
        updatePlayerGrid(temp.getPlayerField());
//        updateOpponentGrid(temp.getOpponentField());
//        setTurn = temp.getPlayerTurn();
    }

    private void updatePlayerGrid(int[][] playerField) {

    }

    private static class Cell extends Button {

        int x;
        int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
