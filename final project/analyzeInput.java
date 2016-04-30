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
	//declare vars
	static HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	public static int state; // Current state
	static String color0 = "green";
	static String color1 = "orange";
	static String color2 = "gray";
	static String color3 = "cyan";
	static String htmlStr;
	static String txtStr;
	static int starCt; //star count for comments
	public static int column = 42;//set start column to error
	public static char currChar;
	public static String input;

	/**
	 * checkInput
	 *
	 * This function checks the editor text against DFA for proper usage. 
	 * 
	 * @param user input from editor
	 * @return n/a
	 */
	public static void checkUserInput(String userInput){
		//init globals
		input = userInput;
		state = 0; // Current state
		htmlStr = "";
		txtStr = "";
		starCt = 0; //star count for comments
		column = 42;//set start column to error
		//map all characters to x values of matrix
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
		//EO initializations
		for(int i = 0; i<userInput.length(); i++){
			//loop through user input string
			currChar = userInput.charAt(i); // Set currChar to character at index i
			try {
				column = map.get(currChar);
				//look up column
			}catch(NullPointerException ex){
				txtStr+=currChar;  
				htmlStr += "<span style='border-bottom: 1px solid #ff0000;'>" + txtStr + "</span>";
				txtStr = "";
				state = 0;        
				continue;
			}//eo try catch
			// Add new line if new line char reached
			if(currChar == '~'){      
				htmlStr+="<p>";
			}else{
				txtStr += currChar;
				//add currChar to the text
			}//eo if else
			state = matrix.dfa[state][column];//look up new state
			//System.out.println("state: "+state+" "+ "char: "+currChar);
			//call check state
			checkState(i);//weird error with using nextChar so pass index instead. FIX LATER
		}//eo for
		gui.editor.setHtmlText(htmlStr);
		//reset
		reset();
	}//eo checkInput
	/**
	 * checkState
	 *
	 * checks state to determine what the editor outputs 
	 * 
	 * @param int index
	 * @return n/a
	 */
	public static void checkState(int index){
		//char nextChar = input.charAt(input.indexOf(currChar)+1); NOT WORKING FOR SOME REASON
		//System.out.println("state: "+state+" "+ "char: "+currChar);
		//txtStr=txtStr.replace('\n', '\n');
		switch(state){
			case 1:
				//p case 
				try{
					if(input.charAt(index+1)=='~'){ 
						//if new line, put it after the error underline
						htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
						txtStr = "";
						state = 0;
						currChar = input.charAt(index+1);
					}
				}catch(StringIndexOutOfBoundsException ex){
					htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
					txtStr = "";
					state = 0;
				}//eo try catch
				break;
			case 14:  
				//print success case
				htmlStr+="<font color='"+color0+"'>" + txtStr + "</font>";
				txtStr = "";
				break;
			case 19: 
				//v case
				try{
					if(input.charAt(index+1)=='~'){ 
						//if new line, put it after the error underline
						htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
						txtStr = "";
						state = 0;
						currChar = input.charAt(index+1);
					}
				}catch(StringIndexOutOfBoundsException ex){
					htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
					txtStr = "";
					state = 0;
				}//eo try catch
				break;
			case 23: 
				//Var Decl
				htmlStr+="<font color='"+color3+"'> " + txtStr + "</font>";
				txtStr = "";
				break;            
			case 24:
				try{
					if(input.charAt(index+1)=='~'){ 
						// if new line, put it after the error underline
						htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
						txtStr = "";
						state = 0;
						currChar = input.charAt(index+1);
					}
				}catch(StringIndexOutOfBoundsException ex){
					htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
					txtStr = "";
					state = 0;      
				}//eo try catch
				break;
			case 25:
				//check for single identifiers
				try{
					if(input.charAt(index+1)!= '='){
						htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
						txtStr = "";
						state = 0;
						//if new line, error
					}else{
						break;
					}
				}catch(StringIndexOutOfBoundsException ex){
					// do nothing
				}//eo try catch
				break;
			case 28: 
				//assign accept state
				htmlStr+="<font color='"+color1+"'>" + txtStr + "</font>";
				txtStr = "";
				break;
			case 31:  
				//assign accept state
				htmlStr+="<font color='"+color1+"'>" + txtStr + "</font>";
				txtStr = "";
				break;
			case 35: 
				//assign accept state
				htmlStr+="<font color='"+color1+"'>" + txtStr + "</font>";
				txtStr = "";
				break;
			case 36: 
				//* comment check
				starCt++;
				if(starCt %2 == 0){
					htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
					txtStr = "";
					state = 0;
					//break;
				}
				break;
			case 41:  
				//comment accept
				starCt++;
				htmlStr+="<font color='"+color2+"'>" + txtStr + "</font>";
				txtStr = "";
				break;
			case 42: 
				//error state
				if(!txtStr.equals(" ")){
					//System.out.println(txtStr);
					htmlStr += "<span style='border-bottom: 1px solid red;'>" + txtStr + "</span>";
				}
				txtStr = "";
				state = 0;
				break;       
		}//eo switch
	}//eo checkState
	/**
	 * reset
	 * resets globals so analyze can run again
	 * @param n/a
	 * @return n/a
	 */
	public static void reset(){
		//reset all globals
		state = 0;
		txtStr = "";
		starCt = 0;
		htmlStr = "";
	}//eo reset
}//eof
