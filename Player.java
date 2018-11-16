import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

//@formatter:on
public class Player implements Serializable
{
	private String username;
	private ArrayList<Score> scores;
	
	public Player(String name)
	{
		this.username = name;
		this.scores = new ArrayList<>();
	}
	public void addScore(Integer value){
		scores.add(new Score(value, username));
		Collections.sort(scores,new ScoreComparator());
	}
	public static Player deserialize(String fileName) throws NoSuchPlayerException{
		FileInputStream fil1 = null;
		try{
			fil1 = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e){
			throw new NoSuchPlayerException("No player Exists");
		}

		ObjectInputStream in = null;
		Player p1 = null;
		try {
			in = new ObjectInputStream(fil1);
			p1 = (Player) in.readObject();
		}
		catch (Exception e){
			System.out.println("File has been corrupted");
		}
		return p1;
	}
	public void serialize(){
		String fileName = username + ".txt";
		ObjectOutputStream out = null;
		try{
			try
			{
				out = new ObjectOutputStream(new FileOutputStream(fileName));
				out.writeObject(this);
			}
			catch (IOException e){
				System.out.println("exception while serializing player " + fileName + "\n in " +  e.getMessage());
			}
			finally {
				out.close();
			}
		}
		catch (IOException e){
			System.out.println("exception while closing out in player " + fileName + "\n in " +  e.getMessage());
		}
	}
	public String getName() {
		return username;
	}
}