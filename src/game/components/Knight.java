package game.components;

import java.util.ArrayList;

import game.Move;
import game.MoveData;

public class Knight extends Piece {

	public Knight(String color) {
		super(color + "Knight");
	}

	@Override
	public void generateMoves(ArrayList<Piece> board) {
		moves = new ArrayList<Move>();
		for (int knightMoveIndex = 0; knightMoveIndex < MoveData.knightMoves[position].length; knightMoveIndex++) {
			int targetSquare = MoveData.knightMoves[position][knightMoveIndex];
			if (isCapture(board, targetSquare)) {
				moves.add(new Move(position, targetSquare));
			}
		}
	}
}
