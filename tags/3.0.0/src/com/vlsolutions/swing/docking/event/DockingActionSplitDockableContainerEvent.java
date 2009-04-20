//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import java.awt.Component;
import java.awt.Container;

/** A split event : split a base component in two, and puts a dockable container
 * in the other split position.
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionSplitDockableContainerEvent extends DockingActionEvent {

  private Container dockableContainer;

  private DockingConstants.Split position;

  private float location;

  private Component base;
  
  /** Constructs a new event 
   */
  public DockingActionSplitDockableContainerEvent(DockingDesktop desk, 
      int initialState, int nextState, Component base, 
      Container dockableContainer, DockingConstants.Split position, float location) {
    super(desk, initialState, nextState, ACTION_SPLIT_DOCKABLE);
    this.base = base;
    this.dockableContainer = dockableContainer;
    this.position = position;
    this.location = location;
  }

  public Container getDockableContainer() {
    return dockableContainer;
  }

  public DockingConstants.Split getSplitPosition() {
    return position;
  }

  public float getLocation() {
    return location;
  }

  public Component getBase() {
    return base;
  }
  
}
