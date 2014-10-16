package org.jdatepicker;

/**
 * Created by jheyns on 2014/10/16.
 */
public class ComponentManager {

    private static final ComponentManager instance;

    static {
        instance = new ComponentManager(
                new ComponentColorDefaults(),
                new ComponentTextDefaults(),
                new ComponentIconDefaults(),
                new ComponentFormatDefaults()
        );
    }

    private ComponentColorDefaults componentColorDefaults;
    private ComponentTextDefaults componentTextDefaults;
    private ComponentIconDefaults componentIconDefaults;
    private ComponentFormatDefaults componentFormatDefaults;

    private ComponentManager(ComponentColorDefaults componentColorDefaults,
                             ComponentTextDefaults componentTextDefaults,
                             ComponentIconDefaults componentIconDefaults,
                             ComponentFormatDefaults componentFormatDefaults) {
        this.componentColorDefaults = componentColorDefaults;
        this.componentTextDefaults = componentTextDefaults;
        this.componentIconDefaults = componentIconDefaults;
        this.componentFormatDefaults = componentFormatDefaults;
    }

    public static ComponentManager getInstance() {
        return instance;
    }

    public ComponentColorDefaults getComponentColorDefaults() {
        return componentColorDefaults;
    }

    public void setComponentColorDefaults(ComponentColorDefaults componentColorDefaults) {
        this.componentColorDefaults = componentColorDefaults;
    }

    public ComponentTextDefaults getComponentTextDefaults() {
        return componentTextDefaults;
    }

    public void setComponentTextDefaults(ComponentTextDefaults componentTextDefaults) {
        this.componentTextDefaults = componentTextDefaults;
    }

    public ComponentIconDefaults getComponentIconDefaults() {
        return componentIconDefaults;
    }

    public void setComponentIconDefaults(ComponentIconDefaults componentIconDefaults) {
        this.componentIconDefaults = componentIconDefaults;
    }

    public ComponentFormatDefaults getComponentFormatDefaults() {
        return componentFormatDefaults;
    }

    public void setComponentFormatDefaults(ComponentFormatDefaults componentFormatDefaults) {
        this.componentFormatDefaults = componentFormatDefaults;
    }

}
