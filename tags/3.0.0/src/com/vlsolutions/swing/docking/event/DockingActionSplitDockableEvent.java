//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;

/** A DockingActionEvent describing a split action (from a base dockable).
 *
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionSplitDockableEvent extends DockingActionDockableEvent {
  private Dockable base;
  private float dividorLocation;
  
  private DockingConstants.Split splitPosition;
  
  /** Constructs a new event  */
  public DockingActionSplitDockableEvent(DockingDesktop desktop, Dockable dockable,
      int initialState, int nextState, Dockable base, DockingConstants.Split splitPosition,
      float dividorLocation) {
    super(desktop, dockable, initialState, nextState, ACTION_SPLIT_DOCKABLE);
    this.base = base;
    this.dividorLocation = dividorLocation;
    this.splitPosition = splitPosition;
  }
  
  /** Returns the dockable which will be used as a base for the splitting */
  public Dockable getBase() {
    return base;
  }
  
  public float getDividorLocation() {
    return dividorLocation;
  }
  
  public DockingConstants.Split getSplitPosition() {
    return splitPosition;
  }
  
  public String toString(){
    return "DockingActionSplitDockableEvent [base:" + base.getDockKey()
    + ", dockable:" + getDockable().getDockKey()+"]";
  }
  
}
