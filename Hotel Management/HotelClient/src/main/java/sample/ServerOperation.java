package sample;

import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerOperation {
    private Socket socket;
    private JSONObject input;
    public ServerOperation(JSONObject object) throws IOException {
        this.socket = new Socket(InetAddress.getLocalHost(),8080);
        this.input = object;
    }
    public JSONObject getAnswerFromServer() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(input.toString());
        writer.newLine();
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = reader.readLine();
        JSONObject output = new JSONObject(s);
        return output;
    }
}
