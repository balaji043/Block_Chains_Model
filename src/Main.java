import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int breaker = 1,passwordCounter;
    private static boolean check;

    private static TreeMap<String, User> userHashMap = new TreeMap<>();
    private static ArrayList<Block> blockChain = new ArrayList<>();

    public static void main(String args[]) {
        String errorMessageOption = "Enter Correct option",
                exitingMessage = "Exiting Menu",
                menu[] = {"Create User-1", "Make Transfer-2","View Account Info-3","Exit Menu-4","Enter Your Option"};
        while (breaker == 1) {
            System.out.printf("%20s\n%20s\n%20s\n%20s\n\n%20s",menu[0],menu[1],menu[2],menu[3],menu[4]);
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
                case 3:{
                    viewAccountInfo();//View Account Details
                    break;
                }
                case 4: {
                    System.out.println(exitingMessage);
                    breaker = 0;
                    break;
                }
                default: {
                    System.out.println(errorMessageOption);
                    break;
                }
            }
        }
    }

    private static void viewAccountInfo() {
        passwordCounter = 0;
        String userAccountNumber,password;
        String messages[] = {"Enter Account Number","Enter Password"};
        String errorMessage[] = {"Account Not Exist","Wrong Password \nTry Again","Try Again Later\n"};
        String details[] = {"Name : ","Number : ","E-MailID : ","Balance : ","Password : "};
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
        User user = userHashMap.get(userAccountNumber);
        System.out.printf("%20s%s%n",  "Welcome ",user.getUserName());
        System.out.printf("%20s%s%n", details[0], user.getUserName());
        System.out.printf("%20s%s%n", details[1], user.getAccountNumber());
        System.out.printf("%20s%s%n", details[2], user.getEmailId());
        System.out.printf("%20s%s%n", details[3], user.getAccountBalance());
        System.out.printf("%20s%s%n", details[4], user.getPassword());
    }

    //  Create User
    private static void createUser() {
        String userName="", password="", emailId="";
        String[] getMessages = {"Enter User Name", "Enter Email Address", "Enter Password"};
        System.out.println(getMessages[0]);
        userName = scanner.next();
        System.out.println(getMessages[1]);
        emailId = scanner.next();
        System.out.println(getMessages[2]);
        password = scanner.next();
        User user = new User(userName, password, emailId);
        String accountNumber = user.getAccountNumber();
        userHashMap.put(accountNumber, user);
        System.out.printf("%s %s %s %d\n",userName,password,emailId,userHashMap.size());
        System.out.printf("%10s %s\n","Account\n",accountNumber);
    }

    private static void transfer() {
        passwordCounter = 0;
        // User Inputs

        String sender, receiver, amount, password;

        //  Error and Prompt Messages

        String transferMessage[] = {
                "Enter Your Account Number:",               //0
                "Enter Password:",                          //1
                "Enter The Receiver's Account Number:",     //2
                "Enter The Amount Your Wish To Send:",      //3
                "Transfer Completed Successfully!!!"        //4
        };
        String errorMessage[] = {
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

    private static boolean transferMoney(String sender, String receiver, String amount) {
        String transaction[]  = {""+sender,""+amount,""+receiver};
        if(!verifyTransaction(transaction))return false;
        int previousHashCode = 0;
        if (!blockChain.isEmpty()) previousHashCode=(blockChain.get(blockChain.size()-1)).getBlockHash();
        Block block = new Block(previousHashCode,transaction);
        blockChain.add(block);
        User s = userHashMap.get(sender);
        User r = userHashMap.get(receiver);
        s.setAccountBalance(s.getAccountBalance()-Integer.parseInt(amount));
        r.setAccountBalance(r.getAccountBalance()+Integer.parseInt(amount));
        return true;
    }

    private static boolean verifyTransaction(String[] transaction) {
        Miner miner  = new Miner(blockChain,transaction);
        return !miner.results;
    }

    private static boolean verifyUserPassword(String sender, String password) {
        return (userHashMap.get(sender)).getPassword().equals(password);
    }

    private static boolean verifyUser(String sender) {
        return userHashMap.containsKey(sender);
    }

    private static boolean verifyUser(String sender, String amount) {
        return (userHashMap.get(sender)).getAccountBalance()>Integer.parseInt(amount);
    }
}
