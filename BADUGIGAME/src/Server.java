import java.util.Random;

import javax.swing.JOptionPane;

public class Server {
	static int online = 0;
	static int NoP = 0, NoB = 0,help=1,wd=7,gm=0;
	static int PortNumber = 0;
	static int[] Mynumber = { 0, 0, 0, 0, 0, 0 };
	Gui gui = null;
	static OknoPolacz window = new OknoPolacz();
	static Server server;
	static Deck deck;
	static WhoWinDeal winner;
	String[] tab;
	static int StartCash;
	static int Pot=0;
	static int RoundNo, AuctionNo = 1, fold = 0, allin = 0, cc = 0,c=1,
			winnerint = 0;
	static int SmallBlind;
	static int BigBlind;
	static int DealerButton;
	static int BB = 4;
	static int SB = 2;
	static int starter = 0;
	static String[] PlayerNick = { "", "", "", "", "", "" };
	static String[][] PlayerCards = new String[6][4];
	static Integer[] PlayerCash = { 0, 0, 0, 0, 0, 0 };
	static Integer[] PlayerBet = { 0, 0, 0, 0, 0, 0 };
	static Integer[] Betcounter = { 0, 0, 0, 0, 0, 0 };
	static Boolean[] Playerfold = { false, false, false, false, false, false };
	static Boolean[] Playerallin = { false, false, false, false, false, false };
	static Boolean[] Playeraction = { false, false, false, false, false, false };
	static Boolean[] GameOver = { false, false, false, false, false, false };
	static Hand[] hand;
	static boolean wait = true;
	static int active = 0;
	static Runnable runners;
	static Thread thread;
	static String[] args2;

