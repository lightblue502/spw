package f2.spw;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Sprite {
	int x;
	int y;
	int width;
	int height;
	Image image;
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	abstract public void draw(Graphics2D g);
	public void setImage(String imagePath){
		try {
			File sourceimage = new File(imagePath);
			image = ImageIO.read(sourceimage);
		}catch (IOException e) {
        	e.printStackTrace();
        }
	}
	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}
	public Ellipse2D.Double getEllipse(){
		return new Ellipse2D.Double(x, y, width, height);
	}
}
