package a2am.minhle.wrapchess;

/**
 * Created by minhle on 9/23/17.
 */

public class ChessEngine {
    //Representing the board as an 8x8 array. Capital are white, lowercase are black.
    // a means king (to prevent this from collide with knight. empty space means nothing is there
    static String chessBoard[][]={
            {"r","k","b","q","a","b","k","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","K","B","Q","A","B","K","R"}};

    //C means Capital, L means Lower, represent by number from 0 to 63 according to the board.
    static int kingPositionC, kingPositionL;

    public static void main(String[] args) {

        System.out.println(possibleMoves());

    }


    public static String possibleMoves () {
        //String format will be: x1 y1 x2 y2 capturePiece
        //x is the verticle, from top to bottom = 0 -> 7
        //y is the horizontal, from left to right = 0 ->7
        //'6151 ' means the a2 pawn moves to a3 and capture none

        String listOfMoves = "";
        for (int i = 0; i < 64; i ++) {
            switch ( chessBoard[i/8][i%8]) {
                case "P": listOfMoves += posibleP(i);
                    break;
                case "R": listOfMoves += posibleR(i);
                    break;
                case "K": listOfMoves += posibleK(i);
                    break;
                case "B": listOfMoves += posibleB(i);
                    break;
                case "Q": listOfMoves += posibleQ(i);
                    break;
                case "A": listOfMoves += posibleA(i);
                    break;
            }
        }
        return listOfMoves;
    }

    public static String posibleP(int i) {
        String list="";
        return list;
    }
    public static String posibleR(int i) {
        String list="";
        return list;
    }
    public static String posibleK(int i) {
        String list="";
        return list;
    }
    public static String posibleB(int i) {
        String list="";
        return list;
    }
    public static String posibleQ(int i) {
        String list="";
        return list;
    }
    public static String posibleA(int i) {
        String list="";
        String oldPiece;
        int row = i/8, col = i%8;

        //King have 9 possible moves (the 3x3 grid) and a castling movement
        // 0 1 2
        // 3 4 5
        // 6 7 8
        for (int pos = 0; pos < 9; pos++ ) {
            if (pos != 4) {
                 if(Character.isLowerCase(chessBoard[row - 1 + pos/3][col -1 + pos%3].charAt(0)) ||
                         chessBoard[row - 1 + pos/3][col -1 + pos%3].equals(" ")) {
                     oldPiece = chessBoard[row - 1 + pos/3][col -1 + pos%3];
                     chessBoard[row][col] = " ";
                     chessBoard[row -1 + pos/3][col - 1 + pos % 3] = "K";

                     if (kingSafe()) {
                         list = list + col + row + (col - 1 + pos % 3) + (row - 1 + pos / 3) + oldPiece;
                     }

                     chessBoard[row][col] = " ";
                     chessBoard[row - 1 + pos / 3][col - 1 + pos % 3] = oldPiece;

                 }
            }
        }
        return list;
    }

    private static boolean kingSafe() {
        return true;
    }
}

