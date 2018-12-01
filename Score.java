
//@formatter:on
import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable
{
	private static long serialVersionUID = 201L;
	private Date date;
	private Integer value;
	private String name;
	
	public Score(Integer value, String name)
	{
		date = new Date();
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue()
	{
		return value;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setValue(Integer value)
	{
		this.value = value;
	}
}