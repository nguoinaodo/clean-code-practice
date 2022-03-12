// Objects and Data Structures
// data abstraction
// concrete point
public class Point {
	public double x;
	public double y;
}

// abstract point
public interface Point {
	double getX();
	double getY();
	void setCartesian(double x, double y);
	double getR();
	double getTheta();
	void setPolar(double r, double theta);
}

// hiding implementation is about abstractions
// concrete vehicle
public interface Vehicle {
	double getFuelTankCapacityInGallons();
	double getGallonsOfGasoline();
}

// abstract vehicle
public interface Vehicle {
	double getPercentFuelRemaining();
}

// we want to express our data in abstract terms

// serious thought needs to be put into the best way
// to represent the data that an object contains
// the worst option is to blithely add getters and setters

/**
 * Data/Object Anti-Symmetry
 * Objects hide their data behind abstractions and expose
 * functions that operate on that data.
 * Data structure expose their data and have no meaningful
 * functions.
 */
// procedural shape
public class Square {
	public Point topLeft;
	public double side;
}

public class Rectangle {
	public Point topLeft;
	public double height;
	public double width;
}

public class Circle {
	public Point center;
	public double radius;
}

public class Geometry {
	public final double PI = 3.14;

	public double area(Object shape) throws NoSuchShapeException
	{
		if (shape instanceof Square) {
			Square s = (Square)shape;
			return s.side * s.side;
		}
		else if (shape instanceof Rectangle) {
			Rectangle r = (Rectangle)shape;
			return r.height * r.width;
		}
		else if (shape instanceof Circle) {
			Circle c = (Circle)shape;
			return PI * c.radius * c.radius;
		}
		throws new NoSuchShapeException();
	}
}

// polymorphic shapes
public class Square implements Shape {
	private Point topLeft;
	private double side;

	public double area() {
		return side*side;
	}
}

public class Rectangle implements Shape {
	private Point topLeft;
	private double height;
	private double width;

	public double area() {
		return height * width;
	}
}

public class Circle implements Shape {
	private Point center;
	private double radius;
	public final double PI = 3.14;

	public double area() {
		return PI * radius * radius;
	}
}

/** 
 * Procedural code (code using data structures) makes it easy
 * to add new functions without changing the existing data structures.
 * OO code makes it easy to add new classes without changing
 * existing functions.
 */

/**
 * Procedural code makes it hard to add new data structures
 * because all the functions must change.
 * OO code makes it hard to add new functions because
 * all the class must change.
 */

/**
 * Mature programmers know that the idea that everything 
 * is an object is a myth. Sometimes you really do do want
 * simple data structures with procedures operating on them. 
 */

// The Law of Demeter
/**
 * A module should not know about the innards of the objects
 * it manipulates.
 * 
 * A method f of a class C should only call the methods of
 * these: C, an object created by f, an object passed as an
 * argument to f, an object held in an instance variable of C
 * 
 * The method should not invoke methods on objects that 
 * are return by any of the allowed functions.
 */ 
// code that violates the Law of Demeter
final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();

// train wrecks
// better code
Options opts = ctxt.getOptions();
File scratchDir = opts.getScratchDir();
final String outputDir = scratchDir.getAbsolutePath();

// or
final String outputDir = ctxt.options.scratchDir.absolutePath;

// hiding structure
// not good code
String outFile = outputDir + "/" + className.replace('.', '/') + ".class";
FileOutputStream fout = new FileOutputStream(outFile);
BufferedOutputStream bos = new BufferOutputStream(fout);

// better code
BufferOutputStream bos = ctxt.createScratchFileStream(classFileName);

// data transfer objects
// a class with public variables and no functions
public class Address {
	private String street;
	private String streetExtra;
	private String city;
	private String state;
	private String zip;

	public Address(String street, String streetExtra,
		String city, String state, String zip) {
		this.street = street;
		this.streetExtra = streetExtra;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetExtra() {
		return streetExtra;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}
}

// active records: specical forms of DTOs
// they are data structures with public variables, but
// they typically have navigational methods like save and find
// typically these Active Records are direct translations from
// database tables, or other data sources