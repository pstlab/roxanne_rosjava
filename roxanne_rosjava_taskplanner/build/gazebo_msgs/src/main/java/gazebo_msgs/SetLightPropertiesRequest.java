package gazebo_msgs;

public interface SetLightPropertiesRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/SetLightPropertiesRequest";
  static final java.lang.String _DEFINITION = "string light_name                    # name of Gazebo Light\nstd_msgs/ColorRGBA diffuse           # diffuse color as red, green, blue, alpha\nfloat64 attenuation_constant\nfloat64 attenuation_linear\nfloat64 attenuation_quadratic\n";
  java.lang.String getLightName();
  void setLightName(java.lang.String value);
  std_msgs.ColorRGBA getDiffuse();
  void setDiffuse(std_msgs.ColorRGBA value);
  double getAttenuationConstant();
  void setAttenuationConstant(double value);
  double getAttenuationLinear();
  void setAttenuationLinear(double value);
  double getAttenuationQuadratic();
  void setAttenuationQuadratic(double value);
}
