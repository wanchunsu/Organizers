package assignment3;

public class Building {

	OneBuilding data;
	Building older;
	Building same;
	Building younger;
	
	public Building(OneBuilding data){
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}
	
	public String toString(){
		String result = this.data.toString() + "\n";
		if (this.older != null){
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null){
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null){
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}
	
	public Building addBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		//Creating a Building object to store OneBuilding b in as data
		Building toAdd = new Building(b);
		
		//If the building is older than this
		if(isOlderThan(toAdd,this)) {
			if(this.older==null) {
				this.older=toAdd;
				return this;
			} else {
				this.older.addBuilding(b);
			}
		//If the building is younger than this
		} else if(isYoungerThan(toAdd,this)){
			if(this.younger==null) {
				this.younger=toAdd;
				return this;
			} else {
				this.younger.addBuilding(b);
			}
		}else {//If the buildings have the same year of construction
			if(isTallerThan(toAdd,this)) { //if they the building toAdd is taller than this
				Building temp = new Building(this.data);
				this.data=toAdd.data;
				temp.same=this.same;
				this.same=temp;
						
				return this;
				
			} else { //if toAdd is shorter or same height
				if(this.same==null) {
					this.same=toAdd;
					return this;
				} else {
					this.same.addBuilding(b);
				}
			}
		}
		return this;
	 // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	//3 helper methods to simplify the addBuilding method
	//returns true if the first inputted building is older, younger, 
	//or taller than the second inputted building, respectively
	public boolean isOlderThan(Building toAdd, Building b) {
		if(b.data.yearOfConstruction>toAdd.data.yearOfConstruction) {
			return true;
		}
		return false;
	}
	public boolean isYoungerThan(Building toAdd, Building b) {
		if (b.data.yearOfConstruction<toAdd.data.yearOfConstruction) {
			return true;
		}
		return false;
	}
	public boolean isTallerThan(Building b, Building toAdd) {
		if(b.data.height>toAdd.data.height) {
			return true;
		}
		return false;
	}
	
	
	public Building addBuildings (Building b){
		// ADD YOUR CODE HERE
		//checking if the building is not null before adding the buildings to their respective locations
		//Calling addBuilding to find where to add the buildings
		if(b!=null) {
			this.addBuilding(b.data);
			if(b.older!=null) {
				this.addBuildings(b.older);
			}
			if(b.same!=null) {
				this.addBuildings(b.same);
			}
			if(b.younger!=null){
				this.addBuildings(b.younger);
			}
		}
		return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public Building removeBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		//If the the current building is the building to be removed
		if(this.data.equals(b)) {
			//Case where this.same replaces the building that is removed
			if(this.same!=null) {
				//creating temp variables to store the the children
				Building toAddOlder = this.older;
				Building toAddYounger = this.younger;
				Building newNode = this.same;
				//setting the connections all to null
				this.same=null;
				this.older=null;
				this.younger=null;
				//Updating the this.data, and adding back the buildings at their respective locations
				this.data = newNode.data;
				this.same = newNode.same;
				this.older = newNode.older;
				this.younger = newNode.younger;
				//Calling addBuildings to back the former older and younger buildings
				this.addBuildings(toAddOlder);
				this.addBuildings(toAddYounger);
				
				return this;
			}
			//Case where this.older replaces the building that is removed 
			if(this.same==null && this.older !=null) {
				//Creating temp variables to store the children
				Building toAddYounger = this.younger;
				Building newNode = this.older;
				//setting all connections to null
				this.older=null;
				this.younger=null;
				//Updating this.data and adding back the children to their respective locations
				this.data = newNode.data;
				this.same = newNode.same;
				this.older = newNode.older;
				this.younger = newNode.younger;
				this.addBuildings(toAddYounger);
				
				return this;
			}
			//Case where this.younger replaces the building that is removed
			if (this.same==null&& this.older==null&& this.younger!=null) {
				//Creating a temp variable to store the children
				Building newNode = this.younger;
				
				//setting connection to null
				this.younger=null;
				//Updating this.data and adding back the children to their respective locations
				this.data=newNode.data;
				this.same = newNode.same;
				this.older = newNode.older;
				this.younger = newNode.younger;
				
				return this;	
			
			}
		//Case where this.same is to be removed	and is a leaf
		} else if(this.same!=null&&this.same.data.equals(b)&& this.same.same==null&& this.same.younger==null && this.same.older==null) {
			this.same=null;
		//Case where this.older is to be removed is a leaf
		} else if(this.older!=null&&this.older.data.equals(b)&& this.older.same==null&& this.older.younger==null && this.older.older==null) {
			this.older=null;
		//Case where this.younger is to be removed is a leaf
		} else if(this.younger!=null&&this.younger.data.equals(b)&& this.younger.same==null&& this.younger.younger==null && this.younger.older==null){
			this.younger=null;
		//Otherwise continue the search for the building to be removed
		} else {
			//same year of construction, so search through same
			if(this.data.yearOfConstruction==b.yearOfConstruction) {
				if(this.same!=null) {
					this.same.removeBuilding(b);
				}
			}//building to be removed is older than this, so search through older
			if(this.data.yearOfConstruction>b.yearOfConstruction) {
				if(this.older!=null) {
					this.older.removeBuilding(b);
				}
			}//building to be removed is younger than this, so search through younger
			if(this.data.yearOfConstruction<b.yearOfConstruction) {
				if(this.younger!=null) {
					this.younger.removeBuilding(b);
				}
			}
		
		} 
		return this;
		// DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int oldest(){
		// ADD YOUR CODE HERE
		//Case where this.older is null, the current node must be oldest
		if(this.older==null) {
			return this.data.yearOfConstruction;
		} else {//case where this.older is not null, call oldest() recursively on this.older
			return this.older.oldest();
		}
		
		 // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	
	public int highest(){
		// ADD YOUR CODE HERE
		//Creating a String[] to store the heights by splitting up the string obtained from stringOfHeights()
		String[] heightsArray = this.stringOfHeights().split(",");
		//setting the greatest height to equal the first element of the array
		int greatestHeight = Integer.parseInt(heightsArray[0]);
		//going through the array to find buildings that are taller than greatestHeight and updating
		for(int i=0; i<heightsArray.length; i++) {
			int height = Integer.parseInt(heightsArray[i]);
			if (greatestHeight<height) {
				greatestHeight = height;
			}
		}
		return greatestHeight;
	}
	
	//Helper method for highest() method
	//Creates a string from the heights of the buildings associated with this
	//Uses preorder traversal
	public String stringOfHeights() {
		String s = "";
		s+=this.data.height + ",";
		if(this.older!=null) {
			s+=this.older.stringOfHeights();
		}
		if(this.younger!=null){
			s+=this.younger.stringOfHeights();
		}
		return s;
	}
	
	
	
	public OneBuilding highestFromYear (int year){
		// ADD YOUR CODE HERE
		//returning this.data if the year corresponds to the yearOfConstruction of the current building
		if(this.data.yearOfConstruction==year) {
			return this.data;
		//if year is greater than this.yearOfConstruction, search through younger
		} else if(this.data.yearOfConstruction<year) {
			if(this.younger!=null) {
				return this.younger.highestFromYear(year);
			} else {
				return null;
			}
		} else { //if year is less than this.yearOfConstruction, search through older
			if(this.older!=null) {
				return this.older.highestFromYear(year);
			} else {
				return null;
			}
		}
		
	 // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	

	public int numberFromYears (int yearMin, int yearMax){
		// ADD YOUR CODE HERE
		if(yearMin>yearMax) {
			return 0;
		}
		//Creating a varaible to store the number of buildings within the year range
		int numBuildings =0;
		//Case where the current building is in the year range
		if(yearMin<= this.data.yearOfConstruction && yearMax>=this.data.yearOfConstruction) {
			numBuildings++;
		}
		//adding the older buildings
		if(this.older != null) {
			numBuildings+=this.older.numberFromYears(yearMin, yearMax);
		}
		//adding the same-aged buildings
		if(this.same != null) {
			numBuildings+=this.same.numberFromYears(yearMin, yearMax);
		}
		//adding the younger buildings
		if(this.younger != null) {
			numBuildings+=this.younger.numberFromYears(yearMin, yearMax);
		}
		
		return numBuildings; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int[] costPlanning (int nbYears){
		// ADD YOUR CODE HERE
		
		//Creating an array to store the costs for each year
		int[] costForYear = new int[nbYears];
		//setting the currentYear to be 2018
		int currentYear = 2018;
		//Updating the array with the respective costs for each year starting from 2018 
		for(int i=0; i<costForYear.length; i++) {
			costForYear[i] = this.costToRepairInYear(currentYear);
			currentYear++;
		}
		return costForYear; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	//Adding up the cost of repairing within a year 
	public int costToRepairInYear(int year) {
		int cost=0;
		//adding the corresponding cost if the year matches
		if(this.data.yearForRepair==year) {
			cost+=this.data.costForRepair;
		//Going through the children and calling the method recursively	
		}
		if(this.same!=null) {
			cost+=this.same.costToRepairInYear(year);
		}
		if(this.older!=null) {
			cost+=this.older.costToRepairInYear(year);
		}
		if(this.younger!=null) {
			cost+=this.younger.costToRepairInYear(year);
		}

		return cost;
	}	
}
