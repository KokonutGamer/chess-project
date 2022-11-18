package game;

import java.util.ArrayList;

public class MoveData {
	// Precomputed Move Data
	public static int[] directionOffsets = { 8, -8, -1, 1, 7, -7, 9, -9 };
	public static int[][] numSquaresToEdge;
	public static int[] allKnightJumps = { 15, 17, -17, -15, 10, -6, 6, -10 };
	public static int[][] knightMoves;
	public static int[][] kingMoves;

	public static void precomputeMoveData() {
		numSquaresToEdge = new int[64][];
		knightMoves = new int[64][];
		kingMoves = new int[64][];

		// Bottom left square of the board is index 0
		// Index increases by 1 when going one file to the right
		// Index increases by 8 when going one rank up
		for (int file = 0; file < 8; file++) {
			for (int rank = 0; rank < 8; rank++) {
				int numNorth = 7 - rank;
				int numSouth = rank;
				int numEast = 7 - file;
				int numWest = file;

				int squareIndex = rank * 8 + file;

				numSquaresToEdge[squareIndex] = new int[8];
				numSquaresToEdge[squareIndex][0] = numNorth;
				numSquaresToEdge[squareIndex][1] = numSouth;
				numSquaresToEdge[squareIndex][2] = numWest;
				numSquaresToEdge[squareIndex][3] = numEast;
				numSquaresToEdge[squareIndex][4] = Math.min(numNorth, numWest);
				numSquaresToEdge[squareIndex][5] = Math.min(numSouth, numEast);
				numSquaresToEdge[squareIndex][6] = Math.min(numNorth, numEast);
				numSquaresToEdge[squareIndex][7] = Math.min(numSouth, numWest);

				ArrayList<Integer> legalKnightJumps = new ArrayList<Integer>();
				for (int knightJumpDelta : allKnightJumps) {
					int knightJumpSquare = squareIndex + knightJumpDelta;
					if (knightJumpSquare >= 0 && knightJumpSquare < 64) {
						int knightSquareY = knightJumpSquare / 8;
						int knightSquareX = knightJumpSquare - knightSquareY * 8;
						int maxCoordMoveDist = Math.max(Math.abs(file - knightSquareX), Math.abs(rank - knightSquareY));
						if (maxCoordMoveDist == 2) {
							legalKnightJumps.add(knightJumpSquare);
						}
					}
				}
				knightMoves[squareIndex] = legalKnightJumps.stream().mapToInt(Integer::intValue).toArray();

				// Calculates legal king moves
				ArrayList<Integer> legalKingMoves = new ArrayList<Integer>();
				for (int kingMoveDelta : directionOffsets) {
					int kingMoveSquare = squareIndex + kingMoveDelta;
					if (kingMoveSquare >= 0 && kingMoveSquare < 64) {
						int kingSquareY = kingMoveSquare / 8;
						int kingSquareX = kingMoveSquare - kingSquareY * 8;
						int maxCoordMoveDist = Math.max(Math.abs(file - kingSquareX), Math.abs(rank - kingSquareY));
						if (maxCoordMoveDist == 1) {
							legalKingMoves.add(kingMoveSquare);
						}
					}
				}
				kingMoves[squareIndex] = legalKingMoves.stream().mapToInt(Integer::intValue).toArray();
			}
		}
	}
}
