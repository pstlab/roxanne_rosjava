package rosapi;

public interface ServiceHostResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceHostResponse";
  static final java.lang.String _DEFINITION = "string host";
  java.lang.String getHost();
  void setHost(java.lang.String value);
}
