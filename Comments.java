//// comment are always failures

//// we must have them because we cannot always figure out how to
//// express ourselves without them, but their use is not a cause
//// for celebration

//// inaccurate comments are far worse than no comment at all

//// truth can only be found in one place: the code

//// comments do not make up for bad code

//// explaing yourself in code
//// bad code with comment

// Check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) &&
	(employee.age > 65))

//// good code with no comment
if (employee.isEligibleForFullBenefits())

//// good comments
//// legal comments

// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

//// informative comments

// Returns an instance of the Responder being tested.
protected abstract Responder responderInstance();

//// good code with no comment
protected abstract Responder responderBeingTested();

//// code with comment
// format matched kk:mm:ss EEE, MMM dd, yyyy
Pattern timeMatcher = Pattern.compile(
	"\\d*:\\d*:\\d* \\w*, \\w* \\d*, \\d*");

//// better code: move this code to a special class that
//// converted the formats of dates and times

//// explaination of intent
public int compareTo(Object o) {
	if (o instanceof WikiPagePath) {
		WikiPagePath p = (WikiPagePath) o;
		String compressedName = StringUtil.join(names, "");
		String compressedArgumentName = StringUtil.join(p.names, "");
		return compressedName.compareTo(compressedArgumentName);
	}
	return 1; // we are greater because we are the right type
}

public void testConcurrentAddWidgets() throws Exception {
	WidgetBuilder widgetBuilder =
		new WidgetBuilder(new Class[]{BoldWidget.class});
	String text = "'''bold text'''";
	ParentWidget parent =
		new BoldWidget(new MockWidgetRoot(), "'''bold text'''");
	AtomicBoolean failFlag = new AtomicBoolean();
	failFlag.set(false);

	//This is our best attempt to get a race condition
	//by creating large number of threads.
	for (int i = 0; i < 25000; i++) {
		WidgetBuilderThread widgetBuilderThread =
			new WidgetBuilderThread(widgetBuilder, text, parent, failFlag);
		Thread thread = new Thread(widgetBuilderThread);
		thread.start();
	}
	assertEquals(false, failFlag.get());
}

//// clarification
//// code with helpful but risky comments
public void testCompareTo() throws Exception {
	WikiPagePath a = PathParser.parse("PageA");
	WikiPagePath ab = PathParser.parse("PageA.PageB");
	WikiPagePath b = PathParser.parse("PageB");
	WikiPagePath aa = PathParser.parse("PageA.PageA");
	WikiPagePath bb = PathParser.parse("PageB.PageB");
	WikiPagePath ba = PathParser.parse("PageB.PageA");

	assertTrue(a.compareTo(a) == 0);    // a == a
	assertTrue(a.compareTo(b) != 0);    // a != b
	assertTrue(ab.compareTo(ab) == 0);  // ab == ab
	assertTrue(a.compareTo(b) == -1);   // a < b
	assertTrue(aa.compareTo(ab) == -1); // aa < ab
	assertTrue(ba.compareTo(bb) == -1); // ba < bb
	assertTrue(b.compareTo(a) == 1);    // b > a
	assertTrue(ab.compareTo(aa) == 1);  // ab > aa
	assertTrue(bb.compareTo(ba) == 1);  // bb > ba
}

//// warning of consequences
//// code with warning comments

// Don't run unless you
// have some time to kill
public void _testWithReallyBigFile()
{
	writeLinesToFile(10000000);

	response.setBody(testFile);
	response.readyToSend(this);
	String responseString = output.toString();
	assertSubString("Content-Length: 1000000000", responseString);
	assertTrue(bytesSent > 1000000000);
}

public static SimpleDateFormat makeStandardHttpDateFormat()
{
	//SimpleDateFormat is not thread safe,
	//so we need to create each instance independently.
	SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM  yyyy HH:mm:ss z");
	df.setTimeZone(TimeZone.getTimeZone("GMT"));
	return df;
}

//// TODO comments

//TODO-MdM these are not needed
// We expect this to go away when we do the checkout model
protected VersionInfo makeVersion() throws Exception
{
	return null;
}

