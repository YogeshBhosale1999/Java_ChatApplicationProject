package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/*
    inner classes 1. ClientHandler
 */
public class Server extends javax.swing.JFrame {

    private ArrayList clientOutputStreams;
    private ArrayList<String> usersList;
    private final int PORT = 1728;
    
    public class ClientHandler implements Runnable {

        private BufferedReader incomingMsg;
        private Socket socket;
        private PrintWriter outgoingMsg;
        
        public ClientHandler(Socket clientSocket, PrintWriter user) {
            outgoingMsg = user;
            try {
                socket = clientSocket;
                incomingMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                //JTextArea_ChatArea.append("Unexpected error... \n");
                JOptionPane.showMessageDialog(null, "Unexpected Error!", "Error", JOptionPane.CANCEL_OPTION);
            }
        }//end - ClientHandler Parameterized Constructor 

        @Override
        public void run() {
            String message;
            final String CONNECT = "Connect";
            final String DISCONNECT = "Disconnect";
            final String CHAT = "Chat";
            String [] data;

            try {
                while ((message = incomingMsg.readLine()) != null) {
                    JTextArea_ChatArea.append("Received: " + message + "\n");
                    data = message.split(":");
                    //data[0]-> username, data[1] -> user message, data[2] -> status
                    for (String token : data) {
                        JTextArea_ChatArea.append(token + "\n");
                    }

                    switch (data[2]) {
                        case CONNECT:
                            sendToEveryone((data[0] + ":" + data[1] + ":" + CHAT));
                            addUser(data[0]);
                            break;
                        case DISCONNECT:
                            ///------------------CHANGED---------------------///
                            sendToEveryone((data[0] + ":has LEAVED." + ":" + CHAT));
                            removeUser(data[0]);
                            break;
                        case CHAT:
                            sendToEveryone(message);
                            break;
                        default:
//                            JTextArea_ChatArea.append("No Conditions were met. \n");
                            JOptionPane.showMessageDialog(null, "No Conditions were meet.", "Conditions Doesn't Meet", JOptionPane.CANCEL_OPTION);
                            break;
                    }
                }//end -while loop
            } catch (IOException ex) {
                JTextArea_ChatArea.append("Lost a connection. \n");
//                JOptionPane.showMessageDialog(null, "Connection lost due to no users are connected.", "Connection Lost", JOptionPane.CANCEL_OPTION);
//                ex.printStackTrace();
                clientOutputStreams.remove(outgoingMsg);
            }// end - try catch
        }//end - run() method
    }//end - ClientHandler class

    private class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            usersList = new ArrayList();
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//                ServerSocket serverSocket = new ServerSocket(PORT);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSocket, writer));
                    listener.start();
                    JTextArea_ChatArea.append("Got a connection. \n");
                }
            } catch (IOException ex) {
//                JTextArea_ChatArea.append("Error making a connection. \n");
            }
        }
    }//end - ServerStart class

    public Server() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTextArea_ChatArea = new javax.swing.JTextArea();
        JButton_Start = new javax.swing.JButton();
        JButton_End = new javax.swing.JButton();
        JButton_OnlineUsers = new javax.swing.JButton();
        JButton_Clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Server");
        setName("server"); // NOI18N
        setResizable(false);

        JTextArea_ChatArea.setColumns(20);
        JTextArea_ChatArea.setRows(5);
        jScrollPane1.setViewportView(JTextArea_ChatArea);

        JButton_Start.setText("START");
        JButton_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_StartActionPerformed(evt);
            }
        });

        JButton_End.setText("END");
        JButton_End.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_EndActionPerformed(evt);
            }
        });

        JButton_OnlineUsers.setText("Online Users");
        JButton_OnlineUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_OnlineUsersActionPerformed(evt);
            }
        });

        JButton_Clear.setText("Clear");
        JButton_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_ClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JButton_Start, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(JButton_End, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JButton_OnlineUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(JButton_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_Start)
                    .addComponent(JButton_OnlineUsers))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_Clear)
                    .addComponent(JButton_End))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JButton_EndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_EndActionPerformed
        try {
            Thread.sleep(2000);                 //2000 milliseconds is 2 second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        sendToEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        JTextArea_ChatArea.append("Server stopping... \n");
        JTextArea_ChatArea.setText("");
        JButton_Start.setEnabled(true); //enable click option for Start Button
    }//GEN-LAST:event_JButton_EndActionPerformed

    private void JButton_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_StartActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        JTextArea_ChatArea.append("Server started...\n");
        JButton_Start.setEnabled(false);    //disable click option for Start Button
    }//GEN-LAST:event_JButton_StartActionPerformed

    private void JButton_OnlineUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_OnlineUsersActionPerformed
        JTextArea_ChatArea.append("\n Online users : \n");
        /*
        for (String current_user : usersList) {
            JTextArea_ChatArea.append(current_user + "\n");
        }
         */ //OR as follows using functional style(done for performance)
        if (usersList.isEmpty()) {
            JTextArea_ChatArea.append("No users are currently online.\n");
            return;
        }
        usersList.forEach((current_user) -> {
            JTextArea_ChatArea.append(current_user + "\n");
        });
    }//GEN-LAST:event_JButton_OnlineUsersActionPerformed

    private void JButton_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_ClearActionPerformed
        JTextArea_ChatArea.setText("");
    }//GEN-LAST:event_JButton_ClearActionPerformed

    public void addUser(String data) {
        String message;
        final String add = ": :Connect";
        final String done = "Server: :Done";
        final String name = data;
        JTextArea_ChatArea.append("Before " + name + " added. \n");
        usersList.add(name);
        JTextArea_ChatArea.append("After " + name + " added. \n");
        String[] tempList = new String[(usersList.size())];
        usersList.toArray(tempList);

        for (String token : tempList) {
            message = token + add;  //concat message
            sendToEveryone(message);
        }
        sendToEveryone(done);
    }//end - addUser() message

    public void removeUser(String data) {
        String message;
        final String add = ": :Connect";
        final String done = "Server: :Done";
        final String name = data;
        usersList.remove(name);
        String[] tempList = new String[usersList.size()];
        usersList.toArray(tempList);

        for (String token : tempList) {
            message = token + add;
            sendToEveryone(message);
        }
        sendToEveryone(done);
    }// end - removeUser() method

    public void sendToEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                JTextArea_ChatArea.append("Sending: " + message + "\n");
                writer.flush();
                JTextArea_ChatArea.setCaretPosition(JTextArea_ChatArea.getDocument().getLength());

            } catch (Exception ex) {
                JTextArea_ChatArea.append("Error telling everyone. \n");
            }
        }
    }//end - sendToEveryone() method

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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server().setVisible(true);
            }
        });
         */  //                 OR
        //OR following lambda expression can be used for same task     
        //Here lambda expression is used for performance
        java.awt.EventQueue.invokeLater(() -> {
            new Server().setVisible(true);
        });

    }// end - main() method

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_Clear;
    private javax.swing.JButton JButton_End;
    private javax.swing.JButton JButton_OnlineUsers;
    private javax.swing.JButton JButton_Start;
    private javax.swing.JTextArea JTextArea_ChatArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
