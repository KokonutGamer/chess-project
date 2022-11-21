package game.components;

public class Queen extends SlidingPiece {

	public Queen(String color) {
		super(color + "Queen");
	}

	@Override
	public void generateMoves() {
		startDirIndex = 0;
		endDirIndex = 8;
		super.generateMoves();
	}

}
