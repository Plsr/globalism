package de.christianpoplawski.globalism;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;



public class Board extends JPanel implements Runnable, ActionListener {
    private final int DELAY = 25;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private Thread animator;
    private SpaceShip spaceShip;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setFocusable(true);

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {
        spaceShip.move();
        updateMissiles();

        repaint(
            spaceShip.getX() - 1,
            spaceShip.getY() -1,
            spaceShip.width + 2,
            spaceShip.height + 2
        );
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
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
    	
    	List<Missile> missiles = spaceShip.getMissiles();
    	for (Missile missile : missiles) {
    		g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
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
