import java.util.Objects;

class User {

    private static int accountBalance;
    private String userName, accountNumber, password, emailId;

    User(String userName, String password, String emailId, int accountBalance) {
        this.userName = userName;
        this.accountNumber = generateAccountNumber();
        this.password = password;
        this.emailId = emailId;
        User.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(accountNumber, user.accountNumber) &&
                Objects.equals(password, user.password) &&
                Objects.equals(emailId, user.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, accountNumber, password, emailId);
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

    private String generateAccountNumber() {
        return ("" + this.hashCode()).substring(2);
    }

}
