//@LICENSE@

package com.vlsolutions.swing.docking;
import java.awt.Window;


/** An abstract factory used by the <code>DockingDesktop</code> to create instance
 * of Dockable containers.
 * <p>
 * This factory enables API Extenders to change part of the behaviour on the DockingDesktop
 * (mostly about look and feel issues) without modifying the code of that central class.
 *
 * @see DockableContainer
 * @see DefaultDockableContainerFactory : the default implementation
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.1
 * @update 2007/01/08 Lilian Chamontin : updated to declare a new titlebar factory method
 * */
public abstract class DockableContainerFactory {

  private static DockableContainerFactory factory;
  
  /** Constant used to describe the type of usage a SingleDockableContainer is for*/
  public static final int PARENT_DESKTOP = 0;
  /** Constant used to describe the type of usage a SingleDockableContainer is for*/
  public static final int PARENT_TABBED_CONTAINER = 1;
  /** Constant used to describe the type of usage a SingleDockableContainer is for*/
  public static final int PARENT_SPLIT_CONTAINER = 2;

  /** Constant used to describe the type of usage a SingleDockableContainer is for*/
  public static final int PARENT_DETACHED_WINDOW = 3;
  

  /** This method is called by the DockingDesktop whenever a dockable is inserted
   * in its containment hierachy.
   *
   */
  public abstract SingleDockableContainer createDockableContainer(Dockable dockable, int parentType);
  
  /** @deprecated use the other createDockableContainer method ({@link #createDockableContainer(Dockable, int)} 
   */
  public SingleDockableContainer createDockableContainer(Dockable dockable, boolean c){
    return createDockableContainer(dockable, PARENT_SPLIT_CONTAINER);
  }

  /** This method is called when a tab insertion is requested by the DockingDesktop. */
  public abstract TabbedDockableContainer createTabbedDockableContainer();

  /** This method is called when a dockable is detached from the DockingDesktop and put 
   * in the FLOATING state. 
   *<p>
   * The floating container must be an instanceof Dialog or Window.
   */
  public abstract FloatingDockableContainer createFloatingDockableContainer(Window owner);
  
  /** This method is invoked when a dockable container needs to install a title bar.
   *<p> 
   * This method is used by the standard implementation of Docking (DockView, etc) but is optional 
   * for other implementations, as currently the titlebar isn't specified as a core component of 
   * the docking framework (i.e. it is not an interface).
   *@since 2.1.3
   */
  public abstract DockViewTitleBar createTitleBar();
  
  /** Returns the current factory.
   * <p> it no factory has been provided, it will
   * fall back on DefaultDockableContainerFactory
   */
  public static DockableContainerFactory getFactory(){
    if (factory == null){
      factory = new DefaultDockableContainerFactory();
    }
    return factory;
  }

  /** Changes the factory to be used by DockingDesktop.
   * <p>
   * Note that this change is not propagated to already docked components.
   * */
  public static void setFactory(DockableContainerFactory f){
     factory = f;
  }
  
  
}
