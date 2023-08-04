import java.util.Random;

public class PlagiarismCheker {
    private Trie databaseTrie;
    private double detectionErrorRate = 0.2;

    /*
     * @param path: Rutas de los archivos que forman la BD.
     * 
     * @return: Booleans informando que no hubo errores con la lectura
     * 
     */
    public boolean loadFiles(String[] path) {
        databaseTrie = new Trie();
        // Llenar las estructuras (recomendado)
        // Lectura del archivo (recomendado)
        for (String filePath : path) {
            databaseTrie.insert(filePath);
        }
        return true;
    }

    /*
     * @param path: Rutas del archivo donde colocaremos el texto con/sin plagio
     * 
     * @return: Resultados del sistema de deteccion de plagio
     */
    public ResultChecker verifyPlagiarism(String path) {
        if (databaseTrie == null) {
            throw new IllegalStateException("La base de datos no ha sido cargada. Invoca loadFiles primero.");
        }

        ResultChecker result = new ResultChecker(databaseTrie.size());

        Random random = new Random();
        for (int i = 0; i < databaseTrie.size(); i++) {
            boolean isPlagiarized = databaseTrie.search(path);
            double randomValue = random.nextDouble();

            if (isPlagiarized && randomValue > detectionErrorRate) {
                result.setResult(i, true);
            } else if (!isPlagiarized && randomValue <= detectionErrorRate) {
                result.setResult(i, true);
            } else {
                result.setResult(i, false);
            }
        }

        return result;
    }
}

class ResultChecker {
    private boolean[] result;

    public ResultChecker(int size) {
        result = new boolean[size];
    }

    public boolean[] getResult() {
        return result;
    }

    public void setResult(int index, boolean value) {
        result[index] = value;
    }
}