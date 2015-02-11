package org.myrobotlab.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.myrobotlab.fileLib.FileIO;
import org.myrobotlab.fileLib.FindFile;
import org.myrobotlab.framework.Encoder;
import org.myrobotlab.framework.Service;
import org.myrobotlab.framework.Status;
import org.myrobotlab.logging.Level;
import org.myrobotlab.logging.LoggerFactory;
import org.myrobotlab.logging.Logging;
import org.myrobotlab.logging.LoggingFactory;
import org.myrobotlab.service.interfaces.ServiceInterface;
import org.slf4j.Logger;

/**
 * Minimal dependency service for rigorous testing
 * 
 * @author GroG
 *
 */
public class Test extends Service {

	private static final long serialVersionUID = 1L;

	public final static Logger log = LoggerFactory.getLogger(Test.class);

	Date now = new Date();
	transient Set<Thread> threads = null;
	transient Set<File> files = new HashSet<File>();

	public Test(String n) {
		super(n);
		//addRoutes(); - what does this do?
	}

	@Override
	public void startService() {
		super.startService();
	}

	// TODO - do all forms of serialization - binary json xml
	public Status serializeTest(ServiceInterface s) {

		String name = s.getName();

		// multiple formats binary json xml
		Status status = Status.info("serializeTest for %s", name);

		try {

			log.info("serializing {}", name);

			// TODO put in encoder
			ByteArrayOutputStream fos = null;
			ObjectOutputStream out = null;
			fos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(fos);
			out.writeObject(s);
			fos.flush();
			out.close();

			// json encoding
			Encoder.gson.toJson(s);

			// TODO xml

			log.info("releasing {}", name);

		} catch (Exception ex) {
			status.addError(ex);
		}

		return status;
	}

	@Override
	public String getDescription() {
		return "used as a general template";
	}

	/*
	 * careful with using other services - as they incur dependencies
	 * 
	 * public Status pythonTest() { Python python = (Python)
	 * Runtime.start("python", "Python"); Serial uart99 = (Serial)
	 * Runtime.start("uart99", "Serial"); // take inventory of currently running
	 * services HashSet<String> keepMeRunning = new HashSet<String>();
	 * 
	 * VirtualSerialPort.createNullModemCable("UART99", "COM12");
	 * 
	 * List<ServiceInterface> list = Runtime.getServices(); for (int j = 0; j <
	 * list.size(); ++j) { ServiceInterface si = list.get(j);
	 * keepMeRunning.add(si.getName()); }
	 * 
	 * String[] serviceTypeNames = Runtime.getInstance().getServiceTypeNames();
	 * Status status = Status.info("subTest");
	 * 
	 * status.add(Status.info("will test %d services",
	 * serviceTypeNames.length));
	 * 
	 * for (int i = 0; i < serviceTypeNames.length; ++i) { String fullName =
	 * serviceTypeNames[i]; String shortName =
	 * fullName.substring(fullName.lastIndexOf(".") + 1);
	 * 
	 * String py =
	 * FileIO.resourceToString(String.format("Python/examples/%s.py",
	 * shortName));
	 * 
	 * if (py == null || py.length() == 0) {
	 * status.addError("%s.py does not exist", shortName); } else {
	 * uart99.connect("UART99"); uart99.recordRX(String.format("%s.rx",
	 * shortName)); // FIXME // FILENAME // OVERLOAD python.exec(py);
	 * uart99.stopRecording(); // check rx file against saved data }
	 * 
	 * // get python errors !
	 * 
	 * // clean services Runtime.releaseAllServicesExcept(keepMeRunning); }
	 * 
	 * return null;
	 * 
	 * }
	 */

	public void testServiceScripts() {
		// get download zip

		// uncompress locally

		// test - instrumentation for
	}

	/*
	 * 
	 * public void testPythonScripts() { try {
	 * 
	 * Python python = (Python) Runtime.start("python", "Python"); // String
	 * script; ArrayList<File> list =
	 * FileIO.listInternalContents("/resource/Python/examples");
	 * 
	 * Runtime.createAndStart("gui", "GUIService"); python = (Python)
	 * startPeer("python"); // InMoov i01 = (InMoov)
	 * Runtime.createAndStart("i01", "InMoov");
	 * 
	 * HashSet<String> keepMeRunning = new HashSet<String>(Arrays.asList("i01",
	 * "gui", "runtime", "python", getName()));
	 * 
	 * for (int i = 0; i < list.size(); ++i) { String r = list.get(i).getName();
	 * if (r.startsWith("InMoov2")) { warn("testing script %s", r); String
	 * script = FileIO.resourceToString(String.format("Python/examples/%s", r));
	 * python.exec(script); log.info("here"); // i01.detach();
	 * Runtime.releaseAllServicesExcept(keepMeRunning); } }
	 * 
	 * } catch (Exception e) { Logging.logException(e); } }
	 * 
	 * public void testInMoovPythonScripts() { try {
	 * 
	 * Python python = (Python) Runtime.start("python", "Python"); // String
	 * script; ArrayList<File> list =
	 * FileIO.listInternalContents("/resource/Python/examples");
	 * 
	 * Runtime.createAndStart("gui", "GUIService"); python = (Python)
	 * startPeer("python"); // InMoov i01 = (InMoov)
	 * Runtime.createAndStart("i01", "InMoov");
	 * 
	 * HashSet<String> keepMeRunning = new HashSet<String>(Arrays.asList("i01",
	 * "gui", "runtime", "python", getName()));
	 * 
	 * for (int i = 0; i < list.size(); ++i) { String r = list.get(i).getName();
	 * if (r.startsWith("InMoov2")) { warn("testing script %s", r); String
	 * script = FileIO.resourceToString(String.format("Python/examples/%s", r));
	 * python.exec(script); log.info("here"); // i01.detach();
	 * Runtime.releaseAllServicesExcept(keepMeRunning); } }
	 * 
	 * } catch (Exception e) { Logging.logException(e); } }
	 */
	// very good - dynamicly subscribing to other service's
	// published errors
	// step 1 subscribe to runtimes registered event
	// step 2 in any registered -
	// step 3 - fix up - so that state is handled (not just "error")
	public void addRoutes() {
		// register with runtime for any new services
		// their errors are routed to mouth
		subscribe(this.getName(), "publishError", "handleError");

		Runtime r = Runtime.getInstance();
		r.addListener(getName(), "registered");
	}

