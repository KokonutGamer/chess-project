package game.components;

import java.util.ArrayList;

import game.Capture;
import game.Move;
import game.MoveData;
import test.ANSI;

public class Pawn extends Piece {

	public Pawn(String color) {
		super(color + "Pawn");
	}

	@Override
	public void generateMoves() {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);

		// Sets the direction and checks if the initial targetSquare is on the board
		int direction = color.equals("White") ? 0 : 1;
		int targetSquare = position.getValue() + MoveData.directionOffsets[direction];
		if (targetSquare > 63 || targetSquare < 0)
			return;

		// If no piece on targetSquare, add the move to the moves ArrayList
		if (getPieceOn(targetSquare) == null) {
			moves.add(new Move(position.getValue(), targetSquare));
		}

		// Check if the pawn is at spawn. If it is, check if it can move two squares.
		if ((position.getValue() / 8 == 1 && direction == 0) || (position.getValue() / 8 == 6 && direction == 1)) {
			if (getPieceOn(targetSquare) == null) {
				targetSquare += MoveData.directionOffsets[direction];
				if (getPieceOn(targetSquare) == null) {
					moves.add(new Move(position.getValue(), targetSquare));
				}
			}
		}

		// Check if the pawn can capture diagonally
		int file = position.getValue() % 8;
		for (int i = 4 + direction; i <= 6 + direction; i += 2) {
			int pawnCaptureSquare = position.getValue() + MoveData.directionOffsets[i];

			if (pawnCaptureSquare < 64 && this.canCaptureOn(pawnCaptureSquare)) {

				// Checks if the file is adjacent to the pawn
				int pawnSquareFile = pawnCaptureSquare % 8;
				if (Math.abs(file - pawnSquareFile) == 1) {
					moves.add(new Capture(position.getValue(), pawnCaptureSquare,
							this.getPieceOn(pawnCaptureSquare)));
				}
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}

}
