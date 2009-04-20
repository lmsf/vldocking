//@LICENSE@

package com.vlsolutions.swing.docking.event;

import com.vlsolutions.swing.docking.Dockable;

/** An event for tracking selection changes of dockables. 
 * <p>
 *  Useful for example when  
 *  the developper wants to enable or disable actions depending on the dockable 
 *  which takes the keyboard focus.
 *
 * <p> implementation note : works with keyboard focus events.
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.0
 * @see DockableSelectionListener
 */
public class DockableSelectionEvent {
  private Dockable selectedDockable;
  public DockableSelectionEvent(Dockable selectedDockable) {
    this.selectedDockable = selectedDockable;
  }
  
  /** Returns the currently selected Dockable */
  public Dockable getSelectedDockable(){
    return selectedDockable;
  }
  
  
}
