import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.awt.Font;

public class NewJFrame extends javax.swing.JFrame {
    private IterationFrame iterationFrame;
    public NewJFrame() {
        initComponents();
        Font customFont = new Font("Arial", Font.PLAIN, 16);
        input.setFont(customFont);
        output.setFont(customFont);
        setSize(1280, 700);
        jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    ActionEvent actionEvent = new ActionEvent(input, ActionEvent.ACTION_PERFORMED, "Enter");
                    jButton1ActionPerformed(actionEvent);
                }
            }
        });
        iterationFrame = new IterationFrame();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        showIterations();
    }
    private void showIterations() {
        IterationFrame iterationFrame = new IterationFrame();  // Initialize IterationFrame
        iterationFrame.setVisible(true);

        int rows = 2; // Number of products
        int columns = 2; // Number of transactions
        String input_text = input.getText();

        // Declare the transaction matrix
        String[][] transactionMatrix = new String[rows][columns];

        transactionMatrix[0][0] = "S0";
        transactionMatrix[0][1] = "S1";
        transactionMatrix[1][0] = "S1";
        transactionMatrix[1][1] = "S0";

        int current_state = 0; // Initialize the current state

        int i = 0;

        while (current_state < rows && i <= input_text.length()) {
            char tc = i == input_text.length() ? '#' : input_text.charAt(i);
            char current_char = (char) (tc == 'a' ? 1 : 0);

            // Append the iteration information to the IterationFrame
            String iterationInfo = String.format("Iteration %d) Ec = %s | tc = %c | String = %s",
                    i, transactionMatrix[current_state][0], tc, input_text.substring(i));
            iterationFrame.appendIteration(iterationInfo);

            // Move to the next state
            String next_state = transactionMatrix[current_state][current_char];
            current_state = Integer.parseInt(next_state.substring(1)); // Assuming state names are like "S0", "S1", etc.

            // Move to the next character in the input string
            i++;
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        output.setText("");
        String input_text = input.getText();
        if (input_text.isEmpty()) {
            output.setText("Error: Input is empty");
            return;
        }
        if(input_text.equals("(") || input_text.equals(")") || input_text.equals(",") || input_text.equals(";") ||
                input_text.equals(".") ){
            output.setText("True : Separatore");
            return;
        }


        int rows = 8;
        int columns = 8;
        String[][] transactionMatrix = new String[rows][columns];
        // [State][Character]
        // State: 0=> S0 , 1=> S1
        // Character:
        // 1=> "( ) , ; . "
        // 2=> ": "
        // 3=> "+ / - ="
        // 4=>"1-9"
        // 5=>"a-z"
        transactionMatrix[0][1] = "S1";
        transactionMatrix[0][2] = "S2";
        transactionMatrix[0][3] = "S3";
        transactionMatrix[0][4] = "S4";
        transactionMatrix[0][5] = "S5";


        // Character:
        // 3=>" = "
        transactionMatrix[2][3] = "S3";

        transactionMatrix[4][4] = "S4"; // Transition for digits (1-9)


        transactionMatrix[5][5] = "S5";
        transactionMatrix[5][6] = "S6"; // Transition for digits after letters
        transactionMatrix[5][4] = "S5"; // Transition for underscore after letters

        transactionMatrix[6][5] = "S5"; // Transition for digits after letters
        transactionMatrix[6][4] = "S5"; // Transition for digits after letters


        int current_state = 0; // Initialize the current state
        int i = 0;
        int current_char;
        while (current_state < rows && i < input_text.length()) {
            char tc = input_text.charAt(i);
            if (tc == ':')
                current_char = 2;
            else if (tc == '+' || tc == '=' || tc == '/' || tc == '-')
                current_char = 3;
            else if (Character.isDigit(tc))
                current_char = 4;
            else if (Character.isLowerCase(tc) || Character.isUpperCase(tc))
                current_char = 5 ;
            else if (tc == '_')
                current_char = 6;
            else {
                output.setText("Error: Symbol '" + tc + "' does not belong to the Language");
                return;
            }

            String next_state = transactionMatrix[current_state][current_char];
            if (next_state == null) {
                output.setText("Error: No transition defined for state " + current_state + " and character '" + tc + "'");
                return;
            }

            current_state = Integer.parseInt(next_state.substring(1));
            i++;
        }

        // Les Etets Finall
        if (current_state == 2) {
            output.setText("True : Separatore");
            jButton2.setEnabled(true);}
        else if (current_state == 3) {
            output.setText("True : Operatore");
            jButton2.setEnabled(true);
        }
        else if (current_state == 4) {
            output.setText("True : Constant");
            jButton2.setEnabled(true);
        }
        else if (current_state == 5) {
            output.setText("True : Identifier");
            jButton2.setEnabled(true);
        }
        else if (current_state == 6) {
            output.setText("flase : Identifiers Cannot Ends With _");
            jButton2.setEnabled(false);
        }
         else {
            output.setText("False");
            jButton2.setEnabled(false);
        }
    }
    private void jButton1ActionPerformedAB(java.awt.event.ActionEvent evt) {
        output.setText("");
        String input_text = input.getText();

        int rows = 2;
        int columns = 2;
        String[][] transactionMatrix = new String[rows][columns];
        // [State][Character]
        // State: 0=> S0 , 1=> S1
        // Character: 0=> a , 1=> b
        transactionMatrix[0][0] = "S1";
        transactionMatrix[0][1] = "S0";
        transactionMatrix[1][0] = "S1";
        transactionMatrix[1][1] = "S0";

        int current_state = 0; // Initialize the current state
        int i = 0;
        int current_char;
        while (current_state < rows && i < input_text.length()) {
            char tc = input_text.charAt(i);
            if (tc == 'a')
                current_char = 0;
            else if (tc == 'b')
                current_char = 1;
            else {
                output.setText("Error, Symbols do not belong to the Language");
                return;
            }
            String next_state = transactionMatrix[current_state][current_char];
            current_state = Integer.parseInt(next_state.substring(1)); // Assuming state names are like "S0", "S1", etc.
            i++;
        }

        if (current_state != 1) { // L'etat Final est S1
            output.setText("String does not belong to the Language");
            jButton2.setEnabled(false);
        } else {
            output.setText("True");
            jButton2.setEnabled(true);
        }
    }






    private void jButton1ActionPerformedSeparation(java.awt.event.ActionEvent evt) {
        output.setText("");
        String input_text = input.getText();

        boolean insideComment = false;
        // Remove Comments
        for (char c : input_text.toCharArray()) {
            if (c == '%') {
                insideComment = !insideComment;
            } else if (!insideComment) {
                output.append(String.valueOf(c));
            }
        }
        if (insideComment){
            output.setText("error: Close the comments please !");
            return;
        }
        //make the output without comments in variable outputText
        // ( ) = ; [ ] + - / * :
        String outputText = output.getText();
        //the result Container
        StringBuilder result = new StringBuilder();
        String input = outputText.toString();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '(' && c!=')' && c != '[' && c!=']' && c!= '=' && c!=';' && c!=':'&& c!='.'&& c!='-' &&
                c != '/' && c!='*' && c!=','
            ) {

                if (c == '+') {
                    if (input.charAt(i - 1) != '+'){
                        result.append('#');
                    }
                    result.append('+');
                    if (i + 1 < input.length() && input.charAt(i + 1) == '+'  ) {
                        if(i + 2 < input.length() &&  input.charAt(i + 2) == '+'){
                            result.append('+');
                            result.append('#');
                        }
                        else{
                            result.append('#');
                            result.append('+');
                        }
                        i++;
                    }else {
                        result.append('#');
                    }
                } else  if (c == '<' ||  c=='>') {
                    if (input.charAt(i - 1) != '#') {
                        result.append('#');
                    }
                    result.append(c);
                    if (i + 1 < input.length() && input.charAt(i + 1) == '=' ) {
                        result.append("=");
                        result.append('#');
                        i++;
                    } else {
                        result.append('#') ;
                    }

                }else {
                    if (c != ' ') {
                        result.append(c);
                    }
                }
            }else {
                if (c != ' ') {
                    // the number
                    if (i+1<input.length() && Character.isDigit(input.charAt(i-1)) && Character.isDigit(input.charAt(i+1)) && c=='.' ) {
                        result.append(c);
                    }
                    else{
                    result.append("#");
                    result.append(c);
                    if (i + 1 < input.length() && input.charAt(i + 1) != c) {
                        result.append("#");
                    }
                    }
                } else {
                    if (i + 1 < input.length() && input.charAt(i + 1) != c && !(c == '.' && Character.isDigit(input.charAt(i - 1)))) {
                        result.append("#");
                    } else {
                        // handle other cases if needed
                    }
                }
            }
        }
        output.setText(result.toString());
    }




    private void openTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                StringBuilder fileContents = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    fileContents.append(line).append("\n");
                }

                bufferedReader.close();

                // Display the contents of the text file (you can modify this part)
                //JOptionPane.showMessageDialog(this, "File Contents:\n" + fileContents.toString());
                input.setText(String.valueOf(fileContents));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening the file: " + ex.getMessage());
            }
        }
    }
    private void saveTextFile() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                // Get the selected file
                File file = fileChooser.getSelectedFile();

                // Ensure the file has a .txt extension
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.toString() + ".txt");
                }

                // Get the contents of the output text area
                String content = output.getText();

                // Write the contents to the selected file
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(content);
                }

                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving the file: " + ex.getMessage());
            }
        }
    }
    private void removeTextFile() {
        output.setText("");
        input.setText("");
    }
    private void exitProgram() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0); // Exit the program
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Open", "Save", "Remove", "Exit"}));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 255, 21));
        jButton1.setText("Analyse");
        jButton1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Set font and size
        jButton1.setForeground(new java.awt.Color(255, 255, 255)); // Set text color
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        //JButton jButton2 = new JButton();
        jButton2.setBackground(new java.awt.Color(255, 51, 51)); // Change the color
        jButton2.setText("Show Iterations");
        jButton2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        input.setColumns(20);
        input.setRows(5);
        jScrollPane1.setViewportView(input);
        input.setLineWrap(true);

        output.setColumns(20);
        output.setRows(5);
        jScrollPane2.setViewportView(output);
        output.setLineWrap(true);
        output.setEditable(false);

        jLabel1.setText("INPUT");
        jLabel2.setText("OUTPUT");

        // Add jButton2 to the layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(25, 25, 25))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(164, 164, 164))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE) // Adjust size as needed
                                                .addGap(25, 25, 25))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(352, 352, 352)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                //.addGap(10, 10, 10)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
    }




    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;

    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea output;
    // End of variables declaration//GEN-END:variables
}
