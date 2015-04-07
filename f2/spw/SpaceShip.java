package f2.spw;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpaceShip extends Sprite {
	private Image image = null;
	int step = 8;
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try {
			File sourceimage = new File("f2/spw/pics/spaceShip.png");
			image = ImageIO.read(sourceimage);
		}catch (IOException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public void draw(Graphics2D g) {
//		g.setColor(Color.GREEN);
		g.drawImage(image, x, y, width, height, null);
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}

}
