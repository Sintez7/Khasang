// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.gameController;

import app.Main;
import app.shared.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button readyBtn;

    @FXML
    private Label rematchLabel;

    @FXML
    private Button rematchYesBtn;

    @FXML
    private Button rematchNoBtn;

    @FXML
    private TextField chatInputTextField;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    private Label availableShipsLabel;

    @FXML
    private AnchorPane playerGridPane;

    @FXML
    private AnchorPane opGridPane;

    @FXML
    private TabPane tabPane;

    @FXML
    private volatile ListView<String> chatListView;
    private final ObservableList<String> chatMsgList = FXCollections.observableArrayList();

    private static final int SHIP_PLACEMENT_TAB = 0;
    private static final int BATTLE_TAB = 1;
    private static final int REMATCH_TAB = 2;

    private ShipPlaceModule spm;
    private ShootModule sm;

//    private int oneDeckShipsPlaced = 0;
//    private int twoDeckShipsPlaced = 0;
//    private int threeDeckShipsPlaced = 0;
//    private int fourDeckShipsPlaced = 0;

    private boolean gameEnded = false;

    private Cell[][] playerField = new Cell[SIZE][SIZE];
    private Cell[][] opponentField = new Cell[SIZE][SIZE];
    private boolean holdingShip = false;
    private int shipSize = 0;
    private Integer shipBias = 0;

    private ShipEntity shipToPlace = null;

    private boolean playerTurn = false;
    private boolean battlePhase = false;

    private Label l = new Label();
    private ShipPicture heldShip = null;
    private Point2D stPoint;
    private double curMX;
    private double curMY;

    private int thisPlayerNumber = -1;

    public GameController(Main main) {
        this.main = main;
    }

    @FXML
    void initialize() {
        spm = new ShipPlaceModule(this);
        sm = new ShootModule(this);

        initPlayerGrid();
        initOpponentGrid();
        initShipSelection();
        prepareChatWindow();
//        Label dl = new Label();
//        flowPane.getChildren().add(dl);
        sPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                System.err.println("sPane OnMouseClicked triggered");
                handleRightClick();
            }
        });

        rootPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateCursorPos(mouseEvent);
            }
        });

