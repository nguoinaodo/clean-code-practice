// Class organization
// public static constants --> private static variables
// --> private instance variables
// --> public functions --> private utilities called by that
// public function

// encapsulation: loosening encapsulation is always a last resort

// class should be small: the more ambiguous the class name,
// the more likely it has to many responsibilities

// the single responsibility principle: a class or module
// should have one, and only one, reason to change
// We want our systems to be composed of many small classes,
// not a few large ones. Each small class encapsulates a single
// responsibility, has a single reason to change, and collaborates
// with a few others to achieve the desired system behaviors

// too many responsibilities
public class SuperDashboard extends JFrame implements MetaDataUser 
 public String getCustomizerLanguagePath() 
 public void setSystemConfigPath(String systemConfigPath) 
 public String getSystemConfigDocument() 
 public void setSystemConfigDocument(String systemConfigDocument) 
 public boolean getGuruState() 
 public boolean getNoviceState() 
 public boolean getOpenSourceState() 
 public void showObject(MetaObject object)
 //...

// small but still many responsibilities
public class SuperDashboard extends JFrame implements MetaDataUser
 public Component getLastFocusedComponent() 
 public void setLastFocused(Component lastFocused) 

 public int getMajorVersionNumber() 
 public int getMinorVersionNumber() 
 public int getBuildNumber() 

// a single-responsibility class
public class Version {
	public int getMajorVersionNumber()
	public int getMinorVersionNumber()
	public int getBuildNumber()
}

// cohesion: the more variables a method manipulates
// the more cohesive that method is to its class

// We would like cohesion to be high. When cohesion is high,
// it means that the methods and virables of the class are
// co-dependent and hang together as a logical whole.

// You should try to separate the variables and methods into
// two or more classes such that the new classes are more
// cohesive.

// a cohesive class
public class Stack {
	private int topOfStack = 0;
	List<Integer> elements = new LinkedList<Integer>();

	public int size() {
		return topOfStack;
	}

	public void push(int element) {
		topOfStack++;
		elements.add(element);
	}

	public int pop() throws PoppedWhenEmpty {
		if (topOfStack == 0)
			throw new PoppedWhenEmpty();
		int element = elements.get(--topOfStack);
		elements.remove(topOfStack);
		return element;
	}
}

// Maintaining cohesion results in many small classes
// big function
package literatePrimes;
public class PrintPrimes {
	public static void main(String[] args) {
		final int M = 1000;
		final int RR = 50;
		final int CC = 4;
		final int WW = 10;
		final int ORDMAX = 30;
		int P[] = new int[M + 1];
		int PAGENUMBER;
		int PAGEOFFSET;
		int ROWOFFSET;
		int C;
		int J;
		int K;
		boolean JPRIME;
		int ORD;
		int SQUARE;
		int N;
		int MULT[] = new int[ORDMAX + 1];

		J = 1;
		K = 1;
		P[1] = 2;
		ORD = 2;
		SQUARE = 9;

		while (K < M) {
			do {
				J = J + 2;
				if (J == SQUARE) {
					ORD = ORD + 1;
					SQUARE = P[ORD] * P[ORD];
					MULT[ORD - 1] = J;
				}
				N = 2;
				JPRIME = true;
				while (N < ORD && JPRIME) {
					while (MULT[N] < J)
						MULT[N] = MULT[N] + P[N] + P[N];
					if (MULT[N] == J)
						JPRIME = false;
					N = N + 1;
				}
			} while (!JPRIME);
			K = K + 1;
			P[K] = J;
		}
		{
			PAGENUMBER = 1;
			PAGEOFFSET = 1;
			while (PAGEOFFSET <= M) {
				System.out.println("The First " + M + 
									" Prime Numbers --- Page " + PAGENUMBER);
				System.out.println("");
				for (ROWOFFSET = PAGEOFFSET; ROWOFFSET < PAGEOFFSET + RR; ROWOFFSET++){
					for (C = 0; C < CC; C++)
						if (ROWOFFSET + C * RR <= M)
							System.out.format("%10d", P[ROWOFFSET + C * RR]);
					System.out.println("");
				}
				System.out.println("\f");
				PAGENUMBER = PAGENUMBER + 1;
				PAGEOFFSET = PAGEOFFSET + RR * CC;
			}
		}
	}
}

// split code
// PrimePrinter.java
package literatePrimes;

public class PrimePrinter {
	public static void main(String[] args) {
		final int NUMBER_OF_PRIMES = 1000;
		int[] primes = PrimeGenerator.generate(NUMBER_OF_PRIMES);

		final int ROWS_PER_PAGE = 50;
		final int COLUMNS_PER_PAGE = 4;
		RowColumnPagePrinter tablePrinter =
			new RowColumnPagePrinter(ROWS_PER_PAGE,
									 COLUMNS_PER_PAGE,
									 "The First " + NUMBER_OF_PRIMES +
									 	" Prime Numbers");
		tablePrinter.print(primes);	
	}
}

