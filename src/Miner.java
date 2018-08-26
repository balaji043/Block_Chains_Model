import java.util.ArrayList;

class Miner {
    private static ArrayList<Block> blockChains = new ArrayList<>();
    private static String[] transaction;
    public boolean results = false;
    Miner(ArrayList<Block> blockChains,String[] transaction){
        Miner.blockChains = blockChains;
        Miner.transaction = transaction;
        results = mine();
    }

    private boolean mine() {
        int balance =0;
        for (Block blockChain : blockChains) {
            if (blockChain.getReceivers().equals(transaction[1])) {
                balance = balance+blockChain.getAmount();
            }
        }
        return balance >= Integer.parseInt(transaction[1]);
    }
}
