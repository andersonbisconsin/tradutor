package starter.threadsTradutor;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import starter.util.H2jdbc;

public class TradutorRunnableImplementation implements Runnable {

    // We are creating anew class that implements the Runnable interface,
    // so we need to override and implement it's only method, run().
    @Override
    public void run() {
		
		boolean continua;
		
		String driverPath = "src/test/resources/webdriver/windows/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
        
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		
		WebDriver wdriver = new ChromeDriver(chromeOptions);

        wdriver.get("https://translate.google.com.br/");
		
		do {
			System.out.println("Thread Executada: " + Thread.currentThread());
			
			H2jdbc banco = new H2jdbc();
			continua = banco.ler();
			if(continua) {
				String[] valor = banco.valoresLeitura;
				
				wdriver.get("https://translate.google.com.br/");
				
				wdriver.findElement(By.id("source")).clear();
				wdriver.findElement(By.id("source")).sendKeys(valor[3]);
				wdriver.findElement(By.id("source")).sendKeys(Keys.ENTER);
				String traducao = "";
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(wdriver.findElements(By.xpath("//*[@class='tlid-translation translation']")).size()>0){
					traducao = wdriver.findElement(By.xpath("//*[@class='tlid-translation translation']")).getText();
				}
				
				
				banco.atualizar(traducao,valor[1]);
			}
		}while(continua);
		
		wdriver.close();
    }
}