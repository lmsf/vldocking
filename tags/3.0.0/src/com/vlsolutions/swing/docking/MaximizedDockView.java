//@LICENSE@

package com.vlsolutions.swing.docking;

import com.vlsolutions.swing.docking.event.DockDragEvent;
import com.vlsolutions.swing.docking.event.DockDropEvent;
import com.vlsolutions.swing.docking.event.DockEvent;

/** A dockView suitable for maximized dockables
 *
 * @author Lilian Chamontin, VLSolutions
 * @update 2007/01/24 Lilian Chamontin : added DnD blocking (maximized components should't support 
 * drag/drop from floating windows)
 */
public class MaximizedDockView extends DockView {
  
  public MaximizedDockView(Dockable dockable) {
    super(dockable, true);   
  }

  /** maximized dockable don't support drag and drop */
  public void processDockableDrag(DockDragEvent event) { //2007/01/24
    event.rejectDrag();
  }

  /** maximized dockable don't support drag and drop */
  public void processDockableDrop(DockDropEvent event) {
    event.rejectDrop();
  }

  
  
}
