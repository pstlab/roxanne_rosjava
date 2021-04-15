package com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform;

import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;
import it.cnr.istc.pst.platinum.control.platform.PlatformProxy;
import org.ros.node.ConnectedNode;

import java.lang.reflect.Constructor;

/**
 *
 */
public class RosJavaPlatformProxyBuilder {

    public RosJavaPlatformProxyBuilder() {
    }

    /**
     *
     * @param pClass
     * @param node
     * @param path
     * @param <T>
     * @return
     * @throws PlatformException
     */
    public static <T extends PlatformProxy> T build(Class<T> pClass, ConnectedNode node, String path)
            throws PlatformException
    {
        T proxy = null;
        try
        {
            Constructor<T> c = pClass.getDeclaredConstructor(ConnectedNode.class);
            c.setAccessible(true);
            proxy = (T) c.newInstance(node);
            proxy.initialize(path);
            return proxy;
        } catch (Exception var4) {
            throw new PlatformException("Error while creating platform proxy instance:\n\t- message= " + var4.getMessage() + "\n");
        }
    }
}
