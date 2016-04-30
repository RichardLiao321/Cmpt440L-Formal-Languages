import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.FileWriter;
/**
 * file: gui.java
 * author: Richard Liao
 * course: CMPT 440L
 * assignment: Final Project
 * due date: May 2nd, 2016
 * version: 2.0
 * Creates the gui and handles the events for each item.
 */

	public class gui extends Application{
		StringBuilder strBuilder; //string builder for file reading
		//run button
		Button analyzeBtn;
		final Tooltip runtooltip = new Tooltip();
		//clear button
		Button clearBtn; 
		Menu menuFile = new Menu("File");
		MenuBar menuBar = new MenuBar();
		Menu colorMenu = new Menu("Text Color Schemes");
		final Tooltip cleartooltip = new Tooltip(); //tooltip for clear button
		SeparatorMenuItem sep = new SeparatorMenuItem();
	   
		Menu lineSep = new Menu("|");
		String filePath = ""; //path for save file
		String tmpHTML = ""; //tmp string to hold html with specified new line chars
		static HTMLEditor editor; //rich text editor
    
/**
* main()
* main method
* @param n/a
* @return n/a
*/     
	  public static void main (String args[]){
		  launch(args);
	  }
	
/**
 * getFile()
 * Allows user to select and open files
 * @param n/a
 * @return n/a
 */
	public void loadFile() throws Exception{
		JFileChooser fileChooser = new JFileChooser(); //file chooser object to grab file text
    
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); 
		fileChooser.setFileFilter(filter); 
		//only text files allowed
		strBuilder = new StringBuilder(); //String to build and read into IDE
    
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			//Checking for selected file 
			// get the file 
			java.io.File file = fileChooser.getSelectedFile();
			Scanner input = new Scanner(file);
			//read text from file
			while(input.hasNext()){
				strBuilder.append(input.nextLine()); // Build our string with the selected text from file
				strBuilder.append("\n");
			}//eo while
			input.close();
		}else{
			strBuilder.append("No File Was Selected");
		}//eo if else
	}//eo getFile
/**
* saveFile()
* This uses JFileChooser to allow users to save and name files to their computer
* @param n/a
* @return n/a
*/
	public void saveFile() throws IOException{
		JFileChooser fileChooser = new JFileChooser(); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "text"); 
		fileChooser.setFileFilter(filter); // only text files allowed
		fileChooser.setDialogTitle("Choose a file to save"); // prompt for file saving	     
		int userSelection = fileChooser.showSaveDialog(null); // catching user selection module
		//if selected file txt file, continue
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			filePath = fileToSave.getAbsolutePath();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath + ".txt")))){
				//grab html text, replace all newlines with ~, sanitize
				String tmpTxt = editor.getHtmlText();
				tmpTxt = tmpTxt.replaceAll("<p>", System.lineSeparator());
				tmpTxt = htmlInputToText(tmpTxt);
				bw.write(tmpTxt);
				bw.close();
       }catch (FileNotFoundException ex) {
    	   System.out.println("File not found");
	   }//eo try catch
    }//eo if
  }//eo saveFile
/**
* start()
* starts up the IDE. Launches the program
* @param the main stage
* @return n/a
*/
	public void start(Stage mainStage) throws Exception{
		//SAVE FILE
		MenuItem save = new MenuItem("Save File");
		//event for save file button
		save.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t) {
				try{
					saveFile();
				}catch(IOException e) {
					e.printStackTrace();
				}//eo try catch
			}//eo eventhandler
		});
		//LOAD FILE
		MenuItem load = new MenuItem("Load File");  
		// Event handler for our load file button
		load.setOnAction(new EventHandler<ActionEvent>(){
			//event handler
			public void handle(ActionEvent t) {
				try{
					loadFile();
				}catch(Exception e){
    	  
				}//eo try catch
				String x = strBuilder.toString().replaceAll("\n", "<p>");
				editor.setHtmlText(x);   
			}//eo even handler
		});//eo setOnAction
		//LIGHT THEME
		MenuItem lightTheme = new MenuItem("Light Theme");
		//event for save light color button
		lightTheme.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t) {
				analyzeInput.color0 = "aqua";
				analyzeInput.color1 = "orange";
				analyzeInput.color2 = "gray";
				analyzeInput.color3 = "green";
		    }//eo eventhandler
		});//eo light theme
        //DARK THEME
		MenuItem darkTheme = new MenuItem("Dark Theme");
		//event for save dark color button
		darkTheme.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t) {
				analyzeInput.color0 = "blue";
				analyzeInput.color1 = "pink";
				analyzeInput.color2 = "darkblue";
				analyzeInput.color3 = "darkorange";
			}//eo eventhandler
		});//eo darktheme
		
		//create gui
		//html editor
		editor = new HTMLEditor();
		//title
		mainStage.setTitle("Richard's HirokIDE");
		//clear button
		clearBtn = new Button("Clear");
		cleartooltip.setText("Click here to clear the textarea.");
		clearBtn.setTooltip(cleartooltip);
		//analyze button
		analyzeBtn = new Button("Analyze");
		runtooltip.setText("Click here to analyze current input.");
		analyzeBtn.setTooltip(runtooltip);
		//menu
		GridPane layout = new GridPane();

		lineSep.setDisable(true);
		analyzeBtn.setTranslateX(200);
		clearBtn.setTranslateX(300);
		menuBar.setStyle("-fx-background-color: white;");
		
		colorMenu.getItems().addAll(lightTheme,darkTheme);
		menuBar.getMenus().addAll(menuFile,lineSep,colorMenu);
		menuFile.getItems().addAll(load,sep,save);
		
    	layout.add(editor,0,1);
		layout.add(analyzeBtn,0,2);
		layout.add(clearBtn,0,2);
		layout.add(menuBar,0,0);
		
		Scene scene = new Scene(layout, 600, 600);
		mainStage.setScene(scene);
		mainStage.show();
		//event handler for run button
		analyzeBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tmpHTML = editor.getHtmlText();
				String x = tmpHTML.replaceAll("<p>","~"); 
				String y = x.replaceAll("&nbsp;"," "); 
				//get rid of nbsp
				analyzeInput.checkUserInput(htmlInputToText(y));
			}//eo eventhandler
		});
		
		//event handler for clear button
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				editor.setHtmlText("");
				//reset html editor
			}//eo eventhandler
		});  
	}//eo start
	/**
	* htmlInputtoText()
	* sanitize the html text and return it
	* @param String htmlText
	* @return sanitized string
	*/  
	private String htmlInputToText(String htmlText){
		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer buffer = new StringBuffer(htmlText.length());
		while(matcher.find()){
			matcher.appendReplacement(buffer, "");
		}//eo while
		matcher.appendTail(buffer);
		return(buffer.toString().trim());
	}//eo HTMLtoText
}//eof
