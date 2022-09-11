/*
i=0
DO
 PRINT "i";i
 i=i+1
LOOP WHILE i<2
j=0
DO WHILE j<3
 PRINT "j";j
 j=j+1
LOOP
 */


import java.util.*;

public abstract class Node {
	Object value ;
	protected ArrayList <Node> Children = new ArrayList<Node>();
	
	public void addChild(Node x) {
		Children.add(x);
	}
	public Object getValue() {
		return value ;
	}
	
	protected abstract void execute(Node n);
}

class programNode extends Node {
	public programNode() {
		
	}
	
	public void Start() {
		execute(null) ;
	}
	
	public void execute(Node x) {
		for(int i=0 ; i<Children.size() ; i++) {
			Children.get(i).execute(this);
		}
	}
}

class statementNode extends Node {
	programNode PR ;
	public statementNode(programNode x) {
		PR = x ;
	}

	public void execute(Node pr) {
		for(int i=0 ; i<Children.size() ; i++) {
			Children.get(i).execute(pr);
		}
	}

}

class endNode extends Node {
	programNode PR ;
	public endNode(programNode x) {
		PR = x ;
	}

	
    public void execute(Node pr) {
		System.exit(0);
	}

}

class printNode extends Node {
	programNode PR ;
	public printNode(programNode x) {
		PR = x ;
	}

	
	public void execute(Node pr) {
		for(int i=0 ; i<Children.size() ; i++) {
			Children.get(i).execute(pr);
			if(Children.get(i) instanceof string_concatenationNode) {
				System.out.print(Children.get(i).getValue().getS());
			}
			else if(Children.get(i) instanceof NumberNode) {
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof OperationNode) {
				//Children.get(i).execute(pr);
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof sinNode) {
				//Children.get(i).execute(pr);
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof cosNode) {
				//Children.get(i).execute(pr);
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof absNode) {
				//Children.get(i).execute(pr);
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof sqrtNode) {
				//Children.get(i).execute(pr);
				System.out.print(Children.get(i).getValue().getD());
			}
			else if(Children.get(i) instanceof variableNode) {
				if(((variableNode) Children.get(i)).getType().equals("")
					|| ((variableNode) Children.get(i)).getType().equals("!")
					||((variableNode) Children.get(i)).getType().equals("%")){
					
					System.out.print(Children.get(i).getValue().getD());
				}
				else if(((variableNode) Children.get(i)).getType().equals("$")) {
					System.out.print(Children.get(i).getValue().getS());
				}	
			}
			else {
				System.out.println("Error") ;
				System.exit(0);
			}
		}
		System.out.println();
	}
}

class stringNode extends Node {
	programNode PR ;
	
	public stringNode(String s , programNode x) {
		value = new Object() ;
		String m="";
		int Len=s.length()-1;
		for (int i=1;i<Len;i++)
			m+=s.charAt(i);
		value.setS(m) ;
		PR = x ;
	}
	
	public void execute(Node pr) {
		
	}

}

class variableNode extends Node {
	programNode PR ;
	private String ID ;
	private String type ;
	public variableNode(String y , String x , programNode z) {
		this.ID = y ;
		this.type = x ;
		value = new Object() ;
		PR = z ;
		PR.addChild(this);
	}
	
	public String getID() {
		return ID ;
	}
	public String getType() {
		return type ;
	}
			
	public void execute(Node pr) {
		
	}

}

/*
class valueNode extends Node {
	private String Op;
	public valueNode(String Op) {
		this.Op=Op;
	}
	
	
	public void execute() {
		
	}
}
*/

class string_concatenationNode extends Node {
	programNode PR ;
	public string_concatenationNode(programNode x) {
		PR = x ;
	}

	public void execute(Node pr) {
		
		value = new Object() ;
		String st = "" ;
		for(int i=0 ; i<Children.size() ; i++) {
			Object ob = Children.get(i).getValue() ;
			st += ob.getS() ;
		}
		
		value.setS(st);
	}

}

