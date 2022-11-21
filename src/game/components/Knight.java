package game.components;

import java.util.ArrayList;

import game.Capture;
import game.Move;
import game.MoveData;
import test.ANSI;

public class Knight extends Piece {

	public Knight(String color) {
		super(color + "Knight");
	}

	@Override
	public void generateMoves() {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);

		// Generate moves based on knightMoveIndex under MoveData.java
		for (int knightMoveIndex = 0; knightMoveIndex < MoveData.knightMoves[position
				.getValue()].length; knightMoveIndex++) {
			int targetSquare = MoveData.knightMoves[position.getValue()][knightMoveIndex];
			if (this.canMoveTo(targetSquare)) {
				Move nextMove;

				if (this.canCaptureOn(targetSquare)) {
					nextMove = new Capture(position.getValue(), targetSquare, board.getPieceOn(targetSquare));
				} else {
					nextMove = new Move(position.getValue(), targetSquare);
				}

				moves.add(nextMove);
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}
}
