import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DriverApp {
    public static void main(String[] args) {

        // Writer write info into a .txt file
        SaveToFile writer = new SaveToFile("results.txt");
        WebDriver driver = new ChromeDriver();
        // Open browser at url
        driver.get("https://www.playtechpeople.com");

        // Explicit wait for elements to become visible/interactable
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Finding the div element in which the Teams are
        WebElement teamsColumn = driver.findElement(By.xpath("//h6[text()='Teams']/parent::div"));
        // Locate teams by cssSelector
        List<WebElement> teams = teamsColumn.findElements(By.cssSelector("li.menu-item-type-custom"));
        // Write into file
        writer.write("Teams: ");
        System.out.println("Teams: ");
        // For each team, write into file and print out team name
        for (WebElement team : teams) {
            String text = team.getText();
            writer.write(text);
            System.out.println(text);
        }

        // Write empty line
        writer.write("");
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
        // Scroll to the container
        actions.scrollToElement(container).perform();
        //Locate fields in which research is conducted by css selector, only picking the li items inside ul
        List<WebElement> researchFields = container.findElements(By.cssSelector("li ul li"));
        writer.write("Research fields: ");
        System.out.println("Research fields: ");
        // For each field, write to file and print out the text
        for (WebElement field : researchFields) {
            String text = field.getText();
            writer.write(text);
            System.out.println(text);
        }
        writer.write("");

        // Longer wait to prevent stale items
        Wait<WebDriver> wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));

        //Locate All Jobs button, wait, click it
        WebElement jobsButton = driver.findElement(By.className("yellow-button"));
        jobsButton = wait1.until(ExpectedConditions.elementToBeClickable(jobsButton));
        jobsButton.click();

        // Find all job offers in estonia
        List<WebElement> jobOffers = driver.findElements(By.cssSelector("a[data-location='estonia']"));

        // List of job offer urls
        List<String> jobUrl = new ArrayList<>();
        // Add url's to the list
        for (WebElement offer : jobOffers) {
            String link = offer.getAttribute("href");
            // Not adding null values or duplicate links
            if (link != null && !jobUrl.contains(link)) {
                jobUrl.add(link);
            }
        }

        // Variables to keep track of how many Tallinn and Tartu links there are
        String tallinn = null;
        String tartu = null;

        writer.write("Available job offers: ");
        System.out.println("Available job offers: ");
        // Loop through each link
        for (String url : jobUrl) {
            driver.get(url);

            // Find shadow host element and access shadow root
            WebElement shadowHost = driver.findElement(By.cssSelector("spl-job-location"));
            SearchContext shadowRoot = shadowHost.getShadowRoot();

            // Find job location and save it as String
            WebElement location = shadowRoot.findElement(By.className("c-spl-job-location__place"));
            String city = location.getText().toLowerCase();

            // Print out and write to file the link for a job in Tartu
            if (city.contains("tartu") && !city.contains("tallinn") && tartu == null) {
                tartu = url;
                writer.write("Tartu: " + url);
                System.out.println(url);
            }

            // Print out and write to file the link for a job in Tallinn
            if (city.contains("tallinn") && !city.contains("tartu") && tallinn == null) {
                tallinn = url;
                writer.write("Tallinn: " +url);
                System.out.println(url);
            }
        }
        writer.close();
        driver.quit();
    }
}