	public static void main(String[] args) {
		try {
			args2=args;
			RoundNo=0;
			server = new Server();
			PortNumber = Integer.parseInt(args[0]);
			NoP = Integer.parseInt(args[1]);
			NoB = Integer.parseInt(args[2]);
			StartCash = Integer.parseInt(args[3]);
			BB = Integer.parseInt(args[10]);
			SB = Integer.parseInt(args[11]);
			hand = new Hand[NoP];
			for (int i = 4; i <= 9; i++) {
				PlayerNick[i - 4] = args[i];
			}
				for (int j = 0; j < 6; j++) {
					PlayerCash[j] = StartCash;
				}
			

		}

		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"The number isn't natural");
		}

		Runnable runners2;
		runners2 = new Game(server, PortNumber);
		Thread thread2 = new Thread(runners2);
		thread2.start();

		System.out.println(NoP + " " + PortNumber + " " + NoB);
		runners = new Starter(server);
		thread = new Thread(runners);
		thread.start();


	}

	public void setOnline() {

		online++;
	}

	public static int getOnline() {
		return online;
	}

	public void removeOnline() {
		online--;
	}

	public int getNumber() {
		for (int i = 0; i <= 5; i++) {
			if (Mynumber[i] == 0) {
				Mynumber[i] = 1;
				return i + 1;
			}
		}
		return -3;
	}

	public void setNumber(int i) {
		Mynumber[i - 1] = 0;
	}

	public int getNoP() {
		return NoP;
	}

	public static void DealerButton() {
		Random random = new Random();
		if (RoundNo == 1) {
			DealerButton = random.nextInt(NoP) + 1;
		} else {
			if (DealerButton < NoP) {
				DealerButton += 1;
			} else {
				DealerButton = 1;
			}
		}
		PlayerNick[DealerButton - 1] = (PlayerNick[DealerButton - 1] + "_DB");
	}

	public static void SmallBlind() {
		if (NoP == 2) {
			SmallBlind = DealerButton;
		} else {
			if (DealerButton < NoP) {

				SmallBlind = DealerButton + 1;
			} else
				SmallBlind = 1;

		}
		PlayerNick[SmallBlind - 1] = (PlayerNick[SmallBlind - 1] + "_SB");
	}

	public static void BigBlind() {
		if (SmallBlind < NoP) {
			BigBlind = SmallBlind + 1;
		} else {
			BigBlind = 1;
		}
		PlayerNick[BigBlind - 1] = (PlayerNick[BigBlind - 1] + "_BB");
	}

	public static void Active() {
		if (AuctionNo == 1) {
			if (BigBlind < NoP) {
				active = BigBlind + 1;
			} else {
				active = 1;
			}
		} else
			active = SmallBlind;
	}

	public static void setActive() {
		if(online==1)
		{
			System.exit(-1);
		}
		if (active < NoP) {
			active = active + 1;
		} else {
			active = 1;
		}
		if ((Playerfold[active - 1] == true)
				|| (Playerallin[active - 1] == true)) {
			setActive();
		}
	}

	public static void setStarter(int mynumber) {
		starter = mynumber;
	}

	public static boolean checkStarter(int mynumber) {
		if (mynumber != BigBlind) {
			if (mynumber == starter) {

				return true;
			} else {

			}
		} else if (Playeraction[mynumber - 1] != false) {

			return true;
		}

		return false;
	}

	public static void NextRound(int k) {
		while(wd<NoP)
		{
			
		}
		PlayerCash[k] = PlayerCash[k] + Pot;
		Pot = 0;
		cc = 0;
		for (int i = 0; i < NoP; i++) {
			PlayerBet[i] = 0;
			Betcounter[i] = 0;
			Playerfold[i] = false;
			Playeraction[i] = false;
		}
		AuctionNo = 1;
		fold = 0;
		allin=0;
		
		wd=0;
	}

	public static void NextAuction() {
		AuctionNo++;

		for (int i = 0; i < 6; i++) {
			Playeraction[i] = false;
		}
		//System.out.println("Nr licytacji: " + AuctionNo);
		Active();

	}

	public static void Pot() {
		Pot = 0;
		for (int i = 0; i < NoP; i++) {
			Pot = Pot + PlayerBet[i];
		}
	}

	public static void PlayerCall(int Mynumber) {
		int m = MaxBet();
		int r = MaxBet() - PlayerBet[Mynumber - 1];
		PlayerBet[Mynumber - 1] = m;
		PlayerCash[Mynumber - 1] = PlayerCash[Mynumber - 1] - r;
		Pot();
	}

	public static int MaxBet() {
		int max = 0;
		for (int i = 0; i < NoP; i++) {
			if (max < PlayerBet[i])
				max = PlayerBet[i];

		}
		return max;
	}

	public static void setFold(int mynumber) {
		Playerfold[mynumber - 1] = true;
		fold++;
	}

	public static void MakeBet(String l, int Mynumber) {
		int b = Integer.parseInt(l.substring(2));
		PlayerBet[Mynumber - 1] = PlayerBet[Mynumber - 1] + b;
		PlayerCash[Mynumber - 1] = PlayerCash[Mynumber - 1] - b;
		Pot();
	}

	public static boolean isEnd() {

		if ((fold == (NoP - 1)) || (AuctionNo > 4) ||(allin==NoP)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String Endround() { // wygrana przez fold reszty
		String win = "";
		int i = 0;

		if (fold == (NoP - 1)) {

			while (Playerfold[i] == true) {
				i++;
			}
			win = (win + PlayerNick[i]);
			if (cc == 0) {
				cc++;
				winnerint = i;

			}
			return win;
		} else {
			for (i = 0; i < NoP; i++) {

				MakeString(i);

			}
			winner = new WhoWinDeal();
			return PlayerNick[winner.whoWin(NoP, PlayerCards)];

		}
	}

	public static void MakeString(int i) {
		for (int k = 0; k < 4; k++) {
			PlayerCards[i][k] = ((hand[i].mycards[k].suit) + (hand[i].mycards[k].rankint));
			System.out.println(PlayerCards[i][k]);
		}
	}
	
public static void allin(int mynumber) {
		Playerallin[mynumber - 1] = true;
		PlayerBet[mynumber - 1] = PlayerBet[mynumber - 1]
				+ PlayerCash[mynumber - 1];
		PlayerCash[mynumber - 1] = 0;
		allin++;
		Pot();
	}

	public static void Game() {
		RoundNo++;
		PlayerCash[winnerint] = PlayerCash[winnerint] + Pot;
		Pot = 0;
		cc = 0;
		for (int i = 0; i < NoP; i++) {
			PlayerBet[i] = 0;
			Betcounter[i] = 0;
			Playerfold[i] = false;
			Playeraction[i] = false;
		}
		for(int i=0;i<NoP;i++)
		{
			if(PlayerCash[i]<=0)
			{
				GameOver[i]=true;
				Playerfold[i] = true;
				Playeraction[i] = true;
				gm++;
			}
		}
		AuctionNo = 1;
		fold = 0;
		allin=0;
		wd=0;
		//NextRound(winnerint);
		winnerint=0;
		for (int i = 4; i <= 9; i++) {
			PlayerNick[i - 4] = args2[i];
		}
		deck = new Deck();
		deck.Shuffling();
		DealerButton();
		SmallBlind();
		BigBlind();
		Active();
		starter = BigBlind;
		System.out.println("DB: " + DealerButton + " SB: " +SmallBlind
				+ " BB: " + BigBlind + " Active " + active);

		PlayerBet[BigBlind - 1] = BB;
		PlayerCash[BigBlind - 1] = PlayerCash[BigBlind - 1] -BB;
		PlayerBet[SmallBlind - 1] = SB;
		PlayerCash[SmallBlind - 1] = PlayerCash[SmallBlind - 1] - SB;
		if(PlayerCash[BigBlind - 1]==0)
		{
			allin(BigBlind);	
		}
		if(PlayerCash[SmallBlind - 1]==0)
		{
			allin(SmallBlind);	
		}
		Pot();
		for (int j = 0; j < NoP; j++) {
			hand[j] = new Hand(deck);

} 
		
		
		}
}
