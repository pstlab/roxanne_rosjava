package rosapi;

public interface PublishersRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/PublishersRequest";
  static final java.lang.String _DEFINITION = "string topic\n";
  java.lang.String getTopic();
  void setTopic(java.lang.String value);
}
