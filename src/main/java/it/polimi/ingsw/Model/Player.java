package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.CardAlreadySetException;
import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.CardUpdateMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.HashMap;

/**
 * Represent one of the 2 or 3 players that play the match
 */
public class Player extends Observable<Message> {

    private final String username;
    private final Worker worker1;
    private final Worker worker2;

    private final HashMap<Actions, Boolean> playerMenu = new HashMap<>();
    private final Game session;
    private final Color color;
    private God card;
    private Turn turn;

    /**
     * Represent the winning condition, it depends on the {@link God} (if the {@link Game} is not simple)
     */
    private Win winAction;
    /**
     * Represent the possible moveset, it depends on the {@link God} (if the {@link Game} is not simple)
     */
    private Move moveAction;
    /**
     * Represent the possible buildset, it depends on the {@link God} (if the {@link Game} is not simple)
     */
    private Build buildAction;

    /**
     * Constructor of the class {@link Player}.
     * <p>
     * The constructor initializes every possible move (of the player menu) at false value.
     * </p>
     *
     * @param username name of the {@link Player}
     * @param session  reference of the {@link Game} session
     * @param color    {@link Color} of the player
     */
    public Player(String username, Game session, Color color) {
        this.username = username;
        this.worker1 = new Worker(1, this);
        this.worker2 = new Worker(2, this);
        this.session = session;
        this.color = color;

        playerMenu.put(Actions.DECK, false);
        playerMenu.put(Actions.CARD, false);
        playerMenu.put(Actions.PLACE, false);
        playerMenu.put(Actions.SELECT, false);
        playerMenu.put(Actions.MOVE, false);
        playerMenu.put(Actions.BUILD, false);
        playerMenu.put(Actions.END, false);
        playerMenu.put(Actions.UNDO, false);


        //Modalità di gioco senza carte
        if (session.isSimple()) {
            card = null;
            winAction = new DefaultWin(this);
            moveAction = new DefaultMove(this);
            buildAction = new DefaultBuild(this);
            turn = new DefaultTurn(this);
        }
    }

    public God getCard() {
        if (session.isSimple()) {
            throw new SimpleGameException("No card!");
        }
        return this.card;
    }

    /**
     * Set the choosen {@link God}
     *
     * @param card the card to be assigned to the player
     * @throws SimpleGameException if the {@link Game} is simple
     * @throws CardAlreadySetException if the {@link Player} has already a {@link God}
     * @throws RuntimeException if the {@link God} doesn't exist
     */
    public void setCard(God card) throws NullPointerException {
        if (session.isSimple()) {
            throw new SimpleGameException("Game mode: no cards!");
        } else if (this.card != null) {
            throw new CardAlreadySetException("Player " + this.getUsername() + " already has a card!");
        }

        this.card = card;

        switch (card) {
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
            case HEPHAESTUS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                turn = new HephaestusTurn(this);
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
                turn = new DefaultTurn(this);
                break;
            case MINOTAUR:
                winAction = new DefaultWin(this);
                moveAction = new MinotaurMove(this);
                buildAction = new DefaultBuild(this);
                turn = new DefaultTurn(this);
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


        CardUpdateMessage cardMessage = new CardUpdateMessage(card);
        notify(cardMessage);
    }

    public Color getColor() {
        return color;
    }

    public String getUsername() {
        return this.username;
    }

    public HashMap<Actions, Boolean> getPlayerMenu() {
        return this.playerMenu;
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
