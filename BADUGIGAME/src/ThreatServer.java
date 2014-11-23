import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreatServer extends Thread {
	Socket client = null;
	int Mynumber;
	BufferedReader in = null;
	PrintWriter out = null;
	String line = "";
	Server server;
	Runnable table;
	boolean wait = true;

	ThreatServer(Socket socket, Server Server) {
		this.client = socket;
		this.server = Server;
		// this.table=runners;
		this.Mynumber = Server.getNumber();
		Server.setOnline();

	}

	public void run() {
		System.out.println("Connected with " + client.getRemoteSocketAddress());
		System.out.println("online: " + Server.getOnline());
		System.out.println("nop: " + Server.NoP);

		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException ioe) {
			System.out.println("Accept failed: 4444");
		}
		while (line != null) {
			try {
				line = in.readLine();
				if (String.valueOf(line.charAt(0)).equals("Q")) {
					break;
				}
				if ((Server.NoP-Server.gm)<=1) {
					out.println("G");
					break;
				}
				if (!Character.isWhitespace(line.charAt(1))) {
					System.out.println("Error: Recived wrong command "
							+ client.getRemoteSocketAddress());
					out.println("E " + "Error: Recived wrong command");
				}

				String cards = " ";
				for (int i = 0; i < Server.NoP; i++) {
					cards = (cards + Server.hand[i].mycards[0].picture + " "
							+ Server.hand[i].mycards[1].picture + " "
							+ Server.hand[i].mycards[2].picture + " "
							+ Server.hand[i].mycards[3].picture + " ");
				}
				for (int i = Server.NoP + 1; i <= 6; i++) {
					cards = (cards + 53 + " " + 53 + " " + 53 + " " + 53 + " ");
				}

				if (Character.toString(line.charAt(0)).equals("W") == false) {
					System.out.println("Received command" + line + " from "
							+ client.getRemoteSocketAddress());
				}

				switch (String.valueOf(line.charAt(0))) {

				case "1": // Bet
					Server.Betcounter[Mynumber - 1] = Server.Betcounter[Mynumber - 1] + 1;
					Server.setStarter(Mynumber);
					Server.MakeBet(line, Mynumber);
					Server.setActive();
					out.println("B" + MakeString());
					break;

				case "2":// Raise
					Server.setStarter(Mynumber);
					Server.MakeBet(line, Mynumber);
					Server.setActive();
					out.println("B" + MakeString());
					break;

				case "3":// Fold
					Server.setFold(Mynumber);
					Server.setActive();
					if (Server.checkStarter(Server.active) == true) {
						Server.NextAuction();
					}
					
					if (Server.isEnd() == true) {
						out.println("E " + Server.Endround());
						Server.thread = new Thread(Server.runners);
						Server.thread.start();
						out.println("J" + MakeString());
						out.println("K" + cards);
					} else
						
						out.println("F");
					break;

				case "4":// Check
					Server.setActive();
					if (Server.checkStarter(Mynumber) == true) {
						Server.NextAuction();
					} else {
						Server.Playeraction[Mynumber - 1] = true;
						//Server.setActive();
						if (Server.checkStarter(Server.active) == true) {
							Server.NextAuction();
						}
					}
					if (Server.isEnd() == true) {
						out.println("E " + Server.Endround());
						Server.thread = new Thread(Server.runners);
						Server.thread.start();
						try {
							sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						out.println("J" + MakeString());
						out.println("K" + cards);
						
					} else
					{
					out.println("C" + MakeString());
					}
					break;

				case "5":// Call
					Server.PlayerCall(Mynumber);
					Server.setActive();
					if (Server.checkStarter(Server.active) == true) {
						Server.NextAuction();
					}
					if (Server.isEnd() == true) {
						out.println("E " + Server.Endround());
						out.println("J" + MakeString());
						out.println("K" + cards);
					}else
					{
					out.println("C" + MakeString());
					}
					break;

				case "6":// Join
					out.println("J" + MakeString());
					out.println("K" + cards);
					break;

				case "7":// ChangeCards
					out.println("O" + ChangeCard(line));
					break;

				case "8":
					Server.allin(Mynumber);
					if (Server.isEnd() == true) {
						out.println("E " + Server.Endround());
						// out.println("K" + cards);
						Server.thread = new Thread(Server.runners);
						Server.thread.start();
						out.println("W" + MakeString());
						out.println("K" + cards);
					} else {
						Server.setActive();
						out.println("B" + MakeString());
						out.println("K" + cards);
					}
					break;

				case "W":// waiting for round

					if (Server.isEnd() == true) {
						out.println("E" + Server.Endround());
						try {
							sleep(10);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
					} else {
						out.println("W" + MakeString());
						out.println("K" + cards);
					}
					break;

				case "P":
					out.println(PossibleAction());
					break;

				default:
					System.out.println("Error: Recived wrong command from "
							+ client.getRemoteSocketAddress());
					out.println("E " + "Error: Recived wrong command");
				}
			} catch (IOException ioe) {
				System.out.println("Error: in/out operation filed");
				break;
			}
		}

		try {
			in.close();
			out.close();
			client.close();
			System.out.println("Cennect over with: "
					+ client.getRemoteSocketAddress());
			server.setNumber(Mynumber);
			server.removeOnline();

		} catch (IOException ioe) {
			System.out.println("Error");
			System.exit(-1);
		}
	}

	public String ChangeCard(String line) {
		int j = 2;
		int start = 2;
		for (int i = 1; i <= 4; i++) {
			while (!(Character.isWhitespace(line.charAt(j)))) {
				j++;

			}
			Server.hand[Mynumber - 1].returncards[i - 1] = Integer
					.parseInt(line.substring(start, j));
			j = j + 1;
			start = j;
		}
		Server.hand[Mynumber - 1].switchcard(Server.deck);
		String cards2 = " ";
		for (int i = 0; i < Server.NoP; i++) {
			cards2 = (cards2 + Server.hand[i].mycards[0].picture + " "
					+ Server.hand[i].mycards[1].picture + " "
					+ Server.hand[i].mycards[2].picture + " "
					+ Server.hand[i].mycards[3].picture + " ");
		}
		for (int i = Server.NoP + 1; i <= 6; i++) {
			cards2 = (cards2 + 53 + " " + 53 + " " + 53 + " " + 53 + " ");
		}
		System.out.println(cards2);
		return cards2;
	}

	public String MakeString() {
		String string = " ";
		for (int i = 0; i < 6; i++) {
			string = (string + Server.PlayerBet[i] + " " + Server.PlayerCash[i]
					+ " " + Server.PlayerNick[i] + " ");
		}
		string = (Mynumber + string + Server.NoP + " " + Server.Pot + " "
				+ Server.RoundNo + " " + Server.active + " ");
		
		return string;
	}

	public String PossibleAction() {
		String pa = "";
		if ((Server.PlayerBet[Mynumber - 1] == Server.MaxBet())
				&& (Server.Playerallin[Mynumber - 1] == false)) {
			pa = (pa + "Y");
		} else {
			pa = (pa + "N");
		}
		if ((Server.Betcounter[Mynumber - 1] == 0)
				&& (Server.Playerallin[Mynumber - 1] == false)
				&& (Server.PlayerCash[Mynumber - 1] > (Server.MaxBet() - Server.PlayerBet[Mynumber - 1]))) {
			pa = (pa + "Y");
		} else {
			pa = (pa + "N");
		}
		if ((Server.Betcounter[Mynumber - 1] != 0)
				&& (Server.Playerallin[Mynumber - 1] == false)
				&& (Server.PlayerCash[Mynumber - 1] > (Server.MaxBet() - Server.PlayerBet[Mynumber - 1]))) {
			pa = (pa + "Y");
		} else {
			pa = (pa + "N");
		}
		if ((Server.PlayerBet[Mynumber - 1] < Server.MaxBet())
				&& (Server.Playerallin[Mynumber - 1] == false)
				&& (Server.PlayerCash[Mynumber - 1] > (Server.MaxBet() - Server.PlayerBet[Mynumber - 1]))) {
			pa = (pa + "Y");
		} else {
			pa = (pa + "N");
		}
		if (Server.Playerallin[Mynumber - 1] == false) {
			pa = (pa + "Y");
		} else {
			pa = (pa + "N");
		}
		pa = pa + Server.MaxBet();
		return pa;
	}
}
