package com.java.main;

import com.java.main.service.Decryption;
import com.java.main.service.Encryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class SourceEncoder extends JFrame{

	public static final long serialVersionUID = 1L;

	public static SourceEncoder encoder = null;
	public boolean currentCorrectFlderSelected = false;

	private JTextField folderPathTextField = new JTextField(60);
	private JTextField secretKey = new JTextField(35);

	private JButton selectFolderPath = new JButton("Select Folder");
	private JButton startButton = new JButton("Start");
	private JButton exitButton = new JButton("Quit");

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu helpMenu;

	public String currentStreamPath = "C://";

	private JMenuItem aboutManuItem;

	JFileChooser chooser;

	String[] functions = new String[] {"Encode","Decode"};

	JComboBox functionMenu = new JComboBox<>(functions);

	public SourceEncoder() {
		super("To hide and Unhide stuff");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		aboutManuItem = new JMenuItem("About");
		aboutManuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Encoder and Decoder Tool. \n Created and maintained by Tamal Das");
			}
		});

		helpMenu.add(aboutManuItem);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		setResizable(true);

		folderPathTextField.setEditable(true);
		secretKey.setEditable(true);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		final JPanel progress = new JPanel(new GridBagLayout());
		GridBagConstraints constraintProgress = new GridBagConstraints();
		constraintProgress.anchor = GridBagConstraints.WEST;
		constraintProgress.insets = new Insets(10, 10, 10, 10);

		final JLabel labelProgress = new JLabel("Processing File...Please Wait");
		Font boldProgress = new Font("SansSerif", java.awt.Font.BOLD, 18);
		labelProgress.setFont((java.awt.Font) boldProgress);
		constraintProgress.gridx = 0;
		constraintProgress.gridy = 1;
		progress.add(labelProgress, constraintProgress);
		labelProgress.setVisible(true);

		final JPanel newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		functionMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				folderPathTextField.setText("Please select the Root folder.");
				if(functionMenu.getSelectedItem().toString().equals("Encode")) {
					JOptionPane.showConfirmDialog(null, "For Encoding please select the Root directory");
					functionMenu.setSelectedIndex(0);
				}
				if(functionMenu.getSelectedItem().toString().equals("Decode")) {
					JOptionPane.showConfirmDialog(null, "For Decoding please select the folder from the ROOT dir.");
				}
			}
		});

		constraints.gridx = 1;
		constraints.gridy = 0;
		newPanel.add(functionMenu, constraints);

		JLabel label = new JLabel("Select Functionality");
		Font font = new Font("SansSerif", Font.BOLD, 12);
		label.setFont(font);
		constraints.gridx = 0;
		newPanel.add(label, constraints);
		label.setVisible(true);

		selectFolderPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Please make sure the selected folder is the ROOT dir");
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("C:\\"));
				chooser.setDialogTitle("Select Media Path");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if(chooser.showOpenDialog(newPanel) == JFileChooser.APPROVE_OPTION) {
					currentStreamPath = chooser.getSelectedFile().getAbsolutePath();
					if(null != functionMenu.getSelectedItem().toString()) {
						if(!currentStreamPath.equals("C:\\")) {
							currentCorrectFlderSelected = true;
							if(currentCorrectFlderSelected) {
								folderPathTextField.setText(currentStreamPath);
							}else {
								folderPathTextField.setText("Please select the correct folder");
								JOptionPane.showConfirmDialog(null, "Please make sure the selected folder is the ROOT dir");
							}
						}
					} else {
						JOptionPane.showConfirmDialog(null, "No options selected");
					}
				}
			}
		});

		JLabel skeyLabel = new JLabel("Secret Key :");
		skeyLabel.setFont(font);
		constraints.gridx = 0;
		constraints.gridy = 2;
		newPanel.add(skeyLabel, constraints);
		skeyLabel.setVisible(true);

		constraints.gridx = 0;
		constraints.gridy = 1;
		newPanel.add(selectFolderPath, constraints);
		
		constraints.gridx = 1;
		newPanel.add(folderPathTextField, constraints);
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if("Encode".equals(functionMenu.getSelectedItem())) {
					Encryption encryption = new Encryption(folderPathTextField.getText(),secretKey.getText());
					encryption.sourceDecoder();
					folderPathTextField.setText("");
					secretKey.setText("");
				}else if("Decode".equals(functionMenu.getSelectedItem())) {
					Decryption decryption = new Decryption(folderPathTextField.getText(),secretKey.getText());
					decryption.sourceDecoder();
					folderPathTextField.setText("");
					secretKey.setText("");
				}
			}
		});

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		newPanel.add(startButton, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		newPanel.add(exitButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridwidth = 5;
		constraints.anchor = GridBagConstraints.WEST;
		newPanel.add(secretKey, constraints);

		newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Image Encryptor"));
		progress.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Image Encryptor"));
		progress.setVisible(false);

		add(newPanel);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Main function to start the pplication
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				encoder = new SourceEncoder();
				encoder.setVisible(true);
			}
		});
	}
}
