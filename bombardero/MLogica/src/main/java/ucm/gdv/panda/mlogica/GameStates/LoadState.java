/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.GameStates;

import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.mlogica.Exceptions.IncorrectFileNameException;
import ucm.gdv.panda.mlogica.ResourceManager;

public class LoadState extends BombarderoState {


    public LoadState(Game game) {
        super(game);
    }


    @Override
    public void update() {}

    @Override
    public void render() {}

    @Override
    public void init(){
        ResourceManager resourceManager = ResourceManager.getResourceManager();
        try {
            resourceManager.LoadResources(_game.getGraphics());
        }catch (IncorrectFileNameException e){
            System.out.print(e);
            _game.stop();
        }
        _game.setState(new InstructionsState(_game));
    }

    @Override
    public void processInput(){}
}
