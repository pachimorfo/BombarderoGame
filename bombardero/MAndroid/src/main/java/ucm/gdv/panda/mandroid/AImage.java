/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mandroid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import ucm.gdv.panda.minterfaz.Image;

public class AImage implements Image {

    Bitmap _sprite;


    AImage(Bitmap sprite){
        _sprite =  sprite;
    }


    //Getters para el tamaño de la imagen
    public int getWidth(){return _sprite.getWidth();};
    public int getHeight(){return _sprite.getHeight();};
}
