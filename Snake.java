
//@formatter:on
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Snake implements Serializable
{
	private static long serialVersionUID = 104L;
	private int size;
	private transient Pane root;
	private ArrayList<Balls> l1 = new ArrayList<>();
	private double x;
	private double y;
	private int type;
	
	public Snake(double x, double y, int size, Pane root, int j)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.root = root;
		this.type = j;
		System.out.println("x is " + String.valueOf(x));
		for (int i = 0; i < size; i++)
		{
			Balls b = new Balls(x, y, type);
			root.getChildren().add(b);
			l1.add(b);
			y += 13;
		}
		System.out.println("Size1 is " + String.valueOf(this.size));
		System.out.println("Size2 is " + String.valueOf(l1.size()));
		assert (size == l1.size());
	}
	
	public void deserialize(Pane root)
	{
		this.root = root;
		l1.clear();
		double tempy = y;
		for (int i = 0; i < size; i++)
		{
			Balls b = new Balls(x, tempy, type);
			root.getChildren().add(b);
			l1.add(b);
			tempy += 13;
		}
	}
	
	public void incLenghtBy(int amt)
	{
		for (int i = 0; i < amt; i++)
		{
			Balls b = new Balls(l1.get(0).getTranslateX(), y + 13 * size, type);
			root.getChildren().add(b);
			l1.add(b);
			size += 1;
		}
		assert (size == l1.size());
	}
	
	public void decLenghtBy(int amt)
	{
		if(amt > l1.size()){
			System.out.println("amt is greater 83401");
			amt = l1.size();
		}
		for (int i = 0; i < amt; i++)
		{
			root.getChildren().remove(l1.get(size - 1));
			l1.remove(size - 1);
			size -= 1;
		}
		assert (size == l1.size());
	}
	
	public void prepareSerialize()
	{
		x = l1.get(size - 1).getTranslateX();
	}
	
	public void moveLeft(double amt)
	{
		System.out.println(l1.size());
		for (Balls b : l1)
		{
			b.moveLeft(amt);
		}
	}
	
	public boolean intersection(Node other)
	{
		if (l1.size() == 0)
		{
			System.out.println("Snake size is zero");
			return false;
		}
		return l1.get(0).getBoundsInParent().intersects(other.getBoundsInParent());
	}
	
	public void moveRight(double amt)
	{
		for (Balls b : l1)
		{
			b.moveRight(amt);
		}
	}
	
	public double getx()
	{
		return l1.get(0).getTranslateX();
	}
	
	public double gety()
	{
		return l1.get(0).getTranslateY();
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public ArrayList<Balls> getL1()
	{
		return l1;
	}
	
	public void setL1(ArrayList<Balls> l1)
	{
		this.l1 = l1;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public void animate(int j)
	{
		for (int i = 0; i < l1.size(); i++)
		{
			l1.get(i).animate(j);
		}
	}
}