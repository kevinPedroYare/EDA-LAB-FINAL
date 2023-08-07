import java.util.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class PlagiarismCheker {
    private Trie databaseTrie;
    private double detectionErrorRate = 0.2;

    /*
     * Carga los archivos de la base de datos en la estructura Trie.
     *
     * @param paths: Rutas de los archivos que forman la base de datos.
     * @return: true si la carga se realizó correctamente.
     */
    public boolean loadFiles(String[] paths) {
        databaseTrie = new Trie();
        for (String filePath : paths) {
            String content = readFile(filePath);
            if (!content.isEmpty()) {
                databaseTrie.insert(content);
            }
        }
        return true;
    }

    /*
     * Lee el contenido de un archivo y lo devuelve como un String.
     *
     * @param filePath: Ruta del archivo a leer.
     * @return: Contenido del archivo como un String.
     */
    private String readFile(String filePath) {
        try {
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Si hay un error al leer el archivo, devuelve una cadena vacía
        }
    }

    /*
     * Verifica si hay plagio en el texto proporcionado comparándolo con la base de datos.
     *
     * @param path: Ruta del archivo que contiene el texto a verificar.
     * @return: Resultados del sistema de detección de plagio.
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

            // Evaluar si el texto es plagio o no, teniendo en cuenta el error de detección
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