class variable_assignmentNode extends Node {
	programNode PR ;
	public variable_assignmentNode(programNode x) {
		PR = x ;
	}
	
	public void execute(Node pr) {
		Children.get(1).execute(pr);
		if(Children.get(1) instanceof string_concatenationNode && ((variableNode) Children.get(0)).getType().equals("$")) {
			Children.get(0).getValue().setS(Children.get(1).getValue().getS());
		}
		else if(Children.get(1) instanceof NumberNode) {
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof OperationNode) {
			Children.get(1).execute(pr);
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof variableNode) {
			if((((variableNode) Children.get(0)).getType().equals("")
				|| ((variableNode) Children.get(0)).getType().equals("!"))
				&& (((variableNode) Children.get(1)).getType().equals("")
				|| ((variableNode) Children.get(1)).getType().equals("!")) ){
				
				
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else if(((variableNode) Children.get(0)).getType().equals("$") 
					&& ((variableNode) Children.get(1)).getType().equals("$")) {
				
				(Children.get(0)).getValue().setS((Children.get(1)).getValue().getS());
			}
			else if(((variableNode) Children.get(0)).getType().equals("%") 
					&& ((variableNode) Children.get(1)).getType().equals("%")) {
				
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof sinNode) {
			Children.get(1).execute(pr);
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof cosNode) {
			Children.get(1).execute(pr);
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof absNode) {
			Children.get(1).execute(pr);
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else if(Children.get(1) instanceof sqrtNode) {
			Children.get(1).execute(pr);
			if(((variableNode) Children.get(0)).getType().equals("") || ((variableNode) Children.get(0)).getType().equals("!")){
				(Children.get(0)).getValue().setD((Children.get(1)).getValue().getD());
			}
			else {
				Children.get(0).getValue().setD(Math.round(Children.get(1).getValue().getD()));
			}
		}
		else {
			System.out.println("Error") ;
			System.exit(0);
		}	
	}
}


class inputNode extends Node {
	programNode PR ;
	public inputNode(programNode x) {
		PR = x ;
	}
	
	public void execute(Node pr){
		if (Children.size()==2) {
			System.out.println(Children.get(1).value.getS());
		}
		//System.out.println("Enter your input");
		Scanner x = new Scanner(System.in) ;
		String s = x.next() ;
		if(((variableNode) Children.get(0)).getType().equals("!")
			|| ((variableNode) Children.get(0)).getType().equals("")) {
			Children.get(0).getValue().setD(Double.parseDouble(s));
		}
		else if(((variableNode) Children.get(0)).getType().equals("%")) {
			Children.get(0).getValue().setD((int)Double.parseDouble(s));
		}
		else if(((variableNode) Children.get(0)).getType().equals("$")) {
			Children.get(0).getValue().setS(s);
		}
		else {
			System.out.println("Error") ;
			System.exit(0);		
		}
		
		
			
	}

}

class ifNode extends Node {
	programNode PR ;
	boolean valid ;
	public ifNode(programNode x) {
		PR = x ;
		valid = false ;
	}
	
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		valid = ((conditionNode) Children.get(0)).getValid() ;
		for(int i=1 ; i<Children.size() ; i++) {
			if(Children.get(i) instanceof elseifNode || Children.get(i) instanceof elseNode) {
				if(!valid) {
					Children.get(i).execute(pr);
					if(Children.get(i) instanceof elseifNode) {
						valid = ((elseifNode) Children.get(i)).getValid() ;
					}
				}	
			}
			else if(Children.get(i) instanceof statementNode && valid) {
				Children.get(i).execute(pr);
			}
		}
	}
}

class elseifNode extends Node {
	boolean valid ;
	programNode PR ;
	public elseifNode(programNode x) {
		PR = x ;
		valid = false ;
	}
	
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		valid = ((conditionNode) Children.get(0)).getValid() ;
		if(valid) {
			for(int i=1 ; i<Children.size() ; i++) {
				Children.get(i).execute(pr);
			}
		}
	}
	
	public boolean getValid() {
		return valid ;
	}
}
class elseNode extends Node {
	programNode PR ;
	public elseNode(programNode x) {
		PR = x ;
	}
	
	
	public void execute(Node pr) {
		for(int i=0 ; i<Children.size() ; i++) {
			Children.get(i).execute(pr);
		}
	}

}


class compareNode extends Node {
	programNode PR ;
	String Op ;
	boolean valid ; 
	public compareNode(String s , programNode x) {
		PR = x ;
		Op = s ;
		valid = false ;
	}
	