// RowColumnPagePrinter.java
package literatePrimes;

import java.io.PrintStream;

public class RowColumnPagePrinter {
	private int rowsPerPage;
	private int columnsPerPage;
	private int numbersPerPage;
	private String pageHeader;
	private PrintStream printStream;

	public RowColumnPagePrinter(int rowsPerPage,
								int columnsPerPage,
								String pageHeader) {
		this.rowsPerPage = rowsPerPage;
		this.columnsPerPage = columnsPerPage;
		this.pageHeader = pageHeader;
		numbersPerPage = rowsPerPage * columnsPerPage;
		printStream = System.out;
	}

	public void print(int data[]) {
		int pageNumber = 1;
		for (int firstIndexOnPage = 0;
			 firstIndexOnPage < data.length;
			 firstIndexOnPage += numbersPerPage) {
			int lastIndexOnPage =
				Math.min(firstIndexOnPage + numbersPerPage - 1,
						 data.length - 1);
			printPageHeader(pageHeader, pageNumber);
			printPage(firstIndexOnPage, lastIndexOnPage, data);
			printStream.println("\f");
			pageNumber++;
		}
	}

	private void printPage(int firstIndexOnPage,
						   int lastIndexOnPage,
						   int[] data) {
		int firstIndexOfLastRowOnPage =
			firstIndexOnPage + rowsPerPage - 1;
		for (int firstIndexInRow = firstIndexOnPage;
			 firstIndexInRow <= firstIndexOfLastRowOnPage;
			 firstIndexInRow++) {
			printRow(firstIndexInRow, lastIndexOnPage, data);
			printStream.println("");
		}
	}

	private void printRow(int firstIndexInRow,
						  int lastIndexOnPage,
						  int[] data) {
		for (int column = 0; column < columnsPerPage; column++) {
			int index = firstIndexInRow + column * rowsPerPage;
			if (index <= lastIndexOnPage)
				printStream.format("%10d", data[index]);
		}
	}

	private void printPageHeader(String pageHeader,
								 int pageNumber) {
		printStream.println(pageHeader + " --- Page " + pageNumber);
		printStream.println("");
	}

	public void setOutput(PrintStream printStream) {
		this.printStream = printStream;
	}
}

// PrimeGenerator.java
package literatePrimes;

import java.util.ArrayList;

public class PrimeGenerator {
	private static int[] primes;
	private static ArrayList<Integer> multiplesOfPrimeFactors;

	protected static int[] generate(int n) {
		primes = new int[n];
		multiplesOfPrimeFactors = new ArrayList<Integer>();
		set2AsFirstPrime();
		checkOddNumbersForSubsequentPrimes();
		return primes;
	}

	private static void set2AsFirstPrime() {
		primes[0] = 2;
		multiplesOfPrimeFactors.add(2);
	}

	private static void checkOddNumbersForSubsequentPrimes() {
		int primeIndex = 1;
		for (int candidate = 3;
			 primeIndex < primes.length;
			 candidate += 2) {
			if (isPrime(candidate))
				primes[primeIndex++] = candidate;
		}
	}

	private static boolean isPrime(int candidate) {
		if (isLeastRelevantMultipleOfNextLargerPrimeFactor(candidate)) {
			multiplesOfPrimeFactors.add(candidate);
			return false;
		}
		return isNotMultipleOfAnyPreviousPrimeFactor(candidate);
	}

	private static boolean
	isLeastRelevantMultipleOfNextLargerPrimeFactor(int candidate) {
		int nextLargerPrimeFactor = primes[multiplesOfPrimeFactors.size()];
		int leastRelevantMultiple = nextLargerPrimeFactor * nextLargerPrimeFactor;
		return candidate == leastRelevantMultiple;
	}

	private static boolean
	isNotMultipleOfAnyPreviousPrimeFactor(int candidate) {
		for (int n = 1; n < multiplesOfPrimeFactor.size(); n++) {
			if (isMultipleOfNthPrimeFactor(candidate, n))
				return false;
		}
		return true;
	}

	private static boolean
	isMultipleOfNthPrimeFactor(int candidate, int n) {
		return candidate == smallestOddNthMultipleNotLessThanCandidate(candidate, n);
	}

	private static int
	smallestOddNthMultipleNotLessThanCandidate(int candidate, int n) {
		int multiple = multiplesOfPrimeFactors.get(n);
		while (multiple < candidate)
			multiple += 2 * primes[n];
		multiplesOfPrimeFactors.set(n, multiple);
		return multiple;
	}
}