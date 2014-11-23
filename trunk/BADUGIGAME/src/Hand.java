


public class Hand {
public Card[] mycards;
public int[] returncards ;
Hand(Deck talia)
{
		returncards=new int[4];
		for(int k=0;k<=3;k++)
		{
			returncards[k]=0;
		}
		mycards=new Card[4];
		for(int i=0;i<=3;i++)
		{
			mycards[i]=talia.addCard();
			talia.removeDeck();			
		}
		
}


public void switchcard(Deck talia)
{
	for(int i=0;i<=3;i++)
	{
		if(returncards[i]==0)
		{
			
		}
		else
		{
			//Card help=mycards[i];
			talia.addCardonlast(mycards[i]);
			mycards[i]=talia.addCard();
			talia.removeDeck();
		}
		returncards[i]=0;
	}
}


}
