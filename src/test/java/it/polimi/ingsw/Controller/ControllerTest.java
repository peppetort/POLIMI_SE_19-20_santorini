package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void DefaultTurnSimpleGamePawnPlacing() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);
        Message message;

        assertTrue(controller.getTurn().get(player2));
        assertFalse(controller.getTurn().get(player1));

        message = new PlacePawn(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlacePawn(player1, 1, 3, 4, 4);
        controller.update(message);

        assertEquals(player2.getWorker1(), board.getBox(0, 0).getPawn());
        assertEquals(player2.getWorker2(), board.getBox(3, 1).getPawn());
        assertEquals(player1.getWorker1(), board.getBox(1, 3).getPawn());
        assertEquals(player1.getWorker2(), board.getBox(4, 4).getPawn());

    }

    @Test
    public void DefaultTurnSimpleGameMove() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);
        Message message;

        message = new PlacePawn(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlacePawn(player1, 1, 3, 4, 4);
        controller.update(message);
        message = new PlayerStart(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2, 1, 0);
        controller.update(message);

        assertEquals(board.getBox(1, 0).getPawn(), player2.getWorker1());

    }


    @Test
    public void DefaultTurnSimpleGameBuild() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);
        Message message;


        message = new PlacePawn(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlacePawn(player1, 1, 3, 4, 4);
        controller.update(message);
        message = new PlayerStart(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuild(player2, 0, 0);
        controller.update(message);

        assertEquals(board.getBox(0, 0).getBlock(), Block.LONE);

    }


    @Test
    public void DefaultTurnSimpleGameEnd() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);
        Message message;


        message = new PlacePawn(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlacePawn(player1, 1, 3, 4, 4);
        controller.update(message);
        message = new PlayerStart(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuild(player2, 0, 0);
        controller.update(message);
        message = new PlayerEnd(player2);
        controller.update(message);

        assertFalse(player2.getPlayerMenu().get("start"));
        assertTrue(player1.getPlayerMenu().get("start"));

    }

    @Test
    public void cardTurn() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        Set<String> cards = new HashSet<>();
        cards.add("PAN");
        cards.add("APOLLO");
        cards.add("APOLLO");

        assertEquals(true, controller.getTurn().get(player2));
        assertEquals(false, controller.getTurn().get(player1));

        Message message = new DeckChoice(player1, cards);
        controller.update(message);

        assertEquals(controller.getCards().get(0).getName(), God.APOLLO);
        assertEquals(controller.getCards().get(1).getName(), God.PAN);
        assertEquals(controller.getCards().size(), 2);
        assertEquals(game.getCards().get(0).getName(), God.APOLLO);
        assertEquals(game.getCards().get(1).getName(), God.PAN);

        message = new CardChoice(player2, "PAN");
        controller.update(message);

        assertEquals(God.PAN, player2.getCard().getName());
        assertEquals(controller.getCards().size(), 1);
        assertEquals(game.getCards().size(), 1);


        message = new CardChoice(player1, "APOLLO");
        controller.update(message);

        assertEquals(God.APOLLO, player1.getCard().getName());
        assertEquals(controller.getCards().size(), 0);
        assertEquals(game.getCards().size(), 0);

    }

    @Test
    public void playerRemove() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        Message message = new PlacePawn(player2, 0, 0, 1, 1);
        controller.update(message);
        message = new PlacePawn(player1, 3,4, 4, 4);
        controller.update(message);

        message = new PlayerRemove(player2);
        controller.update(message);

        assertNull(game.getBoard().getBox(0, 0).getPawn());
        assertNull(game.getBoard().getBox(1, 1).getPawn());
        assertTrue(controller.getTurn().get(player1));
        assertEquals(controller.getTurn().size(), 1);
        assertEquals(controller.getTurn().size(), 1);
        assertEquals(game.getPlayers().size(), 1);
        assertTrue(controller.getOutcome().get(player1));

    }

    @Test
    public void playerUnableToMove() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);
        Message message;

        message = new PlacePawn(player2, 0, 0, 0, 1);
        controller.update(message);
        message = new PlacePawn(player1, 1, 3, 4, 4);
        controller.update(message);

        message = new PlayerStart(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuild(player2, 0, 0);
        controller.update(message);
        message = new PlayerEnd(player2);
        controller.update(message);

        assertEquals(true, controller.getTurn().get(player1));

        message = new PlayerStart(player1, player1.getWorker1());
        controller.update(message);
        message = new PlayerMove(player1, 2, 3);
        controller.update(message);
        message = new PlayerBuild(player1, 1, 3);
        controller.update(message);
        message = new PlayerEnd(player1);
        controller.update(message);

        board.getBox(0, 0).build(Block.LTHREE);
        board.getBox(1, 1).build(Block.LTHREE);
        board.getBox(2, 0).build(Block.LTHREE);
        board.getBox(2, 1).build(Block.LTHREE);
        board.getBox(1, 2).build(Block.LTHREE);
        board.getBox(0, 2).build(Block.LTHREE);

        message = new PlayerStart(player2, player2.getWorker1());
        controller.update(message);

        assertEquals(false, controller.getOutcome().get(player2));
        assertEquals(true, controller.getOutcome().get(player1));

    }


}