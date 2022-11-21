package game.components;

import java.util.ArrayList;

import game.Capture;
import game.Move;
import game.MoveData;
import test.ANSI;

public abstract class SlidingPiece extends Piece {

	protected int startDirIndex;
	protected int endDirIndex;

	public SlidingPiece() {
		super();
	}

	public SlidingPiece(String name) {
		super(name);
	}

	@Override
	public void generateMoves() {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);
		for (int directionIndex = startDirIndex; directionIndex < endDirIndex; directionIndex++) {
			for (int n = 0; n < MoveData.numSquaresToEdge[position.getValue()][directionIndex]; n++) {
				int targetSquare = position.getValue() + MoveData.directionOffsets[directionIndex] * (n + 1);
				System.out.println("Target square index: " + ANSI.BLUE_BACKGROUND + targetSquare + ANSI.RESET);
				if (!this.canMoveTo(targetSquare)) {
					System.out.println(ANSI.RED + "Breaking..." + ANSI.RESET);
					break;
				}

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
