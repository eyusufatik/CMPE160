package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.Market;
import elements.Trader;

public class Main {
	public static Random random;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		long seed = in.nextLong();
		random = new Random(seed);
		
		int marketFee = in.nextInt();
		int numberOfUsers = in.nextInt();
		int numberOfQueries = in.nextInt();
		
		Market market = new Market(marketFee);
		
		// Initializing traders. Trader0 is reserved for the system.
		ArrayList<Trader> traders = new ArrayList<Trader>();
		traders.add(new Trader(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		in.nextDouble();
		in.nextDouble();
		for(int i = 1; i < numberOfUsers; i++) {
			double dollarAmount = in.nextDouble();
			double coinAmount = in.nextDouble();
			traders.add(new Trader(dollarAmount,coinAmount));
			//System.out.println(dollarAmount + " " + coinAmount);
		}
		
		// Queries
		for(int i = 0; i < numberOfQueries; i++) {
			int query = in.nextInt();
			
			if(query == 10) {		// Buy Limit
				int traderID = in.nextInt();
				double price = in.nextDouble();
				double amount = in.nextDouble();
				
				traders.get(traderID).buy(amount, price, market);
			}
			else if(query == 11) {	// Buy market
				int traderID = in.nextInt();
				double amount = in.nextDouble();
				double price = market.getCurrentBuyPrice();
			
				if(price != -1) {
					traders.get(traderID).buy(amount, price, market);
				}else {
					Market.invalidQueries++;
				}
			}
			else if(query == 20) {	// Sell limit
				int traderID = in.nextInt();
				double price = in.nextDouble();
				double amount = in.nextDouble();
				
				traders.get(traderID).sell(amount, price, market);
			}
			else if(query == 21) {	// Sell market
				int traderID = in.nextInt();
				double amount = in.nextDouble();
				double price = market.getCurrentSellPrice();
				
				if(price != -1) {
					traders.get(traderID).sell(amount, price, market);
				}else {
					Market.invalidQueries++;
				}
			}
			else if(query == 3) {	// Trader deposits money.
				int traderID = in.nextInt();
				double amount = in.nextDouble();
				
				traders.get(traderID).depositDollars(amount);
			}
			else if(query == 4) {	// Trader withdraws money.
				int traderID = in.nextInt();
				double amount = in.nextDouble();
				
				traders.get(traderID).withdrawDollars(amount);
			}
			else if(query == 5) {	// Print a trader's wallet information
				int traderID = in.nextInt();
				out.println(traders.get(traderID));
			}
			else if(query == 777) {	// Reward evry trader with PQoins.
				for(Trader t: traders) {
					t.depositCoins(random.nextDouble()*10);
				}
			}
			else if(query == 666) {	// Open market operation TODO
				double price = in.nextDouble();
				market.makeOpenMarketOperation(price, traders);
			}
			else if(query == 500) {	// Print the current market size
				out.println(market.marketSizeToString());
			}
			else if(query == 501) {	// Number of successful transactions.
				out.println(market.numberOfSuccessfulTransactionsToString());
			}
			else if(query == 502) {	// Number of invalid queries.
				out.println("Number of invalid queries: " + Market.invalidQueries);
			}
			else if(query == 505) {	// Current prices
				out.println(market.currentPricesToString());
			}
			else if(query == 555) {
				for(Trader t: traders) {
					out.println(t);
				}
			}
	
			
			
			market.checkTransactions(traders);
		}
	}

}
