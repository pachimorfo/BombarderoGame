/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;


import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.GameState;
import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Input;


public class AGame extends SurfaceView implements Game, Runnable {

    private AssetManager _assetManager;

    private AGraphics _graphics;
    private AInput _input;

    private GameState _currentState;

    private int _maxScore;

    volatile boolean _running;//Para que el compilador no lo cachee, le obligas a mirar en memoria siempre
    Thread _runningThread = null;

    public AGame( Context context){
            super(context,null);
        _assetManager = context.getAssets();
        _maxScore = 0;


    }


    public void init(){
        initResources();
    }

    public void setState(GameState state){
        _currentState = state;
        _currentState.init();
    }


    @Override
    public void run(){

        while(_running) {
            _currentState.processInput();
            _currentState.update();
            _currentState.render();

        }
    }

    public void stop(){
        _running = false;
    }

    @Override
    public void initResources(){

        int w = getWidth();
        _graphics = new AGraphics(_assetManager, this);

        //TODO: inicializar input
        _input = new AInput();
        setOnTouchListener(_input);


    }


    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    public void resume(){
        if(!_running){
            _running = true;
            _runningThread = new Thread(this);
            _runningThread.start();

        }

    }
    public void pause(){
        _running = false;
        while(true) {
            try {
                _runningThread.join();
                break;
            } catch (InterruptedException ie) {


            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        _graphics.setHeight(h);
        _graphics.setWidth(w);

        _currentState.resize(w,h);
    }

    @Override
    public void setMaxScore(int newMaxScore){
        if(newMaxScore > _maxScore)
            _maxScore = newMaxScore;
    }

    public int getMaxScore(){return _maxScore;}


}
