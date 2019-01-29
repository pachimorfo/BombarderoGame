/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.bombarderoPC;

import ucm.gdv.panda.mescritorio.EGame;
import ucm.gdv.panda.mlogica.GameStates.LoadState;


public class BombarderoPC {

    public static void main(String[] args) {

        EGame juego = new EGame();
        LoadState playState = new LoadState(juego);
        juego.setState(playState);
        juego.tick();

    }
}
