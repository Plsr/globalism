package de.christianpoplawski.globalism;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class SpaceShip {
    private int dx;
    private int dy;
    private int x = 140;
    private int y = 160;
    private int w;
    private int h;
    private Image image;

    public SpaceShip() {
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("src/resources/ship.png");
        ii.setImage(ii.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        image = ii.getImage();
        
		w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    } 

    public int getWidth() {
        
        return w;
    }
    
    public int getHeight() {
        
        return h;
    }    

    public Image getImage() {
        
        return image;
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
