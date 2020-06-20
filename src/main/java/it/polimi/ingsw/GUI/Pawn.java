package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class that inherits the proprieties from {@link ImageView}. Used to represent pawns in the playing stage based on the
 * {@link Color}.
 */
public class Pawn extends ImageView {

    Image green  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/purplePawn.png")));
    Image red  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/orangePawn.png")));
    Image blue  = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/bluePawn.png")));

    /**
     * Constructor.
     */
    public Pawn() {
        super();
        this.setVisible(false);
        this.setFitHeight(35);
        this.setFitWidth(35);
        this.setPreserveRatio(true);
        this.setSmooth(true);
    }


    /**
     * The image attribute becomes the .png that represents the pawn of the {@link Color} color.
     * @param color
     */
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

