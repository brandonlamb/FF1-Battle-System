package editor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import engine.TileSet;

/**
 * MapEditorGUI
 * @author nhydock
 *
 *	Simple GUI for map editing with a tile based system
 */
public class MapEditorGUI extends JFrame implements ActionListener{

	/*
	 * Main menu
	 */
	JMenuBar toolbar;
	JMenu mainMenu;
	JMenuItem createNew;
	JMenuItem loadMap;
	JMenuItem saveMap;
	JMenuItem quit;
	
	JPanel mainPane;
	
	/*
	 * Fields
	 */
	JTextField nameField;
	JComboBox tileSetList;
	JCheckBox passability;
	
	/*
	 * Map Editor
	 */
	MapGrid editGrid;
	TileSetGrid tileGrid;
	JScrollPane editPane;
	JScrollPane tilePane;
	
	TileSet activeTileSet;		//the current active tile set
	int tileSetIndex;			//selected tile from the tile set
	
	int mapWidth  = 1;
	int mapHeight = 1;
	
	JLabel dimensionsLabel;
	static final String[] tileSets = buildTileMapList();
	
	Font font = new Font("Arial", 1, 32);
	
	/*
	 * Dialogs
	 */
	NewMapDialog newMapDialog;
	
	public MapEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize all the toolbar components 
		 */
		toolbar = new JMenuBar();
		mainMenu = new JMenu("File");
		createNew = new JMenuItem("Create New Map");
		createNew.addActionListener(this);
		
		loadMap = new JMenuItem("Load Map");
		loadMap.addActionListener(this);
		
		saveMap = new JMenuItem("Save Map");
		saveMap.addActionListener(this);
		
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		
		mainMenu.add(createNew);
		mainMenu.add(loadMap);
		mainMenu.add(saveMap);
		mainMenu.add(quit);
		
		toolbar.add(mainMenu);
		
		setJMenuBar(toolbar);
		
		mainPane = new JPanel();
		
		/*
		 * Initialize fields 
		 */
		JLabel nameLabel = new JLabel("Map name: ");
		nameLabel.setSize(200,16);
		nameLabel.setLocation(10,10);
		nameField = new JTextField("map");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		JLabel dL = new JLabel("Dimensions: ");
		dL.setSize(dL.getPreferredSize());
		dL.setLocation(10, 64);
		
		dimensionsLabel = new JLabel(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210-dimensionsLabel.getWidth(), 64);
		
		tileSetList = new JComboBox(tileSets);
		tileSetList.setSize(200, 24);
		tileSetList.setLocation(10, 92);
		tileSetList.addActionListener(this);
		
		//load tileset
		activeTileSet = new TileSet((String)tileSetList.getItemAt(0));
		
		add(nameLabel);
		add(nameField);
		add(dL);
		add(dimensionsLabel);
		add(tileSetList);
		
		/*
		 * Initialize editor
		 */
		editGrid = new MapGrid(this);
		editPane = new JScrollPane(editGrid);
		editPane.setLocation(220, 10);
		editPane.setSize(420, 365);
		editPane.getViewport().setBackground(Color.GRAY);
		
		tileGrid = new TileSetGrid(this);
		tilePane = new JScrollPane(tileGrid);
		tilePane.setLocation(10, 125);
		tilePane.setSize(200, 250);
		tilePane.getViewport().setBackground(Color.GRAY);
		
		passability = new JCheckBox("Edit Passability");
		passability.setSize(passability.getPreferredSize());
		passability.setLocation(10, 380);
		
		passability.addActionListener(this);
		
		add(editPane);
		add(tilePane);
		add(passability);
		
		/*
		 * Initialize GUI window 
		 */
		newMap(10,10);
		setSize(680, 480);
		setVisible(true);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	}

	/**
	 * Creates a list of available tilemaps
	 * @return
	 */
	private static String[] buildTileMapList()
	{
		String[] s = new File("data/tilemaps").list(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".png");
              }
            });
		return s;
	}
	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == createNew)
			newMapDialog = new NewMapDialog(this);
		else if (event.getSource() == tileSetList)
		{
			JComboBox cb = (JComboBox)event.getSource();
	        String name = (String)cb.getSelectedItem();
			
	        activeTileSet = new TileSet(name);
	        tileGrid.refreshTileSet();
	        tilePane.setViewportView(tileGrid);
		}
		if (event.getSource() == passability)
			tileGrid.swithPassabilityMode();
	}
	
	/**
	 * Initializes a new map to edit
	 * @param w
	 * @param h
	 */
	public void newMap(int w, int h)
	{
		nameField.setText("map");
		mapWidth = w;
		mapHeight = h;
		dimensionsLabel.setText(mapWidth + " x " + mapHeight);
		dimensionsLabel.setSize(dimensionsLabel.getPreferredSize());
		dimensionsLabel.setLocation(210 - dimensionsLabel.getWidth(), 64);	
		editGrid.newMap(mapWidth, mapHeight);
	}
	
	/**
	 * Runner method
	 */
	public static void main(String[] args)
	{
		MapEditorGUI g = new MapEditorGUI();
	}
}
