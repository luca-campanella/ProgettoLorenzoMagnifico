package it.polimi.ingsw.server;

import java.net.Socket;
import java.io.*;
import java.util.*;

public class SocketPlayer implements Runnable {
    Socket socketPlayer;
    public SocketPlayer(Socket socket){
        socketPlayer=socket;
    }

}
