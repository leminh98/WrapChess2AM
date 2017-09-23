package a2am.minhle.wrapchess;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by ariel on 9/23/2017.
 */

public class GameView  extends SurfaceView implements Runnable {

    //boolean variable to track if player 1 has moved a piece or not
    volatile boolean movemade;

    //the game thread
    private Thread gameThread = null;


    //Class constructor
    public GameView(Context context) {
        super(context);

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

    }

    private void draw(){

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void wait() {
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
}
