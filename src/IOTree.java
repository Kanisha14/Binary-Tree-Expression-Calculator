/************************************************************************************************/
/************************************************************************************************/
/**                                                                                            **/
/**       |--------------------------------------|                                             **/
/**       |              IOTree                  |                                             **/
/**       |--------------------------------------|                                             **/
/**                                                                                            **/
/**       @author     Jeff Edmonds                                                             **/
/**       @version    1.0   March 28, 2015                                                     **/
/**       @see        LinkedBinaryTree  by Franck van Breugel                                  **/
/**                                                                                            **/
/** IOtree extending LinkedBinaryTree to contain two key new routine.                          **/
/** constructor IOTree(String toParse) constructs a new tree                                   **/
/**    by parsing a string describing the tree.                                                **/
/**  - PrettyPrint return a sting that prints out this tree in a ``pretty'' way.               **/
/**                                                                                            **/
/**  - To changes element stored at each node from generic Object                              **/
/**    to a new class Element.                                                                 **/
/**  - IOTree rewrited a few of LinkedBinearyTree's routines to handle                         **/
/**    null pointers differently                                                               **/
/**  - IOTree has a few methods to input and output data from Element.                         **/
/**                                                                                            **/
/** Exp             ==>    Term                                                                **/
/**                 ==>    Term + Term - ... + Term                                            **/
/** Term            ==>    Factor                                                              **/
/**                 ==>    Factor * Factor / ... * Factor                                      **/
/** Factor          ==>    ( Exp )                                                             **/
/**                 ==>    Int  | Str | <Int,Str>                                              **/
/**                                                                                            **/
/** T   ==>  (TRT) |  R      ie infix                                                          **/
/** R   ==>  Int | Str | <Int,Str> | null                                                      **/
/**                                                                                            **/
/**   eg ((1 2 3) 4 (5 6 7))                                                                   **/
/**             4                                                                              **/
/**          2     6                                                                           **/
/**         1 3   5 7                                                                          **/
/**                                                                                            **/
/** This is a parser to translate from a string to a tree.                                     **/
/** It has a procedure GetT for each (most) non-terminal T. The                                **/
/** pre-condition/post-condition contract for these will be as follows.                        **/
/**                                                                                            **/
/** Parse:                                                                                     **/
/**   For each nonterminal T, the routine GetT parses the longest substring                    **/
/**   of the tokenizer from the indicated starting point that is a valid T.                    **/
/**                                                                                            **/
/** Output:                                                                                    **/
/**   The output consists of the parsing, which in this case is a tree.                        **/
/**                                                                                            **/
/** token:                                                                                     **/
/**   The global variable token initially contains the next token                              **/
/**   to be processed, ie the first token in T.                                                **/
/**   When GetT returns the global variable token will be changed to the next                  **/
/**   token to be processes which is the first token after this parsed T.                      **/
/**                                                                                            **/
/** ss:                                                                                        **/
/**   A string s passed to each routine indicating the depth of the parse tree.                **/
/**   The purpose of this is to indent the debugging comments                                  **/
/**                                                                                            **/
/************************************************************************************************/
/************************************************************************************************/


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

import javax.swing.JTextArea;

import org.omg.CORBA.Current;

public class IOTree extends LinkedBinaryTree
{
	StreamTokenizer tokenizer; 	// This Java object keeps track of where in the file the tokenizer is
								// and the command tokenizer.nextToken() returns the next token,
								// converting it to an string, character, or real as needed. 
	private int token;          // Contains the next token to process. 
	private String parseStr;    // Contains a string of the parsed tree.

	/************************************************************************************************/
	/** Constructors                                                                               **/
	/**     @param ()            ==> empty tree  (LinkedBinaryTree gives one node)                 **/
	/**       (Object element)   ==> Tree with this element  Exp                                   **/
	/**       (Object element, BinaryTree left, BinaryTree right)                                  **/
	/**                          ==> Tree with this element and these trees as subtrees            **/
	/**       (String toParse)   ==> Tree built from this parsing                                  **/
	/**                                                                                            **/
	/************************************************************************************************/

