//@LICENSE@

package com.vlsolutions.swing.docking.ui;

import com.vlsolutions.swing.docking.AutoHideExpandPanel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/** The UI delegate for the AutoHideExpandPanel component.
 *<p>
 * This class is mostly a placeholder in the case the developper would like 
 * to provide a custom replacement for the expand panel look and feel.
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class AutoHideExpandPanelUI extends BasicPanelUI {
  
  private static AutoHideExpandPanelUI instance = new AutoHideExpandPanelUI();
  
  public AutoHideExpandPanelUI() {
  }
  
  public static ComponentUI createUI(JComponent c) {
    return instance;
  }
  
  public void installUI(JComponent comp){
    super.installUI(comp);    
    AutoHideExpandPanel panel  = (AutoHideExpandPanel)comp;    
    panel.resetBorders();    
  }
  
  public void uninstallUI(JComponent comp){
    super.uninstallUI(comp);        
  }

  
  
}
