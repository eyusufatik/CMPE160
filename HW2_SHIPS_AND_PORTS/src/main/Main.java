
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import ports.Port;
import ships.Ship;
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		//
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int currentContainerID = 0;
		int currentShipID = 0;
		int currentPortID = 0;
		
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ArrayList<Port> ports = new ArrayList<Port>();
		
		int n = in.nextInt();
		in.nextLine();
		for (int i = 0; i < n; i++) {
			String line = in.nextLine();
			String[] inputs = line.split(" ");
			int operation = Integer.parseInt(inputs[0]);
			
			if (operation == 1) { 		// Create container.
				int portID = Integer.parseInt(inputs[1]);
				int weight = Integer.parseInt(inputs[2]);
				Container cont;
				// Check if we are creating a refrigerated or liquid container
				if (inputs.length == 3) {
					// Basic or heacy container.
					if (weight <= 3000) {
						cont = new BasicContainer(currentContainerID, weight);
					}
					else {
						cont = new HeavyContainer(currentContainerID, weight);
					}
				}
				else {
					// R or L
					String type = inputs[3];
					if (type.compareTo("R") == 0) {
						cont = new RefrigeratedContainer(currentContainerID, weight);
					}
					else {
						cont = new LiquidContainer(currentContainerID, weight);
					}
				}
				
				containers.add(cont);
				ports.get(portID).loadContainer(cont);
				currentContainerID++;
			}
			else if (operation == 2) {	// Create ship.
				int portID = Integer.parseInt(inputs[1]);
				int maxWeight = Integer.parseInt(inputs[2]);
				int maxNoOfContainers = Integer.parseInt(inputs[3]);
				int maxNoOfHeavyContainers = Integer.parseInt(inputs[4]);
				int maxNoOfRefrigeratedContainers = Integer.parseInt(inputs[5]);
				int maxNoOfLiquidContainers = Integer.parseInt(inputs[6]);
				double fuelConsumption = Double.parseDouble(inputs[7]);
				
				Ship ship = new Ship(currentShipID, ports.get(portID), maxWeight, maxNoOfContainers,
					maxNoOfHeavyContainers, maxNoOfRefrigeratedContainers, maxNoOfLiquidContainers, fuelConsumption);

				ships.add(ship);
				currentShipID++;
				
			}
			else if (operation == 3) {	// Create port.
				double X = Double.parseDouble(inputs[1]);
				double Y = Double.parseDouble(inputs[2]);
				
				Port port = new Port(currentPortID, X, Y);
				
				ports.add(port);
				currentPortID++;
			}
			else if (operation == 4) {	// Load container to ship.
				int shipID = Integer.parseInt(inputs[1]);
				int containerID = Integer.parseInt(inputs[2]);
				
				ships.get(shipID).load(containers.get(containerID));
			}
			else if (operation == 5) {	// Unload container from the ship.
				int shipID = Integer.parseInt(inputs[1]);
				int containerID = Integer.parseInt(inputs[2]);
				
				ships.get(shipID).unLoad(containers.get(containerID));
			}
			else if (operation == 6) {	// Ship sales to a port.
				int shipID = Integer.parseInt(inputs[1]);
				int portID = Integer.parseInt(inputs[2]);
				
				ships.get(shipID).sailTo(ports.get(portID));
			}
			else if (operation == 7) {	// Ship is refueled.
				int shipID = Integer.parseInt(inputs[1]);
				double fuealAmount = Double.parseDouble(inputs[2]);
				
				ships.get(shipID).reFuel(fuealAmount);
			}
		}
	
		for(Port port: ports) {
			out.print(port);
		}
		
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

