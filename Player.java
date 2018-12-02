
//@formatter:on
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Player class contains name, and score list for the user.
 */
public class Player implements Serializable
{
	private static final long serialVersionUID = 202L;
	private String username;
	private ArrayList<Score> scores;

	/**
	 * Initializes name with given parameter, and creates a new scoreList
	 * @param name
	 */
	public Player(String name)
	{
		this.username = name;
		this.scores = new ArrayList<>();
	}

	/**
	 * Adds a new Score to the scores ArrayList.
	 * @param value
	 */
	public void addScore(Integer value)
	{
		scores.add(new Score(value, username));
		Collections.sort(scores, ScoreComparator.getInstance());
	}

	/**
	 * Deserializes the given filename into objects of this class.
	 * @param fileName filename that contains the object to be deserialized
	 * @return player object formed by deserializing the filename
	 * @throws NoSuchPlayerException
	 */
	public static Player deserialize(String fileName) throws NoSuchPlayerException
	{
		FileInputStream fil1 = null;
		try
		{
			fil1 = new FileInputStream("PlayerFiles/" + fileName);
		}
		catch (FileNotFoundException e)
		{
			throw new NoSuchPlayerException("No player Exists");
		}
		ObjectInputStream in = null;
		Player p1 = null;
		try
		{
			in = new ObjectInputStream(fil1);
			p1 = (Player) in.readObject();
		}
		catch (Exception e)
		{
			System.out.println("File has been corrupted");
		}
		return p1;
	}

	/**
	 * Serializes the object to a file with name: username + ".txt"
	 */
	public void serialize()
	{
		String fileName = username + ".txt";
		ObjectOutputStream out = null;
		try
		{
			try
			{
				out = new ObjectOutputStream(new FileOutputStream("PlayerFiles/" + fileName));
				out.writeObject(this);
			}
			catch (IOException e)
			{
				System.out.println("exception while serializing player " + fileName + "\n in " + e.getMessage());
			}
			finally
			{
				out.close();
			}
		}
		catch (IOException e)
		{
			System.out.println("exception while closing out in player " + fileName + "\n in " + e.getMessage());
		}
	}

	/**
	 * standard getter
	 * @return player name
	 */
	public String getName()
	{
		return username;
	}


	/**
	 * standard getter
	 * @return ArrayList of scores	 */
	public ArrayList<Score> getScores()
	{
		return scores;
	}
	

}