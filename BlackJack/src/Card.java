
/**
 * Represents a card in this simplified version of Blackjack.
 */

public class Card {
	private String name;
	private String imagePath;
	private int value;
	
	public Card() {
		setName("");
		setImagePath("");
		setValue(0);
	}
	
	public Card(String n, String p, int v) {
		setName(n);
		setImagePath(p);
		setValue(v);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		this.value = v;
	}
	
	public String toString() {
		String r;
		r = "Name: " + name + " imagePath = " + imagePath + " value: " + value;
		return r;
	}
}
