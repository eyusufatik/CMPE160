package ports;

import java.lang.Math;
/**
 * This class is used for handling coordinates of ports and finding distances from port to port.
 * 
 * @author Esad Yusuf Atik
 *
 */
public class Coordinate {

	/**
	 * X-coordinate
	 */
	private final double X;
	
	/**
	 * Y-coordinate
	 */
	private final double Y;
	
	/**
	 * Constructor method for the Coordinate class.
	 * 
	 * @param X X-coordinate
	 * @param Y Y-coordinate
	 */
	public Coordinate(double X, double Y) {
		this.X = X;
		this.Y = Y;
	}
	
	/**
	 * 
	 * @param origin Origin coordinates
	 * @param destination Destination coordinates
	 * @return Distance between origin and destination
	 */
	public static double GetDistance(Coordinate origin, Coordinate destination) {
		double xDifference = Math.abs(origin.X - destination.X);
		double yDifference = Math.abs(origin.Y - destination.Y);
		
		return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	@Override
	public String toString() {
		return "(" + String.format("%.2f", X) + ", " + String.format("%.2f", Y) + ")";
	}
}
