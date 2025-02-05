package Cookbook;

import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Cookbook_V3 extends Application{

	Tags allTags = new Tags();
	ArrayList<Recipes> allRecipes = new ArrayList<Recipes>();
    Scene scene = null;  
	
	int row = 1, collum = 1;
	GridPane grid = new GridPane();
	ArrayList<Button> recipeButtons = new ArrayList<Button>();
	
	TabPane tabPane = new TabPane();
    Stage stage = new Stage();

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		if(new File("Tags.txt").isFile() && new File("Recipes.txt").isFile()) {
			
			setUpFromStart();
			
		} else {
		
			Tab mainPage = new Tab("Main Page");
	    	MenuBar menuBar = new MenuBar();    
	    
	    	Menu menuNew = new Menu("New");
	    	Menu menuDelete = new Menu("Delete");
	    	menuBar.getMenus().addAll(menuNew, menuDelete);
	    	
	    	//Sets up the "New" tab at the top of the screen
	    	MenuItem newRecipe = new MenuItem("Recipe");
	    	MenuItem newTag = new MenuItem("Tag");
	    	menuNew.getItems().addAll(newRecipe, newTag);
	    
	    	//Sets up the delete button on the top of the screen
	    	MenuItem deleteRecipe = new MenuItem("Recipe");
	    	menuDelete.getItems().add(deleteRecipe);
	    	
			grid.setHgap(33);
			grid.setVgap(30);
		
	    	VBox mainPageVBox = new VBox();
	    	mainPageVBox.getChildren().addAll(menuBar, grid);
	    	mainPage.setContent(mainPageVBox);
	    	mainPage.setClosable(false);
	    	tabPane.getTabs().add(0, mainPage);
	    	
		    newRecipe.setOnAction(e -> {
		    	addRecipeWindow();
		    });
		    
		    newTag.setOnAction(e -> addTagWindow());
			
		    deleteRecipe.setOnAction(e -> {
		    	
		    	deleteRecipe();
		    });
	    
		}
	    
	    VBox vBox = new VBox();
	    vBox.getChildren().add(tabPane);
	    scene = new Scene(vBox, 1700, 1000);  
		primaryStage.setTitle("CookBook"); // Set the window title
	    primaryStage.setScene(scene); // Place the scene in the window
	    primaryStage.show(); // Display the window
	    
	}

    //Makes a new window where you can type the name for a new Recipe
	public void addRecipeWindow() {
		
    	Label label = new Label("Enter the new recipes name: ");
    	TextField tfRecipe = new TextField();
    	HBox recipeHBox = new HBox(40);
    	recipeHBox.getChildren().addAll(label, tfRecipe);
    	recipeHBox.setAlignment(Pos.CENTER);

    	
    	Button recipeButton = new Button("Submit");
    	Button recipeButton2 = new Button("Cancel");
    	HBox recipeButtonBox = new HBox(20);
    	recipeButtonBox.setAlignment(Pos.CENTER);
    	recipeButtonBox.getChildren().addAll(recipeButton, recipeButton2);
    	
    	VBox tagVBox = new VBox(20);
    	tagVBox.getChildren().addAll(recipeHBox, recipeButtonBox);
    	
	    Scene scene2 = new Scene(tagVBox, 350, 75);
	    stage = new Stage();
	    stage.setTitle("Add Recipe");
	    stage.setScene(scene2);
	    stage.show();
	    
	    recipeButton.setOnAction(s -> {
	    	
			if (tfRecipe.getText().length() > 22) {
				
				label.setText("Exceeded 21 Character Limit");
		
			} else if (tfRecipe.getText().length() == 0) {
		
				label.setText("Please Enter Name");
				
			} else {
			
				Recipes recipe = new Recipes();
				recipe.setName(tfRecipe.getText().trim());
				allRecipes.add(recipe);
				
				Button accessRecipe = new Button(tfRecipe.getText().trim());
				accessRecipe.setOnAction(q -> makeRecipeTabNotEditable(accessRecipe));
				accessRecipe.setPrefHeight(100);
				accessRecipe.setPrefWidth(150);
				recipeButtons.add(accessRecipe);
			
				
				if (collum < 9) {
					
					grid.add(recipeButtons.get(recipeButtons.size() - 1), collum, row, 1, 1);
					collum += 1;
			
				} else {
			
					grid.add(recipeButtons.get(recipeButtons.size() - 1), collum, row, 1, 1);
					collum = 1;
					row += 1;
			
				}
				
		    	if(allRecipes.size() == 1) {
		    		Tab tabs = tabPane.getTabs().get(0);
		    		tabPane.getTabs().remove(0);
		    		tabPane.getTabs().add(0, tabs);
		    	}
				stage.close();
				saveProgress();
			}

	    });
	    
	    recipeButton2.setOnAction(s -> {
	    	stage.close();
	    	saveProgress();
	    });

	}
	
    //Makes a new window where you can type in a new tag
	public void addTagWindow() {		
    	
    	Label label = new Label("Enter the new tags name: ");
    	TextField tfTag = new TextField();
    	HBox tagHBox = new HBox(40);
    	tagHBox.getChildren().addAll(label, tfTag);
    	tagHBox.setAlignment(Pos.CENTER);

    	
    	Button tagButton = new Button("Submit");
    	Button tagButton2 = new Button("Cancel");
    	HBox tagButtonBox = new HBox(20);
    	tagButtonBox.setAlignment(Pos.CENTER);
    	tagButtonBox.getChildren().addAll(tagButton, tagButton2);
    	
    	VBox tagVBox = new VBox(20);
    	tagVBox.getChildren().addAll(tagHBox, tagButtonBox);
    	
	    Scene scene2 = new Scene(tagVBox, 350, 75);
	    Stage stage = new Stage();
	    stage.setTitle("Add Tag");
	    stage.setScene(scene2);
	    stage.show();
	    
	    tagButton.setOnAction(s -> {
	    	
	    	if(allTags.add(tfTag.getText().trim()) == true) {
	    		
	    		allTags.add(tfTag.getText().trim());
	    		stage.close();
				saveProgress();
	    		
	    	} else {
	    		
	    		label.setText("Name already exist: ");
	    		
	    	}
	    });
	    
	    tagButton2.setOnAction(s -> stage.close());

	}
	
	public void makeRecipeTabNotEditable(Button b) {
		
		Tab tab = new Tab(b.getText());
	    MenuBar menuBar = new MenuBar();    

	    Menu menuNew = new Menu("Edit");
	    Menu menuRefresh = new Menu("Refresh");
	    menuBar.getMenus().addAll(menuNew, menuRefresh);
	    
	    MenuItem changeTags = new MenuItem("Edit Tags");
	    MenuItem setEditable = new MenuItem("Edit Recipe");
	    menuNew.getItems().addAll(changeTags, setEditable);
	    
	    MenuItem refresh  = new MenuItem("Refresh");
	    menuRefresh.getItems().add(refresh);
	    
	    int location = recipeButtons.indexOf(b);
	    TextField label = new TextField(allRecipes.get(location).getName());
	    label.setAlignment(Pos.CENTER);
	    label.setEditable(false);
		label.setFont(new Font("Ariel", 48));
		HBox title = new HBox();
		title.getChildren().add(label);
		title.setAlignment(Pos.CENTER);
		
		ArrayList<String> recipeTags = allRecipes.get(location).getTags();
		String tagList = "";
		while(recipeTags.size() >  0) {
			
			tagList += "*" + recipeTags.get(recipeTags.size() - 1) + " ";
			recipeTags.remove(recipeTags.size() - 1);
			
		}
		
	    Label label2 = new Label(tagList);
		label2.setFont(new Font("Ariel", 24));
		HBox tags = new HBox();
		tags.getChildren().add(label2);
		tags.setAlignment(Pos.CENTER);
		
		BorderPane recipeBorder = new BorderPane();

		VBox recipeSection = new VBox();
		Label theWordRecipe = new Label("Recipe");
		theWordRecipe.setFont(new Font("Ariel", 32));
		TextArea recipeText = new TextArea(allRecipes.get(location).getRecipe());
		recipeText.setWrapText(true);
		recipeText.setPrefWidth(850);
		recipeText.setPrefHeight(700);
		recipeText.setEditable(false);
		recipeSection.setAlignment(Pos.CENTER);
		recipeSection.getChildren().addAll(theWordRecipe, recipeText);
		recipeBorder.setLeft(recipeSection);

		VBox instructionSection = new VBox();
		Label theWordInstruction = new Label("Instruction");
		theWordInstruction.setFont(new Font("Ariel", 32));
		TextArea instructions = new TextArea(allRecipes.get(location).getIntructions());
		instructions.setPrefWidth(850);
		instructions.setPrefHeight(700);
		instructionSection.setAlignment(Pos.CENTER);
		instructions.setEditable(false);
		instructionSection.getChildren().addAll(theWordInstruction, instructions);
		recipeBorder.setRight(instructionSection);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(menuBar, title, tags, recipeBorder);
		tab.setContent(vBox);
		tabPane.getTabs().addAll(tab);
		tabPane.getSelectionModel().select(tab);
		
		changeTags.setOnAction(e -> changeRecipeTags(b));
		
		setEditable.setOnAction(e -> {
			
			Button save = new Button("Save");
			vBox.getChildren().add(save);
			
			label.setEditable(true);
			recipeText.setEditable(true);
			instructions.setEditable(true);
			
			save.setOnAction(s -> {
				
				allRecipes.get(location).setName(label.getText().trim());
				allRecipes.get(location).setRecipe(recipeText.getText().trim());
				allRecipes.get(location).setIntructions(instructions.getText().trim());
				
				makeRecipeTabNotEditable(b);
				tabPane.getTabs().remove(tab);
				saveProgress();

			});
			
		});
		
		refresh.setOnAction(e -> {
			
		makeRecipeTabNotEditable(b);
		tabPane.getTabs().remove(tab);
		saveProgress();
		
		});
		
		
	}
	
	public void changeRecipeTags(Button b) {
		
		VBox vBox = new VBox(5);
						
		ArrayList<String> copy = allTags.getArrayList();
		
		while(copy.size() > 0) {
			
			HBox hBox = new HBox(5);
			
			for(int i = 0; i < 5 && copy.size() - 1 >=  0; i++) {
				
				RadioButton button = new RadioButton(copy.get(copy.size() - 1));
				hBox.getChildren().add(button);
				
				if(allRecipes.get(recipeButtons.indexOf(b)).getTags().contains((String) copy.get(0)) == true) {
					
					button.setSelected(true);
					
				}
				copy.remove(copy.size() - 1);
				
			}
			
			vBox.getChildren().add(hBox);
			
		}
		
		Button button = new Button("Save");
		button.setAlignment(Pos.BOTTOM_LEFT);
		vBox.getChildren().add(button);
		
	    Scene scene2 = new Scene(vBox);
	    Stage stage = new Stage();
	    stage.setTitle("Edit Tags");
	    stage.setScene(scene2);
	    stage.show();
	    
	    button.setOnAction(e -> {
	    	
	    	while(vBox.getChildren().size() > 1) {
	    		
	    		HBox hBox = (HBox) vBox.getChildren().get(0);
	    		
	    		while(hBox.getChildren().size() > 0) {
	    			
	    			RadioButton rButton = (RadioButton) hBox.getChildren().get(0);
	    			
	    			if(rButton.isSelected() == true) {
	    				
	    				allRecipes.get(recipeButtons.indexOf(b)).addTags(rButton.getText());
	    				
	    			} else if (rButton.isSelected() == false && allRecipes.get(recipeButtons.indexOf(b)).getTags().contains(button.getText().trim()) == true) {
	    				
	    				allRecipes.get(recipeButtons.indexOf(b)).removeTags(rButton.getText());

	    			}
	    			
	    			hBox.getChildren().remove(0);
	    			
	    		}
	    		
	    		vBox.getChildren().remove(0);
	    		
	    	}
	    	
			saveProgress();
	    	stage.close();
	    });
		
		
	}
	
	//creates the scene from the start of everything whenever invoked
	public void setUpFromStart() {
		
		grid.getChildren().clear();
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Tags.txt"));
			allTags.getArrayList().clear();
			allTags = (Tags) ois.readObject();
			System.out.print(allTags.toString());
			ois.close();
			
			ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("Recipes.txt"));
			allRecipes.clear();
			allRecipes = (ArrayList<Recipes>) ois2.readObject();
			ois2.close();
			
			recipeButtons.clear();
			for(int i = 0; i < allRecipes.size(); i++) {
				
				Button button = new Button(allRecipes.get(i).getName());
				button.setPrefHeight(100);
				button.setPrefWidth(150);
				recipeButtons.add(button);
				button.setOnAction(q -> makeRecipeTabNotEditable(button));
				
			}
			
			collum = 1; row = 1;
			
			for(int i = 0; i < recipeButtons.size(); i++) {
			
				if (collum < 9) {
				
					grid.add(recipeButtons.get(i), collum, row, 1, 1);
					collum += 1;
		
				} else {
		
					grid.add(recipeButtons.get(i), collum, row, 1, 1);
					collum = 1;
					row += 1;
		
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tab mainPage = new Tab("Main Page");
	    MenuBar menuBar = new MenuBar();    
	    
	    Menu menuNew = new Menu("New");
	    Menu menuDelete = new Menu("Delete");
	    menuBar.getMenus().addAll(menuNew, menuDelete);
	    
	    //Sets up the "New" tab at the top of the screen
	    MenuItem newRecipe = new MenuItem("Recipe");
	    MenuItem newTag = new MenuItem("Tag");
	    menuNew.getItems().addAll(newRecipe, newTag);
	    
	    //Sets up the delete button on the top of the screen
	    MenuItem deleteRecipe = new MenuItem("Recipe");
	    menuDelete.getItems().add(deleteRecipe);
	    
		grid.setHgap(33);
		grid.setVgap(30);
		
	    VBox mainPageVBox = new VBox();
	    mainPageVBox.getChildren().addAll(menuBar, grid);
	    mainPage.setContent(mainPageVBox);
	    mainPage.setClosable(false);
	    tabPane.getTabs().add(0, mainPage);
	    
	    newRecipe.setOnAction(e -> {
	    	addRecipeWindow();
	    });
	    
	    newTag.setOnAction(e -> addTagWindow());
		
	    deleteRecipe.setOnAction(e -> {
	    	
	    	deleteRecipe();
	    });
		
	}
	
	public void deleteRecipe() {
		
		if (allRecipes.size() < 2) {
			
	    	Label label = new Label("Must hav at least 2 recipes\n to use this Function ");
	    	HBox tagHBox = new HBox(40);
	    	tagHBox.getChildren().addAll(label);
	    	tagHBox.setAlignment(Pos.CENTER);

	    	
	    	Button button = new Button("OK");
	    	HBox buttonBox = new HBox(20);
	    	buttonBox.setAlignment(Pos.CENTER);
	    	buttonBox.getChildren().addAll(button);
	    	
	    	VBox tagVBox = new VBox(20);
	    	tagVBox.getChildren().addAll(tagHBox, buttonBox);
	    	
		    Scene scene2 = new Scene(tagVBox);
		    Stage stage = new Stage();
		    stage.setTitle("Error");
		    stage.setScene(scene2);
		    stage.show();
			
		    button.setOnAction(e -> stage.close());
			
		} else {
		
			Tab tab = new Tab("Delete Page");
			VBox vBox = new VBox();
		
			Label label = new Label("SELECT WHAT TO DELETE");
			label.setAlignment(Pos.CENTER);
			label.setFont(new Font("Ariel", 32));
			vBox.getChildren().add(label);
			vBox.setAlignment(Pos.CENTER);
			
			ArrayList<Button> copy = recipeButtons;
			GridPane deleteGrid = new GridPane();
			deleteGrid.setHgap(33);
			deleteGrid.setVgap(30);
		
			int deleteRow = 1; int deleteCollum = 1;
		
			for(int a = 0; a < copy.size(); a++) {
			
				Button button = copy.get(a);
				
				if (deleteCollum < 9) {
					
					deleteGrid.add(button, deleteCollum, deleteRow, 1, 1);
					deleteCollum += 1;
		
				} else {
		
					deleteGrid.add(button, collum, deleteRow, 1, 1);
					deleteCollum = 1;
					deleteRow += 1;
		
				}
			
				button.setOnAction(e -> {
				
				
					int i = copy.indexOf(button);
					recipeButtons.remove(i);
					allRecipes.remove(i);
					tabPane.getTabs().remove(tabPane.getTabs().indexOf(tab));
					saveProgress();
					setUpFromStart();
					
				});
			
			}
		
			vBox.getChildren().add(deleteGrid);
		
			Button button = new Button("Cancel");
			vBox.getChildren().add(button);
			
			tab.setContent(vBox);
			tabPane.getTabs().add(tab);
			tabPane.getSelectionModel().select(tabPane.getTabs().indexOf(tab));
			tabPane.getTabs().remove(0);

		
			button.setOnAction(e -> {
		
				tabPane.getTabs().remove(tabPane.getTabs().indexOf(tab));
				saveProgress();
				setUpFromStart();
				
			});
		}
	}
	

	public void saveProgress() {

		try { 

			File file = new File("Tags.txt");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); 
 
			oos.writeObject(allTags); 
			oos.close(); 
			
			File file2 = new File("Recipes.txt");
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(file2)); 
			 
			oos2.writeObject(allRecipes); 
			oos2.close(); 
			
			System.out.println("namesList serialized"); 
	
		} 
		catch (IOException ioe) { 
		
			ioe.printStackTrace(); 
		
		} 
		
	}
}
