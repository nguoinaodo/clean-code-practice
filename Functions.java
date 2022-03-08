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