/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Image;
import ucm.gdv.panda.mlogica.Exceptions.IncorrectFileNameException;

public class ResourceManager {

    private static /*final*/ResourceManager _resourceManager;

    private ResourceManager(){}

    public static ResourceManager getResourceManager(){
        if(_resourceManager == null){
            _resourceManager = new ResourceManager();
        }
        return _resourceManager;
    }

    /*Load all the resources needed by the game. Create a Map who stores the Images
    * indexed by its color.
    * */
    public void LoadResources(Graphics graphics){

        _resources = new HashMap<String,Image>();
        String[] colors = {"00","01", "02", "03", "04", "05", "06", "07", "08", "09","10","11","12","13","14","15"};

        for (String c : colors) {
            String name = "ASCII_" + c + ".png";
            Image img = graphics.newImage(name);
            if(img != null)
                _resources.put(c,img);
            else{
                throw new IncorrectFileNameException(name);
            }
        }//For each color
    }

    //Return an image by its color.
    public Image getImage(String color){
        if(_resources.containsKey(color))
            return _resources.get(color);
        else
            return null;
    }

    /*This function creates and return an sprite. The sprite is created by a character and a color.
    * First, we obtain the spritesheet with the color wanted. Then, calculate the rect of the spritesheet
    * that agree with the character asked and return the Sprite object.*/
    public Sprite getSprite(char c, String color){

        Image spriteSheet = _resources.get(color);
        int asciiNum = (int)c;
        int xRectNum = asciiNum % 16;
        int yRectNum = asciiNum / 16;

        int xRect = xRectNum * 16;
        int yRect = yRectNum * 16;
        Sprite sprite = new Sprite(spriteSheet,xRect,yRect);
        return sprite;
    }
    Map<String,Image> _resources;
}
