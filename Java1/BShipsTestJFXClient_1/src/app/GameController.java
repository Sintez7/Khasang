package app;

import java.net.URL;
import java.util.ResourceBundle;

import app.shared.HitResponse;
import app.shared.TurnUpdate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class GameController {

    private static final int SIZE = 10;

    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;
    private static final int LEFT = 4;

    private static final int ONE_DECK_SHIPS_MAX = 4;
    private static final int TWO_DECK_SHIPS_MAX = 3;
    private static final int THREE_DECK_SHIPS_MAX = 2;
    private static final int FOUR_DECK_SHIPS_MAX = 1;

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

    private int oneDeckShipsPlaced = 0;
    private int twoDeckShipsPlaced = 0;
    private int threeDeckShipsPlaced = 0;
    private int fourDeckShipsPlaced = 0;

    private Cell[][] playerField = new Cell[SIZE][SIZE];
    private Cell[][] opponentField = new Cell[SIZE][SIZE];
    private boolean holdingShip = false;
    private int shipSize = 0;
    private Integer shipBias = 0;

    private ShipEntity shipToPlace = null;

    private boolean playerTurn = false;

    private String tempText = "asddsaasddsasddsaasddsa";

    public GameController(Main main) {
        this.main = main;
    }

    @FXML
    void initialize() {
        initPlayerGrid();
        initOpponentGrid();
        initShipSelection();
    }

    private void initPlayerGrid() {
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
                    if (event.getButton() == MouseButton.SECONDARY) {
                        handleRightClick();
                    } else {
                        System.err.println("cell clicked: x " + ((Cell) event.getSource()).x + " y: " + ((Cell) event.getSource()).y);
                        if (holdingShip) {
                            placeShip(((Cell) event.getSource()).x, ((Cell) event.getSource()).y, shipSize, shipBias);
                            holdingShip = false;
                        }
                    }
                });
                playerField[j][i] = cell;
                playerGrid.add(playerField[j][i], j, i);
            }
        }
    }

    private void initOpponentGrid() {
        double width;
        double height;
        for (int i = 0; i < SIZE; i++) {
            height = opponentGrid.getRowConstraints().get(i).getPrefHeight();
            for (int j = 0; j < SIZE; j++) {
                Cell cell = new Cell(j, i);
                width = opponentGrid.getColumnConstraints().get(j).getPrefWidth();
                cell.setPrefSize(width, height);
                cell.getStyleClass().clear();
                cell.getStyleClass().add("freeOpponentCell");
                cell.setOnMouseClicked(event -> {
                    System.err.println("opponent cell clicked: x " + ((Cell) event.getSource()).x + " y: " + ((Cell) event.getSource()).y);
                    handleShoot(((Cell) event.getSource()).x, ((Cell) event.getSource()).y);
                });
                opponentField[j][i] = cell;
                opponentGrid.add(opponentField[j][i], j, i);
            }
        }
    }

    private void initShipSelection() {
        for (int i = 0; i < 4; i++) {
            ShipButton temp = new ShipButton(i + 1);
            temp.getStyleClass().clear();
//            temp.getStyleClass().add("cellShip");

//            temp.setText("test " + (i + 1));
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
                            shipBias = RIGHT;
                        }
                        System.err.println("clicked on 1 deck ship");
                        System.err.println("holdingShip is " + holdingShip);
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
                            shipBias = RIGHT;
//                            temp.snapPositionX(main.curMouseX);
//                            temp.snapPositionY(main.curMouseY);
                        }
                        System.err.println("clicked on 2 deck ship");
                        System.err.println("holdingShip is " + holdingShip);
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
                            shipBias = RIGHT;
                            ShipPicture s = new ShipPicture(); // TODO: пока не работает
                        }
                        System.err.println("clicked on 3 deck ship");
                        System.err.println("holdingShip is " + holdingShip);
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
                            shipBias = RIGHT;
                        }
                        System.err.println("clicked on 4 deck ship");
                        System.err.println("holdingShip is " + holdingShip);
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

    public void handleRightClick() {
        System.err.println("mouse right click registered in GameController");
        int temp = shipBias;
        if (shipBias + 1 > 4) {
            shipBias = 1;
        } else {
            shipBias++;
        }
    }

    public void handleHitResponse(HitResponse temp) {

    }

    private void handleShoot(int x, int y) {
        if (playerTurn) {
            main.handleShoot(x, y);
        } else {
            System.err.println("not your turn");
        }
    }

    public void handleTurnUpdate(TurnUpdate temp) {
        updatePlayerGrid(temp.getPlayerField());
//        updateOpponentGrid(temp.getOpponentField());
//        setTurn = temp.getPlayerTurn();
    }

    private void updatePlayerGrid(int[][] playerField) {
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField.length; j++) {
                updatePlayerCell(this.playerField[j][i], playerField[j][i]);
            }
        }
    }

    private void updatePlayerCell(Cell cell, int status) {
        if (cell.status != status) {
            cell.status = status;
            switch (status) {
                case 0 -> {
                    cell.getStyleClass().clear();
                    cell.getStyleClass().add("freeCell");
                }
                case 1 -> {
                    cell.getStyleClass().clear();
                    cell.getStyleClass().add("ship");
                }
                case 2 -> {
                    cell.getStyleClass().clear();
                    cell.getStyleClass().add("hitMiss");
                }
                case 3 -> {
                    cell.getStyleClass().clear();
                    cell.getStyleClass().add("hitShip");
                }
            }
        }

        playerTurn = !playerTurn;
    }

    private void placeShip(int x, int y, int shipSize, int shipBias) {
        switch (shipSize) {
            case 1 -> {
                if (oneDeckShipsPlaced + 1 > ONE_DECK_SHIPS_MAX) {
                    System.err.println("limit for one deck ships is 4");
                    return;
                }
            }
            case 2 -> {
                if (twoDeckShipsPlaced + 1 > TWO_DECK_SHIPS_MAX) {
                    System.err.println("limit for two deck ships is 3");
                    return;
                }
            }
            case 3 -> {
                if (threeDeckShipsPlaced + 1 > THREE_DECK_SHIPS_MAX) {
                    System.err.println("limit for three deck ships is 2");
                    return;
                }
            }
            case 4 -> {
                if (fourDeckShipsPlaced + 1 > FOUR_DECK_SHIPS_MAX) {
                    System.err.println("limit for four deck ships is 1");
                    return;
                }
            }
        }
        if (shipToPlace == null) {
            shipToPlace = new ShipEntity(x, y, shipSize, shipBias);
//            main.handlePlaceShip(x, y, shipSize, shipBias);
        } else {
            System.err.println("waiting for ship placement response");
        }
//        shipToPlace = new ShipEntity(x, y, shipSize, shipBias);
        handlePlaceShipResponse(true);
    }

    public void handlePlaceShipResponse(boolean accepted) {
        if (shipToPlace != null) {
            if (accepted) {
                if (shipToPlace.size == 1) {
                    playerField[shipToPlace.x][shipToPlace.y].getStyleClass().add("ship");
                } else {
                    int xOffset = 0;
                    int yOffset = 0;

                    switch (shipToPlace.bias) {
                        case UP -> yOffset = -1;
                        case RIGHT -> xOffset = 1;
                        case DOWN -> yOffset = 1;
                        case LEFT -> xOffset = -1;
                    }
                    for (int i = 0; i < shipToPlace.size; i++) {
                        playerField[shipToPlace.x + (i * xOffset)]
                                   [shipToPlace.y + (i * yOffset)]
                                   .getStyleClass().add("ship");
                    }
                }
                switch (shipSize) {
                    case 1 -> oneDeckShipsPlaced++;
                    case 2 -> twoDeckShipsPlaced++;
                    case 3 -> threeDeckShipsPlaced++;
                    case 4 -> fourDeckShipsPlaced++;
                }
                shipToPlace = null;
            }
        }
    }

    public void startGame() {

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
        int x;
        int y;
        int size;
        int bias;

        public ShipEntity(int x, int y, int shipSize, int shipBias) {
            this.x = x;
            this.y = y;
            size = shipSize;
            bias = shipBias;
        }
    }

    private static class ShipPicture extends Label {


        public ShipPicture() {

        }
    }
}
