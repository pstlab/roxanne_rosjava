package rosapi;

public interface ServicesForTypeResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServicesForTypeResponse";
  static final java.lang.String _DEFINITION = "string[] services";
  java.util.List<java.lang.String> getServices();
  void setServices(java.util.List<java.lang.String> value);
}
