package week4.day4;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Launch Browser and Go to https://www.nykaa.com/
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//get title of the homepage
		String homePage = driver.getTitle();
		System.out.println(homePage);
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		
//		2) Mouse over on Brands and Search L'Oreal Paris
		WebElement brands = driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brands).clickAndHold().perform();
		
//		3) Click L'Oreal Paris
		driver.findElement(By.xpath("//input[@id='brandSearchBox']")).sendKeys("L'Oreal Paris",Keys.ENTER);
		driver.findElement(By.linkText("L'Oreal Paris")).click();
		
//		4) Check the title contains L'Oreal Paris(Hint-GetTitle)
		driver.getTitle();
		
//			5) Click sort By and select customer top rated
		driver.findElement(By.xpath("//span[text()='Sort By : popularity']")).click();
		driver.findElement(By.xpath("//span[text()='customer top rated']")).click();
		System.out.println("Customer Top Rated Clicked");
		Thread.sleep(5000);
		
		//			6) Click Category and click Hair->Click haircare->Shampoo 
		driver.findElement(By.xpath("//div[@id='filters-strip']/div/div/div/div/span")).click();
		driver.findElement(By.xpath("//ul[@id='custom-scroll']/ul/li/div/span")).click();
		driver.findElement(By.xpath("//ul[@class='scroll css-1e7z8zv']/li/div/span")).click();
		System.out.println("Haircare Selected");
		driver.findElement(By.xpath("//div[@class='control-box css-1ap0cm9']/label/div/span[text()='Shampoo']")).click();
		System.out.println("Shampoo Selected");
		Boolean shampooFilter = true;
		//= driver.findElement(By.xpath("(//label[@class='control control-checkbox']/div/span[@class='title'])[1]")).getText();
		System.out.println(shampooFilter);
		
//			7) Click->Concern->Color Protection
		driver.findElement(By.xpath("//div[@class='css-w2222k']/div/span[text()='Concern']")).click();
		driver.findElement(By.xpath("//div[@class='control-box css-1ap0cm9']/label/div/span[text()='Color Protection']")).click();
		System.out.println("Concern Colour Protection Selected");
		
//			8)check whether the Filter is applied with Shampoo
			if(shampooFilter)
				System.out.println("Shampoo Filter Applied: " + shampooFilter);
		
//			9) Click on L'Oreal Paris Colour Protect Shampoo
			driver.findElement(By.xpath("//div[@class='css-d5z3ro']/a/div/img")).click();
			
 //			10) GO to the new window (get all the windowHandles)
			Set<String> windowHandles = driver.getWindowHandles();
			List<String> handles = new ArrayList<String>(windowHandles);
			System.out.println(handles);
			int tabs = handles.size();
			driver.switchTo().window(handles.get(1));
			
//			10) select size as 175ml,
			WebElement liter = driver.findElement(By.xpath("//div[@class='css-11wjdq4']/select"));		
			Select option = new Select(liter);	
			option.selectByVisibleText("175ml");
			System.out.println("175ml shampoo bottle selected");
			
//			11) Print the MRP of the product
			String price = driver.findElement(By.xpath("//div[@class='css-1d0jf8e']/span[2]")).getText();
			System.out.println("MRP of the product: "+price);
			
//			12) Click on ADD to BAG
			driver.findElement(By.xpath("//div[@class='css-vp18r8']/button/span")).click();
			Thread.sleep(5000);
			
//			13) Go to Shopping Bag 
			driver.findElement(By.xpath("//div[@class='css-0 e1ewpqpu1']/button")).click();
			System.out.println("Shopping Cart displayed");
			
//			//Passing the control to Shopping cart frame
//			WebElement frameCart = driver.findElement(By.xpath("//iframe[@src='/mobileCartIframe?ptype=customIframeCart']"));
//			driver.switchTo().frame(frameCart);
//			System.out.println("Control in Shopping cart frame: "+frameCart);
//			driver.findElement(By.xpath("//div[@class='header-layout css-1h9leo3 e13w5ra50']")).click();
//			
////			14) Print the Grand Total amount
//			String grandTotal = driver.findElement(By.xpath("(//div[@class='css-11m81vr e1d9ugpt3']/p)[3]")).getText();
//			System.out.println(grandTotal);
			
	//			14) Print the Grand Total amount
			WebElement frameCart = driver.findElement(By.xpath("//iframe[@src='/mobileCartIframe?ptype=customIframeCart']"));
			driver.switchTo().frame(frameCart);
	//		System.out.println("Control in Shopping cart frame: "+frameCart);
			String grandTotal = driver.findElement(By.xpath("(//span[text()='Grand Total']//following::div)[1]")).getText();
			System.out.println("The Grand total of the product: "+grandTotal);
			
			
//			15) Click Proceed
			driver.findElement(By.xpath("//span[text()='Proceed']/ancestor::button")).click();
		//	System.out.println("Proceed clicked");
			
//			16) Click on Continue as Guest
			driver.findElement(By.xpath("//div[text()='Checkout as guest']/following-sibling::button")).click();
			System.out.println("Continue as Guest Pressed");
			
//			17) Check if this grand total is the same in step 14
			String finalTotal = driver.findElement(By.xpath("//div[@class='payment-details-tbl grand-total-cell prl20']/div[2]")).getText();
			System.out.println(finalTotal);
			
			if(grandTotal.equals(finalTotal)) {
				System.out.println("Product Total are same - Verified");
			}
			else {
				System.out.println("Product total are not same - verified");
			}
				
//			18) Close all windows
			driver.quit();
	}

}
