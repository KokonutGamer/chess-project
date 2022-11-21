package game.components;

public class Rook extends SlidingPiece {

	public Rook(String color) {
		super(color + "Rook");
	}

	@Override
	public void generateMoves() {
		startDirIndex = 0;
		endDirIndex = 4;
		super.generateMoves();
	}

}
