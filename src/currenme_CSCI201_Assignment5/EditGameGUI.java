package currenme_CSCI201_Assignment5;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.util.Timer; 
import java.util.TimerTask; 

import javax.swing.filechooser.FileNameExtensionFilter;

public class EditGameGUI extends JFrame
{
	public ImprovedJButton[][] buttonArray; 
	public ImprovedJButton[][] buttonArrayComp; 
	public JLabel[][] labelArray; 
	boolean editMode = true; 
	public File battleFileName; 
	public char tempComp[][] = new char[10][10]; 
	public char tempPlayer[][] = new char[10][10]; 
	boolean cruiserPlaced = false, battleshipPlaced = false, aircraftcarrierPlaced = false; 
	JButton startButton = new JButton("Start");
	JPanel overall;
	JPanel edit;
	JPanel jp, jp2;
	JPanel GPanel; 
	public int compGuesses[][] = new int[10][10]; 
	JLabel scores; 
	JPanel cards;
	JPanel gameCard; 
	int Acount=0, Bcount=0, Ccount=0, Dcount=0; 
	int PAcount=0, PBcount=0, PCcount=0, PDcount=0; 
	boolean gameIP = true; 
	JMenuBar topBar; 
	JMenu info; 
	JMenuItem about;
	JMenuItem howTo; 
	Timer timer; 
	Destroyer destroyer1 = new Destroyer(); 
	Destroyer destroyer2 = new Destroyer(); 
	Destroyer CompDestroyer1 = new Destroyer(); 
	Destroyer CompDestroyer2 = new Destroyer(); 
	int[][] compDestroyerPos = new int[4][2]; 
	int compDestroyerCount = 0; 
	private int gameRow = 0;
	public String Name1, Name2; 
	Server editServer = null; 
	Client editClient = null; 
	boolean playingComp = false; 
	boolean recClientReady = false; 
	boolean recServerReady = false; 
	boolean clientReady = false; 
	boolean serverReady = false; 
	EditGameGUI(String n1,String n2, Thread ServerClient)
	{
		super("Battleship");
		for (int i = 0; i < 10; i++)
		{
			for (int k = 0; k < 10; k++)
			{
				tempComp[i][k] = 'X'; 
			}
		}
		startButton.setEnabled(false); 
		Name1 = n1; 
		Name2 = n2; 
		if (ServerClient != null && ServerClient.getClass().getName().contains("Server"))
			editServer = (Server) ServerClient; 
		else if (ServerClient != null && ServerClient.getClass().getName().contains("Client"))
			editClient = (Client) ServerClient; 
		else if (ServerClient == null)
			playingComp = true; 
		TimeFunction(); 
		topBar = new JMenuBar(); 
		info = new JMenu("Info"); 
		about = new JMenuItem("About"); 
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		howTo = new JMenuItem("How to"); 
		howTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		about.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ie2)
			{
				JOptionPane.showMessageDialog(null, "Assignment #4. Developed by Curren Mehta", "Note", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		howTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ie)
			{

				JTextArea mainText = new JTextArea(); 
				mainText.setLineWrap(true);
				mainText.setWrapStyleWord(true); 
				mainText.append("To begin click on the position where you want to place your ships." + '\n');
				mainText.append("Place all five ships" + '\n'); 
				mainText.append("Click on a ship to delete it in edit mode" + '\n'); 
				mainText.append("Choose a valid .battle file to read in, and start the game" + '\n'); 
				mainText.append("Attempt to guess all the positions of the computer's battleships" + '\n'); 
				mainText.append("Try to do this before the computer guesses where all of your ships are."); 
				JScrollPane myVertPane = new JScrollPane(mainText); 
				myVertPane.setPreferredSize( new Dimension(200, 200)); 
				JOptionPane.showMessageDialog(null, myVertPane, "How To Play", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
		info.add(howTo); 
		info.add(about); 
		topBar.add(info); 

		overall = new JPanel(new BorderLayout()); 
		edit  = getEditPanel(); 
		jp = new JPanel(); 
		jp2 = new JPanel(); 
		GPanel = getGLPanel(); 

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				compGuesses[i][j] = 0; 
			}
		}

		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ie)
			{
				if (editClient!= null && playingComp == false)
				{
					while (!recServerReady)
					{
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					} 
				}
				if (editServer!= null && playingComp == false)
				{
					while (!recClientReady)
					{
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					} 
				}
				if (playingComp == true)
				{
					PlayGameGUI play = new PlayGameGUI(tempPlayer, tempComp, destroyer1, destroyer2, CompDestroyer1, CompDestroyer2, Name1, Name2, null); 
				}
				else if (editServer != null)
				{
					PlayGameGUI play = new PlayGameGUI(tempPlayer, tempComp, destroyer1, destroyer2, CompDestroyer1, CompDestroyer2, Name1, Name2, editServer);
					editServer.setServerPGG(play);
				}
				else
				{
					PlayGameGUI play = new PlayGameGUI(tempPlayer, tempComp, destroyer1, destroyer2, CompDestroyer1, CompDestroyer2, Name1, Name2, editClient);
					editClient.setClientPGG(play); 
				}
				
				setVisible(false); 
			}
		}); 


		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				tempPlayer[i][j] = 'X'; 
			}
		}


		jp.setLayout(new FlowLayout());

		setSize(1240, 500);
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		jp.add(GPanel);

		JPanel GPanel_comp = getGLPanelComp(); 
		jp.add(GPanel_comp); 

		jp.setBackground(Color.yellow); 

		overall.add(edit, BorderLayout.SOUTH); 

		overall.add(jp, BorderLayout.CENTER);

		overall.add(topBar, BorderLayout.NORTH); 

		add(overall); 
		setVisible(true); 
	}

	public void TimeFunction()
	{
		int wait = 1000; 
		int period = 1000; 
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			public void run() 
			{
				repaint(); 
			}
		}, wait, period);
	}

	private JPanel getGLPanel()
	{
		buttonArray = new ImprovedJButton[11][11]; 
		JPanel jp_ret = new JPanel(new BorderLayout()); 
		JLabel label = new JLabel(Name1, SwingConstants.CENTER); 
		jp_ret.add(label, BorderLayout.NORTH); 
		JPanel jp = new JPanel(); 
		jp.setBorder(BorderFactory.createLineBorder(Color.black));
		jp.setLayout(new GridLayout(11,11));	
		for (int i = 0; i<11; i++)
		{
			for (int j = 0; j<11; j++)
			{
				buttonArray[i][j] = new ImprovedJButton(); 
				buttonArray[i][j].setBackground(new Color(0,0,0,0));
				buttonArray[i][j].setOpaque(false); 
				if(j==0 || i==10)
					buttonArray[i][j].setEnabled(false);	
				if(j!=0 && i!=10)
				{
					OptionAdapter OA = new OptionAdapter(i,j); 
					buttonArray[i][j].addActionListener(OA);
				}
				buttonArray[i][j].setPreferredSize(new Dimension(55, 30));
				buttonArray[i][j].setFont(buttonArray[i][j].getFont().deriveFont(7));
				jp.add(buttonArray[i][j]);
			}
		}

		for (int a = 0; a < 10; a++)
		{
			for (int b = 1; b < 11; b++)
			{
				Icon imageQ = new ImageIcon("Resources/Q.png");
				buttonArray[a][b].setText(""); 
				buttonArray[a][b].setIcon(imageQ);
			}
		} 

		buttonArray[0][0].setText("A"); 
		buttonArray[1][0].setText("B"); 
		buttonArray[2][0].setText("C"); 
		buttonArray[3][0].setText("D"); 
		buttonArray[4][0].setText("E"); 
		buttonArray[5][0].setText("F"); 
		buttonArray[6][0].setText("G"); 
		buttonArray[7][0].setText("H"); 
		buttonArray[8][0].setText("I"); 
		buttonArray[9][0].setText("J"); 
		buttonArray[10][0].setText(" "); 
		for (int k = 1; k < 11; k++)
		{
			String S = Integer.toString(k);
			buttonArray[10][k].setText(S);
		}
		jp_ret.add(jp, BorderLayout.CENTER); 
		return jp_ret; 
	}

	private JPanel getGLPanelComp()
	{
		buttonArrayComp = new ImprovedJButton[11][11]; 
		JPanel jp_ret = new JPanel(new BorderLayout()); 
		JLabel label = new JLabel(Name2, SwingConstants.CENTER); 
		jp_ret.add(label, BorderLayout.NORTH); 
		JPanel jp = new JPanel(); 
		jp.setBorder(BorderFactory.createLineBorder(Color.black));
		jp.setLayout(new GridLayout(11,11));
		for (int i = 0; i<11; i++)
		{
			for (int j = 0; j<11; j++)
			{
				buttonArrayComp[i][j] = new ImprovedJButton(); 
				buttonArrayComp[i][j].setBackground(new Color(0,0,0,0));
				buttonArrayComp[i][j].setOpaque(false); 
				buttonArrayComp[i][j].setEnabled(false);
				buttonArrayComp[i][j].setPreferredSize(new Dimension(55, 30));
				buttonArrayComp[i][j].setFont(buttonArrayComp[i][j].getFont().deriveFont(7));
				jp.add(buttonArrayComp[i][j]);
			}
		}
		buttonArrayComp[0][0].setText("A"); 
		buttonArrayComp[1][0].setText("B"); 
		buttonArrayComp[2][0].setText("C"); 
		buttonArrayComp[3][0].setText("D"); 
		buttonArrayComp[4][0].setText("E"); 
		buttonArrayComp[5][0].setText("F"); 
		buttonArrayComp[6][0].setText("G"); 
		buttonArrayComp[7][0].setText("H"); 
		buttonArrayComp[8][0].setText("I"); 
		buttonArrayComp[9][0].setText("J"); 
		buttonArrayComp[10][0].setText(" "); 

		for (int a = 0; a < 10; a++)
		{
			for (int b = 1; b < 11; b++)
			{
				Icon imageQ = new ImageIcon("Resources/Q.png");
				buttonArrayComp[a][b].setText(""); 
				buttonArrayComp[a][b].setIcon(imageQ);
			}
		}

		for (int k = 1; k < 11; k++)
		{
			String S = Integer.toString(k);
			buttonArrayComp[10][k].setText(S);
		}
		jp_ret.add(jp, BorderLayout.CENTER); 
		return jp_ret; 
	}

	private JPanel getEditPanel()
	{
		JPanel jp = new JPanel(); 
		jp.add(startButton); 
		return jp; 
	}

	public void readInFromOnline(String tempS)
	{
		char tempC; 
		for (int j = 0; j < 10; j++)
		{
			tempC = tempS.charAt(j);
			tempComp[gameRow][j] = tempC; 
			if (tempC == 'D')
			{
				compDestroyerPos[compDestroyerCount][0] = gameRow; 
				compDestroyerPos[compDestroyerCount][1] = j+1; 
				compDestroyerCount++; 
			}
		}
		gameRow++; 
		CompDestroyer1.setCoordinates(compDestroyerPos[0][0], compDestroyerPos[0][1], compDestroyerPos[1][0], compDestroyerPos[1][1]);
		CompDestroyer1.isPlaced = true; 
		CompDestroyer2.setCoordinates(compDestroyerPos[2][0], compDestroyerPos[2][1], compDestroyerPos[3][0], compDestroyerPos[3][1]);
		CompDestroyer2.isPlaced = true; 
	}

	class OptionAdapter implements ActionListener
	{
		public JComboBox<String> shipOptions;
		int I, J; 
		public OptionAdapter (int row, int col)
		{
			I = row; 
			J = col; 
		}

		public void actionPerformed(ActionEvent ie)
		{
			JPanel optionsPanel = new JPanel(new BorderLayout()); 
			String[] ships = {"Select Ship", "Cruiser", "Destroyer", "Battleship", "Aircraft Carrier"}; 
			shipOptions = new JComboBox<String>(ships);
			shipOptions.setSelectedIndex(0);
			if (aircraftcarrierPlaced == true)
			{
				int size = shipOptions.getItemCount(); 
				for(int i = 0; i < size; i++)
				{
					if (shipOptions.getItemAt(i).equals("Aircraft Carrier") == true)
					{
						shipOptions.removeItemAt(i); 
						break; 
					}
				}
			}
			if (cruiserPlaced == true)
			{
				int size = shipOptions.getItemCount(); 
				for (int i = 0; i < size; i++)
				{
					if (shipOptions.getItemAt(i).equals("Cruiser") == true)
					{
						shipOptions.removeItemAt(i); 
						break; 
					}
				}
			}
			if (battleshipPlaced == true)
			{
				int size = shipOptions.getItemCount(); 
				for(int i = 0; i<size; i++)
				{
					if (shipOptions.getItemAt(i).equals("Battleship") == true)
					{
						shipOptions.removeItemAt(i);
						break; 
					}
				}
			}
			if (destroyer1.isPlaced == true && destroyer2.isPlaced == true)
			{
				int size = shipOptions.getItemCount(); 
				for (int i = 0; i<size; i++)
				{
					if (shipOptions.getItemAt(i).equals("Destroyer") == true)
					{
						shipOptions.removeItemAt(i); 
						break; 
					}
				}
			}
			directionAdapter DA = new directionAdapter(this); 
			shipOptions.addActionListener(DA); 
			optionsPanel.add(shipOptions, BorderLayout.NORTH); 
			JRadioButton east = new JRadioButton("East"); 
			JRadioButton north = new JRadioButton("North"); 
			JRadioButton south = new JRadioButton("South"); 
			JRadioButton west = new JRadioButton("West"); 
			ButtonGroup choices = new ButtonGroup(); 
			choices.add(north); 
			choices.add(south); 
			choices.add(east); 
			choices.add(south); 
			JPanel holdButtons = new JPanel(); 
			holdButtons.add(north); 
			holdButtons.add(south); 
			holdButtons.add(east); 
			holdButtons.add(west); 
			Object[] confirm = {"Place ship", "Cancel"}; 
			optionsPanel.add(holdButtons, BorderLayout.CENTER); 
			if ((buttonArray[I][J].getIcon().toString().equals("Resources/Q.png") == true) && cruiserPlaced == true && aircraftcarrierPlaced == true 
					&& battleshipPlaced == true && destroyer1.isPlaced == true && destroyer2.isPlaced == true )
				JOptionPane.showMessageDialog(null, "All ships placed. Click on ship to remove selection", "Note", JOptionPane.INFORMATION_MESSAGE);
			else if (buttonArray[I][J].getIcon().toString().equals("Resources/A.png") == true)
			{
				if (buttonArray[I+1][J].getIcon().toString().equals("Resources/A.png") == true || buttonArray[I-1][J].getIcon().toString().equals("Resources/A.png") == true)
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int I1 = I; 
					int I2 = I; 
					I1++; 
					while (I1<=9 && buttonArray[I1][J].getIcon().toString().equals("Resources/A.png") == true)
					{
						buttonArray[I1][J].setText(""); 
						buttonArray[I1][J].setIcon(imageQ);
						I1++; 
					}
					I2--; 
					while (I2>=0 && buttonArray[I2][J].getIcon().toString().equals("Resources/A.png") == true)
					{
						buttonArray[I2][J].setText(""); 
						buttonArray[I2][J].setIcon(imageQ);
						I2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					aircraftcarrierPlaced = false; 
				}
				if (buttonArray[I][J+1].getIcon().toString().equals("Resources/A.png") == true || buttonArray[I][J-1].getIcon().toString().equals("Resources/A.png") == true)
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int J1 = J; 
					int J2 = J; 
					J1++; 
					while (J1<=10 && buttonArray[I][J1].getIcon().toString().equals("Resources/A.png") == true)
					{
						buttonArray[I][J1].setText(""); 
						buttonArray[I][J1].setIcon(imageQ);
						J1++; 
					}
					J2--; 
					while (J2>=1 && buttonArray[I][J2].getIcon().toString().equals("Resources/A.png") == true)
					{
						buttonArray[I][J2].setText(""); 
						buttonArray[I][J2].setIcon(imageQ);
						J2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					aircraftcarrierPlaced = false; 
				}
			}
			else if (buttonArray[I][J].getIcon().toString().equals("Resources/B.png") == true)
			{
				if (buttonArray[I+1][J].getIcon().toString().equals("Resources/B.png") == true || buttonArray[I-1][J].getIcon().toString().equals("Resources/B.png") == true)
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int I1 = I; 
					int I2 = I; 
					I1++; 
					while (I1<=9 && buttonArray[I1][J].getIcon().toString().equals("Resources/B.png") == true)
					{
						buttonArray[I1][J].setText(""); 
						buttonArray[I1][J].setIcon(imageQ);
						I1++; 
					}
					I2--; 
					while (I2>=0 && buttonArray[I2][J].getIcon().toString().equals("Resources/B.png") == true)
					{
						buttonArray[I2][J].setText(""); 
						buttonArray[I2][J].setIcon(imageQ);
						I2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					battleshipPlaced = false; 
				}
				if (buttonArray[I][J+1].getIcon().toString().equals("Resources/B.png") == true || buttonArray[I][J-1].getIcon().toString().equals("Resources/B.png") == true)
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int J1 = J; 
					int J2 = J; 
					J1++; 
					while (J1<=10 && buttonArray[I][J1].getIcon().toString().equals("Resources/B.png") == true)
					{
						buttonArray[I][J1].setText(""); 
						buttonArray[I][J1].setIcon(imageQ);
						J1++; 
					}
					J2--; 
					while (J2>=1 && buttonArray[I][J2].getIcon().toString().equals("Resources/B.png") == true)
					{
						buttonArray[I][J2].setText(""); 
						buttonArray[I][J2].setIcon(imageQ);
						J2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					battleshipPlaced = false; 
				}
			}
			else if (buttonArray[I][J].getIcon().toString().equals("Resources/Resources/C.png") == true)
			{
				if (buttonArray[I+1][J].getIcon().toString().equals("Resources/Resources/C.png") == true || buttonArray[I-1][J].getIcon().toString().equals("Resources/Resources/C.png") == true)
				{
					Icon imageQ = new ImageIcon("Resrouces/Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int I1 = I; 
					int I2 = I; 
					I1++; 
					while (I1<=9 && buttonArray[I1][J].getIcon().toString().equals("Resrouces/Resources/C.png") == true)
					{
						buttonArray[I1][J].setText(""); 
						buttonArray[I1][J].setIcon(imageQ);
						I1++; 
					}
					I2--; 
					while (I2>=0 && buttonArray[I2][J].getIcon().toString().equals("Resources/Resources/C.png") == true)
					{
						buttonArray[I2][J].setText(""); 
						buttonArray[I2][J].setIcon(imageQ);
						I2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					cruiserPlaced = false; 
				}
				if (buttonArray[I][J+1].getIcon().toString().equals("Resources/Resources/C.png") == true || buttonArray[I][J-1].getIcon().toString().equals("Resources/Resources/C.png") == true)
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[I][J].setText(""); 
					buttonArray[I][J].setIcon(imageQ);
					int J1 = J; 
					int J2 = J; 
					J1++; 
					while (J1<=10 && buttonArray[I][J1].getIcon().toString().equals("Resources/Resources/C.png") == true)
					{
						buttonArray[I][J1].setText(""); 
						buttonArray[I][J1].setIcon(imageQ);
						J1++; 
					}
					J2--; 
					while (J2>=1 && buttonArray[I][J2].getIcon().toString().equals("Resources/Resources/C.png") == true)
					{
						buttonArray[I][J2].setText(""); 
						buttonArray[I][J2].setIcon(imageQ);
						J2--; 
					}
					overall.revalidate(); 
					overall.repaint(); 
					cruiserPlaced = false; 
				}
			}
			else if (buttonArray[I][J].getIcon().toString().equals("Resources/D.png") == true)
			{
				Icon imageQ = new ImageIcon("Resources/Q.png");
				if ((destroyer1.startRow == I && destroyer1.startCol == J) || (destroyer1.endRow == I && destroyer1.endCol == J))
				{
					destroyer1.isPlaced = false; 
					buttonArray[destroyer1.startRow][destroyer1.startCol].setText(""); 
					buttonArray[destroyer1.startRow][destroyer1.startCol].setIcon(imageQ);
					buttonArray[destroyer1.endRow][destroyer1.endCol].setText(""); 
					buttonArray[destroyer1.endRow][destroyer1.endCol].setIcon(imageQ);
				}
				if ((destroyer2.startRow == I && destroyer2.startCol == J) || (destroyer2.endRow == I && destroyer2.endCol == J))
				{
					destroyer2.isPlaced = false; 
					buttonArray[destroyer2.startRow][destroyer2.startCol].setText(""); 
					buttonArray[destroyer2.startRow][destroyer2.startCol].setIcon(imageQ);
					buttonArray[destroyer2.endRow][destroyer2.endCol].setText(""); 
					buttonArray[destroyer2.endRow][destroyer2.endCol].setIcon(imageQ);
				}

			}


			else if ((buttonArray[I][J].getIcon().toString().equals("Resources/Q.png") == true) && (cruiserPlaced == false || aircraftcarrierPlaced == false 
					|| battleshipPlaced == false || destroyer1.isPlaced == false || destroyer2.isPlaced == false))
			{
				int result = JOptionPane.showOptionDialog(null, optionsPanel, "Ship placement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, confirm, confirm[0]);
				boolean validA = true, validB = true, validC = true, validD = true; 
				String tempS;  
				if (result == 0)
				{
					if (DA.getA() == true)
					{
						if(east.isSelected() == true)
						{
							if(J+4 <= 10)
							{
								for(int q = 0; q<5; q++)
								{
									if (buttonArray[I][J+q].getIcon().toString().equals("Resources/Q.png") == false)
										validA = false; 	
								}
								if (validA == true)
								{
									for (int m = 0; m<5; m++)
									{
										aircraftcarrierPlaced = true; 
										Icon imageA = new ImageIcon("Resources/A.png");
										buttonArray[I][J+m].setText(""); 
										buttonArray[I][J+m].setIcon(imageA); 
										tempPlayer[I][J+m-1] = 'A'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
						}
						else if(north.isSelected() == true)
						{
							if(I-4 >= 0)
							{
								for(int q = 0; q<5; q++)
								{
									if (buttonArray[I-q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validA = false; 	
								}
								if (validA == true)
								{
									for (int m = 0; m<5; m++)
									{
										aircraftcarrierPlaced = true; 
										Icon imageA = new ImageIcon("Resources/A.png");
										buttonArray[I-m][J].setText(""); 
										buttonArray[I-m][J].setIcon(imageA);										
										tempPlayer[I-m][J-1] = 'A'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(west.isSelected() == true)
						{
							if(J-4 >= 1)
							{
								for(int q = 0; q<5; q++)
								{
									tempS = buttonArray[I][J-q].getText(); 
									if (buttonArray[I][J-q].getIcon().toString().equals("Resrouces/Resources/Q.png") == false)
										validA = false; 	
								}
								if (validA == true)
								{
									for (int m = 0; m<5; m++)
									{
										aircraftcarrierPlaced = true; 
										Icon imageA = new ImageIcon("Resources/A.png");
										buttonArray[I][J-m].setText(""); 
										buttonArray[I][J-m].setIcon(imageA);										
										tempPlayer[I][J-m-1] = 'A'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
						}
						else if(south.isSelected() == true)
						{
							if(I+4 <= 9)
							{
								for(int q = 0; q<5; q++)
								{
									tempS = buttonArray[I+q][J].getText(); 
									if (buttonArray[I+q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validA = false; 	
								}
								if (validA == true)
								{
									for (int m = 0; m<5; m++)
									{
										aircraftcarrierPlaced = true; 
										Icon imageA = new ImageIcon("Resources/A.png");
										buttonArray[I+m][J].setText(""); 
										buttonArray[I+m][J].setIcon(imageA);
										tempPlayer[I+m][J-1] = 'A'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}
					if (DA.getB() == true)
					{
						if(east.isSelected() == true)
						{
							if(J+3 <= 10)
							{
								for(int q = 0; q<4; q++)
								{
									tempS = buttonArray[I][J+q].getText(); 
									if (buttonArray[I][J+q].getIcon().toString().equals("Resources/Q.png") == false)
										validB = false; 	
								}
								if (validB == true)
								{
									for (int m = 0; m<4; m++)
									{
										battleshipPlaced = true; 
										Icon imageB = new ImageIcon("Resources/B.png");
										buttonArray[I][J+m].setText(""); 
										buttonArray[I][J+m].setIcon(imageB);
										tempPlayer[I][J+m-1] = 'B'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(north.isSelected() == true)
						{
							if(I-3 >= 0)
							{
								for(int q = 0; q<4; q++)
								{
									tempS = buttonArray[I-q][J].getText(); 
									if (buttonArray[I-q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validB = false; 	
								}
								if (validB == true)
								{
									for (int m = 0; m<4; m++)
									{
										battleshipPlaced = true; 
										Icon imageB = new ImageIcon("Resources/B.png");
										buttonArray[I-m][J].setText(""); 
										buttonArray[I-m][J].setIcon(imageB);
										tempPlayer[I-m][J-1] = 'B'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(west.isSelected() == true)
						{
							if(J-3 >= 1)
							{
								for(int q = 0; q<4; q++)
								{
									tempS = buttonArray[I][J-q].getText(); 
									if (buttonArray[I][J-q].getIcon().toString().equals("Resources/Q.png") == false)
										validB = false; 	
								}
								if (validB == true)
								{
									for (int m = 0; m<4; m++)
									{
										battleshipPlaced = true; 
										Icon imageB = new ImageIcon("Resources/B.png");
										buttonArray[I][J-m].setText(""); 
										buttonArray[I][J-m].setIcon(imageB);
										tempPlayer[I][J-m-1] = 'B'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(south.isSelected() == true)
						{
							if(I+3 <= 9)
							{
								for(int q = 0; q<4; q++)
								{
									tempS = buttonArray[I+q][J].getText(); 
									if (buttonArray[I+q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validB = false; 	
								}
								if (validB == true)
								{
									for (int m = 0; m<4; m++)
									{
										battleshipPlaced = true; 
										Icon imageB = new ImageIcon("Resources/B.png");
										buttonArray[I+m][J].setText(""); 
										buttonArray[I+m][J].setIcon(imageB);
										tempPlayer[I+m][J-1] = 'B'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}

					}
					if (DA.getC() == true)
					{
						if(east.isSelected() == true)
						{
							if(J+2 <= 10)
							{
								for(int q = 0; q<3; q++)
								{
									tempS = buttonArray[I][J+q].getText(); 
									if (buttonArray[I][J+q].getIcon().toString().equals("Resources/Q.png") == false)
										validC = false; 	
								}
								if (validC == true)
								{
									for (int m = 0; m<3; m++)
									{
										cruiserPlaced = true; 
										Icon imageC = new ImageIcon("Resources/C.png");
										buttonArray[I][J+m].setText(""); 
										buttonArray[I][J+m].setIcon(imageC);
										tempPlayer[I][J+m-1] = 'C'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(north.isSelected() == true)
						{
							if(I-2 >= 0)
							{
								for(int q = 0; q<3; q++)
								{
									tempS = buttonArray[I-q][J].getText(); 
									if (buttonArray[I-q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validC = false; 	
								}
								if (validC == true)
								{
									for (int m = 0; m<3; m++)
									{
										cruiserPlaced = true; 
										Icon imageC = new ImageIcon("Resources/C.png");
										buttonArray[I-m][J].setText(""); 
										buttonArray[I-m][J].setIcon(imageC);
										tempPlayer[I-m][J-1] = 'C'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(west.isSelected() == true)
						{
							if(J-2 >= 1)
							{
								for(int q = 0; q<3; q++)
								{
									tempS = buttonArray[I][J-q].getText(); 
									if (buttonArray[I][J-q].getIcon().toString().equals("Resources/Q.png") == false)
										validC = false; 	
								}
								if (validC == true)
								{
									for (int m = 0; m<3; m++)
									{
										cruiserPlaced = true; 
										Icon imageC = new ImageIcon("Resources/C.png");
										buttonArray[I][J-m].setText(""); 
										buttonArray[I][J-m].setIcon(imageC);
										tempPlayer[I][J-m-1] = 'C'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(south.isSelected() == true)
						{
							if(I+2 <= 9)
							{
								for(int q = 0; q<3; q++)
								{
									tempS = buttonArray[I+q][J].getText(); 
									if (buttonArray[I+q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validC = false; 	
								}
								if (validC == true)
								{
									for (int m = 0; m<3; m++)
									{
										cruiserPlaced = true; 
										Icon imageC = new ImageIcon("Resources/C.png");
										buttonArray[I+m][J].setText(""); 
										buttonArray[I+m][J].setIcon(imageC);
										tempPlayer[I+m][J-1] = 'C'; 
									}
								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
					}
					if (DA.getD() == true)
					{

						if(east.isSelected() == true)
						{
							if(J+1 <= 10)
							{
								for(int q = 0; q<2; q++)
								{
									tempS = buttonArray[I][J+q].getText(); 
									if (buttonArray[I][J+q].getIcon().toString().equals("Resources/Q.png") == false)
										validD = false; 	
								}
								if (validD == true)
								{
									for (int m = 0; m<2; m++)
									{
										Icon imageD = new ImageIcon("Resources/D.png");
										buttonArray[I][J+m].setText(""); 
										buttonArray[I][J+m].setIcon(imageD);
										tempPlayer[I][J+m-1] = 'D'; 
									}
									if (destroyer1.isPlaced == false)
									{
										destroyer1.setCoordinates(I, J, I, J+1); 
										destroyer1.isPlaced = true; 
									}
									else if (destroyer1.isPlaced && !destroyer2.isPlaced)
									{
										destroyer2.setCoordinates(I, J, I, J+1); 
										destroyer2.isPlaced = true; 
									}

								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(north.isSelected() == true)
						{
							if(I-1 >= 0)
							{
								for(int q = 0; q<2; q++)
								{
									tempS = buttonArray[I-q][J].getText(); 
									if (buttonArray[I-q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validD = false; 	
								}
								if (validD == true)
								{
									for (int m = 0; m<2; m++)
									{
										Icon imageD = new ImageIcon("Resources/D.png");
										buttonArray[I-m][J].setText(""); 
										buttonArray[I-m][J].setIcon(imageD);
										tempPlayer[I-m][J-1] = 'D'; 
									}
									if (destroyer1.isPlaced == false)
									{
										destroyer1.setCoordinates(I, J, I-1, J); 
										destroyer1.isPlaced = true; 
									}
									else if (destroyer1.isPlaced == true && destroyer2.isPlaced == false)
									{
										destroyer2.setCoordinates(I, J, I-1, J); 
										destroyer2.isPlaced = true; 
									}

								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(west.isSelected() == true)
						{
							if(J-1 >= 1)
							{
								for(int q = 0; q<2; q++)
								{
									tempS = buttonArray[I][J-q].getText(); 
									if (buttonArray[I][J-q].getIcon().toString().equals("Resources/Q.png") == false)
										validD = false; 	
								}
								if (validD == true)
								{
									for (int m = 0; m<2; m++)
									{
										Icon imageD = new ImageIcon("Resources/D.png");
										buttonArray[I][J-m].setText(""); 
										buttonArray[I][J-m].setIcon(imageD);
										tempPlayer[I][J-m-1] = 'D'; 
									}
									//Adding this
									if (destroyer1.isPlaced == false)
									{
										destroyer1.setCoordinates(I, J, I, J-1); 
										destroyer1.isPlaced = true; 
									}
									else if (destroyer1.isPlaced == true && destroyer2.isPlaced == false)
									{
										destroyer2.setCoordinates(I, J, I, J-1); 
										destroyer2.isPlaced = true; 
									}

								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 

						}
						else if(south.isSelected() == true)
						{
							if(I+1 <= 9)
							{
								for(int q = 0; q<2; q++)
								{
									tempS = buttonArray[I+q][J].getText(); 
									if (buttonArray[I+q][J].getIcon().toString().equals("Resources/Q.png") == false)
										validD = false; 	
								}
								if (validD == true)
								{
									for (int m = 0; m<2; m++)
									{
										Icon imageD = new ImageIcon("Resources/D.png");
										buttonArray[I+m][J].setText(""); 
										buttonArray[I+m][J].setIcon(imageD);
										tempPlayer[I+m][J-1] = 'D'; 
									}
									//Adding this
									if (destroyer1.isPlaced == false)
									{
										destroyer1.setCoordinates(I, J, I+1, J); 
										destroyer1.isPlaced = true; 
									}
									else if (destroyer1.isPlaced == true && destroyer2.isPlaced == false)
									{
										destroyer2.setCoordinates(I, J, I+1, J); 
										destroyer2.isPlaced = true; 
									}

								}
								else 
									JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else
								JOptionPane.showMessageDialog(null, "Invalid ship placement", "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}
				}
			}

			if (destroyer1.isPlaced == true && destroyer2.isPlaced == true &&aircraftcarrierPlaced == true 
					&& cruiserPlaced == true && battleshipPlaced == true )
			{
				if (editClient == null && editServer != null)
				{
					String AircraftCoord = "Aircraft:"; 
					String BattleshipCoord = "Battleship:"; 
					String CarrierCoord = "Cruiser:"; 
					for (int i = 0; i < 10; i++)
					{
						for (int j = 0; j < 10; j++)
						{
							if (tempPlayer[i][j] == 'A')
								AircraftCoord = AircraftCoord + i + j; 
							else if (tempPlayer[i][j] == 'B')
								BattleshipCoord = BattleshipCoord + i + j; 
							else if (tempPlayer[i][j] == 'C')
								CarrierCoord = CarrierCoord + i + j; 
						}
					}
					String destroyer1Coord = "Destroyer1:"+(destroyer1.startRow)+(destroyer1.startCol-1)+(destroyer1.endRow)+(destroyer1.endCol-1);
					String destroyer2Coord = "Destroyer2:"+(destroyer2.startRow)+(destroyer2.startCol-1)+(destroyer2.endRow)+(destroyer2.endCol-1); 

					editServer.sendMessage(AircraftCoord); 
					editServer.sendMessage(BattleshipCoord); 
					editServer.sendMessage(CarrierCoord); 
					editServer.sendMessage(destroyer1Coord); 
					editServer.sendMessage(destroyer2Coord);
					editServer.sendMessage("Ready"); 
					startButton.setEnabled(true); 
				}
				if (editServer == null && editClient != null)
				{
					String AircraftCoord = "Aircraft:"; 
					String BattleshipCoord = "Battleship:"; 
					String CarrierCoord = "Cruiser:"; 
					for (int i = 0; i < 10; i++)
					{
						for (int j = 0; j < 10; j++)
						{
							if (tempPlayer[i][j] == 'A')
								AircraftCoord = AircraftCoord + i + j; 
							else if (tempPlayer[i][j] == 'B')
								BattleshipCoord = BattleshipCoord + i + j; 
							else if (tempPlayer[i][j] == 'C')
								CarrierCoord = CarrierCoord + i + j; 
						}
					}
					String destroyer1Coord = "Destroyer1:"+(destroyer1.startRow)+(destroyer1.startCol-1)+(destroyer1.endRow)+(destroyer1.endCol-1);
					String destroyer2Coord = "Destroyer2:"+(destroyer2.startRow)+(destroyer2.startCol-1)+(destroyer2.endRow)+(destroyer2.endCol-1); 

					editClient.sendMessage(AircraftCoord); 
					editClient.sendMessage(BattleshipCoord); 
					editClient.sendMessage(CarrierCoord); 
					editClient.sendMessage(destroyer1Coord); 
					editClient.sendMessage(destroyer2Coord);
					editClient.sendMessage("Ready"); 

					startButton.setEnabled(true); 
				}
				if (playingComp == true)
					startButton.setEnabled(true); 
			}
		}

		class directionAdapter implements ActionListener
		{
			boolean addA = false; public boolean addB = false, addC = false, addD = false; 
			String comboResponse;
			OptionAdapter myOA; 
			public directionAdapter (OptionAdapter OA)
			{
				myOA = OA; 
			}
			public void actionPerformed(ActionEvent ie2)
			{
				JComboBox<String> shipOptions_inner = myOA.shipOptions; 
				comboResponse = shipOptions_inner.getSelectedItem().toString(); 
				if (comboResponse == "Cruiser")
					addC = true; 
				if (comboResponse == "Destroyer")
					addD = true; 
				if (comboResponse == "Aircraft Carrier")
					addA = true; 
				if (comboResponse == "Battleship")
					addB = true; 
			}
			public boolean getA()
			{
				return addA; 
			}
			public boolean getB()
			{
				return addB; 
			}
			public boolean getC()
			{
				return addC; 
			}
			public boolean getD()
			{
				return addD; 
			}
		}
	}
}