package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A tag editor button that implements ActionListener.
 */
public class TagEditButtonListener implements ActionListener {
	
	/** An instance of Program class that implements all the user features. */
	private Program program;
	
	/**
	 * A new instance of this TagEditButtonListener.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 */
	public TagEditButtonListener(Program program){
		this.program = program;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		buildTagFrame(program).setVisible(true);
	}
	
	/**
	 * Builds and returns a tag editor window.
	 * 
	 * @param program
	 * 		an instance of Program class that implements all the user features.
	 * 
	 * @return a tag editor window.
	 */
	public static JDialog buildTagFrame(Program program){
		JFrame tagEditFrame = new JFrame();
		JDialog tagWindow = new JDialog(tagEditFrame, "Add/Remove Tags", Dialog.ModalityType.APPLICATION_MODAL);
		tagWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		ArrayList<String> tags = new ArrayList<String>();
		
		JTextField userInput = new JTextField();
		
		JTextArea selectedTags = new JTextArea();
		
		selectedTags.setEditable(false);	
		selectedTags.setText("Tags to be added or deleted: \n");
		JScrollPane selectScroll = new JScrollPane(selectedTags);
		selectScroll.setPreferredSize(new Dimension(250, 250));
		
		userInput.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				tags.add(userInput.getText());
				selectedTags.append("@" + userInput.getText() + "\n");
				userInput.setText("");
				}
			});
		JTextArea currentTags = new JTextArea();
		currentTags.setEditable(false);	
		currentTags.setText("Current Tags: \n");
		currentTags.append(program.getTagManager().toString());
		JScrollPane currentScroll = new JScrollPane(currentTags);
		currentScroll.setPreferredSize(new Dimension(250, 250));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(175, 100));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		JButton addTagsButton = new JButton("Add Selected Tags");
		addTagsButton.setEnabled(false);
		addTagsButton.setVerticalTextPosition(AbstractButton.CENTER);
		addTagsButton.setHorizontalTextPosition(AbstractButton.LEADING);
		addTagsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				try {
					program.getTagManager().addAvailableTags(tags);
					tagWindow.dispose();
				} catch (DuplicateTagsException e) {
					PhotoDisplay.createErrorMessage("One or more selected tags already exist in the Program!").setVisible(true);
					tags.clear();
					selectedTags.setText("Tags to be added or deleted: \n");
					currentTags.removeAll();
					currentTags.setText("Current Tags: \n");
					currentTags.append(program.getTagManager().toString());
					
				}	
			}
		});
		buttonPanel.add(addTagsButton);
		
		JButton removeTagsButton = new JButton("Remove Selected Tags");
		removeTagsButton.setEnabled(false);
		removeTagsButton.setVerticalTextPosition(AbstractButton.CENTER);
		removeTagsButton.setHorizontalTextPosition(AbstractButton.LEADING);
		removeTagsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				try {
					program.getTagManager().removeAvailableTags(tags);
					tagWindow.dispose();
					
				} catch (NonExistentTagsException e) {
					PhotoDisplay.createErrorMessage("One or more selected tags do not exist in the Program!").setVisible(true);
					tags.clear();
					selectedTags.setText("Tags to be added or deleted: \n");
					currentTags.removeAll();
					currentTags.setText("Current Tags: \n");
					currentTags.append(program.getTagManager().toString());
				}	
			}
		});
		buttonPanel.add(removeTagsButton);
		
		selectedTags.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {	
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				addTagsButton.setEnabled(true);
				removeTagsButton.setEnabled(true);
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}
		});
		Container container = tagWindow.getContentPane();
		container.add(userInput, BorderLayout.NORTH);
		container.add(selectScroll, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.EAST);
		container.add(currentScroll, BorderLayout.WEST);
		tagWindow.pack();
		return tagWindow;
	}
}