package a2am.minhle.wrapchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    //declaring gameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing gameView object
        gameView = new GameView(this);

        //adding it to contentview
        setContentView(gameView);
    }
}
