package de.christianpoplawski.globalism;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements Runnable, ActionListener {
	private final int DELAY = 25;
	private final int ICRAFT_X = 0;
	private final int ICRAFT_Y = 0;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	private Thread animator;
	private Timer timer;
	private SpaceShip spaceShip;
	private List<Alien> aliens;
	private boolean ingame;

	private static final Color PLAINS = new Color(102, 153, 0);
	private static final Color FOREST = new Color(0, 102, 0);
	private static final Color SHORELINE = new Color(255, 204, 102);
	private static final Color OCEAN = new Color(0, 153, 153);

	public static final Color[] TERRAIN = { SHORELINE, FOREST, OCEAN, PLAINS };

	public static final int PREFERRED_GRID_SIZE_PIXELS = 30;

	private Color[][] terrainGrid;

	public Board(int numCols, int numRows) {
		// TODO: Remove, not sure what this is used for
		this.numRows = numRows;
		this.numCols = numCols;
		this.width = numCols * PREFERRED_GRID_SIZE_PIXELS;
		this.height = numRows * PREFERRED_GRID_SIZE_PIXELS;
		System.out.println(width);
		System.out.println(height);
		setPreferredSize(new Dimension(width, height));
		generateMap();
		initBoard();
	}

	private void generateMap() {
		this.terrainGrid = new Color[numRows][numCols];
		Random r = new Random();

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int randomTerrainIndex = r.nextInt(TERRAIN.length);
				Color randomColor = TERRAIN[randomTerrainIndex];
				this.terrainGrid[i][j] = randomColor;
			}
		}
	}

	private void initBoard() {
		addKeyListener(new TAdapter());
		setBackground(Color.WHITE);
		setFocusable(true);

		ingame = true;

		spaceShip = new SpaceShip((width / 2) + 15, (height / 2) + 15);

		Timer timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();

		updateShip();
		updateMissiles();

		checkCollisions();

		repaint();
	}

	private void checkCollisions() {
	}

	private void updateShip() {
		if (spaceShip.isVisible()) {
			spaceShip.move();
		}
	}

	private void inGame() {
		if (!ingame) {
			timer.stop();
		}
	}

	private void updateMissiles() {
		List<Missile> missiles = spaceShip.getMissiles();
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			if (missile.isVisible()) {
				missile.move();
			} else {
				missiles.remove(i);
			}
		}

	}

	@Override
	public void addNotify() {
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		int rectWidth = getWidth() / numCols;
	    int rectHeight = getHeight() / numRows;
	    
	    for(int i = 0; i < numRows; i++) {
	    	for(int j = 0; j < numCols; j++) {
	    		int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
	    	}
	    }

		if (ingame) {
			drawObjects(g);
		} else {
			drawGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg, (width - fm.stringWidth(msg)) / 2, height / 2);
	}

	private void drawObjects(Graphics g) {
		g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);

		List<Missile> missiles = spaceShip.getMissiles();

		for (Missile missile : missiles) {
			g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep > 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				String msg = String.format("Thread interuppted: %s", e.getMessage());

				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}

			beforeTime = System.currentTimeMillis();

		}
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			spaceShip.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			spaceShip.keyPressed(e);
		}
	}
}
