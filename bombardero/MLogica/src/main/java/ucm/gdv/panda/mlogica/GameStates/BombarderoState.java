/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.GameStates;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.GameState;
import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Input;
import ucm.gdv.panda.mlogica.Screen;

public abstract class BombarderoState implements GameState {




    public BombarderoState(Game game){
        _game = game;
        if(_game == null){
            //TODO: error
        }

        _input = game.getInput();
        _graphics = game.getGraphics();
    }

    @Override
    public void resize(int width, int height){
        Screen.getScreen().resize(width,height);
    }

    public void render(){
        Screen.getScreen().render(_graphics);
    }

    protected Game _game;
    protected Input _input;
    protected Graphics _graphics;



}
