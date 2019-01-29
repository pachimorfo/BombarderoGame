/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.GameStates;

import java.util.List;

import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.Input;
import ucm.gdv.panda.mlogica.Screen;

public class EndState extends BombarderoState {

    private int _score;

    public EndState(Game game, int score) {
        super(game);
        _score = score;

    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

        Screen.getScreen().init(_graphics,0);

        String scoreString = Integer.toString(_score);
        Screen.getScreen().printText("Ha conseguido " + scoreString + " puntos" , 10,20, "05");
        if(_score >_game.getMaxScore()) {
            Screen.getScreen().printText("BATIO EL RECORD!!", 20, 20, "05");
            _game.setMaxScore(_score);
        }
        else {
            String maxScoreString = Integer.toString(_game.getMaxScore());
            Screen.getScreen().printText("TU RECORD ESTA EN " + maxScoreString, 20, 20, "05");
        }
        Screen.getScreen().printText("Pulse para volver a empezar", 30,20,"05");

    }

    @Override
    public void processInput() {

        List<Input.TouchEvent> events = _input.getTouchEvents();
        for(Input.TouchEvent e : events){
            switch (e.get_tipo()) {
                case PULSACION:
                    playAgain();
                    break;
                default:
                    break;
            }

        }

    }

    public void playAgain(){
        MenuState menuState = new MenuState(_game);
        _game.setState(menuState);
    }
}
