package it.polimi.ingsw.Model;

/**
 * Rappresenta il giocatore
 */
public class Player {

    private String username;
    /**
     * Rappresenta la carta asseganta al giocatore.
     * Se la carta non è assegnata assume valore null.
     */
    private Card card;
    private Turn turn;
    private Worker worker1;
    private Worker worker2;

    /**
     * Rappresenta la sessione di gioco a cui il giocatore sta partecipando
     */
    private Game session;

    /**
     * Rappresenta la condizione di vittoria del giocatore
     */
    private Win winAction;
    /**
     * Rappresenta la mossa del giocatore
     */
    private Move moveAction;
    /**
     * Rappresetna la costruzione del giocatore
     */
    private Build buildAction;

    /**
     * Costruttore della classe {@link Player}.
     * <p>
     * Il costruttore setta il nome del giocatore, la sessione di gioco, le sue pedine {@link Worker} e
     * nel caso la modalità di gioco è impostata senza carte, istanzia le
     * classi di default {@link DefaultWin}, {@link DefaultMove}, {@link DefaultBuild}
     * assegnadole agli attributi {@link #winAction}, {@link #moveAction}, {@link #buildAction}.
     * Ai costruttori dei due {@link Worker} viene passato una stringa composta dal {@link #username} del
     * {@link Player} concatenato con la stringa "1" o "2" a seconda si tratti si {@link #worker1} o {@link #worker2}
     * </p>
     *
     * @param username setta il nome del giocatore
     * @param session  assegna la sessione di gioco
     */
    public Player(String username, Game session) {
        this.username = username;
        this.worker1 = new Worker(username + "1");
        this.worker2 = new Worker(username + "2");
        this.session = session;

        //Modalità di gioco senza carte
        if (!session.isSimple()) {
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

    public Card getCard() {
        return this.card;
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

    /**
     * Setta la carta del giocatore
     * <p>
     * Oltre a settare l'attributo {@link #card},
     * assegna ad uno degli attributi {@link #winAction}, {@link #moveAction},
     * {@link #buildAction} l'istanza della classe specifica della carta settata.
     * Agli altri attributi, assegna l'istanza delle classi di default
     * </p>
     *
     * @param card la carta da settare
     * @throws RuntimeException se la sessione di gioco è senza carte
     * @throws RuntimeException se il giocatore ha già una carta assegnata
     * @throws RuntimeException se il nome della carta assegnata è sconosciuto
     */
    public void setCard(Card card) {
        if (!session.isSimple()) {
            throw new RuntimeException("Game mode: no cards!");
        } else if (this.card != null) {
            throw new RuntimeException("Player " + this.getUsername() + " already has a card!");
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

    public boolean equals(Player other) {
        return username.equals(other.username) && card.getName() == other.card.getName();
    }

    public Move getMoveAction(){
        return this.moveAction;
    }

    public Build getBuildAction(){
        return this.buildAction;
    }

    public Win getWinAction(){
        return this.winAction;
    }

    public Turn getTurn(){
        return this.turn;
    }

}
