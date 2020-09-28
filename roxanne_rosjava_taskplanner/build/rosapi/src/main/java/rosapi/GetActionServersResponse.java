package rosapi;

public interface GetActionServersResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/GetActionServersResponse";
  static final java.lang.String _DEFINITION = "string[] action_servers";
  java.util.List<java.lang.String> getActionServers();
  void setActionServers(java.util.List<java.lang.String> value);
}
