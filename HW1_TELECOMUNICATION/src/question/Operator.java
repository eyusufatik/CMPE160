
package question;

public class Operator {
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private int ID;
	private double talkingCharge;
	private double messageCost;
	private double networkCharge;
	private int discountRate;
	
	private int minutesServed;
	private int messagesSent;
	private double mbServed;
	
	public Operator(int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
		this.ID = ID;
		this.talkingCharge = talkingCharge;
		this.messageCost = messageCost;
		this.networkCharge = networkCharge;
		this.discountRate = discountRate;
	}
	
	public double calculateTalkingCost(int minute, Customer customer) {
		double cost = minute * this.talkingCharge;
		int customerAge = customer.getAge();
		
		if(customerAge < 18 || customerAge > 65) {
			cost -= cost * (float)this.discountRate / 100.0;
		}
		
		return cost;
	}
	
	public double calculateMessageCost(int quantity, Customer customer, Customer other) {
		double cost = quantity * this.messageCost;
		boolean sameOperator = (customer.getOperator().getID() == other.getOperator().getID());
		
		if(sameOperator) {
			cost -= cost * (float)this.discountRate / 100.0;
		}
		
		return cost;
	}	
	
	public double calculateNetworkCost(double amount) {
		return amount * this.networkCharge;
	}
	
	public void talk(int minute, Customer caller, Customer called) {
		double chargeAmount = this.calculateTalkingCost(minute, caller);
		caller.getBill().add(chargeAmount);
		called.getOperator().getCalled(minute, called);
		this.minutesServed += minute;
		
	}
	
	public void getCalled(int minute, Customer called) {
		this.minutesServed += minute;
		called.getCalled(minute);
	}
	
	public void message(int quantity, Customer messager, Customer messaged) {
		double chargeAmount = this.calculateMessageCost(quantity, messager, messaged);
		messager.getBill().add(chargeAmount);
		
		this.messagesSent += quantity;
		
	}
	
	public void connection(Customer user, double amount) {
		double chargeAmount = this.calculateNetworkCost(amount);
		user.getBill().add(chargeAmount);
		
		this.mbServed += amount;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public double getTalkingCharge() {
		return this.talkingCharge;
	}
	
	public void setTalkingCharge(double talkingCharge) {
		this.talkingCharge = talkingCharge;
	}
	
	public double getMessageCost() {
		return this.messageCost;
	}
	
	public void setMessageCost(double messageCost) {
		this.messageCost = messageCost;
	}
	
	public double getNetworkCharge() {
		return this.networkCharge;
	}
	
	public void setNetworkCharge(double networkCharge) {
		this.networkCharge = networkCharge;
	}
	
	public int getDiscountRate() {
		return this.discountRate;
	}
	
	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public int getMinutesServed() {
		return this.minutesServed;
	}
	
	public int getMessagesSent() {
		return this.messagesSent;
	}
	
	public double getMbServed() {
		return this.mbServed;
	}
	
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

