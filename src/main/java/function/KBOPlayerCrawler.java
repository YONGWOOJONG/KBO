//package function;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import form.PlayerRecord;
//
//public class KBOPlayerCrawler implements WebCrawler {
//
//    private final WebDriver driver;
//    private final Connection conn;
//    private static final Logger log = LoggerFactory.getLogger(KBOPlayerCrawler.class);
//
//    public KBOPlayerCrawler(WebDriver driver, Connection conn) {
//        this.driver = driver;
//        this.conn = conn;
//    }
//
//    @Override
//    public void crawl(String year, String team, String position, String url) throws InterruptedException {
//        // 페이지를 로딩합니다.
//        driver.get(url);
//        Thread.sleep(3000);
//
//        // 페이지에서 원하는 요소를 찾습니다.
//        List<WebElement> trElements = driver.findElements(By.cssSelector("table.tbl.tt.mb5 > tbody > tr"));
//
//        // PlayerRecord 객체를 생성합니다.
//        List<PlayerRecord> playerRecords = new ArrayList<>();
//        for (WebElement trElement : trElements) {
//            System.out.println(trElement.getText()); // 각 행의 데이터를 출력합니다.
//            List<WebElement> tdElements = trElement.findElements(By.tagName("td"));
//            playerRecords.add(new PlayerRecord(tdElements));
//        }
//
//        // PlayerRecord 객체를 이용하여 SQL 쿼리를 생성합니다.
//    	String sql = "INSERT INTO kbo_player_daily_stats (date, opposing_team, AVG1, PA, AB, R, H, 2B, 3B, HR, RBI, SB, CS, BB, HBP, SO, GDP, AVG2) "
//                + "VALUES (STR_TO_DATE(?, '%Y-%m-%d'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//       PreparedStatement pstmt = null;
//       try {
//           pstmt = conn.prepareStatement(sql);
//           for (PlayerRecord playerRecord : playerRecords) {
//                pstmt.setString(1, playerRecord.getDate());
//                pstmt.setString(2, playerRecord.getOpposingTeam());
//                pstmt.setDouble(3, playerRecord.getAvg1());
//                pstmt.setInt(4, playerRecord.getPa());
//                pstmt.setInt(5, playerRecord.getAb());
//                pstmt.setInt(6, playerRecord.getR());
//                pstmt.setInt(7, playerRecord.getH());
//                pstmt.setInt(8, playerRecord.getDoubleB());
//                pstmt.setInt(9, playerRecord.getTripleB());
//                pstmt.setInt(10, playerRecord.getHr());
//                pstmt.setInt(11, playerRecord.getRbi());
//                pstmt.setInt(12, playerRecord.getSb());
//                pstmt.setInt(13, playerRecord.getCs());
//                pstmt.setInt(14, playerRecord.getBb());
//                pstmt.setInt(15, playerRecord.getHbp());
//                pstmt.setInt(16, playerRecord.getSo());
//	            pstmt.setInt(17, playerRecord.getGdp());
//	            pstmt.setDouble(18, playerRecord.getAvg2());
//	            pstmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            log.error("Failed to execute SQL query", e);
//        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//                    log.error("Failed to close PreparedStatement", e);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void close() {
//    	// WebDriver와 DB Connection을 종료합니다.
//    	driver.quit();
//    	try {
//    		conn.close();
//    	} catch (SQLException e) {
//    		log.error("Failed to close connection", e);
//    	}
//    }
//}