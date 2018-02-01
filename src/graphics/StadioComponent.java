package graphics;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Crea un campo di calcio grafico.
 * 
 * @author Giovanbattista Felice
 * @author Giuseppa Fiorentino
 * @author Serena Panariello
 *
 */
public class StadioComponent extends JComponent {

	private BufferedImage backgroundImage;
	private Line2D linea1, linea2, linea3, linea4;
	private int imageWidthScaled, imageHeightScaled;

	public StadioComponent() {
		try {
			backgroundImage = ImageIO.read(new File("campoCalcio.jpg"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (backgroundImage != null) {
			imageWidthScaled = backgroundImage.getWidth() / 3;
			imageHeightScaled = backgroundImage.getHeight() / 3;
			g2.drawImage(
					backgroundImage.getScaledInstance(imageWidthScaled, imageHeightScaled,
							backgroundImage.SCALE_SMOOTH),
					(getWidth() - imageWidthScaled) / 2, (getHeight() - imageHeightScaled) / 2, this);

		}
	}

}
