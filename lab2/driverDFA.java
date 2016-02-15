package lab2;
import java.io.IOException;
import java.util.Scanner;

public class driverDFA {
	public static void main(String[]args)throws IOException{
		
		/*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String s = in.readLine();
			while(s!=null){
				s=in.readLine();
				System.out.println(s);
			}*/
		//Read in user input FIXME:buffered reader??? What is wanted for reading input
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter a solution: \n");
		String solutionString = userInput.nextLine();
		//Call manwolf main to run
		ManWolf manWolf = new ManWolf(solutionString);
		manWolf.main(args);
		}
	
	}