package currenme_CSCI201_Assignment5;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.text.DefaultCaret;

import currenme_CSCI201_Assignment5.EditGameGUI.OptionAdapter;

public class PlayGameGUI extends JFrame
{
	JPanel mainPanel; 
	JPanel jp; 
	JPanel jp2; 
	Color myClear = new Color(0, 0, 0, 0); 
	char[][] tempcomp = new char[10][10]; 
	char[][] tempPlayer = new char[10][10]; 
	ImprovedJButton[][] buttonArray; 
	ImprovedJButton[][] buttonArrayComp; 
	JPanel boardHolder; 
	JLabel scores; 
	int Acount=0, Bcount=0, Ccount=0, Dcount=0; 
	boolean Acount_printed = false; 
	boolean Bcount_printed = false; 
	boolean Ccount_printed = false; 
	boolean D1_printed = false; 
	boolean D2_printed = false; 
	int PAcount=0, PBcount=0, PCcount=0, PDcount=0; 
	boolean PAcount_printed = false; 
	boolean PBcount_printed = false; 
	boolean PCcount_printed = false; 
	boolean PD1_printed = false; 
	boolean PD2_printed = false; 
	public int compGuesses[][] = new int[10][10]; 
	JMenuBar topBar; 
	JMenu info; 
	JMenuItem about;
	JMenuItem howTo; 
	JLabel timeLabel; 
	int timeRemaining = 15; 
	Timer timer;
	boolean playerTookTurn = false;  
	String temp5, temp6; 
	boolean compCanGo; 
	int compTime = -1; 
	int Round = 0; 
	String toRet; 
	String toRet2; 
	String toRetComp; 
	String toRetComp2; 
	JTextArea myTextArea; 
	JScrollPane scroll; 
	boolean printPlayerScore = false; 
	String warning; 
	String S_round; 
	JPanel logHolder; 
	String shipStatus; 
	Destroyer destroyer1; 
	Destroyer destroyer2; 
	Destroyer CompDestroyer1; 
	Destroyer CompDestroyer2; 
	PlaySplashAnimation splash; 
	String Name1; 
	String Name2; 
	boolean singlePlayer; 
	Server editServer = null; 
	Client editClient = null; 
	public char opponentGuessRow; 
	public char opponentGuessCol; 

