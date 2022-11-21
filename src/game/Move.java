package game;

import test.ANSI;

public class Move {
	protected int startSquare;
	protected int targetSquare;

	public Move(int startSquare, int targetSquare) {
		this.startSquare = startSquare;
		this.targetSquare = targetSquare;
	}

	public int getStartSquare() {
		return startSquare;
	}

	public int getTargetSquare() {
		return targetSquare;
	}

	@Override
	public String toString() {
		// For debugging purposes
		return "Move [startSquare=" + ANSI.WHITE_UNDERLINED + startSquare + ANSI.RESET + ", targetSquare="
				+ ANSI.WHITE_UNDERLINED + targetSquare + ANSI.RESET + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return startSquare == ((Move) obj).getStartSquare() && targetSquare == ((Move) obj).getTargetSquare();
	}
}
