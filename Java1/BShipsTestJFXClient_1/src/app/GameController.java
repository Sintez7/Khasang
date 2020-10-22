package app;

import java.net.URL;
import java.util.ResourceBundle;

import app.shared.HitResponse;
import app.shared.TurnUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class GameController {

    private static final int SIZE = 10;
    private Main main;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane playerGrid;

    @FXML
    private GridPane opponentGrid;

    @FXML
    private FlowPane flowPane;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField chatInputTextField;

    private Cell[][] playerField = new Cell[SIZE][SIZE];
    private boolean holdingShip = false;
    private int shipSize = 0;
    private int shipBias = 0;

    public GameController(Main main) {
        this.main = main;
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
                cell.getStyleClass().add("freeCell");
                cell.setOnMouseClicked(event -> {
                    System.err.println("cell clicked: x " + ((Cell) event.getSource()).x + " y: " + ((Cell) event.getSource()).y);
                });
                playerGrid.add(cell, j, i);
            }
        }

        for (int i = 0; i < 4; i++) {
            ShipButton temp = new ShipButton(i + 1);
            temp.getStyleClass().clear();
//            temp.getStyleClass().add("cellShip");

            temp.setText("test " + (i + 1));
            switch (i) {
                case 0 -> {
                    temp.setId("oneDeckShipImage");
                    temp.setPrefSize(20, 20);
                    temp.setOnMouseClicked((mouseEvent) -> {
                        if (holdingShip) {
                            holdingShip = false;
                        } else {
                            holdingShip = true;
                            shipSize = 1;
                            shipBias = 0;
                        }
                    });
                }
                case 1 -> {
                    temp.setId("twoDeckShipImage");
                    temp.setPrefSize(40, 20);
                    temp.setOnMouseClicked((mouseEvent) -> {
                        if (holdingShip) {
                            holdingShip = false;
                        } else {
                            holdingShip = true;
                            shipSize = 2;
                            shipBias = 0;
                        }
                    });
                }
                case 2 -> {
                    temp.setId("threeDeckShipImage");
                    temp.setPrefSize(60, 20);
                    temp.setOnMouseClicked((mouseEvent) -> {
                        if (holdingShip) {
                            holdingShip = false;
                        } else {
                            holdingShip = true;
                            shipSize = 3;
                            shipBias = 0;
                        }
                    });
                }
                case 3 -> {
                    temp.setId("fourDeckShipImage");
                    temp.setPrefSize(80, 20);
                    temp.setOnMouseClicked((mouseEvent) -> {
                        if (holdingShip) {
                            holdingShip = false;
                        } else {
                            holdingShip = true;
                            shipSize = 4;
                            shipBias = 0;
                        }
                    });
                }
            }
            flowPane.getChildren().add(temp);
        }
    }

    @FXML
    void sendReady(ActionEvent event) {
//        main.sendReady();
    }

    @FXML
    void sendMessage(ActionEvent event) {

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

    public static class Cell extends Button {

        protected static final int FREE = 0;
        protected static final int OCCUPIED = 1;
        protected static final int HIT = 2;
        protected static final int OCCUPIED_HIT = 3;

        int x;
        int y;
        int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    private static class ShipButton extends Button {

        private int size;

        ShipButton(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    private static class ShipEntity {

    }
}
