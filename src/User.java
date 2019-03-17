import java.util.ArrayList;
import java.util.Objects;

class User {

    private int accountBalance;
    private final String accountNumber;
    private String userName, password, emailId;
    private ArrayList<Block> blockArrayList;

    User(String userName, String password, String emailId, int accountBalance, ArrayList<Block> blockArrayList) {
        this.userName = userName;
        this.accountNumber = generateAccountNumber();
        this.password = password;
        this.emailId = emailId;
        this.accountBalance = accountBalance;
        this.blockArrayList = blockArrayList;
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
        String[] details = {"Name : ", "Number : ", "E-MailID : ", "Balance : ", "Password : "};
        String userInfo;
        userInfo = String.format("%20s%s%n%20s%s%n%20s%s%n%20s%s%n%20s%s%n%20s%s%n\n", "Welcome ", userName, details[0], userName, details[1]
                , accountNumber, details[2], emailId, details[3], accountBalance, details[4], password);
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

    private String generateAccountNumber() {
        return ("" + this.hashCode()).substring(2);
    }

    void add(String amount) {
        accountBalance = accountBalance + Integer.parseInt(amount);
    }

    void sub(String amount) {
        accountBalance = accountBalance - Integer.parseInt(amount);
    }

    void addBlock(Block block) {
        blockArrayList.add(block);
    }

    @SuppressWarnings("Duplicates")
    public int getTotal(String sender) {
        int total = 0;
        for (Block block : blockArrayList) {
            if (sender.equals(block.getReciever())) {
                total = total + Integer.parseInt(block.getAmount());
            }
        }
        return 0;
    }
}
