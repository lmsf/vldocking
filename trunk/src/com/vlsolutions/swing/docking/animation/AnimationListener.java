//@LICENSE@

package com.vlsolutions.swing.docking.animation;

/** The AnimationListener interface is used to notify listeners about the state
 * of an animation process (animation start and end).
 * <p>
 * This interface is used by ComponentAnimator, generally to block
 * events management during animation phase.
 *
 * @see ComponentAnimator
 * @see AnimationEvent
 *
 * @author Lilian Chamontin, vlsolutions.
 * @version 1.0
 * */
public interface AnimationListener {

  /** This method is invoked when the animation state change.
   * <P> Changes are relative to animation start, sequence(frame) and end.
   * */
  public void animation(AnimationEvent e);

}
