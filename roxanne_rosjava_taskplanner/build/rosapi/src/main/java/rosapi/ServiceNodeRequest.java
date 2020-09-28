package rosapi;

public interface ServiceNodeRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceNodeRequest";
  static final java.lang.String _DEFINITION = "string service\n";
  java.lang.String getService();
  void setService(java.lang.String value);
}
