

public class WhoWinDeal
{
        PowerOfCards poc;
        
        WhoWinDeal()
        {
                
        }
        int whoWin(int numberOfPlayers, String[][] cards)
        {
                int whoCanWin[], bestResult=20202020, temp, quantityOfWinners=0;
                whoCanWin = new int[numberOfPlayers];
                for(int i=0; i<numberOfPlayers; i++)
                {
                       
                        poc = new PowerOfCards();
                        temp=poc.resultToCompare(cards[i]);
                        if(temp<bestResult)
                        {
                                quantityOfWinners=0;
                                bestResult=temp;
                                whoCanWin[0]=i;
                        }
                        else if(temp==bestResult)
                        {
                                quantityOfWinners++;
                                bestResult=temp;
                                whoCanWin[quantityOfWinners]=i;
                        }
                }
                if(quantityOfWinners==0) return whoCanWin[0];
                else
                {
                        temp=0;
                        for(int i=0; i<quantityOfWinners; i++)
                        {
                                temp+=whoCanWin[i]*Math.pow(10, i);
                        }
                        return temp;
                }
        }
}