/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mescritorio;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JFrame;
import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.GameState;
import ucm.gdv.panda.minterfaz.Graphics;
import ucm.gdv.panda.minterfaz.Image;
import ucm.gdv.panda.minterfaz.Input;


public class EGame implements Game {

    private EGraphics _graphics;

    private EInput _input;

    private GameState _currentState;

    private boolean _running;

    private int _maxScore;


    private class Window extends JFrame implements ComponentListener{

        public Window(String title){
            super(title);
            getContentPane().addComponentListener(this);
        }

        public void componentHidden(ComponentEvent ce) {};
        public void componentShown(ComponentEvent ce) {};
        public void componentMoved(ComponentEvent ce) { };

        public void componentResized(ComponentEvent ce) {
            int height = this.getHeight();
            int width = this.getWidth();
            if(_currentState != null)
                _currentState.resize(width,height);

        }

    }

    public EGame() {
        initResources();
        _running = true;
        _maxScore = 0;

    }

    public void setState(GameState state){
        _currentState = state;
        _currentState.init();
    }

    // Create a simple GUI window
    private JFrame createWindow() {

        Window window = new Window("Bombardero");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.
        window.setSize(1280,720);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setIgnoreRepaint(true);
        return window;
    }

    /*Here we initializate all the resources: graphics and input*/
    public void initResources(){

        JFrame window = createWindow();
        _graphics = new EGraphics(window);

        _input = new EInput();
        window.addMouseListener(_input);


    }


    public void tick() {

        while (_running){
            _currentState.processInput();
            _currentState.update();
            _currentState.render();
        }
    }

    @Override
    public void setMaxScore(int newMaxScore){
        if(newMaxScore > _maxScore)
            _maxScore = newMaxScore;
    }

    public void stop(){
        _running = false;
    }


    @Override
    public int getMaxScore(){return _maxScore;}

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }
}
