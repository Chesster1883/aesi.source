package aesi;

/**
 * A tree that recursively represents expressions (needs more documentation). Changing, adding and removing
 * nodes or leafs is done by travelling down the tree to the node or leaf that needs to be changed or removed 
 * or added to and performing that operation. All expressions are represented as parts of strings.
 *
 * @author Maarten de Waard
 * @author Maarten Inja
 *
 * @todo	Store a tree as an array. This will probably be a lot faster when mutating, but
 * 			on the other hand, time costs is less significant here.
 */
public final class ExpressionTree implements Cloneable 
{
    private ExpressionTree parent, childLeft, childRight; 
    private String value;
    private int l, r;

    // if the parent == null: this is root 
    // if both childs == null: this is leaf -> value 
    // else: this is a node -> operator

    // leafs are always filled up with a random value. 
    // nodes are always an operator

    /** Clone only works on the root*/
    public Object clone()
    {
	ExpressionTree result = new ExpressionTree();
	result.setValue(new String(value));
	result.setL(new Integer(l));
	result.setR(new Integer(r));
	
	if (!isLeaf())
	{
	    result.setChildLeft((ExpressionTree)childLeft.clone());
	    result.setChildRight((ExpressionTree)childRight.clone());
	}
	
	return result;
    }
    
    // if the parent equals null it means this is the root.
    public ExpressionTree()
    {
        value = getRandomValue();
    }


    // if the parent does not equal null it's either a node or a leaf
    public ExpressionTree(ExpressionTree iParent)
    {
        parent = iParent;
        value = getRandomValue();
    }

    
    /////// methods

    public void addChild()
    {
        if (isLeaf())
        {
            value = getRandomOperator();
            childLeft = new ExpressionTree(this);
            childRight = new ExpressionTree(this);
        }
        else
        {
            if (l <= r)
            {
                l ++;
                childLeft.addChild();
            }
            else
            {
                r ++;
                childRight.addChild();
            }
        }
    }

    public void turnToLeaf()
    {
        childLeft = childRight = null;
        value = getRandomValue();
    }

    public void change()
    {
        if (isLeaf())
            value = getRandomValue();
        else
            value = getRandomOperator();
    }

    public void changeTree()
    {
        if (isLeaf())
        {
            change();
            return;
        }
        int depth = (l>r)?l:r;
        if (depth != 0)
            depth = Program.random.nextInt((l>r)?l:r);
        if (depth == 0)
        {
            change();
            return;
        }
        if (Program.random.nextBoolean())
            childLeft.changeTree(depth - 1); 
        else 
            childRight.changeTree(depth - 1);
    }

    // method change: changes a node or leaf into something different
    public void changeTree(int depth)
    {
        if (depth == 0 || isLeaf())
            change();
        else if (Program.random.nextBoolean())
            childLeft.changeTree(depth - 1);
        else
            childRight.changeTree(depth - 1);
    }

    // methods remove: replaces bottom node for a leaf
    public void removeChild()
    {
        //ExpressionTree child = (Program.random.nextBoolean()?removeChildLeft():removeChildRight()); 
        ExpressionTree child = null;
        while(child != null)
        {          
            if (Program.random.nextBoolean())
            {
                child = childLeft;
                l --;
            }
            else
            {
                child = childRight;
                r --;
            }
        }
        if (parent != null && parent.getParent() != null)
            child.getParent().turnToLeaf();
    }


    public String getRandomOperator()
    {
        return Program.possibleOperators[Program.random.nextInt(Program.possibleOperators.length)];
    }

    // options between calls and random values (generated by random number
    // generator).

    /** Returns a random value for a leaf. This can be a call, random value 
    * (generated by the random number generator) or a private variable. In the 
    * future we do want to be able to weightedly randomly chose between these 
    * options instead of just randomly as we do now.
    *
    * @todo Be able to weightedly randomly chose instead of just randomly.
    */
    public String getRandomValue()
    {
        if (Program.random.nextBoolean())
            return "" + (Program.random.nextInt(Program.top_value));
        else
        {
            int i = Program.random.nextInt(Program.possibleCalls.length + Program.privateVariableList.length);
            if (i < Program.possibleCalls.length)
                return Program.privateVariableList[i];
            return Program.possibleCalls[i - Program.privateVariableList.length];
        }
    }

    /////// public properties

    public ExpressionTree getParent()
    {
        return parent;
    }

    public void setChildLeft(ExpressionTree value)
    {
	childLeft = value;
    }
    
    public ExpressionTree getChildLeft()
    {
	return childLeft;
    }
    
    public void setChildRight(ExpressionTree value)
    {
	childRight = value;
    }
    
    public ExpressionTree getChildRight()
    {
	return childRight;
    }
    
    public String getValue()
    {
        return value;
    }

    public void setValue(String iValue)
    {
        value = iValue;
    }

    public boolean isLeaf()
    {
        return (childLeft == null && childRight == null);
    }
    
    public void setL(int value)
    {
	l = value;
    }
    
    public int getL()
    {
	return l;
    }
    
    public void setR(int value)
    {
	r = value;
    }
    
    public int getR()
    {
	return r;
    }

    public boolean isRoot()
    {
        return parent == null;
    }
    
    public String toString()
    {
        if (!isLeaf())
            return "(" + childLeft.toString() + " " + value + " " + childRight.toString() + ")";
        return value;
    }
}










