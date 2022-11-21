package game.components;

public class Bishop extends SlidingPiece {

	public Bishop(String color) {
		super(color + "Bishop");
	}

	@Override
	public void generateMoves() {
		startDirIndex = 4;
		endDirIndex = 8;
		super.generateMoves();
	}

}
