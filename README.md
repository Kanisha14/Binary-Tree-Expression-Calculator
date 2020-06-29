# Binary-Tree-Expression-Calculator
It represents any polynomial equation in terms of BINARY TREE and solves all operations by evaluaying recursively the left and right sub-tree.

## TreeWalker.java

Treewalker file includes all operations on the binary tree-like rotation of tree, deleting or inserting a node into the tree, evaluating, differentiating, and simplifying.

Methods involve:

1. **public Position root(Position position):**  
     Returns the root position(current) of a node. If it's not there, it will create a node.
2. **public Position parent(Position position:** 
     Returns the parent position of a node at the current position. If it's not there, it will create a node. It throws an exception if position id invalid.
3. **public Position leftChild(Position position):** 
     Returns the left child of a node at the current position. If it's not there, it will create a node. 
4. **public Position rightChild(Position position):** 
     Returns the left child of a node at the current position. If it's not there, it will create a node
5. **public Position rotateR(Position position):** 
     It rotates a node in the right direction raising its left child to its position and lowering itself to the right.
6. **public Position rotateL(Position position):**  
     It rotates a node in the left direction raising its right child to its position and lowering itself to the left.
7. **public Position first(Position position):** 
     It returns the first position when the binary tree is ordered in Inorder traversal.
8. **public Position last(Position position):** 
     It returns the last position when the binary tree is ordered in Inorder traversal.
9. **public Position next(Position position):** 
     It returns the next position when the binary tree is ordered in Inorder traversal. It creates a new node,  if there's no next position i.e current position is a leaf node.
10. **public Position previous(Position position):**  
      It returns the previous position when the binary tree is ordered in Inorder traversal. It creates a new node if there's no previous position i.e current position is a root node.
11. **public Position set(Position position):**  
      It sets the value of a position to be an integer within its binary search tree order and returns the same position with that value.
12. **public Position insert(Position position):** 
      It inserts a new node after the current node according to the tree's Inorder Traversal order.
13. **public Position deleteNoRight(Position position):** 
      It deletes the node at the current position if there's no right child of that node.
14. **public Position deleteNoLeft(Position position):**  
      It deletes the node at the current position if there's no left child of that node.
15. **public Position delete(Position position):** 
      It deletes the node at the current position when the node has no children. If the node has an only left child, then asked deleteNoRight() to do it, and if it only has a right child, then it will ask method deleteNoLeft() to do it.
16. **public int evaluate(int x, int y, int z):**  
      It performs the operation given as the value the node at the current position on its children recursively, at the end solving the whole equation expressed as a binary tree and returns the resultant integer value.  For example, if the current node has value '+', then it will perform addition on its left and right child. And if the current node's value is not an integer value instead of that operation symbol, then it returns that integer value as output. It is a recursive method. It takes the position of the node we need to evaluate, and the three integer values involved the calculation as a Parameter. It also takes the help of a helper method "public int eval(Position f, int x, int y, int z)". 
17. **public void differentiate():** 
      Using the helper method "public BTNode differentiatehide(BTNode f)" which takes the position of the node as a parameter and differentiates the whole equation further from the given node following different rules for differentiation and returns the resultant expression in terms of the binary tree. It is a recursive method. Rules of differentiation:   

    Sum Rule:    (f+g)' = f' + g'

    Difference Rule:   (fg)' =  f g’ + f’ g

    Quotient Rule:   (f/g)' = (f’ g − g’ f )/g^2

    "public BTNode copy(BTNode f)" is a helper method that helps to create a deep copy of the nodes.

18. **public void simplify():**
      It simplifies the polynomial equation given and outputs the resultant equation in the form of a binary tree using the helper method "public BTNode simplifyhide(BTNode f)". It is a recursive method.
