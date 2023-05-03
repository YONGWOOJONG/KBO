import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectBox {
    private WebDriver driver;
    private String selectId;


    public SelectBox(WebDriver driver, String selectId) {
        this.driver = driver;
        this.selectId = selectId;
    }
    
    

    /**
     * 옵션 값을 선택합니다.
     *
     * @param optionValue 선택할 옵션 값
     */
    public void selectOption(String optionValue) {
        WebElement selectElement = driver.findElement(By.id(selectId));
        Select select = new Select(selectElement);
        select.selectByValue(optionValue);
    }
    
    public void selectOptionByText(String optionText) {
        WebElement selectElement = driver.findElement(By.id(selectId));
        Select select = new Select(selectElement);
        select.selectByVisibleText(optionText);
    }
    /**
     * 모든 옵션 값을 가져옵니다.
     *
     * @return 옵션 값 리스트
     */
    public List<String> getOptions() {
        WebElement selectElement = driver.findElement(By.id(selectId));
        Select select = new Select(selectElement);
        List<WebElement> optionElements = select.getOptions();
        List<String> options = new ArrayList<>();
        for (WebElement optionElement : optionElements) {
            String optionValue = optionElement.getAttribute("value");
            if (!optionValue.isEmpty()) {
                options.add(optionValue);
            }
        }
        return options;
    }
    
    
    
 // 모든 옵션 텍스트를 가져옵니다.
    public List<String> getOptionTexts() {
        WebElement selectElement = driver.findElement(By.id(selectId));
        Select select = new Select(selectElement);
        List<WebElement> optionElements = select.getOptions();
        List<String> optionTexts = new ArrayList<>();
        for (WebElement optionElement : optionElements) {
            String optionText = optionElement.getText();
            if (!optionText.isEmpty()) {
                optionTexts.add(optionText);
            }
        }
        return optionTexts;
    }
}