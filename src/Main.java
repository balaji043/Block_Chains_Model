import java.util.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static int passwordCounter;
    private static boolean check;
    private static ArrayList<User> userArrayList = new ArrayList<>();
    private static ArrayList<Block> blockChain = new ArrayList<>();

    public static void main(String[] args) {
        String errorMessageOption = "Enter Correct option", exitingMessage = "Exiting Menu";
        String[] menu = {"Create User-1", "Make Transfer-2", "View Account Info-3", "Exit Menu-4", "Enter Your Option"};
        addUsers();
        boolean breaker = true;
        while (breaker) {
            System.out.printf("%20s\n%20s\n%20s\n%20s\n\n%20s\n", menu[0], menu[1], menu[2], menu[3], menu[4]);
            int option = scanner.nextInt();
            switch (option) {
                case 1: {
                    createUser(); //Create user
                    break;
                }
                case 2: {
                    transfer(); //Transfer amount
                    break;
                }
                case 3: {
                    viewAccountInfo();//View Account Details
                    break;
                }
                case 4: {
                    System.out.println(exitingMessage);
                    breaker = false;
                    break;
                }
                case 5: {
                    showAll();
                }
                case 6: {
                    printBlockchain();
                }
                default: {
                    System.out.println(errorMessageOption);
                    break;
                }
            }
        }

    }

    //  Create User
    private static void createUser() {
        String password, userName, emailId;
        String[] getMessages = {"Enter User Name", "Enter Email Address", "Enter Password"};
        System.out.println(getMessages[0]);
        userName = scanner.next();
        System.out.println(getMessages[1]);
        emailId = scanner.next();
        System.out.println(getMessages[2]);
        password = scanner.next();
        String previousHash = SHA256.getSHA("00000000000000000000000000");
        if (blockChain.size() != 0) previousHash = blockChain.get(blockChain.size() - 1).getBlockHash();
        String[] transaction = {"Admin", "500", userName};
        blockChain.add(new Block(previousHash, transaction));
        ArrayList<Block> arrayList = new ArrayList<>(blockChain);
        User user = new User(userName, password, emailId, 500, arrayList);
        userArrayList.add(user);
        System.out.println(user.getAccountNumber());
    }

    @SuppressWarnings("Duplicates")
    //  View Account Information
    private static void viewAccountInfo() {
        if (userArrayList.size() == 0) return;
        passwordCounter = 0;
        String userAccountNumber, password;
        String[] messages = {"Enter Account Number", "Enter Password"}, errorMessage = {"Account Not Exist", "Wrong Password \nTry Again", "Try Again Later\n"};
        System.out.println(messages[0]);
        userAccountNumber = scanner.next();
        check = verifyUser(userAccountNumber);
        if (!check) {
            System.out.println(errorMessage[0]);
            return;
        }
        System.out.println(messages[1]);
        while (passwordCounter < 3) {
            password = scanner.next();
            check = verifyUserPassword(userAccountNumber, password);
            passwordCounter++;
            if (!check)
                System.out.println(errorMessage[1]);
            else
                break;
        }
        if (!check) {
            System.out.println(errorMessage[2]);
            return;
        }

        System.out.println((userArrayList.get(getUser(userAccountNumber))).toString());
    }

    //  Show All
    private static void showAll() {
        for (User user : userArrayList) {
            System.out.println(user.toString());
        }
    }

    //get user using account number
    private static int getUser(String accountNumber) {
        for (int i = 0; i < userArrayList.size(); i++) {
            if (userArrayList.get(i).getAccountNumber().equals(accountNumber)) {
                return i;
            }
        }
        return 0;
    }

    @SuppressWarnings("Duplicates")
    //  Verify user Blocks and Call Transfer Money
    private static void transfer() {
        if (userArrayList.size() == 0) return;
        passwordCounter = 0;

        // User Inputs
        String sender, receiver, amount, password;
        //  Error and Prompt Messages
        String[] transferMessage = {
                "Enter Your Account Number:",               //0
                "Enter Password:",                          //1
                "Enter The Receiver's Account Number:",     //2
                "Enter The Amount Your Wish To Send:",      //3
                "Transfer Completed Successfully!!!"        //4
        }, errorMessage = {
                "\nAccount Doesn't Exist.\n",                //0
                "\nWrong Password.\nTry Again\n",            //1
                "\nUser Not Found.\n",                       //2
                "\nCould Not Transfer Amount Right Now.\n",  //3
                "\nTry Again Later!\n",                      //4
                "\nNot Enough Balance\n"                     //5
        };
        //  Sender
        System.out.println(transferMessage[0]);
        sender = scanner.next();
        check = verifyUser(sender);
        if (!check) {
            System.out.println(errorMessage[0]);
            return;
        }
        //  password
        System.out.println(transferMessage[1]);
        while (passwordCounter < 3) {
            password = scanner.next();
            check = verifyUserPassword(sender, password);
            passwordCounter++;
            if (!check)
                System.out.println(errorMessage[1]);
            else
                break;
        }
        if (!check) {
            System.out.println(errorMessage[4]);
            return;
        }
        //  Receiver
        System.out.println(transferMessage[2]);
        receiver = scanner.next();
        check = verifyUser(receiver);
        if (!check) {
            System.out.println(errorMessage[2]);
            return;
        }
        //  Amount
        System.out.println(transferMessage[3]);
        amount = scanner.next();
        check = verifyUser(sender, amount);
        if (!check) {
            System.out.println(errorMessage[5]);
            return;
        }
        // BitCoin Transfer
        check = transferMoney(sender, receiver, amount);
        if (!check) {
            System.out.println(errorMessage[3]);
            return;
        }
        System.out.println(transferMessage[4]);
    }

    //  Transfer Money
    private static boolean transferMoney(String sender, String receiver, String amount) {
        String[] transaction = {"" + sender, "" + amount, "" + receiver};
        if (!blockChain.isEmpty() && !verifyTransaction(transaction)) return false;
        Block block = new Block(blockChain.get(blockChain.size() - 1).getBlockHash(), transaction);
        blockChain.add(block);

        int s = getUser(sender);
        int r = getUser(receiver);

        userArrayList.get(s).sub(amount);
        userArrayList.get(r).add(amount);
        for (User user : userArrayList) {
            user.addBlock(block);
        }
        System.out.println("s : " + userArrayList.get(s).getAccountBalance() + "\nr : " + userArrayList.get(r).getAccountBalance());

        return true;
    }

    //  Verify Transaction
    private static boolean verifyTransaction(String[] transaction) {
        int amount = getTotal(transaction[0]);
        if (amount < Integer.parseInt(transaction[1])) return false;
        for (User user : userArrayList)
            if (amount != user.getTotal(transaction[0]))
                return false;
        return true;
    }

    @SuppressWarnings("Duplicates")
    private static int getTotal(String sender) {
        int total = 0;
        for (Block block : blockChain) {
            if (sender.equals(block.getReciever())) {
                total = total + Integer.parseInt(block.getAmount());
            }
        }
        return 0;
    }

    // Verify User Password
    private static boolean verifyUserPassword(String user, String password) {
        return userArrayList.get(getUser(user)).getPassword().equals(password);
    }

    //  Verify User
    private static boolean verifyUser(String user) {
        return userArrayList.contains(userArrayList.get(getUser(user)));
    }

    // Verify User Account amount
    private static boolean verifyUser(String user, String amount) {
        return userArrayList.get(getUser(user)).getAccountBalance() > Integer.parseInt(amount);
    }

    private static void addUsers() {
        String[] a = {"admin", "500", "1"};
        blockChain.add(new Block(SHA256.getSHA("00000000000000"), a));
        User u1 = new User("1", "1", "1", 500, new ArrayList<>(blockChain));

        String[] b = {"admin", "500", "2"};
        blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getBlockHash(), b));
        User u2 = new User("2", "2", "2", 500, new ArrayList<>(blockChain));

        userArrayList.add(u1);
        userArrayList.add(u2);

        System.out.println(u1.getAccountNumber());
        System.out.println(u2.getAccountNumber());
    }

    private static void printBlockchain() {
        for (Block b : blockChain) {
            System.out.println(b.toString());
        }
    }
}