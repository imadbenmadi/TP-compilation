import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewJFrame extends javax.swing.JFrame {
    private IterationFrame iterationFrame;
    public NewJFrame() {
        initComponents();
        Font customFont = new Font("Arial", Font.PLAIN, 16);
        input.setFont(customFont);
        output.setFont(customFont);
        setSize(1280, 700);
        jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        input_text = input_text.replaceAll("\\n", "\\\\n");
        // Declare the transaction matrix
        String[][] transactionMatrix = new String[rows][columns];

        transactionMatrix[0][0] = "S0";
        transactionMatrix[0][1] = "S1";
        transactionMatrix[1][0] = "S1";
        transactionMatrix[1][1] = "S0";

        int current_state = 0; // Initialize the current state

        int i = 0;

        while (current_state < rows && i < input_text.length()) {
            char tc = input_text.charAt(i);
            char current_char = (char) (tc == 'a' ? 1 : 0);

            // Append the iteration information to the IterationFrame
            String iterationInfo = String.format("Iteration %d) Ec = %s | tc = %c | String = %s",
                    i, transactionMatrix[current_state][current_char], tc, input_text.substring(i));
            iterationFrame.appendIteration(iterationInfo);

            // Move to the next state
            String next_state = transactionMatrix[current_state][current_char];
            current_state = Integer.parseInt(next_state.substring(1)); // Assuming state names are like "S0", "S1", etc.

            i++;
        }

        // Append the final iteration when the end of the string is reached
        if (i == input_text.length()) {
            String iterationInfo = String.format("Iteration %d) Ec = %s | tc = # | String = ", i, transactionMatrix[current_state][0]);
            iterationFrame.appendIteration(iterationInfo);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        output.setText("");
        String input_text = input.getText();
        if (input_text.isEmpty()) {
            output.setText("Error: Input is empty");
            return;
        }
        List<String> tokens = separateStrings(input_text);

        if (tokens == null) {
            return;
        }

        int rows = 10;
        int columns = 10;
        String[][] transactionMatrix = new String[rows][columns];

        // [State][Character]
        // State: 0=> S0 , 1=> S1
        // Character:
        // 1=> "( ) , ; . "
        // 2=> ": "
        // 3=> "+ / - ="
        // 4=>"1-9"
        // 5=>"a-z"
        // 6=>" _ "
        // 7=>" . "
        transactionMatrix[0][1] = "S1";
        transactionMatrix[0][2] = "S2";
        transactionMatrix[0][3] = "S3";
        transactionMatrix[0][4] = "S4";
        transactionMatrix[0][5] = "S5";
        // Character:
        // 3=>" = "
        transactionMatrix[2][3] = "S3";

        transactionMatrix[4][4] = "S4";
        transactionMatrix[4][7] = "S7";
        transactionMatrix[7][4] = "S4";

        transactionMatrix[5][5] = "S5";
        transactionMatrix[5][6] = "S6";
        transactionMatrix[5][4] = "S5";

        transactionMatrix[6][5] = "S5";
        transactionMatrix[6][4] = "S5";
        transactionMatrix[6][6] = "S6";
        /*
        System.out.println("Transaction Matrix:");
        System.out.print("State/Char\t");
        for (int j = 0; j < columns; j++) {
            System.out.printf("%-5d", j);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("S%-2d\t\t", i);
            for (int j = 0; j < columns; j++) {
                System.out.printf("%-5s", transactionMatrix[i][j]);
            }
            System.out.println();
        }*/

        output.setText("Debugging Succesfully: All Entities Are True \n");
        output.append("--------------------------------------------------------\n");
        int current_state;
        int current_char;
        int pair_tirer;
        int Points_Counter;

        for (String token : tokens) {
            current_state = 0;
            int i = 0;
            pair_tirer = 0;
            Points_Counter = 0;
            for (char tc : token.toCharArray()) {
                if (tc == '(' || tc == ')' || tc == ',' || tc == ';'|| (tc == '.' && current_state == 0 )  )
                    current_char = 1;

                else if (tc == ':')
                    current_char = 2;
                else if (tc == '+' || tc == '=' || tc == '/' || tc == '-')
                    current_char = 3;
                else if (Character.isDigit(tc))
                    current_char = 4;
                else if (Character.isLowerCase(tc))
                    current_char = 5 ;
                else if (tc == '_'){
                    pair_tirer++;
                    current_char = 6;}
                else if (current_state == 4 && tc == '.' ){
                    Points_Counter++;
                    current_char = 7;
                }
                else {
                    output.setText("Error: Symbol '" + tc + "' does not belong to the Language in token '" + token + "'");
                    return;
                }

                String next_state = transactionMatrix[current_state][current_char];
                if (next_state == null) {
                    output.setText("Error: token :'" + token + "'\nNo transition defined for state '" + current_state +
                            "' and character '" + tc +  "'");
                    return;
                }

                current_state = Integer.parseInt(next_state.substring(1));
                i++;
            }


            // Les Etets Finall
            if (current_state == 1) {
                output.append("'"+ token +"'" +" : Terminal  \n");
            }
            else if (current_state == 2) {
                output.append("'" + token +"'" + " : Separatore \n");
            } else if (current_state == 3) {
                output.append("'" + token +"'" + " : Operatore\n");
            } else if (current_state == 4) {
                if(Points_Counter == 0){
                    if (token.length() > 7) {
                        output.setText("False : Constant Length Must Be Less than 7 Numbers  \n'" + token + "'\n");
                    }
                    else if(Integer.parseInt(token) > 1333634){
                        output.setText("False : Constant Value '" + token + "'\nMust Be Less than 1333634 " +"\n");
                    }
                    else{
                        output.append("'"+token+"'" + " : Constant \n");
                    }
                } else if (Points_Counter == 1) {
                    if (token.length() > 9) {
                        output.setText("False : Constant Length Must Be Less than 9 Numbers  \n'" + token + "'\n");
                    }else{
                        output.append("'" + token + "'"+ " : Constant Réel \n");
                    }
                }
                else if(Points_Counter >1){
                    output.setText("False : Constant Should contain only One Point  \n'" + token + "'\n");
                }

            } else if (current_state == 5) {
                if (token.length() > 6) {
                    output.setText("False : Identifier Length Must Be Less than 6 Characters  \n'" + token + "'\n");
                }
                else if(pair_tirer != 0 && pair_tirer %2 == 0){
                    output.setText("False : Identifier Must not contain Pair _\n'" + token + "'\n");
                }
                else {
                    output.append("'" +token+ "'" + " : Identifier\n");
                }
            } else if (current_state == 6) {
                output.append("False : Identifiers Cannot End With _ \n'" + token + "'\n");
                return;
            } else if (current_state == 7) {
                output.append("False : Constant Cannot Ends With Point \n'" + token + "'\n");
                return;
            }
            else {
                output.setText("Error : '" + token + "' Does not Bellongs To the Language\n");
                return;
            }
        }
    }


    private List<String> separateStrings(String inp) {
        inp = inp.replaceAll("\n", "#");
        String input_text = inp;

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
            output.setText("Error: Close the comments please !");
            return null;
        }
        // ( ) = ; + - / * :
        String outputText = output.getText();
        output.setText("");
        StringBuilder result = new StringBuilder();
        String input = outputText.toString();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '(' && c!=')' && c!= '=' && c!=';'&& c!='.'&& c!='-' &&
                    c != '/' && c!='*' && c!=','&& c!='+'
            ) {
                if (i + 1 < input.length() && c == ':' && input.charAt(i + 1) == '=') {
                    result.append("#:=#");
                    i++;
                } else {
                    result.append(c);
                }
            } else {
                if (c != ' ') {
                    // the number
                    if (i > 0 && i + 1 < input.length() && Character.isDigit(input.charAt(i - 1)) && Character.isDigit(input.charAt(i + 1)) && c == '.') {
                        result.append(c);
                    } else {
                        result.append("#");
                        result.append(c);
                        //if (i + 1 < input.length() && input.charAt(i + 1) != c)
                        result.append("#");
                    }
                } else {
                    if (i + 1 < input.length() && input.charAt(i + 1) != c && !(c == '.' && Character.isDigit(input.charAt(i - 1)))) {
                        result.append("#");
                    } else {
                        result.append("#");
                    }
                }
            }
        }
        String[] tokens = result.toString().split("#");

        // Remove empty strings
        List<String> nonEmptyTokens = Arrays.stream(tokens)
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
        return nonEmptyTokens;
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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

        input.setColumns(25);
        input.setRows(10);
        jScrollPane1.setViewportView(input);
        input.setLineWrap(true);

        output.setColumns(25);
        output.setRows(10);
        jScrollPane2.setViewportView(output);
        output.setLineWrap(true);
        output.setEditable(false);

        jLabel1.setText("INPUT");
        jLabel2.setText("OUTPUT");

     
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
