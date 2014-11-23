import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameCreator implements ActionListener {
	public GameCreator window;
	JFrame frame;
	int NoP = 0, counter = 1,NoB=0, Port;
	private JTextField TFStartPot, TFNick;
	DefaultListModel<String> listModel, listModel2;
	private JList<String> NickList, TypeList;
	JRadioButton RBBot, RBHuman;
	private JButton btnRename, btnPlay, btnDeletePlayer, AddPlayer;
	private JLabel lblType, lblNick, lblStartPot, lblPort, lblPlayerList;
	private JTextField TFPort;
	private JTextField TFBigBlind;
	private JTextField TFSmallBlind;

	public GameCreator() {

		frame = new JFrame();
		frame.setBounds(100, 100, 528, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		listModel = new DefaultListModel<String>();
		listModel2 = new DefaultListModel<String>();

		AddPlayer = new JButton("Add");
		AddPlayer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		AddPlayer.setBounds(6, 126, 113, 34);
		frame.getContentPane().add(AddPlayer);
		AddPlayer.addActionListener(this);

		TFStartPot = new JTextField();
		TFStartPot.setText("1000");
		TFStartPot.setBounds(350, 64, 55, 23);
		frame.getContentPane().add(TFStartPot);
		TFStartPot.setColumns(10);
		TFStartPot.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent eve) {
				char c = eve.getKeyChar();
				if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE))
					eve.consume();
			}
		});

		TFNick = new JTextField();
		TFNick.setBounds(6, 42, 105, 23);
		TFNick.setText("Player_" + (NoP + 1));
		frame.getContentPane().add(TFNick);
		TFNick.setColumns(10);

		NickList = new JList<String>(listModel);
		NickList.setVisibleRowCount(6);
		NickList.setBounds(129, 67, 139, 183);
		frame.getContentPane().add(NickList);

		TypeList = new JList<String>(listModel2);
		TypeList.setEnabled(false);
		TypeList.setVisibleRowCount(6);
		TypeList.setBounds(272, 67, 68, 183);
		frame.getContentPane().add(TypeList);

		btnDeletePlayer = new JButton("Remove");
		btnDeletePlayer.setBounds(6, 171, 113, 34);
		frame.getContentPane().add(btnDeletePlayer);
		btnDeletePlayer.addActionListener(this);

		RBHuman = new JRadioButton("Human");
		RBHuman.setBounds(6, 70, 109, 23);
		frame.getContentPane().add(RBHuman);
		RBHuman.setSelected(true);
		RBHuman.addActionListener(this);

		RBBot = new JRadioButton("Bot");
		RBBot.setBounds(6, 96, 109, 23);
		frame.getContentPane().add(RBBot);
		RBBot.addActionListener(this);

		btnRename = new JButton("Rename");
		btnRename.setBounds(6, 216, 113, 34);
		frame.getContentPane().add(btnRename);
		btnRename.addActionListener(this);

		btnPlay = new JButton("Create!");
		btnPlay.setBounds(350, 171, 113, 34);
		frame.getContentPane().add(btnPlay);
		btnPlay.addActionListener(this);
		btnPlay.setEnabled(false);

		lblPlayerList = new JLabel("Player List:");
		lblPlayerList.setEnabled(false);
		lblPlayerList.setBounds(131, 46, 137, 14);
		frame.getContentPane().add(lblPlayerList);

		lblType = new JLabel("Type:");
		lblType.setEnabled(false);
		lblType.setBounds(272, 46, 46, 14);
		frame.getContentPane().add(lblType);

		lblNick = new JLabel("Nick");
		lblNick.setEnabled(false);
		lblNick.setBounds(6, 24, 46, 14);
		frame.getContentPane().add(lblNick);

		lblStartPot = new JLabel("Start Cash:");
		lblStartPot.setEnabled(false);
		lblStartPot.setBounds(350, 46, 76, 14);
		frame.getContentPane().add(lblStartPot);

		lblPort = new JLabel("Port:");
		lblPort.setEnabled(false);
		lblPort.setBounds(350, 96, 100, 22);
		frame.getContentPane().add(lblPort);

		TFPort = new JTextField();
		TFPort.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent eve) {
				char c = eve.getKeyChar();
				if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE))
					eve.consume();
			}
		});
		TFPort.setText("6688");
		TFPort.setBounds(350, 120, 55, 25);
		frame.getContentPane().add(TFPort);
		TFPort.setColumns(10);
		
		JLabel lblBigblind = new JLabel("BigBlind:");
		lblBigblind.setEnabled(false);
		lblBigblind.setBounds(424, 46, 65, 14);
		frame.getContentPane().add(lblBigblind);
		
		TFBigBlind = new JTextField();
		TFBigBlind.setText("4");
		TFBigBlind.setBounds(423, 64, 55, 23);
		frame.getContentPane().add(TFBigBlind);
		TFBigBlind.setColumns(10);
		TFBigBlind.addActionListener(this);
		
		TFSmallBlind = new JTextField();
		TFSmallBlind.setText("2");
		TFSmallBlind.setBounds(424, 120, 55, 24);
		frame.getContentPane().add(TFSmallBlind);
		TFSmallBlind.setColumns(10);
		TFSmallBlind.addActionListener(this);
		
		JLabel lblSmallBlind = new JLabel("SmallBlind:");
		lblSmallBlind.setEnabled(false);
		lblSmallBlind.setBounds(424, 100, 78, 14);
		frame.getContentPane().add(lblSmallBlind);
		TFPort.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == AddPlayer) {

			if (NoP >= 6) {
				JOptionPane.showMessageDialog(frame, "Max Players 6.", "Sorry",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			NoP = NoP + 1;
			if (NoP >= 2)
				btnPlay.setEnabled(true);

			if (RBHuman.isSelected()) {

				listModel.addElement(TFNick.getText());
				listModel2.addElement("Human");
			} else {
				TFNick.setForeground(Color.RED);
				listModel.addElement(TFNick.getText());
				listModel2.addElement("Bot");
				NoB=NoB+1;
			}

			TFNick.setText("Player_" + (counter + 1));
			counter++;
		}
		if (source == RBHuman) {
			RBBot.setSelected(false);
		}
		if (source == RBBot) {
			RBHuman.setSelected(false);
		}

		if (source == btnDeletePlayer) {
			int index = NickList.getSelectedIndex();
			if (index > NoP || index < 0) {
				JOptionPane.showMessageDialog(frame, "Player not selected",
						"Sorry", JOptionPane.ERROR_MESSAGE);
				return;
			}
			listModel.removeElementAt(index);
			if(listModel2.elementAt(index)=="Bot")
				NoB=NoB-1;
			listModel2.removeElementAt(index);
			NoP--;
			if (NoP < 2)
				btnPlay.setEnabled(false);
		}
		if (source == btnRename) {

			int index = NickList.getSelectedIndex();
			if (index > NoP || index < 0) {
				JOptionPane.showMessageDialog(frame, "Player not selected",
						"Sorry", JOptionPane.ERROR_MESSAGE);
				return;
			}
			listModel.setElementAt(NewName(), index);
		}
		if (source == btnPlay) {
			Port = Integer.parseInt(TFPort.getText());
			for(int i=listModel.getSize()+1;i<=6;i++)
			{
				listModel.addElement("Nobody");
			}
			// port,liczba graczy,liczba botow,stawka poczatkowa,NazwaGracza1,NazwaGracza2...
			String[] tab = {TFPort.getText(), Integer.toString(NoP),Integer.toString(NoB),TFStartPot.getText(),listModel.elementAt(0),listModel.elementAt(1),listModel.elementAt(2),listModel.elementAt(3),listModel.elementAt(4),listModel.elementAt(5),TFBigBlind.getText(),TFSmallBlind.getText()};
			this.frame.dispose();
			Server.main(tab);
			

			
		}

	}

	private String NewName() {

		String s = (String) JOptionPane.showInputDialog("Enter new nick:",
				"New_Nick");

		return s;
	
	}
}