//        l.setText("curMX: " + curMX + " curMY: " + curMY);
        flowPane.getChildren().add(l);

        playerGridPane.getStyleClass().add("grid");
        opGridPane.getStyleClass().add("grid");

        tabPane.getStyleClass().add("tab-pane");
        tabPane.getSelectionModel().select(SHIP_PLACEMENT_TAB);
        applyToChat("Ship placement phase started!");
    }

    private void prepareChatWindow() {
        chatListView.setItems(chatMsgList);

        chatInputTextField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (!chatInputTextField.getText().equals("")) {
                        sendChatMessage(chatInputTextField.getText());
                        chatInputTextField.setText("");
                    }
                }
            }
        });
    }

    private void sendChatMessage(String text) {
        main.sendChatMessage(text);
        chatInputTextField.setText("");
    }

    @FXML
    void sendMessage(ActionEvent event) {
        sendChatMessage(chatInputTextField.getText());
    }

    // Все сообщения не являющиеся сообщениями игроков должны быть
    // помечены как сообщения системы
    public void applyToChat(String text) {
        appendToChat("SYSTEM", text);
    }

    //Это конечный метод для добавления в чат текста
    private void appendToChat(String name, String text) {
        chatMsgList.add(name + ": " + text);
        chatListView.scrollTo(chatMsgList.size() - 1);
    }

    public void handleChatMessage(ChatMessage message) {
        appendToChat(message.getName(), message.getMessage());
    }

    private void updateCursorPos(MouseEvent mouseEvent) {
        curMX = mouseEvent.getSceneX();
        curMY = mouseEvent.getSceneY();
        updateLabel();
    }

    private void updateLabel() {
        l.setText("curMX: " + curMX + " curMY: " + curMY);
        if (heldShip != null) {
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
            switch (i) {
                case 0 -> {
                    temp.setId("oneDeckShipImage");
                    temp.setPrefSize(20, 20);
                    temp.setOnMouseClicked((mouseEvent) -> {
                        if (holdingShip) {
                            holdingShip = false;
                            aPane.getChildren().clear();
                            heldShip = null;
                        } else if (!spm.oneDeckShipsAvailable()) { //oneDeckShipsPlaced >= ONE_DECK_SHIPS_MAX
                            System.err.println("limit for one deck ships is " + ShipPlaceModule.ONE_DECK_SHIPS_MAX);
                            heldShip = null;
                        } else {
                            holdingShip = true;
                            shipSize = 1;
                            shipBias = RIGHT;
                            heldShip = new ShipPicture(1);
                            stPoint = new Point2D(curMX, curMY);
                            heldShip.updatePosition(curMX, curMY);
                            aPane.getChildren().add(heldShip);
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
                        } else if (!spm.twoDeckShipAvailable()) {
                            System.err.println("limit for two deck ships is " + ShipPlaceModule.TWO_DECK_SHIPS_MAX);
                            heldShip = null;
                        } else {
                            holdingShip = true;
                            shipSize = 2;
                            shipBias = RIGHT;
                            heldShip = new ShipPicture(2);
                            stPoint = new Point2D(curMX, curMY);
                            heldShip.updatePosition(curMX, curMY);
                            aPane.getChildren().add(heldShip);
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
                        } else if (!spm.threeDeckShipAvailable()) {
                            System.err.println("limit for three deck ships is " + ShipPlaceModule.THREE_DECK_SHIPS_MAX);
                            heldShip = null;
                        } else {
                            holdingShip = true;
                            shipSize = 3;
                            shipBias = RIGHT;
                            heldShip = new ShipPicture(3);
                            stPoint = new Point2D(curMX, curMY);
                            heldShip.updatePosition(curMX, curMY);
                            aPane.getChildren().add(heldShip);
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
                        } else if (!spm.fourDeckShipAvailable()) {
                            System.err.println("limit for four deck ships is " + ShipPlaceModule.FOUR_DECK_SHIPS_MAX);
                            holdingShip = false;
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
        if (spm.isAllShipsPlaced()) {
            applyToChat("Waiting for opponent...");
            main.sendReady();
        } else {
            applyToChat("You are not ready! Check your ships.");
        }
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
//        applyToChat("player " + thisPlayerNumber + " hit response " + temp.getResponseType());
        sm.handleHitResponse(temp);
    }

    private void handleShoot(int x, int y) {
//        if (battlePhase) {
//            if (playerTurn) {
//                main.handleShoot(x, y);
//            } else {
//                System.err.println("not your turn");
//            }
//        } else {
//            System.err.println("not in battlePhase");
//        }
        sm.handleManualShot(x, y);
    }

    @FXML
    void generateOneShot(ActionEvent event) {
        sm.generateOneShot();
    }

    @FXML
    void autoShoot(ActionEvent event) {
        sm.handleAutoShot();
    }

    public void sendShot(int x, int y) {
        main.handleShoot(x, y);
    }

    public void handleTurnUpdate(TurnUpdate temp) {
//        showField(temp);
        updatePlayerGrid(temp.getPlayerField());
        updateOpponentGrid(temp.getOpponentField());
        setTurn(temp.getPlayerTurn());
        sm.setTurn(temp.getPlayerTurn());
        if (!gameEnded) {
            if (sm.isAuto()) {
                sm.handleAutoShot();
            }
            System.err.println("temp.getPlayerTurn()" + temp.getPlayerTurn());
            applyToChat("current player turn: " + whosTurnName(temp.getPlayerTurn()));
        }
    }

    private void showField(TurnUpdate temp) {
        System.err.println("field on new turn");
        System.err.println("current player field");
        for (int i = 0; i < temp.getPlayerField().length; i++) {
            for (int j = 0; j < temp.getPlayerField()[i].length; j++) {
                System.err.print(temp.getPlayerField()[j][i] + "\t");
            }
            System.err.println();
        }
        System.err.println("==============================");

        System.err.println("current opponent field");
        for (int i = 0; i < temp.getOpponentField().length; i++) {
            for (int j = 0; j < temp.getOpponentField()[i].length; j++) {
                System.err.print(temp.getOpponentField()[j][i] + "\t");
            }
            System.err.println();
        }
        System.err.println("==============================");
    }

    private void setTurn(int playerTurn) {
        this.playerTurn = thisPlayerNumber == playerTurn;
    }

    private String whosTurnName(int playerTurn) {
        if (thisPlayerNumber == 1 & playerTurn == 1 || thisPlayerNumber == 2 & playerTurn == 2) {
            return playerNameLabel.getText();
        }
        return opponentNameLabel.getText();
    }

    private void updatePlayerGrid(int[][] playerField) {
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField.length; j++) {
                updateCell(this.playerField[j][i], playerField[j][i]);
            }
        }
    }

    private void updateOpponentGrid(int[][] opponentField) {
        sm.clearPointsList();
        for (int i = 0; i < opponentField.length; i++) {
            for (int j = 0; j < opponentField.length; j++) {
                updateCell(this.opponentField[j][i], opponentField[j][i]);
                if (opponentField[i][j] == 0) {
                    sm.addPointToAList(i, j);
                }
            }
        }
    }

    private void updateCell(Cell cell, int status) {
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
    }

    private void placeShip(int x, int y, int shipSize, int shipBias) {
        if (shipToPlace == null) {
            shipToPlace = new ShipEntity(x, y, shipSize, shipBias);
            spm.placeShipManual(x, y, shipSize, shipBias);
        } else {
            System.err.println("waiting for ship placement response");
        }
        aPane.getChildren().clear();
        heldShip = null;
    }

    @FXML
    void autoPlaceShips(ActionEvent event) {
        spm.placeShipAuto();
    }

    public void sendPlaceShip(int x, int y, int shipSize, int shipBias) {
        main.handlePlaceShip(x, y, shipSize, shipBias);
        aPane.getChildren().clear();
        heldShip = null;
    }

    public void handlePlaceShipResponse(boolean accepted) {
        spm.handlePlaceShipResponse(accepted);
        shipToPlace = null;
    }

    public void drawPlacedShip(ShipEntity previousShip) {
        System.err.println("drawPlacedShip in GameController invoked");

        if (previousShip.size == 1) {
            playerField[previousShip.x][previousShip.y].getStyleClass().add("ship");
        } else {
            int xOffset = 0;
            int yOffset = 0;

            switch (previousShip.bias) {
                case UP -> yOffset = -1;
                case RIGHT -> xOffset = 1;
                case DOWN -> yOffset = 1;
                case LEFT -> xOffset = -1;
            }
            for (int i = 0; i < previousShip.size; i++) {
                try {
                    playerField[previousShip.x + (i * xOffset)]
                            [previousShip.y + (i * yOffset)]
                            .getStyleClass().add("ship");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("out of area");
                }
            }
        }
    }

    public void handlePlayerInfo(PlayerInfo playerInfo) {
        thisPlayerNumber = playerInfo.getPlayerInfo();
        playerNameLabel.setText(playerInfo.getPlayerName());
        opponentNameLabel.setText(playerInfo.getOpponentName());
        sm.setPlayerNumber(thisPlayerNumber);
    }

    public void handlePlayerWon(PlayerWon temp) {
        applyToChat("Player " + temp.getPlayerWon() + " won!");
        gameEnded = true;
        sm.setGameEnded();
        battlePhase = false;
    }

    public void handleRematchOffer() {
        changeToRematchDecision();
    }

    private void changeToRematchDecision() {
        tabPane.getSelectionModel().select(REMATCH_TAB);
    }

    @FXML
    void rematchYes(ActionEvent event) {
        main.sendRematch(true);
        applyToChat("You voted yes for rematch, waiting for your opponent decision...");
    }

    @FXML
    void rematchNo(ActionEvent event) {
        main.sendRematch(false);
        main.loadLobby();
    }

    public void handleRematch() {
        clearGrids();
        spm.prepareToRematch();
        sm.prepareToRematch();
        tabPane.getSelectionModel().select(SHIP_PLACEMENT_TAB);
        gameEnded = false;
        applyToChat("Rematch accepted");
        applyToChat("Ship placement phase started!");
    }

    private void clearGrids() {
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField.length; j++) {
                playerField[j][i].getStyleClass().clear();
                playerField[j][i].getStyleClass().add("freeCell"); //freeOpponentCell
                opponentField[j][i].getStyleClass().clear();
                opponentField[j][i].getStyleClass().add("freeOpponentCell");
            }
        }
    }

    public void handleBattleStart() {
        tabPane.getSelectionModel().select(BATTLE_TAB);
        battlePhase = true;
        applyToChat("Battle Started!");
    }

    public static class Cell extends Button {
        // Вспомогательный класс для упрощения управления сеткой с кораблями

        public static final int FREE = 0;
        public static final int OCCUPIED = 1;
        public static final int HIT = 2;
        public static final int OCCUPIED_HIT = 3;

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
        // Описание кнопок чтобы "взять" корабль

        private int size;

        ShipButton(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    private static class ShipPicture extends Label {

        /*
         * Этот класс описывает изображение "корабаля" выбранного игроком
         * который перемещается за курсором
         */

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

        /* Перечисление смещения относительно текущего направления отображения корабля
         * Был выбран этот вариант, так как изменение точки вращения у Rotation не давало
         * корректного результата
         */
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
