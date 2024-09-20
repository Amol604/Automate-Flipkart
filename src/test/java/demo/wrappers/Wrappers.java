package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;
import java.util.HashSet;
import java.text.NumberFormat;
import java.util.Locale;


public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public static void enterTextWrapper(WebDriver driver, By locator, String textToEnter) {
        System.out.println("Sending Keys");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement inputBox = driver.findElement(locator);
            inputBox.clear();
            inputBox.sendKeys(textToEnter);
            inputBox.sendKeys(Keys.ENTER);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }
    }

    public static void clickOnElementWapper(WebDriver driver, By locator) {
        System.out.println("Clicking");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement clickableElement = driver.findElement(locator);
            clickableElement.click();
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }
    }

    public static Boolean seachStarRatingAndPrintCount(WebDriver driver, By locator, double starRating) {
        int washingMachineCount = 0;
        Boolean success;
        try {
            List<WebElement> starRatingElements = driver.findElements(locator);
            for (WebElement starRatinElement : starRatingElements) {
                if (Double.parseDouble(starRatinElement.getText()) <= starRating) {
                    washingMachineCount++;
                }
            }
            System.out.println("Count of washing machine which has star rating less than or equal to " + starRating
                    + ": " + washingMachineCount);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static Boolean printTitleAndDiscountIphone(WebDriver driver, By locator, int discount) {
        Boolean success;
        try {
            List<WebElement> productRows = driver.findElements(locator);
            HashMap<String, String> iphoneTitleDiscountMap = new HashMap<>();
            for (WebElement productRow : productRows) {
               // System.out.println("product");
                String discountPercent = productRow.findElement(By.xpath("//div[@class='UkUFwK']/span")).getText();
              //  System.out.println("product1");
                System.out.println(discountPercent);
                int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                if (discountValue > discount) {
                    String iphoneTitle = productRow.findElement(By.xpath(".//div[@class='KzDlHZ']")).getText();
                    iphoneTitleDiscountMap.put(discountPercent, iphoneTitle);
                }
            }

            for (Map.Entry<String, String> iphoneTitleDiscounts : iphoneTitleDiscountMap.entrySet()) {
                System.out.println("Iphone discount percentage :: " + iphoneTitleDiscounts.getKey()
                        + " and its tile :: " + iphoneTitleDiscounts.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static Boolean printTitleAndImageUrlOfCoffeeMug(WebDriver driver, By locator) {
        Boolean success;
        try {
            List<WebElement> userReviewElements = driver.findElements(locator);
            Set<Integer> userReviewSet = new HashSet<>();
            for (WebElement userReviewElement : userReviewElements) {
                int userReview = Integer.parseInt(userReviewElement.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReview);
            }
            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList,Collections.reverseOrder());
            System.out.println(userReviewCountList);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();
            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "("+numberFormat.format(userReviewCountList.get(i))+")";
                String productTitle = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        +formattedUserReviewCount+ "')]/../../a[@class='wjcEIp']")).getText();
                String productImgURL = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"
                        +formattedUserReviewCount+ "')]/../..//img[@class='DByuf4']")).getAttribute("src");
                String hightestReviewCountAndProductTitle = String.valueOf(i+1)+" highest review count: "+formattedUserReviewCount+" Title: "+productTitle;
                productDetailsMap.put(hightestReviewCountAndProductTitle, productImgURL);
            }
            //print title and image url of coffee mug
            for(Map.Entry<String,String> productDetails : productDetailsMap.entrySet()){
                System.out.println(productDetails.getKey()+" and Product image url: "+productDetails.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}