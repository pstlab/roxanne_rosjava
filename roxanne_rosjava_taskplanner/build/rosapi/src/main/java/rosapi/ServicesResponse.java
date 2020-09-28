package rosapi;

public interface ServicesResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServicesResponse";
  static final java.lang.String _DEFINITION = "string[] services";
  java.util.List<java.lang.String> getServices();
  void setServices(java.util.List<java.lang.String> value);
}
