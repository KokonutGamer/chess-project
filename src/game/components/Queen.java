package game.components;

import java.util.List;

public class Queen extends SlidingPiece {

	public Queen(String color) {
		super(color + "Queen");
	}

	@Override
	public void generateMoves(List<Piece> board) {
		startDirIndex = 0;
		endDirIndex = 8;
		super.generateMoves(board);
	}

}
