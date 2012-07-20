package collia.ImageConverter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Locale;

import javax.print.attribute.*;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * This class have to be run with VM parameters -Xms50000000
-Xmx500000000
 * @author collia
 *	
 */
public class MainWindow {
	static ImageConverter ic;
	private static JPanel getParamPane(){
				
		//final JPanel buttons = new JPanel(new GridLayout(1, 6));
		final JPanel buttons = new JPanel(new FlowLayout());
		JCheckBox isPrintDate1 = new JCheckBox("Print date 1");
		JCheckBox isPrintDate2 = new JCheckBox("Print date 2");
		JCheckBox isPrintDate3 = new JCheckBox("Print date 3");
		JButton print = new JButton("Print");
		
		print.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
					printFile();
	
			}
		});
		
		isPrintDate1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(((JCheckBox)arg0.getSource()).isSelected());
				ic.setIsPrintDateImage(((JCheckBox)arg0.getSource()).isSelected(),0);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	SwingUtilities.updateComponentTreeUI(ic);
				    }
				});
			}
		});
		isPrintDate2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(((JCheckBox)arg0.getSource()).isSelected());
				ic.setIsPrintDateImage(((JCheckBox)arg0.getSource()).isSelected(),1);
				//ic.repaint();
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	SwingUtilities.updateComponentTreeUI(ic);
				    }
				});
			}
		});
		isPrintDate3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(((JCheckBox)arg0.getSource()).isSelected());
				ic.setIsPrintDateImage(((JCheckBox)arg0.getSource()).isSelected(),2);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	SwingUtilities.updateComponentTreeUI(ic);
				    }
				});
			}
		});

		buttons.add(isPrintDate1);
		buttons.add(isPrintDate2);
		buttons.add(isPrintDate3);
		
		buttons.add(print);
		return buttons;
	}
	private static void printFile()  {
		
		PrinterJob job = PrinterJob.getPrinterJob();
		if(job != null)
		{
			job.setPrintable(ic);
			PrinterResolution pr = new PrinterResolution(600, 600, PrinterResolution.DPI);
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		    //MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
			MediaSizeName mediaSizeName = MediaSize.ISO.A4.getMediaSizeName();
		    printRequestAttributeSet.add(pr);
		    printRequestAttributeSet.add(mediaSizeName);
		 //   printRequestAttributeSet.add(new Copies(1));
		    printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
		    printRequestAttributeSet.add(new JobName("Foto print", Locale.getDefault()) );
		    printRequestAttributeSet.add(PrintQuality.HIGH);
		    

			if(job.printDialog())
			{
				try {
					//System.out.println(job);
					job.print(printRequestAttributeSet);
				} catch (PrinterException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, 
				   			 "Error on print", "alert",
				   			JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++)
		{
			System.out.printf("Arg[%d]: %s\n", i, args[i]);
		}
		
		 Frame f = new Frame("ImageConvertor");
		 Frame f2 = new Frame("Menu");
	     f.addWindowListener(new WindowAdapter() {
	            public void windowClosing    (WindowEvent e) { System.exit(0); }
	     });
	     f2.addWindowListener(new WindowAdapter() {
	            public void windowClosing    (WindowEvent e) { System.exit(0); }
	     });

	     if(args.length < 3)
	     {
	    	 System.out.println("Wrong parameters. Must be 3 filenames.");
	    	 return;
	     }
	     ic = new ImageConverter( args[0],  args[1],  args[2]);
	     f.add(ic);
	     f2.add(getParamPane());
	     f.pack();
	     f2.pack();
	     f.setSize(new Dimension(500,354));
	     
	     f.setVisible(true);
	     f2.setVisible(true);
	     
	}

}