	public void execute(Node pr) {
		valid=false;
		Children.get(0).execute(pr);
		Children.get(1).execute(pr);
		if(Op.equals(">")) {
			if(Children.get(0) instanceof string_concatenationNode
			   || Children.get(1) instanceof string_concatenationNode) {
				
				System.out.print("Error");
				System.exit(0);
			}
			else if( (Children.get(0) instanceof variableNode  
				&& ((variableNode) Children.get(0)).getType().equals("$") ) 
				|| (Children.get(1) instanceof variableNode
				&& ((variableNode) Children.get(1)).getType().equals("$") ) ) {
				
				System.out.print("Error");
				System.exit(0);
			}
			else {
				if(Children.get(0).getValue().getD() > Children.get(1).getValue().getD()) {
					valid = true ;
				}
			}	
		}
		else if(Op.equals("<")) {
			if(Children.get(0) instanceof string_concatenationNode
					   || Children.get(1) instanceof string_concatenationNode) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else if( (Children.get(0) instanceof variableNode  
						&& ((variableNode) Children.get(0)).getType().equals("$") ) 
						|| (Children.get(1) instanceof variableNode
						&& ((variableNode) Children.get(1)).getType().equals("$") ) ) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else {
						
						if(Children.get(0).getValue().getD() < Children.get(1).getValue().getD()) {
							valid = true ;
						}
					}
		}
		else if(Op.equals(">=")) {
			if(Children.get(0) instanceof string_concatenationNode
					   || Children.get(1) instanceof string_concatenationNode) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else if( (Children.get(0) instanceof variableNode  
						&& ((variableNode) Children.get(0)).getType().equals("$") ) 
						|| (Children.get(1) instanceof variableNode
						&& ((variableNode) Children.get(1)).getType().equals("$") ) ) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else {
						
						if(Children.get(0).getValue().getD() >= Children.get(1).getValue().getD()) {
							valid = true ;
						}
					}
		}
		else if(Op.equals("<=")) {
			if(Children.get(0) instanceof string_concatenationNode
					   || Children.get(1) instanceof string_concatenationNode) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else if( (Children.get(0) instanceof variableNode  
						&& ((variableNode) Children.get(0)).getType().equals("$") ) 
						|| (Children.get(1) instanceof variableNode
						&& ((variableNode) Children.get(1)).getType().equals("$") ) ) {
						
						System.out.print("Error");
						System.exit(0);
					}
					else {
						
						if(Children.get(0).getValue().getD() <= Children.get(1).getValue().getD()) {
							valid = true ;
						}
					}
		}
		else if(Op.equals("<>")) {
			if( (Children.get(0) instanceof string_concatenationNode
			    || (Children.get(0) instanceof variableNode &&((variableNode) Children.get(0)).getType().equals("$") ))
				&& !((Children.get(1) instanceof string_concatenationNode
				|| (Children.get(1) instanceof variableNode &&((variableNode) Children.get(1)).getType().equals("$") )))) {
						
						System.out.print("Error");
						System.exit(0);
			}
			else if((Children.get(0) instanceof string_concatenationNode
				    || (Children.get(0) instanceof variableNode &&((variableNode) Children.get(0)).getType().equals("$") ))
					&& ((Children.get(1) instanceof string_concatenationNode
					|| (Children.get(1) instanceof variableNode &&((variableNode) Children.get(1)).getType().equals("$") )))) {
				
				if(!Children.get(0).getValue().getS().equals(Children.get(1).getValue().getS())) {
					valid = true ;
				}
			}
			else {
				if(Children.get(0).getValue().getD() != Children.get(1).getValue().getD()) {
					valid = true ;
				}
			}
			
		}
		else if(Op.equals("=")) {
			if( (Children.get(0) instanceof string_concatenationNode
				    || (Children.get(0) instanceof variableNode &&((variableNode) Children.get(0)).getType().equals("$") ))
					&& !((Children.get(1) instanceof string_concatenationNode
					|| (Children.get(1) instanceof variableNode &&((variableNode) Children.get(1)).getType().equals("$") )))) {
							
							System.out.print("Error");
							System.exit(0);
				}
				else if((Children.get(0) instanceof string_concatenationNode
					    || (Children.get(0) instanceof variableNode &&((variableNode) Children.get(0)).getType().equals("$") ))
						&& ((Children.get(1) instanceof string_concatenationNode
						|| (Children.get(1) instanceof variableNode &&((variableNode) Children.get(1)).getType().equals("$") )))) {
					
					if(Children.get(0).getValue().getS().equals(Children.get(1).getValue().getS())) {
						valid = true ;
					}
				}
				else {
					if(Children.get(0).getValue().getD() == Children.get(1).getValue().getD()) {
						valid = true ;
					}
				}
		}
		
	}
	
