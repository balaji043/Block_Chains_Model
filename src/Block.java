import java.util.Arrays;

public class Block {
    private static int previousHash;
    private static int blockHash;

    private static String[] transactions;

    Block(int previousHash, String[] transactions) {
        Block.previousHash = previousHash;
        Block.transactions = transactions;
        Object[] objects = {Arrays.hashCode(transactions), previousHash};
        Block.blockHash = Arrays.hashCode(objects);
    }

    public String getReceivers() {
        return transactions[2];
    }

    public int getAmount() {
        return Integer.parseInt(transactions[1]);
    }

    public String[] getTransactions() {
        return transactions;
    }

    public void setTransactions(String[] transactions) {
        Block.transactions = transactions;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(int previousHash) {
        Block.previousHash = previousHash;
    }

    public int getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(int blockHash) {
        Block.blockHash = blockHash;
    }

}
