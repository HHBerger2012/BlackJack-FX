/**Henry Berger and Emannuel Enubelele
*Assignment 4- BlackJack
*October 31, 2017
* This is a simplified version of a common card game, "21"
 * for Assignment 4. 
 *
 * The players take turns requesting cards, trying to get
 * as close to 21 as possible, but not going over 21. A player
 * may stand (ask for no more cards). Once a player has passed,
 * he or she cannot later ask for another card. When all three
 * players have passed, the game ends.
 *
 * The winner is the player who has come closest to 21 without
 * exceeding it. In the case of a tie, or if everyone goes over
 * 21, no one wins.
 * 
 * Per the assignment, we assume exactly three players. The game 
 * is only played once.
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Game extends Application 
{

	// An ArrayList to store the 4 Hand objects 
	static Hand p1Hand = new Hand();
	static Hand p2Hand = new Hand();
	static Hand p3Hand = new Hand();
	static Hand dealerHand = new Hand(); 
	
	//An ArrayList to store the 4 Player objects
	static ArrayList<Player> players;
	static Player dealer = new Player("Dealer", dealerHand);
	static Player player1 = new Player("Player 1",p1Hand);
	static Player player2 = new Player("Player 2",p2Hand);
	static Player player3 = new Player("Player 3",p3Hand);
	
	//Variable to decide whose turn it is
	static int turn=1;
	
	//Stage, Pane, and Scene for the Player's Hit or Stay choice
	static Stage playerStage = new Stage();
	static Pane playerPane;
	static Scene playerScene;
	
	//Stage, Pane, and Scene for the final hands
	static Stage lastStage = new Stage();
	static Pane lastPane;
	static Scene lastScene;
	
	//Variable for the statement of who won
	static Text result;
	
	//Variables for how large the Card images are
	static int playerCardX;
	static int playerCardY;
	
	//Stage, Pane, and Scene for the end game announcement
	static Stage endStage = new Stage();
	static Pane endPane;
	static Scene endScene;
	
	//GridPane for the main game board
	static final GridPane pane = new GridPane();

	//Variables for how big the picture will be
	static int picX=90;
	static int picY=110;
	
	// An ArrayList to store the deck of cards
	static ArrayList<Card> cardDeck;
	
	public void start(Stage primaryStage) 
	{
		//Adding the Player objects to the Player ArrayList
		players = new ArrayList<Player>();
		players.add(dealer);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		//Initiallizing the Card ArrayList
		cardDeck = new ArrayList<Card>();
		initDeck(cardDeck);
		
		//Defining the Stylistic means for the main game Pane
		pane.setHgap(50);
		pane.setVgap(50);
		pane.setGridLinesVisible(false);
		pane.setStyle("-fx-background-color: transparent");
		
		//Text objects for the Players' names on the main game board
		Text dName = new Text(dealer.getName());
		dName.setFill(Color.WHITE);
		dName.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		
		Text p1Name = new Text(player1.getName());
		p1Name.setRotate(270);
		p1Name.setFill(Color.WHITE);
		p1Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));

		Text p2Name = new Text(player2.getName());
		p2Name.setFill(Color.WHITE);
		p2Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));

		Text p3Name = new Text(player3.getName());
		p3Name.setRotate(90);
		p3Name.setFill(Color.WHITE);
		p3Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		
		//Gives each player their initial two cards
		giveCards();
		
		//Adds the Players' names to the main game board
		pane.add(dName, 4, 0);
		pane.add(p1Name, 9, 3);
		pane.add(p2Name, 4, 6);
		pane.add(p3Name, 0, 3);
		
		//Makes main game Scene
		Scene scene = new Scene(pane, 960, 750);
		scene.setFill(Color.GREEN);

		//Set main Stage variables
		primaryStage.setTitle("Blackjack"); // Set the stage title
		primaryStage.setScene(scene); 
		primaryStage.setResizable(true);
		
		//Choose which Player's turn it is
		chooseTurn();
		
		//Shows the game Stage
		primaryStage.show(); 
	}
	
	public static void chooseTurn()
	{
		//Chooses which Player's turn it is
		if (turn==1)
		{
			doGame(player1);
		}
		else if (turn==2)
		{
			doGame(player2);
		}
		else if (turn==3)
		{
			doGame(player3);
		}
		else if (turn==4)
		{
			doDealer(dealer);
		}
	}
	
	public static void endGame()
	{
		//Defines the final game Pane and Scene
		lastPane = new Pane();
		lastPane.setStyle("-fx-background-color: transparent");
		
		lastScene = new Scene(lastPane, 960,750);
		lastScene.setFill(Color.GREEN);
		
		//Defines each Player's name and final cards
		Text dName = new Text(dealer.getName());
		dName.setFill(Color.WHITE);
		dName.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		dName.setLayoutX(460);
		dName.setLayoutY(15);
		int dealerX = 415;
		int dealerY = 25;
		
		for (int i=0;i<dealer.getHand().getHand().size();i++)
		{
			try{
				Image pic = new Image(new FileInputStream(dealer.getHand().getHand().get(i).getImagePath()),picX,picY,false,false);
				ImageView cardImage = new ImageView();
				cardImage.setImage(pic);
			 	cardImage.setLayoutX(dealerX);
				cardImage.setLayoutY(dealerY);
				lastPane.getChildren().add(cardImage);
				}
				catch (FileNotFoundException ex) {	
					System.out.println(ex.getMessage());
				}
			dealerX+=25;
		}
		
		Text p1Name = new Text(player1.getName());
		p1Name.setRotate(270);
		p1Name.setFill(Color.WHITE);
		p1Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		p1Name.setLayoutX(920);
		p1Name.setLayoutY(375);
		int p1X = 815;
		int p1Y = 335;
		
		for (int i=0;i<player1.getHand().getHand().size();i++)
		{
			try{
				Image pic = new Image(new FileInputStream(player1.getHand().getHand().get(i).getImagePath()),picX,picY,false,false);
				ImageView cardImage = new ImageView();
				cardImage.setImage(pic);
			 	cardImage.setLayoutX(p1X);
				cardImage.setLayoutY(p1Y);
				cardImage.setRotate(270);
				lastPane.getChildren().add(cardImage);
				}
				catch (FileNotFoundException ex) {	
					System.out.println(ex.getMessage());
				}
			p1Y-=25;
		}

		Text p2Name = new Text(player2.getName());
		p2Name.setFill(Color.WHITE);
		p2Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		p2Name.setLayoutX(460);
		p2Name.setLayoutY(735);
		int p2X = 425;
		int p2Y = 600;
		
		for (int i=0;i<player2.getHand().getHand().size();i++)
		{
			try{
				Image pic = new Image(new FileInputStream(player2.getHand().getHand().get(i).getImagePath()),picX,picY,false,false);
				ImageView cardImage = new ImageView();
				cardImage.setImage(pic);
			 	cardImage.setLayoutX(p2X);
				cardImage.setLayoutY(p2Y);
				lastPane.getChildren().add(cardImage);
				}
				catch (FileNotFoundException ex) {	
					System.out.println(ex.getMessage());
				}
			p2X+=25;
		}

		Text p3Name = new Text(player3.getName());
		p3Name.setRotate(90);
		p3Name.setFill(Color.WHITE);
		p3Name.setFont(Font.font("Verdana", FontWeight.BOLD,12));
		p3Name.setLayoutX(0);
		p3Name.setLayoutY(375);
		int p3X = 75;
		int p3Y = 315;
		
		for (int i=0;i<player3.getHand().getHand().size();i++)
		{
			try{
				Image pic = new Image(new FileInputStream(player3.getHand().getHand().get(i).getImagePath()),picX,picY,false,false);
				ImageView cardImage = new ImageView();
				cardImage.setImage(pic);
			 	cardImage.setLayoutX(p3X);
				cardImage.setLayoutY(p3Y);
				cardImage.setRotate(90);
				lastPane.getChildren().add(cardImage);
				}
				catch (FileNotFoundException ex) {	
					System.out.println(ex.getMessage());
				}
			p3Y+=25;
		}
		
		//Adds all the Player's names and cards to the final game Pane
		lastPane.getChildren().addAll(dName,p1Name,p2Name,p3Name);
		
		//Sets the final Stage's variables
		lastStage.setTitle("BlackJack");
		lastStage.setResizable(false);
		lastStage.setScene(lastScene);
		lastStage.setAlwaysOnTop(false);
		lastStage.show();	
	}
	
	public static void doEnding()
	{
		//Closes the Player's choice Stage and defines the End Game Options
		playerStage.close();
		endPane = new Pane();
		endPane.setStyle("-fx-background-color: transparent");
		
		endScene = new Scene(endPane, 400,200);
		endScene.setFill(Color.GREEN);
		
		//Creates an ArrayList for the player(s) who did not bust
		ArrayList<Player>endPlayers=new ArrayList<Player>();
		
		//Creates an ArrayList for the player(s) with the best final score
		ArrayList<Player>winners = new ArrayList<Player>();
		
		//Adds players who did not bust to the ArrayList
		for (Player p: players)
		{
			if (p.getEndScore()>=0)
			{
				endPlayers.add(p);
			}
		}
		
		//If everyone busted
		if (endPlayers.size()==0)
		{
			result = new Text("No One Wins! All Busted");
			result.setFill(Color.WHITE);
		}
		
		//Calculates the best score and adds the player(s) with that score to the winners ArrayList
		else
		{
			int bestScore = endPlayers.get(0).getEndScore();
			
			for (int i=1;i<endPlayers.size();i++)
			{
				if (endPlayers.get(i).getEndScore()<=bestScore)
				{
					bestScore = endPlayers.get(i).getEndScore();
				}
			}
			
			for (Player p: endPlayers)
			{
				if (p.getEndScore()==bestScore)
				{
					winners.add(p);
				}
			}
			
			//if it is a tie
			if (winners.size()>1)
			{
				if (winners.get(0).getEndScore()==winners.get(1).getEndScore())
				{
					result = new Text("No One Wins! It was a Tie");
					result.setFill(Color.WHITE);
				}
			}
			
			//Prints name and score of winner with outright best score
			else
			{
				result = new Text(winners.get(0).getName()+" Wins with a score of "+winners.get(0).getScore());
				result.setFill(Color.WHITE);
				
			}
		}	
		
		//Sets the stylistic means for the results and adds it to the Pane
		result.setFont(Font.font("Verdana", FontWeight.BOLD,20));
		result.setLayoutX(25);
		result.setLayoutY(100);
		endPane.getChildren().add(result);
		
		//Sets the Stage variables
		endStage.setTitle("Game Over!");
		endStage.setResizable(false);
		endStage.setScene(endScene);
		endStage.setAlwaysOnTop(true);
		endStage.show();	
		endGame();
	}
	
	public static void doDealer(Player d)
	{
		//Forces dealer to draw another card if his score is less than 16
		if (d.getScore()<16)
		{
			Card card = cardDeck.get(0);
			d.getHand().addCard(card);
			cardDeck.remove(0);
			doDealer(dealer);
		}
		else
		{
			doEnding();
		}
	}
	
	public static void giveCards()
	{
		//shuffles Card deck then gives each Player its first 2 cards
		Collections.shuffle(cardDeck);
		givePlayer1Cards();
		givePlayer2Cards();
		givePlayer3Cards();
		giveDealerCards();
	}

	public static void doGame(Player p)
	{
		//X and Y coordinate for new Card when a Player Hits
		playerCardX=25;
		playerCardY=75;
		
		//Initializes the Player choice Pane and Scene
		playerPane=new Pane();
		playerPane.setStyle("-fx-background-color: transparent");

		playerScene = new Scene (playerPane,450,300);
		playerScene.setFill(Color.GREEN);

		//Sets a Text Object with the respective Player's name
		Text name = new Text(p.getName());
		name.setLayoutX(25);
		name.setLayoutY(50);
		name.setFill(Color.WHITE);
		name.setFont(Font.font("Verdana", FontWeight.BOLD,20));
		
		String s = Integer.toString(p.getScore());
		
		//Sets a Text Object with the respective Player's score
		Text score = new Text(s);
		score.setLayoutX(325);
		score.setLayoutY(125);
		score.setFill(Color.WHITE);
		score.setFont(Font.font("Verdana", FontWeight.BOLD,40));
		
		//Adds Player's current cards to the Pane
		try{
		Image pic = new Image(new FileInputStream(p.getHand().getHand().get(0).getImagePath()),picX,picY,false,false);
		ImageView cardImage = new ImageView();
		cardImage.setImage(pic);
		cardImage.setLayoutX(25);
		cardImage.setLayoutY(75);
		playerPane.getChildren().add(cardImage);
		Image pic2 = new Image(new FileInputStream(p.getHand().getHand().get(1).getImagePath()),picX,picY,false,false);
		ImageView cardImage2 = new ImageView();
		cardImage2.setImage(pic2);
		cardImage2.setLayoutX(125);
		cardImage2.setLayoutY(75);
		playerPane.getChildren().add(cardImage2);
		}
		catch (FileNotFoundException e) {	
			System.out.println(e.getMessage());
		}
		
		//Initializes Button for when a Player chooses to Stay
		Button stand = new Button("Stay");
		stand.setLayoutX(25);
		stand.setLayoutY(250);
		
		//Sets an action to happen when the user pressed the Stay Button, adds to the turn count and goes back to Choose whose turn it is
		stand.setOnMousePressed(e -> 
		{
			turn=turn+1;
			chooseTurn();
		});
		
		//Initializes Button for when a Player chooses to Hit
		Button hit = new Button("Hit");
		
		//Sets an action for when the user Hits
		hit.setOnMousePressed(e -> 
		{
			//Only can hit if score is less than 21
			if (p.getScore()<21)
			{
			try{
			Card card = cardDeck.get(0);
			
			//Sets an Ace's value to 1 if the addition of an 11 would send the player over 21
			if (card.getValue()==11)
			{
				if (p.getScore()+11>21)
				{
					card.setValue(1);
				}
			}
			playerCardX+=15;
			playerCardY+=15;
			p.getHand().addCard(card);
			score.textProperty().bind(new SimpleIntegerProperty(p.getScore()).asString());
			Image pic = new Image(new FileInputStream(card.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage = new ImageView();
			cardImage.setImage(pic);
		 	cardImage.setLayoutX(playerCardX);
			cardImage.setLayoutY(playerCardY);
			playerPane.getChildren().add(cardImage);
			}
			catch (FileNotFoundException ex) {	
				System.out.println(ex.getMessage());
			}
			}
			else
			{
				//adds delay for visual aid
				try {
					Thread.sleep(750);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				turn=turn+1;
				chooseTurn();
			}
		});
		
		//adds the buttons, name, and score to the Pane
		hit.setLayoutX(150);
		hit.setLayoutY(250);
		playerPane.getChildren().addAll(stand,hit,name,score);
		
		//sets the Player Stage's options
		playerStage.setTitle("Choose Action");
		playerStage.setResizable(false);
		playerStage.setScene(playerScene);
		playerStage.setAlwaysOnTop(true);
		playerStage.show();		
	}

	public static void givePlayer1Cards()
	{
		//Gives player 1 his first 2 cards
		try { 
			Card player1Card1 = cardDeck.get(0);
			player1.getHand().addCard(player1Card1);
			Image pic = new Image(new FileInputStream(player1Card1.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage = new ImageView();
			cardImage.setImage(pic);
			pane.add(cardImage,8,2);
			cardImage.setRotate(270);
			Card player1Card2 = cardDeck.get(0);
			player1.getHand().addCard(player1Card2);
			Image pic2 = new Image(new FileInputStream(player1Card2.getImagePath()),picX,picY,false,false);			
			cardDeck.remove(0);
			ImageView cardImage2 = new ImageView();
			cardImage2.setImage(pic2);
			pane.add(cardImage2,8,4);
			cardImage2.setRotate(270);
		}
		catch (FileNotFoundException e) {	
			System.out.println(e.getMessage());
		}
	}
	
	public static void givePlayer2Cards()
	{
		//Gives player 2 his first 2 cards
		try { 
			Card player2Card1 = cardDeck.get(0);
			player2.getHand().addCard(player2Card1);
			Image pic = new Image(new FileInputStream(player2Card1.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage = new ImageView();
			cardImage.setImage(pic);
			pane.add(cardImage,3,5);
			Card player2Card2 = cardDeck.get(0);
			player2.getHand().addCard(player2Card2);
			Image pic2 = new Image(new FileInputStream(player2Card2.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage2 = new ImageView();
			cardImage2.setImage(pic2);
			pane.add(cardImage2,5,5);
		}
		catch (FileNotFoundException e) {	
			System.out.println(e.getMessage());
		}
	}
	
	public static void givePlayer3Cards()
	{
		//Gives player 3 his first 2 cards
		try { 
			Card player3Card1 = cardDeck.get(0);
			player3.getHand().addCard(player3Card1);
			Image pic = new Image(new FileInputStream(player3Card1.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage = new ImageView();
			cardImage.setImage(pic);
			pane.add(cardImage,1,2);
			cardImage.setRotate(90);
			Card player3Card2 = cardDeck.get(0);
			player3.getHand().addCard(player3Card2);
			Image pic2 = new Image(new FileInputStream(player3Card2.getImagePath()),picX,picY,false,false);
			cardDeck.remove(0);
			ImageView cardImage2 = new ImageView();
			cardImage2.setImage(pic2);
			pane.add(cardImage2,1,4);
			cardImage2.setRotate(90);
		}
		catch (FileNotFoundException e) {	
			System.out.println(e.getMessage());
		}
	}
	
	public static void giveDealerCards()
	{
		//Gives the Dealer his first 2 cards
	try { 
		Card dealerCard1 = cardDeck.get(0);
		dealerHand.getHand().add(dealerCard1);
		Image pic = new Image(new FileInputStream(dealerCard1.getImagePath()),picX,picY,false,false);
		cardDeck.remove(0);
		ImageView cardImage = new ImageView();
		cardImage.setImage(pic);
		pane.add(cardImage,3,1);
		Card dealerCard2 = cardDeck.get(0);
		dealerHand.getHand().add(dealerCard2);
		Image pic2 = new Image(new FileInputStream(dealerCard2.getImagePath()),picX,picY,false,false);
		cardDeck.remove(0);
		ImageView cardImage2 = new ImageView();
		cardImage2.setImage(pic2);
		pane.add(cardImage2,5,1);
		Image pic3 = new Image(new FileInputStream("images/backCard.jpg"),picX,picY,false,false);
		ImageView cardImage3 = new ImageView();
		cardImage3.setImage(pic3);
		pane.add(cardImage3, 5, 1);
		
	}
	catch (FileNotFoundException e) {	
		System.out.println(e.getMessage());
	}
	}
	
	public static void initDeck(ArrayList<Card> d) 
	{
		
		String[] suits = new String[] {"C", "D", "H", "S"};
		String[] faces = new String[] {"J", "Q", "K", "A"};
		
		for (String s: suits) {           
	        for(int i = 2; i < 11; i++) {
	        		// Create and add a new card for each of the numbered cards
	        		String name = i + s; 
	        		String imagePath = "images/" + name + ".png";
	        		Card c = new Card(name, imagePath, i);
	        		d.add(c);
	        }
	    } 
		
		for (String s:suits) {           
	        for(String f:faces) {
	        		// Create and add a new card for each of the face cards
	        		String name = f + s; 
	        		String imagePath = "images/" + name + ".png";
	        		
	        		if( f.equals("A") )
	        			d.add(new Card(name, imagePath, 11));
	        		else	
	        			d.add(new Card(name, imagePath, 10));
	        }
	    } 
	}
	
  public static void main(String[] args) 
  {
      launch(args);
  }
}