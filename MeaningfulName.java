// use intention-revealing names
// bad code
public List<int[]> getThem() {
	List<int[]> list1 = new ArrayList<int[]>();
	for (int[] x: theList) {
		if (x[0] == 4) {
			list1.add(x);
		}
	}
	return list1;
}

// good code
public List<int[]> getFlaggedCells() {
	List<int[]> flaggedCells = new ArrayList<int[]>();
	for (int[] cell: gameBoard)
		if (cell[STATUS_VALUE] == FLAGGED)
			flaggedCells.add(cell);
	return flaggedCells;
}

// better code
public List<Cell> getFlaggedCells() {
	List<Cell> flaggedCells = new ArrayList<Cell>();
	for (Cell cell: gameBoard)
		if (cell.isFlagged())
			flaggedCells.add(cell);
	return flaggedCells;
}

// make meaningful distinctions
// bad code
public static void copyChars(char a1[], char a2[]) {
	for (int i = 0; i < a1.length; i++) {
		a2[i] = a1[i];
	}
}

// good code
public static void copyChars(char source[], char destination[]) {
	for (int i = 0; i < source.length; i++) {
		destination[i] = source[i];
	}
}

// use pronounceable names
// bad code
class DtaRcrd102 {
	private Date genymdhms;
	private Date modymdhms;
	private final String pszqint = "102";
}

// good code
class Customer {
	private Date generationTimestamp;
	private Date modificationTimestamp;
	private final String recordId = "102";
}

// use searchable names
// bad code
for (int j = 0; j < 34; j++) {
	s += (t[j]*4)/5;
}

// good code
public int getTotalRealTaskWeeks () {
	int realDaysPerIdealDay = 4;
	const int WORK_DAYS_PER_WEEK = 5;
	int sum = 0;
	for (int i = 0; i < NUMBER_OF_TASKS; i++) {
		int realTaskDays = taskEstimate[i] * realDaysPerIdealDay;
		int realTaskWeeks = (realTaskDays / WORK_DAYS_PER_WEEK);
		sum += realTaskWeeks;
	}
	return sum;
}

// member prefixes
// bad code
public class Part {
	private String m_dsc; // The textual description
	void setName(String name) {
		m_dsc = name;
	}
}

// good code
public class Part {
	String description;
	void setDescription(String description) {
		this.description = description;
	}
}

// interfaces and implementation
// common code
interface IShapeFactory {}
class ShapeFactory implements IShapeFactory {}

// recommended code
interface ShapeFactory {}
class ShapeFactoryImp implements ShapeFactory {}
class CShapeFactory implements ShapeFactory {}

// class names
// bad code
class Manager {}
class Processor {}
class Data {}
class Info {}
class Run {} // verb

// good code
class Customer {} // noun
class WikiPage {} // noun phrase
class Account {}
class AddressParser {}

// method names
// bad code

// good code
class Example {
	public void run() {
		string name = employee.getName(); // get
		customer.setName("mike"); // set

		// static factory method with names that describe the arguments
		Complex fulcrumPoint = Complex.FromRealNumber(23.0);
		
		if (paycheck.isPosted()) { // is
			return;
		}
	}
}

// say what you mean, mean what you say
// pick one word per concept
// don't pun: avoid using the same word for two purposes
// use solution domain names: CS terms, algorithm names, pattern names, math terms and so forth
// use problem domain names: when there is no programmable-eese for what you're doing

// add meaningful context
// bad code
private void printGuessStatistics(char candidate, int count) {
	String number;
	String verb;
	String pluralModifier;
	if (count == 0) {
		number = "no";
		verb = "are";
		pluralModifier = "s";
	} else if (count == 1) {
		number = "1";
		verb = "is";
		pluralModifier = "";
	} else {
		number = Integer.toString(count);
		verb = "are";
		pluralModifier = "s";
	}
	String guessMessage = String.format(
		"There %s %s %s%s", verb, number, candidate, pluralModifier);
	print(guessMessage);
}

// good code
public class GuessStatisticsMessage {
	private String number;
	private String verb;
	private String pluralModifier;

	public String make(char candidate, int count) {
		createPluralDependentMessageParts(count);
		return String.format(
			"There %s %s %s%s",
			verb, number, candidate, pluralModifier);
	}

	private void createPluralDependentMessageParts(int count) {
		if (count == 0) {
			thereAreNoLetters();
		} else if (count == 1) {
			thereIsOneLetter();
		} else {
			thereAreManyLetters(count);
		}
	}

	private void thereAreManyLetters(int count) {
		number = Integer.toString(count);
		verb = "are";
		pluralModifier = "s";
	}

	private void thereIsOneLetter() {
		number = "1";
		verb = "is";
		pluralModifier = "";
	}

	private void thereIsNoLetter() {
		number = "no";
		verb = "are";
		pluralModifier = "s";
	}

}

// don't add gratuitous context: add no more context to a name than is necessary

