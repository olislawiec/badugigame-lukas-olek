public class Card {	
	public String suit,rank;
	public int suitint,rankint,picture=53;
	public String[] suits = { "Hearts", "Spades", "Diamonds", "Clubs" };
	public String[] ranks  = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
	
	Card(int suit,int rank,int picture)
	{
		this.suitint=suit;
		this.rankint=rank;
		this.suit=suits[suit-1].substring(0,1);
		this.rank=ranks[rank-1];
		this.picture=picture;
		
	}
	
	public String getRank() 
	{
		return rank; 
	}
	public String getSuit() 
	{		
		return suit;	
	}
	public int getPicture()
	{
	return picture;
	}

}
