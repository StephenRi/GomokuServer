import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import message.LinkMessage;
import message.LinkMessageAck;
import message.StepMessage;

public class Launcher {
    public static void main(String[] args) throws InterruptedException{
        Configuration config = new Configuration();
        config.setHostname("144.168.59.201");
        //config.setHostname("localhost");
        config.setPort(9092);
        final SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println(socketIOClient.getRemoteAddress() + " connect");
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println(socketIOClient.getRemoteAddress() + " disconnect");
            }
        });

        //处理连接，分房间
        server.addEventListener("linkEvent", LinkMessage.class, new DataListener<LinkMessage>() {
            public void onData(SocketIOClient socketIOClient, LinkMessage linkMessage, AckRequest ackRequest) throws Exception {
                String userId = linkMessage.getUserId();
                String roomNo = linkMessage.getRoomNo();
                socketIOClient.joinRoom(roomNo);
                int size = server.getRoomOperations(roomNo).getClients().size();

                if (size == 1) {
                    System.out.println(roomNo + " has " + size + " clients, and " + userId + " is black");
                    LinkMessageAck ack = new LinkMessageAck();
                    ack.setUserId(userId);
                    ack.setRoomNo(roomNo);
                    ack.setUserRole("black");
                    ack.setNumber(1);
                    server.getRoomOperations(roomNo).sendEvent("linkEventAck", ack);
                }

                if (size == 2) {
                    System.out.println(roomNo + " has " + size + " clients, and " + userId + " is write");
                    LinkMessageAck ack = new LinkMessageAck();
                    ack.setUserId(userId);
                    ack.setRoomNo(roomNo);
                    ack.setUserRole("write");
                    ack.setNumber(2);
                    server.getRoomOperations(roomNo).sendEvent("linkEventAck", ack);
                }

                if (size >= 3) {
                    System.out.println(roomNo + " has " + size + " clients, and " + userId + " is ob");
                    LinkMessageAck ack = new LinkMessageAck();
                    ack.setUserId(userId);
                    ack.setRoomNo(roomNo);
                    ack.setUserRole("ob");
                    ack.setNumber(3);
                    server.getRoomOperations(roomNo).sendEvent("linkEventAck", ack);
                }
            }
        });

        server.addEventListener("stepEvent", StepMessage.class, new DataListener<StepMessage>() {
            public void onData(SocketIOClient socketIOClient, StepMessage stepMessage, AckRequest ackRequest) throws Exception {
                String roomNo = stepMessage.getRoomNo();
                server.getRoomOperations(roomNo).sendEvent("stepEventAck", stepMessage);
            }
        });

        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }
}
