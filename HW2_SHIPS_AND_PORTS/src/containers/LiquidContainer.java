
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * Special type of HeavyContainers.
 * 
 * @author esad
 * 
 * @see {@link containers.HeavyContainer}
 */
public class LiquidContainer extends HeavyContainer {

	/**
	 * Constructor for the LiquidContainer class.
	 * @param ID ID of the container.
	 * @param weight Weight of the container.
	 */
	public LiquidContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Fuel consumption is weight * 4.00 per km.
	 * @return Fuel consumption per km.
	 */
	@Override
	public double consumption() {
		return weight * 4.00;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

