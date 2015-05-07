package currenme_CSCI201_Assignment5;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Menu extends JFrame
{
	JPanel topholder;  
	JLabel IPLabel;
	JLabel actualIP;
	JLabel name; 
	JTextField enterName; 
	JPanel nameHolder; 
	JPanel topContainer; 
	JCheckBox hostBox; 
	JLabel enterIP; 
	JTextField storeIP; 
	JPanel midHolder; 
	JCheckBox portBox; 
	JLabel portLabel; 
	JTextField portText; 
	JPanel midContainer; 
	JPanel portHolder; 
	JCheckBox mapBox; 
	JTextField mapText; 
	JPanel mapHolder; 
	JButton refreshBut;
	JButton connectBut; 
	JPanel buttonHolder; 
	JPanel bottomContainer; 
	JPanel overallHolder; 
	String ip; 
	Timer timer; 
	boolean internetExists; 
	URL toCheckIp; 
	JPanel cards; 
	JLabel waitingTime; 
	JPanel card2; 
	JPanel card3; 
	int time; 
	boolean prepareGame; 
	EditGameGUI egi = null;
	EditGameGUI egi2 = null;
	EditGameGUI egi3 = null; 
	Client client; 
	Server server; 
	boolean enteredName; 
	JMenuBar MenuInfoBar; 
	JMenu info; 
	JPanel aboutPanel; 
	JLabel infoLabel; 
	JPanel topPanel2; 
	JMenuItem howTo; 
	JPanel storeDirec; 
	JLabel text1, text2, text3, text4, text5; 
	JButton infoReturn; 
	public Menu()
	{
		super("Battleship Menu"); 
		waitingTime = new JLabel("Waiting for another player...30 seconds until timeout");
		MenuInfoBar = new JMenuBar(); 
		howTo = new JMenuItem("How To"); 
		info = new JMenu("Menu Information"); 
		info.add(howTo); 
		MenuInfoBar.add(info); 
		topPanel2 = new JPanel(new BorderLayout()); 
		MenuInfoBar.add(info); 
		card2 = new JPanel(); 
		card3 = new JPanel(); 
		card2.add(waitingTime); 
		cards = new JPanel(); 
		cards.setLayout(new CardLayout()); 
		toCheckIp = null;
		storeDirec = new JPanel(new BorderLayout()); 
		text1 = new JLabel("<html>1. Select host if you wish to both play the game and host the game<br> "
				+ "2. If hosting, enter localhost for IP. <br> Otherwise enter the IP of the host's computer<br>"
				+ "3. If hosting, choose a port to host the game on<br>" + "Otherwise, enter the port of the host<br>"
						+ "4. To play against the computer select 201 Maps<br>" 
				+"5. If playing against the computer enter <br> map1, map2, map3, map4, or map5<br> to load the Computer's data<br>"
						+"6. When ready, press connect.<br></html>"); 
		storeDirec.add(text1, BorderLayout.NORTH); 
		infoReturn = new JButton("Retun to Menu"); 
		storeDirec.add(infoReturn, BorderLayout.SOUTH); 
		card3.add(storeDirec); 
		internetExists = true; 
		try
		{
			InetAddress website = InetAddress.getByName("www.yahoo.com"); 
			if (website == null)
			{
				internetExists = false; 
			}
		}
		catch (UnknownHostException uhe)
		{
			internetExists = false;
		}
		catch (Exception e)
		{
			internetExists = false;
		}
		if (internetExists == true)
		{
			try {
				toCheckIp = new URL("http://checkip.amazonaws.com");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(toCheckIp.openStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ip = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		topholder = new JPanel(); 
		IPLabel = new JLabel("Your Public IP:"); 
		actualIP = new JLabel();
		if (internetExists == true)
			actualIP.setText(ip); 
		else
			actualIP.setText("Error: No Internet"); 
		topholder.add(IPLabel); 
		topholder.add(actualIP); 
		name = new JLabel("Name: "); 
		enterName = new JTextField("", 20); 
		nameHolder = new JPanel(); 
		nameHolder.add(name); 
		nameHolder.add(enterName); 
		topContainer = new JPanel(new BorderLayout()); 
		topContainer.add(topholder, BorderLayout.NORTH); 
		topContainer.add(nameHolder, BorderLayout.SOUTH);
		hostBox = new JCheckBox("Host Game", false); 
		enterIP = new JLabel("Enter IP: "); 
		storeIP = new JTextField("localhost", 10); 
		midHolder = new JPanel(); 
		midHolder.add(hostBox); 
		midHolder.add(enterIP); 
		midHolder.add(storeIP); 
		portBox = new JCheckBox("Custom Port", false); 
		portLabel = new JLabel("Port: "); 
		portText = new JTextField("3469", 10);
		midContainer = new JPanel(new BorderLayout()); 
		portHolder = new JPanel(); 
		portHolder.add(portBox); 
		portHolder.add(portLabel); 
		portHolder.add(portText); 
		midContainer.add(midHolder, BorderLayout.NORTH); 
		midContainer.add(portHolder, BorderLayout.SOUTH); 
		mapBox = new JCheckBox("201 Maps", false); 
		mapText = new JTextField("", 20); 
		mapHolder = new JPanel(); 
		mapHolder.add(mapBox); 
		mapHolder.add(mapText); 
		refreshBut = new JButton("Refresh"); 
		mapBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (mapBox.isSelected())
				{
					hostBox.setSelected(false); 
					hostBox.setEnabled(false); 
					portBox.setSelected(false); 
					portBox.setEnabled(false);
					storeIP.setEnabled(false); 
					portText.setEnabled(false);
					mapText.setEnabled(true);
				}
				else
				{
					hostBox.setEnabled(true);
					portBox.setEnabled(true); 
					storeIP.setEnabled(true); 
					portText.setEnabled(true); 
				}
			}
		});
		hostBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e2) 
			{
				if (hostBox.isSelected())
				{
					mapBox.setSelected(false); 
					mapBox.setEnabled(false);
					mapText.setEnabled(false);
				}	
				else
				{
					mapBox.setEnabled(true);  
				}
			}
		}); 
		portBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e2) 
			{
				if (portBox.isSelected())
				{
					mapBox.setSelected(false); 
					mapBox.setEnabled(false); 
					mapText.setEnabled(false);
				}
				else
				{
					mapBox.setEnabled(true);
				}
			}
		});
		if (internetExists == true)
			refreshBut.setEnabled(false); 
		else
			refreshBut.setEnabled(true);
		refreshBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				internetExists = true; 
				try
				{
					InetAddress website = InetAddress.getByName("www.yahoo.com"); 
					if (website == null)
					{
						internetExists = false; 
					}
				}
				catch (UnknownHostException uhe)
				{
					internetExists = false;
				}
				catch (Exception e1)
				{
					internetExists = false;
				}
				if (internetExists == true)
				{
					try {
						toCheckIp = new URL("http://checkip.amazonaws.com");
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					BufferedReader in = null;
					try {
						in = new BufferedReader(new InputStreamReader(toCheckIp.openStream()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						ip = in.readLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (internetExists == true)
					actualIP.setText(ip);
				if (internetExists == true)
					refreshBut.setEnabled(false); 
				if (internetExists == true)
					connectBut.setEnabled(true); 
			}
		}); 
		connectBut = new JButton("Connect"); 
		if (internetExists == true)
			connectBut.setEnabled(true); 
		else
			connectBut.setEnabled(false); 
		buttonHolder = new JPanel(); 
		buttonHolder.add(refreshBut); 
		buttonHolder.add(connectBut); 
		bottomContainer = new JPanel(new BorderLayout()); 
		bottomContainer.add(mapHolder, BorderLayout.NORTH); 
		bottomContainer.add(buttonHolder, BorderLayout.SOUTH); 
		overallHolder = new JPanel(new BorderLayout()); 
		topPanel2.add(MenuInfoBar, BorderLayout.NORTH); 
		topPanel2.add(topContainer, BorderLayout.SOUTH); 
		overallHolder.add(topPanel2, BorderLayout.NORTH); 
		overallHolder.add(midContainer, BorderLayout.CENTER); 
		overallHolder.add(bottomContainer, BorderLayout.SOUTH); 
		cards.add(overallHolder, "MainMenu"); 
		cards.add(card2, "WaitingMenu"); 
		cards.add(card3, "Information"); 
		prepareGame = false; 
		howTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ie)
			{
				((CardLayout)cards.getLayout()).show(cards, "Information"); 
			}
		});
		infoReturn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				((CardLayout)cards.getLayout()).show(cards, "MainMenu"); 
			}
		}); 
		connectBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (hostBox.isSelected() == false && portBox.isSelected() == false && mapBox.isSelected() == false)
				{
					
				}
				boolean isHost = false; 
				boolean use201Maps = false; 
				if (hostBox.isSelected() == true)
					isHost = true; 
				if (mapBox.isSelected() == true) 
					use201Maps = true;
				if (isHost == true && use201Maps == false)
				{
					int port = Integer.parseInt(portText.getText()); 
					String name = enterName.getText();
					if (name.length() > 0)
						enteredName = true; 
					if (name.length() == 0)
					{
						enteredName = false; 
						JOptionPane.showMessageDialog(null, "Please enter a name");
					}
					if (enteredName == true)
					{
						server = new Server(port, name);
						server.start();
						time = 30; 
						
						((CardLayout)cards.getLayout()).show(cards, "WaitingMenu"); 
						timer = new Timer(1000, new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								if (time > 0)
								{
									time--; 
									String temp = Integer.toString(time); 
									waitingTime.setText("Waiting for another player..." + temp + " seconds until timeout");
									if (server.clientConnected && server.gotClientName && egi == null)
									{
										egi = new EditGameGUI(name, server.ClientName(), server); 
										server.setServerEGI(egi);
										timer.stop(); 
										setVisible(false); 
									}
								}
								if (time == 0)
								{
									((CardLayout)cards.getLayout()).show(cards, "MainMenu"); 
									timer.stop(); 
								}
							}
						}); 
						timer.start();
					}
				}
				if (isHost == false && use201Maps == false)
				{
					int port = Integer.parseInt(portText.getText()); 
					String IP = storeIP.getText(); 
					String name = enterName.getText(); 
					if (name.length() == 0)
					{
						enteredName = false; 
						JOptionPane.showMessageDialog(null, "Please enter a name");
					}
					else
						enteredName = true; 
					if (enteredName == true)
					{
						client = new Client(IP, port, name); 
						client.start(); 
						try {
							Thread.sleep(500);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						if (client.correctPort) 
						{
							egi2 = new EditGameGUI(name, client.ServerName(), client);
							client.setClientEGI(egi2); 
							setVisible(false); 
						}
					}
				}
				if (use201Maps == true)
				{
					URL maps;
					String file = mapText.getText(); 
					BufferedReader in;
					String name = enterName.getText(); 
					if (name.length() == 0)
					{
						enteredName = false; 
						JOptionPane.showMessageDialog(null, "Please enter a name");
					}
					else
						enteredName = true; 
					if (enteredName == true)
					{
						String website = "http://www-scf.usc.edu/~csci201/assignments/" + file + ".battle"; 
						String inputLine;
						try 
						{
							maps = new URL(website);
							in = new BufferedReader(new InputStreamReader(maps.openStream()));
							EditGameGUI egi3 = new EditGameGUI(name, "Computer", null); 
							setVisible(false); 
							while ((inputLine = in.readLine()) != null)
							{
								egi3.readInFromOnline(inputLine); 
							}
							in.close();
						} 
						catch (MalformedURLException e) 
						{
							e.printStackTrace();
						}
						catch (FileNotFoundException e)
						{
							JOptionPane.showMessageDialog(null, "Incorrect file type. See Menu Information for more details");
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		add(cards); 
		setSize(450, 250); 
		setResizable(false); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		Menu men = new Menu(); 
	}
}