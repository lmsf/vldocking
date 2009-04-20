//@LICENSE@

package com.vlsolutions.swing.toolbars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;

/** A simple rounded border. 
 *
 * Can be used anywhere a rounded border is needed (no specific purpose).
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class RoundedBorder implements Border {

  private Insets insets = new Insets(2,2,2,2);
  private Color shadow = UIManager.getColor("controlShadow");

  public RoundedBorder() {
  }

  public boolean isBorderOpaque() {
    return false;
  }

  public void paintBorder(Component component, Graphics graphics, int x,
      int y, int w, int h) {
    graphics.setColor(shadow);
    graphics.drawRoundRect(x,y, w-1, h-1, 9, 9);
     
  }

  public Insets getBorderInsets(Component component) {
    return insets;
  }
}
