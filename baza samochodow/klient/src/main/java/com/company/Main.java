package com.company;

import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
        Klient klient = new Klient(socket);
        klient.connect();
    }
}
