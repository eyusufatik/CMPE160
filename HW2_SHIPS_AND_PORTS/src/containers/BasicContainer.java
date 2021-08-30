
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * Containers weighing <= 3000 kg
 * @author Esad Yusuf Atik
 *
 */
public class BasicContainer extends Container {

	public BasicContainer(int ID, int weight) {
		super(ID, weight);
	}

	/**
	 * Fuel consumption is weight * 2.50 per km.
	 * @return Fuel consumption per km.
	 */
	@Override
	public double consumption() {
		// TODO Auto-generated method stub
		return weight * 2.50;
	}

}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

