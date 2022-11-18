package game.components;

import java.util.ArrayList;

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
	public void generateMoves(ArrayList<Piece> board) {
		moves = new ArrayList<Move>();
		System.out.println("Generating moves for: " + ANSI.YELLOW_BOLD + this.getClass().getSimpleName() + ANSI.RESET);
		for(int directionIndex = startDirIndex; directionIndex < endDirIndex; directionIndex++) {
			for(int n = 0; n < MoveData.numSquaresToEdge[position][directionIndex]; n++) {
				int targetSquare = position + MoveData.directionOffsets[directionIndex] * (n + 1);
				System.out.println("Target square index: " + ANSI.BLUE_BACKGROUND + targetSquare + ANSI.RESET);
				if(!isCapture(board, targetSquare)) {
					System.out.println(ANSI.RED + "Breaking..." + ANSI.RESET);
					break;
				}
				moves.add(new Move(position, targetSquare));
			}
		}
		System.out.println(ANSI.GREEN_UNDERLINED + "Finished generating moves!" + ANSI.RESET);
		moves.forEach(System.out::println);
		System.out.println();
	}

}