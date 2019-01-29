/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mescritorio;

import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Image;

public class EGraphics implements Graphics {

    private JFrame _window;
    private BufferStrategy _bufferStrategy;
    private java.awt.Graphics _currentBuffer;




    public EGraphics(JFrame window){

        _window = window;
        _window.createBufferStrategy(2);
        _bufferStrategy = _window.getBufferStrategy();


    }

    @Override
    public boolean present(){



        _bufferStrategy.getDrawGraphics().dispose();
        boolean contentRestored = _bufferStrategy.contentsRestored();
        if(contentRestored){
            return false;
        }
        else{
            _bufferStrategy.show();
            if(_bufferStrategy.contentsLost()){
                return false;
            }
            else return true;
        }

    }

    @Override
    public boolean prepareBuffer(){

        _currentBuffer = _window.getBufferStrategy().getDrawGraphics();
        if(_currentBuffer != null)
            return true;
        return false;

    }

    @Override
    public Image newImage(String name) {

        java.awt.Image img = null;

        try {
            img = ImageIO.read(new File("Resources/Images/" + name));
        } catch (IOException e) {
            System.out.println(e);
            return null;

        }

        return new EImage(img);
    }

    @Override
    public void clear(int color) {


        _currentBuffer.setColor(new Color(color));
        _currentBuffer.fillRect(0,0,_window.getWidth(),_window.getHeight());


    }


    @Override
    public void drawImage(Image image, int xScreen, int yScreen, int width, int height, int xSprite, int ySprite){
        _currentBuffer.drawImage(((EImage)image)._image,xScreen, yScreen, xScreen + width,
                yScreen + height,xSprite, ySprite,xSprite + 16, ySprite + 16, null);
    }
    @Override
    public int getWidth() {
        return _window.getWidth();
    }

    @Override
    public int getHeigth() {
        return _window.getHeight();
    }
}
