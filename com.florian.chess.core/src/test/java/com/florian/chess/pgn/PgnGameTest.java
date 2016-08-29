package com.florian.chess.pgn;

import static org.junit.Assert.*;

import org.junit.Test;

public class PgnGameTest {

	@Test
	public void getInitialPosition_WhenItsAnOrdinaryPgnGame_ItsTheStartingPosition() {
		PgnGame game = new PgnGame();
		com.florian.chess.core.Position position = game.getInitialPosition();

		assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", position.getFenString());
	}

	@Test
	public void getInitialPosition_WhenItsAChess69Game_GameDoesntStartWithTheStartingPosition() {
		PgnGame game = new PgnGame();
		game.setFenString("1R6/7k/4bP1p/4N1p1/3pr3/1P6/3K4/8 w - - 0 54");

		com.florian.chess.core.Position position = game.getInitialPosition();
		assertEquals("1R6/7k/4bP1p/4N1p1/3pr3/1P6/3K4/8 w - - 0 54", position.getFenString());
	}

}
