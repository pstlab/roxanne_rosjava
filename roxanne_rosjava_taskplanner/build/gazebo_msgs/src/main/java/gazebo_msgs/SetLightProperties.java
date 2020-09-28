package gazebo_msgs;

public interface SetLightProperties extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "gazebo_msgs/SetLightProperties";
  static final java.lang.String _DEFINITION = "string light_name                    # name of Gazebo Light\nstd_msgs/ColorRGBA diffuse           # diffuse color as red, green, blue, alpha\nfloat64 attenuation_constant\nfloat64 attenuation_linear\nfloat64 attenuation_quadratic\n---\nbool success                         # return true if get successful\nstring status_message                # comments if available\n";
}
