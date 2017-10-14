package photo_renamer;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.AbstractButton;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * A photo renamer.
 * This class is involved in the Singleton Design Pattern.
 * It has only one instance and therefore provides
 * a global point of access.
 */
public class PhotoRenamer{
	
	/**
	 * A new instance of PhotoRenamer.
	 */
	private PhotoRenamer() {	
	}
	
	/**
	 * Returns an instance of PhotoRenamerHolder.
	 * 
	 * @return an instance of PhotoRenamerHolder.
	 */
	public static PhotoRenamer getInstance() {
		return PhotoRenamerHolder.INSTANCE;
	}

	/**
	 * A photo renamer holder.
	 * This class is also involved in the Singleton Design Pattern.
	 * Its role is to create a static instance of PhotoRenamer and
	 * therefore provides a global point of access.
	 */
	private static class PhotoRenamerHolder {
		
		/**
		 * Instantiates a static instance of Photorenamer.
		 */
		private static final PhotoRenamer INSTANCE = new PhotoRenamer();
	}
	
	/**
	 * Builds a directory chooser window for this PhotoRenamer.
	 * Returns a directory chooser window.
	 * 
	 * @return a directory chooser window.
	 */
	public static JFrame buildWindow(){
			
		Program newProgram = new Program();
		newProgram.open();
		
		JFrame directoryFrame = new JFrame("Photo Renamer: Directory Selection");
		directoryFrame.setSize(600, 300);
		directoryFrame.setLayout(new GridLayout(4, 1));
		directoryFrame.addWindowListener(new WindowListener(){

			@Override
			public void windowClosing(WindowEvent e) {
				newProgram.close();
				System.exit(0);
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
		
		JLabel directoryLabel = new JLabel("Please select a directory:", JLabel.CENTER);
		JPanel directoryButtonPanel = new JPanel();
		JPanel closeButtonPanel = new JPanel();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JButton closeButton = new JButton("Close Photo Renamer");
		closeButton.setVerticalTextPosition(AbstractButton.CENTER);
		closeButton.setHorizontalTextPosition(AbstractButton.LEADING);
		closeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				newProgram.close();
				System.exit(0);
			}	
		});
		closeButtonPanel.add(closeButton);
		
		JButton directoryButton = new JButton("Choose Directory");
		directoryButton.setVerticalTextPosition(AbstractButton.CENTER);
		directoryButton.setHorizontalTextPosition(AbstractButton.LEADING);
		directoryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int selection = fileChooser.showOpenDialog(fileChooser);
				if(selection == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					if(selectedFile.exists()){
						newProgram.selectDirectory(selectedFile);
						PhotoDisplay.buildDisplay(newProgram).setVisible(true);
					}
				}
			}
		});
		directoryButtonPanel.add(directoryButton);
		
		Container container = directoryFrame.getContentPane();
		container.add(directoryLabel);
		container.add(directoryButtonPanel);
		container.add(closeButtonPanel);
		return directoryFrame;
	}	
	
	public static void main(String[] args){
		PhotoRenamer.buildWindow().setVisible(true);
	}
}