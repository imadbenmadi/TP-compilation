import javax.swing.*;

public class IterationFrame extends JFrame {
    private JTextArea iterationTextArea;

    public IterationFrame() {
        initComponents();
    }

    private void initComponents() {
        iterationTextArea = new JTextArea();
        iterationTextArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(iterationTextArea);
        add(jScrollPane);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void appendIteration(String text) {
        iterationTextArea.append(text + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IterationFrame iterationFrame = new IterationFrame();
            iterationFrame.setVisible(true);
        });
    }
}
