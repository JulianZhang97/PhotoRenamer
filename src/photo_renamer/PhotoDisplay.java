package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * A photo displayer.
 */
public class PhotoDisplay {
	
	/**
	 * Builds a display window for the Photo Renamer program.
	 * Returns a photo displayer window.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 * 		
	 * @return the photo displayer window of this program.
	 */
	public static JDialog buildDisplay(Program program){
		
		JFrame programFrame = new JFrame();
		JDialog programWindow = new JDialog(programFrame, "Photo Renamer", Dialog.ModalityType.APPLICATION_MODAL);
		programWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JPanel photoPanel = new JPanel();
		JScrollPane photoScroll = new JScrollPane(photoPanel);
		photoScroll.setPreferredSize(new Dimension(1024, 768));
		
		JLabel photoLabel = new JLabel("No Photo Currently Selected");
		
		JPanel programMenuPanel = new JPanel();
		programMenuPanel.setPreferredSize(new Dimension(175, 768));
		programMenuPanel.setLayout(new BoxLayout(programMenuPanel, BoxLayout.Y_AXIS));
		
		JTextArea programText = new JTextArea();
		programText.setEditable(false);
		JScrollPane programTextScroll = new JScrollPane(programText);
		programTextScroll.setPreferredSize(new Dimension(200, 100));
		
		
		JPanel filePanel = new JPanel();
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		JScrollPane fileScroll = new JScrollPane(filePanel);
		fileScroll.setPreferredSize(new Dimension(300,768));
		JLabel fileLabel = new JLabel("All Photos Under This Directory:");
		filePanel.add(fileLabel);
		refreshButtons(filePanel, program, photoLabel, photoPanel);
		
		
		JButton viewLog = new JButton("View Program Log");
		viewLog.setVerticalTextPosition(AbstractButton.CENTER);
		viewLog.setHorizontalTextPosition(AbstractButton.LEADING);
		viewLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				programText.removeAll();
				programText.setText("Program Log\n");
				programText.append(program.getLog().displayLog());
			}
		});
		
		JButton editTags = new JButton("Add/Remove Tags");
		editTags.setVerticalTextPosition(AbstractButton.CENTER);
		editTags.setHorizontalTextPosition(AbstractButton.LEADING);
		editTags.addActionListener(new TagEditButtonListener(program));
		
		
		JButton renamePhoto = new JButton("Tag Photo");
		renamePhoto.setVerticalTextPosition(AbstractButton.CENTER);
		renamePhoto.setHorizontalTextPosition(AbstractButton.LEADING);
		renamePhoto.addActionListener(new RenamePhotoButtonListener(photoLabel, filePanel, program, photoPanel));
		
		JButton revertName = new JButton("View/Revert Photo Name");
		revertName.setVerticalTextPosition(AbstractButton.CENTER);
		revertName.setHorizontalTextPosition(AbstractButton.LEADING);
		revertName.addActionListener(new PhotoRevertButtonListener(photoLabel, filePanel, program, photoPanel));
		
		JButton directorySelect = new JButton("Select Another Directory");
		directorySelect.setVerticalTextPosition(AbstractButton.CENTER);
		directorySelect.setHorizontalTextPosition(AbstractButton.LEADING);
		directorySelect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				programWindow.dispose();
			}
		});
		
		programMenuPanel.add(viewLog);
		programMenuPanel.add(editTags);
		programMenuPanel.add(renamePhoto);
		programMenuPanel.add(revertName);
		programMenuPanel.add(directorySelect);
		
		Container container = programWindow.getContentPane();
		container.add(photoLabel, BorderLayout.NORTH);
		container.add(fileScroll, BorderLayout.EAST);
		container.add(photoScroll, BorderLayout.CENTER);
		container.add(programMenuPanel, BorderLayout.WEST);
		container.add(programTextScroll, BorderLayout.SOUTH);
		
		programWindow.pack();
		return programWindow;
	}
	
	/**
	 * Utilizes the Program class to refresh and update the information displayed 
	 * by this photo displayer's filePanel, photoLabel, and photoPanel after 
	 * every time a photo is renamed.
	 * 
	 * @param filePanel
	 * 		a panel that displays a list of buttons that represent all the names
	 * 		of photo files in the selected directory.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 * 
	 * @param photoLabel
	 * 		a label that displays the original and current names of the selected photo.
	 * 
	 * @param photoPanel
	 * 		a panel that displays the currently selected photo.
	 */
	public static void refreshButtons(JPanel filePanel, Program program, JLabel photoLabel, JPanel photoPanel){
		
		filePanel.removeAll();
		program.selectDirectory(program.getCurDirectory());
		for(File photoFile: program.getDirectoryFiles()){
			JButton photoButton = new JButton(photoFile.getName());
			photoButton.setVerticalTextPosition(AbstractButton.CENTER);
			photoButton.setHorizontalTextPosition(AbstractButton.LEADING);
			photoButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					program.setSelectedPhoto(photoFile);
					try{
						BufferedImage photo = ImageIO.read(photoFile);
						ImageIcon image = new ImageIcon(photo);
						JLabel imageLabel = new JLabel(image);
						photoPanel.removeAll();
						photoPanel.add(imageLabel);
						photoPanel.revalidate();
						photoPanel.repaint();
						photoLabel.setText("CURRENT PHOTO NAME: " + program.getSelectedPhoto().getCurName() + " " 
								+ "ORIGINAL PHOTO NAME: " + program.getSelectedPhoto().getOrigName());
					}
					catch(IOException r){
						r.printStackTrace();
					}
				}
			});
			filePanel.add(photoButton);
		}
		filePanel.revalidate();
		filePanel.repaint();
	}
	
	/**
	 * Returns a window that displays an error message.
	 * 
	 * @param error
	 * 		the error message that is going to be displayed.
	 * 
	 * @return a window displaying the error message error.
	 */
	public static JDialog createErrorMessage(String error){
		JFrame errorFrame = new JFrame();
		JDialog errorWindow = new JDialog(errorFrame, "ERROR!", Dialog.ModalityType.APPLICATION_MODAL);
		errorWindow.setLayout(new GridLayout(3, 1));
		errorWindow.setSize(new Dimension(300,150));
		errorWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JLabel directoryLabel = new JLabel(error, JLabel.CENTER);
		JPanel buttonPanel = new JPanel();
		
		JButton acceptButton = new JButton("OK");
		acceptButton.setVerticalTextPosition(AbstractButton.CENTER);
		acceptButton.setHorizontalTextPosition(AbstractButton.LEADING);
		acceptButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				errorFrame.dispose();	
			}
		});
		buttonPanel.add(acceptButton);
		
		Container container = errorWindow.getContentPane();
		container.add(directoryLabel, BorderLayout.NORTH);
		container.add(buttonPanel, BorderLayout.CENTER);
		
		return errorWindow;
	}
}