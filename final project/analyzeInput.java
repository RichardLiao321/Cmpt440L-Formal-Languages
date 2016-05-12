import java.util.HashMap;

/**
 * file: test.java
 * author: Richard Liao
 * course: CMPT 440
 * assignment: Final Project
 * due date: May 2nd, 2016
 * version: 1.8
 * 
 * Checks the input from gui.java textarea against the dfa to determine validity
 *
 */
public class analyzeInput{
	//declare class vars
	static HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	public static int state; // Current state
	static String htmlStr;
	static String txtStr;
	static int starCt; //star count for comments
	public static char currChar;
	public static String input;
	public static String printColor 	= "green";
	public static String assignColor	= "blue";
	public static String varDeclColor 	= "yellow";
	public static String commentColor	= "grey";
	/**
	 * lex
	 *
	 * This function checks the editor text against DFA for proper usage. 
	 * 
	 * @param user input from editor
	 * @return n/a
	 */
	public static void lex(String userInput){
		//init globals
		state = 0;
		input = userInput;
		initialize();//recall initialize every run
		for(int i = 0; i<input.length(); i++){
			//loop through user input string
			currChar = input.charAt(i);
			// Add new line to htmlStr if new line char(~) reached
			if(currChar == '~'){
				htmlStr+="<p>";
			}else{
				txtStr += currChar;
			}//eo if else
			//System.out.println("X "+state+" "+ "Y "+map.get(currChar));
			int y=43;//start y at 43(~ or newline)
			if(!map.containsKey(currChar)){
				//if our current character is not found in our hashmap, it is not in the alphabet and therefore the statement is an error
				//System.out.println("character: "+currChar+" is not in the alphabet");
				inputError();			
			}else{  
				y = map.get(currChar);
			}//eo if else
			state = matrix.dfa[state][y];//look up new state
			//call check state
			try{
				checkState(i);
			}catch(StringIndexOutOfBoundsException ex){
				inputError();
			}//eo try catch
		}//eo for
		gui.htmlEditor.setHtmlText(htmlStr);
	}//eo lex
	
	/**
	 * checkState
	 *
	 * checks state to determine what the editor outputs 
	 * 
	 * @param int index
	 * @return n/a
	 */
	public static void checkState(int index){
		//char nextChar = input.charAt(input.indexOf(currChar)+1);
		//System.out.println("state: "+state+" "+ "char: "+currChar);
		switch(state){
			case 1:
				//p with \n  
				if(input.charAt(index+1)=='~'){
					inputError();
				}
				break;
			case 14:  
				//print accept case
				//System.out.println("Print Accept");
				acceptStatement(printColor);
				break;
			case 19: 
				//v with \n
				if(input.charAt(index+1)=='~'){
					inputError();
				}
				break;
			case 23: 
				//Var Decl
				//System.out.println("Var Decl Accept");
				acceptStatement(varDeclColor);
				break;            
			case 24:
				//assignment with \n
				if(input.charAt(index+1)=='~'){
					inputError();
				}

				break;
			case 25:
				//check for single identifiers
				if(input.charAt(index+1)!= '='){
					inputError();
					//if new line, error
				}//eo if
				break;
			case 28: 
				//System.out.println("Assign Accept");
				//assign accept state
				acceptStatement(assignColor);
				break;
			case 31:  
				//System.out.println("Assign Accept");
				//assign accept state
				acceptStatement(assignColor);;
				break;
			case 35: 
				//System.out.println("Assign Accept");
				//assign accept state
				acceptStatement(assignColor);
				break;
			case 36: 
				//* comment check
				starCt++;
				if(starCt %2 == 0){
					inputError();
				}
				break;
			case 41:  
				//comment accept
				starCt++;
				acceptStatement(commentColor);
				break;
			case 42: 
				//error state
				//call inputError no matter what
				inputError();
				break;
			default:
				//If eof is reached before accept state, give it an error
				if(index+1==input.length()){
					inputError();
				}
				break;
		}//eo switch
	}//eo checkState
	/**
	 * initialize
	 * initialize all globals and map characters. 
	 * @param n/a
	 * @return n/a
	 */
	public static void initialize(){
		//reset all globals
		txtStr = "";
		starCt = 0;
		htmlStr = "";
		map.put('a',0);  
		map.put('b',1);
		map.put('c',2);
		map.put('d',3);
		map.put('e',4);
		map.put('f',5);
		map.put('g',6);
		map.put('h',7);
		map.put('i',8);
		map.put('j',9);
		map.put('k',10);
		map.put('l',11);
		map.put('m',12);
		map.put('n',13);
		map.put('o',14);
		map.put('p',15);
		map.put('q',16);
		map.put('r',17);
		map.put('s',18);
		map.put('t',19);
		map.put('u',20);
		map.put('v',21);
		map.put('w',22);
		map.put('x',23);
		map.put('y',24);
		map.put('z',25);
		map.put('0',26);
		map.put('1',27);
		map.put('2',28);
		map.put('3',29);
		map.put('4',30);
		map.put('5',31);
		map.put('6',32);
		map.put('7',33);
		map.put('8',34);
		map.put('9',35);
		map.put('=',36);
		map.put('+',37);
		map.put('(',38);
		map.put(')',39);
		map.put('-',40);
		map.put('*',41);
		map.put(' ',42);
		map.put('~',43);
		map.put('"',44);
	}//eo initialize
	/**
	 * inputError
	 * gives the currently built textstring error for html string. dump txt string and reset state
	 * @param 
	 * @return n/a
	 */
	public static void inputError(){
		htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
		txtStr = "";
		state = 0;
	}//eo inputError
	
	/**
	 * acceptStatement
	 * static function to reduce redundant code. 
	 * When an accept state is reached, call this function to format the htmlStr and reset. 
	 * depending on the accept state give a color
	 * @param String color
	 * @return n/a
	 */
	
	public static void acceptStatement(String color){
		htmlStr+="<font color='"+color+"'>" + txtStr + "</font>";
		txtStr = "";
		state=0;
	}//eo acceptStatement
}//eof
