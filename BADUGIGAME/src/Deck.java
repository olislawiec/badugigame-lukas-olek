import java.util.ArrayList;
import java.util.Random;

public class Deck {
	public static ArrayList<Card> cards;

	Deck() {
		cards = new ArrayList<Card>();
		int rank, number, picture = 1;
		for (rank = 1; rank <= 4; rank++) {
			for (number = 1; number <= 13; number++) {
				cards.add(new Card(rank, number, picture));
				picture++;
			}
		}
		/*
		 * int random=0; Random generator = new Random(); for(int k=1;k<=3;k++){
		 * for(int i=52;i>=1;i--) {
		 * 
		 * random=generator.nextInt(i); Card cd=cards.get(random);
		 * 
		 * cards.remove(random); cards.add(cd);
		 * 
		 * } }
		 */
	}

	public String returns(int CardNo) {
		String asd = "";
		asd += cards.get(CardNo - 1).getRank();
		asd += "  ";
		asd += cards.get(CardNo - 1).getSuit();
		return asd;

	}

	void Shuffling() {
		/*
		 * int random=0; Random generator = new Random(); for(int
		 * i=52;i>=1;i++){ while(random==0){ random=generator.nextInt(i);}
		 * cards.remove(random); cards.add(new
		 * Card(random/13,random-(random/13)));
		 * 
		 * }
		 */
		int random = 0;
		Random generator = new Random();
		// for(int k=1;k<=3;k++){
		for (int i = 52; i >= 1; i--) {

			random = generator.nextInt(i);
			Card cd = cards.get(random);
			cards.add(cd);
			cards.remove(random);

		}
		// }
	}

	public void removeDeck() {
		cards.remove(0);
	}

	public Card addCard() {
		return cards.get(0);
	}

	public void addCardonlast(Card card) {
		cards.add(card);
	}
}
