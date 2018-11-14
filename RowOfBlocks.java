import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.Pane;

//@formatter:on
public class RowOfBlocks
{
	private ArrayList<Block> blockrow;
	private ArrayList<Integer> values;
	private ArrayList<Boolean> pos;
	private Pane root;
	
	public RowOfBlocks(int size, Pane root)
	{
		this.values = new ArrayList<Integer>();
		this.blockrow = new ArrayList<Block>();
		this.pos = new ArrayList<Boolean>();
		this.root = root;
		Random random = new Random();
		while (values.size() < 8)
		{
			int num = random.nextInt(size) + random.nextInt(size) / 2;
			if (num == 0)
			{
				num = 1;
			}
			values.add(num);
		}
		boolean check = false;
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i) < size)
			{
				check = true;
				break;
			}
		}
		if (check == false)
		{
			values.set(random.nextInt(8), size - 1);
		}
		for (int i = 0; i < values.size(); i++)
		{
			int guess = random.nextInt(10);
			if (guess < 5)
			{
				pos.add(true);
			}
			else
			{
				pos.add(false);
			}
		}
		for (int i = 0; i < pos.size(); i++)
		{
			if (pos.get(i) == true)
			{
				Block b1;
				int val = values.get(i);
				if (val <= size / 3)
				{
					b1 = new Block(i * 500 / 8, 45, String.valueOf(val), 1);
				}
				else if (val > size / 3 && val <= (size - size / 3))
				{
					b1 = new Block(i * 500 / 8, 45, String.valueOf(val), 2);
				}
				else if (val > size)
				{
					b1 = new Block(i * 500 / 8, 45, String.valueOf(val), 4);
				}
				else
				{
					b1 = new Block(i * 500 / 8, 45, String.valueOf(val), 3);
				}
				root.getChildren().addAll(b1, b1.getA());
				blockrow.add(b1);
			}
		}
	}
	
	public ArrayList<Block> getBlockrow()
	{
		return blockrow;
	}
	
	public void setBlockrow(ArrayList<Block> blockrow)
	{
		this.blockrow = blockrow;
	}
	
	public ArrayList<Integer> getValues()
	{
		return values;
	}
	
	public void setValues(ArrayList<Integer> values)
	{
		this.values = values;
	}
	
	public ArrayList<Boolean> getPos()
	{
		return pos;
	}
	
	public void setPos(ArrayList<Boolean> pos)
	{
		this.pos = pos;
	}
	
	public Pane getRoot()
	{
		return root;
	}
	
	public void setRoot(Pane root)
	{
		this.root = root;
	}
}
