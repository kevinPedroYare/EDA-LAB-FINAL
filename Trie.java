public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.getChildren().putIfAbsent(c, new TrieNode());
            current = current.getChildren().get(c);
        }
        current.setEndOfWord(true);
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.getChildren().get(c);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord();
    }

    public int size(){
        return size(root);
    }

    private int size(TrieNode node){
        int count = 0;
        if(node.isEndOfWord()){
            count++;
        }
        for (TrieNode child : node.getChildren().values()) {
            count += size(child);
        }
        return count;
    }
}