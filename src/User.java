import org.jetbrains.annotations.NotNull;

final class User {

    private final String userName, accountNumber, password, emailId;
    private static int accountBalance = 500;

    User(String userName, String password, String emailId) {
        this.userName = userName;
        this.accountNumber = generateAccountNumber();
        this.password = password;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        String details[] = {"Name : ", "Number : ", "E-MailID : ", "Balance : ", "Password : "};
        String userInfo;
        userInfo = String.format("%20s%s%n%20s%s%n%20s%s%n%20s%s%n%20s%s%n%20s%s%n\n", "Welcome ", userName, details[0], userName, details[1], accountNumber, details[2], emailId, details[3], accountBalance, details[4], password);
        return userInfo;
    }

    // Getters for Username account password emailId accountBalance
    String getPassword() {
        return password;
    }

    String getAccountNumber() {
        return accountNumber;
    }

    int getAccountBalance() {
        return accountBalance;
    }

    // Setters for Balance
    void setAccountBalance(int accountBalance) {
        User.accountBalance = accountBalance;
    }

    @NotNull
    private String generateAccountNumber() {
        return ("" + this.hashCode()).substring(2);
    }

}
