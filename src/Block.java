import java.util.Arrays;

class Block {
    private String previousHash;
    private String blockHash;
    private String[] transactions;

    Block(String previousHash, String[] transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.blockHash = SHA256.getSHA(previousHash + Arrays.toString(transactions));
    }

    String getBlockHash() {
        return blockHash;
    }

    String getPreviousHash() {
        return previousHash;
    }

    String getSender() {
        return transactions[0];
    }

    String getAmount() {
        return transactions[1];
    }

    String getReciever() {
        return transactions[2];
    }

    @Override
    public String toString() {
        return "Block{" +
                "previousHash='" + previousHash + '\'' +
                ", blockHash='" + blockHash + '\'' +
                ", transactions=" + Arrays.toString(transactions) +
                '}';
    }
}
