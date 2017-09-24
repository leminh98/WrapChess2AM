package a2am.minhle.wrapchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ariel on 9/23/2017.
 */

public class Piece {
    //Bitmap to get character from image
    private Bitmap bitmap;

    //coords
    private int x;
    private int y;

    //boolean variable to determine whether or not piece shall be moved
    private boolean finalmove;

    //apply a parameter to Y coordinate such that piece cannot move off board
    private int maxY;
    private int minY;


    //constructor
    public Piece(Context context){
        x = 75;
        y = 50;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    //Updating the coordinates of a piece
    public void update(){
    //to update the x coordinate of a piece
        x++;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
