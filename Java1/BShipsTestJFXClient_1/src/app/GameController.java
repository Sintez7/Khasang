package app;

import app.shared.HitResponse;
import app.shared.TurnUpdate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;

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
    private AnchorPane rootPane;

    @FXML
    private AnchorPane aPane;

    @FXML
    private Pane pane;

    @FXML
    private StackPane sPane;

    @FXML
    private BorderPane bPane;

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
    private Label l = new Label();
    private ShipPicture heldShip = null;
    //    private ShipButton heldS = null;
    private Point2D stPoint;
    private double curMX;
    private double curMY;

    public GameController(Main main) {
        this.main = main;
    }

    @FXML
    void initialize() {
        initPlayerGrid();
        initOpponentGrid();
        initShipSelection();
        Label dl = new Label();
        flowPane.getChildren().add(dl);
        sPane.setOnMouseMoved(mouseEvent -> {
            System.err.println("sPane eventHandler triggered");
            curMX = mouseEvent.getSceneX();
            curMY = mouseEvent.getSceneY();
            updateLabel();
//            dl.setText(mouseEvent.toString());
            System.err.println("sPane eventHandler");
            System.err.println(mouseEvent.toString());
//            Event.fireEvent(bPane, mouseEvent);
//                heldShip.updatePos(heldShip.getLayoutX() - mouseEvent.getSceneX(),
//                                   heldShip.getLayoutY() - mouseEvent.getSceneY());

        });
        sPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.err.println("sPane OnMouseClicked triggered");
                    handleRightClick();
                }
            }
        });
        sPane.addEventFilter(MouseEvent.ANY, mouseEvent -> {
//            System.err.println("mouse event filter triggered on sPane");
            System.err.println("sPane eventFilter");
            System.err.println(mouseEvent.toString());
        });
//        pane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                curMX = mouseEvent.getSceneX();
//                curMY = mouseEvent.getSceneY();
//                updateLabel();
//            }
//        });

        aPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.err.println("aPane eventFilter triggered");
                System.err.println(mouseEvent.toString());
            }
        });

        l.setText("curMX: " + curMX + " curMY: " + curMY);
        flowPane.getChildren().add(l);
    }

    private void updateLabel() {
        l.setText("curMX: " + curMX + " curMY: " + curMY);
        if (heldShip != null) {
//            heldShip.setTranslateX(curMX - stPoint.getX());
//            heldShip.setTranslateY(curMY - stPoint.getY());
            heldShip.updatePosition(curMX, curMY);
        }
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
                            aPane.getChildren().clear();
                            heldShip = null;
                        } else {
                            if (oneDeckShipsPlaced + 1 > ONE_DECK_SHIPS_MAX) {
                                System.err.println("limit for one deck ships is " + ONE_DECK_SHIPS_MAX);
                                heldShip = null;
                            } else {
                                holdingShip = true;
                                shipSize = 1;
                                shipBias = RIGHT;
                            }
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
                            aPane.getChildren().clear();
                            heldShip = null;
                        } else {
                            if (twoDeckShipsPlaced + 1 > TWO_DECK_SHIPS_MAX) {
                                System.err.println("limit for two deck ships is " + TWO_DECK_SHIPS_MAX);
                                heldShip = null;
                            } else {
                                holdingShip = true;
                                shipSize = 2;
                                shipBias = RIGHT;
//                            temp.snapPositionX(main.curMouseX);
//                            temp.snapPositionY(main.curMouseY);
                            }
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
                            aPane.getChildren().clear();
                            heldShip = null;
                        } else {
                            if (threeDeckShipsPlaced + 1 > THREE_DECK_SHIPS_MAX) {
                                System.err.println("limit for three deck ships is " + THREE_DECK_SHIPS_MAX);
                                heldShip = null;
                            } else {
                                holdingShip = true;
                                shipSize = 3;
                                shipBias = RIGHT;
//                            ShipPicture s = new ShipPicture(); // TODO: пока не работает
                            }
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
                            aPane.getChildren().clear();
                            heldShip = null;
                        } else {
                            if (fourDeckShipsPlaced + 1 > FOUR_DECK_SHIPS_MAX) {
                                System.err.println("limit for four deck ships is " + FOUR_DECK_SHIPS_MAX);
                                heldShip = null;
                            } else {
                                holdingShip = true;
                                shipSize = 4;
                                shipBias = RIGHT;
                                heldShip = new ShipPicture(4);
                                stPoint = new Point2D(curMX, curMY);
                                heldShip.updatePosition(curMX, curMY);
                                aPane.getChildren().add(heldShip);
                            }
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
        if (shipBias + 1 > 4) {
            shipBias = 1;
        } else {
            shipBias++;
        }
        if (heldShip != null) {
            heldShip.updateRotation(shipBias);
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
        if (shipToPlace == null) {
            shipToPlace = new ShipEntity(x, y, shipSize, shipBias);
//            main.handlePlaceShip(x, y, shipSize, shipBias);
        } else {
            System.err.println("waiting for ship placement response");
        }
//        shipToPlace = new ShipEntity(x, y, shipSize, shipBias);
        handlePlaceShipResponse(true);
        aPane.getChildren().clear();
        heldShip = null;
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
                        try {
                            playerField[shipToPlace.x + (i * xOffset)]
                                    [shipToPlace.y + (i * yOffset)]
                                    .getStyleClass().add("ship");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("out of area");
                        }
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

        private Bias bias;
        double lastMX;
        double lastMY;

        public ShipPicture(int size) {
            switch (size) {
                case 1 -> setPrefSize(20, 20);
                case 2 -> setPrefSize(40, 20);
                case 3 -> setPrefSize(60, 20);
                case 4 -> setPrefSize(80, 20);
            }
            getStyleClass().clear();
            getStyleClass().add("fDeckShipImage");
//            getStyleClass().add("aDeckShipImage");
            updateRotation(2);
        }

        public void updateRotation(int shipBias) {
            double angle = 0.0;
            switch (shipBias) {
                case 1 -> {
                    angle = 270.0;
                    bias = Bias.UP;
                }
                case 2 -> {
                    angle = 0.0;
                    bias = Bias.RIGHT;
                }
                case 3 -> {
                    angle = 90.0;
                    bias = Bias.DOWN;
                }
                case 4 -> {
                    angle = 180.0;
                    bias = Bias.LEFT;
                }
                default -> System.err.println("unsupported bias");
            }
            getTransforms().clear();
            Rotate r = new Rotate(angle, 0, 0);
            r.axisProperty().setValue(Rotate.Z_AXIS);
            getTransforms().addAll(r);
            updatePosition(lastMX, lastMY);
        }

        public void updatePosition(double curMX, double curMY) {
            lastMX = curMX;
            lastMY = curMY;
            setLayoutX(curMX + bias.getXOffset());
            setLayoutY(curMY + bias.getYOffset());
        }

        private enum Bias {
            UP {
                @Override
                double getXOffset() {
                    return -10;
                }

                @Override
                double getYOffset() {
                    return 10;
                }
            },
            RIGHT {
                @Override
                double getXOffset() {
                    return -10;
                }

                @Override
                double getYOffset() {
                    return -10;
                }
            },
            DOWN {
                @Override
                double getXOffset() {
                    return 10;
                }

                @Override
                double getYOffset() {
                    return -10;
                }
            },
            LEFT {
                @Override
                double getXOffset() {
                    return 10;
                }

                @Override
                double getYOffset() {
                    return 10;
                }
            };

            abstract double getXOffset();
            abstract double getYOffset();
        }
    }
}
