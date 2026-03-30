import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DriverApp {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        // Open browser at url
        driver.get("https://www.playtechpeople.com");

        //Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Finding the div element in which the Teams are
        WebElement teamsColumn = driver.findElement(By.xpath("//h6[text()='Teams']/parent::div")); //possibly not the best approach, but works for me now, might change later
        // Locate teams by cssSelector
        List<WebElement> teams = teamsColumn.findElements(By.cssSelector("li.menu-item-type-custom"));
        // For each team, print out the text (team name)
        for (WebElement team : teams) {
            String text = team.getText();
            System.out.println(text);
        }

        driver.quit();
    }
}