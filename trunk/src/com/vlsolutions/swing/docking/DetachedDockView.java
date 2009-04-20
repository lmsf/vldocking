//@LICENSE@

package com.vlsolutions.swing.docking;
import com.vlsolutions.swing.docking.event.DockDragEvent;
import com.vlsolutions.swing.docking.event.DockDropEvent;
import com.vlsolutions.swing.docking.event.DockEvent;
import com.vlsolutions.swing.docking.event.DockingActionCreateTabEvent;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;


/** A DockView suitable for detached dockables (in their own window).
 *
 * @see DockView
 *
 * @author Lilian Chamontin, VLSolutions
 * @since 2.0
 */
public class DetachedDockView extends DockView {
  
  private static final String uiClassID = "DetachedDockViewUI";
  
  public DetachedDockView(Dockable dockable) {
    super(dockable);
  }
  
   /** {@inheritDoc}
   * @since 2.0
   */
  public String getUIClassID() {
    return uiClassID;
  }
  
  /* common method for drag && drop, overriden from DockView to only allow tabbed drops */
  protected void scanDrop(DockEvent event, boolean drop){
    if (getParent() instanceof TabbedDockableContainer){
        // cannot nest DockTabbedPanes
        if (drop){
          ((DockDropEvent) event).rejectDrop();
        } else {
          ((DockDragEvent) event).delegateDrag();
        }
        return;
    }

    if (event.getDragSource().getDockable() == dockable) {
      // cannot drop onto itself
      if (drop){
        ((DockDropEvent) event).rejectDrop();
      } else {
        ((DockDragEvent) event).rejectDrag();
      }
      return;
    }
    
    

    // The dockview isn't contained inside a tabbedpane, 
    // there is only one possible option : to create an tab
    
    if (event.getDragSource().getDockableContainer() instanceof TabbedDockableContainer) {
      // but you cannot drop a whole tab container into a detached view
      if (drop){
        ((DockDropEvent) event).rejectDrop();
      } else {
        ((DockDragEvent) event).rejectDrag();
      }
      return;
    }
    
    Rectangle bounds = getBounds();

    // not on the borders : we should create a new tab
    // reject if key groups aren't compatible
    DockGroup thisGroup = dockable.getDockKey().getDockGroup();
    DockGroup dragGroup = event.getDragSource().getDockable().getDockKey().getDockGroup();
    if (!DockGroup.areGroupsCompatible( thisGroup, dragGroup)){
      if (drop){
        ((DockDropEvent) event).rejectDrop();
      } else {
        ((DockDragEvent) event).rejectDrag();
      }
      return;
    }
    
    Dockable d = event.getDragSource().getDockable();
    int dockableState = d.getDockKey().getDockableState();
    int viewState = dockable.getDockKey().getDockableState();
    
    if (drop){
      event.setDockingAction(new DockingActionCreateTabEvent(event.getDesktop(),
            d, dockableState, viewState, dockable, 0));
      ((DockDropEvent) event).acceptDrop(false); // don't remove the floatable : we have to find it's current position
      desktop.createTab(dockable, event.getDragSource().getDockable(), 0, true);
    } else {
      Rectangle2D r2d = new Rectangle2D.Float(bounds.x, bounds.y,
          bounds.width,
          bounds.height);
      event.setDockingAction(new DockingActionCreateTabEvent(event.getDesktop(),
            d, dockableState, viewState, dockable, 0));
      if (r2d.equals(lastDropShape)) {
        // optimized shape caching
        ( (DockDragEvent) event).acceptDrag(lastDropGeneralPath);
      } else {
        // draw a 'tabbed pane shape'
        GeneralPath path = buildPathForTab(bounds);;
        
        lastDropShape = r2d;
        lastDropGeneralPath = path;
        ( (DockDragEvent) event).acceptDrag(lastDropGeneralPath);
      }
    }    
  
  }
  
  /** {@inheritDoc}
   */
  public String toString(){
     return "DetachedDockView of " + dockable.getDockKey();
  }
  
}
