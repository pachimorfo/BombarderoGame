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

public class InstructionsState extends BombarderoState {



    public InstructionsState(Game game) {
        super(game);

    }


    @Override
    public void update() {

    }

    @Override
    public void init() {

        Screen.getScreen().init(_game.getGraphics(),0);
        Screen.getScreen().printText("Usted esta pilotando un avion sobre una ciudad desierta " +
                "y tiene que pasar sobre los edificios para aterrizar y repostar. Su avion se " +
                "mueve de izquierda a derecha.@Al llegar a la derecha, el avion vuelve a salir por " +
                "la izquierda pero MAS BAJO. Dispone de un numero limitado de bombas y puede hacerlas " +
                "caer sobre los edificios pulsando en la pantalla.@Cada vez que aterriza, sube la altura de los edificios " +
                "y la velocidad.@UNA VEZ DISPARADA UNA BOMBA, YA NO PUEDE DISPARAR OTRA MIENTRAS NO HAYA " +
                "EXPLOSIONADO LA PRIMERA!!!!!", 0,0, "01");

        Screen.getScreen().printText("Pulse la pantalla para comezar",25,12,"02");
    }

    @Override
    public void processInput() {

        List<Input.TouchEvent> events = _input.getTouchEvents();
        for(Input.TouchEvent e : events){
            switch (e.get_tipo()) {
                case PULSACION:
                    start();
                    break;
                default:
                    break;
            }

        }

    }

    private void start(){

        MenuState menuState = new MenuState(_game);
        _game.setState(menuState);

    }
}
