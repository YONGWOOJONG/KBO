import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import function.PlayerInfoCrawler;
import save.ExcelToDB;
import save.ImageDownloader;
import save.SaveToExcel;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import form.PlayerRecord;

public class Main {
	public static void main(String[] args) throws Exception {

		// properties 파일을 로딩합니다.
		Properties prop = ConfigLoader.loadProperties();

		// WebDriver 객체를 생성합니다.
		System.setProperty("webdriver.edge.driver", "C:\\edgedriver_win64 (1)\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();

		// WebDriverWait 객체를 생성합니다.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// DB Connection 객체를 생성합니다.
		DatabaseConnector dbConn = new DatabaseConnector(prop);
		Connection conn = dbConn.getConnection();
		
		// 메인 클래스에서 ExcelToDB 사용
		ExcelToDB excelToDB = new ExcelToDB();

		// 크롤링할 페이지 URL을 설정합니다.
		String url = "https://www.koreabaseball.com/Record/Player/HitterBasic/BasicOld.aspx?sort=HRA_RT";

		// 페이지를 로드합니다.
		driver.get(url);

		// SelectBox 객체를 생성합니다.
		SelectBox yearSelectBox = new SelectBox(driver, "cphContents_cphContents_cphContents_ddlSeason_ddlSeason");
		SelectBox seriesSelectBox = new SelectBox(driver, "cphContents_cphContents_cphContents_ddlSeries_ddlSeries");
		SelectBox teamSelectBox = new SelectBox(driver, "cphContents_cphContents_cphContents_ddlTeam_ddlTeam");

//		SelectBox positionSelectBox = new SelectBox(driver, "cphContents_cphContents_cphContents_ddlPos_ddlPos");

		// 크롤링을 시작합니다.
		System.out.println("크롤링을 시작합니다.");

		// 년도 셀렉트 박스에서 2021년, 2022년을 선택합니다.
		List<String> yearTexts = yearSelectBox.getOptionTexts();
		for (String yearText : yearTexts) {
			if (yearText.equals("2021") || yearText.equals("2022")) {
				System.out.println(yearText);
				yearSelectBox.selectOption(yearText);
				wait.until(ExpectedConditions.stalenessOf(
						driver.findElement(By.id("cphContents_cphContents_cphContents_ddlSeason_ddlSeason"))));

				// 정규시즌을 선택합니다.
				seriesSelectBox.selectOptionByText("KBO 정규시즌");
				Thread.sleep(1000);

				// 팀 셀렉트 박스에서 모든 팀을 선택합니다.
				List<String> teamTexts = teamSelectBox.getOptionTexts();
				for (int j = 1; j < teamTexts.size(); j++) {
					if (!teamTexts.isEmpty()) {
						System.out.println(teamTexts.get(j));
						teamSelectBox.selectOptionByText(teamTexts.get(j));
						wait.until(ExpectedConditions.stalenessOf(
								driver.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam"))));

						// 포지션 셀렉트 박스에서 모든 포지션을 선택합니다.
//						List<String> positionOptions = positionSelectBox.getOptions();
//						for (String positionOption : positionOptions) {
//							if (!positionOption.isEmpty()) {
//								positionSelectBox.selectOption(positionOption);
//								Thread.sleep(2000);

						System.out.println("돈다돌아");

						// 페이지에서 선수 리스트를 찾습니다.
						List<WebElement> playerList = driver.findElements(
								By.cssSelector("div.sub-content > div > div.record_result > table > tbody > tr"));
						System.out.println(playerList.size() + "명의 선수를 찾았습니다.");

						// 각각의 선수 상세 페이지를 방문하여 정보를 크롤링합니다.
						for (int i = 0; i < playerList.size(); i++) {
							System.out.println(playerList.get(i).getText());
							WebElement nameElement = playerList.get(i)
									.findElement(By.cssSelector("td:nth-child(2) > a"));
							// 선수 상세페이지 클릭

							nameElement.click();
							wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.player_basic")));
							// 선수 정보를 크롤링합니다.
							PlayerRecord playerRecord = PlayerInfoCrawler.crawlPlayerBasicInfo(driver);

							// 크롤링한 선수 정보를 출력합니다.
							System.out.println(playerRecord.toString());

							// 크롤링한 선수 정보를 엑셀 파일에 저장합니다.
							List<String[]> data = new ArrayList<>();
							data.add(new String[] { playerRecord.getPlayerName(),
									Integer.toString(playerRecord.getJerseyNumber()), playerRecord.getDateOfBirth(),
									playerRecord.getPosition(), Integer.toString(playerRecord.getHeight()),
									Integer.toString(playerRecord.getWeight()), playerRecord.getCareer(),
									Integer.toString(playerRecord.getSigningBonus()),
									Integer.toString(playerRecord.getSalary()), playerRecord.getDraftRank(),
									playerRecord.getDebutYear() });
							
							

							String fileName = "C:\\Users\\INSD\\Documents\\Excel\\" + "KBO" + "\\" + teamTexts.get(j)
									+ "\\" + yearText + "_" + teamTexts.get(j) + "KBOplyr.xlsx";
							System.out.println(fileName);
							SaveToExcel.saveDataToFile(fileName, data);

							// 이미지를 선택합니다 (이 예에서는 페이지 내의 첫 번째 이미지를 선택합니다).
					        WebElement imageElement = driver.findElement(By.xpath("/html/body/form/div[3]/section/div/div/div[2]/div[1]/div[1]/div/img"));
					        String imageUrl = imageElement.getAttribute("src");

					        // 이미지 URL로부터 이미지를 다운로드합니다.
					        ImageDownloader.downloadImage(imageUrl, "C:\\Users\\INSD\\Documents\\Excel\\IMG\\player\\"+ teamTexts.get(j) +"\\"+ teamTexts.get(j)+"_"+playerRecord.getPlayerId() + ".jpg");
							
							
							// Excel 파일에서 데이터를 읽고 MySQL 데이터베이스에 저장
							String excelFilePath = "C:\\Users\\INSD\\Documents\\Excel\\" + "KBO" + "\\" + teamTexts.get(j)
							+ "\\" + yearText + "_" + teamTexts.get(j) + "KBOplyr.xlsx";

							String jdbcUrl = "jdbc:mysql://localhost:3306/KBO_DB_SSS?useSSL=false";
							String dbUser = "root";
							String dbPassword = "1234";

							// Excel 파일에서 데이터 읽기
							List<String[]> excelData = excelToDB.readExcelData(excelFilePath, yearText, teamTexts.get(j));
							for (int z= 0; z < excelData.size(); z++) {
								System.out.println(excelData.get(z) +"222");
							}
							// 데이터베이스에 데이터 삽입
							excelToDB.insertDataIntoDB(jdbcUrl, dbUser, dbPassword, excelData, playerRecord.getPlayerId(), teamTexts.get(j), yearText);
			
							// 데이터 초기화
							excelData.clear();

							// 뒤로 가기 버튼을 클릭합니다.
							driver.navigate().back();
							System.out.println("뒤로가기");
							Thread.sleep(500);

							teamSelectBox.selectOptionByText(teamTexts.get(j));

							// 페이지에서 선수 리스트를 다시 찾습니다.
							playerList = driver.findElements(
									By.cssSelector("div.sub-content > div > div.record_result > table > tbody > tr"));

						}
					}
				}
			}
			
		}
		// 객체를 종료합니다.
//		crawler.close();

		// 크롤링이 모두 완료되었습니다.
		System.out.println("크롤링이 모두 완료되었습니다.");

	}
}