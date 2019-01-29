/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mandroid;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.panda.minterfaz.Input;

public class AInput implements Input, View.OnTouchListener {

    private ArrayList<TouchEvent> _touchEvents;


    public AInput(){

        _touchEvents = new ArrayList<TouchEvent>();
    }


    @Override
    public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> copy = (ArrayList)_touchEvents.clone();
        _touchEvents.clear();
        return copy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                _touchEvents.add(new TouchEvent(x,y,0,TouchEvent.Tipo.PULSACION));
                break;
            case MotionEvent.ACTION_MOVE:
                break;

        }

        return false;
    }
}
