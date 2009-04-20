//@LICENSE@

package com.vlsolutions.swing.docking.ws;

/** An exception raised when a workspace operation has gone wrong (can encapsulate 
 * another exception).
 *
 * @author Lilian Chamontin, VLSolutions
 */
public class WorkspaceException extends Exception{
  
  public WorkspaceException(String message) {
    super(message);
  }
  public WorkspaceException(Exception cause) {
    super(cause);
  }
  public WorkspaceException(String message, Exception cause) {
    super(message, cause);
  }
  
}
