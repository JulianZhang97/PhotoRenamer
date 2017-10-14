package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * A renamer button that implements ActionListener.
 */
public class RenamePhotoButtonListener implements ActionListener{
	
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
	 * A new instance of RenamePhotoButtonListener.
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
	public RenamePhotoButtonListener(JLabel photoLabel, JPanel filePanel, Program program, JPanel photoPanel){
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
			buildRenameFrame(photoLabel, filePanel, program, photoPanel).setVisible(true);
		}
		catch(NoPhotoSelectedException a){
			PhotoDisplay.createErrorMessage("No Photo Selected!").setVisible(true);
		}
	}
	
	/**
	 * Builds and returns a renamer window.
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
	 * @return a renamer window.
	 * 
	 * @throws NoPhotoSelectedException
	 * 		if there is no photo selected by the user yet.
	 */
	public static JDialog buildRenameFrame(JLabel photoLabel, JPanel filePanel, Program program, JPanel photoPanel) throws NoPhotoSelectedException{
		if(program.getSelectedPhoto() == null){
			throw new NoPhotoSelectedException("No Photo Selected!");
		}
		else{
			JFrame renamerFrame = new JFrame();
			JDialog renamerWindow = new JDialog(renamerFrame, "Add/Remove Tags", Dialog.ModalityType.APPLICATION_MODAL);
			renamerWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			ArrayList<String> tagsToAdd = new ArrayList<String>();
			
			JLabel renamePhotoLabel = new JLabel(photoLabel.getText());
			
			JTextArea availableTags = new JTextArea();
			availableTags.setEditable(false);	
			availableTags.setText("Available Tags in the Program:\n");
			availableTags.append(program.getTagManager().toString());
			JScrollPane availableScroll = new JScrollPane(availableTags);
			availableScroll.setPreferredSize(new Dimension(250, 250));
			
			
			JPanel selectTags = new JPanel();
			selectTags.setLayout(new BoxLayout(selectTags, BoxLayout.Y_AXIS));
			for(String tag: program.getTagManager().getAvailableTags()){
				JCheckBox tagBox = new JCheckBox(tag);
				tagBox.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e) {
							if(e.getStateChange() == ItemEvent.SELECTED){
								tagsToAdd.add(tag);
						}
							else if(e.getStateChange() == ItemEvent.DESELECTED){
								tagsToAdd.remove(tag);
							}
					}
				});
				selectTags.add(tagBox);
			}
			JScrollPane selectScroll = new JScrollPane(selectTags);
			selectScroll.setPreferredSize(new Dimension(250, 250));
			
			
			JButton addButton = new JButton("Add Selected Tags");
			addButton.setVerticalTextPosition(AbstractButton.CENTER);
			addButton.setHorizontalTextPosition(AbstractButton.LEADING);
			addButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent c){
					if(tagsToAdd.size() == 0){
						PhotoDisplay.createErrorMessage("Please Select Tags to Add").setVisible(true);
					}
					
					else{
						try{
						program.addPhotoTags(tagsToAdd);
						renamePhotoLabel.setText("CURRENT PHOTO NAME: " + program.getSelectedPhoto().getCurName() + " " 
								+ "ORIGINAL PHOTO NAME: " + program.getSelectedPhoto().getOrigName());
						photoLabel.setText("CURRENT PHOTO NAME: " + program.getSelectedPhoto().getCurName() + " " 
								+ "ORIGINAL PHOTO NAME: " + program.getSelectedPhoto().getOrigName());
						PhotoDisplay.refreshButtons(filePanel, program, photoLabel, photoPanel);
						renamerWindow.dispose();
						}
						catch(DuplicateTagsException e){
							PhotoDisplay.createErrorMessage("Cannot Add Duplicate Tags!").setVisible(true);
							//tagsToAdd.clear(); 
							}
						}
					}
				});
			Container container = renamerWindow.getContentPane();
			container.add(selectScroll, BorderLayout.CENTER);
			container.add(availableScroll, BorderLayout.WEST);
			container.add(addButton, BorderLayout.EAST);
			container.add(renamePhotoLabel, BorderLayout.NORTH);
			renamerWindow.pack();
			return renamerWindow;
			}
	}
}