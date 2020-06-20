package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        ServerSocket serverSocket = new ServerSocket(8080);
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASS = "nosorozec123";
        while (true) {
            Socket socket = serverSocket.accept();
            Connection connection = DriverManager.getConnection(URL,USER,PASS);
            Watek watek = new Watek(socket,connection);
            watek.start();
        }
    }
}
