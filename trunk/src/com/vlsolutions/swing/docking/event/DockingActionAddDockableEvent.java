//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingDesktop;
import java.awt.Container;

/** A DockingActionEvent describing the first insertion of a dockable either inside a desktop
 * docking panel (main panel) or into a compoundDockable (nested) container.
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionAddDockableEvent extends DockingActionDockableEvent {
  
  private Container parentContainer;
  
  /** Constructor for a DockingActionAddDockableEvent 
   * @param parentContainer the container (DockingPanel or CompoundDockingPanel) into which this
   *                        dockable is goint to be added. 
   */
  public DockingActionAddDockableEvent(DockingDesktop desktop, Dockable dockable, 
      int initialState, int nextState, Container parentContainer) {
    super(desktop, dockable, initialState, nextState, ACTION_ADD_DOCKABLE);
    this.parentContainer = parentContainer;
  }

  /** Returns the parent container into which this dockable will be added  */
  public Container getParentContainer() {
    return parentContainer;
  }

  public String toString(){
    return "DockingActionAddDockableEvent ";
  }

  
}
