//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockableState;
import com.vlsolutions.swing.docking.DockingDesktop;

/** An event describing a basic state change.
 *<p> 
 * Baic state changes include :
 *<ul>
 * <li> Maximize/Restore
 * <li> Float (just detach : attach is expressed with split/addDockable/createTab events)
 * <li> Hide (just hide : show is expressed with split/addDockable/createTab events)
 *</ul>
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class DockingActionSimpleStateChangeEvent extends DockingActionDockableEvent {
  
  /** Constructs a new event  */
  public DockingActionSimpleStateChangeEvent(DockingDesktop desktop, Dockable dockable, int initialState, int nextState) {
    super(desktop, dockable, initialState, nextState, ACTION_STATE_CHANGE);
  }
 
  
  public String toString(){
    return "DockingActionSimpleStateChangeEvent (" 
        + DockableState.getStateName(getInitialDockableState()) 
        + " -> " 
        + DockableState.getStateName(getNextDockableState());
  }

  
}
