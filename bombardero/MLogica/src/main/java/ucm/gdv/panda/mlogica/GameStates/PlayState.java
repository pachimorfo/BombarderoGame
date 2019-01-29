/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.GameStates;

import java.util.LinkedList;
import java.util.List;

import ucm.gdv.panda.minterfaz.Game;
import ucm.gdv.panda.minterfaz.Input;
import ucm.gdv.panda.mlogica.Screen;


public class PlayState extends BombarderoState {



    //Bombardero attributes
    private enum PlaySubStates {BUILDING, PLAYING, CRASHING, LANDING}

    private char[][] _grid;
    private String[][] _colorGrid;
    private int _totalBuildings;
    private int _difficulty;
    private int _velocity;
    private int _aiplanePos[];
    private boolean _buildingDestroyed;
    private LinkedList<int[]> _destroyedCells;
    private int _crashFrame;

    private int _bombPos[];
    private boolean _bombDropped;
    private PlaySubStates _currentPlayState;

    //Limits in the grid [0,24]
    private final int LEFT_LIMIT = 1;
    private final int RIGHT_LIMIT = 17;
    private final int BOTTOM_LIMIT = 21;
    private final char FRONT_CHAR =(char)242;
    private final char BACK_CHAR =(char)241;
    private final char BOMB_CHAR = (char)252;
    private final char[] CRASH_CHARS ={(char)188, (char)238, (char)253};

    private long _lastTimeUpdate;
    private long _currentTime;
    private long _nanoElapsedTime;
    private double _frameTime;
    private double _deltaTime;

    private int _score;


    public PlayState(Game game, int difficulty, int velocity){
        super(game);
        _difficulty = difficulty;
        _velocity = velocity;

    }

    //-------------------------------------------- GameState override functions -------------------------------------------------
    @Override
    public void update(){

        _currentTime = System.nanoTime();
        _nanoElapsedTime = _currentTime - _lastTimeUpdate;
        _deltaTime = ((double) _nanoElapsedTime / 1E09);
        if(_deltaTime > _frameTime) {

            switch (_currentPlayState){
                case BUILDING:
                    createBuildings();
                    break;
                case PLAYING:
                    updateEntities();
                    setScoreRender();
                    break;
                case CRASHING:
                    updateCrash();
                    break;
                case LANDING:
                    land();
                    break;
            }

            _lastTimeUpdate = _currentTime;

        }

    }
    @Override
    public void processInput(){

        List<Input.TouchEvent> events = _input.getTouchEvents();
        for(Input.TouchEvent e : events){
            switch (e.get_tipo()) {
                case PULSACION:
                    dropBomb();
                    break;
                default:
                    break;
            }

        }
    }
    @Override
    public void init(){

        Screen.getScreen().init(_graphics,1);

        _grid = Screen.getScreen().getGrid();
        _colorGrid = Screen.getScreen().getColorGrid();

        //Create and add the airplane entity
        _aiplanePos = new int[2];
        _aiplanePos[0] = 2;
        _aiplanePos[1] = 2;

        _bombPos = new int[2];
        _destroyedCells = new LinkedList<int[]>();



        setScoreRender();

       _currentPlayState = PlaySubStates.BUILDING;


        //Variables

        _bombDropped = false;
        _lastTimeUpdate = System.nanoTime();
        _currentTime = _nanoElapsedTime = 0;
        _deltaTime = 0;
        _frameTime = 0.05 * _velocity;
        _buildingDestroyed = false;

    }


    //--------------------------------------------- PlayState's entities manage functions ---------------------------------------
    /*Create the buildins and setting it to the screen grid. This function also draw and show
    the creation process of the buildings one by one*/
    private void createBuildings(){
        _totalBuildings = 11;

        int minHeight = 5 - _difficulty;
        int i;
        int[] buildingPosition = {4,BOTTOM_LIMIT};
        for(i = 0; i < _totalBuildings; i++){

            int colorIndex = getRandomIntegerBetweenRange(0,Screen.getScreen().getColors().length - 1);
            String color = Screen.getScreen().getColors()[colorIndex];
            int buildingHeight = minHeight + getRandomIntegerBetweenRange(0,7);
            int totalHeight = minHeight + buildingHeight;
            if( totalHeight > 0) {
                int h;
                for (h = 0; h <= totalHeight - 1; h++) {
                    _grid[buildingPosition[0]][buildingPosition[1] - h] = (char) 143;
                    _colorGrid[buildingPosition[0]][buildingPosition[1] - h] = color;
                    Screen.getScreen().render(_graphics);
                    try {
                        Thread.sleep(20);
                    }catch (InterruptedException e){}
                }
                _grid[buildingPosition[0]][buildingPosition[1] - totalHeight] = (char)244;
                _colorGrid[buildingPosition[0]][buildingPosition[1] - totalHeight] = color;
            }
            buildingPosition[0] += 1;
        }//Create buildings loop*/

        //Add the airplane to the grid
        _grid[_aiplanePos[0]][_aiplanePos[1]] = FRONT_CHAR;
        _grid[_aiplanePos[0]-1][_aiplanePos[1]] = BACK_CHAR;
        _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "01";
        _colorGrid[_aiplanePos[0]-1][_aiplanePos[1]] = "01";

        //Change state
        _currentPlayState = PlaySubStates.PLAYING;
    }

