import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children; // Mapa que mapea caracteres a nodos hijos
    private boolean isEndOfWord; // Indica si este nodo representa el final de una palabra

    public TrieNode() {
        children = new HashMap<>(); // Inicializa el mapa de hijos
        isEndOfWord = false; // Inicializa isEndOfWord como falso por defecto
    }

    public Map<Character, TrieNode> getChildren() {
        return children; // Obtiene el mapa de hijos del nodo actual
    }

    public boolean isEndOfWord() {
        return isEndOfWord; // Verifica si este nodo representa el final de una palabra
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord; // Establece si este nodo representa el final de una palabra
    }
}