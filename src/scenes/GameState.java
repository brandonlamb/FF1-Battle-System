package scenes;

import java.awt.event.KeyEvent;

/**
 * GameState
 * @author nhydock
 *
 *  State class for systems to handle themselves
 */
public abstract class GameState {

    protected GameSystem parent;
    protected int index;
    
    /**
     * Creates a game state
     * @param c
     */
    public GameState(GameSystem c)
    {
        parent = c;
    }
    
    /**
     * Handles anything that is required to be set upon switching to the state
     */
    abstract public void start();
    
    /**
     * Handles updating
     */
    abstract public void handle();
    
    /**
     * Finishes the state's execution
     */
    abstract public void finish();

    /**
     * Handles the key input for the state
     * @param arg0
     */
    abstract public void handleKeyInput(KeyEvent arg0);
    
    /**
     * Parent system/scene that the state is interacting with
     * @param p
     */
    public void setParent(GameSystem p)
    {
        parent = p;
    }
    
    /**
     * Returns the index of the state, whatever that may be
     * Might be the current step of the state or it might be the index in a menu
     * that the state is handling.
     * @return
     */
    public int getIndex()
    {
    	return index;
    }
}
