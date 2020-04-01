package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void DefaultTurnNoErr() {
        Board board = new Board();
        Game game = new Game("Marco","Giovanni",board,false);
        Player player1 = new Player("Marco",game);
        Player player2 = new Player("Giovanni",game);
        Controller controller = new Controller(game);
        board.placePawn(game.getPlayers().get(0).getWorker1(),0,0);
        board.placePawn(game.getPlayers().get(1).getWorker1(),2,2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        InitializePlayersMessage iMessage = new InitializePlayersMessage(players);
        controller.initializePlayers(iMessage);

        ArrayList<Card> deck = new ArrayList<>();
        Card c1 = new Card(God.PAN);
        Card c2 = new Card(God.DEMETER);
        deck.add(c1);
        deck.add(c2);
        DeckChoice deckChoice = new DeckChoice(player1,deck);

        assertEquals("Marco",game.getPlayers().get(0).getUsername());

        //The View notifies the controller which update itself with the message DeckChoice as a parameter
        assertEquals(true,controller.getTurn().get(game.getPlayers().get(0)));
        assertEquals(false,controller.getTurn().get(game.getPlayers().get(1)));
        controller.update(deckChoice);
        assertEquals(true,controller.getFirstTurn());
        assertEquals(God.PAN,game.getCards().get(0).getName());
        assertEquals(God.DEMETER,game.getCards().get(1).getName());
        //The turn has been updated
        assertEquals(false,controller.getTurn().get(game.getPlayers().get(0)));
        assertEquals(true,controller.getTurn().get(game.getPlayers().get(1)));

        //Player2 has to choose the card and he wants PAN
        CardChoice cardChoice = new CardChoice(player2,game.getCards().get(0));
        controller.update(cardChoice);
        assertEquals(God.PAN,game.getPlayers().get(1).getCard().getName());
        assertEquals(God.DEMETER,game.getPlayers().get(0).getCard().getName());
        assertEquals(true,controller.getTurn().get(game.getPlayers().get(0)));
        assertEquals(false,controller.getTurn().get(game.getPlayers().get(1)));

        //now the game has to start, player 1 can move 1 time and build one block
        //p1S is the message of startTurn for player1 with the first of his workers

        PlayerStart p1S = new PlayerStart(player1,player1.getWorker1());
        controller.update(p1S);
        assertEquals(true,game.getPlayers().get(0).getTurn() instanceof DemeterTurn);
        PlayerMove p1M = new PlayerMove(player1,0,1);
        PlayerBuild p1B = new PlayerBuild(player1,0,2);
        controller.update(p1M);
        assertEquals(game.getPlayers().get(0).getWorker1(),board.getBox(0,1).getPawn());
        controller.update(p1B);
        assertEquals(Block.LONE,board.getBox(0,2).getBlock());
        PlayerBuild p2B = new PlayerBuild(player1,1,2);
        controller.update(p2B);
        assertEquals(Block.LONE,board.getBox(1,2).getBlock());
        PlayerEnd p1e = new PlayerEnd(player1);
        controller.update(p1e);
        assertEquals(false,controller.getTurn().get(game.getPlayers().get(0)));
        assertEquals(true,controller.getTurn().get(game.getPlayers().get(1)));

    }
    @Test
    public void testTurnInterrupt() {
        Board board = new Board();
        Game game = new Game("Marco","Giovanni",board,false);
        Player player1 = new Player("Marco",game);
        Player player2 = new Player("Giovanni",game);
        Controller controller = new Controller(game);
        board.placePawn(game.getPlayers().get(0).getWorker1(),0,0);
        board.placePawn(game.getPlayers().get(1).getWorker1(),2,2);
    }























}