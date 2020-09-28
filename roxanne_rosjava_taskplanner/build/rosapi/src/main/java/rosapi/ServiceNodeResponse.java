package rosapi;

public interface ServiceNodeResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceNodeResponse";
  static final java.lang.String _DEFINITION = "string node";
  java.lang.String getNode();
  void setNode(java.lang.String value);
}
