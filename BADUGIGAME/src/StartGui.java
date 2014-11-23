import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;


public class StartGui implements ActionListener {

	JFrame frame;
	JButton btnNewGame,btnJoinToExisting;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGui window = new StartGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartGui() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 380, 188);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(10, 48, 129, 52);
		frame.getContentPane().add(btnNewGame);
		btnNewGame.addActionListener(this);
		
		btnJoinToExisting = new JButton("Join to existing game");
		btnJoinToExisting.setBounds(158, 48, 196, 52);
		frame.getContentPane().add(btnJoinToExisting);
		btnJoinToExisting.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source==btnNewGame)
		{
		System.out.println("New Game");
		GameCreator window = new GameCreator();
		window.frame.setVisible(true);
		frame.dispose();
		}
		else if(source==btnJoinToExisting)
		{
			OknoPolacz okno = new OknoPolacz();
			okno.setVisible(true);
			System.out.println("Join");
			frame.dispose();
		}
	}
	
public int PlayersNumbers()
{
	return 1;
}
}
