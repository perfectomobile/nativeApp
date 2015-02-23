package test.java;

import org.testng.annotations.Test;
 

 import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
 import org.testng.annotations.BeforeTest;


public class SmokeTests {

	String _Device;
	RemoteWebDriver _RWD;
	DesiredCapabilities _CP;

	@BeforeMethod
	public void beforeMethod() {
	}

	@BeforeTest
	public void beforeTest() {

		String host = Constants.PM_CLOUD;
		Reporter.log("Connect to:"+host);
	}

	@AfterSuite
	public void afterTest(){
		System.out.println("End Test");
	}


	@DataProvider(name = "Devices" , parallel = true)
	public Object[][] testSumInput() {
		return new Object[][] { 
				// build dynamic list 
			{ "Device", "0149BCA71700D01F" }, 
			{ "Device", "39F3DA5531ADBE2A05CFF4D65E43A2C38D3D595A" },
		  	{ "Device", "5CDCB21A"},
		  	{ "Device", "57AF4ED9"},
//			
//			
		};
	}



	//@Parameters({ "deviceID" })
	@Test (dataProvider="Devices" )
	public void testDevices(String type,String deviceID) {
		logIn test= null;
		if (type.equals("Device"))
		{
			test= new logIn(deviceID);
		}
		 
		test.runTest();
	}

}
