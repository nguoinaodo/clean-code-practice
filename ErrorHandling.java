// Error handling
// Use exceptions rather than return codes
// bad code
public class DeviceController {
	public void sendShutDown() {
		DeviceHandle handle = getHandle(DEV1);
		// Check the state of the device
		if (handle != DeviceHandle.INVALID) {
			// Save the device status to the record field
			retrieveDeviceRecord(handle);
			// If not suspended, shut down
			if (record.getStatus() != DEVICE_SUSPENDED) {
				pauseDevice(handle);
				clearDeviceWorkQueue(handle);
				closeDevice(handle);
			} else {
				logger.log("Device suspended. Unable to shut down");
			}
		} else {
			logger.log("Invalid handle for: " + DEV1.toString());
		}
	}
}

// better code
public class DeviceController {
	public void sendShutDown() {
		try {
			tryToShutDown();
		} catch (DeviceShutDownError e) {
			logger.log(e);
		}
	}

	private void tryToShutDown() {
		DeviceHandle handle = getHandle(DEV1);
		DeviceRecord record = retrieveDeviceRecord(handle);

		pauseDevice(handle);
		clearDeviceWorkQueue(handle);
		closeDevice(handle);
	}

	private DeviceHandle getHandle(DeviceID id) {
		//...
		throw new DeviceShutDownError("Invalid handle for: " + id.toString());
		//...
	}
}

// write your try-catch-finally statement first
// good code
public List<RecoredGrip> retrieveSection(String sectionName) {
	try {
		FileInputStream stream = new FileInputStream(sectionName);
		stream.close();
	} catch (FileNotFoundException e) {
		throw new StorageException("retrieval error", e);
	}
	return new ArrayList<RecoredGrip>();
}

// use unchecked exception
// the price of checked exceptions is an Open/Close Principle
// violation
// encapsulation is broken because all functions in the
// path of a throw must know about details of that low-level
// exception

// provide context with exceptions
// create informative error messages and pass them along 
// with your exceptions
// mention the operation that failed and the type of failure

// define exception classes in terms of a caller's needs
// code with duplication
ACMEPort port = new ACMEPort(12);

try {
	port.open();
} catch (DeviceResponseException e) {
	reportPortError(e);
	logger.log("Device response exception", e);
} catch (ATM1212UnlockedException e) {
	reportPortError(e);
	logger.log("Unlock exception", e);
} catch (GMXError e) {
	reportPortError(e);
	logger.log("Device response exception");
} finally {
	//...
}

// better code
LocalPort port = new LocalPort(12);

try {
	port.open();
} catch (PortDeviceFailure e) {
	reportError(e);
	logger.log(e.getMessage(), e);
} finally {
	//...
}

public class LocalPort {
	private ACMEPort innerPort;

	public LocalPort(int portNumber) {
		innerPort = new ACMEPort(portNumber);
	}

	public void open() {
		try {
			innerPort.open();
		} catch (DeviceResponseException e) {
			throw new PortDeviceFailure(e);
		} catch (ATM1212UnlockedException e) {
			throw new PortDeviceFailure(e);
		} catch (GMXError e) {
			throw new PortDeviceFailure
		}
	}
}

// define the normal flow
// awkward code
try {
	MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
	m_total += expenses.getTotal();
} catch (MealExpensesNotFound e) {
	m_total += getMealPerDiem();
}

// better code
MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
m_total += expenses.getTotal();

// we can change the ExpenseReportDAO so that it always returns
// a MealExpense object
public class PerDiemMealExpenses implements MealExpenses {
	public int getTotal() {
		// return the per diem default
	}
}

// this is called the SPECIAL CASE PATTERN: you create a class
// or configure an object so that in handles a special case
// for you

// don't return null
// bad code that return null
public void registerItem(Item item) {
	if (item != null) {
		ItemRegistry registry = persistentStore.getItemRegistry();
		if (registry != null) {
			Item existing = registry.getItem(item.getID());
			if (existing.getBillingPeriod().hasRetailOwner()) {
				existing.register(item);
			}
		}
	}
}
// we would have had a NullPointerException at runtime, it's bad
// if you are tempted to return null from a method, consider
// throwing an exception or returning a SPECIAL CASE object instead
// if you are calling a null-returning method from a third-party
// API, consider wrapping that method with a method that 
// either throws an exception or returns special case object

// bad code
List<Employee> employees = getEmployees();
if (employees != null) {
	for (Employee e : employees) {
		totalPay += e.getPay();
	}
}

// better code
List<Employee> employees = getEmployees();
for (Employee e : employees) {
	totalPay += e.getPay();
}

public List<Employee> getEmployees() {
	if ( .. there are no employees .. )
		return Collections.emplyList();
}

// don't pass null
// bad code
public class MetricsCalculator
{
	public double xProjection(Point p1, Point p2) {
		return (p2.x - p1.x) * 1.5;
	}
}

calculator.xProjection(null, new Point(12, 12)); // NullPointerException

// litte better code
public class MetricsCalculator
{
	public double xProjection(Point p1, Point p2) {
		if (p1 == null || p2 == null) {
			throw InvalidArgumentException(
				"Invalid argument for MetricsCalculator.xProjection");
		}
		return (p2.x - p1.x) * 1.5;
	}
}

// another alternative: it's good documentation, but it's not solve
// the problem. If someone passes null, we'll still have a 
// runtime error
public class MetricsCalculator
{
	public double xProjection(Point p1, Point p2) {
		assert p1 != null : "p1 should not be null";
		assert p2 != null : "p2 should not be null";
		return (p2.x - p1.x) * 1.5;
	}
}

// the rational approach is to forbid passing null by default
