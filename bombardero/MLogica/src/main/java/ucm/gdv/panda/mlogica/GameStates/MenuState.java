/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.GameStates;

import java.util.List;

import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.Input;
import ucm.gdv.panda.mlogica.Screen;

public class MenuState extends BombarderoState {

    private enum MenuSubState {SELECT_DIFFICULTY, SELECT_VELOCITY};

    private MenuSubState _currentState;
    private int _diffculty;
    private int _velocity;
    private final String BUTTON_COLOR =  "09";

    public MenuState(Game game) {
        super(game);

    }

    //-------------------------------------------- GameState override functions ---------------------------------
    @Override
    public void update() {

    }

    @Override
    public void render() {

        Screen.getScreen().render(_graphics);

    }

    @Override
    public void init() {

        Screen.getScreen().init(_graphics,0);

        _diffculty = 0;
        _velocity = 0;

        //Set current state to difficulty select
        _currentState = MenuSubState.SELECT_DIFFICULTY;

        renderDifficultyMenu();
    }

    @Override
    public void processInput() {

        List<Input.TouchEvent> events = _input.getTouchEvents();
        for(Input.TouchEvent e : events){
            switch (e.get_tipo()) {
                case PULSACION:
                    int[] cellClicked = Screen.getScreen().onTouch(e.getPosition()[0],e.getPosition()[1]);
                    if(_currentState == MenuSubState.SELECT_DIFFICULTY)
                        inputDiffcultyMenu(cellClicked);
                    else
                        inputVelocityMenu(cellClicked);
                    break;
                default:
                    break;
            }

        }

    }

    //---------------------------------------- MenuState 'sub-states' manage functions -----------------------------------
    /*There are two sub-states in this class, one for the velocity menu and one for the difficulty menu. */
    private void renderDifficultyMenu(){


        Screen.getScreen().printText("Dificultad",2,25,"01");
        Screen.getScreen().printText("Elija el nivel: 0(CRACK) a 5(PRINCIPIANTE)", 18,12, "05");

        Screen.getScreen().printText("0  1  2  3  4  5",20,22,"09");

    }

    private void renderVelocityMenu(){


        Screen.getScreen().printText("Velocidad",2,25,"01");
        Screen.getScreen().printText("Elija la velocidad: 0(MAX) a 9(MIN)", 18,12, "05");

        Screen.getScreen().printText("0  1  2  3  4",20,22,"09");
        Screen.getScreen().printText("5  6  7  8  9",23,22,"09");

    }

    private void inputDiffcultyMenu(int[] cellClicked){

        if(Screen.getScreen().getColorGrid()[cellClicked[0]][cellClicked[1]] == BUTTON_COLOR
                && Screen.getScreen().getGrid()[cellClicked[0]][cellClicked[1]] != ' '){
            _diffculty = Character.getNumericValue(Screen.getScreen().getGrid()[cellClicked[0]][cellClicked[1]]);
            changeMenuState(MenuSubState.SELECT_VELOCITY);
        }

    }

    private void inputVelocityMenu(int[] cellClicked){
        if(Screen.getScreen().getColorGrid()[cellClicked[0]][cellClicked[1]] == BUTTON_COLOR
                && Screen.getScreen().getGrid()[cellClicked[0]][cellClicked[1]] != ' '){
            _velocity = Character.getNumericValue(Screen.getScreen().getGrid()[cellClicked[0]][cellClicked[1]]);
            startGame();
        }

    }

    private void changeMenuState(MenuSubState m){
        _currentState = m;
        Screen.getScreen().clearGrids();
        if(_currentState == MenuSubState.SELECT_VELOCITY)
            renderVelocityMenu();
        else
            renderDifficultyMenu();

    }

    private void startGame(){
        PlayState playState = new PlayState(_game,_diffculty,_velocity);
        _game.setState(playState);
    }
}
