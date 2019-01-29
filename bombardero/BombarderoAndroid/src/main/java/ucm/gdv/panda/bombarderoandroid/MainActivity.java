/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.bombarderoandroid;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import ucm.gdv.panda.mandroid.AGame;
import ucm.gdv.panda.mlogica.GameStates.LoadState;

public class MainActivity extends Activity {

    AGame _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar and set orientation
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        _game = new AGame(this);
        _game.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(_game);
        _game.init();


        LoadState playState = new LoadState(_game);
        _game.setState(playState);

    }

    @Override
    protected void onResume(){
        super.onResume();
        _game.resume();
    }
    @Override
    protected  void onPause(){
        super.onPause();
        _game.pause();

    }


}
