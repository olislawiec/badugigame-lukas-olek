
import static java.lang.Math.*;

import javax.swing.JOptionPane;

//Klasa do wyliczania mocy kart, które gracz ma w rece
public class PowerOfCards
{
        public PowerOfCards() {}
        int[] countPower(String[] card)
        {
                int[] power=null;
                char[] figure = {card[0].charAt(0), card[1].charAt(0), card[2].charAt(0), card[3].charAt(0), 'T'};
                try
                {
                        int[] number={Integer.parseInt(card[0].substring(1)), Integer.parseInt(card[1].substring(1)), Integer.parseInt(card[2].substring(1)), Integer.parseInt(card[3].substring(1)), 0};
                        for(int i=0; i<3; i++)
                        {
                                for(int j=i+1; j<4; j++)
                                {
                                        if(number[i]>number[j])
                                        {
                                                number[4]=number[i];
                                                figure[4]=figure[i];
                                                number[i]=number[j];
                                                figure[i]=figure[j];
                                                number[j]=number[4];
                                                figure[j]=figure[4];
                                        }
                                }
                        }
                        int[] check=checkFigures(figure);
                        power = new int[check[0]+2];
                        power[0]=check[0];
                        int j=1;
                        if(number[0]==number[1] && number[1]==number[2] && number[2]==number[3]) power[0]=1;
                        else
                        {
                                for(int i=3; i>0; i--)
                                {
                                        if(check[i+1]<=4 && number[i]!=number[i-1])
                                        {
                                                //System.out.println(number[i]);
                                                power[j]=number[i];
                                                j++;
                                        }
                                        else if(check[i+1]>4)
                                        {
                                                int k=i-1;
                                                while(k>=0 && check[k+1]!=check[i+1]-4) k--;
                                                if((k>0 && number[k]==number[k-1]) || (k>=0 && number[k]==number[k+1] && check[k+2]<=4))
                                                {
                                                        //System.out.println(number[i]);
                                                        check[k]+=4;
                                                        power[j]=number[i];
                                                        j++;
                                                }
                                        }
                                        else if(number[i]==number[i-1] && check[i+1]<=4 && check[i]<=4) power[0]--;
                                }
                        }
                        //System.out.println(number[0]+"\n");
                        power[j]=number[0];
                }
                catch (NumberFormatException nfe) {
        			JOptionPane.showMessageDialog(null,
        					"Podana liczba nie jest liczba naturalna");
        		}
                return power;
        }
        private int[] checkFigures(char[] symbol)
        {
                char[] allsymbol={'C', 'D', 'H', 'S'};
                int check[]=new int[5], eliminate;
                check[0]=4;
                for(int i=0; i<4; i++)
                {
                        eliminate=0;
                        for(int j=0; j<4; j++)
                        {
                                if(allsymbol[i]==symbol[j])
                                {
                                        if(eliminate==0) 
                                        {
                                                eliminate++;
                                                check[j+1]=i+1;
                                        }
                                        else 
                                        {
                                                check[j+1]=(i+1)+4;
                                                check[0]--;
                                        }
                                }
                        }
                }
                return check;
        }
        public int resultToCompare(String[] card)
        {
                int result=0, power[];
                power=countPower(card);
                for(int i=0; i<4-power[0]; i++) result+=20*Math.pow(10, (3-i)*2);
                for(int i=0; i<power[0]; i++) result+=power[power[0]-i]*pow(10, i*2);
                return result;
        }
}