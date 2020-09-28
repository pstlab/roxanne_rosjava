package rosapi;

public interface NodesResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/NodesResponse";
  static final java.lang.String _DEFINITION = "string[] nodes";
  java.util.List<java.lang.String> getNodes();
  void setNodes(java.util.List<java.lang.String> value);
}
