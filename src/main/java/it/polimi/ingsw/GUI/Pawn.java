package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class Pawn extends ImageView {

    Image green  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/purplePawn.png")));
    Image red  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/orangePawn.png")));
    Image blue  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/bluePawn.png")));

    /**
     * Creates a new view that represents an IMG element.
     *
     */
    public Pawn() {
        super();
        this.setVisible(false);
        this.setFitHeight(30);
        this.setFitWidth(30);
        this.setPreserveRatio(true);
        this.setSmooth(true);
    }


    public void setColor(Color color){
        if(color != null) {
            switch (color) {
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
            this.setVisible(true);
        }else {
            this.setVisible(false);
        }
    }



}

