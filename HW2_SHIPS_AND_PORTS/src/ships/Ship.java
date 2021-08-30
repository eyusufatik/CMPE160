
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import interfaces.IShip;
import ports.Port;

/**
 * Ships will be carrying containers from ports to other ports.
 * 
 * @author Esad Yusuf Atik
 *
 */
public class Ship implements IShip, Comparable<Ship> {
	/**
	 * ID of the ship.
	 */
	private final int ID;
	
	/**
	 * How much fuel the ship has. 
	 */
	private double fuel;
	
	/**
	 * Port that the ship is currently docked.
	 */
	public Port currentPort;
	
	/**
	 * Maximum weight of all containers onboard.
	 */
	private int totalWeightCapacity;
	
	/**
	 * Maximum number of all containers onboard.
	 */
	private int maxNumberOfAllContainers;
	
	/**
	 * Maximum number of all heavy containers onboard.
	 */
	private int maxNumberOfHeavyContainers;
	
	/**
	 * Maximum number of refrigerated containers onboard.
	 */
	private int maxNumberOfRefrigeratedContainers;
	
	/**
	 * Maximum number of liquid containers onboard.
	 */
	private int maxNumberOfLiquidContainers;
	
	/**
	 * Current number of basic containers onboard.
	 */
	private int currentNumberOfBasicContainers;
	
	/**
	 * Current number of heavy containers onboard.
	 */
	private int currentNumberOfHeavyContainers;
	
	/**
	 * Current number of refrigerated containers onboard.
	 */
	private int currentNumberOfRefrigeratedContainers;
	
	/**
	 * Current number of liquid containers onboard.
	 */
	private int currentNumberOfLiquidContainers;
	
	/**
	 * Fuel consumption per kilometers of the ship.
	 */
	private double fuelConsumptionPerKM;
	
	/**
	 * List of containers onboard.
	 */
	private ArrayList<Container> currentContainersOnboard;
	
	
	/**
	 * Constructor for the Ship class.
	 * @param ID ID of the ship.
	 * @param p Current port of the ship.
	 * @param totalWeightCapacity Total weight capacity of the ship. (weights of all the containers)
	 * @param maxNumberOfAllContainers Max number of all containers allowed onboard.
	 * @param maxNumberOfHeavyContainers Max number of heavy containers allowed onboard.
	 * @param maxNumberOfRefrigeratedContainers Max number of refrigerated containers allowed onboard.
	 * @param maxNumberOfLiquidContainers Max number of liquid containers allowed onboard.
	 * @param fuelConsumptionPerKM Fuel consumption of the ship. (per km)
	 */
	public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers,
			int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, 
			int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		this.ID = ID;
		this.currentPort = p;
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
		this.currentContainersOnboard = new ArrayList<Container>();
		
