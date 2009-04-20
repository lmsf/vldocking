//@LICENSE@

package com.vlsolutions.swing.docking;

import java.awt.Component;
import java.awt.Shape;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/** This interface defines the features required for a drag contoler.
 *
 * @since 3.0
 * @author Lilian Chamontin, vlsolutions.
 */
public interface DragControler extends MouseListener, MouseMotionListener{

    public void cancelDrag();

    public Dockable getDockable();

    public DockDropReceiver getDropReceiver();

    public Shape getDropShape();

    public boolean isFloatingShape();

}
