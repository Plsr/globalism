package de.christianpoplawski.globalism;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int stepSize = 3;
	protected boolean visible;
	protected Image image;
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	protected void loadImage(String imageName) {
		loadImage(imageName, 30);
	}
	
	protected void loadImage(String imageName, int size) {
		loadImage(imageName, size, size);
	}
	
	protected void loadImage(String imageName, int width, int height) {
		ImageIcon ii = new ImageIcon(imageName);
		ii.setImage(ii.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        image = ii.getImage();
	}
	
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