//// Amplification
String listItemContent = match.group(3).trim();
// the trim is real important. It removes the starting
// spaces that could cause the item to be recognized
// as another list.
new ListItemWidget(this, listItemContent, this.level + 1);
return buildList(text.substring(match.end()));

//// Javadocs in Public APIs

//// bad comments - mumbling
public void loadProperties()
{
	try
	{
		String propertiesPath = propertiesLocation + "/" + PROPERTIES_FILE;
		FileInputStream propertiesStream = new FileInputStream(propertiesPath);
		loadProperties.load(propertiesStream);
	}
	catch(IOException e)
	{
		// No properties files means all defaults are loaded
	}
}
//// any comment that forces you to look in another module for
//// the meaning of that comment has failed to communicate to
//// you and is not worth the bits it consumes

//// redundant comments

// Utility method that returns when this.closed is true. Throws an exception
// if the timeout is reached.
public synchronized void waitForClose(final long timeoutMillis) 
throws Exception
{
	if(!closed)
	{
		wait(timeoutMillis);
		if(!closed)
			throw new Exception("MockResponseSender could not be closed");
	}
}

public abstract class ContainerBase
 implements Container, Lifecycle, Pipeline, 
 MBeanRegistration, Serializable {
 /**
 * The processor delay for this component.
 */
 protected int backgroundProcessorDelay = -1;
 /**
 * The lifecycle event support for this component.
 */
 protected LifecycleSupport lifecycle = 
 new LifecycleSupport(this);
 /**
 * The container event listeners for this Container.
 */
 protected ArrayList listeners = new ArrayList();
 /**
 * The Loader implementation with which this Container is
 * associated.
 */
 protected Loader loader = null;
}


//// misleading comments
//// mandated comments - This clutter adds nothing
//// and serves only to obfuscate the code and create the
//// potential for lies and misdirection.
/**
 * 
 * @param title The title of the CD
 * @param author The author of the CD
 * @param tracks The number of tracks on the CD
 * @param durationInMinutes The duration of the CD in minutes
 */
public void addCD(String title, String author, 
 int tracks, int durationInMinutes) {
 CD cd = new CD();
 cd.title = title;
 cd.author = author;
 cd.tracks = tracks;
 cd.duration = duration;
 cdList.add(cd);
}

//// journal comments - should be completely removed

/** Changes (from 11-Oct-2001)
 * --------------------------
 * 11-Oct-2001 : Re-organised the class and moved it to new package 
 * com.jrefinery.date (DG);
 * 05-Nov-2001 : Added a getDescription() method, and eliminated NotableDate 
 * class (DG);
 * 12-Nov-2001 : IBD requires setDescription() method, now that NotableDate 
 * class is gone (DG); Changed getPreviousDayOfWeek(), 
 * getFollowingDayOfWeek() and getNearestDayOfWeek() to correct 
 * bugs (DG);
 * 05-Dec-2001 : Fixed bug in SpreadsheetDate class (DG);
 * 29-May-2002 : Moved the month constants into a separate interface 
 * (MonthConstants) (DG);
 * 27-Aug-2002 : Fixed bug in addMonths() method, thanks to N???levka Petr (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 29-May-2003 : Fixed bug in addMonths method (DG);
 * 04-Sep-2003 : Implemented Comparable. Updated the isInRange javadocs (DG);
 * 05-Jan-2005 : Fixed bug in addYears() method (1096282) (DG);
 */

//// noise comments

/**
 * Default constructor.
 */
protected AnnualDateRule() {
}

/** The day of the month. */
 private int dayOfMonth;

/**
 * Returns the day of the month.
 *
 * @return the day of the month.
 */
public int getDayOfMonth() {
 return dayOfMonth;
}

//// bad code with noise comments
private void startSending()
{
	try
	{
		doSending();
	}
	catch(SocketException e)
	{
		// normal. someone stopped the request.
	}
	catch(Exception e)
	{
		try
		{
			response.add(ErrorResponder.makeExceptionString(e));
			response.closeAll();
		}
		catch(Exception e1)
		{
			//Give me a break!
		}
	}
}

