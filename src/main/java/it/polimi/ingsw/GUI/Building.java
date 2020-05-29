package it.polimi.ingsw.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Building extends ImageView {

	Image block;

	public Building() {
		super();
		this.setPreserveRatio(true);
		this.setPickOnBounds(true);
		this.setFitWidth(74);
		this.setFitHeight(109);
		this.setVisible(false);
	}

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
				case 4:
					block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/level4.png")));
					break;
			}
			this.setImage(block);
			this.setVisible(true);
		}else {
			this.setVisible(false);
		}
	}

	public void buildDome() {
		block = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("img/levelDome.png")));
		this.setVisible(true);
	}
}
