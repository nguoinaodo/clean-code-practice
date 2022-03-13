// Boundaries
// using third-party code
// not clean code
Map sensors = new HashMap();
//...
Sensor s = (Sensor)sensors.get(sensorId);

// cleaner code
Map<Sensor> sensors = new HashMap<Sensor>();
//...
Sensor s = sensors.get(sensorId);

// better code
public class Sensors {
	private Map sensors = new HashMap();

	public Sensor getById(String id) {
		return (Sensor) sensors.get(id);
	}

	//...
}
// if you use a boundary interface like Map, keep it inside
// the class, or close family of classes, where it is used.
// Avoid returning it from, or accepting it as an argument
// to public APIs

// exploring and learning boundaries
// Instead of experimenting and trying out the new stuff in
// our production code, we could write some tests to explore
// our understanding of the third-party code: learning tests.

// learning log4j
public class LogTest {
	private Logger logger;

	@Before
	public void initialize() {
		logger = Logger.getLogger("logger");
		logger.removeAllDependers();
		Logger.getRootLogger().removeAllAppenders();
	}

	@Test
	public void basicLogger() {
		BasicConfigurator.configure();
		logger.info("basicLogger");
	}

	@Test
	public void addAppenderWithStream() {
		logger.addAppender(new ConsoleAppender(
			new PatternLayout("%p %t %m%n"),
			ConsoleAppender.SYSTEM_OUT));
		logger.info("addAppenderWithStream");
	}

	@Test
	public void addAppenderWithoutStream() {
		logger.addAppender(new ConsoleAppender(
			new PatternLayout("%p %t %m%n")));
		logger.info("addAppenderWithoutStream");
	}
}

// learning tests are better than free

// using code that does not yet exist
// ADAPTER PATTERN: the ADAPTER encapsulated the interaction
// with the API and provides a single place to change when
// the API evolves.
