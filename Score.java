
//@formatter:on
import java.io.Serializable;
import java.util.Date;

/**
 * Container class to store the name, date and score the player.
 */
public class Score implements Serializable
{
	private static final long serialVersionUID = 201L;
	private Date date;
	private Integer value;
	private String name;

	/**
	 * initializes with given parameter, and current date
	 * @param value player score
	 * @param name player name
	 */
	public Score(Integer value, String name)
	{
		date = new Date();
		this.value = value;
		this.name = name;
	}

	/**
	 * standard getter
	 */
	public Integer getValue()
	{
		return value;
	}

	/**
	 * standard getter
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * standard getter
	 */
	public String getName()
	{
		return name;
	}



	/**
	 * standard setter
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * standard setter
	 */
	public void setValue(Integer value)
	{
		this.value = value;
	}


	/**
	 * returns String containing Date in format "date month year hour minutes"
	 * @return String containing Date in format "date month year hour minutes"
	 */
	public  String concatenatedDate(){
		String x = date.toString();
		return x.substring(0,x.lastIndexOf("IST"));
	}
}