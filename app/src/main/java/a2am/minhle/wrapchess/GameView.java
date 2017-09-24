package a2am.minhle.wrapchess;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by ariel on 9/23/2017.
 */

public class GameView  extends SurfaceView implements Runnable {

    //boolean variable to track if player 1 has moved a piece or not
    volatile boolean movemade;

    //the game thread
    private Thread gameThread = null;

    //adding the piece to the class
    private Piece piece;

    //objects to be drawn
    private Paint paint;
    private android.graphics.Canvas canvas;
    private android.view.SurfaceHolder surfaceHolder;

    //Class constructor
    public GameView(Context context) {
        super(context);

        //initializing piece object
        piece = new Piece(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {
    //update frame
        update();

    //to draw the frame
        draw();

    //to control
        control();
    }

    private void update() {
        //Update the coordinate of our characters
        piece.update();
    }

    private void draw(){
        //draw the characters to the canvas
        //first check if surface is valid
        if (surfaceHolder.getSurface().isValid()){
            //lock the canvas
            canvas = surfaceHolder.lockCanvas();

            //draw a background color for canvas
            canvas.drawColor(Color.BLACK);

            //draw the piece
            canvas.drawBitmap(piece.getBitmap(), piece.getX(), piece.getY(), paint);

            //unlock the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
    //Control frames per second drawn
        try {
        //delaying thread causes frame rate to be ~60 fps
            gameThread.sleep(17);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void waiting() {
    //when waiting for player 2
        movemade = true;
        try{
        //stopping the thread
            gameThread.join();
        } catch(InterruptedException e){
        }
    }

    public void nextmove(){
        //when player 2 makes a move, and thus player 1 may move again
        //starting the thread again
        movemade = false;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                //do following when user presses screen
                //want to call all possible paths for one touch
                //want to move piece when double tapped

                break;
            case MotionEvent.ACTION_DOWN:
                //do following when user releases screen
                //want to either display possible paths or move a piece

                break;
        }
        return true;
    }
}
