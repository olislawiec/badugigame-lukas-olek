import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class OknoPolacz extends JFrame implements ActionListener
{
	JTextField host, nr_portu;
	JLabel nazwa, numer;
	JButton polacz;
	Gui Client = new Gui();
	int port;
	

	
	public OknoPolacz()
	{
		
		
		this.setBounds(200,200,230,180);
		this.setTitle("Okno Polacz");
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		nazwa = new JLabel("Host:");
		nazwa.setBounds(10,10,120,30);
		add(nazwa);
		
		host = new JTextField();
		host.setBounds(10,40,120,30);
		add(host);
		
		numer = new JLabel("Port:");
		numer.setBounds(10,70,120,30);
		add(numer);
		
		nr_portu = new JTextField();
		nr_portu.setBounds(10,100,120,30);
		add(nr_portu);
		
		polacz = new JButton("Polacz");
		polacz.setBounds(140, 70, 80, 30);
		polacz.addActionListener(this);
		add(polacz);
	}
	
	public void actionPerformed(ActionEvent a)
	{
		
		if(a.getSource()==polacz)
		{
			port=Integer.parseInt(nr_portu.getText());
			polacz(port);
			
		}
	}
	public void polacz(int port)
	{
		try
		{
			
			
			if(port<=0)
			{
				JOptionPane.showMessageDialog(null, "Port musi byc liczba wieksza od 0");
				nr_portu.setText("");
			}
		}
		catch(NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(null, "Podana liczba nie jest liczba naturalna");
			nr_portu.setText("");
		}
		
		//Proba nawiazania palaczenia z serwerem
		try
		{	
			
			Client.socket = new Socket(host.getText(),port);
			Client.out = new PrintWriter(Client.socket.getOutputStream(), true);
			Client.in = new BufferedReader(new InputStreamReader(Client.socket.getInputStream()));
			Client.connected=true;
			Client.runners = new Wait(Client,Client.socket,Client.in,Client.out);
			Client.thread2 = new Thread(Client.runners);
			
			
			
		}
		catch(java.net.UnknownHostException uhn)
		{
			JOptionPane.showMessageDialog(null, "Nieznany host");
		}
		catch(IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "Polaczenie nieudane");
		}
		
		//Instrukcja odpowiedzialna za znikanie jednego okna i pojawienie sie drugiego
		if(Client.connected)
		{
			
			this.dispose();
			Client.frame.setVisible(true);
			Client.close();
		}

	}
}