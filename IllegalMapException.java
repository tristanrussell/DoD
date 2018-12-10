/**
 * Signals that an attempt to read a map from the map file has failed.
 *
 * This exception will be thrown by the {@link Map} constructor when a file
 * is read that contains a map with illegal elements. It will also be thrown
 * when the win conditions for the map cannot be reached.
 */
class IllegalMapException extends Exception {

    /**
     * Constructs an IllegalMapException with
     * null as its error detail message.
     */
    IllegalMapException() {
        super();
    }

}
