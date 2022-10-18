package week4.day4;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonWindow {

	public static void main(String[] args) throws InterruptedException {
//			1.Launch the browser and pass the URL https://www.amazon.in/
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Getting Parent Windowhandle
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		String homePage = driver.getTitle();
		System.out.println(homePage);

		//			2.search as oneplus 9 pro 
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("One Plus 9 Pro",Keys.ENTER);
		
//			3.Get the price of the first product
		String Price = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[4]")).getText();
		System.out.println("Price of the first product: " + Price);
		String firstPrice = Price.replace(",", "");
		double fstPrice = Double.parseDouble(firstPrice);
		System.out.println(fstPrice);
//			5. Click the first text link of the first image
		driver.findElement(By.xpath("//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']")).click();
		
//			6. Take a screen shot of the product displayed
//		File source = driver.getScreenshotAs(OutputType.FILE);
//		File destination = new File("./Snaps/oneplus9pro.png");
//		FileUtils.copyToDirectory(source, destination);

		//get all the windowhandles
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> handles = new ArrayList<String>(windowHandles);
		System.out.println(handles);
		int tabs = handles.size();
		driver.switchTo().window(handles.get(1));
	
//		4. Print the number of customer ratings for the first displayed product
	WebElement starRating = driver.findElement(By.xpath("//i[@class='a-icon a-icon-star a-star-4']/span"));
	//using actions class to move to Rating
	Actions builder = new Actions(driver);
	builder.moveToElement(starRating).click().perform();
	Thread.sleep(3000);
	String rating=driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-beside-button a-text-bold']")).getText();
	System.out.println("Customer rating of the first product: " + rating);
		
//			7. Click 'Add to Cart' button
		driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();
		driver.findElement(By.xpath("//a[@id='attach-close_sideSheet-link']")).click();
		System.out.println("Product added to cart successfully");
		
		Thread.sleep(5000);
//			8. Get the cart subtotal and verify if it is correct.
		driver.findElement(By.xpath("//span[@id='nav-cart-count']")).click();
	
		String Price1= driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText();
		String finalPrice = Price1.replace(",", "");
		double netPrice = Double.parseDouble(finalPrice);
		System.out.println(netPrice);

		String cartWindow = driver.getWindowHandle();
		System.out.println(cartWindow);
		String cartPage = driver.getTitle();

		if (fstPrice == netPrice)
		{
			System.out.println("Prices are same - Verified");
		}
		else {
			System.out.println("Prices are not same - Verified");
		}
//			9.close the browsers tab
		driver.quit();
	}

}
