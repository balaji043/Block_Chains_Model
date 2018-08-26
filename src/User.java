public class User {

    private static String userName;
    private static String accountNumber;
    private static String password;
    private static String emailId;
    private static int accountBalance=500;

    User(String userName, String password, String emailId) {
        User.userName = userName;
        accountNumber = generateAccountNumber();
        User.password = password;
        User.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        User.emailId = emailId;
    }

    private String generateAccountNumber() {
        return "" + Math.random() + 446343d;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        User.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        User.accountNumber = accountNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        User.accountBalance = accountBalance;
    }


}