		p.incomingShip(this);
	}
	
	/**
	 * 
	 * @return Returns containers onboard in ascending order of IDs.
	 */
	public ArrayList<Container> getCurrentContainers(){
		Collections.sort(currentContainersOnboard);
		return currentContainersOnboard;
	}
	
	/**
	 * Sail to desired port, if there is enough fuel.
	 * This function will add up containers' and the ship's fuel consumption and check if it will be sufficient to arrive at the destination.
	 * @param p Destination port.
	 */
	@Override
	public boolean sailTo(Port p) {
			// Calculate total fuel consumption
			double totalFuelConsumption = fuelConsumptionPerKM; // start with the ship's
			for (Container c: currentContainersOnboard) {
				totalFuelConsumption += c.consumption();
			}
			
			double distance = currentPort.getDistance(p);
			double fuelNeeded = distance * totalFuelConsumption;
			
			if (fuelNeeded > fuel) {
				// there isn't enough fuel to sail
				return false;
			}
			else {
				// sail
				currentPort.outgoingShip(this);
				p.incomingShip(this);
				currentPort = p;
				fuel -= fuelNeeded;
				return true;
			}
		
	}

	/**
	 * Refuels the ship 
	 * @param newFuel Amount of fuel to load.
	 * @see {@link Ship#fuel}
	 */
	@Override
	public void reFuel(double newFuel) {
		fuel += newFuel;
	}

	/**
	 * Loads the container to the ship, if the conditions allow.
	 * 
	 * <ol>
	 * <li>Container must be in the same port with the ship.</li>
	 * <li>Current weight + cont weight must be less than {@link ships.Ship#totalWeightCapacity}</li>
	 * <li>{@link ships.Ship#maxNumberOfAllContainers} must not be exceeded.</li>
	 * <li>{@link ships.Ship#maxNumberOfHeavyContainers} must not be exceeded.</li>
	 * <li>{@link ships.Ship#maxNumberOfLiquidContainers} must not be exceeded.</li>
	 * <li>{@link ships.Ship#maxNumberOfRefrigeratedContainers} must not be exceeded.</li>
	 * </ol>
	 * 
	 * @param cont Container to be loaded.
	 */
	@Override
	public boolean load(Container cont) {
		// First check if the container and the ship is in the same port.
		if (!currentPort.hasContainer(cont)) {
			return false;
		}
		
		// Then check if the weight limit will be exceeded
		int currentWeight = 0;
		
		for(Container c: currentContainersOnboard) {
			currentWeight += c.getWeight();
		}
		
		if (currentWeight + cont.getWeight() > totalWeightCapacity) {
			return false;
		}
		
		// Then check container number limitations
		if (currentContainersOnboard.size() + 1 > maxNumberOfAllContainers) {
			return false;
		}
		if (cont instanceof HeavyContainer) {
			if (currentNumberOfHeavyContainers + 1 > maxNumberOfHeavyContainers) {
				return false;
			}
			
			if (cont instanceof LiquidContainer && currentNumberOfLiquidContainers + 1 > maxNumberOfLiquidContainers) {
				return false;
			}
			
			if (cont instanceof RefrigeratedContainer && currentNumberOfRefrigeratedContainers + 1 > maxNumberOfRefrigeratedContainers) {
				return false;
			}
		}
		
		// If the function still hasn't returned, the container can be loaded.
		if (cont instanceof BasicContainer) {
			currentNumberOfBasicContainers++;
		}
		else if (cont instanceof LiquidContainer) {
			currentNumberOfLiquidContainers++;
			currentNumberOfHeavyContainers++;
		}
		else if (cont instanceof RefrigeratedContainer) {
			currentNumberOfRefrigeratedContainers++;
			currentNumberOfHeavyContainers++;
		}
		else {
			// HeavyContainer
			currentNumberOfHeavyContainers++;
		}
		currentContainersOnboard.add(cont);
		
		currentPort.unloadContainer(cont);
		return true;
	}

	/**
	 * Unloads given container from the ship and puts it on the port.
	 * 
	 * @param cont Container to be unloaded.
	 * 
	 * @see ports.Port#containers
	 */
	@Override
	public boolean unLoad(Container cont) {
		// Check if the container is on the ship
		if (currentContainersOnboard.contains(cont)) {
			currentContainersOnboard.remove(cont);
			currentPort.loadContainer(cont);
			
			if (cont instanceof BasicContainer) {
				currentNumberOfBasicContainers--;
			}
			else if (cont instanceof LiquidContainer) {
				currentNumberOfLiquidContainers--;
				currentNumberOfHeavyContainers--;
			}
			else if (cont instanceof RefrigeratedContainer) {
				currentNumberOfRefrigeratedContainers--;
				currentNumberOfHeavyContainers--;
			}
			else {
				// HeavyContainer
				currentNumberOfHeavyContainers--;
			}
			
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public String toString() {
		String output = "Ship " + ID + ": " + String.format("%.2f", fuel) + "\n";
				
		// Containers
		ArrayList<Container> basic = new ArrayList<Container>();
		ArrayList<Container> heavy = new ArrayList<Container>();
		ArrayList<Container> refrigerated = new ArrayList<Container>();
		ArrayList<Container> liquid = new ArrayList<Container>();
		
		for (Container cont: currentContainersOnboard) {
			if (cont instanceof BasicContainer) {
				basic.add(cont);
			}
			else if (cont instanceof LiquidContainer) {
				liquid.add(cont);
			}
			else if (cont instanceof RefrigeratedContainer) {
				refrigerated.add(cont);
			}
			else {
				heavy.add(cont);
			}
		}
		
		Collections.sort(basic);
		Collections.sort(heavy);
		Collections.sort(refrigerated);
		Collections.sort(liquid);
		
		if (basic.size() > 0) {
			output += "    BasicContainer:";
			for (Container cont: basic) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (heavy.size() > 0) {
			output += "    HeavyContainer:";
			for (Container cont: heavy) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (refrigerated.size() > 0) {
			output += "    RefrigeratedContainer:";
			for (Container cont: refrigerated) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (liquid.size() > 0) {
			output += "    LiquidContainer:";
			for (Container cont: liquid) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		return output;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Ship) {
			Ship s = (Ship) other;
			boolean bool = (ID == s.getID());
			return bool;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return ID of the ship.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Compares two ships.
	 * @param other Ship to compare to.
	 * @return True if ID of the containers are the same.
	 */
	@Override
	public int compareTo(Ship o) {
		
		return ID - o.getID();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

