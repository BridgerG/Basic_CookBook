# Basic_CookBook

## Synopsis

A basic JavaFX CookBook that you can put recipes in that saves bewteen uses
Tags currently dont work.

## Motivation

To one day make a REALLY good version, this is my third attempt

## How to use

Run the CookBook class and the rest is simply following instructions or pressing buttons

Recipe Section
![image](https://github.com/user-attachments/assets/13c1f031-cd4c-48bf-b4c9-df74d38b426b)


## Code Examples

This is what goes off to update the main page

'''
		
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
'''


