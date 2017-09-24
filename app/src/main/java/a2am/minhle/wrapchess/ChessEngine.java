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
        String list="", oldPiece;

        int row = i/8, col = i%8;

        for (int j = -1; j <= 1; j += 2) {
            try {
                if (Character.isLowerCase(chessBoard[row - 1][col + j].charAt(0))) {
                    if (row >= 2) { // capturing
                        oldPiece = chessBoard[row - 1][col + j];
                        chessBoard[row][col] = " ";
                        chessBoard[row - 1][col + j] = "P";

                        if (kingSafe()) {
                            list = list + col + row + (col + j) + (row - 1) + oldPiece;
                        }

                        chessBoard[row][col] = "P";
                        chessBoard[row - 1][col + j] = oldPiece;
                    } else { // capturing while promoting
                        String[] temp = {"Q", "K", "R", "B"};
                        for (int k = 0; k < 4; k++) {
                            oldPiece = chessBoard[row - 1][col + j];
                            chessBoard[row][col] = " ";
                            chessBoard[row - 1][col + j] = temp[k];
                            if (kingSafe()) { // has different inputs, column, column, captured piece, new piece, P
                                list = list + col + (col + j) + oldPiece + temp[k] + "P";
                            }
                            chessBoard[row][col] = "P";
                            chessBoard[row - 1][col + j] = oldPiece;
                        }
                    }
                }
            } catch (Exception e) {}
        }
        try {
            if (" ".equals(chessBoard[row-1][col])) {
                if (row > 1) { // Move up one
                    chessBoard[row][col] = " ";
                    chessBoard[row - 1][col] = "P";

                    if (kingSafe()) {
                        list = list + col + row + (col) + (row - 1) + " ";
                    }

                    chessBoard[row][col] = "P";
                    chessBoard[row - 1][col] = " ";
                } else { // Move up one and promote
                    String[] temp = {"Q", "K", "R", "B"};
                    for (int k = 0; k < 4; k++) {
                        chessBoard[row][col] = " ";
                        chessBoard[row - 1][col] = temp[k];
                        if (kingSafe()) {
                            list = list + col + (col) + " " + temp[k] + "P";
                        }
                        chessBoard[row][col] = "P";
                        chessBoard[row - 1][col] = " ";
                    }
                }
            }
        } catch {Exception e} {}

        if(row == 6) {
            if (" ".equals(chessBoard[row-2][col])) {
                chessBoard[row][col] = " ";
                chessBoard[row - 2][col] = "P";

                if (kingSafe()) {
                    list = list + col + row + (col) + (row - 2) + " ";
                }

                chessBoard[row][col] = "P";
                chessBoard[row - 2][col] = " ";
            }
        }

        return list;
    }
    public static String posibleR(int i) {
        String list="", oldPiece;
        int row = i/8, col = i%8;

        int temp;

        // Rooks keep going either horizontal or vertical
        for (int j = -1; j <= -1; j+=2) {
            temp = 1;
            try {
                while (" ".equals(chessBoard[row + j * temp][col])) {
                    oldPiece = chessBoard[row + j * temp][col];
                    chessBoard[row][col] = " ";
                    chessBoard[row + j * temp][col] = "R";

                    if (kingSafe()) {
                        list = list + col + row + (col) + (row + j * temp) + oldPiece;
                    }

                    chessBoard[row][col] = "R";
                    chessBoard[row + j * temp][col] = oldPiece;
                    temp++;
                }

                if (Character.isLowerCase(chessBoard[row + j * temp][col].charAt(0))) {
                    oldPiece = chessBoard[row + j * temp][col];
                    chessBoard[row][col] = " ";
                    chessBoard[row + j * temp][col] = "R";

                    if (kingSafe()) {
                        list = list + col + row + (col) + (row + j * temp) + oldPiece;
                    }

                    chessBoard[row][col] = "R";
                    chessBoard[row + j * temp][col] = oldPiece;
                }
            } catch (Exception e) {}

            temp = 1;
            try {
                while (" ".equals(chessBoard[row][col + j * temp])) {
                    oldPiece = chessBoard[row][col + j * temp];
                    chessBoard[row][col] = " ";
                    chessBoard[row][col + j * temp] = "R";

                    if (kingSafe()) {
                        list = list + col + row + (col + j * temp) + (row) + oldPiece;
                    }

                    chessBoard[row][col] = "R";
                    chessBoard[row][col + j * temp] = oldPiece;
                    temp++;
                }

                if (Character.isLowerCase(chessBoard[row][col + j * temp].charAt(0))) {
                    oldPiece = chessBoard[row][col + j * temp];
                    chessBoard[row][col] = " ";
                    chessBoard[row][col + j * temp] = "R";

                    if (kingSafe()) {
                        list = list + col + row + (col + j * temp) + (row) + oldPiece;
                    }

                    chessBoard[row][col] = "R";
                    chessBoard[row][col + j * temp] = oldPiece;
                }
            } catch (Exception e) {}

        }
        return list;
    }
    public static String posibleK(int i) {
        String list="", oldPiece;

        int row = i/8, col = i%8;

        // Knight can go one in one direction and two in another
        for (int j = -1; j <= -1; j+=2) {
            for (int k = -1; k <= -1; k+=2) {
                try {
                    while (" ".equals(chessBoard[row + j * 2][col + k]) ||
                            Character.isLowerCase(chessBoard[row + j * 2][col + k].charAt(0))) {
                        oldPiece = chessBoard[row + j * 2][col + k];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j * 2][col + k] = "K";

                        if (kingSafe()) {
                            list = list + col + row + (col + k) + (row + j * 2) + oldPiece;
                        }

                        chessBoard[row][col] = "K";
                        chessBoard[row + j * 2][col + k] = oldPiece;
                    }
                } catch (Exception e) {}

                try {
                    while (" ".equals(chessBoard[row + j ][col + k * 2]) ||
                            Character.isLowerCase(chessBoard[row +j][col + k * 2].charAt(0))) {
                        oldPiece = chessBoard[row + j][col + k * 2];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j][col + k * 2] = "K";

                        if (kingSafe()) {
                            list = list + col + row + (col + k * 2) + (row + j) + oldPiece;
                        }

                        chessBoard[row][col] = "K";
                        chessBoard[row + j][col + k * 2] = oldPiece;
                    }
                } catch (Exception e) {}
            }
        }

        return list;
    }
    public static String posibleB(int i) {
        String list="", oldPiece;

        int row = i/8, col = i%8;

        // Bishop is like Queen, but only diagonal
        int temp;
        for (int j = -1; j <= -1; j+=2) {
            for (int k = -1; k <= -1; k+=2) {
                temp = 1;
                try {
                    while (" ".equals(chessBoard[row + j * temp][col + k * temp])) {
                        oldPiece = chessBoard[row + j * temp][col + k * temp];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j * temp][col + k * temp] = "B";

                        if (kingSafe()) {
                            list = list + col + row + (col + k * temp) + (row + j * temp) + oldPiece;
                        }

                        chessBoard[row][col] = "B";
                        chessBoard[row + j * temp][col + k * temp] = oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[row + j * temp][col + k * temp].charAt(0))) {
                        oldPiece = chessBoard[row + j * temp][col + k * temp];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j * temp][col + k * temp] = "B";

                        if (kingSafe()) {
                            list = list + col + row + (col + k * temp) + (row + j * temp) + oldPiece;
                        }

                        chessBoard[row][col] = "B";
                        chessBoard[row + j * temp][col + k * temp] = oldPiece;
                    }
                } catch (Exception e) {}
            }
        }


        return list;
    }
    public static String posibleQ(int i) {
        String list="";
        String oldPiece;
        int row = i/8, col = i%8;

        //Queen can go in any of 8 directions
        int temp;
        for (int j = -1; j <= -1; j++) {
            for (int k = -1; k <= -1; k++) {
                temp = 1;
                try {
                    while (" ".equals(chessBoard[row + j * temp][col + k * temp])) {
                        oldPiece = chessBoard[row + j * temp][col + k * temp];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j * temp][col + k * temp] = "Q";

                        if (kingSafe()) {
                            list = list + col + row + (col + k * temp) + (row + j * temp) + oldPiece;
                        }

                        chessBoard[row][col] = "Q";
                        chessBoard[row + j * temp][col + k * temp] = oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[row + j * temp][col + k * temp].charAt(0))) {
                        oldPiece = chessBoard[row + j * temp][col + k * temp];
                        chessBoard[row][col] = " ";
                        chessBoard[row + j * temp][col + k * temp] = "Q";

                        if (kingSafe()) {
                            list = list + col + row + (col + k * temp) + (row + j * temp) + oldPiece;
                        }

                        chessBoard[row][col] = "Q";
                        chessBoard[row + j * temp][col + k * temp] = oldPiece;
                    }

                } catch (Exception e) {}





            }
        }

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
                try {
                    if (Character.isLowerCase(chessBoard[row - 1 + pos / 3][col - 1 + pos % 3].charAt(0)) ||
                            chessBoard[row - 1 + pos / 3][col - 1 + pos % 3].equals(" ")) {
                        oldPiece = chessBoard[row - 1 + pos / 3][col - 1 + pos % 3];
                        chessBoard[row][col] = " ";
                        chessBoard[row - 1 + pos / 3][col - 1 + pos % 3] = "A";

                        if (kingSafe()) {
                            list = list + col + row + (col - 1 + pos % 3) + (row - 1 + pos / 3) + oldPiece;
                        }

                        chessBoard[row][col] = "A";
                        chessBoard[row - 1 + pos / 3][col - 1 + pos % 3] = oldPiece;

                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }

    private static boolean kingSafe() {

        int row = kingPositionC/8, col = kingPositionC%8;

        // Bishop / Queen
        int temp;
        for (int j = -1; j <= -1; j+=2) {
            for (int k = -1; k <= -1; k+=2) {
                temp = 1;
                try {
                    while (" ".equals(chessBoard[row + j * temp][col + k * temp])) { temp++; }
                    if ("b".equals(chessBoard[row + j * temp][col + k * temp]) ||
                            "q".equals(chessBoard[row + j * temp][col + k * temp])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }

        // Rook / Queen
        for (int j = -1; j<= 1; j+=2) {
            temp = 1;
            try {
                while (" ".equals(chessBoard[row + j * temp][col])) { temp++; }
                if ("r".equals(chessBoard[row + j * temp][col]) ||
                        "q".equals(chessBoard[row + j * temp][col])) {
                    return false;
                }

            } catch(Exception e) {}

            temp = 1;
            try {
                while (" ".equals(chessBoard[row][col + j * temp])) { temp++; }
                if ("r".equals(chessBoard[row][col + j * temp]) ||
                        "q".equals(chessBoard[row][col + j * temp])) {
                    return false;
                }

            } catch(Exception e) {}
        }

        // Knight
        for (int j = -1; j  <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                try {
                    if ("k".equals(chessBoard[row + j * 2][col + k])) {return false;}

                } catch (Exception e) {}

                try {
                    if ("k".equals(chessBoard[row + j][col + k * 2])) {return false;}

                } catch (Exception e) {}

            }
        }

        // Pawn
        if (row <= 1) {
            try {
                if ("p".equals(chessBoard[row - 1][col -1])) {return false;}
            } catch (Exception e) {}

            try {
                if ("p".equals(chessBoard[row - 1][col +1])) {return false;}
            } catch (Exception e) {}

        }

        // King
        if (((row - kingPositionL/8) <= 1 || row - kingPositionL/8 >= -1) &&
                (col - kingPositionL%8 <= 1) || col - kingPositionL%8 >= -1) {
            return false;
        }

        return true;
    }
}

