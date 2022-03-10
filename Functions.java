// bad code
public static String testableHtml(
	PageDate pageData,
	boolean includeSuiteSetup
) throws Exception {
	WikiPage wikiPage = pageData.getWikiPage();
	StringBuffer buffer = new StringBuffer();
	if (pageData.hasAttribute("Test")) {
		if (includeSuiteSetup) {
			WikiPage suiteSetup =
				PageCrawlerImpl.getInheritedPage(
						SuiteResponder.SUITE_SETUP_NAME, wikiPage
				);
 			if (suiteSetup != null) {
 				WikiPagePath pagePath =
 					suiteSetup.getPageCrawler().getFullPath(suiteSetup);
 				String pagePathName = PathParser.render(pagePath);
				buffer.append("!include -setup .")
					  .append(pagePathName)
					  .append("\n");
			}
 		}
		WikiPage setup = 
			PageCrawlerImpl.getInheritedPage("SetUp", wikiPage);
		if (setup != null) {
			WikiPagePath setupPath =
				wikiPage.getPageCrawler().getFullPath(setup);
			String setupPathName = PathParser.render(setupPath);
			buffer.append("!include -setup .")
				  .append(setupPathName)
				  .append("\n");
		}
	}
	buffer.append(pageData.getContent());
	if (pageData.hasAttribute("Test")) {
		WikiPage teardown = 
			PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
		if (teardown != null) {
			WikiPagePath tearDownPath =
				wikiPage.getPageCrawler().getFullPath(teardown);
			String tearDownPathName = PathParser.render(tearDownPath);
			buffer.append("\n")
				  .append("!include -teardown .")
				  .append(tearDownPathName)
				  .append("\n");
		}
		if (includeSuiteSetup) {
			WikiPage suiteTeardown =
				PageCrawlerImpl.getInheritedPage(
						SuiteResponder.SUITE_TEARDOWN_NAME,
						wikiPage
				);
			if (suiteTeardown != null) {
				WikiPagePath pagePath =
					suiteTeardown.getPageCrawler().getFullPath (suiteTeardown);
				String pagePathName = PathParser.render(pagePath);
				buffer.append("!include -teardown .")
					  .append(pagePathName)
					  .append("\n");
			}
		}
	}
	pageData.setContent(buffer.toString());
	return pageData.getHtml();
}

// good code
public static String renderPageWithSetupsAndTeardowns (
	PageData pageData, boolean isSuite
) throws Exception {
	boolean isTestPage = pageData.hasAttribute("Test");
	if (isTestPage) {
		WikiPage testPage = pageData.getWikiPage();
		StringBuffer newPageContent = new StringBuffer();
		includeSetupPages(testPage, newPageContent, isSuite);
		newPageContent.append(pageData.getContent());
		includeTeardownPages(testPage, newPageContent, isSuite);
		pageData.setContent(newPageContent.toString());
	}

	return pageData.getHtml();
}

// small: they should be smaller than that
// better code
public static String renderPageWithSetupsAndTeardowns (
	PageData pageData, boolean isSuite) throws Exception {
	if (isTestPage(pageData))
		includeSetupAndTeardownPages(pageData, isSuite);
	return pageData.getHtml();
}

// block and indenting

// do one thing: if a function does only those steps
// that are one level below the stated name of the
// function, then the function is doing one thing

// function that do one thing cannot be reasonably
// divided into sections

// The Stepdown Rule: We want every function to be
// followed by those at the next level of abstraction
// so that we can read the program, descending one
// level of abstraction at a time as we read down
// the list of functions.

/*
To include the setups and teardowns, we include setups,
		then we include the test page content,
		and then we include the teardowns.
	To include the setups, we include the suite setup if this is a suite,
			then we include the regular setup.
	To include the suite setup, we search the parent hierarchyfor the
			“SuiteSetUp” page and add an include statement with
			the path of that page.
	To search the parent. . .
*/

// switch statements
// bad code
public Money calculatePay(Employee e)
throws InvalidEmployeeType {
	switch (e.type) {
		case COMMISSIONED:
			return calculateCommissionedPay(e);
		case HOURLY:
			return calculateHourlyPay(e);
		case SALARIED:
			return calculateSalariedPay(e);
		default:
			throw new InvalidEmployeeType(e.type);
	}
}
// 1. It's large, and when new employee types are added,
// it will grow

// 2. It does more than one thing

// 3. It violates the Single Responsibility Principle (SRP)
// because there is more than one reason for it to change

// 4. It violates the Open Closed Principle because it must
// change whenever new types are added

// 5. There are an unlimited number of other functions that
// will have the same structure


