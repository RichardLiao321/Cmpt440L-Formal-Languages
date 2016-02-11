package lab2;
public class ManWolf {
	private static String inputStr;
	private static final int q0=0;	//E:mwgc	W:
	private static final int q1=1;	//E:wc		W:mg
	private static final int q2=2;	//E:mwc		W:g
	private static final int q3=3;	//E:c		W:mwg
	private static final int q4=4;	//E:w		W:mgc
	private static final int q5=5;	//E:mgc		W:w
	private static final int q6=6;	//E:mgw		W:c
	private static final int q7=7;	//E:g		W:mwc
	private static final int q8=8;	//E:mg		W:wc
	private static final int q9=9;	//E:		W:mwgc
	private static final int q10=10;//ERRRORRR
	private int state=0;
	static private int[][] delta ={
		//      |m  |w  |g  |c
		/*q0*/	{q10,q10,q1 ,q10},
		/*q1*/	{q2 ,q10,q0 ,q10},
		/*q2*/	{q1 ,q3 ,q10,q4},
		/*q3*/	{q10,q2 ,q5 ,q10},
		/*q4*/	{q10,q10,q6 ,q2},
		/*q5*/	{q10,q10,q3 ,q7},
		/*q6*/	{q10,q7 ,q4 ,q10},
		/*q7*/	{q8 ,q6 ,q10,q5},
		/*q8*/	{q7 ,q10,q9 ,q10},
		/*q9*/	{q10,q10,q8 ,q10},//accept state
		/*q10*/	{q10,q10,q10,q10} //error state	
	};
	/**
	 * constructor:define inputStr
	 * @param solutionString
	 */
	public ManWolf(String solutionString) {
		inputStr=solutionString;
	}//eo ManWolf

	/**
	 * Take in string, call relevant functions and return true or false
	 * depending on whether the DFA accepts the input
	 * @param input: String to use to traverse DFA
	 */
	public void main(String args[]){
		//System.out.println(inputStr);
		process(inputStr);
		//System.out.println("done");
		
	}//eo main
	/**
	 * Make a transition for each char in the string
	 * @param in: String used by process
	 */
	public boolean process(String in){
		char c;
		int coord;
		for(int i=0;i<in.length();i++){
			c = in.charAt(i);
			//System.out.println(c);
			//switch case to translate characters m,w,g,c to something accepted by the array
			switch (c) {
				case 'm':coord = 0;
					break;
				case 'w':coord = 1;
					break;
				case 'g':coord = 2;
					break;
				case 'c':coord = 3;
					break;
				default: System.out.println("Error: Invalid Input "+c);
					return false;
			}//eo switch
			try{
				state = delta[state][coord];
				//System.out.println("state1:"+state);
			}catch(ArrayIndexOutOfBoundsException ex){
				state = q10;
				System.out.println("state2:"+state);
			}//eo catch
		}//eo for
		//Check the state that the traversal ended up in
		if(state==q9){
			System.out.println("This is a solution");
			return true;
		}else{
			System.out.println("This is not a solution");
			return false;
		}//eo elseif
	}//eo process
}//eof