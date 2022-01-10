import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

// for some reason I cannot directly access Rectangle2D.Double with import
import java.awt.geom.*;

public class SlideExtractor {
	public static void usage() {
		System.out.println("Usage: slideextractor <filename>");
	}

	public static void main (String[] args) throws FileNotFoundException, IOException {
 		if (args.length == 0) {
			usage();
			return;
		}
		
		System.out.println(args[0]);
		System.out.println("User Working Dir: " + System.getProperty("user.dir"));
		String classpath = System.getProperty("java.class.path");
		String[] classpathEntries = classpath.split(File.pathSeparator);

		// Using the process outlined on Apache POI tutorial pages, but for pptx files
		FileInputStream is = new FileInputStream(args[0]);
		XMLSlideShow pptx = new XMLSlideShow(is);

		Dimension pgsize = pptx.getPageSize();

		int idx = 1;
		for (XSLFSlide slide : pptx.getSlides()) {
			BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = img.createGraphics();
			
			// clear the drawing area
		    graphics.setPaint(Color.white);
		    graphics.fill(new Rectangle2D.Double(0, 0, pgsize.width, pgsize.height));

			//render
			slide.draw(graphics);
			
			//save the output
			FileOutputStream out = new FileOutputStream("slide-" + idx + ".png");
			javax.imageio.ImageIO.write(img, "png", out);
			out.close();

			idx++;
		}

		is.close();
		return;
    }
}