// The solution to this problem is to bury the switch
// statement in the basement of an ABSTRACT FACTORY and
// never let anyone see it. The factory will use the
// switch statement to create approriate instances of
// the derivatives of Employee, and the various functions,
// such as calculatePay, isPayDay, and deliverPay, will be
// dispatched polymorphically through the Employee interface
// good code
public abstract class Employee {
	public abstract boolean isPayday();
	public abstract Money calculatePay();
	public abstract void deliverPay(Money pay);
}

public interface EmployeeFactory {
	public Employee makeEmployee(EmployeeRecord r)
	throws InvalidEmployeeType;
}

public class EmployeeFactoryImpl implements EmployeeFactory {
	public Employee makeEmployee(EmployeeRecord r)
	throws InvalidEmployeeType {
		switch (r.type) {
			case COMMISSIONED:
				return new CommissionedEmployee(r);
			case HOURLY:
				return new HourlyEmployee(r);
			case SALARIED:
				return new SalariedEmployee(r);
			default:
				throw new InvalidEmployeeType(r.type);
		}
	}
}

// use descriptive names: the smaller and more focused
// a function is, the easier it is to choose
// a descriptive name

// function arguments
// the ideal number of arguments for a function is zero (niladic)
// next comes one (monadic), followed closely by two (dyadic)
// three arguments (triadic) should be avoided where possible
// more than three (polyadic) require very special justification,
// and then shouldn't be used anyway

// the argument is at a different level of abstraction
// than the function name and forces you to know a detail
// that isn't particularly important at that point

// the difficulty of writing all the test cases to ensure that
// all the various combinations of arguments work properly

// common monadic forms
// 1. asking a question about that argument
boolean fileExists(String filename) {}
// 2. operating on that argument, transforming it in to something
// else and returning it
InputStream fileOpen(String filename) {}
// 3. event: there is one input argument but no output argument
// the overall program is meant to interpret the function call
// as an event and use the argument to alter the state of
// the system
void passwordAttemptFailedNtimes(int attempts) {}
// 4. using an output argument instead of a return value
// for a transformation is confusing

// flag arguments
// bad code
render(boolean isSuite);

// good code
renderForSuite();
renderForSingleTest();

// dyadic functions
writeField(outputStream, name);

// convert dyads into monads
outputStream.writeField(name);

class ExampleClass {
	private OutputStream outputStream;
	public writeField(name) {}
}

class FieldWriter {
	private OutputStream outputStream;
	FieldWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public write(name) {}
}

// triads
// think very carefully before creating a triad

// have no side effects
// bad code
public class UserValidator {
	private Crytographer cryptographer;
	public boolean checkPassword(String username, String password) {
		User user = UserGateway.findByName(username);
		if (user != User.NULL) {
			String codedPhrase = user.getPhraseEncodedByPassword();
			String phrase = cryptographer.decrypt(codedPhrase, password);
			if ("Valid Password".equals(phrase)) {
				Session.initialize(); // side effect
				return true;
			}
		}
		return false;
	}
}

// argument objects
// when a function seems to beed more than two or three arguments,
// it is likely that some of those arguments ought to be wrapped
// into a class of their own
Circle makeCircle(double x, double y, double radius);

// good code
Circle makeCircle(Point center, double radius);

// argument lists
public String format(String format, Object... args);

void monad(Integer... args);
void dyad(String name, Integer... args);
void triad(String name, int count, Integer... args);

// verb and keywords
write(name)
writeField(name)
assertExpectedEqualsActual(expected, actual)

// the side effect creates a temporal coupling

// temporal couplings are confusing, especially when
// hidden as a side effect

// if you must have a temporal coupling, you should
// make it clear in the name of the function
public class UserValidator {
	private Crytographer cryptographer;
	public boolean checkPasswordAndInitializeSession(String username, String password) {
		User user = UserGateway.findByName(username);
		if (user != User.NULL) {
			String codedPhrase = user.getPhraseEncodedByPassword();
			String phrase = cryptographer.decrypt(codedPhrase, password);
			if ("Valid Password".equals(phrase)) {
				Session.initialize(); // side effect
				return true;
			}
		}
		return false;
	}
}

// output arguments
// anything that forces you to check the function signature
// is equivalent to a double-take, it's a cognitive break
// and should be avoided
public void appendFooter(StringBuffer report) {}
appendFooter(s);

// good code
report.appendFooter();
// in general output argument should be avoided
// if yout function must change the state of something,
// have it change the state of its owning object

// command query separation
// function should either do something or answer something,
// but not both
// bad code
public boolean set(String attribute, String value);

if (set("username", "unclebob")) {}

// good code
if (attributeExists("username")) {
	setAttribute("username", "unclebob");
}

// prefer exceptions to returning error codes
// bad code
if (deletePage(page) == E_OK) {
	if (registry.deleteReference(page.name) == E_OK) {
		if (configKeys.deleteKey(page.name.makeKey()) == E_OK) {
			logger.log("page deleted");
		} else {
			logger.log("configKey not deleted");
		}
	} else {
		logger.log("deleteReference from registry failed");
	}
} else {
	logger.log("delete failed");
	return E_ERROR;
}

