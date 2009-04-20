//@LICENSE@

package com.vlsolutions.swing.docking;

import com.vlsolutions.swing.docking.event.DockDragEvent;
import com.vlsolutions.swing.docking.event.DockDropEvent;
import com.vlsolutions.swing.docking.event.DockEvent;
import java.awt.Component;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** A DockView that can be nested into a TabbedDockableContainer
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class TabbedDockView extends DockView {
  
  public TabbedDockView(Dockable dockable) {
    super(dockable, false);
  }
  
  protected void scanDrop(DockEvent event, boolean drop){
    int state = dockable.getDockKey().getDockableState();
    if (state == DockableState.STATE_DOCKED) {
      super.scanDrop(event, drop);
    } else if (state == DockableState.STATE_FLOATING){
      // don't allow drop for floating tabs : only when child of a compound dockable
      if (DockingUtilities.isChildOfCompoundDockable(dockable)){
        super.scanDrop(event, drop);
      } else {
        if (drop){
          ((DockDropEvent) event).rejectDrop();
        } else {
          ((DockDragEvent) event).delegateDrag();
        }
      }
    }
  }
  
  public void setVisible(boolean visible){
    super.setVisible(visible);
    if (visible){
      if (UIManager.getBoolean("TabbedContainer.requestFocusOnTabSelection")){
        // this is a workaround to get focus on a tab when it's selected
        // obviously, this relies in the fact that the parent of this dockView is
        // the tab container.
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            if (getDockable() != null){
              Component comp = getDockable().getComponent();
              if (comp != null){
                comp.requestFocus();
              }
            }
          }
        });
      }
    }
  }
  
  
}
