package Model;

import java.util.Scanner;

public class Player {
    private static int playerNum = 0;
    private static Marks senteMark = null; //sente(先手)'s Mark :)
    Marks playerMark;
    private String playerName;
    Marks oppositeMark;


    Player() {
        loadPlayerInfo();
    }

    private void loadPlayerInfo() {
        playerName = "Player " + ++playerNum + ""; //default player name

        if (senteMark == null) {
            System.out.println(playerName + ", you want to play as X or O?");
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNext("[oOxX]")) {
                System.out.println("Error input! Please input again: ");
                scanner.next();
            }
            if (scanner.hasNext("[xX]")){
                playerMark = Marks.X;
                oppositeMark = Marks.O;
            }
            else{
                playerMark = Marks.O;
                oppositeMark = Marks.X;
            }
            senteMark = playerMark;
        }
        else if (senteMark.equals(Marks.X)){
            playerMark = Marks.O;
            oppositeMark = Marks.X;
        }
        else{
            playerMark = Marks.X;
            oppositeMark = Marks.O;
        }
    }

    public int[] mark(CheckBoard board) {
        System.out.print(playerName + ", enter your x (0-2) and y (0-2) position: ");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("[0-2\u0020]+")){    //match 0, 1, 2 and space
            System.out.println("Error input! Please input again: ");
            scanner.next();
        }
        int x = scanner.nextInt();
        int y = scanner.nextInt();
//        System.out.println(x + " " + y);
        return new int[] {x, y};
    }

    public Marks getPlayerMark() {
        return playerMark;
    }

    public void setPlayerMark(Marks playerMark) {
        this.playerMark = playerMark;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    Marks getOppositeMark() {
        return oppositeMark;
    }

    @Override
    public String toString() {
        return playerName;
    }
}
