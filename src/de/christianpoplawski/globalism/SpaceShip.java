package de.christianpoplawski.globalism;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class SpaceShip extends Sprite {
    private int dx;
    private int dy;
    private int w;
    private int h;
    private Image image;

    public SpaceShip(int x, int y) {
        super(x, y);
        
        initSpaceship();
        
    }

    private void initSpaceship() {
    	loadImage("src/resources/ship.png");
    	getImageDimensions();
		
	}

    public void move() {
        x += dx;
        y += dy;
    }
    
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
