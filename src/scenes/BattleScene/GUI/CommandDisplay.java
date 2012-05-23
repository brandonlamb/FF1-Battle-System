package scenes.BattleScene.GUI;

import java.awt.Graphics;

import engine.GameScreen;

import scenes.HUD;
import scenes.BattleScene.System.*;

import actors.Actor;

import graphics.SFont;
import graphics.Sprite;
import graphics.SWindow;

/**
 * CommandDisplay
 * @author nhydock
 *
 *	Shows list of commands that the player can select and execute
 */
public class CommandDisplay extends HUD{
	
	SFont font = GameScreen.font;
	SWindow window;
	
	BattleSystem parent;
	
	public CommandDisplay(int x, int y)
	{
		window = new SWindow(x, y, 108, 80);
	}
	
	public void setParentScene(BattleSystem bs)
	{
		parent = bs;
	}
	
	/**
	 * Main render method
	 */
	public void paint(Graphics g)
	{
		//window is first sprite
		window.paint(g);
		
		Actor a = parent.getActiveActor();
		for (int i = 0; i < a.getCommands().length; i++)
			font.drawString(g, a.getCommands()[i], 10 + 60*(i/4), 
							16 + 16 * (i % 4), window);
	}

	@Override
	public void update() {
	}
	
	public int[] getArrowPosition(int index)
	{
		return new int[]{window.getX() + 1 + 60*(index/4), window.getY() + 24 + 16 * (index % 4) - 12};
	}
}
