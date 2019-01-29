/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.minterfaz;

import java.util.List;

public interface GameState {

    void update();

    void render();

    void init();

    void resize(int w, int h);

    void processInput();


}
