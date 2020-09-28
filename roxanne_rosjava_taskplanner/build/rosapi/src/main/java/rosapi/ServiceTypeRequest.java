package rosapi;

public interface ServiceTypeRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceTypeRequest";
  static final java.lang.String _DEFINITION = "string service\n";
  java.lang.String getService();
  void setService(java.lang.String value);
}
