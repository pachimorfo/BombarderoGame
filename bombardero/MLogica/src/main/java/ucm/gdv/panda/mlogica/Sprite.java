/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica;

import ucm.gdv.panda.minterfaz.Image;

public class Sprite{


    public Sprite(Image image, int x1, int y1){
        _image = image;
        _x1 = x1;
        _y1 = y1;
    }

    public int[] getImageRect(){
        int rect[] = {_x1, _y1};
        return  rect;
    }

    public Image getImage(){
        return _image;
    }


    int _x1, _y1;
    private Image _image;
}

