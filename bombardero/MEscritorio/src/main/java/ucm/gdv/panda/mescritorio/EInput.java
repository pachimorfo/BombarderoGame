/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mescritorio;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import ucm.gdv.panda.minterfaz.Input;

public class EInput implements Input, MouseListener, MouseMotionListener {


    private ArrayList<TouchEvent> _touchEvents;


    EInput(){
        _touchEvents = new ArrayList<TouchEvent>();

    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        //_touchEvents.add(new TouchEvent(mouseEvent.getX(),mouseEvent.getY(),0,TouchEvent.Tipo.PULSACION));

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        _touchEvents.add(new TouchEvent(mouseEvent.getX(),mouseEvent.getY(),0,TouchEvent.Tipo.PULSACION));

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        //_touchEvents.add(new TouchEvent(mouseEvent.getX(),mouseEvent.getY(),0,TouchEvent.Tipo.PULSACION));


    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        boolean tue = true;
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public synchronized List<TouchEvent> getTouchEvents() {

        List<TouchEvent> copy = (ArrayList)_touchEvents.clone();
        if(!_touchEvents.isEmpty())
            _touchEvents.clear();
        return copy;
    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