//// better code
private void startSending() {
	try {
		doSending();
	} catch (SocketException e) {
		// normal, someone stopped the request
	} catch (Exception e) {
		addExceptionAndCloseResponse(e);
	}
}

private void addExceptionAndCloseResponse(Exception e) {
	try {
		response.add(ErrorResponder.makeExceptionString(e));
		response.closeAll();
	} catch (Exception e1) {
	}
}

//// scary noise

/** The name. */
private String name;

/** The version. */
private String version;

/** The licenceName. */
private String licenceName;

/** The version. */
private String info;


//// don't use a comment when you can use a function or a variable
//// code with comment

// does the module from the global list <mod> depend on the
// subsystem we are part of?
if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))

//// code without comment
ArrayList moduleDependees = smodule.getDependSubsystems();
String ourSubSystem = subSysMod.getSubSystem();
if (moduleDependees.contains(ourSubSystem))


//// bad comment - position markers
// Actions /////////////////////////////////

//// bad comment - closing brace comment
public class wc {
 public static void main(String[] args) {
  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  String line;
  int lineCount = 0;
  int charCount = 0;
  int wordCount = 0;
  try {
   while ((line = in.readLine()) != null) {
    lineCount++;
    charCount += line.length();
    String words[] = line.split("\\W");
    wordCount += words.length;
   } //while
   System.out.println("wordCount = " + wordCount);
   System.out.println("lineCount = " + lineCount);
   System.out.println("charCount = " + charCount);
  } // try
  catch (IOException e) {
   System.err.println("Error:" + e.getMessage());
  } //catch
 } //main
}

//// bad comment - attributions and bylines
/* Added by Rick */

//// bad comment - commented-out code

InputStreamResponse response = new InputStreamResponse();
response.setBody(formatter.getResultStream(), formatter.getByteCount());
// InputStream resultsStream = formatter.getResultStream();
// StreamReader reader = new StreamReader(resultsStream);
// response.setContent(reader.read(formatter.getByteCount()));

//// bad comment - HTML comments
/**
 * Task to run fit tests. 
 * This task runs fitnesse tests and publishes the results.
 * <p/>
 * <pre>
 * Usage:
 * &lt;taskdef name=&quot;execute-fitnesse-tests&quot; 
 * classname=&quot;fitnesse.ant.ExecuteFitnesseTestsTask&quot; 
 * classpathref=&quot;classpath&quot; /&gt;
 * OR
 * &lt;taskdef classpathref=&quot;classpath&quot; 
 * resource=&quot;tasks.properties&quot; /&gt;
 * <p/>
 * &lt;execute-fitnesse-tests 
 * suitepage=&quot;FitNesse.SuiteAcceptanceTests&quot; 
 * fitnesseport=&quot;8082&quot; 
 * resultsdir=&quot;${results.dir}&quot; 
 * resultshtmlpage=&quot;fit-results.html&quot; 
 * classpathref=&quot;classpath&quot; /&gt;
 * </pre>
 */

//// bad comment - nonlocal information
/**
 * Port on which fitnesse would run. Defaults to <b>8082</b>.
 *
 * @param fitnessePort
 */
public void setFitnessePort(int fitnessePort)
{
	this.fitnessePort = fitnessePort;
}


//// bad comment - too much information
/*
 RFC 2045 - Multipurpose Internet Mail Extensions (MIME) 
 Part One: Format of Internet Message Bodies
 section 6.8. Base64 Content-Transfer-Encoding
 The encoding process represents 24-bit groups of input bits as output 
 strings of 4 encoded characters. Proceeding from left to right, a 
 24-bit input group is formed by concatenating 3 8-bit input groups. 
 These 24 bits are then treated as 4 concatenated 6-bit groups, each 
 of which is translated into a single digit in the base64 alphabet. 
 When encoding a bit stream via the base64 encoding, the bit stream 
 must be presumed to be ordered with the most-significant-bit first. 
 That is, the first bit in the stream will be the high-order bit in 
 the first 8-bit byte, and the eighth bit will be the low-order bit in 
 the first 8-bit byte, and so on.
 */

