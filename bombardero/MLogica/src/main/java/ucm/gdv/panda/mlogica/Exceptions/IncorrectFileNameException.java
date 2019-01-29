/*
 * Carlos Sanchez Bouza
 * Ángel Romero Pareja
 *
 * Videojuegos en dispositivos moviles
 *
 */

package ucm.gdv.panda.mlogica.Exceptions;

public class IncorrectFileNameException extends RuntimeException {

    public IncorrectFileNameException(String errorMsg) {
        super("Incorrect File Name" + errorMsg);
    }
}
