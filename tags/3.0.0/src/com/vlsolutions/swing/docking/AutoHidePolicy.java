//@LICENSE@

package com.vlsolutions.swing.docking;

import java.beans.*;

/** This class holds preferences on default autohiding behaviour.
 *
 * @see AutoHideButtonPanel
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 */
public class AutoHidePolicy {

  /** A constant for expanding a button on rollover */
  public static final int EXPAND_ON_ROLLOVER = 0;

  /** A constant for expanding a button on click */
  public static final int EXPAND_ON_CLICK = 1;

  /** a constant designing the bound property DEFAULT_HIDE_BORDER */
  public static final String PROPERTY_DEFAULT_HIDE_BORDER = "defaultHideBorder";
  /** a constant designing the bound property DEFAULT_GAP */
  public static final String PROPERTY_DEFAULT_GAP = "defaultGap";

  /** a constant designing the bound property EXPAND_MODE */
  public static final String PROPERTY_EXPAND_MODE = "expandMode";

  /** a constant designing the bound property ROLLOVER_TRIGGER_DELAY */
  public static final String PROPERTY_ROLLOVER_TRIGGER_DELAY = "rolloverTriggerDelay";

  /** a constant designing the bound property EXPANSION_DURATION */
  public static final String PROPERTY_EXPANSION_DURATION = "expansionDuration";


  private static AutoHidePolicy policy = new AutoHidePolicy();

  /** where do we put the button at startup */
  private DockingConstants.Hide defaultHideBorder = DockingConstants.HIDE_LEFT;

  /** pixels between two buttons */
  private int defaultGap = 4;

  private int expandMode; // rollover / click
  private int rolloverTriggerDelay = 500; // wait before expansion (only for expandMode==ROLLOVER)
  private int expansionDuration = 300; // millis

  private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

  private AutoHidePolicy() {
  }

  /** Updates the default hide border.
   * <p>
   * Default is AutoHideExpandPanel.LEFT;
   *
   * @param defaultHideBorder values taken from DockingConstants.HIDE_TOP, HIDE_LEFT, HIDE_BOTTOM, HIDE_RIGHT
   * */
  public void setDefaultHideBorder(DockingConstants.Hide defaultHideBorder){
    DockingConstants.Hide old = this.defaultHideBorder;
    this.defaultHideBorder = defaultHideBorder;
    propertySupport.firePropertyChange(PROPERTY_DEFAULT_HIDE_BORDER, old, defaultHideBorder);
  }

  /** Returns the default hide border (used when not specified in a DockKey)*/
  public DockingConstants.Hide getDefaultHideBorder(){
    return defaultHideBorder;
  }

  /** Returns the default gap between border components */
  public int getDefaultGap(){
    return defaultGap;
  }

  /** Updates the gap (in pixels) between auto-hide buttons*/
  public void setDefaultGap(int gap){
    int old = this.defaultGap;
    this.defaultGap = gap;
    propertySupport.firePropertyChange(PROPERTY_DEFAULT_GAP, old, gap);
  }

  /** Returns the singleton instance of this class */
  public static AutoHidePolicy getPolicy(){
    return policy;
  }

  /** Returns the expand mode in use.
   *   @return EXPAND_ON_ROLLOVER or EXPAND_ON_CLICK
   * */
  public int getExpandMode(){
    return expandMode;
  }

  /** Updates the expand mode.
   * @param expandMode legal values are EXPAND_ON_ROLLOVER or EXPAND_ON_CLICK
   * */
  public void setExpandMode(int expandMode){
    int old = this.expandMode;
    this.expandMode = expandMode;
    propertySupport.firePropertyChange(PROPERTY_EXPAND_MODE, old, expandMode);
  }

  /** Returns the rollover trigger delay (in millis)
   *
   * */
  public int getRolloverTriggerDelay(){
    return rolloverTriggerDelay;
  }

  /** Updates the rollover trigger delay
   * @param delay  delay in millis before expanding a Dockable
   */
  public void setRolloverTriggerDelay(int delay){
    int old = this.rolloverTriggerDelay;
    this.rolloverTriggerDelay = delay;
    propertySupport.firePropertyChange(PROPERTY_ROLLOVER_TRIGGER_DELAY, old, delay);
  }

  /** Returns the expansion duration (in millis) */
  public int getExpansionDuration(){
    return expansionDuration;
  }

  /** Updates the expansion duration (time in millis) */
  public void setExpansionDuration(int millis){
    int old = expansionDuration;
    this.expansionDuration = millis;
    propertySupport.firePropertyChange(PROPERTY_EXPANSION_DURATION, old, millis);

  }

  /** Hook for property change notification */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }

  /** Hook for property change notification */
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(propertyName, listener);
  }

  /** Remove a property change notification */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }

  /** Remove a property change notification */
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(propertyName, listener);
  }



}
