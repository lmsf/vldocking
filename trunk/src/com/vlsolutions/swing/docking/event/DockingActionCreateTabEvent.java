//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingDesktop;

/** A DockingActionEvent describing a tab insertion (or movement).
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionCreateTabEvent extends DockingActionDockableEvent {

  private Dockable base;

  private int order;
  
  public DockingActionCreateTabEvent(DockingDesktop desktop, Dockable dockable, 
      int initialState, int nextState, Dockable base, int order) {
    super(desktop, dockable, initialState, nextState, ACTION_CREATE_TAB);
    this.base = base;
    this.order = order;
  }

  /** Returns the dockable used as a reference to create a tab (may already belong to a tab)*/
  public Dockable getBase() {
    return base;
  }

  /** Returns the order of insertion in the tabbed container */
  public int getOrder() {
    return order;
  }
  
  public String toString(){
    return "DockingActionCreateTabEvent";
  }

  
}
