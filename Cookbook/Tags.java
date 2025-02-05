package Cookbook;

import java.io.Serializable;
import java.util.ArrayList;

public class Tags implements Serializable{
	
	ArrayList<String> tags = new ArrayList<String>();
	
	Tags () {	
	}
	
	Tags (String s) {
		
		tags.add(s);
		
	}
	
	public boolean add(String s) {
		
		if (tags.contains(s) == false) {
			
			tags.add(s);
			return true;
			
		} else {
			
			System.out.print("Already exist");
			return false;
			
		}
		
	}
	
	public boolean delete(String s) {
		
		if (tags.contains(s) == true) {
			
			tags.remove(s);
			return true;
			
		} else {
			
			System.out.print("Does not exist");
			return false;
			
		}
	}
	
	public ArrayList<String> getArrayList() {
		
		return tags;
		
	}
}
