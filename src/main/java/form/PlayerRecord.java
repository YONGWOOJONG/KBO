package form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerRecord {
	private String playerId;
    private String playerName;
    private int jerseyNumber;
    private String dateOfBirth;
    private String position;
    private int height;
    private int weight;
    private String career;
    private int signingBonus;
    private int salary;
    private String draftRank;
    private String debutYear;
    

    public PlayerRecord(String playerName, int jerseyNumber, String dateOfBirth, String position, int height, int weight, String career, int signingBonus, int salary, String draftRank, String debutYear) {
        this.playerName = playerName;
        this.jerseyNumber = jerseyNumber;
        
        
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parsedDate = oldFormat.parse(dateOfBirth);
            String newDateOfBirth = newFormat.format(parsedDate);
            System.out.println("변환된 날짜: " + newDateOfBirth);
            this.dateOfBirth = newDateOfBirth;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.position = position;
        this.height = height;
        this.weight = weight;
        this.career = career;
        this.signingBonus = signingBonus;
        this.salary = salary;
        this.draftRank = draftRank;
        this.debutYear = debutYear;
        this.playerId = playerName + "_" + this.dateOfBirth;
        System.out.println(playerId+"플레이어 아이디");
        
    }

   

    public String getPlayerId() {
		return playerId;
	}



	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}



	public String getPlayerName() {
		return playerName;
	}



	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}



	public int getJerseyNumber() {
		return jerseyNumber;
	}



	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}



	public String getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public int getWeight() {
		return weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}



	public String getCareer() {
		return career;
	}



	public void setCareer(String career) {
		this.career = career;
	}



	public int getSigningBonus() {
		return signingBonus;
	}



	public void setSigningBonus(int signingBonus) {
		this.signingBonus = signingBonus;
	}



	public int getSalary() {
		return salary;
	}



	public void setSalary(int salary) {
		this.salary = salary;
	}



	public String getDraftRank() {
		return draftRank;
	}



	public void setDraftRank(String draftRank) {
		this.draftRank = draftRank;
	}



	public String getDebutYear() {
		return debutYear;
	}



	public void setDebutYear(String debutYear) {
		this.debutYear = debutYear;
	}



	@Override
    public String toString() {
        return "PlayerRecord{" +
                "playerName='" + playerName + '\'' +
                ", jerseyNumber=" + jerseyNumber +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", position='" + position + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", career='" + career + '\'' +
                ", signingBonus=" + signingBonus +
                ", salary=" + salary +
                ", draftRank='" + draftRank + '\'' +
                ", debutYear='" + debutYear + '\'' +
                '}';
    }
}