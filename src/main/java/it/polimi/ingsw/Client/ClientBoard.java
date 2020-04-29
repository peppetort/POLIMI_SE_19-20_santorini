package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientBoard {

    private static class Box {
        private int level;
        private Color player;
        private Integer worker;

        public Box() {
            level = 0;
            player = null;
            worker = null;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setPlayer(Color player, int worker) {
            this.player = player;
            this.worker = worker;
        }

        public void clear() {
            this.player = null;
            this.worker = null;
        }

        public int getLevel() {
            return level;
        }

        public Color getPlayer() {
            return player;
        }

        public Integer getWorker() {
            return worker;
        }
    }

    private final Box[][] board = new Box[5][5];

    HashMap<Color, Box[]> playersLatestBoxes = new HashMap<>();

    public ClientBoard(ArrayList<Color> players) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                board[x][y] = new Box();
            }
        }

        for (Color p : players) {
            playersLatestBoxes.put(p, new Box[2]);
        }

        print();
    }

    public void lose(Color player) {
        Box[] workers = playersLatestBoxes.get(player);
        workers[0].clear();
        workers[1].clear();
        playersLatestBoxes.remove(player);
    }

    public void placePlayer(int x, int y, Color player, int worker) {
        board[x][y].setPlayer(player, worker);

        //TODO: rivedere nel caso di Apollo

        Box worker1 = playersLatestBoxes.get(player)[0];
        Box worker2 = playersLatestBoxes.get(player)[1];

        if (worker == 1) {
            if (worker1 != null) {
                worker1.clear();
            }
            worker1 = board[x][y];
            Box[] newArray = {worker1, worker2};
            playersLatestBoxes.replace(player, newArray);
        } else if (worker == 2) {
            if (worker2 != null) {
                worker2.clear();
            }
            worker2 = board[x][y];
            Box[] newArray = {worker1, worker2};
            playersLatestBoxes.replace(player, newArray);
        }

        print();
    }

    public void setLevel(int x, int y, int level) {
        board[x][y].setLevel(level);
        print();
    }


    //TODO: creare una classe CLI e spostare lì
    public void print() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";

        String title = "BOARD";
        String menuFormat;
        String bodyFormat;

        String lengthMarker = "-";
        String widthMarker = "+";

        int maxLength = 18;

        menuFormat = "%-";
        menuFormat += Integer.toString(11 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(12 + maxLength / 2);
        menuFormat += "s%n";

        int row = 0;

        int length = 2 * maxLength + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        bodyFormat = "%-" + (maxLength - 12) + "s%6s%6s%6s%6s%6s%" + (maxLength - 8) + "s";
        System.out.printf(bodyFormat, widthMarker, 0, 1, 2, 3, 4, widthMarker);
        System.out.print("\n");

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        for (int x = 0; x < 5; x++) {
            StringBuilder sRow = new StringBuilder(String.format("%-" + (maxLength - 12) + "s%s", widthMarker, row));
            for (int y = 0; y < 5; y++) {

                try {
                    switch (board[x][y].getPlayer()) {
                        case GREEN:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_GREEN + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case RED:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RED + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case BLUE:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_BLUE + board[x][y].getWorker() + ANSI_RESET));
                            break;
                    }
                } catch (NullPointerException e) {
                    sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RESET + 0));
                }
            }
            sRow.append(String.format("%" + (maxLength - 9) + "s", widthMarker));
            System.out.print(sRow);
            System.out.print("\n");
            System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
            row++;
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
        System.out.print("\n");

    }

}
