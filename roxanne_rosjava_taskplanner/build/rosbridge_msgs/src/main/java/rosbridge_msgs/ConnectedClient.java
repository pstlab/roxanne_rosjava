package rosbridge_msgs;

public interface ConnectedClient extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosbridge_msgs/ConnectedClient";
  static final java.lang.String _DEFINITION = "string ip_address\ntime connection_time\n";
  java.lang.String getIpAddress();
  void setIpAddress(java.lang.String value);
  org.ros.message.Time getConnectionTime();
  void setConnectionTime(org.ros.message.Time value);
}
