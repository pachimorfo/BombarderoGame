/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mescritorio;


import ucm.gdv.panda.minterfaz.Image;

public class EImage implements Image {


    public java.awt.Image _image;

    public EImage(java.awt.Image img){
        _image = img;
    }

    //Getters para el tamaño de la imagen
    @Override
    public int getWidth(){return _image.getWidth(null);}
    @Override
    public int getHeight(){return _image.getHeight(null);}
}

