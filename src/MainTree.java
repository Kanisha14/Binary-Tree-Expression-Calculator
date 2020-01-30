import java.io.IOException;


public class MainTree {

	public static void main(String[] args) throws IOException {
		
		TreeWalker tree = new TreeWalker("((10 20 30) 40 (null 50 (60 70 80)))");		
			System.out.println(tree.PrettyPrint());
		Position current = tree.root();
		
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.insert(current);
	
		
		
		/* Test Rotate */
		current = tree.root();
		current = tree.rightChild(current);
		current = tree.insert(current);
		current = tree.insert(current);
		current = tree.parent(current);
		current = tree.parent(current);
		current = tree.rotateR(current);
		current = tree.parent(current);
		current = tree.rotateL(current);
		current = tree.rotateR(current);
		current = tree.rotateR(current);
		current = tree.rotateR(current);
		current = tree.insert(current);
		current = tree.parent(current);
		current = tree.parent(current);
		
		
		/*

		current = tree.rightChild(current);
		current = tree.parent(current);
		current = tree.leftChild(current);
		current = tree.rightChild(current);
		current = tree.leftChild(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.root(current);
		current = tree.parent(current);
		current = tree.next(current);
		current = tree.next(current);
		
		
		
		
			current = tree.leftChild(current);
		current = tree.rightChild(current);
		current = tree.parent(current);
		current = tree.rightChild(current);
		current = tree.leftChild(current);
		current = tree.leftChild(current);
	
		
		
		current = tree.last(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		current = tree.previous(current);
		
	
		current = tree.parent(current);
		current = tree.leftChild(current);
		current = tree.leftChild(current);
		current = tree.leftChild(current);
		current = tree.leftChild(current);
		current = tree.leftChild(current);
	
		IOTree tree;
		tree = new; IOTree("((1 2 3) 4 (null 5 (6 7 8)))");
		tree.makeCurrent(tree.leftChild(tree.root()));
		System.out.println(tree.toString());
		tree.Prettyput();				
		tree = new IOTree("(1+2+3+4*5*6)*7+8");
		tree.PrettyPrint();		
*/
		
	}
}
