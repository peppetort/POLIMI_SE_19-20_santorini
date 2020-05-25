package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Model.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayingStageController implements Initializable {

    public GridPane boardPane;
    public GridPane actionPane;

    //todo trasformare le label in button -> lo switch funziona :)
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

    MenuButton[][] menu;
    ImageView[][] terrain;
    ImageView[][] levelOne;
    ImageView[][] levelTwo;
    ImageView[][] levelThree;
    ImageView[][] dome;

    MenuItem[][] place;


    static ObservableList<Actions> list = FXCollections.observableArrayList();

    public PlayingStageController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        place = new MenuItem[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                place[i][j] = new MenuItem("Place");
                place[i][j].setOnAction(this::handlePlace);
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
            menu = new MenuButton[5][5];


            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {

                    terrain[i][j] = new ImageView();
                    terrain[i][j].setImage(terrainImg);
                    terrain[i][j].setFitHeight(80);
                    terrain[i][j].setFitWidth(80);
                    terrain[i][j].setPreserveRatio(true);
                    terrain[i][j].setSmooth(true);

                    levelOne[i][j] = new ImageView();
                    levelOne[i][j].setImage(levelOneImg);
                    levelOne[i][j].setFitHeight(80);
                    levelOne[i][j].setFitWidth(80);
                    levelOne[i][j].setPreserveRatio(true);
                    levelOne[i][j].setSmooth(true);

                    levelTwo[i][j] = new ImageView();
                    levelTwo[i][j].setImage(levelTwoImg);
                    levelTwo[i][j].setFitHeight(80);
                    levelTwo[i][j].setFitWidth(80);
                    levelTwo[i][j].setPreserveRatio(true);
                    levelTwo[i][j].setSmooth(true);

                    levelThree[i][j] = new ImageView();
                    levelThree[i][j].setImage(levelThreeImg);
                    levelThree[i][j].setFitHeight(80);
                    levelThree[i][j].setFitWidth(80);
                    levelThree[i][j].setPreserveRatio(true);
                    levelThree[i][j].setSmooth(true);

                    dome[i][j] = new ImageView();
                    dome[i][j].setImage(domeImg);
                    dome[i][j].setFitHeight(80);
                    dome[i][j].setFitWidth(80);
                    dome[i][j].setPreserveRatio(true);
                    dome[i][j].setSmooth(true);

                    menu[i][j] = new MenuButton();
                    menu[i][j].setPrefHeight(80);
                    menu[i][j].setPrefWidth(80);
                    menu[i][j].setOpacity(0);
                    menu[i][j].setPopupSide(Side.RIGHT);
                    menu[i][j].setOnMouseEntered(this::handleMouseOver);
                    menu[i][j].setOnMouseExited(this::handleMouseExit);
                    menu[i][j].setOnMouseClicked(this::handleAction);

                    actionPane.setConstraints(menu[i][j],i,j);

                    boardPane.setConstraints(terrain[i][j], i, j);
                    boardPane.setConstraints(levelOne[i][j], i, j);
                    boardPane.setConstraints(levelTwo[i][j], i, j);
                    boardPane.setConstraints(levelThree[i][j], i, j);
                    boardPane.setConstraints(dome[i][j], i, j);

                    actionPane.getChildren().add(menu[i][j]);
                    boardPane.getChildren().addAll(terrain[i][j],levelOne[i][j],levelTwo[i][j],levelThree[i][j],dome[i][j]);
                }
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
//        actionLabel.textProperty().bind(action);

        //serve :) purtroppo ho un valore che si aggiorna troppo presto e quindi devo effettuare questo initialize

        //todo : trovare una forma piu elegante per gli switch

        for(Actions a: Actions.values()){
            if(list.contains(a)){
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
                        break;
                    case MOVE:
                        moveLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case DECK:
                        deckLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case CARD:
                        cardLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case END:
                        endLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case SELECT:
                        selectLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case UNDO:
                        undoLabel.setStyle("-fx-background-color: yellow");
                        break;
                    case BUILD_DOME:
                        buildDomeLabel.setStyle("-fx-background-color: yellow");
                        break;
                }
            }else{
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


        list.addListener(new ListChangeListener<Actions>() {
            @Override
            public void onChanged(Change<? extends Actions> change) {
                if (change.next()){
                    System.out.print(change.getList());
                    for(Actions a: Actions.values()){
                        if(list.contains(a)){
                            switch(a){
                                case PLACE:
                                    placeLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case BUILD:
                                    buildLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case MOVE:
                                    moveLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case DECK:
                                    deckLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case CARD:
                                    cardLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case END:
                                    endLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case SELECT:
                                    selectLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case UNDO:
                                    undoLabel.setStyle("-fx-background-color: yellow");
                                    break;
                                case BUILD_DOME:
                                    buildDomeLabel.setStyle("-fx-background-color: yellow");
                                    break;
                            }
                        }else{
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
            }
        });

    }

    private void handlePlace(ActionEvent actionEvent) {
        System.out.println("handling place");
    }

    public static void setActionLabel(ArrayList<Actions> act) {
        list.addAll(act);
    }

    public void handleAction(javafx.scene.input.MouseEvent e){
        System.out.println(actionPane.getRowIndex((Node)e.getSource())+" "+actionPane.getColumnIndex((Node)e.getSource()));
        //clicco sul menuButton

    }


    public void handleMouseOver(javafx.scene.input.MouseEvent e){
        ((MenuButton)e.getSource()).setOpacity(0.4);
    }


    public void handleMouseExit(javafx.scene.input.MouseEvent e){
        ((MenuButton)e.getSource()).setOpacity(0);
    }



}
