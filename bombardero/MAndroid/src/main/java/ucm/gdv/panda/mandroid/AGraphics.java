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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Image;

public class AGraphics implements Graphics {


    private int _width, _height;
    private SurfaceView _surfaceView;
    private AssetManager _assetManager;
    private Canvas _canvas;
    private SurfaceHolder _mSurfaceHolder;


    AGraphics( AssetManager assetManager, SurfaceView surfaceView){

        _surfaceView = surfaceView;
        _assetManager = assetManager;




    }


    /*Carga una imagen almacenada en el contenedor
   de recursos de la aplicación a partir de su nombre*/
    public Image newImage(String name){

        Bitmap sprite = null;

        try{
            InputStream inputStream = _assetManager.open("Images/" + name);
            sprite = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e){
            //TODO: controlar la excepcion (mensaje, error)
        }
            return new AImage(sprite);
    }

    /*Borra el contenido completo de la ventana, rellenándolo
    con un color recibido como parámetro.*/
    public void clear(int color){

        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        //TODO: MIRAR COMO CAMBIAR EL COLOR
       _canvas.drawRGB(r,g,b);


    }

    @Override
    public void drawImage(Image image, int xScreen, int yScreen, int width, int height, int xSprite, int ySprite) {

        Rect dst = new Rect(xScreen, yScreen, xScreen + width,yScreen+height);
        Rect src = new Rect(xSprite, ySprite, xSprite + 16,ySprite + 16);

        _canvas.drawBitmap(((AImage)image)._sprite, src,dst, null);

    }

    @Override
    public boolean present() {

        _mSurfaceHolder.unlockCanvasAndPost(_canvas);
        return true;
    }

    @Override
    public boolean prepareBuffer() {

        _mSurfaceHolder = _surfaceView.getHolder();

        if(_mSurfaceHolder.getSurface().isValid()) {
                _canvas = _mSurfaceHolder.lockCanvas();
                _canvas.save();
                return true;
        }
        return false;

    }

    //Getters del tamaño de la ventana
    public int getWidth(){return _width ;}
    public int getHeigth(){return _height;}

    public void setWidth(int nWidth){_width = nWidth;}
    public void setHeight(int nHeight){_height = nHeight;}



}
