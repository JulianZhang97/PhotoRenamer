package photo_renamer;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * A photo reverter button that implements ActionListener.
 */
public class PhotoRevertButtonListener implements ActionListener{

	/** An instance of Program class that implements all the user features. */
	private Program program;
	
	/** 
	 * A panel in the main PhotoDisplay window that displays a list of buttons
	 * that represent the names of the photo files in the selected directory.
	 */
	private JPanel filePanel;
	
	/**
	 * A label in the main PhotoDisplay window that displays the original and 
	 * current names of the selected photo.
	 */
	private JLabel photoLabel;
	
	/** A panel in the main PhotoDisplay window that displays the currently selected photo.*/
	private JPanel photoPanel;
	
	/** 
	 * An old name of the currently selected photo that the user chooses to
	 * revert the photo's name to.
	 */
	private static String targetName;
	
	/**
	 * A new instance of PhotoRevertButtonListener.
	 * 
	 * @param photoLabel
	 * 		a label in the main PhotoDisplay window that displays the original and 
	 * 		current names of the selected photo.
	 * 
	 * @param filePanel
	 * 		a label in the main PhotoDisplay window that displays the original and 
	 * 		current names of the selected photo.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 * 
	 * @param photoPanel
	 * 		a panel in the main PhotoDisplay window that displays the currently selected photo.
	 */
	public PhotoRevertButtonListener(JLabel photoLabel, JPanel filePanel, Program program, JPanel photoPanel){
		this.photoLabel = photoLabel;
		this.program = program;
		this.filePanel = filePanel;
		this.photoPanel = photoPanel;
		}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			buildRevertNameFrame(photoLabel, filePanel, program, photoPanel).setVisible(true);
		}
		catch(NoPhotoSelectedException b){
			PhotoDisplay.createErrorMessage("No Photo Selected!").setVisible(true);
		}
	}
	
	/**
	 * Builds and returns a photo reverter window.
	 * 
	 * @param photoLabel
	 * 		a label in the main PhotoDisplay window that displays the original and 
	 * 		current names of the selected photo.
	 * 
	 * @param filePanel
	 * 		a label in the main PhotoDisplay window that displays the original and 
	 * 		current names of the selected photo.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 * 
	 * @param photoPanel
	 * 		a panel in the main PhotoDisplay window that displays the currently selected photo.
	 * 
	 * @return a photo reverter window.
	 * 
	 * @throws NoPhotoSelectedException
	 * 		if there is no photo selected by the user yet.
	 */
	public static JDialog buildRevertNameFrame(JLabel photoLabel, JPanel filePanel, Program program, JPanel photoPanel) throws NoPhotoSelectedException{
		if(program.getSelectedPhoto() == null){
			throw new NoPhotoSelectedException("No Photo Selected!");
		}
		else{
		JFrame reverterFrame = new JFrame();
		JDialog reverterWindow = new JDialog(reverterFrame, "View/Revert Photo Name", Dialog.ModalityType.APPLICATION_MODAL);
		reverterWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JLabel revertedPhotoLabel = new JLabel(photoLabel.getText());
		
		JPanel previousNamesSelection = new JPanel();
		previousNamesSelection.setLayout(new BoxLayout(previousNamesSelection, BoxLayout.Y_AXIS));
		
		JButton revertButton = new JButton("Revert Name");
		revertButton.setEnabled(false);
		revertButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				program.revertPhotoName(targetName);
				revertedPhotoLabel.setText("CURRENT PHOTO NAME: " + program.getSelectedPhoto().getCurName() + " " 
						+ "ORIGINAL PHOTO NAME: " + program.getSelectedPhoto().getOrigName());
				photoLabel.setText("CURRENT PHOTO NAME: " + program.getSelectedPhoto().getCurName() + " " 
						+ "ORIGINAL PHOTO NAME: " + program.getSelectedPhoto().getOrigName());
				PhotoDisplay.refreshButtons(filePanel, program, photoLabel, photoPanel);
				reverterWindow.dispose();
			}
		});
		
		ButtonGroup buttonGroup = new ButtonGroup();
		for(String pastPhotoName: program.showPhotoHistory()){
			JRadioButton pastPhotoButton = new JRadioButton(pastPhotoName);
			pastPhotoButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					targetName = pastPhotoName;
					revertButton.setEnabled(true);
				}	
			});
			buttonGroup.add(pastPhotoButton);
			previousNamesSelection.add(pastPhotoButton);
		}
		JScrollPane selectScroll = new JScrollPane(previousNamesSelection);
		selectScroll.setPreferredSize(new Dimension(250, 250));
		
		JTextArea listPreviousNames = new JTextArea();
		listPreviousNames.setText("All Previous Names of this Photo: \n");
		listPreviousNames.setEditable(false);
		for(String photoName: program.showPhotoHistory()){
			listPreviousNames.append(photoName + "\n");
		}
		JScrollPane nameScroll = new JScrollPane(listPreviousNames);
		nameScroll.setPreferredSize(new Dimension(250, 250));
		
		Container container = reverterWindow.getContentPane();
		container.add(selectScroll, BorderLayout.CENTER);
		container.add(revertButton, BorderLayout.EAST);
		container.add(nameScroll, BorderLayout.WEST);
		container.add(revertedPhotoLabel, BorderLayout.SOUTH);
		reverterWindow.pack();
		return reverterWindow;
		}
	}
}