    /*Here we call all the entities update functions*/
    private void updateEntities(){

        moveAirplane();
        if(_buildingDestroyed)
            destroyBuilding();
        if(_bombDropped) {
            moveBomb();
        }

    }

    /*Update the position of the airplane. If it chash with a building, method
    * return TRUE, otherwise it returns FALSE*/
    private boolean moveAirplane(){


        //Delete the position in the screen _grid
        _grid[_aiplanePos[0]][_aiplanePos[1]] = ' ';
        _grid[_aiplanePos[0] - 1][_aiplanePos[1]] = ' ';
        _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "00";
        _colorGrid[_aiplanePos[0] - 1][_aiplanePos[1]] = "00";


        //Check collision
        if(_grid[_aiplanePos[0] + 1][_aiplanePos[1]] != ' '){
            //Call gameOver
            crash();
            return true;
        }
        else {
        /*Update the position of the airplane in the X and Y axis.
        Also check if the player has reach the landing area*/
            _aiplanePos[0] += 1;
            if (_aiplanePos[0] > RIGHT_LIMIT) {
                _aiplanePos[0] = LEFT_LIMIT;
                _aiplanePos[1] += 1;
                if (_aiplanePos[1] > BOTTOM_LIMIT) {
                    _currentPlayState = PlaySubStates.LANDING;
                    return true;
                }
            }

            //Move airplane in the _grid
            _grid[_aiplanePos[0]][_aiplanePos[1]] = FRONT_CHAR;
            _grid[_aiplanePos[0] - 1][_aiplanePos[1]] = BACK_CHAR;
            _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "01";
            _colorGrid[_aiplanePos[0] - 1][_aiplanePos[1]] = "01";

        }
        return false;
    }

    /*Function that update the position of the bomb in the grid and check
     *if it collide with some building or it falls on the ground.*/
    private void moveBomb(){

        char[][] _grid = Screen.getScreen().getGrid();
        String[][] _colorGrid = Screen.getScreen().getColorGrid();

        //Delete the position in the screen _grid
        _grid[_bombPos[0]][_bombPos[1]] = ' ';
        _colorGrid[_bombPos[0]][_bombPos[1]] = "00";

        //Check collision and call bombExploded function
        if(_grid[_bombPos[0]][_bombPos[1] + 1] != ' '
                || (_bombPos[1] + 1) > BOTTOM_LIMIT){
            bombExploded();
        }
        else{
            _bombPos[1] += 1;
            //Set the position in the screen _grid
            _grid[_bombPos[0]][_bombPos[1]] = BOMB_CHAR;
            _colorGrid[_bombPos[0]][_bombPos[1]] = "01";
        }

    }

    /*Function wich is called when the players touch the screen.
     *If there is no bomb in the game, it drops one. If there is another bomb already falling,
     * it do nothing.*/
    private void dropBomb(){
        if(!_bombDropped){
            //Set the initial positin of the bomb right under the airplane.
            _bombPos[0] = _aiplanePos[0];
            _bombPos[1] = _aiplanePos[1] + 1;

            //Set the position in the screen _grid
            _grid[_bombPos[0]][_bombPos[1]] = BOMB_CHAR;
            _colorGrid[_bombPos[0]][_bombPos[1]] = "01";

            //Point out that a bomb has created.
            _bombDropped = true;
        }//If no bomb dropped.
    }

