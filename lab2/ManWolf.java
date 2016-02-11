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
private int state;
static private int[][] delta ={
	/*q0*/	{q10,q10,q1 ,q10},
	/*q1*/	{q2 ,q10,q0 ,q10},
	/*q2*/	{q1 ,q3 ,q10,q4},
	/*q3*/	{q10,q2 ,q5 ,q10},
	/*q4*/	{q10,q10,q6 ,q2},
	/*q5*/	{q10,q10,q3 ,q7},
	/*q6*/	{q10,q7 ,q4 ,q10},
	/*q7*/	{q8 ,q6 ,q10,q5},
	/*q8*/	{q7 ,q10,q9 ,q10},
	/*q9*/	{q10,q10,q8 ,q10},
	/*q10*/	{q10,q10,q10,q10},	
};
	/**
	 * constructor:define inputStr
	 * @param solutionString
	 */
	public ManWolf(String solutionString) {
		inputStr=solutionString;
	}

	/**
	 * Take in string, call relevant functions and return true or false
	 * depending on whether the DFA accepts the input
	 * @param input: String to use to traverse DFA
	 */
	public void main(String args[]){
		System.out.println(inputStr);
		
	}
	/**
	 * Make a transition for each char in the string
	 * @param in: String used by process
	 */
	public void process(String in){
		for(int i=0;i<in.length();i++){
			char c = in.charAt(i);
			try{
				state = delta[state][c-'0'];
			}catch(ArrayIndexOutOfBoundsException ex){
				state = q10;
			}
		}
	}
}
