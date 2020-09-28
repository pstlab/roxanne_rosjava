package rosapi;

public interface NodeDetailsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/NodeDetailsResponse";
  static final java.lang.String _DEFINITION = "string[] subscribing\nstring[] publishing\nstring[] services";
  java.util.List<java.lang.String> getSubscribing();
  void setSubscribing(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getPublishing();
  void setPublishing(java.util.List<java.lang.String> value);
  java.util.List<java.lang.String> getServices();
  void setServices(java.util.List<java.lang.String> value);
}