    /*When a bomb collide with something, this functions check if there was a building or it crashed with the ground*/
    private void bombExploded() {

        int nFloorsDestroyed = 0;

        //If the bomb has exploded in the 'Play' _grid, it means it has collided with a building.
        if (_bombPos[1] + 1 <= BOTTOM_LIMIT) {
            _buildingDestroyed = true;
            //Delete the first floor
            _grid[_bombPos[0]][_bombPos[1] + 1] = (char)238;
            _colorGrid[_bombPos[0]][_bombPos[1] + 1] = "10";
            //Choose random int to determine how many blocks are destroyed [2,4] by the bomb and delete them.
            nFloorsDestroyed = getRandomIntegerBetweenRange(2, 4);
            for (int i = 1; i <= nFloorsDestroyed; i++) {
                //If there is a floor, we destroy it.s
                if (_grid[_bombPos[0]][_bombPos[1] + i] != ' ' && _bombPos[1] + i < 22) {
                    int[] destroyedCell = {_bombPos[0],_bombPos[1] + i};
                    _destroyedCells.add(destroyedCell);
                }
            }
            _score+=nFloorsDestroyed;
        }
        //Reset the bomb variables.
        _bombDropped = false;
        _bombPos[0] = _bombPos[1] = 0;
    }

    /*When a bomb crash with a building, it has to destroy one block by frame. This function manages that
    * process, getting the blocks positions that were stored when the bomb crashed. */
    private void destroyBuilding(){

        int[] c;
        if(!_destroyedCells.isEmpty()) {
            c = _destroyedCells.poll();
            _grid[c[0]][c[1]] = ' ';
            _colorGrid[c[0]][c[1]] = "00";

            if(!_destroyedCells.isEmpty() && _destroyedCells.getFirst()[0] == c[0]) {
                _grid[c[0]][c[1] + 1] = (char) 238;
                _colorGrid[c[0]][c[1] + 1] = "10";
            }
        }
        else
            _buildingDestroyed = false;
    }

    //----------------------------------------------- PlayState life cycle functions -------------------------------------------
    /*If the airplane reach the goal cell, we reset the variables an increase the difficulty and velocity and change the current state
    * to BUILDING*/
    private void success(){

        //Change current state
        _currentPlayState = PlaySubStates.BUILDING;

        //Add the airplane entity
        _aiplanePos[0] = 2;
        _aiplanePos[1] = 2;


        _grid[_aiplanePos[0]][_aiplanePos[1]] = FRONT_CHAR;
        _grid[_aiplanePos[0]-1][_aiplanePos[1]] = BACK_CHAR;
        _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "01";
        _colorGrid[_aiplanePos[0]-1][_aiplanePos[1]] = "01";

        setScoreRender();

        //Variables
        if(_velocity > 0) _velocity--;
        if(_difficulty > 0) _difficulty--;
        _bombDropped = false;
        _lastTimeUpdate = System.nanoTime();
        _currentTime = _nanoElapsedTime = 0;
        _deltaTime = 0;
        _frameTime = 0.05 * _velocity;
        _buildingDestroyed = false;

    }

    /*If the airplane land, we sleep the game and call success method to create a new level*/
    private void land(){

        //Delete the position in the screen grid
        _grid[_aiplanePos[0]][_aiplanePos[1]] = ' ';
        _grid[_aiplanePos[0] - 1][_aiplanePos[1]] = ' ';
        _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "00";
        _colorGrid[_aiplanePos[0] - 1][_aiplanePos[1]] = "00";

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            _game.stop();
        }
        success();
    }

    /*When the airplane crash with a building, we change the state to CRASHING so the animation shows frame by frame*/
    private void crash(){

        //Delete airplane
        _grid[_aiplanePos[0]][_aiplanePos[1]] = ' ';
        _colorGrid[_aiplanePos[0]][_aiplanePos[1]] = "00";
        _grid[_aiplanePos[0]-1][_aiplanePos[1]] = ' ';
        _colorGrid[_aiplanePos[0]-1][_aiplanePos[1]] = "00";

        _crashFrame = 0;

        _currentPlayState = PlaySubStates.CRASHING;

    }

    /*This function is called one time by frame if the current state is CRASHING. It shows the correct sprite of the crashing animation*/
    private void updateCrash(){

        if(_crashFrame < 9) {
            _grid[_aiplanePos[0]+1][_aiplanePos[1]] = CRASH_CHARS[_crashFrame%3];
            _colorGrid[_aiplanePos[0]+1][_aiplanePos[1]] = "10";
            _crashFrame++;
        }
        else{
            //Change Game State
            EndState m = new EndState(_game,_score);
            _game.setState(m);
        }
    }

    //----------------------------------------------- Others functions -----------------------------------------------------------
    /*Set the score in the grid*/
    private void setScoreRender(){

        String stringScore = Integer.toString(_score);
        String maxScore = Integer.toString(_game.getMaxScore());

        Screen.getScreen().printText("____________________",23,0,"09");
        Screen.getScreen().printText("Puntos " + stringScore,24,0,"09");
        Screen.getScreen().printText("MAX " + maxScore, 24,12, "09");
    }

    /*Aux function that returns a random integer*/
    private static int getRandomIntegerBetweenRange(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

}
