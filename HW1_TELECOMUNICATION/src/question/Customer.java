
package question;

public class Customer {
	
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private int ID;
	private String name;
	private int age;
	private Operator operator;
	private Bill bill;
	
	private double moneySpent;
	private int minutesTalked;
	private int messagesSent;
	private double mbSpent;
	
	public Customer(int ID, String name, int age , Operator operator, double limitingAmount) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.operator = operator;
		this.bill = new Bill(limitingAmount);
	}

	public void talk(int minute, Customer other) {
		double chargeAmount = this.operator.calculateTalkingCost(minute, this);
		if (!this.bill.check(chargeAmount) && this.ID != other.getID()) {
			this.operator.talk(minute, this, other);
			minutesTalked += minute;
		}
		
	}
	public void getCalled(int minute) {
		this.minutesTalked += minute;
	}
	
	
	public void message(int quantity, Customer other) {
		double chargeAmount = this.operator.calculateMessageCost(quantity, this, other);
		if (!this.bill.check(chargeAmount) && this.ID != other.getID()) {
			this.operator.message(quantity, this, other);
			this.messagesSent += quantity;
		}
	}
	
	public void connection(double amount) {
		double chargeAmount = this.operator.calculateNetworkCost(amount);
		
		if(!this.bill.check(chargeAmount)) {
			this.operator.connection(this, amount);
			this.mbSpent += amount;
		}
	}
	
	public void pay(double amount) {
		double currentDebt = this.bill.getCurrentDebt();
		if (amount > currentDebt) {
			this.bill.pay(currentDebt);
			this.moneySpent += currentDebt;
		}
		else {
			this.bill.pay(amount);
			this.moneySpent += amount;
		}
	}
	
	public void changeLimit(double amount) {
		this.bill.changeTheLimit(amount);
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	public double getMoneySpent() {
		return this.moneySpent;
	}
	
	public int getMinutesTalked() {
		return this.minutesTalked;
	}
	
	public int getMessagesSent() {
		return this.messagesSent;
	}
	
	public double getMbSpent() {
		return this.mbSpent;
	}
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

