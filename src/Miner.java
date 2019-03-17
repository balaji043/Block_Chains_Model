import java.util.ArrayList;

class Miner {
    private ArrayList<Block> blockChains;
    private String[] transaction;

    Miner(ArrayList<Block> blockChains, String[] transaction) {
        this.blockChains = blockChains;
        this.transaction = transaction;
    }

    boolean mine() {
        int balance = 0;
        for (Block blockChain : blockChains) {
/*
            if (blockChain.getReceivers().equals(transaction[1])) {
                balance = balance + blockChain.getAmount();
            }
*/
        }
        return true;
    }

    boolean min() {
        for (Block block : blockChains) {
        }
        return true;
    }
}