
public class Element {
	public char c;
	public int x;
	public String s;		
	public char type; 
	public boolean current;

	public Element(char c, int x, String s, char type)  
	{
		this.c = c;
		this.x = x;
		this.s = s;
		this.type = type;
		current = false;
	}


	public boolean IsChar() { return(type == 'c'); }
	public boolean IsNumb() { return(type == 'x'); }
	public boolean IsStr() { return(type == 's'); }
	public boolean IsKeyed() { return(type == 'k'); }
		
	public String toString() {
	if( IsChar() ) return( Character.toString(c) );
	else if( IsNumb() ) return( Integer.toString(x) );
	else if( IsKeyed() ) return( "<"+Integer.toString(x)+","+s+">" );
	else return( s );
	}

}