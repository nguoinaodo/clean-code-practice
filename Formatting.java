// Formatting
// vertical formatting
// vertical openness between concepts
// bad code
package fitnesse.wikitext.widgets;
import java.util.regex.*;
public class BoldWidget extends ParentWidget {
 public static final String REGEXP = "'''.+?'''";
 private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
 Pattern.MULTILINE + Pattern.DOTALL);
 public BoldWidget(ParentWidget parent, String text) throws Exception {
 super(parent);
 Matcher match = pattern.matcher(text);
 match.find();
 addChildWidgets(match.group(1));}
 public String render() throws Exception {
 StringBuffer html = new StringBuffer("<b>");
 html.append(childHtml()).append("</b>");
 return html.toString();
 }
}

// good code
package fitnesse.wikitext.widgets;

import java.util.regex.*;

public class BoldWidget extends ParentWidget {
	public static final String REGEXP = "'''.+?'''";
	private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
		Pattern.MULTILINE + Pattern.DOTALL
	);

	public BoldWidget (ParentWidget parent, String text) throws Exception {
		super(parent);
		Matcher match = pattern.matcher(text);
		match.find();
		addChildWidgets(match.group(1));
	}

	public String render() throws Exception {
		StringBuffer html = new StringBuffer("<b>");
		html.append(childHtml()).append("</b>");
		return html.toString();
	}
}

// vertical density
// bad code
public class ReporterConfig {

	/**
	 * The class name of the reporter listener
	 */
	private String m_className;

	/**
	 * The properties of the reporter listener
	 */
	private List<Property> m_properties = new ArrayList<Property>();

	public void addProperty(Property property) {
		m_properties.add(property);
	}
}

// better code
public class ReporterConfig {
	private String m_className;
	private List<Property> m_properties = new ArrayList<Property>();

	public void addProperty(Property property) {
		m_properties.add(property);
	}
}

// vertical distance
// concepts that are closely related should be kept vertically close
// to each other

// variable declarations: variables should be declared as close to
// their usage as possible
private static void readPreferences() {
	InputStream is = null;
	try {
		is = new FileInputStream(getPreferencesFile());
		setPreferences(new Properties(getPreferences()));
		getPreferences().load(is);
	} catch (IOException e) {
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
		}
	}
}

// control variables for loops should usually be declared within
// the loop statement
public int countTestCases() {
	int count = 0;
	for (Test each: tests)
		count += each.countTestCases();
	return count;
}

// in rare cases a variable might be declared at the top of
// a block or just before a loop in a long-ish function
...
for (XmlTest test: m_suite.getTests()) {
	TestRunner tr = m_runnerFactory.newTestRunner(this, test);
	tr.addListener(m_textReporter);
	m_testRunners.add(tr);

	invoker = tr.getInvoker();

	for (ITestNgMethod m: tr.getBeforeSuiteMethods()) {
		beforeSuiteMethods.put(m.getMethod(), m);
	}

	for (ITestNgMethod m: tr.getAfterSuiteMethods()) {
		afterSuiteMethods.put(m.getMethod(), m);
	}
}
...

// instance variables: should be declared at the top of the class

// dependent functions: if one function calls another, they should
// be vertically close, and the caller should be above the callee,
// if at all possible
public class WikiPageResponder implements SecureResponder {
	protected WikiPage page;
	protected PageData pageData;
	protected String pageTitle;
	protected Request request;
	protected PageCrawler crawler;

	public Response makeResponse(FitNesseContext context, Request request)
		throws Exception {
		String pageName = getPageNameOrDefault(request, "FrontPage");
		loadPage(pageName, context);
		if (page == null)
			return notFoundResponse(context, request);
		else
			return makePageResponse(context);
	}

	private String getPageNameOrDefault(Request request, String defaultPageName)
	{
		String pageName = request.getResource();
		if (StringUtil.isBlank(pageName))
			pageName = defaultPageName;

		return pageName;
	}

	protected void loadPage(String resource, FitNesseContext context)
		throws Exception {
		WikiPagePath path = PathParser.parse(resource);
		crawler = context.root.getPageCrawler();
		crawler.setDeadEndStrategy(new VirtualEnabledPageCrawler());
		page = crawler.getPage(context.root, path);
		if (page != null)
			pageData = page.getDate();
	}

	private Response notFoundResponse(FitNesseContext context, Request request)
		throws Exception {
		return new NotFoundResponder().makeResponse(context, request);	
	}

	private SimpleResponse makePageResponse(FitNesseContext context)
		throws Exception {
		pageTitle = PathParser.render(crawler.getFullPath(page));
		String html = makeHtml(context);

		SimpleResponse response = new SimpleResponse();
		response.setMaxAge(0);
		response.setContent(html);
		return response;
	}
}

