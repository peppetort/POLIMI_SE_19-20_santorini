package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.CardAlreadySetException;
import it.polimi.ingsw.Exceptions.SimpleGameException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represent one of the 2 or 3 players that play the match
 */
public class Player {

    private final String username;
    private final Worker worker1;
    private final Worker worker2;

    private final HashMap<String, Boolean> playerMenu = new HashMap<>();
    private final Game session;
    private final Color color;
    private Card card;
    private Turn turn;


    public Color getColor() {
        return color;
    }
    /**
     * Represent the winning condition, it depends on the {@link Card} (if the {@link Game} is not simple)
     */
    private Win winAction;
    /**
     * Represent the possible moveset, it depends on the {@link Card} (if the {@link Game} is not simple)
     */
    private Move moveAction;
    /**
     * Represent the possible buildset, it depends on the {@link Card} (if the {@link Game} is not simple)
     */
    private Build buildAction;

    /**
     * Constructor of the class {@link Player}.
     * <p>
     * The constructor initializes every possible move (of the player menu) at false value.
     * </p>
     *
     * @param username name of the {@link Player}
     * @param session reference of the {@link Game} session
     * @param color {@link Color} of the player
     */
    public Player(String username, Game session, Color color) {
        this.username = username;
        this.worker1 = new Worker(1, this);
        this.worker2 = new Worker(2, this);
        this.session = session;
        this.color = color;

        playerMenu.put("start", false);
        playerMenu.put("move", false);
        playerMenu.put("build", false);
        playerMenu.put("end", false);


        //Modalità di gioco senza carte
        if (session.isSimple()) {
            card = null;
            winAction = new DefaultWin(this);
            moveAction = new DefaultMove(this);
            buildAction = new DefaultBuild(this);
            turn = new DefaultTurn(this);
        }
    }

    public String getUsername() {
        return this.username;
    }

    public HashMap<String, Boolean> getPlayerMenu(){
        return this.playerMenu;
    }

    public Card getCard() {
        if (session.isSimple()) {
            throw new SimpleGameException("No card!");
        }
        return this.card;
    }

    /**
     * Set the choosen {@link Card}
     *
     * @param card
     * @throws RuntimeException if the {@link Game} is simple
     * @throws RuntimeException if the {@link Player} has already a {@link Card}
     * @throws RuntimeException if the {@link Card} doesn't exist
     */
    public void setCard(Card card) throws NullPointerException{
        if (session.isSimple()) {
            throw new SimpleGameException("Game mode: no cards!");
        } else if (this.card != null) {
            throw new CardAlreadySetException("Player " + this.getUsername() + " already has a card!");
        }

        this.card = card;

        switch (card.getName()) {
            case APOLLO:
                winAction = new DefaultWin(this);
                moveAction = new ApolloMove(this);
                buildAction = new DefaultBuild(this);
                turn = new DefaultTurn(this);
                break;
            case ARTEMIS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new ArtemisTurn(this);
                break;
            case ATHENA:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new AthenaTurn(this);
                break;
            case DEMETER:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new DemeterTurn(this);
                break;
            case HEPHASTUS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new HaphestusTurn(this);
                break;
            case PROMETHEUS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new PrometheusTurn(this);
                break;
            case ATLAS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new AtlasBuild(this);
                turn = new DemeterTurn(this);
                break;
            case MINOTAUR:
                winAction = new DefaultWin(this);
                moveAction = new MinotaurMove(this);
                buildAction = new DefaultBuild(this);
                turn = new DemeterTurn(this);
                break;
            case PAN:
                winAction = new PanWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new DefaultTurn(this);
                break;
            default:
                throw new RuntimeException("Unexpected case");
        }
    }

    public Worker getWorker1() {
        return this.worker1;
    }

    public Worker getWorker2() {
        return this.worker2;
    }

    public Game getSession() {
        return this.session;
    }

    public boolean equals(Player other) {
        return username.equals(other.username);
    }

    public Move getMoveAction() {
        return this.moveAction;
    }

    public Build getBuildAction() {
        return this.buildAction;
    }

    public Win getWinAction() {
        return this.winAction;
    }

    public Turn getTurn() {
        return this.turn;
    }

}
