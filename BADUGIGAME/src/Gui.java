import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;

import java.io.*;
import java.net.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;

import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener {

	JFrame frame;
	public Gui window;
	JButton MyCard1, MyCard2, MyCard3, MyCard4, btnCall, btnAllIn, btnFold,
			btnRaise, btnBet, btnCheck, btnChangeCards;
	JScrollBar scrollBar;
	JTextPane txtp;
	JLabel lbPlayer4, lbPlayer3, lbPlayer6, lbPlayer5, BgTable, lbPlayer2;
	JLabel lblRoundNo;
	JLabel lblPot;
	// Client
	static boolean connected = false;
	static int Cn = 0, NoP = 0;
	static Socket socket = null;
	static BufferedReader in = null;
	static PrintWriter out = null;
	Integer[] returncard = { 0, 0, 0, 0 };
	int Mynumber = 0, active = 0;
	String command,enable="";
	JLabel lblLbmypot;
	JLabel lbP4Pot;
	JLabel lbP3Pot;
	JLabel lbP6Pot;
	JLabel lbP2Pot;
	JLabel lbP5Pot;
	JLabel lblPlayer6Nick, lblPlayer2Nick, lblPlayer3Nick, lblPlayer4Nick,
			lblPlayer5Nick, lblMyNick;
	Server server;
	StartGui okno = new StartGui();
	private JButton btnJoin;
	private JLabel MyCash;
	Runnable runners;
	Thread thread2;
	String w = "";

	public Gui() {

		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(200, 20, 905, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		MyCard1 = new JButton();
		MyCard1.setBackground(new Color(153, 0, 0));
		MyCard1.setBounds(257, 431, 76, 96);
		frame.getContentPane().add(MyCard1);
		MyCard1.addActionListener(this);

		MyCard2 = new JButton();
		MyCard2.setBackground(new Color(153, 0, 0));
		MyCard2.setBounds(329, 431, 76, 96);
		frame.getContentPane().add(MyCard2);
		MyCard2.addActionListener(this);

		MyCard3 = new JButton();
		MyCard3.setBackground(new Color(153, 0, 0));
		MyCard3.setBounds(403, 431, 76, 96);
		frame.getContentPane().add(MyCard3);
		MyCard3.addActionListener(this);

		MyCard4 = new JButton();
		MyCard4.setBackground(new Color(153, 0, 0));
		MyCard4.setBounds(478, 431, 76, 96);
		frame.getContentPane().add(MyCard4);
		MyCard4.addActionListener(this);

		scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar.setBounds(608, 593, 213, 17);
		frame.getContentPane().add(scrollBar);
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				int s=0,max=0;
				
				//cash = Integer.parseInt(MyCash.getText());
				// startCash =
				txtp.setText(Integer.toString(scrollBar.getValue()));
				s = scrollBar.getValue();
				max = scrollBar.getMaximum() - 10;
				//System.out.println(s + " ");
				if (s == max) {
					System.out.println(s+ " "+max);
				if (btnBet.isEnabled() == true) {
				enable = "1";
				//System.out.println(enable);
				} else
				if (btnRaise.isEnabled() == true) {
				enable = "2";
				}
				btnBet.setEnabled(false);
				btnRaise.setEnabled(false);
				
				} else {
					System.out.println(enable);
				if (enable == "1") {
				btnBet.setEnabled(true);
				} else {
				if (enable == "2") {
				btnRaise.setEnabled(true);
				}
				}
				}
				}
				});

		txtp = new JTextPane();
		txtp.setText(Integer.toString(scrollBar.getValue()));
		txtp.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtp.setForeground(new Color(255, 255, 255));
		txtp.setBackground(Color.RED);
		txtp.setBounds(840, 590, 27, 20);
		frame.getContentPane().add(txtp);

		btnCall = new JButton("Call");
		btnCall.setEnabled(false);
		btnCall.setBounds(706, 556, 67, 23);
		frame.getContentPane().add(btnCall);
		btnCall.addActionListener(this);

		btnRaise = new JButton("Raise");
		btnRaise.setEnabled(false);
		btnRaise.setBounds(608, 556, 67, 23);
		frame.getContentPane().add(btnRaise);
		btnRaise.addActionListener(this);

		btnBet = new JButton("Bet");
		btnBet.setEnabled(false);
		btnBet.setBounds(706, 522, 67, 23);
		frame.getContentPane().add(btnBet);
		btnBet.addActionListener(this);
		btnAllIn = new JButton("All-in");
		btnAllIn.setEnabled(false);
		btnAllIn.setBounds(800, 522, 67, 23);
		frame.getContentPane().add(btnAllIn);
		btnAllIn.addActionListener(this);

		btnFold = new JButton("Fold");
		btnFold.setEnabled(false);
		btnFold.setBounds(800, 556, 67, 23);
		frame.getContentPane().add(btnFold);
		btnFold.addActionListener(this);

		btnCheck = new JButton("Check");
		btnCheck.setEnabled(false);
		btnCheck.setBounds(608, 522, 67, 23);
		frame.getContentPane().add(btnCheck);

		btnJoin = new JButton("Join");
		btnJoin.setBounds(58, 578, 67, 32);
		frame.getContentPane().add(btnJoin);
		btnJoin.addActionListener(this);

		btnChangeCards = new JButton("Change Cards");
		btnChangeCards.setBounds(329, 593, 143, 23);
		frame.getContentPane().add(btnChangeCards);
		btnChangeCards.addActionListener(this);
		btnChangeCards.setEnabled(false);

		lblPot = new JLabel("Pot");
		lblPot.setForeground(Color.WHITE);
		lblPot.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPot.setBounds(10, 11, 59, 14);
		frame.getContentPane().add(lblPot);

		btnCheck.addActionListener(this);

		lbPlayer4 = new JLabel("");
		lbPlayer4
				.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\b2fh.png"));
		lbPlayer4.setBounds(348, 62, 102, 105);
		frame.getContentPane().add(lbPlayer4);

		lblPlayer4Nick = new JLabel("");
		lblPlayer4Nick.setForeground(Color.WHITE);
		lblPlayer4Nick.setBounds(348, 36, 89, 14);
		frame.getContentPane().add(lblPlayer4Nick);

		lbP4Pot = new JLabel("0");
		lbP4Pot.setHorizontalAlignment(SwingConstants.CENTER);
		lbP4Pot.setForeground(Color.WHITE);
		lbP4Pot.setBounds(377, 169, 46, 14);
		frame.getContentPane().add(lbP4Pot);

		lbPlayer3 = new JLabel("");
		lbPlayer3.setBackground(new Color(153, 0, 0));
		lbPlayer3
				.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\b2fh.png"));
		lbPlayer3.setBounds(98, 62, 96, 105);
		frame.getContentPane().add(lbPlayer3);

		lbP3Pot = new JLabel("0");
		lbP3Pot.setHorizontalAlignment(SwingConstants.CENTER);
		lbP3Pot.setForeground(Color.WHITE);
		lbP3Pot.setBounds(125, 169, 46, 14);
		frame.getContentPane().add(lbP3Pot);

		lbPlayer6 = new JLabel("");
		lbPlayer6
				.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\b2fh.png"));
		lbPlayer6.setBackground(new Color(153, 0, 0));
		lbPlayer6.setBounds(606, 263, 96, 105);
		frame.getContentPane().add(lbPlayer6);

		lbP6Pot = new JLabel("0");
		lbP6Pot.setHorizontalAlignment(SwingConstants.CENTER);
		lbP6Pot.setForeground(Color.WHITE);
		lbP6Pot.setBounds(624, 379, 46, 14);
		frame.getContentPane().add(lbP6Pot);

		lbPlayer2 = new JLabel("");
		lbPlayer2
				.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\b2fh.png"));
		lbPlayer2.setBackground(new Color(153, 0, 0));
		lbPlayer2.setBounds(98, 263, 96, 105);
		frame.getContentPane().add(lbPlayer2);

		lbP2Pot = new JLabel("0");
		lbP2Pot.setHorizontalAlignment(SwingConstants.CENTER);
		lbP2Pot.setForeground(Color.WHITE);
		lbP2Pot.setBounds(125, 379, 46, 14);
		frame.getContentPane().add(lbP2Pot);

		lbPlayer5 = new JLabel("");
		lbPlayer5
				.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\b2fh.png"));
		lbPlayer5.setBackground(new Color(153, 0, 0));
		lbPlayer5.setBounds(599, 62, 96, 105);
		frame.getContentPane().add(lbPlayer5);

		lblRoundNo = new JLabel("Round No");
		lblRoundNo.setForeground(Color.WHITE);
		lblRoundNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRoundNo.setBounds(715, 11, 115, 32);
		frame.getContentPane().add(lblRoundNo);

		lblLbmypot = new JLabel("0");
		lblLbmypot.setForeground(Color.WHITE);
		lblLbmypot.setBounds(471, 406, 46, 14);
		frame.getContentPane().add(lblLbmypot);

		lblMyNick = new JLabel("");
		lblMyNick.setForeground(Color.WHITE);
		lblMyNick.setBounds(272, 406, 133, 14);
		frame.getContentPane().add(lblMyNick);

		lbP5Pot = new JLabel("0");
		lbP5Pot.setHorizontalAlignment(SwingConstants.CENTER);
		lbP5Pot.setForeground(Color.WHITE);
		lbP5Pot.setBounds(624, 169, 46, 14);
		frame.getContentPane().add(lbP5Pot);

		lblPlayer2Nick = new JLabel("");
		lblPlayer2Nick.setForeground(Color.WHITE);
		lblPlayer2Nick.setBounds(98, 228, 96, 14);
		frame.getContentPane().add(lblPlayer2Nick);

		lblPlayer3Nick = new JLabel("");
		lblPlayer3Nick.setForeground(Color.WHITE);
		lblPlayer3Nick.setBounds(98, 36, 96, 14);
		frame.getContentPane().add(lblPlayer3Nick);

		lblPlayer5Nick = new JLabel("");
		lblPlayer5Nick.setForeground(Color.WHITE);
		lblPlayer5Nick.setBounds(599, 36, 96, 14);
		frame.getContentPane().add(lblPlayer5Nick);

		lblPlayer6Nick = new JLabel("");
		lblPlayer6Nick.setForeground(Color.WHITE);
		lblPlayer6Nick.setBounds(609, 228, 96, 14);
		frame.getContentPane().add(lblPlayer6Nick);

		MyCash = new JLabel("");
		MyCash.setForeground(Color.WHITE);
		MyCash.setBounds(257, 538, 46, 14);
		frame.getContentPane().add(MyCash);

		BgTable = new JLabel("");
		BgTable.setIcon(new ImageIcon("C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\felt_table.jpg"));
		BgTable.setBounds(0, 0, 911, 672);
		frame.getContentPane().add(BgTable);
		
		
		//runners = new Wait(window, socket,in,out);
		//thread2 = new Thread(runners);

	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == MyCard1) {
			if (returncard[0] == 1) {
				returncard[0] = 0;
			} else {
				returncard[0] = 1;
			}

		} else if (source == MyCard2) {
			if (returncard[1] == 1) {
				returncard[1] = 0;
			} else {
				returncard[1] = 1;
			}
		} else if (source == MyCard3) {
			if (returncard[2] == 1) {
				returncard[2] = 0;
			} else {
				returncard[2] = 1;
			}
		} else if (source == MyCard4) {
			if (returncard[3] == 1) {
				returncard[3] = 0;
			} else {
				returncard[3] = 1;
			}
		} else {
			if (connected) {

				System.out.print(NoP);

				command = "";

				if (e.getSource() == btnBet) {
					setEnabled();
					command = ("1 " + scrollBar.getValue());
				}

				if (e.getSource() == btnRaise) {
					setEnabled();
					command = "2 " + scrollBar.getValue();
				}
				if (e.getSource() == btnFold) {
					setEnabled();
					command = "3 ";
				}
				if (e.getSource() == btnCheck) {
					setEnabled();
					command = "4 ";
				}
				if (e.getSource() == btnCall) {
					setEnabled();

					command = "5 ";
				}
				if (e.getSource() == btnAllIn) {
					setEnabled();
					command = "8 ";
				}
				if (e.getSource() == btnJoin) {
					command = "6 ";
					btnChangeCards.setEnabled(true);
					btnJoin.setVisible(false);
				}
				if (e.getSource() == btnChangeCards) {
					command = ("7 " + returncard[0] + " " + returncard[1]
							+ " " + returncard[2] + " " + returncard[3] + " ");
					btnChangeCards.setEnabled(false);
					returncardClear();
				}
				out.println(command);

			}

			try {

				String answer = in.readLine();

				// NoP=Character.getNumericValue(answer.charAt(3));
				switch (String.valueOf(answer.charAt(0))) {

				// Join
				case "J":
					Mynumber = Character.getNumericValue(answer.charAt(1));
					setEverything(answer);
					answer = in.readLine();
					setCards(answer);

				
					thread2.start();
					btnJoin.setEnabled(false);
					break;

				// change cards
				case "O":
					setCards(answer);
					break;

				// Call
				case "C":

					setEverything(answer);
					thread2 = new Thread(runners);
					thread2.start();
					//Wait();
					break;

				// Bet and Raise
				case "B":

					setEverything(answer);
					thread2 = new Thread(runners);
					thread2.start();
					//Wait();
					break;

				// End round
				case "E":
					JOptionPane.showMessageDialog(frame,
							"Winner is: " + answer.substring(2));
					answer=in.readLine();
					setEverything(answer);
					answer=in.readLine();
					setCards(answer);
					thread2 = new Thread(runners);
					thread2.start();
					break;

				case "F":
					thread2 = new Thread(runners);
					thread2.start();
					break;
					
				case "G":
					System.exit(0);
					this.frame.dispose();
					break;
				case "Q":
					System.exit(0);
					this.frame.dispose();
					break;
					
				default:
					JOptionPane.showMessageDialog(frame, "Error");
					frame.dispose();
					break;

				}
				System.out.println("Mój numer: " + Mynumber + "Liczba graczy: "
						+ NoP);

				if (Mynumber > NoP) {
					JOptionPane.showMessageDialog(frame, "server is full");
					this.frame.dispose();
				}
			} catch (IOException ea) {

			}
		}

	}

	public void setEverything(String answer) {
		int j = 3;
		int start = 3;
		for (int i = 1; i <= 22; i++) {
			while (!(Character.isWhitespace(answer.charAt(j)))) {
				j++;

			}
			wypisz(answer, start, j, i);
			j = j + 1;
			start = j;
		}
		if((Integer.parseInt(MyCash.getText())<0)||((Integer.parseInt(MyCash.getText())==0)&&(Integer.parseInt(lblLbmypot.getText())==0)))
		{
			try {
				out.println("Q");
			} catch (NullPointerException ex) {
			}
			System.exit(0);
			this.frame.dispose();
		}
	}

	public void setCards(String answer) {
		int j = 2;
		int start = 2;
		for (int k = 1; k <= 24; k++) {
			while (!(Character.isWhitespace(answer.charAt(j)))) {
				j++;

			}
			cards(answer, start, j, k);
			j = j + 1;
			start = j;
		}
	}

	public void wypisz(String answer, int start, int j, int i) {
		switch (Mynumber) {

		case 1:
			if (i == 1)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 2)
			{	MyCash.setText(answer.substring(start, j));
				scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 3)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 4)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 6)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 7)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 9)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 10)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 12)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 13)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 15)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 16)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 18)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

				lblPot.setText("Pot: " + answer.substring(start, j));

			if (i == 21)

				lblRoundNo.setText("Round No. " + answer.substring(start, j));

			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
				}
			
			break;
		case 2:
			if (i == 4)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 5)
				{MyCash.setText(answer.substring(start, j));
				scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 6)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 7)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 9)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 10)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 12)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 13)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 15)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 16)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 18)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 1)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 3)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

			{
				lblPot.setText("Pot: " + answer.substring(start, j));
			}
			if (i == 21)

			{
				lblRoundNo.setText("Round No. " + answer.substring(start, j));
			}
			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}

			break;
		case 3:
			if (i == 7)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 8)
			{	MyCash.setText(answer.substring(start, j));
			scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 9)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 10)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 12)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 13)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 15)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 16)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 18)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 1)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 3)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 4)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 6)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

			{
				lblPot.setText("Pot: " + answer.substring(start, j));
			}
			if (i == 21)

			{
				lblRoundNo.setText("Round No. " + answer.substring(start, j));
			}
			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}

			break;
		case 4:
			if (i == 10)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 11)
				{MyCash.setText(answer.substring(start, j));
				scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 12)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 13)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 15)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 16)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 18)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 1)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 3)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 4)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 6)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 7)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 9)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

			{
				lblPot.setText("Pot: " + answer.substring(start, j));
			}
			if (i == 21)

			{
				lblRoundNo.setText("Round No. " + answer.substring(start, j));
			}
			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}

			break;
		case 5:
			if (i == 13)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 14)
				{MyCash.setText(answer.substring(start, j));
				scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 15)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 16)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 18)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 1)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 3)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 4)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 6)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 7)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 9)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 10)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 12)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

			{
				lblPot.setText("Pot: " + answer.substring(start, j));
			}
			if (i == 21)

			{
				lblRoundNo.setText("Round No. " + answer.substring(start, j));
			}
			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}

			break;
		case 6:
			if (i == 16)
				lblLbmypot.setText(answer.substring(start, j));
			if (i == 17)
				{MyCash.setText(answer.substring(start, j));
				scrollBar.setMaximum(10+Integer.parseInt((answer.substring(start, j))));}
			if (i == 18)
				lblMyNick.setText(answer.substring(start, j));

			if (i == 1)
				lbP2Pot.setText(answer.substring(start, j));

			if (i == 3)
				lblPlayer2Nick.setText(answer.substring(start, j));

			if (i == 4)
				lbP3Pot.setText(answer.substring(start, j));

			if (i == 6)
				lblPlayer3Nick.setText(answer.substring(start, j));

			if (i == 7)
				lbP4Pot.setText(answer.substring(start, j));

			if (i == 9)
				lblPlayer4Nick.setText(answer.substring(start, j));

			if (i == 10)
				lbP5Pot.setText(answer.substring(start, j));

			if (i == 12)
				lblPlayer5Nick.setText(answer.substring(start, j));

			if (i == 13)
				lbP6Pot.setText(answer.substring(start, j));

			if (i == 15)
				lblPlayer6Nick.setText(answer.substring(start, j));
			if (i == 19)
				try {
					NoP = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {
					

				}
			if (i == 20)

			{
				lblPot.setText("Pot: " + answer.substring(start, j));
			}
			if (i == 21)

			{
				lblRoundNo.setText("Round No. " + answer.substring(start, j));
			}
			if (i == 22)
				try {
					active = Integer.parseInt(answer.substring(start, j));
				}

				catch (NumberFormatException e) {

				}

			break;

		}
	}

	public void cards(String answer, int start, int j, int i) {
		switch (Mynumber) {
		case 1:
			if (i == 1) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 2) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 3) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 4) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}

			break;
		case 2:
			if (i == 5) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 6) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 7) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 8) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}

			break;
		case 3:
			if (i == 9) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 10) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 11) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 12) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}

			break;
		case 4:
			if (i == 13) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 14) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 15) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 16) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}

			break;
		case 5:
			if (i == 17) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 18) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 19) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 20) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}

			break;
		case 6:
			if (i == 21) {
				MyCard1.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ answer.substring(start, j) + ".png"));
			}
			if (i == 22) {
				MyCard2.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 23) {
				MyCard3.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}
			if (i == 24) {
				MyCard4.setIcon(new ImageIcon(
						"C:\\Users\\Olek\\workspace\\BADUGIGAME\\src\\classic-cards\\"
								+ Integer.parseInt(answer.substring(start, j))
								+ ".png"));
			}

			break;
		}
	}

	public void close() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					out.println("Q");
				} catch (NullPointerException ex) {
				}
				System.exit(0);
			}
		});
	}

	public void returncardClear() {
		for (int i = 0; i < 4; i++) {
			returncard[i] = 0;
		}
	}

	public void PossibleAction(String w) {
		System.out.println(w);
		if(Character.toString(w.charAt(0)).equals("G"))
		{
		frame.dispose();
		}
		else{
		if (Character.toString(w.charAt(0)).equals("Y")) {

			btnCheck.setEnabled(true);
		} else {
			btnCheck.setEnabled(false);

		}
		if (Character.toString(w.charAt(1)).equals("Y")) {

			btnBet.setEnabled(true);
		} else {
			btnBet.setEnabled(false);

		}
		if (Character.toString(w.charAt(2)).equals("Y")) {

			btnRaise.setEnabled(true);
		} else {
			btnRaise.setEnabled(false);

		}
		if (Character.toString(w.charAt(3)).equals("Y")) {

			btnCall.setEnabled(true);
		} else {
			btnCall.setEnabled(false);

		}
		if (Character.toString(w.charAt(4)).equals("Y")) {

			btnAllIn.setEnabled(true);
		} else {
			btnAllIn.setEnabled(false);

		}
		btnFold.setEnabled(true);
		
		
		scrollBar.setMinimum(Integer.parseInt(w.substring(5))-Integer.parseInt(lblLbmypot.getText())+1);
	}}

	public void setEnabled() {
		btnCheck.setEnabled(false);
		btnFold.setEnabled(false);
		btnAllIn.setEnabled(false);
		btnCall.setEnabled(false);
		btnRaise.setEnabled(false);
		btnBet.setEnabled(false);
		btnChangeCards.setEnabled(true);
	}

	public void Wait() {

		while (active != Mynumber) {
			out.println("W ");
			try {
				String w = in.readLine();

				setEverything(w);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		out.println("P ");
		try {
			w = in.readLine();
			PossibleAction(w);
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
};
