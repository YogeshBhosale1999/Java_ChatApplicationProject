package chatclient;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame {

    private final ArrayList<String> usersList = new ArrayList();
    private final ArrayList<String> onlineUsersList = new ArrayList();
    private String username;
    private String serverName; //= "localhost";   //serverName indicates Server IP address
    private final int PORT = 1728;
    private boolean isConnected = false;
    private Socket socket;
    private BufferedReader incomingMsg;
    private PrintWriter outgoingMsg;
    private String userFullname;

    public Client(String userFullname) {
        this.userFullname = userFullname;

        initComponents();

        // to display the window at center of the screen  
        this.setLocationRelativeTo(null);
        JTextField_Username.setText(userFullname);
        JTextField_Username.setEditable(false);
        JTextField_ServerName.setText("Mayuresh"); 
        JTextField_ServerName.setForeground(Color.black);
    }//end - Client() parameterized constructor

    public Client() {
        initComponents();

        // to display the window at center of the screen  
        this.setLocationRelativeTo(null);
        JTextField_ServerName.setText("Mayuresh");
        JTextField_ServerName.setForeground(Color.black);
    }//end - Client() default constructor

    //--------------------------//
    public void listenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    } // end - listenThread() method

    //--------------------------//
    public void addUser(String data) {
        usersList.add(data);
    } // end - addUser() method

    //--------------------------//
    public void removeUser(String data) {
        JTextArea_ChatArea.append(data + " is now offline.\n");
    } // end - removeUser() method

    //--------------------------//
    //ignore it ()
    public void writeUsers() {
        String[] tempList = new String[usersList.size()];
        usersList.toArray(tempList);
    } // end - writeUsers() method

    public void displayOnlineUsers() {
        if (onlineUsersList.isEmpty()) {
            JTextArea_OnlineUsersDisplay.append("No users are currently online.\n");
            return;
        }
        JTextArea_OnlineUsersDisplay.setEditable(true);
        JTextArea_OnlineUsersDisplay.setText("");
        onlineUsersList.forEach((String current_user) -> {
            JTextArea_OnlineUsersDisplay.append(current_user + "\n");
        });
        JTextArea_OnlineUsersDisplay.setEditable(false);
    }//end - dsiplayOnlineUsers() method 

    public void clearWholeChatArea() {
        //Clear Chat Area
        JTextArea_ChatArea.setText("");

        // Clear Online Display Area
        JTextArea_OnlineUsersDisplay.setEditable(true);
        JTextArea_OnlineUsersDisplay.setText("");
        JTextArea_OnlineUsersDisplay.setEditable(false);
    }//end - clearWholeChatArea() method

    //--------------------------//
    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            outgoingMsg.println(bye);
            outgoingMsg.flush();
        } catch (Exception e) {
            //JTextArea_ChatArea.append("Could not send Disconnect message.\n");
            JOptionPane.showMessageDialog(null, "Could not send Disconnect message.", "Sending Failed", JOptionPane.CANCEL_OPTION);
        }
    } // end - sendDisconnect() method

    //--------------------------//
    public void disconnect() {
        try {
            JTextArea_ChatArea.append("Disconnected.\n");
            socket.close();
        } catch (IOException ex) {
            //JTextArea_ChatArea.append("Failed to disconnect. \n");
            JOptionPane.showMessageDialog(null, "Failed to disconnect.", "Disconnection Failed", JOptionPane.CANCEL_OPTION);
        }
        isConnected = false;
        JTextField_Username.setEditable(true);
    }  // end - disconnect() method

    //--------------------------//
    private class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String message;
            final String DONE = "Done";
            final String CONNECT = "Connect";
            final String DISCONNECT = "Disconnect";
            final String CHAT = "Chat";

            try {
                while ((message = incomingMsg.readLine()) != null) {
                    data = message.split(":");
                    // data[0] -> username  data[1] -> message  data[2] -> Status(Acknowledge)
                    switch (data[2]) {
                        case CHAT:
                            JTextArea_ChatArea.append(data[0] + ": " + data[1] + "\n");
                            JTextArea_ChatArea.setCaretPosition(JTextArea_ChatArea.getDocument().getLength());
                            break;

                        case CONNECT:
                            JTextArea_ChatArea.removeAll();
                            addUser(data[0]);
                            onlineUsersList.add(data[0]);
                            displayOnlineUsers();
                            break;

                        case DISCONNECT:
                            removeUser(data[0]);
                            onlineUsersList.remove(data[0]);
//                            displayOnlineUsers();
                            break;
//
                        case DONE:
                            //users.setText("");
                            writeUsers();
                            usersList.clear();
                            onlineUsersList.clear();
                            break;
                        default:
                            break;
                    }
                }
            } catch (NullPointerException | IOException ex) {
                System.err.println("Can't connect. Try again!");
            }//end - try catch
        } // end - run() method
    }//end - IncomingReader class

    //--------------------------//
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JLabel_ServerName = new javax.swing.JLabel();
        JTextField_ServerName = new javax.swing.JTextField();
        JLabel_Username = new javax.swing.JLabel();
        JTextField_Username = new javax.swing.JTextField();
        JButton_Connect = new javax.swing.JButton();
        JButton_Disconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTextArea_ChatArea = new javax.swing.JTextArea();
        JTextField_MessageField = new javax.swing.JTextField();
        JButton_Send = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTextArea_OnlineUsersDisplay = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arcane Buzz");
        setName("client"); // NOI18N
        setResizable(false);

        JLabel_ServerName.setText("Server Name / Server IP  Address :");

        JTextField_ServerName.setText("Mayuresh");
        JTextField_ServerName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JTextField_ServerNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                JTextField_ServerNameFocusLost(evt);
            }
        });
        JTextField_ServerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextField_ServerNameActionPerformed(evt);
            }
        });

        JLabel_Username.setText("Username :");

        JTextField_Username.setText("username");
        JTextField_Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextField_UsernameActionPerformed(evt);
            }
        });

        JButton_Connect.setText("Connect");
        JButton_Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_ConnectActionPerformed(evt);
            }
        });

        JButton_Disconnect.setText("Disconnect");
        JButton_Disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_DisconnectActionPerformed(evt);
            }
        });

        JTextArea_ChatArea.setColumns(20);
        JTextArea_ChatArea.setRows(5);
        jScrollPane1.setViewportView(JTextArea_ChatArea);

        JTextField_MessageField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTextField_MessageFieldKeyPressed(evt);
            }
        });

        JButton_Send.setText("SEND");
        JButton_Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_SendActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Online Users");

        JTextArea_OnlineUsersDisplay.setColumns(20);
        JTextArea_OnlineUsersDisplay.setRows(5);
        JTextArea_OnlineUsersDisplay.setBorder(null);
        JTextArea_OnlineUsersDisplay.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(JTextArea_OnlineUsersDisplay);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JTextField_MessageField)
                        .addGap(18, 18, 18)
                        .addComponent(JButton_Send, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JLabel_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JLabel_ServerName)
                        .addGap(18, 18, 18)
                        .addComponent(JTextField_ServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(JButton_Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JButton_Disconnect)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLabel_ServerName)
                    .addComponent(JTextField_ServerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JButton_Connect)
                    .addComponent(JButton_Disconnect))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JLabel_Username)
                            .addComponent(JTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTextField_MessageField)
                    .addComponent(JButton_Send, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTextField_ServerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextField_ServerNameActionPerformed
        String tempServerName = JLabel_ServerName.getText().trim();
        if (tempServerName == null) {
            serverName = "Mayuresh"; //or serverName = "192.168.43.218";
        } else {
            serverName = tempServerName;
        }
    }//GEN-LAST:event_JTextField_ServerNameActionPerformed

    private void JButton_ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_ConnectActionPerformed

        username = JTextField_Username.getText();
        JTextField_Username.setText(userFullname);
        JTextField_Username.setEditable(false);

        serverName = JTextField_ServerName.getText().trim();
        if (serverName == null || serverName.equals("")) {
            serverName = "Mayuresh"; //or serverName = "127.0.0.0"; //or serverName = "localhost";
        }

        try {
            socket = new Socket(serverName, PORT);
            incomingMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outgoingMsg = new PrintWriter(socket.getOutputStream());
            ///-----------------------CHANGED-----------------------///
            outgoingMsg.println(username + ":has JOINED.:Connect");
            outgoingMsg.flush();
            isConnected = true;
            JButton_Connect.setEnabled(false);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Cannot Connect! Try Again.", "Can't Connect", JOptionPane.CANCEL_OPTION);
            JTextField_Username.setEditable(true);
        }//end - try catch block

        listenThread();

    }//GEN-LAST:event_JButton_ConnectActionPerformed

    private void JButton_DisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_DisconnectActionPerformed

        sendDisconnect();
        disconnect();
        clearWholeChatArea();
        JButton_Connect.setEnabled(true);

    }//GEN-LAST:event_JButton_DisconnectActionPerformed

    private void sendTextMessageField() {
        String nothing = "";
        if ((JTextField_MessageField.getText()).equals(nothing)) {
            JTextField_MessageField.setText("");
            JTextField_MessageField.requestFocus();
        } else {
            try {
                outgoingMsg.println(username + " : " + JTextField_MessageField.getText() + ":" + "Chat");
                outgoingMsg.flush(); // flushes the buffer
            } catch (Exception ex) {
                //JTextArea_ChatArea.append("Message was not sent. \n");
                JOptionPane.showMessageDialog(null, "Message was not sent..", "Sending Failed", JOptionPane.CANCEL_OPTION);
            }//end -try catch block
            JTextField_MessageField.setText("");
            JTextField_MessageField.requestFocus();
        }

        JTextField_MessageField.setText("");
        JTextField_MessageField.requestFocus();
    } //end - sendTextMessageField() method


    private void JButton_SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_SendActionPerformed
        sendTextMessageField();
    }//GEN-LAST:event_JButton_SendActionPerformed

    private void JTextField_ServerNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextField_ServerNameFocusGained
        if (JTextField_ServerName.getText().trim().toLowerCase().equals("Mayuresh")) {
            JTextField_ServerName.setText("");
            JTextField_ServerName.setForeground(Color.black);
        }
    }//GEN-LAST:event_JTextField_ServerNameFocusGained

    private void JTextField_ServerNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextField_ServerNameFocusLost
        if (JTextField_ServerName.getText().trim().equals("")
                || JTextField_ServerName.getText().trim().toLowerCase().equals("Mayuresh")) {
            JTextField_ServerName.setText("Mayuresh");
            JTextField_ServerName.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_JTextField_ServerNameFocusLost

    private void JTextField_UsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextField_UsernameActionPerformed

        JTextField_Username.setText(userFullname);

    }//GEN-LAST:event_JTextField_UsernameActionPerformed

    private void JTextField_MessageFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextField_MessageFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendTextMessageField();
        }

    }//GEN-LAST:event_JTextField_MessageFieldKeyPressed

    public static void main(String args[]) {

        //nimbus look & feel setup
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client().setVisible(true);
            }
        });
         */  //                 OR
        //OR following lambda expression can be used for same task     
        //Here lambda expression is used for performance
        java.awt.EventQueue.invokeLater(() -> {
            new Client().setVisible(true);
        });
    }//end - main() method

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_Connect;
    private javax.swing.JButton JButton_Disconnect;
    private javax.swing.JButton JButton_Send;
    private javax.swing.JLabel JLabel_ServerName;
    private javax.swing.JLabel JLabel_Username;
    private javax.swing.JTextArea JTextArea_ChatArea;
    private javax.swing.JTextArea JTextArea_OnlineUsersDisplay;
    private javax.swing.JTextField JTextField_MessageField;
    private javax.swing.JTextField JTextField_ServerName;
    private javax.swing.JTextField JTextField_Username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}//end - Client




//if (evt.getKeyCode() == KeyEvent.VK_ENTER && (evt.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
//    JTextField_MessageField.setText(JTextField_MessageField.getText() + '\n');
//} else