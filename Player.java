import java.util.ArrayList;

//@formatter:on
public class Player
{
	private String Username;
	private ArrayList<Integer> Scores;
	
	public Player(String name)
	{
		this.Username = name;
		this.Scores = new ArrayList<Integer>();
	}
}