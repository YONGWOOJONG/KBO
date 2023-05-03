package save;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import form.PlayerRecord;

public class ExcelToDB {

	
	public List<String[]> readExcelData(String filePath, String SEASON, String TEAM) {
		 List<String[]> data = new ArrayList<>();
		    try (FileInputStream inputStream = new FileInputStream(filePath)) {
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet sheet = workbook.getSheetAt(0);

		        // Get the last row in the sheet
		        int lastRowIndex = sheet.getLastRowNum();
		        Row lastRow = sheet.getRow(lastRowIndex);

		        String[] rowData = new String[lastRow.getLastCellNum()];
		        for (int i = 0; i < lastRow.getLastCellNum(); i++) {
		            Cell cell = lastRow.getCell(i);
		            rowData[i] = cell.toString();
		        }

		        data.add(rowData);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return data;
		}
	
	public void insertDataIntoDB(String url, String user, String password, List<String[]> data, String ID, String team, String SEASON) {
		System.out.println(ID+"1111111");
		String updateQuery = "UPDATE PLYR_INFO SET"
				+ "    KBO_PLYR_HT = ?,"
				+ "    KBO_PLYR_WT = ?,"
				+ "    PLYR_CAR = ?,"
				+ "    KBO_PLYR_SALARY = ?,"
				+ "    KBO_PLYR_SIGNING_BONUS = ?,"
				+ "    KBO_PLYR_DRAFT = ?,"
				+ "    KBO_PLYR_IMG = ?"
				+ "WHERE"
				+ "    PLYR_ID = ? AND RC_YEAR = ?;";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

			for (String[] row : data) {
//				for (int i = 2; i < row.length; i++) {
//					preparedStatement.setString(i + 1, row[i]);
//				}
				preparedStatement.setString(1, row[4]);
				preparedStatement.setString(2, row[5]);
				preparedStatement.setString(3, row[6]);
				preparedStatement.setString(4, row[7]);
				preparedStatement.setString(5, row[8]);
				preparedStatement.setString(6, row[9]);
				preparedStatement.setString(7, "http://192.168.70.181:8081/IMG/player/"+ team + "/" + team +"_"+ ID+".jpg");
				preparedStatement.setString(8, ID);
				preparedStatement.setString(9, SEASON);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
