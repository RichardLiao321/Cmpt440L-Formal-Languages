import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	//class variables
    public static HTMLEditor htmlEditor = new HTMLEditor();
	/**
	* main()
	* main method calls launch
	* @param n/a
	* @return n/a
	*/     
	  public static void main (String args[]){
		  launch(args);
	  }
	/**
	* start()
	* starts up the IDE. Launches the program
	* @param the main stage
	* @return n/a
	*/
	public void start(Stage stage) throws Exception{
		//MENU BAR
		MenuBar menuBar  = new MenuBar();
		//File menu
		Menu 	menuFile = new Menu("File");
		MenuItem loadFile = new MenuItem("Load a File");
		loadFile.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				try {
					FileChooser fileChooser = new FileChooser();
	                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	                fileChooser.getExtensionFilters().add(extFilter);
	                File file = fileChooser.showOpenDialog(stage);
	                if(file != null){
	                    htmlEditor.setHtmlText(loadFile(file));
	                }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//eo loadFile Event
		});
		MenuItem saveFile = new MenuItem("Save a File");
		saveFile.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				try {
					saveFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//eo loadFile Event
		});
		//BUTTON PANE
        HBox buttonPane = new HBox();
        HBox seperator = new HBox();
        seperator.setMinWidth(335);
        //go button
        Button analyze = new Button("Go");
        analyze.setTooltip(new Tooltip("Click To lex input"));
        analyze.setPrefWidth(75);
        analyze.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				String stripped = htmlEditor.getHtmlText().replaceAll("<p>","~"); 
				stripped = stripped.replaceAll("&nbsp;"," "); 
				analyzeInput.lex(htmlInputToText(stripped));
			}//eo analyze handler
        });
        //clear button
        Button clear = new Button("Clear Input");
        clear.setTooltip(new Tooltip("Delete everything in the text area"));
        clear.setPrefWidth(75);
        clear.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				htmlEditor.setHtmlText("");
			}//eo clear handler
        });
        buttonPane.getChildren().addAll(analyze,seperator,clear);
		menuFile.getItems().addAll(saveFile,loadFile);
		//view menu
		Menu menuView = new Menu("View");
		Menu themeMenu = new Menu("Themes");
		MenuItem originalTheme = new MenuItem("Original Theme");
		originalTheme.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				analyzeInput.printColor="green";
				analyzeInput.assignColor="blue";
				analyzeInput.varDeclColor="yellow";
				analyzeInput.commentColor="grey";
				analyzeInput.errorColor="black";
				analyze.fire();
			}//eo originalTheme handler
        });
		MenuItem blueTheme = new MenuItem("Blue Theme");
		blueTheme.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				analyzeInput.printColor="DarkBlue";
				analyzeInput.assignColor="blue";
				analyzeInput.varDeclColor="SkyBlue";
				analyzeInput.commentColor="grey";
				analyzeInput.errorColor="#ff00bf";
				analyze.fire();
			}//eo blueTheme handler
        });
		MenuItem randomTheme = new MenuItem("Random Theme");
		randomTheme.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				analyzeInput.printColor=colorRandomizer();
				analyzeInput.assignColor=colorRandomizer();
				analyzeInput.varDeclColor=colorRandomizer();
				analyzeInput.commentColor=colorRandomizer();
				analyzeInput.errorColor=colorRandomizer();
				analyze.fire();
			}//eo randomTheme handler
        });
		themeMenu.getItems().addAll(originalTheme,blueTheme,randomTheme);
		menuView.getItems().addAll(themeMenu);
		menuBar.getMenus().addAll(menuFile, menuView);
		//HTML EDITOR
		Scene scene = new Scene(new Group());
        stage.setTitle("Richard Liao IDE");
        stage.setWidth(500);
        stage.setHeight(600);
        htmlEditor.setPrefHeight(550);
        htmlEditor.setTooltip(new Tooltip("Type your input here"));
        //ROOT VBOX
		VBox root = new VBox();
        root.getChildren().addAll(menuBar,htmlEditor,buttonPane);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
	}//eo start
	/**
	 * loadFile()
	 * Allows user to select and open files
	 * @param File file
	 * @return n/a
	 */
	public String loadFile(File file) throws Exception{
     	//StringBuilder stringBuffer = new StringBuilder(); UNSURE IF I SHOULD USE THIS
     	String fileString ="";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String readText;
        try{
            while((readText=bufferedReader.readLine())!= null) {
                fileString+=readText+"<p>";
            }
        }catch(FileNotFoundException ex){
        	fileString="File not found";
        }
        bufferedReader.close();
        return fileString;

	}//eo loadFile
	/**
	* saveFile()
	* This uses JFileChooser to allow users to save and name files to their computer
	* @param n/a
	* @return n/a
	*/
	public void saveFile() throws IOException{
		JFileChooser fileChooser = new JFileChooser(); 
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("text", "txt"); 
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setDialogTitle("Save File");
		int userSelection = fileChooser.showSaveDialog(null);
		//if selected file txt file, continue
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String filePath = fileToSave.getAbsolutePath();
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter buffWriter = new BufferedWriter(fileWriter);
			String rawTxt = htmlEditor.getHtmlText().replaceAll("<p>", System.lineSeparator());
			String htmlTxt = htmlInputToText(rawTxt);
			buffWriter.write(htmlTxt);
			buffWriter.close();
		}else if(userSelection == JFileChooser.CANCEL_OPTION){
			System.out.println("File save cancelled");
		}
	}//eo saveFile

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
	}//eo htmlInputToText
	private String colorRandomizer(){
		String[] letters = "0123456789ABCDEF".split("");
	    String color = "#";
	    for (int i = 0; i < 6; i++ ) {
	        color += letters[(int) Math.floor(Math.random() * 16)];
	    }
	    return color;
	}
}//eof