	public boolean getValid() {
		return valid ;
	}
	public void setValid(boolean x) {
		valid = x ;
	}
}

class conditionNode extends Node
{
	programNode PR ;
	boolean valid;
	public conditionNode(programNode x)
	{
		PR = x;
		valid =false;
	}
	
	public void execute(Node pr)
	{
		Children.get(0).execute(pr);
	    valid=((compareNode)Children.get(0)).getValid();
		for (int i=1;i<Children.size();i+=2)
		{
			Children.get(i+1).execute(pr);
			if ( ((logicalNode)Children.get(i)).getOp().equals("AND") )
				valid&=((compareNode)Children.get(i+1)).getValid();
			else if ( ((logicalNode)Children.get(i)).getOp().equals("OR") )
				valid|=((compareNode)Children.get(i+1)).getValid();
		}
	}
	public boolean getValid() {
		return valid;
	}
}

class logicalNode extends Node
{
	programNode PR ;
	String Op;
	public logicalNode(String op,programNode x)
	{
		PR = x;
		Op=op;
	}
	
	public void execute(Node pr)
	{
		
	}
	
	public String getOp()
	{
		return Op;
	}
	
}

class forNode extends Node {
	programNode PR ;
	boolean step = false ;
	public forNode(programNode x) {
		PR = x ;
	}
	
