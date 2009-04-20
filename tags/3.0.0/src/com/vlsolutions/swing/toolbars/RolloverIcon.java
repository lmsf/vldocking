//@LICENSE@

package com.vlsolutions.swing.toolbars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/** A custom Icon to have a nice rollover effect for toolbar icons.
 * <p> 
 * This icon uses a gray version of the provided image and paints it under the original one.
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class RolloverIcon implements Icon{ 
    private int shadowWidth = 1; 
    private int shadowHeight = 1; 
    private Icon icon, shadowIcon; 
 
    public RolloverIcon(Icon icon){ 
        this.icon = icon; 
        shadowIcon = new ImageIcon(GrayFilter.createDisabledImage(((ImageIcon)icon).getImage())); 
    } 
 
    public int getIconHeight(){ 
        return icon.getIconWidth(); 
    } 
 
    public int getIconWidth(){ 
        return icon.getIconHeight(); 
    } 
 
    public void paintIcon(Component c, Graphics g, int x, int y){ 
      Color highlight = UIManager.getColor("controlLtHighlight");
      Color shadow = UIManager.getColor("controlShadow"); 
      g.setColor(highlight);
      g.fillRect(x, y, getIconWidth(), getIconHeight());
      shadowIcon.paintIcon(c, g, x + shadowWidth-1, y + shadowHeight-1); 
      icon.paintIcon(c, g, x-1,  y-1); 
    } 


  
}
