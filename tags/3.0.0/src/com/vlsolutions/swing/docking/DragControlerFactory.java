//@LICENSE@
package com.vlsolutions.swing.docking;

/** A Basic factory providing a lightweight and heavyweight implementation of the
 * DragControler interface
 *
 * @author Lilian Chamontin, vlsolutions.
 */
public class DragControlerFactory {
    private static DragControlerFactory instance;

    public DragControler createDragControler(DockingDesktop desktop) {
        if (DockingPreferences.isLightWeightUsageEnabled()) {
            return new LightWeightDragControler(desktop);
        } else {
            return new HeavyWeightDragControler(desktop);
        }
    }

    public static DragControlerFactory getInstance() {
        if (instance == null) {
            instance = new DragControlerFactory();
        }
        return instance;
    }

    public static void setInstance(DragControlerFactory instance) {
        DragControlerFactory.instance = instance;
    }
}
