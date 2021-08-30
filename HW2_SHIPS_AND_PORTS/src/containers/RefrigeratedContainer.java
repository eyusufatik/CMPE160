
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * Special type of HeavyContainers.
 * 
 * @author esad
 * 
 * @see {@link containers.HeavyContainer}
 */
public class RefrigeratedContainer extends HeavyContainer {

	/**
	 * Constructor for the RefrigeratedContainer class.
	 * @param ID ID of the container.
	 * @param weight Weight of the container.
	 */
	public RefrigeratedContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Fuel consumption is weight * 5.00 per km.
	 * @return Fuel consumption per km.
	 */
	@Override
	public double consumption() {
		// TODO Auto-generated method stub
		return weight * 5.00;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

