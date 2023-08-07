import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;

public class PlagiarismChekerGUI extends JFrame{

    private JList<String> fileList;
    private JButton selectFilesButton;
    private JButton loadParagraphButton;
    private JButton detectPlagiarismButton;
    private DefaultListModel<String> listModel;
    private JTextArea paragraphTextArea;
    private JTextArea resultTextArea;
    private PlagiarismCheker plagiarismCheker;

    public PlagiarismChekerGUI() {

        plagiarismCheker = new PlagiarismCheker();
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JScrollPane fileListScrollPane = new JScrollPane(fileList);

        selectFilesButton = new JButton("Cargar archivos (base de datos)");
        
        selectFilesButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                //falta
            }
        });
        

        loadParagraphButton = new JButton("Cargar texto para verificar plagio");
        loadParagraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                //falta
            }
        });

        detectPlagiarismButton = new JButton("Detectar plagio");
        detectPlagiarismButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //falta
            }
        });

        paragraphTextArea = new JTextArea(10, 30);
        JScrollPane paragraphScrollPane = new JScrollPane(paragraphTextArea);

        resultTextArea = new JTextArea(10, 30);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileListScrollPane, BorderLayout.CENTER);
        filePanel.add(selectFilesButton, BorderLayout.SOUTH);

        JPanel paragraphPanel = new JPanel(new BorderLayout());
        paragraphPanel.add(paragraphScrollPane, BorderLayout.CENTER);
        paragraphPanel.add(loadParagraphButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detectPlagiarismButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.add(filePanel);
        mainPanel.add(paragraphPanel);
        mainPanel.add(resultPanel);

        getContentPane().add(mainPanel);

        setTitle("Detector de Plagio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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