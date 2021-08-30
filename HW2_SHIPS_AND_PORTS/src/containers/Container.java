
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * Containers will be loaded/unloaded to/from ships from/to ports
 * @author Esad Yusuf Atik
 *
 */
public abstract class Container implements Comparable<Container> {
	/**
	 * ID of the container
	 */
	private final int ID;
	
	/**
	 * Weight of the container
	 */
	protected final int weight;
	
	/**
	 * Constructor for Container class.
	 * @param ID ID of the container.
	 * @param weight Weight of the container.
	 */
	public Container(int ID, int weight) {
		this.ID = ID;
		this.weight = weight;
	}
	
	/**
	 * 
	 * @return ID of the container.
	 */
	public int getID() {
		 return ID;
	}

	/**
	 * 
	 * @return weight of the container.
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * Used for sorting inside ArrayLists.
	 */
	@Override
	public int compareTo(Container o) {
		// TODO Auto-generated method stub
		return ID - o.getID();
	}
	
	/**
	 * Compares two containers.
	 * @param other Container to compare to.
	 * @return True if type, ID and weight of two containers are the same.
	 */
	public boolean equals(Container other) {
		boolean bool = (ID == other.getID()) && (getClass() == other.getClass()) && (weight == other.getWeight());
		return bool;
	}
	
	/**
	 * Child classes must implement this method
	 * @return Consumption per km.
	 */
	public abstract double consumption(); 

	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

