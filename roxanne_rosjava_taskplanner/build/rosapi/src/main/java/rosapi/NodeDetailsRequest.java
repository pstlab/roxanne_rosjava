package rosapi;

public interface NodeDetailsRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/NodeDetailsRequest";
  static final java.lang.String _DEFINITION = "string node\n";
  java.lang.String getNode();
  void setNode(java.lang.String value);
}
