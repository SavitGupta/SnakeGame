
//@formatter:on
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class RowOfBlocks implements Serializable
{
	private ArrayList<Block> blockrow;
	private ArrayList<Integer> values;
	private ArrayList<Boolean> pos;
	private transient Pane root;
	double y;
	
	public RowOfBlocks(int size, Pane root)
	{
		this.values = new ArrayList<Integer>();
		this.blockrow = new ArrayList<Block>();
		this.pos = new ArrayList<Boolean>();
		this.root = root;
		Random random = new Random();
		for (int i = 0; i < 8; i++)
		{
			int num = random.nextInt(2 * size) + 1;
			values.add(num);
		}
		for (int i = 0; i < values.size(); i++)
		{
			int guess = random.nextInt(10);
			if (guess < 7)
			{
				pos.add(true);
			}
			else
			{
				pos.add(false);
			}
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
		if (check)
		{
			if (size != 1)
			{
				values.set(random.nextInt(8), random.nextInt(size - 1) + 1);
			}
			else
			{
				pos.add(random.nextInt(8), false);
			}
		}
		for (int i = 0; i < pos.size(); i++)
		{
			if (pos.get(i))
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
	
	public void prepareSerialize()
	{
		for (int i = 0; i < blockrow.size(); i++)
		{
			blockrow.get(i).prepareSerialize();
		}
	}
	
	public void deserialize(Pane root)
	{
		this.root = root;
		System.out.println(" the size of blockrow is " + String.valueOf(blockrow.size()));
		for (int i = 0; i < blockrow.size(); i++)
		{
			blockrow.get(i).deserialize();
			root.getChildren().addAll(blockrow.get(i), blockrow.get(i).getA());
		}
	}
    public boolean intersection(Bounds other)
    {
        if(blockrow.isEmpty()){
            return false;
        }
        double topy = blockrow.get(0).getBoundsInParent().getMinY();
        double boty = topy + blockrow.get(0).getHeight();
        if(!(other.getMaxY() < topy || other.getMinY() > boty)){
			//System.out.println("blocks " + blockrow.get(0).getBoundsInParent());
			//System.out.println("skipped due to blocks" + other);
            return true;
        }
        return false;
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