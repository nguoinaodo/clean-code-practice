// Unit Tests

// The Three Laws of TDD

// First Law: You may not write production code until you have
// written a failing unit test.

// Second Law: You may not write more of a unit test than
// is sufficient to fail, and not compiling is failing.

// Third Law: You may not write more production code than
// is sufficient to pass the currently failing test.

// Keeping tests clean
// Test code is just as important as production code.
// It must be kept as clean as production code.

// Tests enable the -ilities
// If you have tests, you do not fear making changes to the code.
// Without tests every change is a possible bug.
// The dirtier your tests, the dirtier your code becomes.
// Eventually you lose the tests, and your code rots.

// Clean tests: readability
// What makes tests readable: clarity, simplicity, density of expression
// In a test you want to say a lot with as few expressions
// as possible.

// bad test code
public void testGetPageHieratchyAsXml() throws Exception
{
	crawler.addPage(root, PathParser.parse("PageOne"));
	crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
	crawler.addPage(root, PathParser.parse("PageTwo"));
	
	request.setResource("root");
	request.addInput("type", "pages");
	Responder responder = new SerializedPageResponder();
	SimpleResponse response = 
		(SimpleResponse) responder.makeResponse(
			new FitNesseContext(root), request);
	String xml = response.getContent();
	
	assertEquals("text/xml", response.getContentType());
	assertSubString("<name>PageOne</name>", xml);
	assertSubString("<name>PageTwo</name>", xml);
	assertSubString("<name>ChildOne</name>", xml);
}

public void testGetPageHieratchyAsXmlDoesntContainSymbolicLinks() 
throws Exception
{
	WikiPage pageOne = crawler.addPage(root, PathParser.parse("PageOne"));
	crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
	crawler.addPage(root, PathParser.parse("PageTwo"));
	
	PageData data = pageOne.getData();
	WikiPageProperties properties = data.getProperties();
	WikiPageProperty symLinks = properties.set(SymbolicPage.PROPERTY_NAME);
	symLinks.set("SymPage", "PageTwo");
	pageOne.commit(data);
	
	request.setResource("root");
	request.addInput("type", "pages");
	Responder responder = new SerializedPageResponder();
	SimpleResponse response = 
		(SimpleResponse) responder.makeResponse(
			new FitNesseContext(root), request);
	String xml = response.getContent();
	
	assertEquals("text/xml", response.getContentType());
	assertSubString("<name>PageOne</name>", xml);
	assertSubString("<name>PageTwo</name>", xml);
	assertSubString("<name>ChildOne</name>", xml);
	assertNotSubString("SymPage", xml);
}

public void testGetDataAsHtml() throws Exception
{
	crawler.addPage(root, PathParser.parse("TestPageOne"), "test page");
	
	request.setResource("TestPageOne");
	request.addInput("type", "data");
	Responder responder = new SerializedPageResponder();
	SimpleResponse response = 
		(SimpleResponse) responder.makeResponse(
			new FitNesseContext(root), request);
	String xml = response.getContent();

	assertEquals("text/xml", response.getContentType());
	assertSubString("test page", xml);
	assertSubString("<Test", xml);
}

// readable test code
public void testGetPageHieratchyAsXml() throws Exception {
	makePages("PageOne", "PageOne.ChildOne", "PageTwo");

	submitRequest("root", "type:pages");

	assertResponseIsXML();
	assertResponseContains(
		"<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>"
	);
}

public void testSymbolicLinksAreNotInXmlPageHierachy() throws Exception {
	WikiPage page = makePage("PageOne");
	makePages("PageOne.ChildOne", "PageTwo");

	addLinkTo(page, "PageTwo", "SymPage");

	submitRequest("root", "type:pages");

	assertResponseIsXML();
	assertResponseContains(
		"<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>"
	);
	assertResponseDoesNotContain("SymPage");
}

public void testGetDataAsXml() throws Exception {
	makePageWithContent("TestPageOne", "test page");

	submitRequest("TestPageOne", "type:data");

	assertResponseIsXML();
	assertResponseContains("test page", "<Test");
}

// The BUILD-OPERATE-CHECK pattern is made obvious by
// the structure of these tests.

// Domain-specific testing language
// Rather than using the APIs that programmers use to manipulate
// the system, we buildup a set of functions and utilities
// that make use of those APIs and that make the tests more
// convenient to write and easier to read
// The testing API is not designed up front; rather it evolves
// from the continued refactoring of the test code that has
// gotten to tainted by obfuscating detail.

// A dual standard
// There are things that you might never do in a production
// environment that are perfectly fine in a test environment.
// Usually they involve issues of memory or CPU efficiency.
// But the never involve issues of cleanliness.

// test code with lots of details
@Test
public void turnOnLoTempAlarmAtThreshold() throws Exception {
	hw.setTemp(WAY_TOO_COLD);
	controller.tic();
	assertTrue(hw.heaterState());
	assertTrue(hw.blowerState());
	assertFalse(hw.coolerState());
	assertFalse(hw.hiTempAlarm());
	assertTrue(hw.loTempAlarm());
}

// better code
@Test
public void turnOnLoTempAlarmAtThreshold() throws Exception {
	wayTooCold();
	asertEquals("HBchl", hw.getState());
}

@Test
public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
	tooHot();
	assertEquals("hBChl", hw.getState());
}

@Test
public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
	tooCold();
	assertEquals("HBchl", hw.getState());
}

@Test
public void turnOnHiTempAlarmAtThreshold() throws Exception {
	wayTooHot();
	assertEquals("hBCHl", hw.getState());
}

public String getState() {
	String state = "";
	state += heater ? "H" : "h";
	state += blower ? "B" : "b";
	state += cooler ? "C" : "c";
	state += hiTempAlarm ? "H" : "h";
	state += loTempAlarm ? "L" : "l";
	return state;
}

// Even though this is close to a violation of the
// rule about mental mapping, it seems
// appropriate in this case

// one assert per test: the best thing we can say is that
// the number of asserts in a test ought to be minimized

// one concept per test: you should minimize the number of
// asserts per concept and test just one concept per test
// function

// F.I.R.S.T
// Fast: tests should run quickly
// Indepedent: you should be able to run each test independently
// and run the tests in any order you like.
// Repeatable: tests should be repeatable in any environment.
// Self-validating: the tests should have a boolean output
// Timely: Unit tests should be written just before the production
// code that makes them pass. If you write tests after the
// production code, then you may find the production code
// to be hard to test.