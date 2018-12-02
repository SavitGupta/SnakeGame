
//@formatter:on
import java.util.Comparator;

/**
 * Singleton Comparator for comparing 2 Score object on the basis of their integer score value.
 */
public class ScoreComparator implements Comparator<Score>
{
	private static ScoreComparator scoreComparator;

	/**
	 * Empty constructor to remove default constructor
	 */
	private ScoreComparator(){

	}

	/**
	 * returns an instance of the comparator
	 * @return
	 */
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