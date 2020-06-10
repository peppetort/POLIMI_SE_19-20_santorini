package it.polimi.ingsw.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class used to represent buildings in the {@link PlayingStageController}
 */
public class Building extends ImageView {

	Image block;

	/**
	 * Constructor of the class.
	 */
	public Building() {
		super();
		this.setPreserveRatio(true);
		this.setPickOnBounds(true);
		this.setFitWidth(91);
		this.setFitHeight(136);
		this.setVisible(false);
	}

	/**
	 * Set the {@link Image} attribute of the class (block) with the correct image
	 * url (no dome).
	 * @param level
	 */
	public void build(int level) {
		if(level > 0) {
			switch (level) {
				case 1:
					block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/level1.png")));
					break;
				case 2:
					block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/level2.png")));
					break;
				case 3:
					block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/level3.png")));
					break;
			}
			this.setImage(block);
			this.setVisible(true);
		}else {
			this.setVisible(false);
		}
	}

	/**
	 * Used to build a dome. Handled in a different way than other buildings because
	 * Atlas can build over terrain.
	 */
	public void buildDome() {
		block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/levelDome.png")));
		this.setImage(block);
		this.setVisible(true);
	}
}
