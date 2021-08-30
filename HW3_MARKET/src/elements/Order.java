package elements;

/**
 * Parent class of {@link elements.BuyingOrder} and {@link elements.SellingOrder}
 * @author Esad Yusuf Atik
 *
 */
public class Order {
	/**
	 * Quantity of the order.
	 */
	private double amount;
	
	/**
	 * Price desired for the order to execute.
	 */
	private final double price;
	
	/**
	 * Trader who gave the order.
	 */
	private final int traderID;
	
	/**
	 * Constructor for Order objects.
	 * @param traderID Trader who gave the order.
	 * @param amount Quantity of the order.
	 * @param price Price desired for the order to execute.
	 */
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public int getTraderID() {
		return traderID;
	}
}
