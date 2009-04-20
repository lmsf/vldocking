//@LICENSE@

package com.vlsolutions.swing.docking;

import java.awt.*;

/** General information about the current state of a dockable component.
 * <p>
 * This class is a simple record of a dockable and its current display state.
 * <p> It is used together with DockableStateChangeEvents (and listeners) to
 * track changes of Dockable visibility.
 *
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 *
 * @update 2005/10/07 Lilian Chamontin : added getStateName(int state) method
 */
public class DockableState implements Comparable {
  private Dockable dockable;
  
  private static final String [] stateNames = {"CLOSED", "DOCKED", "HIDDEN", "MAXIMIZED", "FLOATING"};

  /** A constant describing a Dockable that is not visible.
   *
   */
  public static final int STATE_CLOSED = 0; // only registered

  /** A constant describing a Dockable that is visible (displayed in a DockableContainer) */
  public static final int STATE_DOCKED = 1;

  /** A constant describing a Dockable that is in auto-hide mode (reduced to a button) */
  public static final int STATE_HIDDEN = 2;

  /** A constant describing a Dockable that is currently maximized */
  public static final int STATE_MAXIMIZED = 3;

  /** A constant describing a Dockable that is currently floating (detached from the desktop) */
  public static final int STATE_FLOATING = 4;
  
  private int state ;

  /** Relative position for hidden/closed dockable show-again */
  private RelativeDockablePosition position;

  /** Desktop currently using this dockable */
  private DockingDesktop desktop;


  public DockableState() {
    position = new RelativeDockablePosition();
  }

  public DockableState(DockingDesktop desktop, Dockable dockable, int state) {
    this.desktop = desktop;
    this.dockable = dockable;
    this.state = state;
    position = new RelativeDockablePosition();
  }

  public DockableState(DockingDesktop desktop, Dockable dockable, int state, RelativeDockablePosition position) {
    this.desktop = desktop;
    this.dockable = dockable;
    this.state = state;
    this.position = position;
  }
  
  /** Creates a dockable state based on an existing state, with another relative positionning */
  public DockableState(DockableState copy, RelativeDockablePosition position) {
    this.desktop = copy.desktop;
    this.dockable = copy.dockable;
    this.state = copy.state;
    this.position = position;
  }

  /** Comparable interface, used to sort components by name order.
   * */
  public int compareTo(Object object) {
    if (object instanceof DockableState){
      return dockable.getDockKey().getName().compareTo( ( (DockableState)
          object).dockable.getDockKey().getName());
    } else {
      return -1;
    }
  }

  /** Returns the dockable this state is for */
  public Dockable getDockable() {
    return dockable;
  }

  /** Convenience method returning wether the dockable is in the DOCKED state */
  public boolean isDocked(){
    return state == STATE_DOCKED;
  }

  /** Convenience method returning wether the dockable is in the HIDDEN state */
  public boolean isHidden(){
    return state == STATE_HIDDEN;
  }
  
  /** Convenience method returning wether the dockable is in the CLOSED state */
  public boolean isClosed(){
    return state == STATE_CLOSED;
  }
  
  /** Convenience method returning wether the dockable is in the FLOATING state */
  public boolean isFloating(){
    return state == STATE_FLOATING;
  }
  
  /** Convenience method returning wether the dockable is in the MAXIMIZED state */
  public boolean isMaximized(){
    return state == STATE_MAXIMIZED;
  }

  /** Returns the current state of the dockable (CLOSED, DOCKED, HIDDEN...).
   */
  public int getState() {
    return state;
  }

  /** Returns the relative restore position of the dockable when it is not shown (auto-hide
   * or closed).
   * <P>
   * Please note that this position is meaningless for visible components
   * (as it is not automatically calculated at every move / resize of the dockable ).
   *
   * */
  public RelativeDockablePosition getPosition(){
    return position;
  }

  public String toString(){
    return "DockableState [" + dockable.getDockKey() + ", state=" + stateNames[state] + ", position="
        + position;
  }
  
  /** Returns a litteral representation of the given state 
   *@since 2.0.1
   */
  public static String getStateName(int state){ //2005/10/07
    return stateNames[state];
  }

  /** Returns the desktop currently using this dockable, or null if none 
   *
   * @since 2.1
   */
  public DockingDesktop getDesktop() {
    return desktop;
  }

  /** Updates the desktop field (desktop using this dockable)
   * @since 2.1
   */
  public void setDesktop(DockingDesktop desktop) {
    this.desktop = desktop;
  }
  
  

}
