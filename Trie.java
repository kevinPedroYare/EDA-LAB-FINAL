public class Trie {
    private TrieNode root; // Nodo raíz del Trie

    public Trie() {
        root = new TrieNode(); // Inicializa el Trie con un nuevo nodo raíz vacío
    }

    public void insert(String word) {
        TrieNode current = root; // Comenzamos desde el nodo raíz
        for (char c : word.toCharArray()) {
            current.getChildren().putIfAbsent(c, new TrieNode()); // Agrega un nuevo nodo hijo si no existe
            current = current.getChildren().get(c); // Avanza al siguiente nodo hijo
        }
        current.setEndOfWord(true); // Marca el último nodo como el final de una palabra
    }

    public boolean search(String word) {
        TrieNode current = root; // Comenzamos desde el nodo raíz
        for (char c : word.toCharArray()) {
            current = current.getChildren().get(c); // Avanza al siguiente nodo hijo
            if (current == null) {
                return false; // Si no se encuentra un nodo hijo para el carácter, la palabra no existe en el Trie
            }
        }
        return current.isEndOfWord(); // Verifica si el último nodo representa el final de una palabra
    }

    public int size() {
        return size(root); // Devuelve el tamaño del Trie contando desde la raíz
    }

    private int size(TrieNode node) {
        int count = 0;
        if (node.isEndOfWord()) {
            count++;
        }
        for (TrieNode child : node.getChildren().values()) {
            count += size(child); // Suma el tamaño de cada subárbol recursivamente
        }
        return count; // Devuelve el tamaño total del Trie
    }
}
