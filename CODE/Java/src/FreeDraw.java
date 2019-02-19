import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

@SuppressWarnings("serial")
public class FreeDraw extends JPanel {

	private boolean drawing = false;
	private static int npixels_dim = 50;
	private static int init_size = 500;
	private int f_height = init_size;
	private int f_width = init_size;
	private int pixels[][] = new int[npixels_dim][npixels_dim];
	private MouseHandler mouseHandler = new MouseHandler();
	private boolean drawn = false;

	public FreeDraw() {

		this.setPreferredSize(new Dimension(init_size, init_size));
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.setVisible(true);
	}

	public boolean hasDrawn() {
		return drawn;
	}

	public int[][] getImage() {
		return pixels;
	}
	

	public void paint(Graphics g) {
		// This is called everytime window is resized

		super.paint(g); // ensure the screen is cleared out

		this.f_height = this.getHeight();
		this.f_width = this.getWidth();

		int x_increment = (int) Math.ceil(f_width / FreeDraw.npixels_dim);
		int y_increment = (int) Math.ceil(f_height / FreeDraw.npixels_dim);

		for (int x = 0; x < f_width; x += x_increment) {
			for (int y = 0; y < f_height; y += y_increment) {
				int x_ind = x / x_increment;
				int y_ind = y / y_increment;

				try {
					if (pixels[y_ind][x_ind] == 0) {
						// g.drawRect(x, y, x_increment, y_increment);
					} else {
						g.fillRect(x, y, x_increment, y_increment);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void pixelClicked(int x, int y) {

		int x_increment = (int) Math.ceil(f_width / FreeDraw.npixels_dim);
		int y_increment = (int) Math.ceil(f_height / FreeDraw.npixels_dim);

		int x_index = x / x_increment;
		int y_index = y / y_increment;

		try {
			if (pixels[y_index][x_index] == 0) {
				pixels[y_index][x_index] = 1;
				paintImmediately(this.getGraphics(), x_index, y_index, x_increment, y_increment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void paintImmediately(Graphics g, int x_ind, int y_ind, int x_increment, int y_increment) {
		g.fillRect(x_ind * x_increment, y_ind * y_increment, x_increment, y_increment);
	}

	public void clear() {
		this.pixels = new int[npixels_dim][npixels_dim];
		drawn = false;
		repaint();
	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			drawn = true;

			int x = e.getX();
			int y = e.getY();

			drawing = true;
			pixelClicked(x, y);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			drawing = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (drawing) {
				int x = e.getX();
				int y = e.getY();
				pixelClicked(x, y);
			}
		}

	}

}