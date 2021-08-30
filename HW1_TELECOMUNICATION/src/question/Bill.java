
package question;

public class Bill {

	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	
	private double limitingAmount;
	private double currentDebt;
	
	public Bill(double limitingAmount) {
		this.limitingAmount = limitingAmount;
		this.currentDebt = 0;
	}
	
	public boolean check(double amount) { //True if the debt will exceed limitingAmount
		return currentDebt + amount > this.limitingAmount;
	}
	
	public void add(double amount) {
		this.currentDebt += amount;
	}
	
	public void pay(double amount) {
		this.currentDebt -= amount;
	}
	
	public void changeTheLimit(double amount) {
		if(amount >= this.currentDebt) {
			this.limitingAmount = amount;
		}
	}
	
	public double getLimitingAmount() {
		return this.limitingAmount;
	}
	
	public double getCurrentDebt() {
		return this.currentDebt;
	}
	
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

