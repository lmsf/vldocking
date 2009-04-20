//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockableState;
import com.vlsolutions.swing.docking.DockingDesktop;

/** A DockingActionEvent describing the closing of a dockable
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionCloseEvent extends DockingActionDockableEvent {
  
  public DockingActionCloseEvent(DockingDesktop desktop, Dockable dockable, int initialState) {
    super(desktop, dockable, initialState, DockableState.STATE_CLOSED, ACTION_CLOSE);
  }
  
  public String toString(){
    return "DockingActionCloseEvent";
  }

}
