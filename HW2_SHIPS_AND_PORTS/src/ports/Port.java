
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import java.util.Collections;

import containers.BasicContainer;
import containers.Container;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import ports.Coordinate;
import interfaces.IPort;
import ships.Ship;


/**
 * Ports are going to be used for loading/unloading containers.
 * 
 * @author Esad Yusuf Atik
 *
 */
public class Port implements IPort{
	/**
	 * ID of the port
	 */
	private final int ID;
	
	/**
	 * X-coordinate of the port.
	 * Coordinates can be accessed by the {@link ports.Coordinate} class as well.
	 */
	private final double X;
	
	/**
	 * Y-coordinate of the port.
	 * Coordinates can be accessed by the {@link ports.Coordinate} class as well.
	 */
	private final double Y;
	
	/**
	 * Mainly used for distance calculations.
	 */
	public final Coordinate position;
	
	/**
	 * List of ships that have visited the port.
	 * 
	 * <b>This isn't a chronologically correct record of ships that have visited.</b>
	 */
	private ArrayList<Ship> history;
	
	/**
	 * List of ships currently in the port.
	 */
	private ArrayList<Ship> current;
	
	/**
	 * List of containers currently in the port.
	 */
	private ArrayList<Container> containers;
	
	/**
	 * Constructor method for the Port class.
	 * 
	 * @param ID ID of the port
	 * @param X X-coordinate of the port
	 * @param Y Y-coordinate of the port
	 */
	public Port(int ID, double X, double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		this.position = new Coordinate(X, Y);
		
		this.history = new ArrayList<Ship>();
		this.current = new ArrayList<Ship>();
		this.containers = new ArrayList<Container>();
	}
	
	/**
	 * 
	 * @param other Destination port.
	 * @return Distance between this port and another port.
	 */
	public double getDistance(Port other) {
		
		return Coordinate.GetDistance(position, other.position);
	}
	
	/**
	 * Adds incoming ship to the current ship list 
	 * @param s incoming ship
	 * @see {@link #current}
	 */
	@Override
	public void incomingShip(Ship s) {
		if (!current.contains(s)) {
			current.add(s);
		}
		
	}
	
	/**
	 * Adds outgoing ship to the history list.
	 * @param s outgoing ship
	 * @see {@link Port#history}
	 */
	@Override
	public void outgoingShip(Ship s) {
		if (!history.contains(s)) {
			history.add(s);
		}
		
		// Don't forget to remove ship from the current ship list
		current.remove(s);
	}
	
	/**
	 * 
	 * @return ID of the port.
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Removes container from the containers list.
	 * @param cont Container to be removed.
	 * @see {@link ports.Port#containers}
	 */
	public void unloadContainer(Container cont) {
		containers.remove(cont);
	}
	
	/**
	 * Adds container to the containers list.
	 * @param cont Container to be added.
	 * @see {@link ports.Port#containers}
	 */
	public void loadContainer(Container cont) {
		if (!containers.contains(cont)) {
			containers.add(cont);
		}
	}
	/**
	 * Checks if the container is in the port.
	 * @param cont Container to be checked
	 * @return True if the container is in the port.
	 */
	public boolean hasContainer(Container cont) {
		return containers.contains(cont);
	}
	
	@Override
	public String toString() {
		// First the port info.
		String output = "Port " + ID + ": " + position.toString() + "\n";
		
		// Containers
		ArrayList<Container> basic = new ArrayList<Container>();
		ArrayList<Container> heavy = new ArrayList<Container>();
		ArrayList<Container> refrigerated = new ArrayList<Container>();
		ArrayList<Container> liquid = new ArrayList<Container>();
		
		for (Container cont: containers) {
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
			output += "  BasicContainer:";
			for (Container cont: basic) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (heavy.size() > 0) {
			output += "  HeavyContainer:";
			for (Container cont: heavy) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (refrigerated.size() > 0) {
			output += "  RefrigeratedContainer:";
			for (Container cont: refrigerated) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		if (liquid.size() > 0) {
			output += "  LiquidContainer:";
			for (Container cont: liquid) {
				output += " " + cont.getID();
			}
			output += "\n";
		}
		
		Collections.sort(current);
		
		for(Ship ship: current) {
			output += "  " + ship;
		}
		
		
		return output;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

