package game;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Icon {
	BlackBishop, BlackKing, BlackKnight, BlackPawn, BlackQueen, BlackRook,
	WhiteBishop, WhiteKing, WhiteKnight, WhitePawn, WhiteQueen, WhiteRook;
	
	private ImageView image;
	private Label imageLabel;
	
	private Icon() {
		String path = "/images/icons/" + this.name() + ".png";
		image = new ImageView(new Image(Icon.class.getResource(path).toExternalForm()));
		imageLabel = new Label();
		imageLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		imageLabel.setGraphic(image);
		imageLabel.setStyle("-fx-font: 20 TimesNewRoman;");
		imageLabel.setTooltip(new Tooltip("Component Type"));
	}
	
	public ImageView getImage() {
		return image;
	}

	public Label getImageLabel() {
		return imageLabel;
	}
}