// good code
try {
	deletePage(page);
	registry.deleteReference(page.name);
	configKeys.deleteKey(page.name.makeKey());
}
catch (Exception e) {
	logger.log(e.getMessage)
}

// extract try/catch blocks
// try/catch blocks are ugly in their own right, they confuse
// the structure of the code and mix error processing with
// normal processing, so it is better to extract the bodies
// of the try and catch blocks out into functions of their own

// better code
public void delete(Page page) {
	try {
		deletePageAndAllReferences(page);
	}
	catch (Exception e) {
		logError(e);
	}
}

private void deletePageAndAllReferences(Page page) throws Exception {
	deletePage(page);
	registry.deleteReference(page.name);
	configKeys.deleteKey(page.name.makeKey());
}

private void logError(Exception e) {
	logger.log(e.getMessage());
}

// error handling is one thing
// function should do one thing, error handling is one thing,
// thus a function that handles errors should do nothing else

// the Error.java dependency magnet
pulic enum Error {
	OK,
	INVALID,
	NO_SUCH,
	LOCKED,
	OUT_OF_RESOURCES,
	WAITING_FOR_EVENT;
}
// classes like this are a dependency magnet, many other classes
// must import and use them
// when you use exceptions rather than error codes, then
// new exceptions are derivatives of the exception class, they
// can be added without forcing any recompilation or redeployment

// don't repeat yourself

// When I write functions, they come out long and complicated.
// They have lots of indenting and nested loops. They have long
// argument lists. The name are arbitrary, and there is duplicated code.
// But I also have a suite of unit tests that cover every one of
// those clumsy lines of code.

// So the I massage and refine that code, splitting out functions,
// changing names, eliminating duplication. I shrink the methods and reorder them.
// Sometimes I break out whole class, all the while keeping
// the tests passing.

// good code
package fitnesse.html;

import fitnesse.responders.run.SuiteResponder;
import fitness.wiki.*;

public class SetupTeardownIncluder {
	private PageData pageData;
	private boolean isSuite;
	private WikiPage testPage;
	private StringBuffer newPageContent;
	private PageCrawler pageCrawler;

	public static String render(PageData pageData) throws Exception {
		return render(pageData, false);
	}

	public static String render(PageData pageData, boolean isSuite)
	throws Exception {
		return new SetupTeardownIncluder(pageData).render(isSuite);
	}

	private SetupTeardownIncluder(PageData pageData) {
		this.pageData = pageData;
		testPage = pageData.getWikiPage();
		pageCrawler = testPage.getPageCrawler();
		newPageContent = new StringBuffer();
	}

	private String render(boolean isSuite) throws Exception {
		this.isSuite = isSuite;
		if (isTestPage())
			includeSetupAndTeardownPages();
		return pageData.getHtml();
	}

	private boolean isTestPage() throws Exception {
		return pageData.hasAttribute("Test");
	}

	private void includeSetupAndTeardownPages() throws Exception {
		includeSetupPages();
		includePageContent();
		includeTeardownPages();
		updatePageContent();
	}

	private void includeSetupPages() throws Exception {
		if (isSuite)
			includeSuiteSetupPage();
		includeSetupPage();
	}

	private void includeSuiteSetupPage() throws Exception {
		include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
	}

	private void includeSetupPage() throws Exception {
		include("SetUp", "-setup");
	}

	private void includePageContent() throws Exception {
		newPageContent.append(pageData.getContent());
	}

	private void includeTeardownPages() throws Exception {
		includeTeardownPage();
		if (isSuite)
			includeSuiteTeardownPage();
	}

	private void includeTeardownPage() throws Exception {
		include("TearDown", "-teardown");
	}

	private void includeSuiteTeardownPage() throws Exception {
		include(SuiteRender.SUITE_TEARDOWN_NAME, "-teardown");
	}

	private void updatePageContent() throws Exception {
		pageData.setContent(newPageContent.toString());
	}

	private void include(String pageName, String arg) throws Exception {
		WikiPage inheritedPage = findInheritedPage(pageName);
		if (inheritedPage != null) {
			String pagePathName = getPathNameForPage(inheritedPage);
			buildIncludeDirective(pagePathName, arg);
		}
	}

	private WikiPage findInheritedPage(String pageName) throws Exception {
		return PageCrawlerImpl.getInheritedPage(pageName, testPage);
	}

	private String getPathNameForPage(WikiPage page) throws Exception {
		WikiPagePath pagePath = pageCrawler.getFullPath(page);
		return PathParser.render(pagePath);
	}

	private void buildIncludeDirective(String pagePathName, String arg) {
		newPageContent
			.append("\n!include ")
			.append(arg)
			.append(" .")
			.append(pagePathName)
			.append("\n");
	}

}