package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import org.junit.Test;

import java.util.ArrayList;
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
        //TODO: SISTEMARE

        message = new PlayerPlacePawnsMessage(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 1, 3, 4, 4);
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
        //TODO: SISTEMARE
        message = new PlayerPlacePawnsMessage(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 1, 3, 4, 4);
        controller.update(message);

        message = new PlayerSelectMessage(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMoveMessage(player2, 1, 0);
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

        //TODO: SISTEMARE
        message = new PlayerPlacePawnsMessage(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 1, 3, 4, 4);
        controller.update(message);
        message = new PlayerSelectMessage(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMoveMessage(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuildMessage(player2, 0, 0);
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

        //TODO: SISTEMARE
        message = new PlayerPlacePawnsMessage(player2, 0, 0, 3, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 1, 3, 4, 4);
        controller.update(message);
        message = new PlayerSelectMessage(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMoveMessage(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuildMessage(player2, 0, 0);
        controller.update(message);
        message = new PlayerEndMessage(player2);
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

        ArrayList<God> cards = new ArrayList<>();
        cards.add(God.PAN);
        cards.add(God.APOLLO);
        cards.add(God.APOLLO);

        assertEquals(false, controller.getTurn().get(player2));
        assertEquals(true, controller.getTurn().get(player1));

        Message message = new PlayerDeckMessage(player1, cards);
        controller.update(message);

        assertEquals(controller.getCards().size(),2);

        for(God c: game.getCards()){
            assertTrue(((PlayerDeckMessage)message).getDeck().contains(c));
        }


        message = new PlayerCardChoiceMessage(player2, God.PAN);
        controller.update(message);

        assertEquals(God.PAN, player2.getCard());
        assertEquals(controller.getCards().size(), 1);

        message = new PlayerCardChoiceMessage(player1, God.APOLLO);
        controller.update(message);

        assertEquals(God.APOLLO, player1.getCard());
        assertEquals(controller.getCards().size(), 0);

    }

    @Test
    public void playerRemove() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        //TODO: SISTEMARE

        Message message = new PlayerPlacePawnsMessage(player2, 0, 0, 1, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 3,4, 4, 4);
        controller.update(message);

        message = new PlayerRemoveMessage(player2.getUsername());
        controller.update(message);

        assertNull(game.getBoard().getBox(0, 0).getPawn());
        assertNull(game.getBoard().getBox(1, 1).getPawn());
        //assertTrue(controller.getTurn().get(player1));
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

        //TODO: SISTEMARE
        message = new PlayerPlacePawnsMessage(player2, 0, 0, 0, 1);
        controller.update(message);
        message = new PlayerPlacePawnsMessage(player1, 1, 3, 4, 4);
        controller.update(message);

        message = new PlayerSelectMessage(player2, player2.getWorker1());
        controller.update(message);
        message = new PlayerMoveMessage(player2, 1, 0);
        controller.update(message);
        message = new PlayerBuildMessage(player2, 0, 0);
        controller.update(message);
        message = new PlayerEndMessage(player2);
        controller.update(message);

        assertEquals(true, controller.getTurn().get(player1));

        message = new PlayerSelectMessage(player1, player1.getWorker1());
        controller.update(message);
        message = new PlayerMoveMessage(player1, 2, 3);
        controller.update(message);
        message = new PlayerBuildMessage(player1, 1, 3);
        controller.update(message);
        message = new PlayerEndMessage(player1);
        controller.update(message);

        board.build(0, 0,Block.LTHREE);
        board.build(1, 1,Block.LTHREE);
        board.build(2, 0,Block.LTHREE);
        board.build(2, 1,Block.LTHREE);
        board.build(1, 2,Block.LTHREE);
        board.build(0, 2,Block.LTHREE);

        message = new PlayerSelectMessage(player2, player2.getWorker1());
        controller.update(message);

        assertEquals(false, controller.getOutcome().get(player2));
        assertEquals(true, controller.getOutcome().get(player1));

    }


}