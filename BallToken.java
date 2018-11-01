public class BallToken extends Token
{
	private int value;
	public BallToken(double x, double y, int value)
	{
		super(x, y, "BallToken");
		this.value = value;
	}
}