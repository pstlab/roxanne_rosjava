package rosapi;

public interface ServiceProvidersResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/ServiceProvidersResponse";
  static final java.lang.String _DEFINITION = "string[] providers";
  java.util.List<java.lang.String> getProviders();
  void setProviders(java.util.List<java.lang.String> value);
}
