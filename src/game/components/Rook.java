package game.components;

import java.util.List;

public class Rook extends SlidingPiece {

	public Rook(String color) {
		super(color + "Rook");
	}

	@Override
	public void generateMoves(List<Piece> board) {
		startDirIndex = 0;
		endDirIndex = 4;
		super.generateMoves(board);
	}

}
