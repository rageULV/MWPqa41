package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    private WebDriver driver;


    public WebDriver getDriver() {
        return driver;
    }
private void initializeDriver(String browser){
    if (browser.equalsIgnoreCase("chrome")) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
    } else if (browser.equalsIgnoreCase("firefox")) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("intl.accept_languages", "en");
        driver = new FirefoxDriver(options);
    } else {
        throw new IllegalArgumentException("Invalid browser " + browser);
    }
}
    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser) {
        initializeDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeGroups(groups = {"group1"})
    public void setUpForGroup1() {
        initializeDriver("firefox");
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //BasePage.setDriver(driver);
    }
    @AfterGroups(groups = {"group1"})
    public void tearDownGroup() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();

            //driverThreadLocal.remove();
        }
    }


}

/*

public class BaseTest {     private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }


    // Эти строки аннотируют метод setUp как метод, который должен быть выполнен перед каждым тестовым методом (@BeforeMethod), и указывают, что он принимает параметр browser из файла конфигурации.
    // Аннотация @Optional("firefox") означает, что значение по умолчанию для browser - это "firefox".
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser){

        // Этот блок кода проверяет, является ли значение параметра browser равным "chrome".
        // Если да, то он настраивает ChromeDriver и добавляет опции для запуска браузера на английском языке.
        if(browser.equalsIgnoreCase("chrome")){



            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");
           //  options.addArguments("--headless");
            driver=new ChromeDriver(options);
        }
        // Аналогично предыдущему блоку, но если browser равен "firefox",
        // то настраивается FirefoxDriver и добавляются опции для запуска браузера на английском языке.
        else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.accept_languages", "en");
            // options.addArguments("-headless");
            driver=new FirefoxDriver(options);
        }

        else{throw new IllegalArgumentException("Invalid browser "+browser); }

        // Этот блок кода получает веб-драйвер с помощью метода getDriver(), максимизирует окно браузера,
        // устанавливает время ожидания загрузки страницы и неявного ожидания, а затем устанавливает этот драйвер для BasePage.
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000)); // Здесь устанавливается время ожидания для загрузки страницы. Если страница не загружается в течение указанного времени (в данном случае, 20 секунд), будет сгенерировано исключение.
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
        BasePage.setDriver(driver); // Этот вызов используется для установки экземпляра драйвера в базовом классе страницы. это полезно т.к. есть базовый класс для всех ваших страниц, который управляет инициализацией драйвера.
    }

    @AfterMethod
    public void tearDown(){
        WebDriver driver = getDriver(); // Получаем текущий экземпляр WebDriver с помощью метода getDriver().
        if (driver != null){ // Проверяем, что экземпляр драйвера не равен null.
            driver.quit(); // Если драйвер не равен null, то закрываем браузер с помощью метода quit().
          //  driverThreadLocal.remove(); // Удаляем текущий экземпляр WebDriver из объекта driverThreadLocal.
        }
    }

}
*/