	public IOTree() 
	{
		setRoot(null); 
		setSize(0);
	}

	public IOTree(Object element) 
	{
		super(element);	
	}

	public IOTree(Object element, BinaryTree left, BinaryTree right) 
	{
		super(element,left,right);	
	}

	public IOTree(String toParse) 
	{
		LinkedBinaryTree tree;
		try{	
			HandleTokenizer(toParse);

			if( toParse.contains("+") || toParse.contains("*") ) 
				tree = GetExp(" ");
			else
				tree = GetTree(" ");
			this.setRoot(tree.root()); 	
			this.setSize(tree.size()); 	
			// this.size = tree.size();  It is my size. How do I set it?
			// if debugging print  parseStr
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************************************************************************************************
	 * Return parent, leftChild, or rightChild of position.                                         *
	 *    This over rides these routines in LinedBinaryTree    										*
	 *        - will return null instead of throwing an InvalidPositionException                    *	
	 ************************************************************************************************/

	public Position parent(Position position) throws InvalidPositionException 
	{
		if(position==null) throw new InvalidPositionException("position is null.");
		BTNode node = (BTNode) position;
		return node.getParent();
	}

	public Position leftChild(Position position) throws InvalidPositionException 
	{
		if(position==null) throw new InvalidPositionException("position is null.");
		BTNode node = (BTNode) position;
		return node.getLeft();
	}

	public Position rightChild(Position position) throws InvalidPositionException 
	{
		if(position==null) throw new InvalidPositionException("position is null.");
		BTNode node = (BTNode) position;
		return node.getRight();
	}

	/************************************************************************************************
	 * makeCurrent sets position to be the current position                                         *
	 *    returns the same position to allow   current = tree.makeCurrent(tree.root());             *
	 * notCurrent sets position to not be the current position                                      *
	 ************************************************************************************************/

	public Position makeCurrent(Position position) 
	{
		if(position==null) return null; 
		((Element) position.element()).current = true;
		return(position);
	}

	public void notCurrent(Position position) 
	{
		if(position!=null)
			((Element) position.element()).current = false;
	}

	/************************************************************************************************
	 * value gets value of postion's elements x field                                               *
	 * setValue sets it                                                                             *
	 ************************************************************************************************/

	public int value(Position position) 
	{
		return(((Element) position.element()).x);
	}

	public void setValue(Position position, int x) 
	{
		((Element) position.element()).x = x;
	}

	public String string(Position position) 
	{
		return(((Element) position.element()).s);
	}

	public void setString(Position position, String s) 
	{
		((Element) position.element()).s = s;
	}

	/************************************************************************************************/
	/** Routine GetExp produces a parsed tree                                                      **/
	/**                                                                                            **/
	/** Exp             ==>    Term                                                                **/
	/**                 ==>    Term + Term - ... + Term                                            **/
	/**                                                                                            **/
	/************************************************************************************************/

	private  LinkedBinaryTree GetExp(String indent) throws IOException{
		LinkedBinaryTree leftT, rightT; Element element;  
		leftT = GetTerm(indent+"  "); 						// Read first Term 
		while(token=='+' || token== '-') { 					// Token after the term
			element = BuildElement(indent);   				// Save and move past the '+'
			rightT = GetTerm(indent+"  "); 					// Read next Term 
			leftT = new LinkedBinaryTree(element,leftT,rightT);  // Merge two trees into one
		}
		return(leftT);
	}

	/************************************************************************************************/
	/** Routine GetTerm produces a parsed tree                                                     **/
	/**                                                                                            **/
	/** Term            ==>    Factor                                                              **/
	/**                 ==>    Factor * Factor / ... * Factor                                      **/
	/**                                                                                            **/
	/************************************************************************************************/

	private  LinkedBinaryTree GetTerm(String indent) throws IOException{
		LinkedBinaryTree leftT, rightT; Element element;  
		leftT = GetFact(indent+"  "); 						// Read first Fact 
		while(token=='*' || token== '/') { 					// Token after the fact
			element = BuildElement(indent);   				// Save and move past the '*'
			rightT = GetFact(indent+"  "); 					// Read next Fact
			leftT = new LinkedBinaryTree(element,leftT,rightT);  // Merge two trees into one
		}
		return(leftT);
	}

	/************************************************************************************************/
	/** Routine GetFact produces a parsed tree                                                     **/
	/**                                                                                            **/
	/** Factor          ==>    ( Exp )                                                             **/
	/**                 ==>    Int  | Str | <Int,Str>                                              **/
	/**                                                                                            **/
	/************************************************************************************************/

	private LinkedBinaryTree GetFact(String indent) throws IOException{
		LinkedBinaryTree tree; Element element;  
		if(token=='(') { 			
			BuildElement("");   							// Throw away '(' and move past it.
			tree = GetExp(indent+"  ");    					// Evaluate Exp
			BuildElementCheck("",')');						// Check for ')' and move past it.
		}
		else 
		{
			element = BuildElement(indent);   				// save and move token past Int | Str | <Int,Str>
			tree = new LinkedBinaryTree(element);
		}
		return(tree);
	}


	/************************************************************************************************/
	/** Routine GetTree parses a string and produces a binary tree                                 **/
	/**                                                                                            **/
	/** T   ==>  (TRT) |  R      ie infix                                                          **/
	/** R   ==>  Int | Str | <Int,Str> | null                                                      **/
	/**                                                                                            **/
	/**   eg ((1 2 3) 4 (5 6 7))                                                                   **/
	/**             4                                                                              **/
	/**          2     6                                                                           **/
	/**         1 3   5 7                                                                          **/
	/**                                                                                            **/
	/************************************************************************************************/

	private LinkedBinaryTree GetTree(String indent) throws IOException{
		LinkedBinaryTree leftT,rightT,tree; Element element;
		if(token=='(') { 			
			BuildElement("");   				// Throw away '(' and move past it.
			leftT  = GetTree(indent+"  ");  	// Evaluate T
			element = BuildElement(indent); 			// Evaluate Int | Str | <Int,Str>   
			rightT = GetTree(indent+"  "); 		// Evaluate T
			BuildElementCheck("",')');			// Check for ')' and move past it.
			tree = new LinkedBinaryTree(element,leftT,rightT); 
		}
		else 
		{
			element = BuildElement(indent);   	// save and move token past Int | Str | <Int,Str>
			if(element.s.equals("null")) {
				tree = new LinkedBinaryTree();
				tree.setRoot(null);
			}
			else {
				tree = new LinkedBinaryTree(element);
			}
		}
		return(tree);
	}

	/************************************************************************************************/
	/** Routine element = BuildElement constructs a tree element containing the current token      **/
	/**    (s = indenting) + element concatenated onto parseStr                                    **/
	/**    Gets the next token                                                                     **/
	/************************************************************************************************/
	private  Element BuildElement(String indent) throws IOException{
		Element element;
		switch (token) 
		{ 
		case StreamTokenizer.TT_NUMBER: // A number was found; the value is in nval double num = tokenizer.nval; 
			element = new Element('0',(int) tokenizer.nval,Double.toString(tokenizer.nval),'x');
			break;
		case StreamTokenizer.TT_WORD: 	// A word was found; the value is in sval String word = tokenizer.sval; 
			element = new Element(tokenizer.sval.charAt(0),0,tokenizer.sval,'s');
			break;
		case '"': // A string like "Hello World" was found; the value is in sval String word = tokenizer.sval; 
			element = new Element(tokenizer.sval.charAt(0),0,tokenizer.sval,'s');
			break;
		case '<': // A keyed element <3.4,fun> 
			token = tokenizer.nextToken();
			if( token != StreamTokenizer.TT_NUMBER ) System.out.println("Poorly Formated Key d");
			int d = (int) tokenizer.nval;
			token = tokenizer.nextToken();
			if( token != ',' ) System.out.println("Poorly Formated Key ,");
			token = tokenizer.nextToken();
			if( token != '"' ) System.out.println("Poorly Formated Key str");
			String s = tokenizer.sval;
			token = tokenizer.nextToken();
			if( token != '>' ) System.out.println("Poorly Formated Key >");
			element = new Element(s.charAt(0),d,s,'k');
			break;
		default: // A regular character was found; the value is the token itself char ch = (char)tokenizer.ttype; 
			element = new Element((char) token,0,Character.toString((char) token),'c');
			break;
		}
		if(!indent.equals("")) parseStr = parseStr + indent + element.toString() + "\n";
		token = tokenizer.nextToken();
		return(element);
	}

	/************************************************************************************************/
	/** Routine element = BuildElementCheck checks the current token and gets the next token       **/
	/**    If( current token is not c ) Gives Error message          						       **/
	/**    s = String to print if Debugging                                                        **/
	/**    Gets the next token and puts it in token		                                           **/
	/************************************************************************************************/
	private  Element BuildElementCheck(String indent, char c) throws IOException{
		if(token!=c) { 		
			System.out.println("* Error: Expected )");
		}
		return(BuildElement(indent));	
	}	


	/************************************************************************************************/
	/**    Set up Tokenizer             														   **/
	/************************************************************************************************/
	private void HandleTokenizer(String toParse) throws IOException{
		tokenizer = new StreamTokenizer(new StringReader(toParse));		
		tokenizer.ordinaryChar('-');        	// Tells tokenizer to parse -3 as two tokens.
		tokenizer.ordinaryChar('/');        	// Tells tokenizer to parse 5/10 as three tokens.
		tokenizer.eolIsSignificant(false);  	// Tells tokenizer to ignore the end of the line
		tokenizer.slashSlashComments(true); 	// Tells tokenizer to ignore // comments 
		tokenizer.slashStarComments(true);  	// Tells tokenizer to ignore /* comments */
		token = tokenizer.nextToken();      	// GetT requires first token to have been read
		parseStr = ""; 							// Contains a string of the parsed tree.

	}

	/************************************************************************************************
       Returns a string representation of the subtree of this binary
       tree rooted at the specified node.

       @param node The root of the subtree.
       @return A string representation of the subtree of this binary
       tree rooted at the specified node.
	 ************************************************************************************************/

	private String inorderPrint(BTNode node) 
	{
		if (node == null) return "";
		else if(isExternal(node)) return(node.element().toString());
		else return ("(" + inorderPrint(node.getLeft()) + " " + node.element().toString() + " " + inorderPrint(node.getRight()) + ")");		
	}


	public String toString() 
	{
		return inorderPrint((BTNode) root());	
	}


	/************************************************************************************************/
	/************************************************************************************************
	/** Same as PrettyPrint except for the additional inputs:                                      **/
	/**    PputRec(String prefix, char dir, BTNode node)                                           **/
	/**    PreCond:  - node is the root of a valid subtree                                         **/
	/**              - prefix is any string                                                        **/
	/**              - dir is:                                                                     **/
	/**                   root    if node is the root of the entire tree                           **/
	/**                   right   if node is the right child of its parent                         **/
	/**    PostCond:                                                                               **/
	/**      - The string in prefix is used to prefix every line printed.                          **/
	/**      - The next character in the next column of each line is:                              **/
	/**                                                                                            **/
	/**               |  left sub-tree |     root      |  right sub-tree                           **/
	/**         ---------------------------------------------------------                          **/
	/**         left  |     blank      |    blank      |      '|'                                  **/
	/**         root  |      '|'       |     '-'       |      '|'                                  **/
	/**         right |      '|'       |    blank      |     blank                                 **/
	/**                                                                                            **/
	/**      - the tree representing the expression is printed.                                    **/
	/**      - node, prefix, and dir are not modified.                                             **/
	/**                                                                                            **/
	/**       For example, given                                                                   **/
	/**          prefix : "aaaa"                                                                   **/
	/**          dir: right                                                                        **/
	/**          tree  [y*z]                                                                       **/
	/**       The output is:                                                                       **/
	/**          aaaa|     |- Y                                                                   **/
	/**          aaaa|- * -|                                                                       **/
	/**          aaaa      |- Z                                                                   **/
	/**                                                                                            **/
	/**       For example, given                                                                   **/
	/**          prefix : "aaaa"                                                                   **/
	/**          dir: left                                                                         **/
	/**          tree  [x+z]                                                                       **/
	/**       The output is:                                                                       **/
	/**          aaaa      |- X                                                                   **/
	/**          aaaa|- + -|                                                                       **/
	/**          aaaa|     |- Z                                                                   **/
	/**                                                                                            **/
	/**    Called with:                                                                            **/
	/**      - PputRec( "", dirType.root, tree.root )                                              **/
	/**                                                                                            **/
	/************************************************************************************************/                     
	/************************************************************************************************/

	private static final char DIRLEFT = 'l';
	private static final char DIRROOT = 'c';
	private static final char DIRRIGHT = 'r';

	private String PputRec(String prefix, char dir, BTNode node) {
		char c_left = 0, c_root = 0, c_right = 0;  // char in chart
		String snode;

		if (node == null)
		{
			return prefix +"\n";
			// return( prefix + "|-- null \n"  );
		}
		else 
		{
			Element element = (Element) node.element(); // Mark current node

			if(element.current) {
				snode = "<" + node.element().toString() + ">";
			}
			else {
				snode = " " + node.element().toString() + " ";
			}

			// Fill in chars from table.
			switch (dir) 
			{ 
			case DIRLEFT :
				c_left  = ' ';
				c_root  = '|';
				c_right = '|';
				break;
			case DIRROOT :
				c_left  = ' ';
				c_root  = '-';
				c_right = ' ';
				break;
			case DIRRIGHT :
				c_left  = '|';
				c_root  = '|';
				c_right = ' ';
				break;
			}

			if (isExternal(node))
				return( prefix + c_root + "-" + snode + "\n");
			else {
				// make blank string of length '-' + node's string + '-'
				String blanks = "";
				int leng = snode.length() + 2;
				for(int i=1; i<=leng; i++) blanks += " ";

				String leftS = PputRec(prefix + c_right + blanks, DIRRIGHT, node.getRight());
				String rootS = prefix + c_root + "-" + snode + "-| \n";
				String rightS = PputRec(prefix + c_left + blanks, DIRLEFT, node.getLeft());
				return(leftS+rootS+rightS);
			}
		}
	}


	public String  PrettyPrint() {
		
		
		return "\n" + PputRec ("", DIRROOT, (BTNode) this.root() ) + "\n";
	}
    
	public static void main(String args[]) {
		//Node root = null;
	 //Position p1 = tree.root();
		IOTree tree = new IOTree("((1 2 3) 4 (null 5 (6 7 8)))");
		//tree.makeCurrent(tree.leftChild(tree.root()));
		Position p = tree.makeCurrent(tree.rightChild(tree.leftChild(tree.root())));
		//Position m = tree.rightChild(tree.leftChild(tree.root()));
		Object m2 = tree.rightChild(tree.root()).element();
		//BTNode n = new BTNode(, null, null, p);
		tree.parent(p);
		Position m = tree.rightChild(p);
		
		Object a = m.element();
		 a = new BTNode();
		//tree.children(m, 2) = n;
		
		
		
		//System.out.println(tree.rightChild(p).element().toString());
		System.out.println(tree.toString());
		System.out.println(tree.PrettyPrint());
		//tree = new IOTree(“(1+2+3+4*5*6)*7+8”); // These strings need a ’+’ or a ’*’
		//System.out.println(tree.PrettyPrint());
	 
		
	}
	
}











