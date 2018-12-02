
//@formatter:on
import java.util.Comparator;

public class ScoreComparator implements Comparator<Score>
{

	private static ScoreComparator scoreComparator;
	private ScoreComparator(){

	}
	public static ScoreComparator getInstance(){
		if(scoreComparator == null){
			scoreComparator = new ScoreComparator();
		}
		return scoreComparator;
	}

	@Override
	public int compare(Score o1, Score o2)
	{
		return o2.getValue() - o1.getValue();
	}
}