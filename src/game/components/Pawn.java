package game.components;

import java.util.ArrayList;
import java.util.List;

import game.Move;
import game.MoveData;
import test.ANSI;

public class Pawn extends Piece {

	public Pawn(String color) {
		super(color + "Pawn");
	}

	@Override
	public void generateMoves(List<Piece> board) {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);
		int direction = color.equals("White") ? 0 : 1;
		int targetSquare = position.getValue() + MoveData.directionOffsets[direction];
		if (targetSquare > 63 || targetSquare < 0)
			return;

		if (!isOccupied(board, targetSquare)) {
			moves.add(new Move(position.getValue(), targetSquare));
		}

		if ((position.getValue() / 8 == 1 && direction == 0) || (position.getValue() / 8 == 6 && direction == 1)) {
			if (!isOccupied(board, targetSquare)) {
				targetSquare += MoveData.directionOffsets[direction];
				if (!isOccupied(board, targetSquare)) {
					moves.add(new Move(position.getValue(), targetSquare));
				}
			}
		}

		int file = position.getValue() % 8;
		for (int i = 4 + direction; i <= 6 + direction; i += 2) {
			int pawnCaptureSquare = position.getValue() + MoveData.directionOffsets[i];
			if (pawnCaptureSquare < 64 && board.contains(this.onSquare(pawnCaptureSquare))) {
				Piece pieceToCapture = board.get(board.indexOf(this.onSquare(pawnCaptureSquare)));
				int pawnSquareFile = pawnCaptureSquare % 8;
				if (Math.abs(file - pawnSquareFile) == 1 && !pieceToCapture.getColor().equals(color)) {
					moves.add(new Move(position.getValue(), pawnCaptureSquare));
				}
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}

}
