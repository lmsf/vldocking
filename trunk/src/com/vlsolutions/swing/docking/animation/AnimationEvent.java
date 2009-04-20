//@LICENSE@

package com.vlsolutions.swing.docking.animation;

import java.awt.*;

/** An Event describing the state of an animation
 *
 * @see AnimationListener
 * @see ComponentAnimator
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 * */
public class AnimationEvent {
  /** state identifying the beginning of an animation */
  public static final int ANIMATION_START = 0;

  /** state identifying a change in the animation (the component has changed of bounds) */
  public static final int ANIMATION_FRAME = 1;

  /** state identifying the end of an animation */
  public static final int ANIMATION_END = 2;

  private Component source;
  private int state;

  /** Event Constructor.
   * @param source   the animated component
   * @param state    the state of animation (ANIMATION_START , ANIMATION_FRAME or ANIMATION_END)
   * */
  public AnimationEvent(Component source, int state) {
     this.source = source;
     this.state = state;
  }

  /** returns the state of the animation (ANIMATION_START, ANIMATION_FRAME  or ANIMATION_END) */
  public int getState(){
     return state;
  }

  /** returns the source of animation */
  public Object getSource(){
    return source;
  }


}