// it was better to pass that constant down from the place
// where it makes sense to know it to the place that actually
// use it

// conceptual affinity
// the stronger that affinity, the less vertical distance
// there should be between them

// affinity might be caused becaused a group of functions
// perform a similar operation
public class Assert {
	static public void assertTrue(String message, boolean condition) {
		if (!condition)
			fail(message);
	}

	static public void assertTrue(boolean condition) {
		assertTrue(null, condition);
	}

	static public void assertFalse(String message, boolean condition) {
		asertTrue(message, !condition);
	}

	static public void assertFalse(boolean condition) {
		assertFalse(null, condition);
	}

	//...
}

// vertical ordering
// a function that is called should be below a function
// that does the calling
// this creates a nice flow down the source code module
// from high level to low level


// horizontal formatting
// horizontal openness and density
// accentuate the precedence of operators
public class Quadratic {
	public static double root1(double a, double b, double c) {
		double determinant = determinant(a, b, c);
		return (-b + Math.sqrt(determinant)) / (2*a);
	}

	public static double root2(int a, int b, int c) {
		double determinant = determinant(a, b, c);
		return (-b - Math.sqrt(determinant) / (2*a));
	}

	private static double determinant(double a, double b, double c) {
		return b*b - 4*a*c;
	}
}

// horizontal alignment: not useful
// indentation: without identation, programs would be virtually
// unreadable by humans

// breaking indentation
// bad code
public class CommentWidget extends TextWidget
{
	public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";
	public CommentWidget(ParentWidget parent, String text){super(parent, text);}
	public String render() throws Exception {return ""; }
}

// better code
public class CommentWidget extends TextWidget {
	public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";
	
	public CommentWidget(ParentWidget parent, String text) {
		super(parent, text);
	}
	
	public String render() throws Exception {
		return "";
	}
}

// a team of developers should agree upon a single formatting style,
// and then every member of that team should use that style

// the last thing we want to do is add more complexity to
// the source code by writing it in a jumble of different
// individual styles

// good code formatting
public class CodeAnalyzer implements JavaFileAnalysis {
	private int lineCount;
	private int maxLineWidth;
	private int widestLineNumber;
	private LineWidthHistogram lineWidthHistogram;
	private int totalChars;

	public CodeAnalyzer() {
		lineWidthHistogram = new LineWidthHistogram();
	}

	public static List<File> findJavaFiles(File parentDirectory) {
		List<File> files = new ArrayList<File>();
		findJavaFiles(parentDirectory, files);
		return files;
	}

	private static void findJavaFiles(File parentDirectory, List<File> files) {
		for (File file : parentDirectory.listFiles()) {
			if (file.getName().endsWith(".java"))
				files.add(file);
			else if (file.isDirectory())
				findJavaFiles(file, files);
		}
	}

	public void analyzeFile(File javaFile) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(javaFile));
		String line;
		while (line = br.readLine() != null)
			measureLine(line);
	}

	private void measureLine(String line) {
		lineCount++;
		int lineSize = line.length();
		totalChars += lineSize;
		lineWidthHistogram.addLine(lineSize, lineCount);
		recordWidestLine(lineSize);
	}

	private void recordWidestLine(int lineSize) {
		if (lineSize > maxLineWidth) {
			maxLineWidth = lineSize;
			widestLineNumber = lineCount;
		}
	}

	public int getLineCount() {
		return lineCount();
	}

	public int getMaxLineWidth() {
		return maxLineWidth;
	}

	public int getWidestLineNumber() {
		return widestLineNumber;
	}

	public LineWidthHistogram getLineWidthHistogram() {
		return lineWidthHistogram;
	}

	public double getMeanLineWidth() {
		return (double)totalChars/lineCount;
	}

	public int getMedianLineWidth() {
		Integer[] sortedWidths = getSortedWidths();
		int cumulativeLineCount = 0;
		for (int width : sortedWidths) {
			cumulativeLineCount += lineCountForWidth(width);
			if (cumulativeLineCount > lineCount/2)
				return width;
		}
		throw new Error("Cannot get here");
	}

	private int lineCountForWidth(int width) {
		return lineWidthHistogram.getLinesForWidth(width).size();
	}

	private Integer[] getSortedWidths() {
		Set<Integer> widths = lineWidthHistogram.getWidths();
		Integer[] sortedWidths = (widths.toArray(new Integer[0]));
		Array.sort(sortedWidths);
		return sortedWidths;
	}
}
