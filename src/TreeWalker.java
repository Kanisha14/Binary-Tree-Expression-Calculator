import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class TreeWalker extends IOTree {

	// it inherits root and size from LinkedBinaryTree vis IOTree;
	Position current;

	/************/
	/* Constructors                                                                               */
	/*     @param ()            ==> use default tree description                                  */
	/*       (String toParse)   ==> Tree built from this parsing                                  */
	/*                                                                                            */
	/************/

	public TreeWalker() throws IOException
	{
		this("((10 20 30) 40 (null 50 (60 70 80)))");  // The TreeWalker constructor with use default tree description  
	}

	public TreeWalker(String toParse) 
	{
		super(toParse);
		makeCurrent(root()); 
	}	


	/************
	 * root, parent, leftChild, and rightChild return the state position of given position.         *
	 *    - They create it if it is not there.                                                      *	
	 ************/

	public Position root(Position position) 
	{
		if(super.root()!=null)
			return super.root();
		else  // Create a new root
		{
			Element element = new Element('r', 100, "new root", 'x');
			BTNode newroot = new BTNode(element,null,null,null);
			setRoot(newroot);
			return newroot;
		}
	}

	public Position parent(Position position)  throws InvalidPositionException 
	{
		if(!isRoot(position) && super.parent(position) != null) {
			return super.parent(position); 
		}
		else if(isRoot(position)) {  // Create a new root with current node as its left child. 
			Element element = new Element('r', 10, "new root", 'x');
			BTNode node = (BTNode) position;
			BTNode newNode = new BTNode(element,node,null,null);
			node.setParent(newNode);
			setRoot(newNode);
			return set(newNode);
		}
		else {
			throw new InvalidPositionException("position has no parent.");
		}
	}

	public Position leftChild(Position position)  
	{
		if(super.leftChild(position)!= null) {
			return super.leftChild(position); 
		}
		else {  // Create a new left child for the current node. 
			Element element = new Element('l', 20, "new left", 'x');
			BTNode node = (BTNode) position;
			BTNode newNode = new BTNode(element,null,null,node);
			node.setLeft(newNode);
			return set(newNode);
		}
	}

	public Position rightChild(Position position) 
	{
		if(super.rightChild(position)!= null) {
			return super.rightChild(position); 
		}
		else {  // Create a new right child for the current node. 
			Element element = new Element('l', 20, "new right", 'x');
			BTNode node = (BTNode) position;
			BTNode newNode = new BTNode(element,null,null,node);
			node.setRight(newNode);
			return set(newNode);
		}
	}


	/************
	 * rotateR rotates a node raising its left child to its position and lowering itself to the right *
	 * rotateL rotateR does the reverse                                                           *
	 ************/

	public Position rotateR(Position position)  
	{
		if(super.leftChild(position)==null){
			System.out.println("No left child");
			return(position); 
		} 
		BTNode parent = (BTNode) super.parent(position);
		BTNode node = (BTNode) position;
		BTNode child = (BTNode) super.leftChild(position);
		BTNode tree2 = (BTNode) super.rightChild((Position) child);
		if( parent!=null && parent.getLeft()==node ) parent.setLeft(child);
		if( parent!=null && parent.getRight()==node ) parent.setRight(child);
		if( node==root() ) setRoot(child);
		child.setParent(parent);
		child.setRight(node);
		node.setParent(child);
		node.setLeft(tree2);
		if( tree2!=null ) tree2.setParent(node);
		return (Position) child;
	}

	public Position rotateL(Position position)  
	{
		if(super.rightChild(position)==null) {
			System.out.println("No right child");
			return(position); 
		}
		BTNode parent = (BTNode) super.parent(position);
		BTNode node = (BTNode) position;
		BTNode child = (BTNode) super.rightChild(position);
		BTNode tree2 = (BTNode) super.leftChild((Position) child);
		if( parent!=null && parent.getLeft()==node ) parent.setLeft(child);
		if( parent!=null && parent.getRight()==node ) parent.setRight(child);
		if( node==root() ) setRoot(child);
		child.setParent(parent);
		child.setLeft(node);
		node.setParent(child);
		node.setRight(tree2);
		if( tree2!=null ) tree2.setParent(node);
		return (Position) child;
	}

	/************
	 * first, last, next, previous return this position according to the infix traversal order.     *
	 *    - The last two create it if it is not there.                                                      *	
	 ************/

	public Position first(Position position)  
	{
		position = root();
		while(super.leftChild(position)!= null)
			position = super.leftChild(position); 
		return position;
	}

	public Position last(Position position)  
	{
		position = root();
		while(super.rightChild(position)!= null)
			position = super.rightChild(position); 
		return position;
	}

	public Position next(Position position) 
	{
		Position position1 = position;
		if(super.rightChild(position)!= null)   		   // go right left left left ...
		{
			position = super.rightChild(position); 
			while(super.leftChild(position)!= null)
				position = super.leftChild(position); 
		}
		else
		{	
			while( super.parent(position)!=null && super.rightChild(super.parent(position))==position )   // position is a right child
				position = super.parent(position);
			if( super.parent(position)!=null )
				position = super.parent(position);
			else        							   	   // position1 is the last
				position = set(rightChild(position1)); // Note super not used. Hence, it creates the node.
		}		
		return position;
	}

	public Position previous(Position position) 
	{
		Position position1 = position;
		if(super.leftChild(position)!= null)   				// go left right right right ...
		{
			position = super.leftChild(position); 
			while(super.rightChild(position)!= null)
				position = super.rightChild(position); 
		}
		else
		{	
			while( super.parent(position)!=null && super.leftChild(super.parent(position))==position )   // position is a left child
				position = super.parent(position);
			if( super.parent(position)!=null )
				position = super.parent(position);
			else        							         // position1 is the last
				position = set(leftChild(position1));    // Note super not used. Hence, it creates the node.
		}		
		return position;
	}

	/************
	 * set sets the value of a position to be an integer within its binary search tree order        *
	 *        (set returns the same position to allow   position = set(leftChild(position1))        *
	 ************/

	public Position set(Position position)  
	{
		int x;
		if( position==first(position) && position==last(position))
			x = value(position);
		else if( position==first(position) )
			x = value(next(position))-10;
		else if( position==last(position) )
			x = value(previous(position))+10;
		else
			x = (int) (value(previous(position)) + value(next(position)))/2;
		setValue(position,x);
		return(position);
	}

	/***********
	 * insert inserts a new node after the current node according to the tree's infix Traversal order,* 
	 * i.e. go right and then left left left and put the new node there.                             * 
	 ************/

	public Position insert(Position position)  
	{
		if( super.rightChild(position)==null) 
			return set(rightChild(position)); 	// Note super not used. Hence, it creates the node.
		else
		{
			position = next(position); 
			return set(leftChild(position)); 	// Note super not used. Hence, it creates the node.
		}
	}

	/***********
	 * deletes the current node                                                                      *
	 * If no right child use deleteNoRight, if no left use deleteNoLeft                              *
	 * else move contents of next to current and delete next                                         *   
	 ************/

	/* Deletes current when no right child */
	/* Parent adopts left child            */
	
	public Position deleteNoRight(Position position)  
	{
		if( super.rightChild(position)==null) 
		{
			BTNode parent = (BTNode) super.parent(position);
			BTNode node = (BTNode) position;
			BTNode child = (BTNode) super.leftChild(position);
			if( parent!=null && parent.getLeft()==node ) parent.setLeft(child);
			if( parent!=null && parent.getRight()==node ) parent.setRight(child);
			if( node==root() ) setRoot(child);
			if( child!=null ) {
				child.setParent(parent);
				position = child;
			}
			else
				position = parent;
			return position;
		}
		else
		{
			System.out.println("Panic: Is right child");
			return null;
		}
	}

	/* Deletes current when no left child */
	/* Parent adopts right child            */

	public Position deleteNoLeft(Position position)  
	{
		if( super.leftChild(position)==null) 
		{
			BTNode parent = (BTNode) super.parent(position);
			BTNode node = (BTNode) position;
			BTNode child = (BTNode) super.rightChild(position);
			if( parent!=null && parent.getLeft()==node ) parent.setLeft(child);
			if( parent!=null && parent.getRight()==node ) parent.setRight(child);
			if( node==root() ) setRoot(child);
			if( child!=null ) {
				child.setParent(parent);
				position = child;
			}
			else
				position = parent;
			return position;
		}
		else
		{
			System.out.println("Panic: Is left child");
			return null;
		}
	}

	public Position delete(Position position)  
	{
		if( super.rightChild(position)==null) 
			return deleteNoRight(position);
		else if( super.leftChild(position)==null) 
			return deleteNoLeft(position);
		else
		{
			BTNode node = (BTNode) position;
			BTNode toDelete = (BTNode) next(position); 
			node.setElement(toDelete.element());
			deleteNoLeft(toDelete);
			return position;
		}
	}

	public int evaluate(int x, int y, int z)  
	{
		
		Position f = (Position) this.root(this.current);
		
		int ans = eval(f,x,y,z);
	
		return ans;
		
	}
	
	public int eval(Position f,int x, int y, int z) {
		
		if(((Element) f.element()).IsNumb()) {
			return value(f);
		}
		else if( ((Element) f.element()).c == 'x' ) {
			return x;
		}
		else if(((Element) f.element()).c == 'y') {
			return y;
		}
		else if(((Element) f.element()).c == 'z') {
			return z;
		}
		else if( ((Element) f.element()).c == '+' ) {
			return((eval(leftChild(f), x,y,z)) + (eval(rightChild(f), x,y,z)));
		}
		else if( ((Element) f.element()).c == '*' ) {
			return((eval(leftChild(f), x,y,z)) * (eval(rightChild(f), x,y,z)));
		}
		else if( ((Element) f.element()).c == '/' ) {
			return((eval(leftChild(f), x,y,z)) / (eval(rightChild(f), x,y,z)));
		}
		else if( ((Element) f.element()).c == '-' ) {
			return((eval(leftChild(f), x,y,z)) - (eval(rightChild(f), x,y,z)));
		}
		return 0;
	}
	
	public void differentiate()  
	{
		BTNode root = (BTNode) this.root(this.current);
		
	    differentiatehide(root);
	  
	}

	public BTNode differentiatehide(BTNode f)  
	{
		
		if (((Element) f.element()) == null) {//empty tree or null
			
			//return (BTNode) (f.element());
			return copy(f);
		}
		else if(((Element) f.element()).IsNumb()) {
			//sets the element to zero
			
			((Element) f.element()).x = 0;
			return copy(f);//copy(f);
		}
		else if( ((Element) f.element()).c == 'x') {
			//derivative of x = 1;
			
			((Element) f.element()).s = "1";
		
			return copy(f);
		}
		else if( ((Element) f.element()).c == '+' ) {
			
			/*BTNode l = differentiatehide(f.getLeft());
			BTNode r = differentiatehide(f.getRight());
			Element plusElem = new Element('+', 0, "", 'c');
			BTNode plus = new BTNode(plusElem, null , null , null);
			plus.setLeft(l);
			plus.setRight(r);
			setRoot(plus);*/
			differentiatehide(f.getLeft());
			differentiatehide(f.getRight());
			return copy(f);
		}
		
		else if( ((Element) f.element()).c == '-' ) {
			BTNode n = differentiatehide(f.getLeft());
			BTNode m = differentiatehide(f.getRight());
			Element minusElem = new Element('-', 0, "", 'c');
			BTNode minus = new BTNode(minusElem, null , null , null);
			minus.setLeft(n);
			minus.setRight(m);
			setRoot(minus);
			return copy(minus);
			
		}
		
		else if( ((Element) f.element()).c == '*' ) {
			
			
			
			BTNode right = copy(f.getRight());//(f.getRight());
			BTNode left = copy(f.getLeft());
				
			right.setLeft(differentiatehide(f.getLeft()));
			right.setRight(f.getRight());
			
			left.setRight(differentiatehide(f.getRight()));
			left.setLeft(f.getLeft());
			
			Element plusElem = new Element('+', 0, "", 'c');
			Element mulElem = new Element('*', 0, "", 'c');
			BTNode plus = new BTNode(plusElem, null , null , null);
			
			BTNode mul1 = new BTNode(mulElem, left.getLeft() , left.getRight() , plus.getLeft());
			BTNode mul2 = new BTNode(mulElem, right.getLeft() , right.getRight() , plus.getRight());
			plus.setLeft(mul1);
			plus.setRight(mul2);
			setRoot(plus);
				
			return copy(plus);
			
		}
		
		else if( ((Element) f.element()).c == '/' ) {
			
			
			BTNode right = copy(f.getRight());
			BTNode left = copy(f.getLeft());
				
			right.setLeft(differentiatehide(f.getLeft()));
			right.setRight(f.getRight());
			
			left.setRight(differentiatehide(f.getRight()));
			left.setLeft(f.getLeft());
			
			Element minusElem = new Element('-', 0, "", 'c');
			Element mulElem = new Element('*', 0, "", 'c');
			BTNode minus = new BTNode(minusElem, null , null , null);
			
			BTNode mul1 = new BTNode(mulElem, left.getLeft() , left.getRight() , minus.getLeft());
			BTNode mul2 = new BTNode(mulElem, right.getLeft() , right.getRight() , minus.getRight());
			minus.setLeft(mul1);
			minus.setRight(mul2);
			
			BTNode square = new BTNode(mulElem, left.getRight(),left.getRight() , null);
			
			BTNode divide = new BTNode(f.element(), minus, square, null);
	    	setRoot(divide);
				
			return copy(divide);
		}
		else if( ((Element) f.element()).c != 'x' ) {
			((Element) f.element()).s = "0";
			
			return copy(f);
		}
			
		return null;
		
	}
	
	public BTNode copy(BTNode f) {
		
		if(f == null) {
			return null;
		}
		else {
		BTNode newNode = new BTNode();
		newNode.setElement(new Element(((Element) f.element()).c, 0 ,"",'c'));
		if(f.getLeft() != null) {
			newNode.setLeft(copy(f.getLeft()));
			//f.getLeft().setParent(newNode);
		}
		if(f.getRight() != null) {
			newNode.setRight(copy(f.getRight()));
			//f.getRight().setParent(newNode);
		}		
		return newNode;
		}
	}
	
	public void simplify()  
	{
		BTNode root = (BTNode) this.root(this.current);
		
		simplifyhide(root);
		System.out.println("Test: simplify");
	}
    
	public BTNode simplifyhide(BTNode f) {
	   // System.out.println(f.element());
		
		if(f == null) {
			return f;
		}
		if( f.getLeft() == null && f.getRight() == null) {
			return f;
		}
		
		if(((Element) f.element()).IsNumb() || ((Element) f.element()).IsStr() ) {
			return copy(f);
		}
		else {
			
     		BTNode g = simplifyhide(f.getLeft());
			BTNode h = simplifyhide(f.getRight());
			Element left = (Element) g.element();
			Element right = (Element) h.element();
			
			
			if (((Element) f.element()).c == '+') {
				if(left.IsNumb() && right.IsNumb()) {
					int val = left.x + right.x;
					Element x = new Element('0',val, Integer.toString(val), 'x');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
					
				}
				else if(left.x == 0 && left.IsNumb()) {
					
					Element x = new Element(right.c,0, "", 'c');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
			   }	
				else if(right.x == 0 && right.IsNumb()) {
						Element x = new Element(left.c, 0 , "", 'c');
						System.out.println(left);
						BTNode newnode = new BTNode(x, null,null,null);
						setRoot(newnode);
						return copy(newnode);
					   
					}	
			}
			if (((Element) f.element()).c == '-') {
				if(left.IsNumb() && right.IsNumb()) {
					int val = left.x - right.x;
					Element x = new Element('0',val, Integer.toString(val), 'x');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
					//f.setElement(x);
					
				}
				if(left.c == right.c) {
					Element x = new Element('c',0, "", 'x');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
				}
				if(right.x == 0 && right.IsNumb()) {
					Element x = new Element(left.c,0, "", 'c');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);				
				    
				}
				
			}
			if (((Element) f.element()).c == '*') {	
				if(left.IsNumb() && right.IsNumb()) {
					int val = left.x * right.x;
					Element x = new Element('c',val, "", 'x');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);					
				}
				if(left.type == 's' && (right.type == 'x' && right.x == 1)) {
					Element x = new Element(left.c, 0 , "", 'c');
					System.out.println(right);
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
				}
			else if(right.type == 's' && (left.type == 'x' && left.x == 1)) {
				Element x = new Element(right.c,0, "", 'c');
				BTNode newnode = new BTNode(x, null,null,null);
				setRoot(newnode);
				return copy(newnode);
			}	
			
				if(left.x == 0 || right.x == 0) {
					Element x = new Element('c',0, "", 'x');
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);	
				}
				
			}
		if (((Element) f.element()).c == '/') {	
			if(left.IsNumb() && right.IsNumb()) {
				int val = left.x / right.x;
				Element x = new Element('c',val, "", 'x');
				BTNode newnode = new BTNode(x, null,null,null);
				setRoot(newnode);
				return copy(newnode);					
			}
			if(left.c == right.c) {
				Element x = new Element('c',1, "", 'x');
				BTNode newnode = new BTNode(x, null,null,null);
				setRoot(newnode);
				return copy(newnode);
			}
			if(right.x == 0) {
				Element x = new Element('c',0, "", 'x');
				BTNode newnode = new BTNode(x, null,null,null);
				setRoot(newnode);
				System.out.println("Division by 0 gives infinity");
				return copy(newnode);
			}				
             else if(left.x == 0 && left.IsNumb()) {
				
				Element x = new Element(right.c,0, "", 'c');
				BTNode newnode = new BTNode(x, null,null,null);
				setRoot(newnode);
				return copy(newnode);
		   }	
			else if(right.x == 1 && right.IsNumb()) {
					Element x = new Element(left.c, 0 , "", 'c');
					System.out.println(left);
					BTNode newnode = new BTNode(x, null,null,null);
					setRoot(newnode);
					return copy(newnode);
				   
				}
		   			
		}
			return copy(f);
		}
    }


}
