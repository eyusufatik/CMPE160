package elements;

/**
 * Used for storing transaction info.
 * @author Esad Yusuf Atik
 *
 */
public class Transaction {
	private final SellingOrder sellingOrder; 
	private final BuyingOrder buyingOrder;
	
	public Transaction(BuyingOrder buyingOrder, SellingOrder sellingOrder) {
		this.buyingOrder = buyingOrder;
		this.sellingOrder = sellingOrder;
	}
}
