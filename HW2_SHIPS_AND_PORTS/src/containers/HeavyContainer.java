
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * Containers weighing >3000
 * @author esad
 *
 */
public class HeavyContainer extends Container {

	/**
	 * Constructor for the HeavyContainer class.
	 * @param ID ID of the container.
	 * @param weight Weight of the container.
	 */
	public HeavyContainer(int ID, int weight) {
		super(ID, weight);
	}

	/**
	 * Fuel consumption is weight * 3.00 per km.
	 * @return Fuel consumption per km.
	 */
	@Override
	public double consumption() {
		// TODO Auto-generated method stub
		return weight * 3.00;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

