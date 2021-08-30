package elements;

/**
 * Trader class is used for holding trader data and sending buy/sell orders.
 * 
 * @author Esad Yusuf Atik
 *
 */
public class Trader {
	/**
	 * Placeholder for IDs.
	 */
	public static int numberOfUsers = 0;
	
	/**
	 * ID of the trader.
	 */
	private int id;
	
	/**
	 * Wallet of the trader. Holds dollars and PQoin
	 * 
	 * @see {@link elements.Wallet}
	 */
	private Wallet wallet;
	
	/**
	 * Constructor for Trader objects.
	 * @param dollars Initial amount of Dollars the trader has. 
	 * @param coins Initial amount of PQoins the trader has.
	 */
	public Trader(double dollars, double coins) {
		this.id = numberOfUsers;
		this.wallet = new Wallet(dollars, coins);
		
		numberOfUsers++;
	}
	
	/**
	 * Creates a sell order and blocks coins until the order is canceled or executed.
	 * @param amount Amount of coins to sell.
	 * @param price Price which the trader wants to sell coins at.
	 * @param market Market on which the order will be executed at.
	 * @return Returns 1 if the order was successful, 0 if the order was unsuccessful.
	 */
	public int sell(double amount, double price, Market market) {
		if (amount <= wallet.getCoins() || id == 0) {
			SellingOrder sellOrder = new SellingOrder(id, amount, price);
			market.giveSellOrder(sellOrder);
			wallet.blockCoins(amount);
			return 1;
		}else {
			Market.invalidQueries++;
			return 0;
		}
	}
	
	/**
	 * Creates a buy order.
	 * 
	 * @param amount Amount of PQoins to buy.
	 * @param price Price which the trader desires to buy PQoins
	 * @param market Market on which the order will be executed.
	 * @return Returns 1 if the order was successful, 0 if the order was unsuccessful.
	 */
	public int buy(double amount, double price, Market market) {
		if (amount * price <= wallet.getDollars() || id == 0) {
			BuyingOrder buyOrder = new BuyingOrder(id, amount, price);
			market.giveBuyOrder(buyOrder);
			wallet.blockDollars(amount * price);
			return 1; // return 1 if the order is successful
		}else {
			Market.invalidQueries++;
			return 0; // return 0 if the order is unsuccessful
		}
	}
	
	/**
	 * Deposits given amount to trader wallet.
	 * 
	 * @param amount Amount to be deposited.
	 * @see {@link elements.Wallet}
	 */
	public void depositDollars(double amount) {
		wallet.depositDollars(amount);
	}
	
	/**
	 * Withdraws given amount from trader wallet.
	 * 
	 * @param amount Amount to be withdrew.
	 * @see {@link elements.Wallet}
	 */
	public void withdrawDollars(double amount) {
		wallet.withdrawDollars(amount);
	}
	
	/**
	 * Deposits given amount to trader wallet.
	 * 
	 * @param amount Amount to be deposited.
	 * @see {@link elements.Wallet}
	 */
	public void depositCoins(double amount) {
		wallet.depositCoins(amount);
	}
	
	/**
	 * Withdraws given amount from trader wallet.
	 * 
	 * @param amount Amount to be withdrew.
	 * @see {@link elements.Wallet}
	 */
	public void withdrawCoins(double amount) {
		wallet.withdrawCoins(amount);
	}
	
	/**
	 * Frees all the blocked dollars in the wallet.
	 */
	protected void freeDollars() {
		wallet.freeAllDollars();
	}
	
	/**
	 * Frees all the blocked coins in the wallet.
	 */
	protected void freeCoins() {
		wallet.freeAllCoins();
	}
	/**
	 * Called by {@link elements.Market#checkTransactions(java.util.ArrayList)}
	 * @param coinsToGet
	 * @param dollarsToSpend
	 */
	protected void spendDollarsGetCoins(double coinsToGet, double dollarsToSpend) {
		wallet.depositCoins(coinsToGet);
		wallet.withdrawDollars(dollarsToSpend);
	}
	
	/**
	 * Called by {@link elements.Market#checkTransactions(java.util.ArrayList)}
	 * Spends coins from wallet to get dollars.
	 * @param coinsToSpend Coins sold.
	 * @param dollarsToGet Dollars gotten.
	 */
	protected void sellCoinsGetDollars(double coinsToSpend, double dollarsToGet) {
		wallet.depositDollars(dollarsToGet);
		wallet.withdrawCoins(coinsToSpend);
	}
	
	@Override
	public String toString() {
		if(id == 0)
			return "Trader 0: 0.00000$ 0.00000PQ";
		return "Trader "+ id + ": " + wallet.toString();
	}

}
