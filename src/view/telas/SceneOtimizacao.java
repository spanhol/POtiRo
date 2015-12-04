package view.telas;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Fernando Spanhol
 */
public class SceneOtimizacao extends Scene {

	Pane root;

	public SceneOtimizacao(Pane main) {
		super(main);
		root = main;

		HBox hMapa = new HBox();
		main.getChildren().add(hMapa);
	}
}
