//@LICENSE@

package com.vlsolutions.swing.docking.event;

/** Interface implemented by classes that need to be notified of
 * a state change of a dockable.
 * <p>
 * Events are triggered when a Dockable component is :
 * <ul>
 * <LI> Docked (added to the desktop)
 * <LI> set in Auto-hide mode (collapsed as a border button)
 * <LI> closed (removed from the desktop)
 * <LI> maximized (the only one visible)
 * <LI> floating (detached from the desktop)
 * </UL>
 * <p>
 *
 *
 * @see DockableStateChangeEvent
 * @see DockableStateWillChangeEvent
 * @see com.vlsolutions.swing.docking.DockingDesktop#addDockableStateChangeListener(DockableStateChangeListener)
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 * */
public interface DockableStateChangeListener {

  /** this method will be called after a dockable component has changed of state
   * (closed, hidden, docked) */
  public void dockableStateChanged(DockableStateChangeEvent event);
}