	public void execute(Node pr) {
		variable_assignmentNode ret = new variable_assignmentNode((programNode) pr) ;
		ret.addChild(Children.get(0));
	    ret.addChild(Children.get(1));
	    ret.execute(pr);
	    while(true) {
	    	compareNode ret1 = new compareNode("<=" , (programNode) pr) ;
	    	ret1.addChild(Children.get(0));
	    	ret1.addChild(Children.get(2));
	    	ret1.execute(pr);
	    	if(ret1.getValid()) {
	    		for(int i=0 ; i<Children.size() ; i++) {
	    			if(Children.get(i) instanceof statementNode) {
	    				Children.get(i).execute(pr);
	    			}
	    		}
	    	}
	    	else {
	    		break ;
	    	}
	    	
	    	double x = Children.get(0).getValue().getD() ;
	    	if(step) {
	    		double d = Children.get(3).getValue().getD() ;
	    		Children.get(0).getValue().setD(x + d);
	    	}
	    	else {
	    		Children.get(0).getValue().setD(x + 1);
	    	}	
	    }
		
	}
	public void setStep(boolean k) {
		step = k ;
	}

	
}

class whileNode extends Node {
	programNode PR ;
	boolean isWhile;
	public whileNode(programNode x) {
		PR = x ;
	}
	public void setIsWhile(boolean isWhile)
	{
		this.isWhile=isWhile;
	}
	public void execute(Node pr) {
		if(Children.get(0) instanceof statementNode) {
			while(true) {
				for(int i=0 ; i<Children.size() ; i++) {
					Children.get(i).execute(pr);
				}
				if (isWhile) {
					if(((conditionNode) Children.get(Children.size()-1)).getValid() == false) {
						break ;
					}
				}
				else
				{
					if(((conditionNode) Children.get(Children.size()-1)).getValid() == true) {
						break ;
					}
				}
			}
		}
		else {
			while(true) {
				Children.get(0).execute(pr);
				if (isWhile) {
					if(((conditionNode) Children.get(0)).getValid() == false) {
						break ;
					}
				}
				else
				{
					if(((conditionNode) Children.get(0)).getValid() == true) {
						break ;
					}
				}

				for(int i=1 ; i<Children.size() ; i++) {
					Children.get(i).execute(pr);
				}
			}
			
		}
	}

}

class sinNode extends Node {
	programNode PR ;
	public sinNode(programNode x) {
		PR = x ;
		value = new Object() ;
	}
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		value.setD(Math.sin(Children.get(0).getValue().getD()));
	}
}
class cosNode extends Node {
	programNode PR ;
	public cosNode(programNode x) {
		PR = x ;
		value = new Object() ;
	}
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		value.setD(Math.cos(Children.get(0).getValue().getD()));
	}
}
class absNode extends Node {
	programNode PR ;
	public absNode(programNode x) {
		PR = x ;
		value = new Object() ;
	}
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		value.setD(Math.abs(Children.get(0).getValue().getD()));
	}
}
class sqrtNode extends Node {
	programNode PR ;
	public sqrtNode(programNode x) {
		PR = x ;
		value = new Object() ;
	}
	
	public void execute(Node pr) {
		Children.get(0).execute(pr);
		value.setD(Math.sqrt(Children.get(0).getValue().getD()));
	}
}

class OperationNode extends Node {
	programNode PR ;
	private String Op;
	public OperationNode(String Op , programNode x) {
		this.Op=Op;
		PR = x ;
	}
	
	public void execute(Node pr) {
		value = new Object() ;
		
		for(int i=0 ; i<Children.size() ; i++) {
			if(Children.get(i) instanceof variableNode && ((variableNode) Children.get(0)).getType().equals("$")) {
				System.out.print("Error");
				System.exit(0);
			}
		}
		Children.get(0).execute(pr);
		Children.get(1).execute(pr);
		double d1 , d2 ;
		d1 = Children.get(0).getValue().getD() ;	
	    d2 = Children.get(1).getValue().getD() ;
				
		if(Op.equals("negative")) {
			value.setD(-1 * d1);
		}
		else if(Op.equals("+")) {
			value.setD(d1 + d2);
		}
		else if(Op.equals("-")) {
			value.setD(d1 - d2);
		}
		else if(Op.equals("/")) {
			value.setD(d1 / d2);
		}
		else if(Op.equals("*")) {
			value.setD(d1 * d2);
		}
		else if(Op.equals("%")) {
			value.setD(d1 % d2);
		}
		else if(Op.equals("^")) {
			value.setD(Math.pow(d1 , d2));
		}	
	}
}

class NumberNode extends Node{
	programNode PR ;
	
	public NumberNode(String s , programNode x) {
		value = new Object(s) ;
		PR = x ;
	}
	
	public void execute(Node pr) {
		
	}
}

class Object{
	String s ="";
	double d ;
	
	Object(){
		
	}
	Object(String s){
		this.s = s ;
		this.d = Double.parseDouble(s) ;
	}
	public String getS() {
		return s ;
	}
	public double getD() {
		return d ;
	}
	
	public void setS(String s) {
		this.s = s ;
	}
	
	public void setD(double d) {
		this.d = d ;
	}
	
}


