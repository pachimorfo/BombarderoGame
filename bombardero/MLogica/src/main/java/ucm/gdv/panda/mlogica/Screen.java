/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica;

import ucm.gdv.panda.minterfaz.Graphics;

public class Screen {

    private static Screen _screenInstance;
    private char _grid[][];
    private String _colorGrid[][];
    private int _gridRows;
    private int _gridColumns;
    private int _cellWidth;
    private int _cellHeight;
    private int[] _topLeftCorner;

    private String[] _colors = {"02","03","04","05","06","07","08","09",
            "10","11","12","13","14","15"};

    private Screen(){ }

    //Singleton getter
    public static Screen getScreen(){
        if(_screenInstance == null)
            _screenInstance = new Screen();
        return _screenInstance;
    }

    /*Init the screen. It can take two modes: Text(0) and Play(0). The dimensions of each mode
    * are diferent. Once getting the size of the grid, init and resize it */
    public void init(Graphics graphics, int mode){
        int width = graphics.getWidth();
        int height = graphics.getHeigth();

        if(mode == 0)//Text mode
        {
            _gridColumns = 60;
            _gridRows = 35;
        }
        else if (mode == 1)//Play mode
        {
            _gridColumns = 20;
            _gridRows = 25;
        }

        initGrids();
        resize(width,height);
    }

    //Clear the graphics buffer setting it black
    public void clear(Graphics graphics) {

        graphics.clear(0);
    }

    //Set all the cells empty with black color
    public void clearGrids(){
        int i;
        int j;
        for(i = 0; i < _gridColumns; i++){
            for(j = 0; j < _gridRows; j++){
                _grid[i][j] = ' ';
                _colorGrid[i][j] = "00";
            }
        }
    };

    //Initialize the grids to blank
    public void initGrids(){
        _grid = new char[_gridColumns][_gridRows];
        _colorGrid = new String[_gridColumns][_gridRows];
       clearGrids();
    }

    /*Draw the grid cell by cell, getting a sprite for each cell, wich contains the character and the color
    of such sprite.
    */
    public void draw(Graphics graphics){
            int i;
            int j;
            for (i = 0; i < _gridColumns; i++) {
                for (j = 0; j < _gridRows; j++) {
                    if (_grid[i][j] != ' ') {
                        Sprite s = ResourceManager.getResourceManager().getSprite(_grid[i][j], _colorGrid[i][j]);
                        int screenX = _topLeftCorner[0] + (i * _cellWidth);
                        int screenY = _topLeftCorner[1] + (j * _cellHeight);
                        graphics.drawImage(s.getImage(), screenX, screenY, _cellWidth, _cellHeight,
                                s.getImageRect()[0], s.getImageRect()[1]);
                    }
                }
            }
    }

    public char[][] getGrid(){
        return _grid;
    }

    /*The render function. First, check if there's an available buffer to draw in. Then clear the buffer
    * and draw the grid. Ultimately it flip the buffer to draw in the screen.
     */
    public void render(Graphics graphics){

        boolean renderSuccess = false;
        while(!renderSuccess) {
            if(!graphics.prepareBuffer())
                break;
            clear(graphics);
            draw(graphics);
            renderSuccess = graphics.present();
        }
    }

    public String[][] getColorGrid(){return _colorGrid;}

    public String[] getColors(){
        return _colors;
    }

    /*This function take the dimensions of the screen and creates the enviroment to
    * draw the game. It creates the grid cells dimensions and put the top-left corner
    * of the grid to keep the grid center on the screen.
    */
    public void resize(int width, int height){

        int widthPixels;
        int heightPixels;

        //Margins
        int horizontalMarginPixels = 200;
        int verticalMarginPixels = 100;

        //Cell dimensions
        _cellWidth = (width - horizontalMarginPixels) / _gridColumns;
        widthPixels = (width - horizontalMarginPixels) % _gridColumns;
        _cellHeight = (height - verticalMarginPixels) / _gridRows;
        heightPixels =(height - verticalMarginPixels) % _gridRows;

        //The top left corner
        _topLeftCorner = new int [2];
        _topLeftCorner[0] = (widthPixels / 2) + (horizontalMarginPixels / 2);
        _topLeftCorner[1] = (heightPixels / 2) + (verticalMarginPixels / 2);
    }

    /*This function obtains the cell touched by the player in the screen*/
    public int[] onTouch(int x, int y){
        int[] cell = new int[2];

        cell[0] = ((x - _topLeftCorner[0]) / _cellWidth);
        cell[1] = ((y - _topLeftCorner[1]) / _cellHeight);

        return cell;
    }

    public boolean printText(String text, int startRow, int startColumn, String c){

        //If the index of the startColum or the starRow are invalid, return false;
        if(startColumn >= _gridColumns || startRow > _gridRows)
            return false;

        //First check the text longitude. If its greater than our grid, return false.
        int textLength = text.length();
        if(textLength > ((_gridRows  - startRow)
                * (_gridColumns)) -startColumn){
            return false;
        }

        //Else we can print the text character by character
        int textIndex;
        int gX = startColumn;
        int gY = startRow;
        String color = c;
        for(textIndex = 0; textIndex < textLength; textIndex++){
            _colorGrid[gX][gY] = color;
            //The character '@' means line break
            if(text.charAt(textIndex) == '@'){
                gY+=2;
                gX = 0;
            }//If Line break
            else {
                //If the next cell is out of the horizontal limits of the grids, format the text to keep it readable.
                //What we do here is check that a word keep readable if it is needed to be divided in two lines.
                if (gX + 1 >= _gridColumns) {
                    if (textIndex  + 1 < textLength && text.charAt(textIndex + 1) != ' '  ) {
                        if(text.charAt(textIndex) != ' ') {
                            if(text.charAt(textIndex - 1) != ' ') {
                                _grid[gX][gY] = '-';
                                textIndex--;
                            }
                            else {
                                _grid[gX][gY] = ' ';
                                textIndex--;
                            }
                        }
                    } else {
                        _grid[gX][gY] = text.charAt(textIndex);
                        textIndex++;
                    }
                    gY++;
                    gX = 0;
                } else {
                    _grid[gX][gY] = text.charAt(textIndex);
                    gX++;
                }
            }//Else line break
        }

        return true;
    }
}
