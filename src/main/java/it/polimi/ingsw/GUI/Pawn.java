package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Pawn extends ImageView {

    Color color;
    Image green  = new Image(this.getClass().getClassLoader().getResourceAsStream("img/green.png"));
    Image red  = new Image(this.getClass().getClassLoader().getResourceAsStream("img/red.png"));
    Image blue  = new Image(this.getClass().getClassLoader().getResourceAsStream("img/blue.png"));

    /**
     * Creates a new view that represents an IMG element.
     *
     * @param color
     */
    public Pawn(Color color) {
        super();
        this.color = color;
        switch (color){
            case BLUE:
                this.setImage(blue);
                break;
            case RED:
                this.setImage(red);
                break;
            case GREEN:
                this.setImage(green);
                break;
        }
        this.setFitHeight(80);
        this.setFitWidth(80);
        this.setPreserveRatio(true);
        this.setSmooth(true);
    }



}

