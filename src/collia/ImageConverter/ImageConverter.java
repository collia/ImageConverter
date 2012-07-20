/**
 * 
 */

package collia.ImageConverter;
import static java.awt.Color.*;

import java.awt.*;

import com.sun.image.codec.jpeg.*;

import java.awt.image.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import static java.awt.RenderingHints.*;

/**
 * @author collia
 *
 */
public class ImageConverter extends JPanel implements Printable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5848354631387130613L;
	private static BufferedImage[] images;
	private static Date dateOfImages[]; 
	private boolean isPrintDate[];
	static Font theFont = new Font("serif", Font.ROMAN_BASELINE, 14);
	
	private static int BORDER = 5;//15;
	private static int DIMENTION = 45;
	private static int OFFSET_TOP = 20;
	private static int OFFSET_BOTTOM = 0;
	private static int OFFSET_LEFT = 20;
	private static int OFFSET_RIGHT = 0;

	
	public ImageConverter (String file1, String file2, String file3) {
		images = new BufferedImage[3];
		dateOfImages = new Date[3];
		isPrintDate = new boolean[3];
		setBackground(WHITE);
 
	//	URLClassLoader urlLoader =
    //        (URLClassLoader)this.getClass().getClassLoader();
	//	URL fileLoc = urlLoader.findResource(file1);
		
      		
		try {
			//File file_1 = new File(fileLoc.getPath());
			File file_1 = new File(file1);
			
			images[0] = javax.imageio.ImageIO.read(file_1);
			//fileLoc = urlLoader.findResource(file2);
			//File file_2 = new File(fileLoc.getPath());
			File file_2 = new File(file2);
			images[1] = javax.imageio.ImageIO.read(file_2);
			//fileLoc = urlLoader.findResource(file3);
			//File file_3 = new File(fileLoc.getPath());
			File file_3 = new File(file3);
			images[2] = javax.imageio.ImageIO.read(file_3);
			
			dateOfImages[0] = new Date(file_1.lastModified());
			dateOfImages[1] = new Date(file_2.lastModified());
			dateOfImages[2] = new Date(file_3.lastModified());
			
			System.out.println(dateOfImages[0]);
			System.out.println(dateOfImages[1]);
			System.out.println(dateOfImages[2]);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	

	public void setIsPrintDateImage(boolean isPrintDate, int image) {
		if(image < this.isPrintDate.length)
			this.isPrintDate[image] = isPrintDate;
	//	SwingUtilities.invokeLater(new Runnable() {
	//	    public void run() {
	//	    	SwingUtilities.updateComponentTreeUI(this);
	//	    }
	//	});

		//this.repaint();
	}


	private BufferedImage renderImage(int ww, int hh, Graphics2D g2, Image image, Date creationDate, boolean isVertical)
	{
		if(ww < 0 || hh < 0)
			return null;
		 BufferedImage bi = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_RGB);
	        Graphics2D big = bi.createGraphics();

	        // .. use rendering hints from J2DCanvas ..
	        big.setRenderingHints(g2.getRenderingHints());

	        big.setBackground(Color.WHITE);
	        big.clearRect(0, 0, ww, hh);

	        int iw = image.getWidth(this);
	        int ih = image.getHeight(this);
	        
	        if(!isVertical)
	        {
	        	if(iw > ih)
	        	{
	        		double scale = (double)hh / (iw*2/3) ;
	        		double offset = (ih - (iw*2/3))  / 2;
		        	
		        	AffineTransform at = (AffineTransform.getScaleInstance(scale, scale));
		        	at.concatenate(AffineTransform.getTranslateInstance(0, -offset));
		        	
		        	big.drawImage(image, at, this);
		        	if (creationDate != null)
		        	{
		        		//System.out.println("Test hor not rot");
		        		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		        		big.setColor(RED);
		        		//big.drawString(df.format(creationDate), ww - 65, hh - 5);
		        		//new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext()).draw(big, ww - 72, hh - 5);
		        		TextLayout tl = new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext());
		        		//AffineTransform atText = AffineTransform.getRotateInstance(Math.PI/2);
		        		//atText.concatenate(AffineTransform.getTranslateInstance(-10, -50));
		        		AffineTransform atText = AffineTransform.getTranslateInstance(ww-80, hh-5);
		        		big.draw(tl.getOutline(atText));
		        	}
		             
		        }
		        else 
		        {
		        	double scale = (double)hh / (ih*2/3) ;
		        	double offset = (iw - (ih*2/3))  / 2;
		        	AffineTransform at = (AffineTransform.getScaleInstance(scale, scale));
		        	at.concatenate(AffineTransform.getRotateInstance(Math.PI/2));
		        	at.concatenate(AffineTransform.getTranslateInstance(0, (hh - offset)));
		        	big.drawImage(image, at, this);
		        	
		        	if (creationDate != null)
		        	{
		        		//System.out.println("Test hor rot");
		        		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		        		big.setColor(RED);
		        		//big.drawString(df.format(creationDate), ww - 65, hh - 5);
		        		//new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext()).draw(big, ww - 72, hh - 5);
		        		TextLayout tl = new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext());
		        		AffineTransform atText = AffineTransform.getRotateInstance(Math.PI/2);
		        		atText.concatenate(AffineTransform.getTranslateInstance(ww-10, hh-50));
		        		//AffineTransform atText = AffineTransform.getTranslateInstance(ww-80, hh-5);
		        		big.draw(tl.getOutline(atText));
		        	}
		        }
	        }
	        else
	        {
	        	if(iw < ih)
	        	{
	        		double scale = (double)ww / (ih*2/3) ;
	        		double offset = (iw - (ih*2/3))  / 2;
		        	
		        	AffineTransform at = (AffineTransform.getScaleInstance(scale, scale));
		        	at.concatenate(AffineTransform.getTranslateInstance( -offset, 0));
		        	
		        	big.drawImage(image, at, this);
		        	System.out.println("Test1");
		        	if (creationDate != null)
		        	{
		        		//System.out.println("Test Vert not rot");
		        		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		        		big.setColor(RED);
		        		//big.drawString(df.format(creationDate), ww - 65, hh - 5);
		        		//new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext()).draw(big, ww - 72, hh - 5);
		        		TextLayout tl = new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext());
		        		//AffineTransform atText = AffineTransform.getRotateInstance(Math.PI/2);
		        		//atText.concatenate(AffineTransform.getTranslateInstance(ww-10, hh-50));
		        		AffineTransform atText = AffineTransform.getTranslateInstance(ww-80, hh-5);
		        		big.draw(tl.getOutline(atText));
		        	}
		        	
		             
		        }
		        else 
		        {
		        	double scale = (double)ww / (iw*2/3) ;
		        	double offset = (ih - (iw*2/3))  / 2;
		        	AffineTransform at = (AffineTransform.getScaleInstance(scale, scale));
		        	at.concatenate(AffineTransform.getRotateInstance(Math.PI/2));
		        	at.concatenate(AffineTransform.getTranslateInstance( 0, -(ih - offset)));
		        	big.drawImage(image, at, this);
		        	
		        	//System.out.println("Test2");
		        	if (creationDate != null)
		        	{
		        		//System.out.println("Test Vert rot");
		        		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		        		big.setColor(RED);
		        		//big.drawString(df.format(creationDate), ww - 65, hh - 5);
		        		//new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext()).draw(big, ww - 72, hh - 5);
		        		TextLayout tl = new TextLayout(df.format(creationDate), theFont, big.getFontRenderContext());
		        		AffineTransform atText = AffineTransform.getRotateInstance(Math.PI/2);
		        		atText.concatenate(AffineTransform.getTranslateInstance(hh-80, -5));
		        		//AffineTransform atText = AffineTransform.getTranslateInstance(ww-80, hh-5);
		        		big.draw(tl.getOutline(atText));
		        	}
		        }
	        }
	       
	        return bi;
	        
	}
	private void drawRect(int x, int y, int ww, int hh, Graphics2D g2) 
	{
		BasicStroke dotted = new BasicStroke(2, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 0, new float[]{0,6,0,6}, 0);
		g2.setStroke(dotted); 
		g2.drawLine( x,                 y,                 x + ww + 2*BORDER, y);
        g2.drawLine( x + ww + 2*BORDER, y,                 x + ww + 2*BORDER, y + hh + 2*BORDER );
        g2.drawLine( x + ww + 2*BORDER, y + hh + 2*BORDER, x,                 y + hh + 2*BORDER );
        g2.drawLine( x,                 y + hh + 2*BORDER, x,                 y);
	}
	private void render(int w, int h, Graphics2D g2) {


        int hh = (h - 4*BORDER - DIMENTION - OFFSET_TOP - OFFSET_BOTTOM)/2;
        int ww = (int)(((double)(w - 4*BORDER - DIMENTION - OFFSET_LEFT - OFFSET_RIGHT)/5)*3);
        if (hh < (ww/3*2))
        	ww = hh*3/2;
        else 
        	hh = ww*2/3;
        if(isPrintDate[0])
        	g2.drawImage(renderImage(ww, hh, g2,images[0], dateOfImages[0], false), OFFSET_LEFT + BORDER, OFFSET_TOP + BORDER, this);
        else
        	g2.drawImage(renderImage(ww, hh, g2,images[0], null, false), OFFSET_LEFT + BORDER, OFFSET_TOP + BORDER, this);
        if(isPrintDate[1])
        	g2.drawImage(renderImage(ww, hh, g2,images[1], dateOfImages[1], false), OFFSET_LEFT + BORDER, OFFSET_TOP + hh + 3*BORDER + DIMENTION,  this);
        else
        	g2.drawImage(renderImage(ww, hh, g2,images[1], null, false), OFFSET_LEFT + BORDER, OFFSET_TOP + hh + 3*BORDER + DIMENTION,  this);
        if(isPrintDate[2])
        	g2.drawImage(renderImage(hh, ww, g2,images[2], dateOfImages[2], true), OFFSET_LEFT + ww + 3*BORDER + DIMENTION, OFFSET_TOP + BORDER ,  this);
        else
        	g2.drawImage(renderImage(hh, ww, g2,images[2], null, true), OFFSET_LEFT + ww + 3*BORDER + DIMENTION, OFFSET_TOP + BORDER ,  this);
        
        g2.setColor(BLACK);
        drawRect(OFFSET_LEFT,                             OFFSET_TOP,                             ww, hh, g2);
        drawRect(OFFSET_LEFT,                             OFFSET_TOP + hh + 2*BORDER + DIMENTION, ww, hh, g2);
        drawRect(OFFSET_LEFT + ww + 2*BORDER + DIMENTION, OFFSET_TOP,                             hh, ww, g2);
        
        drawRect(OFFSET_LEFT + 1, OFFSET_TOP + 1, w - OFFSET_RIGHT - OFFSET_LEFT - 2*BORDER, h - OFFSET_BOTTOM - OFFSET_TOP - 2*BORDER, g2);
        
        drawRect(1, 1, w-1 - 2*BORDER, h - 1 - 2*BORDER, g2);
	}
	 public void paint(Graphics g) {
		 Dimension d = getSize();
		 Graphics2D g2 = (Graphics2D)g;
		 render(d.width, d.height, g2);
	 }
    /*
     * Gets the named image using the toolkit of the specified component.
     * Note that this has to work even before we have had a chance to
     * instantiate DemoImages and preload the cache. 
     */
    public static Image getImage(String name, Component cmp) {
        Image img = null;
       

        URLClassLoader urlLoader =
            (URLClassLoader)cmp.getClass().getClassLoader();
        URL fileLoc = urlLoader.findResource(name);
        img = cmp.getToolkit().createImage(fileLoc);

        MediaTracker tracker = new MediaTracker(cmp);
        tracker.addImage(img, 0);
        try {
            tracker.waitForID(0);
            if (tracker.isErrorAny()) {
                System.out.println("Error loading image " + name);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return img;
    }
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		   if (pageIndex >= 1) {
	            return Printable.NO_SUCH_PAGE;
	        }

		   Graphics2D g2 = (Graphics2D)graphics;
		   render((int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight(), g2);

	        return Printable.PAGE_EXISTS;
	}

}
