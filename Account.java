/*Account
 */
public class Account {

	private String cardId;  
	private String userName;     
	private String passWord;        
	private double money;   
	private double quotaMoney;   

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getQuotaMoney() {
		return quotaMoney;
	}

	public void setQuotaMoney(Double quotaMoney) {
		this.quotaMoney = quotaMoney;
	}
}
