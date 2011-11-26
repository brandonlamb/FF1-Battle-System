package battleSystem;

import java.awt.event.KeyEvent;

import engine.Input;
import engine.MP3;

/**
 * VictoryState
 * @author nhydock
 *
 *	BattleSystem state that is invoked when all the members
 *	of the enemy formation is dead.  Program is killed upon striking any
 *	key.
 */
public class VictoryState extends BattleState {

	int step = 0;		//step 0 = show "all enemies eliminated"
						//step 1 = show exp and g gained
	
	/**
	 * Kill the music and play the game over medley
	 */
	@Override
	public void start() {
		parent.bgm.close();
		new MP3("data/audio/victory.mp3").play();
		
	}

	/**
	 * Do nothing
	 */
	@Override
	public void handle() {}

	/**
	 * Do nothing
	 */
	@Override
	public void finish() {}

	/**
	 * Kill program when key is striked
	 */
	@Override
	public void handleKeyInput(KeyEvent arg0) {
		if (arg0.getID() == Input.KEY_A)
		{
			if (step >= 1)
				System.exit(-1);
			step++;
		}
	}

	public int getStep()
	{
		return step;
	}
}