package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void DefaultTurnSimpleGame() {
        Board board = new Board();
        Game game = new Game("Pippo","Pluto",board,true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        assertEquals(true,controller.getTurn().get(player1));
        assertEquals(false,controller.getTurn().get(player2));
        assertEquals(true,controller.getFirstTurn());

        board.placePawn(player1.getWorker1(),0,0);
        board.placePawn(player1.getWorker2(),0,1);


        Message message = new PlayerStart(player1,player1.getWorker1());
        controller.update(message);

        message = new PlayerMove(player1,1,1);
        controller.update(message);
        assertEquals(player1.getWorker1(),board.getBox(1,1).getPawn());

        message = new PlayerBuild(player1,1,2);
        controller.update(message);
        assertEquals(1,board.getBox(1,2).getBlock().getValue());

        message = new PlayerEnd(player1);
        controller.update(message);

        assertEquals(false,controller.getTurn().get(player1));
        assertEquals(true,controller.getTurn().get(player2));

        board.getBox(2,2).build(Block.LTWO);
        board.getBox(2,3).build(Block.LTHREE);

        assertEquals(3,board.getBox(2,3).getBlock().getValue());
        assertEquals(2,board.getBox(2,2).getBlock().getValue());

        board.placePawn(player2.getWorker1(),2,2);
        board.placePawn(player2.getWorker2(),3,3);

        message = new PlayerStart(player2,player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2,2,3);
        controller.update(message);

        assertEquals(player2.getWorker1(),board.getBox(2,3).getPawn());
        assertEquals(Block.LTHREE,board.getBox(2,3).getBlock());
        assertEquals(true,player2.getTurn().won());
        assertEquals(true,controller.getOutcome().get(player2));
        assertEquals(false,controller.getOutcome().get(player1));

    }

    @Test
    public void cardTurn() {
        Board board = new Board();
        Game game = new Game("Pippo","Pluto",board,false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        ArrayList<Card> cards = new ArrayList<>();
        Card card1 = new Card(God.PAN);
        Card card2 = new Card(God.APOLLO);
        cards.add(card1);
        cards.add(card2);

        assertEquals(true,controller.getTurn().get(player1));
        assertEquals(false,controller.getTurn().get(player2));

        Message message = new DeckChoice(player1,cards);
        controller.update(message);

        assertEquals(true,controller.getFirstTurn());
        assertEquals(false,controller.getTurn().get(player1));
        assertEquals(true,controller.getTurn().get(player2));
        assertEquals(God.PAN,controller.getCards().get(0).getName());
        assertEquals(God.APOLLO,controller.getCards().get(1).getName());
        assertEquals(God.PAN,controller.getCards().get(0).getName());
        assertEquals(God.APOLLO,controller.getCards().get(1).getName());

        message = new CardChoice(player2,card1);
        controller.update(message);

        assertEquals(God.PAN,player2.getCard().getName());
        assertEquals(God.APOLLO,player1.getCard().getName());
        assertEquals(true,controller.getTurn().get(player1));
        assertEquals(false,controller.getTurn().get(player2));

    }

    @Test
    public void playerRemove() {
        Board board = new Board();
        Game game = new Game("Pippo","Pluto",board,true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        Message message = new PlacePawn(player1,0,0,1,1);
        controller.update(message);

        assertEquals(player1.getWorker1(),game.getBoard().getBox(0,0).getPawn());
        assertEquals(player1.getWorker2(),game.getBoard().getBox(1,1).getPawn());

        message = new PlacePawn(player2,2,2,3,3);
        controller.update(message);

        assertEquals(player2.getWorker1(),game.getBoard().getBox(2,2).getPawn());
        assertEquals(player2.getWorker2(),game.getBoard().getBox(3,3).getPawn());

        message = new PlayerRemove(player2);
        controller.update(message);
        assertNotEquals(player2.getWorker1(),game.getBoard().getBox(2,2).getPawn());
        assertNotEquals(player2.getWorker2(),game.getBoard().getBox(3,3).getPawn());
        assertFalse(controller.getOutcome().get(player2));
        assertEquals(1,controller.getLoosingPlayers());

        message = new PlayerStart(player1,player1.getWorker1());
        controller.update(message);
        assertEquals(true,controller.getOutcome().get(player1));
    }

    @Test
    public  void playerUnableToMove(){
        Board board = new Board();
        Game game = new Game("Pippo","Pluto",board,true);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Controller controller = new Controller(game);

        board.placePawn(player1.getWorker1(),0,0);
        board.placePawn(player1.getWorker2(),0,1);
        board.placePawn(player2.getWorker1(),4,4);
        board.placePawn(player2.getWorker2(),3,3);

        Message message = new PlayerStart(player1,player1.getWorker1());
        controller.update(message);
        message = new PlayerMove(player1,1,0);
        controller.update(message);
        message = new PlayerBuild(player1,0,0);
        controller.update(message);
        message = new PlayerEnd(player1);
        controller.update(message);

        assertEquals(true,controller.getTurn().get(player2));

        message = new PlayerStart(player2,player2.getWorker1());
        controller.update(message);
        message = new PlayerMove(player2,4,3);
        controller.update(message);
        message = new PlayerBuild(player2,4,4);
        controller.update(message);
        message = new PlayerEnd(player2);
        controller.update(message);

        board.getBox(0,0).build(Block.LTHREE);
        board.getBox(1,1).build(Block.LTHREE);
        board.getBox(2,0).build(Block.LTHREE);
        board.getBox(2,1).build(Block.LTHREE);
        board.getBox(1,2).build(Block.LTHREE);
        board.getBox(0,2).build(Block.LTHREE);

        message = new PlayerStart(player1,player1.getWorker1());
        controller.update(message);

        assertEquals(1,controller.getLoosingPlayers());
        assertEquals(false,controller.getOutcome().get(player1));
        assertEquals(true,controller.getOutcome().get(player2));



    }


}