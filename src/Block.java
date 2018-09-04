import java.util.Arrays;

class Block {
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
    public int getBlockHash() {
        return blockHash;
    }
}
