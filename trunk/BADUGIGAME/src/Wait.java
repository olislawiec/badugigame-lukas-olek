import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;


public class Wait implements Runnable{
	
	Gui window;
	 Socket socket = null;
	 BufferedReader in = null;
	 PrintWriter out = null;
	 String w;

	Wait(Gui window,Socket socket,BufferedReader in,PrintWriter out)
	{
		this.window=window;
		this.socket=socket;
		this.in=in;
		this.out=out;
	}
	public void run() {
		
		while (window.active != window.Mynumber) {
			out.println("W ");
			try {
				w = in.readLine();
				switch (String.valueOf(w.charAt(0))) {
				
				case "G":
					System.exit(0);
				window.frame.dispose();
					break;
				
				case "W":
				
				window.setEverything(w);
				
				w = in.readLine();
				
				window.setCards(w);
				break;
				case "E":
					JOptionPane.showMessageDialog(window.frame,
							"Winner is: " + w.substring(2));
					
					break;
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		out.println("P ");
		try {
			w=in.readLine();
			System.out.print(w);
			window.PossibleAction(w);
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
		
	}


