package rosbridge_msgs;

public interface ConnectedClients extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosbridge_msgs/ConnectedClients";
  static final java.lang.String _DEFINITION = "ConnectedClient[] clients\n";
  java.util.List<rosbridge_msgs.ConnectedClient> getClients();
  void setClients(java.util.List<rosbridge_msgs.ConnectedClient> value);
}
