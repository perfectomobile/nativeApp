package test.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.perfectomobile.httpclient.MediaType;
import com.perfectomobile.httpclient.utils.FileUtils;
import com.perfectomobile.selenium.MobileCoordinates;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.MobilePoint;
import com.perfectomobile.selenium.api.IMobileDevice;
import com.perfectomobile.selenium.api.IMobileWebDriver;

public class logIn {

	IMobileDevice _device;
	MobileDriver PMdriver ;
	public logIn(String dev) {

		PMdriver = new MobileDriver();
		_device = PMdriver.getDevice(dev);


	}
	public void logIn()
	{
		IMobileWebDriver  NativeDriver ;
		IMobileWebDriver  VisualDriver;

		_device.open();

		NativeDriver = _device.getNativeDriver("Starbucks");
		VisualDriver = _device.getVisualDriver();

		// Timeout set 

		VisualDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		VisualDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		VisualDriver.manageMobile().visualOptions().validationOptions().setThreshold(95);

		NativeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		NativeDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);


		try {
			NativeDriver.close();

		}
		catch(Exception e) {
			System.out.println("app Close");
		}
		NativeDriver.open();

		try {
			NativeDriver.findElement(By.xpath("//button[text()='SIGN IN']"));

		}
		catch(Exception e) {
			System.out.println("Close App");
			logout(NativeDriver);

		}

		try{
			NativeDriver.findElement(By.xpath("//button[text()='SIGN IN']")).click();

			// Login 
			NativeDriver.findElement(By.xpath("//textfield[contains(text(),'Username')]")).sendKeys("uzi.eilon@gmail.com");
			try{
				NativeDriver.findElement(By.xpath("//secure")).sendKeys("Perfecto1");

			}catch(Exception e)
			{
				NativeDriver.findElement(By.xpath("//textfield[contains(text(),'password')]")).sendKeys("Perfecto1");

			}

			NativeDriver.findElement(By.xpath("//button[text()='SIGN IN']")).click();

			VisualDriver.findElement(By.linkText("PAY")) ;
			logout(NativeDriver);

		}catch(Exception e)
		{
			//test Error
			Assert.assertTrue("LogIn Error"+e.getMessage(),false);
		}finally 
		{
			//download Report 
			_device.close();
			PMdriver.quit();	
			InputStream rep = PMdriver.downloadReport(MediaType.PDF);
			File repFile = new File("C:\\test\\Starbucks"+_device+".pdf");
			try {
				FileUtils.write(rep, repFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void logout(WebDriver w)
	{

		try {
			WebElement menu = w.findElement(By.xpath("(//*[@class='android.widget.ImageView'])[2]"));
			menu.click();
			w.findElement(By.xpath("//text[text()='SETTINGS']")).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_device.getMobileTouchScreen().swipe(new MobileCoordinates(new MobilePoint("50%,85%")), new MobileCoordinates(new MobilePoint("50%,15%")), 3);

			w.findElement(By.xpath("//text[text()='Sign Out']")).click();
			w.findElement(By.xpath("//button[text()='SIGN OUT']")).click();


		} catch (Exception e) {
			// not found = iphone
			w.findElement(By.xpath("//button[text()='Account & Settings']")).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_device.getMobileTouchScreen().swipe(new MobileCoordinates(new MobilePoint("50%,85%")), new MobileCoordinates(new MobilePoint("50%,15%")), 3);
			w.findElement(By.xpath("//button[text()='SIGN OUT']")).click();
			w.findElement(By.xpath("//button[text()='Sign Out']")).click();


		}






	}
}

