package assignment2;

public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		//implementing mergeSort by calling merge after recursive calls of mergeSort
		if(start<end) {
			//creating a variable to store the midpoint
			int mid = (start+end)/2;
			//Calling mergeSort on the first half of the array
			mergeSort(start,mid);
			//Calling mergeSort on the second half of the array
			mergeSort(mid+1,end);
			//Calling merge to merge the arrays
			merge(start,mid,end);
		}
		
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		//Creating variables to store the number of elements on the left and right of the midpoint
		int m = mid-start+1;
		int n = end - mid;
		
		//Temporary arrays used for sorting the left and right elements
		Shelf[] left = new Shelf[m];
		Shelf[] right = new Shelf[n];
		
		//Initializing these temporary arrays with their respective elements 
		//from the left or the right of storage 
		
		for(int i=0; i<m; i++) {
			left[i] = this.storage[start+i];
		}
		for(int j=0; j<n; j++) {
			right[j] = this.storage[mid+j+1];		
		}
		//merging the two temporary arrays 
		//This performs the initial part of merging
		//This may leave out elements of one of the subarrays since 
		//the while loop stops once either one of the subarrays' last index is reached
		int i= 0;
		int j= 0;
		int k= start;
		while (i<m && j<n) {
			if(left[i].height<=right[j].height) {
				this.storage[k] = left[i];
				i++;
			} else {
				this.storage[k] = right[j];
				j++;
			}
			k++;
		}
		//Accounting for the leftover elements
		//Remaining elements will either be in the left or the right array (not both)
		while(i<m) {
			this.storage[k] = left[i];
			i++;
			k++;
			}
		while (j<n) {
			this.storage[k] = right[j];
			j++;
			k++;
		}
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		//Going through the shelves in the warehouse to find the smallest shelf that can accommodate the box
		//return the noProblem message id the box was added onto a shelf
		//otherwise returning the problem message if none of the shelves can hold the box
		for(int i=0; i<this.storage.length; i++) {
			if(this.storage[i].availableLength >= b.length && this.storage[i].height >= b.height) {
				this.storage[i].addBox(b);
				return noProblem;
			}
		}
		return problem;
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		
		//Checking to see if the box is an urgent box and adding it to the front of the toShipUrgently list
		if(b instanceof UrgentBox) {
			//Typecasting
			UrgentBox urgent = (UrgentBox)(b);
			//Case where the toShipUrgently list is empty
			if(this.toShipUrgently==null) {
				this.toShipUrgently = urgent;
			//Case where the list is non-empty
			} else {
				this.toShipUrgently.previous = urgent;
				urgent.next = toShipUrgently;
				this.toShipUrgently = urgent;
			}
			//Returning no problem if the box was added to ship properly
			return noProblem;
		//Checking if the box is an instance of Box and adding it to the beginning of the toShip list
		} else if (b instanceof Box){
			//Case where the toShip list is empty
			if(this.toShip== null) {
				this.toShip = b;
				
			//Case where the toShip list is non-empty
			} else {
				this.toShip.previous = b;
				b.next = this.toShip;
				this.toShip = b;
			}
			//returning no problem if the box was added to ship properly
			return noProblem;
			
		//returning problem if the box was not added to ship properly
		} else {
			return problem;
		}
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		
		//Going through the warehouse the find the box that needs to be shipped
		for(int i=0; i<storage.length; i++) {
			//Calling removeBox to remove this box from its shelf
			Box b = storage[i].removeBox(identifier);
			//Calling addToShip on the box if the removeBox method does not return null(i.e. the box was found)
			//returning the noProblem message if the box was shipped properly
			if(b != null) {
				addToShip(b);
				return noProblem;
			} 
		}
		//returning the problem mesage if the box does not exist in the warehouse
		return problem;
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		
		//Going through the shelves that are before the shelf with index position
		for(int i=0; i<position; i++){
			/*If a smaller shelf can accommodate the box,
			remove the box from its shelf and add it the the smaller shelf*/
			if(b.height<= this.storage[i].height && b.length <= this.storage[i].availableLength) {
				this.storage[position].removeBox(b.id);
				this.storage[i].addBox(b);
				//break out of the loop once the box has been moved
				break;
			}
		}	
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	
	public void reorganize (){
		//ADD YOUR CODE HERE
		
		//Going through the shelves starting from the lowest once and the first box 
		for(int i=0; i<this.storage.length; i++) {
			//Creating a variable to store the box that is being evaluated
			//first initializing this variable to be the firstBox
			Box b = this.storage[i].firstBox;
			//Using a while loop to check that the shelf is not empty and that there is a box that can be evaluated
			while(b != null && this.storage[i].firstBox != null) {
				//Considering the case where the first box can be moved
				//Calling the moved method to check if the first box can be moved
				if(b.id.equals(this.storage[i].firstBox.id) && moved(b,i)) {
					//If the first box can be moved, call moveOneBox on the firstBox
					moveOneBox(this.storage[i].firstBox,i);
					//moveOneBox removes the firstBox, so now the firstBox is the box after the removed box
					//if there is a box after the removed firstBox, b needs to be updated to be the firstBox for the next iteration
					if(this.storage[i].firstBox != null) {
						b= this.storage[i].firstBox;
					}	
				}
				//Otherwise, call moveOneBox and update b to be the next box in the list
				else {
					moveOneBox(b,i);
					b = b.next;
				}
				
			}
		}
	}
	//A helper method to find out if the box can be moved or not
	//It returns true if the box can be moved and false otherwise
	public boolean moved(Box b, int position) {
		for(int i=0; i<position; i++){
			if(b.height<=this.storage[i].height && b.length <= this.storage[i].availableLength) {
				return true;
			}
		}
		return false;
	}
}