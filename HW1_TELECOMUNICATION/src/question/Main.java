
package question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {


	public static void main(String args[]) {

		Customer[] customers;
		Operator[] operators;

		int C, O, N;

		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
		try {
			PrintStream outstream = new PrintStream(outFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Scanner reader;
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			return;
		}

		C = reader.nextInt();
		O = reader.nextInt();
		N = reader.nextInt();

		customers = new Customer[C];
		operators = new Operator[O];

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
		PrintStream outstream1;
		try {
		        outstream1 = new PrintStream(outFile);
		}catch(FileNotFoundException e2) {
		        e2.printStackTrace();
		        return;
		}
		
		int customerCount = 0;
		int operatorCount = 0;
		reader.nextLine();
		for (int i = 0; i < N; i++) {
			String line = reader.nextLine();
			
			String[] parameters = line.split(" ");
			int action = Integer.parseInt(parameters[0]);

			if(action == 1) {
				String name = parameters[1];
				int age = Integer.parseInt(parameters[2]);
				int operatorID = Integer.parseInt(parameters[3]);
				double limitingAmount = Double.parseDouble(parameters[4]);
				
				customers[customerCount] = new Customer(customerCount, name, age, operators[operatorID], limitingAmount);
				
				customerCount++;
			}
			else if(action == 2) { //Creating operator
				double talkingCharge = Double.parseDouble(parameters[1]);
				double messageCost = Double.parseDouble(parameters[2]);
				double networkCharge = Double.parseDouble(parameters[3]);
				int discountRate = Integer.parseInt(parameters[4]);
				
				operators[operatorCount] = new Operator(operatorCount, talkingCharge, messageCost, networkCharge, discountRate);
				
				operatorCount++;
			}
			else if(action == 3) { //Customer talks to another customer
				int callerCustomerID = Integer.parseInt(parameters[1]);
				int calledCustomerID = Integer.parseInt(parameters[2]);
				int minute = Integer.parseInt(parameters[3]);
				
				customers[callerCustomerID].talk(minute, customers[calledCustomerID]);
			}
			else if(action == 4) { //Customer sends message
				int messagerCustomerID = Integer.parseInt(parameters[1]);
				int messagedCustomerID = Integer.parseInt(parameters[2]);
				int quantity = Integer.parseInt(parameters[3]);
				
				customers[messagerCustomerID].message(quantity, customers[messagedCustomerID]);
			}
			else if(action == 5) { //Customer connects to the internet
				int customerID = Integer.parseInt(parameters[1]);
				double amount = Double.parseDouble(parameters[2]);
				
				customers[customerID].connection(amount);
			}
			else if(action == 6) { //Customer pays bill
				int customerID = Integer.parseInt(parameters[1]);
				double amount = Double.parseDouble(parameters[2]);
				
				customers[customerID].pay(amount);
			}
			else if(action == 7) { //Customer changes operator
				int customerID = Integer.parseInt(parameters[1]);
				int operatorID = Integer.parseInt(parameters[2]);
				
				customers[customerID].setOperator(operators[operatorID]);
			}
			else if(action == 8) { //Customer changes bill limit
				int customerID = Integer.parseInt(parameters[1]);
				double amount = Double.parseDouble(parameters[2]);
				
				customers[customerID].changeLimit(amount);
			}
		}
		
		for (Operator operator : operators) {
			outstream1.format("Operator %d : %d %d %.2f\n", operator.getID(), operator.getMinutesServed(), operator.getMessagesSent(), operator.getMbServed());
		}
		
		int maxTalk = 0;
		int maxTalkerID = 0;
		int maxMsg = 0;
		int maxMsgID = 0;
		double maxMb = 0;
		int maxMbID = 0;
		
		for(Customer customer : customers) {
			int ID = customer.getID();
			int minute = customer.getMinutesTalked();
			int message = customer.getMessagesSent();
			double mb = customer.getMbSpent();
			outstream1.format("Customer %d : %.2f %.2f\n", ID, customer.getMoneySpent(), customer.getBill().getCurrentDebt());
			
			if (minute > maxTalk) {
				maxTalkerID = ID;
				maxTalk = minute;
			}
			
			if (message > maxMsg) {
				maxMsgID = ID;
				maxMsg = message;
			}
			
			if (mb > maxMb) {
				maxMbID = ID;
				maxMb = mb;
			}
		}
		
		outstream1.format("%s : %d\n", customers[maxTalkerID].getName(), maxTalk);
		outstream1.format("%s : %d\n", customers[maxMsgID].getName(), maxMsg);
		outstream1.format("%s : %.2f\n", customers[maxMbID].getName(), maxMb);

		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
	} 
}

