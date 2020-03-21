package it.polimi.ingsw.Model;

public class Player {

    private String username;
    private Card card;
    private Worker worker1;
    private Worker worker2;

    private Game session;

    //Classi delle azioni
    private Win winAction;
    private Move moveAction;
    private Build buildAction;

    public Player(String username, Game session){
        this.username = username;
        this.worker1 = new Worker(username+"1");
        this.worker2 = new Worker(username+"2");
        this.session = session;

        //Modalità di gioco senza carte
        if(!session.isSimple()){
            card = null;
            winAction = new DefaultWin(this);
            moveAction = new DefaultMove(this);
            buildAction = new DefaultBuild(this);
        }
    }

    public String getUsername(){
        return this.username;
    }

    public Card getCard(){ return this.card; }

    public Worker getWorker1(){
        return this.worker1;
    }

    public Worker getWorker2(){
        return this.worker2;
    }

    public Game getSession(){return this.session;}

    public void setCard(Card card){
        if(!session.isSimple()){
            throw new RuntimeException("Game mode: no cards!");
        }else if(this.card != null){
            throw new RuntimeException("Player " + this.getUsername() + " already has a card!");
        }

        this.card = card;

        switch (card.getName()){
            case APOLLO:
                winAction = new DefaultWin(this);
                moveAction = new ApolloMove(this);
                buildAction = new DefaultBuild(this);
                break;
            case ARTEMIS:
            case ATHENA:
            case DEMETER:
            case HEPHASTUS:
            case PROMETHEUS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                break;
            case ATLAS:
                winAction = new DefaultWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new AtlasBuild(this);
                break;
            case MINOTAUR:
                winAction = new DefaultWin(this);
                moveAction = new MinotaurMove(this);
                buildAction = new DefaultBuild(this);
                break;
            case PAN:
                winAction = new PanWin(this);
                moveAction = new DefaultMove(this);
                buildAction = new DefaultBuild(this);
                break;
            default:
                throw new RuntimeException("Unexpected case");
        }
    }


}