	public void registered(ServiceInterface sw) {

		subscribe(sw.getName(), "publishError", "handleError");
	}

	/*
	 * public Status junit(){
	 * 
	 * 
	 * Status status = Status.info("starting %s %s junit", getName(),
	 * getType()); File buildFile = new File("build.xml"); Project p = new
	 * Project(); p.setUserProperty("ant.file", buildFile.getAbsolutePath());
	 * p.init(); ProjectHelper helper = ProjectHelper.getProjectHelper();
	 * p.addReference("ant.projectHelper", helper); helper.parse(p, buildFile);
	 * p.executeTarget(p.getDefaultTarget());
	 * 
	 * 
	 * }
	 */

	/**
	 * used to get state of the current service and runtime - so that the
	 * environment and final system can be cleaned to an original "base" state
	 */
	public void getState() {
		try {
			threads = Thread.getAllStackTraces().keySet();
			List<File> f = FindFile.find("libraries", ".*");
			for (int i = 0; i < f.size(); ++i) {
				files.add(f.get(i));
			}
		} catch (Exception e) {
			Logging.logException(e);
		}
	}

	/**
	 * this can not be used to test environment
	 * 
	 * @return
	 */
	public Status testAll() {

		String[] serviceTypeNames = Runtime.getInstance().getServiceTypeNames();
		Status status = Status.info("subTest");

		status.add(Status.info("will test %d services", serviceTypeNames.length));

		for (int i = 0; i < serviceTypeNames.length; ++i) {
			String fullName = serviceTypeNames[i];
			String shortName = fullName.substring(fullName.lastIndexOf(".") + 1);
			test(fullName);
			// status.add(test(fullName)); cant accumulate with exit(status)
		}

		return status;
	}

	/**
	 * The outer level of all tests on a per Service basis Environment is
	 * expected to be prepared correctly by an Agent. This method will test the
	 * heck out of a single service and save the results in a partFile
	 * 
	 * @param serviceType
	 * @return
	 */
	public void test(String serviceType) {

		getState();

	   status = Status.info("==== testing %s ====", serviceType);

		try {

			// install of depencencies and environment is done by
			// the Agent smith (thompson)

			ServiceInterface s = null;

			// create test
			try {
				s = Runtime.create(serviceType, serviceType);
			} catch (Exception e) {
				status.addError("create %s", e);
				exit(status);
			}

			// start test
			if (s == null) {
				status.addError("could not create %s", serviceType);
				exit(status);
			}
			
			// add error route - for call backs
			subscribe(s.getName(), "publishError", "handleError", String.class);

			try {
				s.startService();
				// FIXME - s.waitForStart();
				// Thread.sleep(500); 
			} catch (Exception e) {
				status.addError("startService %s", e);
				exit(status);
			}

			status.add(serializeTest(s));
			
			status.add(s.test());

			// assume installed - Agent's job

			// serialize test

			// python test

			// release
			try {
				if (s.hasPeers()) {
					s.releasePeers();
				}
			} catch (Exception e) {
				status.addError("releasePeers %s", e);
			}

			try {
				s.releaseService();
			} catch (Exception e) {
				status.addError("releaseService %s", e);
			}

			log.info("exiting environment");

		} catch (Exception e) {
			status.addError(e);
		}

		exit(status);
	}
	
	Status status = null;
	
	/**
	 * call-back from service under testing to route
	 * errors to this service...
	 */
	public void handleError(String errorMsg){
		if (status != null){
			status.addError(errorMsg);
		}
	}

	public void exit(Status status) {
		try {
			// check against current state for
			// NOT NEEDED Regular save file - since Agent is process.waitFor
			FileIO.savePartFile("test.json", Encoder.gson.toJson(status).getBytes());
			Runtime.releaseAll();
			// TODO - should be all clean - if not someone left threads open -
			// report them
			// big hammer
		} catch (Exception e) {
			Logging.logException(e);
		}
		System.exit(0);
	}

	public Status test() {
		// we are started so .. we'll use the big hammer at the end
		Status status = Status.info("========TESTING=============");
		log.info("===========INFO TESTING========");
		log.info(String.format("TEST PID = %s", Runtime.getPID()));
		// big hammer
		System.exit(0);
		return status;
	}

	// TODO - subscribe to registered --> generates subscription to
	// publishState() - filter on Errors
	// FIXME - FILE COMMUNICATION !!!!
	public static void main(String[] args) {
		LoggingFactory.getInstance().configure();
		LoggingFactory.getInstance().setLevel(Level.INFO);

		// LoggingFactory.getInstance().addAppender(Appender.FILE);

		Test test = (Test) Runtime.start("test", "Test");
		test.getState();
		test.test("org.myrobotlab.service.Clock");

	}

}