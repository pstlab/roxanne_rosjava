package rosapi;

public interface SubscribersResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/SubscribersResponse";
  static final java.lang.String _DEFINITION = "string[] subscribers";
  java.util.List<java.lang.String> getSubscribers();
  void setSubscribers(java.util.List<java.lang.String> value);
}
