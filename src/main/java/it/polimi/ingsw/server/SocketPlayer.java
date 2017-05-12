package it.polimi.ingsw.server;

import java.net.Socket;

public class SocketPlayer implements Runnable {
    Socket socketPlayer;
    public SocketPlayer(Socket socket){
        socketPlayer=socket;
    }

    @Override
    public void run() {
        //TODO implement the method run in socket
    }
}
