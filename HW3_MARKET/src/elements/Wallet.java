package elements;

/**
 * Wallet class holds traders dollars and coins.
 * It also stores how much of those assets are blocked, meaning that those
 * are in an order.
 * 
 * @author Esad Yusuf Atik
 *
 */
public class Wallet {
	/**
	 * Amount of Dollars wallet holder has.
	 */
	private double dollars;
	
	/**
	 * Amount of PQoins wallet holder has.
	 */
	private double coins;
	
	/**
	 * Blocked amount of Dollars.
	 */
	private double blockedDollars;
	
	/**
	 * Blocked amount of PQoins.
	 */
	private double blockedCoins;
	
	/**
	 * Constructor for Wallet objects.
	 * @param dollars Initial amount of Dollars in the wallet.
	 * @param coins Initial amount of PQoins in the wallet.
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}
	
	/**
	 * Deposits given amount to the wallet.
	 * 
	 * @param amount Amount to be deposited.
	 * @see {@link elements.Trader#depositDollars(double)}
	 */
	protected void depositDollars(double amount) {
		if (amount > 0) {
			dollars += amount;
		}
	}
	
	/**
	 * Withdraws given amount from the wallet.
	 * 
	 * @param amount Amount to be withdrew.
	 * @see {@link elements.Trader#withdrawDollars(double)}
	 */
	protected void withdrawDollars(double amount) {
		if(amount > dollars)
			Market.invalidQueries++;
		
		if (amount > 0 && amount <= dollars) {
			dollars -= amount;
		}
		
	}
	
	/**
	 * Deposits given amount to the wallet.
	 * 
	 * @param amount Amount to be deposited.
	 * @see {@link elements.Trader#depositCoins(double)}
	 */
	protected void depositCoins(double amount) {
		if (amount > 0) {
			coins += amount;
		}
	}
	
	/**
	 * Withdraws given amount from the wallet.
	 * 
	 * @param amount Amount to be withdrew.
	 * @see {@link elements.Trader#withdrawCoins(double)}
	 */
	protected void withdrawCoins(double amount) {
		if (amount > 0 && amount <= coins) {
			coins -= amount;
		}
	}
	
	/**
	 * @return Amount of dollars the wallet holds.
	 */
	protected double getDollars() {
		return dollars;
	}
	
	protected void blockDollars(double amount) {
		if (amount >= 0) {
			dollars -= amount;
			blockedDollars += amount;
		}
	}
	
	protected void unblockDollars(double amount) {
		if (amount > 0 && amount <= blockedDollars) {
			blockedDollars -= amount;
			dollars += amount;
		}
	}
	
	/**
	 * @return Amount of coins the wallet holds.
	 */
	protected double getCoins() {
		return coins;
	}
	
	protected void blockCoins(double amount) {
		if (amount >= 0) {
			coins -= amount;
			blockedCoins += amount;
		}
	}
	
	protected void unblockCoins(double amount) {
		if (amount > 0 && amount <= blockedCoins) {
			coins += amount;
			blockedCoins -= amount;
		}
	}
	
	protected void freeAllDollars() {
		unblockDollars(blockedDollars);
	}
	
	protected void freeAllCoins() {
		unblockCoins(blockedCoins);
	}
	@Override
	public String toString() {
		return "%.5f$ %.5fPQ".formatted(dollars+blockedDollars, coins+blockedCoins);
	}
}
