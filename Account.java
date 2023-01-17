/*
    账户类
 */
public class Account {

	private String cardId;  // 卡号
	private String userName;    //  用户名
	private String passWord;       //   密码
	private double money;   //  账户余额
	private double quotaMoney;   // 每次取现额度

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
