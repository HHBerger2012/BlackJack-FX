
/**
 * Represents a player in this simplified version of the
 * "21" card game.
 */

public class Player {

    /** The name of the player (used for printing purposes). */
    private String name;
    private Hand hand;
   
    
    public Player (String n, Hand h) {
        name = n;
        hand = h;
    }
    
   
    /**
     * Return the value of the player's hand. 
     */
    
    public int getEndScore()
    {
    	return (21)-this.getScore();
    }
    public Hand getHand()
    {
    	return hand;
    }
    public int getScore() {
        int v = 0;
        v = hand.getTotalValue();
        return v;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
