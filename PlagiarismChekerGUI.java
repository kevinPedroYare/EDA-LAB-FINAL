import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultListModel;

public class PlagiarismChekerGUI extends JFrame {

    private JList<String> fileList;
    private JButton selectFilesButton;
    private JButton loadParagraphButton;
    private JButton detectPlagiarismButton;
    private DefaultListModel<String> listModel;
    private JTextArea paragraphTextArea;
    private JTextArea resultTextArea;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private PlagiarismCheker plagiarismCheker;

    public PlagiarismChekerGUI() {
        plagiarismCheker = new PlagiarismCheker(); // Instancia del detector de plagio
        listModel = new DefaultListModel<>(); // Modelo de datos para la lista de archivos
        fileList = new JList<>(listModel); // Lista de archivos que se muestra en la GUI
        JScrollPane fileListScrollPane = new JScrollPane(fileList); // Panel con barra de desplazamiento para la lista

        selectFilesButton = new JButton("Cargar archivos (base de datos)");
        selectFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(PlagiarismChekerGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    String[] filePaths = new String[selectedFiles.length];
                    for (int i = 0; i < selectedFiles.length; i++) {
                        filePaths[i] = selectedFiles[i].getAbsolutePath();
                        listModel.addElement(filePaths[i]); // Agregar la ruta del archivo al DefaultListModel
                    }
                    plagiarismCheker.loadFiles(filePaths); // Cargar los archivos seleccionados en el detector de plagio
                }
            }
        });

        loadParagraphButton = new JButton("Cargar texto para verificar plagio");
        loadParagraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el texto ingresado por el usuario en el JTextArea paragraphTextArea
                String paragraph = paragraphTextArea.getText();
                resultTextArea.setText(paragraph);
            }
        });

        // Crear el modelo de la tabla con tipos de datos específicos para cada columna
        String[] columnNames = {"Archivo", "Plagio"};
        Class<?>[] columnTypes = {String.class, String.class};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };

        detectPlagiarismButton = new JButton("Detectar plagio");
        detectPlagiarismButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plagiarismCheker == null) {
                    JOptionPane.showMessageDialog(PlagiarismChekerGUI.this, "Carga primero los archivos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String paragraph = paragraphTextArea.getText();
                ResultChecker result = plagiarismCheker.verifyPlagiarism(paragraph);

                // Limpiar la tabla antes de mostrar los nuevos resultados
                tableModel.setRowCount(0);

                // Mostrar los resultados en la tabla
                for (int i = 0; i < result.getResult().length; i++) {
                    String fileName = "A" + (i + 1);
                    String plagiarismResult = result.getResult()[i] ? "SÍ" : "NO";
                    Object[] rowData = {fileName, plagiarismResult};
                    tableModel.addRow(rowData);
                }
            }
        });

        // JTextArea para el párrafo a verificar
        paragraphTextArea = new JTextArea(10, 30);
        JScrollPane paragraphScrollPane = new JScrollPane(paragraphTextArea);

        // JTextArea para mostrar los resultados de detección
        resultTextArea = new JTextArea(10, 30);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        // JPanel para la lista de archivos de la base de datos y el botón "Cargar archivos"
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileListScrollPane, BorderLayout.CENTER); // fileListScrollPane contiene la JList con los archivos
        filePanel.add(selectFilesButton, BorderLayout.SOUTH);

        // JPanel para el párrafo a verificar y el botón "Cargar texto"
        JPanel paragraphPanel = new JPanel(new BorderLayout());
        paragraphPanel.add(paragraphScrollPane, BorderLayout.CENTER);
        paragraphPanel.add(loadParagraphButton, BorderLayout.SOUTH);

        // JPanel para mostrar los resultados de detección y el botón "Detectar plagio"
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detectPlagiarismButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Crear la tabla y asignar el modelo
        resultTable = new JTable(tableModel);

        // Crear un JScrollPane para la tabla
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        // Agregar el JScrollPane al resultPanel
        resultPanel.add(tableScrollPane, BorderLayout.CENTER);

        // JPanel principal que contiene los otros paneles dispuestos en un GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.add(filePanel); // Panel con la lista de archivos y el botón "Cargar archivos"
        mainPanel.add(paragraphPanel); // Panel con el área de texto para el párrafo y el botón "Cargar texto"
        mainPanel.add(resultPanel); // Panel con el área de texto para mostrar los resultados y el botón "Detectar plagio"

        // Agregar el JPanel principal al contenido del JFrame
        getContentPane().add(mainPanel);

        setTitle("Detector de Plagio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // NOTA: Aquí no es necesario crear otra instancia de resultTable,
        // ya que ya lo habías hecho previamente. Puedes eliminar esta línea.
        // resultTable = new JTable(tableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PlagiarismChekerGUI gui = new PlagiarismChekerGUI();
                gui.setVisible(true);
            }
        });
    }
}