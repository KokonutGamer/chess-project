package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import game.components.Piece;

public class Board {

	private ArrayList<Piece> pieces;

	public Board() {
		// Load inital starting position
		loadPosition("/files/PieceStartingPositions.csv");
	}

	public Board(String positionPath) {
		loadPosition(positionPath);
	}

	private void loadPosition(String positionPath) {
		String line;
		pieces = new ArrayList<Piece>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Board.class.getResourceAsStream(positionPath)))) {
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				String pieceType = tokens[1].substring(5);

				// Load the appropriate Class of the given pieceType
				Class<?> piece = Class.forName("game.components." + pieceType);
				Constructor<?> constructor = piece.getConstructor(String.class);
				Piece currentPiece = (Piece) constructor.newInstance(tokens[1].substring(0, 5));

				// Set the position of the piece
				// NOTE: ASCII value of 'a' = 97, ASCII value of 'h' = 104
				String position = tokens[0].toLowerCase();
				int fileAsInt = position.charAt(0) - 97;
				int rank = Integer.parseInt(position.substring(1)) - 1;
				currentPiece.setPosition(fileAsInt + rank * 8);

				pieces.add(currentPiece);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}
}
