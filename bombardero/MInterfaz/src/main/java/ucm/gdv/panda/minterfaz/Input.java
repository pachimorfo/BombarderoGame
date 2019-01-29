/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.minterfaz;

import java.util.List;

public interface Input {


    public class TouchEvent{


        public enum Tipo {PULSACION, LIBERACION, DESPLAZAMIENTO;}

        public TouchEvent(int x, int y, int id, Tipo tipo){

            _position = new int[2];
            _position[0] = x; _position[1] = y;

            _tipo = tipo;

            _id = id;


        }
        //TODO: TouchEvent(...), constructora con parámetros.


        public int[] getPosition() {
            return _position;
        }

        public void setPosition(int[] position) {
            this._position = position;
        }

        public int getId() {
            return _id;
        }

        public void setId(int id) {
            this._id = id;
        }

        private int[] _position; //Posición del dedo/ratón en la pantalla

        private int _id; //Identificador del boton/dedo

        public Tipo get_tipo() {
            return _tipo;
        }

        private Tipo _tipo;

    }


    /*Devuelve la lista de eventos recibidos
    desde la última invocación.*/
    List<TouchEvent> getTouchEvents();
}
