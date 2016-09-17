import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.cssSelector;


public class MainTest {

    @Test
    public void startWebDriver() throws Exception {
        //System.setProperty("webdriver.ie.driver", "/usr/bin/IEDriverServer");
        //WebDriver driver = new InternetExplorerDriver();

        WebDriver d = new ChromeDriver();
        System.setProperty("d.chrome.driver", "/usr/bin/chromedriver");

        d.get("http://www.gmail.com");
        d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Store the current windows handles
        Set<String> winFirst = d.getWindowHandles();

        //login
        d.findElement(By.xpath("./*//*[@id='Email']")).sendKeys("topaloha@mail.ru");
        d.findElement(By.xpath("./*//*[@id='Email']")).submit();
        //d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        d.findElement(By.xpath("./*//*[@id='Passwd']")).sendKeys("18061991A");
        d.findElement(By.cssSelector("#Passwd")).submit();
        Thread.sleep(6000);

        //Create a latter
        d.findElement(By.cssSelector("div.nM div div div ")).click();
        //d.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        d.findElement(By.cssSelector("textarea.vO")).clear();
        d.findElement(By.cssSelector("textarea.vO")).sendKeys("mdkdksldlfkl@gmail.com");
        d.findElement(By.cssSelector(".AD input[name=\"subjectbox\"]")).clear();
        d.findElement(By.cssSelector(".AD input[name=\"subjectbox\"]")).sendKeys("testing form");
        d.findElement(By.cssSelector(".AD div[role=\"textbox\"]")).clear();
        d.findElement(By.cssSelector(".AD div[role=\"textbox\"]")).sendKeys("Hello there, pinch yourself coz this mtf working ^^");

        d.findElement(By.xpath("//div[@class='wG J-Z-I']")).click();
        Thread.sleep(2000);

        //attachment, change the path to yours
        StringSelection s = new StringSelection("~/Desktop/Linux_Hints");
        Clipboard pass = Toolkit.getDefaultToolkit().getSystemClipboard();
        pass.setContents(s, null);

        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        robot.delay(3000);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.delay(3000);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(3000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(6000);

        //send letter
        d.findElement(By.cssSelector(".AD .n1tfz td div")).click();
        Thread.sleep(5000);

        //log out
        d.findElement(By.cssSelector("a[role=\"button\"] span.gbii")).click();
        Thread.sleep(2000);

        //..log out
        d.findElement(By.cssSelector("div.gb_wb .gb_vb")).click();
        Thread.sleep(2000);
        d.close(); //close winFirst

        //switch to new window
        Set<String> allWindows = d.getWindowHandles();// store name of all the windows opene
        allWindows.removeAll(winFirst);
        String winSecond = allWindows.iterator().next();
        d.switchTo().window(winSecond);


        //log in
        d.findElement(By.xpath(".//*[@id='Email']")).sendKeys("mdkdksldlfkl@gmail.com");
        d.findElement(By.xpath(".//*[@id='Email']")).submit();
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        d.findElement(By.xpath(".//*[@id='Passwd']")).sendKeys("topaloha");
        d.findElement(cssSelector("#Passwd")).submit();
        Thread.sleep(6000);

        //find the letter by its theme
        java.util.List<WebElement> a = d.findElements(By.xpath("//*[@class='y6']/span[1]"));
        System.out.println(a.size());
        for (int i = 0; i < a.size(); i++) {
            //System.out.println(a.get(i).getText());
            if (a.get(i).getText().matches("testing form")) //to click on a specific mail.
            {
                a.get(i).click();
            }
        }
        Thread.sleep(250);
        //download the attachment
        d.findElement(By.cssSelector("div[aria-label*=\"Download attachment\"]")).click();
        Thread.sleep(2000);

        //check the text from attachment

        //"/home/d/Downloads/Linux_Hints" ;
        String first = "/home/d/Downloads/Linux_Hints"; // file that we downloaded
        String second = "/home/d/Desktop/Linux_Hints"; // are original file
        BufferedReader fBr = new BufferedReader(new FileReader(first));
        BufferedReader sBr = new BufferedReader(new FileReader(second));

        ArrayList<String> strings = new ArrayList<String>();

        while ((first = fBr.readLine()) != null) {
            strings.add(first);
        }
        fBr.close();

        while ((second = sBr.readLine()) != null) {
            if (strings.contains(second)) {
                System.out.println(second);
            }
        }
        sBr.close();
    }
}