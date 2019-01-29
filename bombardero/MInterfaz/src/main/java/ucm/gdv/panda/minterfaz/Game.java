/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.minterfaz;

public interface Game {


    /*Devuelve la instancia del motor grafico.*/
    Graphics getGraphics();

    /*Devuelve la instancia del gestor de entrada*/
    Input getInput();

    /*Inicia los recursos*/

    void initResources();

    void setState(GameState state);

    int getMaxScore();

    void setMaxScore(int maxScore);

    void stop();

}
