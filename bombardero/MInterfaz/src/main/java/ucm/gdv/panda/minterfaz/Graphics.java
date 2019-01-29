/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.minterfaz;

public interface Graphics {

    /*Carga una imagen almacenada en el contenedor
    de recursos de la aplicación a partir de su nombre*/
    Image newImage(String name);

    /*Borra el contenido completo de la ventana, rellenándolo
    con un color recibido como parámetro.*/
    void clear(int color);

    /*Recibe una imagen y la muestra en
    la pantalla.*/
    void drawImage(Image image, int xScreen, int yScreen, int width, int height, int xSprite, int ySprite);

    boolean present();

    boolean prepareBuffer();

    //Getters del tamaño de la ventana
    int getWidth();
    int getHeigth();
}
