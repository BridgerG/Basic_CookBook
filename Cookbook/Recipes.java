package Cookbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Recipes implements Serializable{
	
	Tags tag = new Tags();
	
	String rname = "", theRecipe = "", intstuction = "";
	Date date = new Date();
	
	Recipes() {
	}
	
	Recipes(String rname, String theRecipe, String instructions) {
		
		this.theRecipe = theRecipe;
		this.intstuction = instructions;
		this.rname = rname;
		
	}
	
	public String getName() {
		
		return rname;
		
	}
	
	public void setName(String rname) {
		
		this.rname = rname;
		
	}
	
	public String getRecipe() {
		
		return theRecipe;
		
	}
	
	public void setRecipe(String theRecipe) {
		
		this.theRecipe = theRecipe;
		
	}
	public String getIntructions() {
		
		return intstuction;
		
	}
	
	public void setIntructions(String intstuction) {
		
		this.intstuction = intstuction;
		
	}

	
	
	public Date getDateCreated() {
		
		return date;
		
	}
	
	public ArrayList<String> getTags() {
		
		return tag.getArrayList();
		
	}
	
	public void addTags(String s) {
		
		tag.add(s);
		
	}
	
	public void removeTags(String s) {
		
		tag.delete(s);
		
	}

}
