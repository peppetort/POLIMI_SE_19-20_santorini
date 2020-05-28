package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Messages.InvalidChoiceMessage;
import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.Color;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayingStageController implements Initializable {

    public GridPane boardPane;
    public GridPane actionPane;
    public GridPane pawnPane;

    public VBox vBox;
    public HBox actionsBox;

    public Label placeLabel;
    public Label endLabel;
    public Label deckLabel;
    public Label cardLabel;
    public Label moveLabel;
    public Label buildLabel;
    public Label buildDomeLabel;
    public Label selectLabel;
    public Label undoLabel;

    public Button endButton;
    public Button undoButton;

    public TextArea chatField;

    private static MainController mainController;

    static MenuButton[][] menu = new MenuButton[5][5];

    static ImageView[][] terrain;
    static ImageView[][] levelOne;
    static ImageView[][] levelTwo;
    static ImageView[][] levelThree;
    static ImageView[][] dome;

    static Pawn[][] greenPawns;
    static Pawn[][] bluePawns;
    static Pawn[][] redPawns;

    static MenuItem[][] place;
    static MenuItem[][] build;
    static MenuItem[][] select;
    static MenuItem[][] move;

    protected static int x,y;


    ActionsHandler actionsHandler = new ActionsHandler(this.mainController,this);

    static ObservableList<Actions> list = FXCollections.observableArrayList();
    static ObservableList<String> messages = FXCollections.observableArrayList();

    public static void setMainController(MainController mc){
        mainController = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainController.setPlaying(true);

        messages.addListener((ListChangeListener.Change<? extends String> change) -> {
            if(change.next()){
                chatField.appendText(change.toString());
            }
        });

        undoButton.setOnAction(actionsHandler::handleUndo);
        endButton.setOnAction(actionsHandler::handleEnd);

        place = new MenuItem[5][5];
        move = new MenuItem[5][5];
        build = new MenuItem[5][5];
        select = new MenuItem[5][5];

        redPawns = new Pawn[5][5];
        bluePawns = new Pawn[5][5];
        greenPawns = new Pawn[5][5];

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                place[i][j] = new MenuItem("Place");
                place[i][j].setOnAction(actionsHandler::handlePlace);

                build[i][j] = new MenuItem("Build");
                build[i][j].setOnAction(actionsHandler::handleBuild);

                select[i][j] = new MenuItem("Select");
                select[i][j].setOnAction(actionsHandler::handleSelect);

                move[i][j] = new MenuItem("Move");
                move[i][j].setOnAction(actionsHandler::handleMove);

                redPawns[i][j] = new Pawn(Color.RED);
                redPawns[i][j].setOpacity(0);
                greenPawns[i][j] = new Pawn(Color.GREEN);
                greenPawns[i][j].setOpacity(0);
                bluePawns[i][j] = new Pawn(Color.BLUE);
                bluePawns[i][j].setOpacity(0);

            }
        }


        try {
            Image terrainImg = new Image(this.getClass().getClassLoader().getResourceAsStream("img/terrain.png"));
            Image levelOneImg = new Image(this.getClass().getClassLoader().getResourceAsStream("img/level1.png"));
            Image levelTwoImg = new Image(this.getClass().getClassLoader().getResourceAsStream("img/level2.png"));
            Image levelThreeImg = new Image(this.getClass().getClassLoader().getResourceAsStream("img/level3.png"));
            Image domeImg = new Image(this.getClass().getClassLoader().getResourceAsStream("img/dome.png"));

            terrain = new ImageView[5][5];
            levelOne = new ImageView[5][5];
            levelTwo = new ImageView[5][5];
            levelThree = new ImageView[5][5];
            dome = new ImageView[5][5];




            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {

                    terrain[i][j] = new ImageView();
                    terrain[i][j].setImage(terrainImg);
                    terrain[i][j].setFitHeight(80);
                    terrain[i][j].setFitWidth(80);
                    terrain[i][j].setPreserveRatio(true);
                    terrain[i][j].setSmooth(true);
                    terrain[i][j].setOpacity(1);

                    levelOne[i][j] = new ImageView();
                    levelOne[i][j].setImage(levelOneImg);
                    levelOne[i][j].setFitHeight(80);
                    levelOne[i][j].setFitWidth(80);
                    levelOne[i][j].setPreserveRatio(true);
                    levelOne[i][j].setSmooth(true);
                    levelOne[i][j].setOpacity(0);

                    levelTwo[i][j] = new ImageView();
                    levelTwo[i][j].setImage(levelTwoImg);
                    levelTwo[i][j].setFitHeight(80);
                    levelTwo[i][j].setFitWidth(80);
                    levelTwo[i][j].setPreserveRatio(true);
                    levelTwo[i][j].setSmooth(true);
                    levelTwo[i][j].setOpacity(0);

                    levelThree[i][j] = new ImageView();
                    levelThree[i][j].setImage(levelThreeImg);
                    levelThree[i][j].setFitHeight(80);
                    levelThree[i][j].setFitWidth(80);
                    levelThree[i][j].setPreserveRatio(true);
                    levelThree[i][j].setSmooth(true);
                    levelThree[i][j].setOpacity(0);

                    dome[i][j] = new ImageView();
                    dome[i][j].setImage(domeImg);
                    dome[i][j].setFitHeight(80);
                    dome[i][j].setFitWidth(80);
                    dome[i][j].setPreserveRatio(true);
                    dome[i][j].setSmooth(true);
                    dome[i][j].setOpacity(0);

                    menu[i][j] = new MenuButton();
                    menu[i][j].setPrefHeight(80);
                    menu[i][j].setPrefWidth(80);
                    menu[i][j].setOpacity(0);
                    menu[i][j].setPopupSide(Side.RIGHT);
                    menu[i][j].setOnMouseEntered(this::handleMouseOver);
                    menu[i][j].setOnMouseExited(this::handleMouseExit);
                    menu[i][j].setOnMouseClicked(this::handleAction);

                    actionPane.setConstraints(menu[i][j],j,i);

                    pawnPane.setConstraints(redPawns[i][j],j,i);
                    pawnPane.setConstraints(bluePawns[i][j],j,i);
                    pawnPane.setConstraints(greenPawns[i][j],j,i);

                    boardPane.setConstraints(terrain[i][j], j,i);
                    boardPane.setConstraints(levelOne[i][j], j,i);
                    boardPane.setConstraints(levelTwo[i][j], j,i);
                    boardPane.setConstraints(levelThree[i][j], j,i);
                    boardPane.setConstraints(dome[i][j],j,i);

                    actionPane.getChildren().add(menu[i][j]);

                    pawnPane.getChildren().addAll(redPawns[i][j],greenPawns[i][j],bluePawns[i][j]);

                    boardPane.getChildren().addAll(terrain[i][j],levelOne[i][j],levelTwo[i][j],levelThree[i][j],dome[i][j]);
                }
            }
        }catch (Exception e){
            System.err.print(e.getMessage());
        }
