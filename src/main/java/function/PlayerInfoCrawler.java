package function;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import form.PlayerRecord;

public class PlayerInfoCrawler {

	public static PlayerRecord crawlPlayerBasicInfo(WebDriver driver) {

		List<WebElement> playerInfoElements = driver
				.findElements(By.xpath("/html/body/form/div[3]/section/div/div/div[2]/div[1]/div[1]/ul/li/span"));

		String[] playerInfo = new String[playerInfoElements.size()];

		for (int i = 0; i < playerInfoElements.size(); i++) {
			playerInfo[i] = playerInfoElements.get(i).getText();
			System.out.println(playerInfo[i]);
		}

		// 선수 정보 초기화
		String playerName = "";
		int jerseyNumber = 0;
		String dateOfBirth = "";
		String position = "";
		int height = 0;
		int weight = 0;
		String career = "";
		int signingBonus = 0;
		int salary = 0;
		String draftRank = "";
		String debutYear = "";

		// 선수 정보 추출 (4개의 정보가 있는 경우)
		if (playerInfoElements.size() == 4) {
			playerName = playerInfo[0];
			dateOfBirth = playerInfo[1];
			career = playerInfo[2];
			draftRank = playerInfo[3];
		}

		// 선수 정보 추출 (10개의 정보가 있는 경우)
		if (playerInfoElements.size() == 10) {
			playerName = playerInfo[0];
			jerseyNumber = playerInfo[1].isEmpty() ? 0 : Integer.parseInt(playerInfo[1]);
			dateOfBirth = playerInfo[2];
			position = playerInfo[3];
			String[] heightWeight = playerInfo[4].split("cm/");
			height = heightWeight[0].trim().isEmpty() ? 0 : Integer.parseInt(heightWeight[0].trim());
			weight = heightWeight[1].replace("kg", "").trim().isEmpty() ? 0
					: Integer.parseInt(heightWeight[1].replace("kg", "").trim());
			career = playerInfo[5];

			// signingBonus 처리: 원화(만원) 또는 달러
			String signingBonusStr = playerInfo[6].replace("만원", "").replace("달러", "").replace(",", "").trim();
			if (!signingBonusStr.isEmpty()) {
				signingBonus = Integer.parseInt(signingBonusStr);
				if (playerInfo[6].contains("달러")) {
					signingBonus = signingBonus * 1200; // 달러를 원화로 환산 (환율은 예시입니다)
				}
			} else {
				signingBonus = 0;
			}

			// salary 처리: 원화(만원)
			String salaryStr = playerInfo[7].replace("만원", "").replace("달러", "").replace(",", "").trim();
			if (!salaryStr.isEmpty()) {
				salary = Integer.parseInt(salaryStr);
				if (playerInfo[6].contains("달러")) {
					salary = salary * 1200; // 달러를 원화로 환산 (환율은 예시입니다)
				}
			} else {
				salary = 0;
			}

			draftRank = playerInfo[8];
			debutYear = playerInfo[9];
		}

		// 선수 정보를 PlayerRecord 객체로 반환합니다.
		return new PlayerRecord(playerName, jerseyNumber, dateOfBirth, position, height, weight, career, signingBonus,
				salary, draftRank, debutYear);
	}
}