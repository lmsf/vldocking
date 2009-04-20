//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import java.awt.Component;

/** A DockingActionEvent describing a split action (from a base component (splitcontainer, tab...)).
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1 
 */
public class DockingActionSplitComponentEvent extends DockingActionDockableEvent {
  private Component base;
  private float dividorLocation;
  private float parentDividorLocation;

  private DockingConstants.Split splitPosition;
  
  /** Constructs a new event */
  public DockingActionSplitComponentEvent(DockingDesktop desktop, Dockable dockable, 
      int initialState, int nextState, Component base, DockingConstants.Split splitPosition, float dividorLocation) {
    super(desktop, dockable, initialState, nextState, ACTION_SPLIT_COMPONENT);
    this.base = base;
    this.dividorLocation = dividorLocation;
    this.parentDividorLocation = -1;
    this.splitPosition = splitPosition;
  }

  /** Constructs a new event.
   *<p>
   * This version of the constructor also contains resizing information for the parent of 
   * thhe splitted component.
   */
  public DockingActionSplitComponentEvent(DockingDesktop desktop, Dockable dockable, 
      int initialState, int nextState, Component base, DockingConstants.Split splitPosition, 
      float dividorLocation, float parentDividorLocation) {
    super(desktop, dockable, initialState, nextState, ACTION_SPLIT_COMPONENT);
    this.base = base;
    this.dividorLocation = dividorLocation;
    this.parentDividorLocation = parentDividorLocation;
    this.splitPosition = splitPosition;
  }

  /** Returns the dockable which will be used as a base for the splitting */
  public Component getBase() {
    return base;
  }

  public float getDividorLocation() {
    return dividorLocation;
  }

  /** returns a dividor location value for the parent split container, or -1 if not needed 
   *<p>
   * This value is used to express inserting a component with same orientation of the 
   * parent split container (like : transform A|B into [A|child]|B : we need to adjust 
   * A|child dividor, and also [] | B dividor).
   */
  public float getParentDividorLocation() {
    return parentDividorLocation;
  }

  public DockingConstants.Split getSplitPosition() {
    return splitPosition;
  }
  public String toString(){
    return "DockingActionSplitComponentEvent";
  }
}

  
