package it.polimi.ingsw.Client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    @Test
    public void prova(){
        Client prova = new Client("127.0.0.1", 12346, 1);
    }

}