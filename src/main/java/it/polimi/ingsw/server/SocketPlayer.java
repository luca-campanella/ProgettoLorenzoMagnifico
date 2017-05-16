package it.polimi.ingsw.server;

import it.polimi.ingsw.protocol.LoginOrRegisterPacket;
import it.polimi.ingsw.protocol.PacketType;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;

public class SocketPlayer implements Runnable {

    private Socket socket;

    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    private ServerMain serverMainInst;

    /**
     * constructor to open the streams
     * @param socket
     * @param serverMainInst needs this to call login and register functions and to be able to join a room
     * @throws IOException
     */
    public SocketPlayer(Socket socket, ServerMain serverMainInst) throws IOException {
        this.socket=socket;
        this.serverMainInst = serverMainInst;
        inStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

    }

    @Override
    public void run() {
        Debug.printVerbose("New socket player object waiting for login");
        try {
            waitLoginRegisterPackets();//before performing any action the player needs to be logged ind
        } catch (IOException | ClassNotFoundException e) {
            Debug.printError("Something went wrong when reading objects from client with address " + socket.getInetAddress(), e);
            closeEverything(); //At this point the only thing we can do is close the connection and terminate the process
            //TODO signal room that one player is no longer connected
        }
    }

    private void waitLoginRegisterPackets() throws IOException, ClassNotFoundException {
        PacketType pkgType = null;
        LoginOrRegisterPacket packet = null;
        do {
                pkgType = (PacketType) inStream.readObject();
                //TODO tell clients the packet needs to be a login or a register one
        } while(pkgType != PacketType.LOGIN && pkgType != PacketType.REGISTER);

        packet = (LoginOrRegisterPacket) inStream.readObject();

        switch(pkgType) {
            case REGISTER:
                serverMainInst.registerPlayer(packet.getNickname(), packet.getPassword());
                break;
            case LOGIN:
                serverMainInst.loginPlayer(packet.getNickname(), packet.getPassword());
                break;
        }
    }

    private void closeEverything()
    {
        try {
            inStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            Debug.printError("Can't close the socket connection with client", e);
        }
    }
}
