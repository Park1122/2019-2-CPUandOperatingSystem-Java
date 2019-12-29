package power;

import hardware.Board;

public class Power {

	public static void main(String[] args) {
		System.out.println("Computer Power ON!!!");
		Board board = new Board();
		board.run();
	}

}
