package de.christianpoplawski.globalism;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;



public class Board extends JPanel implements Runnable, ActionListener {
    private final int DELAY = 25;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 660;
    private final int B_HEIGHT= 660;
    private Thread animator;
    private Timer timer;
    private SpaceShip spaceShip;
    private List<Alien> aliens;
    private boolean ingame;
    
    private final int[][] alienPos = {
            {1, 29}, {70, 90}, {240, 89},
            {300, 109}, {580, 139}, {680, 239},
            {300, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
        };

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setFocusable(true);
        ingame = true;

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        initAliens();
        
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initAliens() {
		aliens = new ArrayList<>();
		
		for(int[] p : alienPos) {
			aliens.add(new Alien(p[0], p[1]));
		}
		
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		inGame();
		
		updateShip();
		updateMissiles();
		updateAliens();
		
		checkCollisions();
		
        repaint();
    }

    private void checkCollisions() {
		Rectangle r3 = spaceShip.getBounds();
		
		for (Alien alien : aliens) {
			Rectangle r2 = alien.getBounds();
			
			if(r3.intersects(r2)) {
				spaceShip.setVisible(false);
				alien.setVisible(false);
				ingame = false;
			}
		}
		
		List<Missile> missiles = spaceShip.getMissiles();
		
		for (Missile missile : missiles) {
			Rectangle r1 = missile.getBounds();
			
			for (Alien alien : aliens) {
				Rectangle r2 = alien.getBounds();
				
				if (r1.intersects(r2)) {
					missile.setVisible(false);
					alien.setVisible(false);
				}
			}
		}
	}

	private void updateAliens() {
    	if (aliens.isEmpty()) {
    		ingame = false;
    		return;
    	}
    	
    	for(int i = 0; i < aliens.size(); i++) {
			Alien a = aliens.get(i);
			
			if(a.isVisible()) {
				a.move();
			} else {
				aliens.remove(i);
			}
		}
	}

	private void updateShip() {
    	if(spaceShip.isVisible()) {
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
        
        if(ingame) {
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

    	g.setColor(Color.BLACK);
    	g.setFont(small);
    	g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	private void drawObjects(Graphics g) {
    	g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
    	
    	List<Missile> missiles = spaceShip.getMissiles();

    	for (Missile missile : missiles) {
    		g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
    	}
    	
    	for (Alien alien : aliens) {
    		g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
    	}
    	
    	g.setColor(Color.BLACK);
    	g.drawString("Aliens left: " + aliens.size(), 5, 15);
    			
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
                String msg = String.format(
                    "Thread interuppted: %s",
                    e.getMessage()
                );

                JOptionPane.showMessageDialog(this, msg, "Error",
                    JOptionPane.ERROR_MESSAGE
                );
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