//        actionLabel.textProperty().bind(action);

        //serve :) purtroppo ho un valore che si aggiorna troppo presto e quindi devo effettuare questo initialize

//        for(Actions a: Actions.values()){
//            if(list.contains(a)){
//                switch(a){
//                    case PLACE:
//                        placeLabel.setStyle("-fx-background-color: yellow");
//                        for(int i=0;i<5;i++){
//                            for(int j=0;j<5;j++){
//                                menu[i][j].getItems().add(place[i][j]);
//                            }
//                        }
//                        break;
//                    case BUILD:
//                        buildLabel.setStyle("-fx-background-color: yellow");
//                        for(int i=0;i<5;i++){
//                            for(int j=0;j<5;j++){
//                                menu[i][j].getItems().add(build[i][j]);
//                            }
//                        }
//                        break;
//                    case MOVE:
//                        moveLabel.setStyle("-fx-background-color: yellow");
//                        for(int i=0;i<5;i++){
//                            for(int j=0;j<5;j++){
//                                menu[i][j].getItems().add(move[i][j]);
//                            }
//                        }
//                        break;
//                    case DECK:
//                        deckLabel.setStyle("-fx-background-color: yellow");
//                        Platform.runLater(() ->{
//                            try {
//                                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("AllCardsMenu.fxml"));
//                                Scene scene = new Scene(pane, 1280, 720);
//                                ClientGUIApp.window.setScene(scene);
//                            }catch (IOException e){}
//                        });
//                        break;
//                    case CARD:
//                        cardLabel.setStyle("-fx-background-color: yellow");
//                        break;
//                    case END:
//                        endLabel.setStyle("-fx-background-color: yellow");
//                        break;
//                    case SELECT:
//                        selectLabel.setStyle("-fx-background-color: yellow");
//                        for(int i=0;i<5;i++){
//                            for(int j=0;j<5;j++){
//                                menu[i][j].getItems().add(select[i][j]);
//                            }
//                        }
//                        break;
//                    case UNDO:
//                        undoLabel.setStyle("-fx-background-color: yellow");
//                        break;
//                    case BUILD_DOME:
//                        buildDomeLabel.setStyle("-fx-background-color: yellow");
//                        break;
//                }
//            }else{
//                switch(a){
//                    case PLACE:
//                        placeLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case BUILD:
//                        buildLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case MOVE:
//                        moveLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case DECK:
//                        deckLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case CARD:
//                        cardLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case END:
//                        endLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case SELECT:
//                        selectLabel.setStyle("-fx-background-color: white");
//                        break;
//                    case UNDO:
//                        undoLabel.setStyle("-fx-background-color: white;");
//                        break;
//                    case BUILD_DOME:
//                        buildDomeLabel.setStyle("-fx-background-color: white");
//                        break;
//                }
//
//            }
//        }


        list.addListener((ListChangeListener.Change<? extends Actions> change) -> {
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    menu[i][j].getItems().clear();
                }
            }
                if (change.next()){
                    for(Actions a: Actions.values()){
                        if(list.contains(a)  && change.wasAdded()){
                            switch(a){
                                case PLACE:
                                    placeLabel.setStyle("-fx-background-color: yellow");

                                    for(int i=0;i<5;i++){
                                        for(int j=0;j<5;j++){
                                            menu[i][j].getItems().add(place[i][j]);
                                        }
                                    }

                                    break;
                                case BUILD:
                                    buildLabel.setStyle("-fx-background-color: yellow");
                                    for(int i=0;i<5;i++){
                                        for(int j=0;j<5;j++){
                                            menu[i][j].getItems().add(build[i][j]);
                                        }
                                    }
                                    break;
                                case MOVE:
                                    moveLabel.setStyle("-fx-background-color: yellow");
                                    for(int i=0;i<5;i++){
                                        for(int j=0;j<5;j++){
                                            menu[i][j].getItems().add(move[i][j]);
                                        }
                                    }
                                    break;
                                case DECK:
                                    deckLabel.setStyle("-fx-background-color: yellow");
                                    Platform.runLater(() ->{
                                        try {
                                            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("AllCardsMenu.fxml"));
                                            Scene scene = new Scene(pane, 1280, 720);
                                            ClientGUIApp.window.setScene(scene);
                                        }catch (IOException e){}
                                    });
                                    break;
                                case CARD:
                                    cardLabel.setStyle("-fx-background-color: yellow");
                                    Platform.runLater(() ->{
                                        try {
                                            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("SelectCardMenu.fxml"));
                                            Scene scene = new Scene(pane, 1280, 720);
                                            ClientGUIApp.window.setScene(scene);
                                        }catch (IOException e){}
                                    });
                                    break;
                                case END:
                                    endLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case SELECT:
                                    selectLabel.setStyle("-fx-background-color: yellow");
                                    for(int i=0;i<5;i++){
                                        for(int j=0;j<5;j++){
                                            menu[i][j].getItems().add(select[i][j]);
                                        }
                                    }
                                    break;
                                case UNDO:
                                    undoLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case BUILD_DOME:
                                    buildDomeLabel.setStyle("-fx-background-color: yellow");
                                    break;
                            }
                        }else if(change.wasRemoved() || !list.contains(a)){
                            switch(a){
                                case PLACE:
                                    placeLabel.setStyle("-fx-background-color: white");
                                    break;
                                case BUILD:
                                    buildLabel.setStyle("-fx-background-color: white");
                                    break;
                                case MOVE:
                                    moveLabel.setStyle("-fx-background-color: white");
                                    break;
                                case DECK:
                                    deckLabel.setStyle("-fx-background-color: white");
                                    break;
                                case CARD:
                                    cardLabel.setStyle("-fx-background-color: white");
                                    break;
                                case END:
                                    endLabel.setStyle("-fx-background-color: white");
                                    break;
                                case SELECT:
                                    selectLabel.setStyle("-fx-background-color: white");
                                    break;
                                case UNDO:
                                    undoLabel.setStyle("-fx-background-color: white;");
                                    break;
                                case BUILD_DOME:
                                    buildDomeLabel.setStyle("-fx-background-color: white");
                                    break;
                            }

                        }
                    }
                }
        });

    }



    public static void setActionLabel(ArrayList<Actions> act) {
        Platform.runLater(() -> {
            try {
                list.clear();
                list.addAll(act);
            }catch(NullPointerException e){System.out.print("lista vuota");}
        });
    }

    public void handleAction(javafx.scene.input.MouseEvent e){

        //NEL MODEL LA X E' LA RIGA E Y LA COLONNA

        x = actionPane.getRowIndex((Node)e.getSource());
        y = actionPane.getColumnIndex((Node)e.getSource());

        e.consume();
    }


    public void handleMouseOver(javafx.scene.input.MouseEvent e){
        ((MenuButton)e.getSource()).setOpacity(0.4);
    }


    public void handleMouseExit(javafx.scene.input.MouseEvent e){
        ((MenuButton)e.getSource()).setOpacity(0);
    }

    public static void updateBoard(){
        Client client = mainController.client;

        //box ha il livello e il colore della pedina
        // box get level e get player
        try {
            Box[][] boardModel = client.getBoard().getBoard();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    switch (boardModel[i][j].getLevel()) {
                        case 0:
                            terrain[i][j].setOpacity(1);
                            levelOne[i][j].setOpacity(0);
                            break;
                        case 1:
                            terrain[i][j].setOpacity(0);
                            levelOne[i][j].setOpacity(1);
                            levelTwo[i][j].setOpacity(0);
                            break;
                        case 2:
                            levelOne[i][j].setOpacity(0);
                            levelTwo[i][j].setOpacity(1);
                            levelThree[i][j].setOpacity(0);
                            break;
                        case 3:
                            levelTwo[i][j].setOpacity(0);
                            levelThree[i][j].setOpacity(1);
                            dome[i][j].setOpacity(0);
                            break;
                        case 4:
                            levelThree[i][j].setOpacity(0);
                            dome[i][j].setOpacity(1);
                            break;
                    }
                    if(boardModel[i][j].getPlayer() != null) {
                        switch (boardModel[i][j].getPlayer()) {
                            case BLUE:
                                bluePawns[i][j].setOpacity(1);
                                redPawns[i][j].setOpacity(0);
                                greenPawns[i][j].setOpacity(0);
                                break;
                            case RED:
                                bluePawns[i][j].setOpacity(0);
                                redPawns[i][j].setOpacity(1);
                                greenPawns[i][j].setOpacity(0);
                                break;
                            case GREEN:
                                bluePawns[i][j].setOpacity(0);
                                redPawns[i][j].setOpacity(0);
                                greenPawns[i][j].setOpacity(1);
                                break;
                            default:
                                bluePawns[i][j].setOpacity(0);
                                redPawns[i][j].setOpacity(0);
                                greenPawns[i][j].setOpacity(0);
                                break;
                        }
                    }else{
                        bluePawns[i][j].setOpacity(0);
                        redPawns[i][j].setOpacity(0);
                        greenPawns[i][j].setOpacity(0);
                    }

                }
            }
        }catch (NullPointerException e){
        }

    }

    public static void handleException(InvalidChoiceMessage message){
        updateBoard();
        messages.add("Error: "+message.getMessage()+"\n");
    }

}