	public PlayGameGUI(char[][] tempP, char[][] tempC, Destroyer d1, Destroyer d2, Destroyer Cd1, Destroyer Cd2, String n1, String n2, Thread ServerClient)
	{
		super("Battleship");
		if (ServerClient != null && ServerClient.getClass().getName().contains("Server"))
			editServer = (Server) ServerClient; 
		else if (ServerClient != null && ServerClient.getClass().getName().contains("Client"))
			editClient = (Client) ServerClient; 
		else if (ServerClient == null)
			singlePlayer = true; 
		Name1 = n1; 
		Name2 = n2; 
		destroyer1 = d1; 
		destroyer2 = d2; 
		CompDestroyer1 = Cd1;  
		CompDestroyer2 = Cd2; 
		logHolder = new JPanel(new FlowLayout()); 
		myTextArea = new JTextArea(5,50); 
		((DefaultCaret)myTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		scroll = new JScrollPane(myTextArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		logHolder.add(scroll); 
		timeLabel = new JLabel("Time", SwingConstants.CENTER); 
		TimeLabelFunction(); 
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
				JOptionPane.showMessageDialog(null, "Assignment #3. Developed by Curren Mehta", "Note", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		howTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ie)
			{

				JTextArea mainText = new JTextArea(); 
				mainText.setLineWrap(true);
				mainText.setWrapStyleWord(true); 
				mainText.append("To begin click on the position where you want to place your ships.");
				mainText.append("Place all five ships"); 
				mainText.append("Choose a valid .battle file to read in, and start the game"); 
				mainText.append("Attempt to guess all the positions of the computer's battleships"); 
				mainText.append("Try to do this before the computer guesses where all of your ships are"); 
				JScrollPane myVertPane = new JScrollPane(mainText); 
				myVertPane.setPreferredSize( new Dimension(200, 200)); 
				JOptionPane.showMessageDialog(null, myVertPane, "How To Play", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
		info.add(howTo); 
		info.add(about); 
		topBar.add(info);

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				compGuesses[i][j] = 0; 
			}
		}

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				tempcomp[i][j] = tempC[i][j]; 
			}
		}

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				tempPlayer[i][j] = tempP[i][j]; 
			}
		}

		JPanel topHolder = new JPanel(new BorderLayout()); 
		topHolder.add(topBar, BorderLayout.NORTH); 
		topHolder.add(timeLabel, BorderLayout.SOUTH); 
		mainPanel = new JPanel (new BorderLayout()); 

		jp = getPGamePanel(); 
		boardHolder = new JPanel(new FlowLayout()); 
		boardHolder.add(jp); 
		jp2 = getCGamePanel(); 
		boardHolder.add(jp2); 
		boardHolder.setBackground(Color.yellow); 
		mainPanel.add(boardHolder, BorderLayout.CENTER); 
		mainPanel.add(logHolder, BorderLayout.SOUTH); 
		mainPanel.add(topHolder, BorderLayout.NORTH); 

		add(mainPanel); 

		setSize(1240, 500);
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void TimeLabelFunction()
	{
		int wait = 1000; 
		int period = 1000; 
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			public void run() 
			{
				timeLabel.setText("Time - " + timeRemaining);
				decrementTime(); 
			}
		}, wait, period);
	}

	public void decrementTime()
	{
		if (singlePlayer == true)
		{
			if (timeRemaining == 15)
			{
				Round++; 
				S_round = "Round " + Round + '\n'; 
				myTextArea.append(S_round);
				compCanGo = true;   
				Random guess = new Random(); 
				compTime =  guess.nextInt(14); 
				if (compTime == 0)
					compTime = 1; 
				printPlayerScore = true; 

			}
			if (timeRemaining == compTime && compCanGo == true)
			{
				String toRetComp = makeComputerGuess(); 
				compCanGo = false; 
				mainPanel.revalidate(); 
				mainPanel.repaint();
				if (compTime >= 10)
					toRetComp2 = toRetComp + " (0:" + compTime + ")" + '\n'; 
				else if (compTime < 10)
					toRetComp2 = toRetComp + " (0:0" + compTime + ")" + '\n'; 
				myTextArea.append(toRetComp2); 
				if (PAcount == 5 && PAcount_printed == false )
				{
					shipStatus = Name2 + " sunk " + Name1 +"'s Aircraft Carrier" + '\n'; 
					myTextArea.append(shipStatus); 
					PAcount_printed = true; 
				}
				if (PBcount == 4 && PBcount_printed == false)
				{
					shipStatus = Name2 +" sunk " + Name1 + "'s Battleship" + '\n'; 
					myTextArea.append(shipStatus); 
					PBcount_printed = true; 
				}
				if (PCcount == 4 && PCcount_printed == false)
				{
					shipStatus = Name2 + " sunk " + Name1 +"'s Cruiser" + '\n'; 
					myTextArea.append(shipStatus); 
					PCcount_printed = true; 
				}
				if (destroyer1.isSunk == true && PD1_printed == false)
				{
					shipStatus = Name2 + " sunk " + Name1 + "'s Destoyer" + '\n'; 
					myTextArea.append(shipStatus); 
					PD1_printed = true; 
				}
				if (destroyer2.isSunk == true && PD2_printed == false)
				{
					shipStatus = Name2 + " sunk " + Name1 + "'s Destoyer" + '\n'; 
					myTextArea.append(shipStatus); 
					PD2_printed = true; 
				}

			}

			if (timeRemaining == 3)
			{
				warning = "Warning - 3 seconds left in the round!" + '\n'; 
				myTextArea.append(warning); 
			}

			if (playerTookTurn == true && printPlayerScore == true)
			{
				if (timeRemaining >= 10)
					toRet2 = toRet + " (0:" + timeRemaining + ")" + '\n'; 
				else if (timeRemaining < 10)
					toRet2 = toRet + " (0:0" + timeRemaining + ")" + '\n'; 
				myTextArea.append(toRet2); 
				if (Acount == 5 && Acount_printed == false )
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Aircraft Carrier" + '\n'; 
					myTextArea.append(shipStatus); 
					Acount_printed = true; 
				}
				if (Bcount == 4 && Bcount_printed == false)
				{
					shipStatus = Name1 +" sunk " + Name2 + "'s Battleship" + '\n'; 
					myTextArea.append(shipStatus); 
					Bcount_printed = true; 
				}
				if (Ccount == 4 && Ccount_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Cruiser" + '\n'; 
					myTextArea.append(shipStatus); 
					Ccount_printed = true; 
				}
				if (CompDestroyer1.isSunk == true && D1_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Destroyer" + '\n'; 
					myTextArea.append(shipStatus); 
					D1_printed = true; 
				}
				if (CompDestroyer2.isSunk == true && D2_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Destroyer" + '\n'; 
					myTextArea.append(shipStatus); 
					D2_printed = true; 
				}

				printPlayerScore = false; 
			}

			if (playerTookTurn == true && compCanGo == false)
			{
				timeRemaining = 15; 
				repaint(); 
				playerTookTurn = false; 
			}
			else
			{
				timeRemaining = timeRemaining - 1; 
				repaint(); 

				if (timeRemaining == 0)
				{
					timeRemaining = 15; 
					playerTookTurn = false; 
				}
			}	  
		}
		else if (editServer != null || editClient != null)
		{
			if (timeRemaining == 3)
			{
				warning = "Warning - 3 seconds left in the round!" + '\n'; 
				myTextArea.append(warning); 
			}

			if (playerTookTurn == true && printPlayerScore == true)
			{
				if (timeRemaining >= 10)
					toRet2 = toRet + " (0:" + timeRemaining + ")" + '\n'; 
				else if (timeRemaining < 10)
					toRet2 = toRet + " (0:0" + timeRemaining + ")" + '\n'; 
				myTextArea.append(toRet2); 
				if (Acount == 5 && Acount_printed == false )
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Aircraft Carrier" + '\n'; 
					myTextArea.append(shipStatus); 
					Acount_printed = true; 
				}
				if (Bcount == 4 && Bcount_printed == false)
				{
					shipStatus = Name1 +" sunk " + Name2 + "'s Battleship" + '\n'; 
					myTextArea.append(shipStatus); 
					Bcount_printed = true; 
				}
				if (Ccount == 4 && Ccount_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Cruiser" + '\n'; 
					myTextArea.append(shipStatus); 
					Ccount_printed = true; 
				}
				if (CompDestroyer1.isSunk == true && D1_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Destroyer" + '\n'; 
					myTextArea.append(shipStatus); 
					D1_printed = true; 
				}
				if (CompDestroyer2.isSunk == true && D2_printed == false)
				{
					shipStatus = Name1 + " sunk " + Name2 + "'s Destroyer" + '\n'; 
					myTextArea.append(shipStatus); 
					D2_printed = true; 
				}

				printPlayerScore = false; 
			}
		}
	}

	public JPanel getPGamePanel()
	{
		JPanel jp_ret = new JPanel(new BorderLayout()); 
		jp = new JPanel();
		JLabel label = new JLabel(Name1, SwingConstants.CENTER); 
		jp_ret.add(label, BorderLayout.NORTH); 
		jp.setBorder(BorderFactory.createLineBorder(Color.black));
		jp.setLayout(new GridLayout(11,11));
		buttonArray = new ImprovedJButton[11][11]; 
		String temp; 
		char tempC; 
		for (int i = 0; i<11; i++)
		{
			for (int j = 0; j<11; j++)
			{				
				buttonArray[i][j] = new ImprovedJButton(); 
				buttonArray[i][j].setBackground(new Color(0,0,0,0));
				buttonArray[i][j].setOpaque(false); 
				buttonArray[i][j].setEnabled(false);
				buttonArray[i][j].setPreferredSize(new Dimension(55, 30));
				buttonArray[i][j].setFont(buttonArray[i][j].getFont().deriveFont(7));
				jp.add(buttonArray[i][j]);
			}
		}

		for(int i = 0; i < 11; i++)
		{
			temp = Integer.toString(i); 
			buttonArray[10][i].setText(temp); 
		}
		for (int k = 0; k < 10; k++)
		{
			for (int j = 1; j<11; j++)
			{
				tempC = tempPlayer[k][j-1]; 
				temp = Character.toString(tempC); 
				if (temp.equals("X"))
				{
					Icon imageQ = new ImageIcon("Resources/Q.png");
					buttonArray[k][j].setText(""); 
					buttonArray[k][j].setIcon(imageQ);
				}
				else if (temp.equals("A"))
				{
					Icon imageA = new ImageIcon("Resources/A.png");
					buttonArray[k][j].setText(""); 
					buttonArray[k][j].setIcon(imageA);
				}
				else if (temp.equals("B"))
				{
					Icon imageB = new ImageIcon("Resources/B.png");
					buttonArray[k][j].setText(""); 
					buttonArray[k][j].setIcon(imageB);
				}
				else if (temp.equals("C"))
				{
					Icon imageC = new ImageIcon("Resources/C.png");
					buttonArray[k][j].setText(""); 
					buttonArray[k][j].setIcon(imageC);
				}
				else if (temp.equals("D"))
				{
					Icon imageD = new ImageIcon("Resources/D.png");
					buttonArray[k][j].setText(""); 
					buttonArray[k][j].setIcon(imageD);
				}
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

		jp_ret.add(jp, BorderLayout.CENTER); 
		return jp_ret; 
	}

	public JPanel getCGamePanel()
	{
		JPanel jp_ret = new JPanel(new BorderLayout()); 
		jp = new JPanel();
		JLabel label = new JLabel(Name2, SwingConstants.CENTER); 
		jp_ret.add(label, BorderLayout.NORTH); 
		jp.setBorder(BorderFactory.createLineBorder(Color.black));
		jp.setLayout(new GridLayout(11,11));
		buttonArrayComp = new ImprovedJButton[11][11]; 
		String temp; 
		char tempC; 
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

		for(int i = 0; i < 11; i++)
		{
			temp = Integer.toString(i); 
			buttonArrayComp[10][i].setText(temp); 
		}
		for (int k = 0; k < 10; k++)
		{
			for (int j = 1; j<11; j++)
			{
				buttonArrayComp[k][j].setEnabled(true);
				PlayGameAdapter PGA = new PlayGameAdapter(k, j); 
				buttonArrayComp[k][j].addActionListener(PGA);
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

		jp_ret.add(jp, BorderLayout.CENTER); 
		return jp_ret; 
	}

	public class PlayGameAdapter implements ActionListener
	{
		int M, N; 
		public PlayGameAdapter (int row, int col)
		{
			M = row; 
			N = col; 
		}

		public void actionPerformed(ActionEvent ie)
		{
			PlayerGuess(M, N); 
		}
	} 

	public String PlayerGuess(int R, int C)
	{
		int M = R; 
		int N = C; 
		char recieved; 
		String letters =  "ABCDEFGHIJ"; 
		char[] letterArray = letters.toCharArray();
		char temp = letterArray[M]; 
		temp5 = Character.toString(temp); 
		temp6 = Integer.toString(N);
		recieved = tempcomp[M][N-1]; 
		if (editServer != null)
		{
			editServer.sendMessage("Guess:" + M + ":" + (N-1)); 
		}
		else if (editClient != null)
		{
			editClient.sendMessage("Guess: " + M + ":" + (N-1)); 
		}
		if (playerTookTurn == false)
		{
			SoundLibrary.playSound("Resources/cannon.wav");
			if (recieved == 'X')
			{
				buttonArrayComp[M][N].setIcon(null); 
				buttonArrayComp[M][N].setText("");
				splash = new PlaySplashAnimation(M, N, buttonArrayComp, 'X'); 
				splash.start(); 
				mainPanel.revalidate(); 
				mainPanel.repaint(); 
			}
			else if (recieved == 'A')
			{
				buttonArrayComp[M][N].setText(""); 
				PlayHitAnimation hitA = new PlayHitAnimation(M, N, 'A', buttonArrayComp); 
				hitA.start(); 
				mainPanel.revalidate(); 
				mainPanel.repaint(); 
				int[][] A_loc; 
				Acount++;
				if (Acount == 5)
				{
					int counter = 0; 
					A_loc = new int[5][2]; 
					for (int i = 0; i <10; i++)
					{
						for (int j = 1; j < 11; j++)
						{
							if (tempcomp[i][j-1] == 'A')
							{
								A_loc[counter][0] = i; 
								A_loc[counter][1] = j;
								counter++; 
							}
						}
					} 
					AircraftSunk AS = new AircraftSunk(hitA, A_loc, buttonArrayComp); 
					AS.start(); 
					if (Acount==5 && Bcount==4 && Ccount==3 &&Dcount==4)
					{
						FinishingMessage finish = new FinishingMessage(AS, 1, this); 
						finish.start(); 
					}
				}
			}
			else if (recieved == 'B')
			{
				buttonArrayComp[M][N].setText(""); 
				PlayHitAnimation hitB = new PlayHitAnimation(M, N, 'B', buttonArrayComp); 
				hitB.start(); 
				mainPanel.revalidate(); 
				mainPanel.repaint(); 
				Bcount++; 
				int[][] B_loc; 
				if (Bcount == 4)
				{
					int counter = 0; 
					B_loc = new int[4][2]; 
					for (int i = 0; i <10; i++)
					{
						for (int j = 1; j < 11; j++)
						{
							if (tempcomp[i][j-1] == 'B')
							{
								B_loc[counter][0] = i; 
								B_loc[counter][1] = j;
								counter++; 
							}
						}
					} 
					BattleshipSunk BS = new BattleshipSunk(hitB, B_loc, buttonArrayComp); 
					BS.start(); 
					if (Acount==5 && Bcount==4 && Ccount==3 &&Dcount==4)
					{
						FinishingMessage finish = new FinishingMessage(BS, 1, this); 
						finish.start(); 
					}
				}
			}
			else if (recieved == 'C')
			{
				buttonArrayComp[M][N].setText(""); 
				PlayHitAnimation hitC = new PlayHitAnimation(M, N, 'C', buttonArrayComp); 
				hitC.start(); 
				mainPanel.revalidate(); 
				mainPanel.repaint(); 
				Ccount++; 
				int[][] C_loc; 
				if (Ccount == 3)
				{
					int counter = 0; 
					C_loc = new int[3][2]; 
					for (int i = 0; i <10; i++)
					{
						for (int j = 1; j < 11; j++)
						{
							if (tempcomp[i][j-1] == 'C')
							{
								C_loc[counter][0] = i; 
								C_loc[counter][1] = j;
								counter++; 
							}
						}
					} 
					CruiserSunk CS = new CruiserSunk(hitC, C_loc, buttonArrayComp); 
					CS.start(); 
					if (Acount==5 && Bcount==4 && Ccount==3 &&Dcount==4)
					{
						FinishingMessage finish = new FinishingMessage(CS, 1, this); 
						finish.start(); 
					}
				}
			}
			else if (recieved == 'D')
			{
				buttonArrayComp[M][N].setText(""); 
				PlayHitAnimation hitD = new PlayHitAnimation(M, N, 'D', buttonArrayComp); 
				hitD.start(); 
				mainPanel.revalidate(); 
				mainPanel.repaint(); 
				Dcount++; 
				if ((CompDestroyer1.startRow == M && (CompDestroyer1.startCol) == N) || (CompDestroyer1.endRow == M && (CompDestroyer1.endCol) == N))
				{
					CompDestroyer1.hitCount++; 
					if (CompDestroyer1.hitCount == 2)
					{
						CompDestroyer1.isSunk = true; 
						int[][] CompDestroyer1loc = new int[2][2]; 
						CompDestroyer1loc[0][0] = CompDestroyer1.startRow; 
						CompDestroyer1loc[0][1] = CompDestroyer1.startCol; 
						CompDestroyer1loc[1][0] = CompDestroyer1.endRow; 
						CompDestroyer1loc[1][1] = CompDestroyer1.endCol; 
						DestroyerSunk CDS = new DestroyerSunk(hitD, CompDestroyer1loc, buttonArrayComp); 
						CDS.start(); 
						if (Acount==5 && Bcount==4 && Ccount==3 &&Dcount==4)
						{
							FinishingMessage finish = new FinishingMessage(CDS, 1, this); 
							finish.start(); 

						}
					}
				}
				if ((CompDestroyer2.startRow == M && (CompDestroyer2.startCol) == N) || (CompDestroyer2.endRow == M && (CompDestroyer2.endCol) == N))
				{
					CompDestroyer2.hitCount++; 
					if (CompDestroyer2.hitCount == 2)
					{
						CompDestroyer2.isSunk = true; 
						int[][] CompDestroyer2loc = new int[2][2]; 
						CompDestroyer2loc[0][0] = CompDestroyer2.startRow; 
						CompDestroyer2loc[0][1] = CompDestroyer2.startCol; 
						CompDestroyer2loc[1][0] = CompDestroyer2.endRow; 
						CompDestroyer2loc[1][1] = CompDestroyer2.endCol; 
						DestroyerSunk CDS2 = new DestroyerSunk(hitD, CompDestroyer2loc, buttonArrayComp); 
						CDS2.start(); 
						if (Acount==5 && Bcount==4 && Ccount==3 &&Dcount==4)
						{
							//JOptionPane.showMessageDialog(null, "You won the game", "Result", JOptionPane.INFORMATION_MESSAGE);
							FinishingMessage finish = new FinishingMessage(CDS2, 1, this); 
							finish.start(); 
							//EditGameGUI gameGUI = new EditGameGUI(); 
						}
					}
				}
			}

			playerTookTurn = true; 
		}
		//toRet = "Player hit " + temp + Integer.toString(N); 
		toRet = Name1 + " hit " + temp + Integer.toString(N); 
		if (recieved == 'A')
			toRet = toRet + " and hit an Aircraft Carrier "; 
		else if (recieved == 'B')
			toRet = toRet + " and hit a Battleship "; 
		else if (recieved == 'C')
			toRet = toRet + " and hit a Cruiser "; 
		else if (recieved == 'D')
			toRet = toRet + " and hit a Destroyer "; 
		else
			toRet = toRet + " and hit nothing "; 
		return toRet; 
	}

	public String makeComputerGuess() 
	{
		SoundLibrary.playSound("Resources/cannon.wav");
		String letters2 =  "ABCDEFGHIJ"; 
		char[] letterArray2 = letters2.toCharArray(); 
		boolean uniqueGuess = false; 
		Random randRow = new Random(); 
		Random randCol = new Random(); 
		int compRow, compCol; 
		char temp2 = 'A', temp3 = 'A'; 
		String temp7="hi", temp8="hi"; 
		while (uniqueGuess == false)
		{
			compRow = randRow.nextInt(10); 
			compCol = randCol.nextInt(10); 
			if (compGuesses[compRow][compCol] == 0)
			{
				compGuesses[compRow][compCol] = 1; 
				temp3 = letterArray2[compRow]; 
				temp7 = Character.toString(temp3); 
				temp8 = Integer.toString(compCol+1); 

				temp2 = tempPlayer[compRow][compCol]; 
				if (temp2 == 'X')
				{
					PlaySplashAnimation cSplash = new PlaySplashAnimation(compRow, compCol+1, buttonArray, 'X'); 
					cSplash.start(); 
					mainPanel.revalidate(); 
					mainPanel.repaint(); 
				}
				else if (temp2 == 'A')
				{
					PlayHitAnimation hitP = new PlayHitAnimation(compRow, compCol+1, 'X', buttonArray); 
					hitP.start(); 
					PAcount++; 
					mainPanel.revalidate(); 
					mainPanel.repaint(); 
					int[][] PA_loc; 
					if (PAcount == 5)
					{
						int counter = 0; 
						PA_loc = new int[5][2]; 
						for (int i = 0; i <10; i++)
						{
							for (int j = 1; j < 11; j++)
							{
								if (tempPlayer[i][j-1] == 'A')
								{
									PA_loc[counter][0] = i; 
									PA_loc[counter][1] = j;
									counter++; 
								}
							}
						} 
						CompAircraftSunk CAS = new CompAircraftSunk(hitP, PA_loc, buttonArray); 
						CAS.start(); 
						if (PAcount==5 && PBcount==4 && PCcount==3 && PDcount==4)
						{
							FinishingMessage finish2 = new FinishingMessage(CAS, 0, this);
							finish2.start(); 
						}
					}
				}
				else if (temp2 == 'B')
				{
					PlayHitAnimation hitP = new PlayHitAnimation(compRow, compCol+1, 'X', buttonArray); 
					hitP.start(); 
					PBcount++; 
					mainPanel.revalidate(); 
					mainPanel.repaint(); 
					int[][] PB_loc; 
					if (PBcount == 4)
					{
						int counter = 0; 
						PB_loc = new int[4][2]; 
						for (int i = 0; i <10; i++)
						{
							for (int j = 1; j < 11; j++)
							{
								if (tempPlayer[i][j-1] == 'B')
								{
									PB_loc[counter][0] = i; 
									PB_loc[counter][1] = j;
									counter++; 
								}
							}
						} 
						CompBattleshipSunk CBS = new CompBattleshipSunk(hitP, PB_loc, buttonArray); 
						CBS.start(); 
						if (PAcount==5 && PBcount==4 && PCcount==3 && PDcount==4)
						{
							FinishingMessage finish2 = new FinishingMessage(CBS, 0, this); 
							finish2.start(); 
						}
					}
				}
				else if (temp2 == 'C')
				{
					PlayHitAnimation hitP = new PlayHitAnimation(compRow, compCol+1, 'X', buttonArray); 
					hitP.start(); 
					PCcount++; 
					mainPanel.revalidate();
					mainPanel.repaint(); 
					int[][] PC_loc; 
					if (PCcount == 3)
					{
						int counter = 0; 
						PC_loc = new int[3][2]; 
						for (int i = 0; i <10; i++)
						{
							for (int j = 1; j < 11; j++)
							{
								if (tempPlayer[i][j-1] == 'C')
								{
									PC_loc[counter][0] = i; 
									PC_loc[counter][1] = j;
									counter++; 
								}
							}
						} 
						CompCruiserSunk CCS = new CompCruiserSunk(hitP, PC_loc, buttonArray); 
						CCS.start(); 
						if (PAcount==5 && PBcount==4 && PCcount==3 && PDcount==4)
						{
							FinishingMessage finish2 = new FinishingMessage(CCS, 0, this); 
							finish2.start(); 
						}
					}
				}
				else if (temp2 == 'D')
				{
					PlayHitAnimation hitP = new PlayHitAnimation(compRow, compCol+1, 'X', buttonArray); 
					hitP.start(); 
					PDcount++; 
					mainPanel.revalidate(); 
					mainPanel.repaint(); 
					if ((destroyer1.startRow == compRow && (destroyer1.startCol) == (compCol+1)) || (destroyer1.endRow == compRow && (destroyer1.endCol) == (compCol+1)))
					{
						destroyer1.hitCount++; 
						if (destroyer1.hitCount == 2)
						{
							destroyer1.isSunk = true; 
							int[][] destroyer1loc = new int[2][2]; 
							destroyer1loc[0][0] = destroyer1.startRow; 
							destroyer1loc[0][1] = destroyer1.startCol; 
							destroyer1loc[1][0] = destroyer1.endRow; 
							destroyer1loc[1][1] = destroyer1.endCol; 
							DestroyerSunk DS = new DestroyerSunk(hitP, destroyer1loc, buttonArray); 
							DS.start(); 
							if (PAcount==5 && PBcount==4 && PCcount==3 && PDcount==4)
							{
								FinishingMessage finish2 = new FinishingMessage(DS, 0, this); 
								finish2.start(); 
							}
						}
					}
					if ((destroyer2.startRow == compRow && (destroyer2.startCol) == (compCol+1)) || (destroyer2.endRow == compRow && (destroyer2.endCol) == (compCol+1)))
					{
						destroyer2.hitCount++; 
						if (destroyer2.hitCount == 2)
						{
							destroyer2.isSunk = true; 
							int[][] destroyer2loc = new int[2][2]; 
							destroyer2loc[0][0] = destroyer2.startRow; 
							destroyer2loc[0][1] = destroyer2.startCol; 
							destroyer2loc[1][0] = destroyer2.endRow; 
							destroyer2loc[1][1] = destroyer2.endCol; 
							DestroyerSunk DS2 = new DestroyerSunk(hitP, destroyer2loc, buttonArray); 
							DS2.start(); 
							if (PAcount==5 && PBcount==4 && PCcount==3 && PDcount==4)
							{
								FinishingMessage finish2 = new FinishingMessage(DS2, 0, this); 
								finish2.start(); 
							}
						}
					}
				}
				uniqueGuess = true; 
			}
		}
		//toRetComp = "Computer hit " + temp3 + temp8 + " "; 
		toRetComp = Name2 + " hit " + temp3 + temp8 + " "; 
		if (temp2 == 'A')
			toRetComp = toRetComp + "and hit an Aircraft Carrier "; 
		else if (temp2 == 'B')
			toRetComp = toRetComp + "and hit a Battleship "; 
		else if (temp2 == 'C')
			toRetComp = toRetComp + "and hit a Cruiser "; 
		else if (temp2 == 'D')
			toRetComp = toRetComp + "and hit a Destroyer ";
		else
			toRetComp = toRetComp + "and hit nothing "; 
		return toRetComp; 
	}	
}