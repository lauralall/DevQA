import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DriverApp {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        // Open browser at url
        driver.get("https://www.playtechpeople.com");

        // Explicit wait for elements to become visible/interactable
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Finding the div element in which the Teams are
        WebElement teamsColumn = driver.findElement(By.xpath("//h6[text()='Teams']/parent::div")); //possibly not the best approach, but works for me now, might change later
        // Locate teams by cssSelector
        List<WebElement> teams = teamsColumn.findElements(By.cssSelector("li.menu-item-type-custom"));
        // For each team, print out the text (team name)
        for (WebElement team : teams) {
            String text = team.getText();
            System.out.println(text);
        }

        // Find "Life at Playtech"
        WebElement lifeAtPlaytech = driver.findElement(By.id("menu-item-49"));

        // Actions class for mouse interactions
        Actions actions = new Actions(driver);
        // Move cursor to "Life at Playtech"
        actions.moveToElement(lifeAtPlaytech).perform();
        // Wait until "Who we are" becomes visible, then click it to navigate
        WebElement whoWeAre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-item-47")));
        whoWeAre.click();

        // Find the Research button by aria-controls
        WebElement researchButton = driver.findElement(By.cssSelector("button[aria-controls='collapse-6-4-6'"));
        // Scroll to the button
        actions.scrollToElement(researchButton).perform();
        // Wait until it is clickable, then click
        researchButton = wait.until(ExpectedConditions.elementToBeClickable(researchButton));
        researchButton.click();

        // Wait until the opened container is visible
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("collapse-6-4-6")));
        //Locate fields in which research is conducted by css selector, only picking the li items inside ul
        List<WebElement> researchFields = container.findElements(By.cssSelector("li ul li"));
        // For each field, print out the text
        for (WebElement field : researchFields) {
            String text = field.getText();
            System.out.println(text);
        }

        driver.quit();
    }
}