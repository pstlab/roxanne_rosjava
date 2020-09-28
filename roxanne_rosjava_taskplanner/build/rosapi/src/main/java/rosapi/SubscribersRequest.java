package rosapi;

public interface SubscribersRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/SubscribersRequest";
  static final java.lang.String _DEFINITION = "string topic\n";
  java.lang.String getTopic();
  void setTopic(java.lang.String value);
}
