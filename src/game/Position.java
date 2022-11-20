package game;

public class Position implements Comparable<Position> {

	private int position;
	private int file;
	private int rank;
	
	public Position(int value) {
		position = (value < 0) ? 0 : value;
		position = (value > 63) ? 63 : value;
		file = position % 8;
		rank = position / 8;
	}

	public Position(String fileAndRank) {
		if(fileAndRank.length() > 2) {
			throw new IllegalArgumentException("Input a position with two characters.");
		}
		file = fileAndRank.charAt(0) - 97;
		rank = Integer.parseInt(fileAndRank.substring(1)) - 1;
		position = file + rank * 8;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getFile() {
		return file;
	}
	
	public char getFileAsChar() {
		return (char)(file + 97);
	}

	public void setFile(int file) {
		this.file = file;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(Position o) {
		return position - o.getPosition();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position) {
			return position == ((Position) obj).getPosition(); 
		}
		return false;
	}

	@Override
	public String toString() {
		return "Position Index:" + String.format("dd", position) + " - " + getFileAsChar() + rank;
	}

}
