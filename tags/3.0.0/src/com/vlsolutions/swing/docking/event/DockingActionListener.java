//@LICENSE@

package com.vlsolutions.swing.docking.event;

/** A listener to docking actions.
 *<p>
 * This listener can be used to be notified of docking actions and to react to them (including 
 * vetoing).
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.1
 */
public interface DockingActionListener {
  
  /** Invoked before a docking action is processed, to give a chance to the 
   * listener to react to it and cancel it if needed.
   *
   * @return true  if action can be processed, false if it should be cancelled
   *
   */
  public boolean acceptDockingAction(DockingActionEvent event);
  
  /** Invoked once a docking action has been performed.
   *
   * @param  event   the event corresponding to the docking action. Subclasses of 
   *                 DockingActionEvent are used to reflect different types of action
   *                 (DockingActionCloseEvent when a dockable is closed, 
   *                 DockingActionSplitDockableEvent when a dockable is moved ...).
   */
  public void dockingActionPerformed(DockingActionEvent event);
    
}
