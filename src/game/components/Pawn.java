package game.components;

import java.util.ArrayList;

import game.Move;
import game.MoveData;
import test.ANSI;

public class Pawn extends Piece {

	public Pawn(String color) {
		super(color + "Pawn");
	}
	
	@Override
	public void generateMoves(ArrayList<Piece> board) {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);
		int direction = color.equals("White") ? 0 : 1;
		int targetSquare = position + MoveData.directionOffsets[direction];
		if (targetSquare > 63 || targetSquare < 0)
			return;

		if (!isOccupied(board, targetSquare)) {
			moves.add(new Move(position, targetSquare));
		}

		if ((position / 8 == 1 && direction == 0) || (position / 8 == 6 && direction == 1)) {
			if (!isOccupied(board, targetSquare)) {				
				targetSquare += MoveData.directionOffsets[direction];
				if (!isOccupied(board, targetSquare)) {
					moves.add(new Move(position, targetSquare));
				}
			}
		}

		int file = position % 8;
		for (int i = 4 + direction; i <= 6 + direction; i += 2) {
			int pawnCaptureSquare = position + MoveData.directionOffsets[i];
			if (pawnCaptureSquare < 64 && board.contains(this.onSquare(pawnCaptureSquare))) {
				Piece pieceToCapture = board.get(board.indexOf(this.onSquare(pawnCaptureSquare)));
				int pawnSquareFile = pawnCaptureSquare % 8;
				if (Math.abs(file - pawnSquareFile) == 1 && !pieceToCapture.getColor().equals(color)) {
					moves.add(new Move(position, pawnCaptureSquare));
				}
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}

}
