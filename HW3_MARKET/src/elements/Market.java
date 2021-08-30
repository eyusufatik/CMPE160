package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Market class is used for market operations.
 * @author Esad Yusuf Atik
 *
 */
public class Market {
	/**
	 * Number of invalid queries. Invalid operations:
	 *
	 * <ul>
	 * 	<li>Trader doesn't have enough Dollars to buy PQoin.</li>
	 * 	<li>Trader doesn't have enough PQoins to sell.</li>
	 * 	<li>Trader doesn't have enough Dollars to withdraw.</li>
	 * 	<li>Trader sends a market order but there is no current price.</li>
	 * </ul>
	 */
	public static int invalidQueries = 0;
	/**
	 * Priority Queue of selling orders. 
	 * 
	 * Order with the lowest price is on top.
	 */
	private PriorityQueue<SellingOrder> sellingOrders;
	
	/**
	 * Priority Queue of selling orders.
	 * 
	 * Order with the highest price is on top.
	 */
	private PriorityQueue<BuyingOrder> buyingOrders;
	
	/**
	 * ArrayList of transactions.
	 */
	private ArrayList<Transaction> transactions; 
	
	/**
	 * Fee for transactions per thousand.
	 */
	private final int marketFee;
	
	/**
	 * Constructor for Market objects.
	 * @param fee Market fee for transactions.
	 */
	public Market(int fee) {
		this.marketFee = fee;
		this.buyingOrders = new PriorityQueue<BuyingOrder>();
		this.sellingOrders = new PriorityQueue<SellingOrder>();
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Adds given order to {@link elements.Market#sellingOrders}
	 * @param order Buy order from trader.
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	
	/**
	 * Adds given order to {@link elements.Market#buyingOrders}
	 * @param order Buy order from trader.
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}
	
	/**
	 * Manipulates the market in order to pull the price to desired level.
	 * @param price Desired price level.
	 * @param traders Traders ArrayList. See also {@link elements.Market#checkTransactions(ArrayList)}
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
		try {
		
		double topOfBuyPQPrice = getCurrentSellPrice();
		double topOfSellPQPrice = getCurrentBuyPrice();
		
		
		
			while(topOfBuyPQPrice!= -1 && price <= topOfBuyPQPrice) {
				BuyingOrder buyOrder = buyingOrders.peek();
				double amount = buyOrder.getAmount();
				double orderPrice = buyOrder.getPrice();
				SellingOrder sellOrder = new SellingOrder(0, amount, orderPrice);
				giveSellOrder(sellOrder);
				checkTransactions(traders);

				topOfBuyPQPrice = buyingOrders.peek().getPrice();
			}

			
		while(topOfSellPQPrice != -1 && price >= topOfSellPQPrice) {
				SellingOrder sellOrder = sellingOrders.peek();
				double amount = sellOrder.getAmount();
				double orderPrice = sellOrder.getPrice();
				BuyingOrder buyOrder = new BuyingOrder(0, amount, orderPrice);
				giveBuyOrder(buyOrder);
				checkTransactions(traders);
				
				topOfSellPQPrice = sellingOrders.peek().getPrice();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	/**
	 * Matches buying orders and selling order and makes transactions.
	 * @param traders
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		if(buyingOrders.isEmpty() || sellingOrders.isEmpty()) {
			return;
		}
		BuyingOrder topBuyingOrder = buyingOrders.peek();
		SellingOrder topSellingOrder = sellingOrders.peek();
		double buyingPrice = topBuyingOrder.getPrice();
		double sellingPrice = topSellingOrder.getPrice();

		if(buyingPrice >= sellingPrice) {	// There is an overlap
			topBuyingOrder = buyingOrders.poll();
			topSellingOrder = sellingOrders.poll();
			
			double buyingAmount = topBuyingOrder.getAmount();
			double sellingAmount = topSellingOrder.getAmount();
			
			BuyingOrder executedBuyOrder;
			SellingOrder executedSellOrder;
			
			double coinsBuyerGets;
			double dollarsBuyerSpends;
			
			double dollarsSellerGets;
			double coinsSellerSpends;
			double sellerExcessCoins;
			
			if (sellingAmount <= buyingAmount) {
				coinsBuyerGets = sellingAmount;
				dollarsBuyerSpends = sellingAmount * sellingPrice;
				
				dollarsSellerGets = sellingPrice * sellingAmount * (1-(float)marketFee/1000.0);
				coinsSellerSpends = sellingAmount;
				sellerExcessCoins = 0;
			}else {
				coinsBuyerGets = buyingAmount;
				dollarsBuyerSpends = sellingPrice * buyingAmount;
				
				dollarsSellerGets = buyingAmount * sellingPrice * (1-(float)marketFee/1000.0);
				coinsSellerSpends = buyingAmount;
				sellerExcessCoins = sellingAmount - buyingAmount;
			}

			Trader buyer = traders.get(topBuyingOrder.getTraderID());
			Trader seller = traders.get(topSellingOrder.getTraderID());
			
			buyer.freeDollars();
			buyer.spendDollarsGetCoins(coinsBuyerGets, dollarsBuyerSpends);
			if (buyingAmount - sellingAmount > 0)
				buyer.buy(buyingAmount - sellingAmount, buyingPrice, this);
			
			
			seller.freeCoins();
			seller.sellCoinsGetDollars(coinsSellerSpends, dollarsSellerGets);
			if(sellerExcessCoins > 0) {
				seller.sell(sellerExcessCoins, sellingPrice, this);
			}
			executedSellOrder =  new SellingOrder(topSellingOrder.getTraderID(), coinsBuyerGets, sellingPrice);
			executedBuyOrder = new BuyingOrder(topBuyingOrder.getTraderID(), coinsBuyerGets, sellingPrice);
			
			Transaction transaction = new Transaction(executedBuyOrder, executedSellOrder);
			transactions.add(transaction);
		}
	}
	
	
	/**
	 * 
	 * @return Lowest selling order's price.
	 */
	public double getCurrentBuyPrice() {
		SellingOrder sellOrder = sellingOrders.peek();
		
		if(sellOrder != null) {
			return sellOrder.getPrice();
		}else {
			return -1;
		}
	}
	
	/**
	 * 
	 * @return Highest buying order's price.
	 */
	public double getCurrentSellPrice() {
		BuyingOrder buyOrder = buyingOrders.peek();
		
		if(buyOrder != null) {
			return buyOrder.getPrice();
		}else {
			return -1;
		}
	}
	
	/**
	 * Answers market size query (query no: 500)
	 * @return Current market size: 'total_$_in_buying_pq' 'total_PQoin_in_selling_pq'
	 */
	public String marketSizeToString() {
		double totalDollarsInBuyingPQ = 0;
		for(BuyingOrder bo: buyingOrders)
			totalDollarsInBuyingPQ += bo.getPrice() * bo.getAmount();
		
		double PQoinsInSellingPQ = 0;
		for(SellingOrder so: sellingOrders)
			PQoinsInSellingPQ += so.getAmount();
		
		return "Current market size: %.5f %.5f".formatted(totalDollarsInBuyingPQ, PQoinsInSellingPQ);
	}
	
	/**
	 * Answers number of successful transactions query (query no: 501)
	 * 
	 * @return Number of successful transactions: 'num_of_successful_transaction'
	 */
	public String numberOfSuccessfulTransactionsToString() {
		return "Number of successful transactions: " + transactions.size();
	}
	
	/**
	 * Answers current price query (query no: 505)
	 * @return Current market size: 'cp_buying' 'cp_selling' 'cp_average'
	 */
	public String currentPricesToString() {
		double curBuyPrice = getCurrentBuyPrice();
		double curSellPrice = getCurrentSellPrice();
		
		double sum = 0;
		double divider = 0;
		
		String s = "Current prices:";
		
		
		if (curSellPrice != -1) {
			sum += curSellPrice;
			divider++;
			s += " %.5f".formatted(curSellPrice);
		}else
			s += " 0.00000";
		
		if (curBuyPrice != -1) {
			sum += curBuyPrice;
			divider++;
			s += " %.5f".formatted(curBuyPrice);
		}else
			s += " 0.00000";
		
		
		if(divider != 0) {
			sum = sum / divider;
			s += " %.5f".formatted(sum);
		}else
			s += " 0.00000";
		
		return s;
		
	}

}