//// bad comment - inobvious connection
/*
 * start with an array that is big enough to hold all the pixels
 * (plus filter bytes), and an extra 200 bytes for header info
 */
 this.pngBytes = new byte[((this.width + 1) * this.height * 3) + 200];

//// bad comment - function headers
//// bad comment - Javadocs in nonpublic code


//// bad code with bad comments
/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * <p>
 * Eratosthenes of Cyrene, b. c. 276 BC, Cyrene, Libya --
 * d. c. 194, Alexandria. The first man to calculate the
 * circumference of the Earth. Also known for working on
 * calendars with leap years and ran the library at Alexandria.
 * <p>
 * The algorithm is quite simple. Given an array of integers
 * starting at 2. Cross out all multiples of 2. Find the next
 * uncrossed integer, and cross out all of its multiples.
 * Repeat untilyou have passed the square root of the maximum
 * value.
 *
 * @author Alphonse
 * @version 13 Feb 2002 atp
 */
import java.util.*;
public class GeneratePrimes
{
 /**
 * @param maxValue is the generation limit.
 */
 public static int[] generatePrimes(int maxValue)
 {
  if (maxValue >= 2) // the only valid case
  {
  // declarations
  int s = maxValue + 1; // size of array
  boolean[] f = new boolean[s];
  int i;
  // initialize array to true.
  for (i = 0; i < s; i++)
   f[i] = true;
   // get rid of known non-primes
   f[0] = f[1] = false;
   // sieve
   int j;
   for (i = 2; i < Math.sqrt(s) + 1; i++)
   {
    if (f[i]) // if i is uncrossed, cross its multiples.
    {
     for (j = 2 * i; j < s; j += i)
      f[j] = false; // multiple is not prime
    }
   }
   // how many primes are there?
   int count = 0;
   for (i = 0; i < s; i++)
   {
    if (f[i])
     count++; // bump count.
    }
    int[] primes = new int[count];
    // move the primes into the result
    for (i = 0, j = 0; i < s; i++)
    {
     if (f[i]) // if prime
      primes[j++] = i;
    }
   return primes; // return the primes
  }
  else // maxValue < 2
   return new int[0]; // return null array if bad input.
 }
}

//// better code

/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * Given an array of integers starting at 2:
 * Find the first uncrossed integer, and cross out all its
 * multiples. Repeat until there are no more multiples
 * in the array.
 */

public class PrimeGenerator
{
	private static boolean[] crossedOut;
	private static int[] result;

	public static int[] generatePrimes(int maxValue)
	{
		if (maxValue < 2)
			return new int[0];
		else
		{
			uncrossIntegerUpTo(maxValue);
			crossOutMultiples();
			putUncrossedIntegersIntoResult();
			return result;
		}
	}

	private static void uncrossIntegerUpTo(int maxValue)
	{
		crossedOut = new boolean[maxValue + 1];
		for (int i = 2; i < crossedOut.length; i++)
			crossedOut[i] = false;
	}

	private static void crossOutMultiples()
	{
		int limit = determineIterationLimit();
		for (int i = 2; i <= limit; i++) {
			if (notCrossed(i))
				crossOutMultiplesOf(i);
		}
	}

	private static int determineIterationLimit()
	{
		// Every multiple in the array has a prime factor that
		// is less than or equal to the root of the array size,
		// so we don't have to cross out multiples of numbers
		// larger than that root.
		double iterationLimit = Math.sqrt(crossedOut.length);
		return (int) iterationLimit;
	}

	private static void crossOutMultiplesOf(int i)
	{
		for  (int multiple = 2 * i;
			  multiple < crossedOut.length;
			  multiple += i)
			crossedOut[multiple] = true;
	}

	private static boolean notCrossed(int i)
	{
		return crossedOut[i] == false;
	}

	private static void putUncrossedIntegersIntoResult()
	{
		result = new int[numberOfUncrossedIntegers()];
		for (int j = 0, i = 2; i < crossedOut.length; i++)
			if (notCrossed(i))
				response[j++] = i;
	}

	private static int numberOfUncrossedIntegers()
	{
		int count = 0;
		for (int i = 2; i < crossedOut.length; i++)
			if (notCrossed(i))
				count++;
		return count;
	}
}