package game.components;

import java.util.ArrayList;
import java.util.List;

import game.Move;
import game.MoveData;
import test.ANSI;

public class Knight extends Piece {

	public Knight(String color) {
		super(color + "Knight");
	}

	@Override
	public void generateMoves(List<Piece> board) {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);
		for (int knightMoveIndex = 0; knightMoveIndex < MoveData.knightMoves[position.getValue()].length; knightMoveIndex++) {
			int targetSquare = MoveData.knightMoves[position.getValue()][knightMoveIndex];
			if (isCapture(board, targetSquare)) {
				moves.add(new Move(position.getValue(), targetSquare));
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}
}
