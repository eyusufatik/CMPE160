package elements;

/**
 * Child class of {@link elements.Order}. Used for buying orders.
 * @author esad
 *
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder> {

	/**
	 * Constructor for BuyingOrder objects.
	 * @param traderID Trader who gave the order.
	 * @param amount Quantity of the order.
	 * @param price Price desired for the order to execute.
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}

	/**
	 *	Priority queue orders in ascending order.
	 *	Negative return value means current object is smaller.
	 *	
	 *	We want selling orders to be ordered according to:
	 *	<p><ol>
	 *	<li>Highest price first.</li>
	 *	<li>Highest amount first.</li>
	 *	<li>Lowest trader ID first.</li>
	 *	</ol></p>
	 */
	@Override
	public int compareTo(BuyingOrder o) {
		double price = getPrice();
		double otherPrice = o.getPrice();
		
		if(price != otherPrice) {
			return (int) (otherPrice - price);
		}else {
			double amount = getAmount();
			double otherAmount = o.getAmount();
			
			if(amount != otherAmount) {
				return (int) (otherAmount - amount);
			}else {
				double traderID = getTraderID();
				double otherTraderID = o.getTraderID();
				
				return (int) (traderID - otherTraderID);
			}
		}
	}

}
