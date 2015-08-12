# Lesson 3 : Listening to dockable events #



This is the third part of the VLDocking Framework tutorial for Java Swing applications.


In this lesson, you will learn about the docking event model and about writing
event listeners.


## The docking event model ##



The VLDocking Framework works with an event model : to be notified of changes, you
have to add some listeners to the DockingDesktop, not to override specialized methods.


This way, there should be no need for you to subclass DockingDesktop, and you will reduce
the coupling between listening components and their source.

### Package `com.vlsolutions.swing.docking.event` ###


This package contains events and listeners class, so don't forget to import
it in your listener classes.

### Categories of events ###




There are 2 kinds of events, dedicated to different purposes :


  * Events that are triggered when an action is performed on a Dockable (or just before) :<br>
<blockquote><img src='http://vldocking.googlecode.com/svn/wiki/state_changes.png' /><br>
These events are usefull to provide feedback on dockable closing (the "are you sure ?" message)<br>
or to keep track of components visibility and position.</blockquote></li></ul>

  * `DockEvent` events are triggered during the drag and drop process

> of a dockable component. These lower-level events are used to perform docking
> and are of no use for normal application programming (but they are the key to
> the framework extension).



## Listening to Dockable State changes and Docking Actions ##



  * ote : As of VLDocking 2.1, a new set of classes has been introduced to
> improve the event model (they are called DockingAction Events). The DockableStateChange
> classes can still be used if you are only interested in high-level changes 


There are two types of DockableStateChange events :


  * Events triggered before a state change are instances of
`DockableStateWillChangeEvent`.

Those are **vetoable**, meaning you can cancel the state change process if a condition of
your own is not fulfilled.
  * Notification events, triggered after a state change are instances of `DockableStateChangeEvent`.



### `DockableStateWillChangeEvent` events ###


To be notified **before** a state change occur, you have to implement the
`DockableStateWillChangeListener` interface on an existing class, or
as on the following example, on an anonymous class :


```
  DockingDesktop desk = ...

  DockableStateWillChangeListener listener = new DockableStateWillChangeListener(){
     public void dockableStateWillChange(DockableStateWillChangeEvent event) {
        // event processing
     }
  };
  desk.addDockableStateWillChangeListener(listener);
```




The DockableStateWillChangeEvent object contains fields that inform you on
the dockable which is changing, on what its current state is, and on what
its next state will be if not vetoed.


Look at this example, where we cancel a CLOSE operation if it occurs on the editorPanel
dockable.



```
  DockingDesktop desk = ...

  DockableStateWillChangeListener listener = new DockableStateWillChangeListener(){
     public void dockableStateWillChange(DockableStateWillChangeEvent event) {
        DockableState current = event.getCurrentState();
        if (current.getDockable() == editorPanel){
            if (event.getFutureState().isClosed()){

                // we are facing a closing of the editorPanel

                event.cancel(); // refuse it, always
            }
        }
     }
  };
  desk.addDockableStateWillChangeListener(listener);
```



### DockableStateChangeEvent}}} events ###


These events are triggered **after** the state change, so you cannot veto them.


You listen to them by implementing a `DockableStateChangeListener` which is
different from `DockableState*Will*ChangeListener`.



They are usefull to keep track of the state of your dockables, especially
in the following situations :


  * You want to "dispose" your dockable and GC its resources : a closed dockable
is just "not visible", but still referenced by the desktop (it might show up again).
As there is currently no "`setDefaultCloseOperation()`" on the dockkey
you have to properly code the removal of the dockable after it is closed :



```
  DockingDesktop desk = ...

  DockableStateChangeListener listener = new DockableStateChangeListener(){
     public void dockableStateChange(DockableStateChangeEvent event) {
        DockableState newState = event.getNewState();
        if (newState.isClosed()){
           // the dockable has been closed
           desk.unregisterDockable(newState.getDockable()); // forget about it !
        }
     }
  };
  desk.addDockableStateChangeListener(listener);
```


  * You want to know when a dockable becomes visible/not visible to allocate
resources only when needed (expecially if the dockable is a Heavyweight AWT component).
  * You want to show a dialog to let the user select the dockables
to show and those to close, by a click on a checkbox, and want to update the
visible dockables list dynamically .


But keep it mind that there is already a customizable dialog included in the
framework : `com.vlsolutions.swing.docking.DockingSelectorDialog`.


> ### The DockingAction Events ###


As of VLDocking 2.1, it is possible to keep track of actions performed on dockables.
Most of these actions are _vetoable_, and this method is for example used to
anchor dockable (by rejecting actions that would break the anchoring).


Every action that can be performed on a dockable (or a set of dockables) has its
corresponding class in the event package.


Here are the main classes introduced :
<table border='1'><tr><th>Class</th><th> Usage </th></tr>
<tr><td>DockingActionAddDockableEvent</td><td>Event produced when adding the first dockable to a top-level container</td></tr>
<tr><td>DockingActionCloseEvent</td><td>Produced when the close button is used on a dockable</td></tr>
<tr><td>DockingActionCreateTabEvent</td><td>Produced when a dockable is added as a new tab</td></tr>
<tr><td>DockingActionSimpleStageChangeEvent</td><td>Produced by different action that don't involve parameters, like 'detach', 'auto-hide'</td></tr>
<tr><td>DockingActionSplitComponentEvent</td><td>Produced when a dockable is moved to another split position (drag and drop)</td></tr>
<tr><td>DockingActionSplitDockableEvent</td><td>Produced when a dockable is added besides another one (split() API call)</td></tr>
</table>


To listen to these events, you just have to register a DockingActionListener to your
DockingDesktop with the `addDockingActionListener()` method.


DockingActionListener defines two methods :
`public boolean acceptDockingAction(DockingActionEvent event)`
<br>and <br>
<code>public void dockingActionPerformed(DockingActionEvent event)</code>


The <b>accept</b> method is vetoable (returns a boolean) and is invoked before the action<br>
is performed, to give you a chance to react to it before.<br>
<br>
<br>
<br>
The <b>dockingActionPerformed</b> method is called after the action, and gives you a means to<br>
track the dockable state or position change<br>
<br>
<br>
<br>
<h2>Listening to Dockable selections</h2>


Some application need to know when a dockable is selected (for example, to show a different<br>
set of icons on their toolbar). By selection, we mean "the title bar is highlighted" of, if you prefer,<br>
a component inside the dockable has obtained the keyboard focus (which is exactly the same... as<br>
the framework tracks focus events to highlight title bars).<br>
<br>
<br>
So here are the dedicated events and listeners :<br>
<br>
<table border='1'><thead><tr><th>Class</th><th>Description</th></tr></thead>
<tbody><tr><td>DockableSelectionEvent</td><td>Describes the dockable that has just been selected</td></tr>
<tr><td>DockableSelectionListener</td><td>Listener invoked on selection change</td></tr>
</tbody></table>


To add a listener to the desktop, just use the followind methods :<br>
<br>
<table border='1'><thead><tr><th>Method</th><th>Description</th></tr></thead>
<tbody><tr><td>DockingDesktop.addDockableSelectionListener(DockableSelectionListener listener)</td>
<td>Adds a listener for selection changes</td></tr>
<tr><td>DockingDesktop.addDockableSelectionListener(DockableSelectionListener listener)</td>
<td>Removes the listener</td></tr>
</tbody></table>


<b>Note :</b> There is also another way to know which dockable is currently selected : just ask the desktop :<br>
<br>
<br>
<pre><code> DockingDesktop desk = ...<br>
 Dockable selected = desk.getSelectedDockable(); // may return null<br>
</code></pre>



<h2>Listening to Drag and Drop docking events</h2>



This section is for API extenders : developpers that want to add docking features<br>
to the API. <br>
If you (most probably) are not of that kind, you can skip the end of<br>
the lesson and go to the <a href='tutorial4'>next one</a>.<br>
<br>
<h3>The DockEvent classes</h3>


There are 3 classes of events : one ancestor class <code>DockEvent</code>,<br>
and two specialized subclasses <code>DockDragEvent</code> and <code>DockDropEvent</code>.<br>
<br>
<br>
Those events are related to two other classes :<br>
<br>
<br>
<ul><li>{{DockableDragSource}}} : an interface implemented by a UI component<br>
used to drag a dockable (for example, the default title bar of a <code>SingleDockableContainer</code>
is a DockableDragSource).<br>
DockableDragSource contains methods to get the associated <code>Dockable </code>
and to be notified of a drag gesture start.<br>
</li></ul><ul><li>{{DockDropReceiver}}} : an inteface implemented by a UI component<br>
used to drop a dockable (for example, the whole content of a <code>SingleDockableContainer</code>
is a DockDropReceiver).<br>
A DockDropReceiver is responsible for accepting (and processing) a "drop" of a dockable.</li></ul>



<h4>DockEvent</h4>


The <code>DockEvent</code> class contains informations about the <code>DockableDragSource</code>
of the event, and the MouseEvent associated to the gesture (pointer coordinates are converted<br>
to be used in the target component coordinates system).<br>
<br>
<h4>DockDragEvent</h4>


The <code>DockDragEvent</code> event is a subclass of <code>DockEvent</code> which<br>
is transmitted to <code>DockDropReceiver</code> during a drag gesture.<br>
<br>
<br>
When the drag gesture begins over a <code>DockableDragSource</code>, the DockingDesktop<br>
switches to "Drag and Dock" mode :<br>
<br>
<br>
<ul><li>When mouse pointer is over a component implementing DockDropReceiver, a DockDragEvent<br>
is triggered and transmitted to this receiver. The receiver can <code>acceptDrag()</code>,<br>
<code>rejectDrag()</code>, or <code>delegateDrag()</code> (tell the DockingDesktop to look<br>
up in the containment hierarchy for another receiver).</li></ul>

<ul><li>To accept the drag, the DockDropReceiver invokes <code>dragEvent.acceptDrag(Shape outline)</code>
on the event, providing a shape that will be used to give a visual feedback to<br>
the user (usually, a rectangular shape is enough).</li></ul>

<ul><li>The DockingDesktop displays the shape on its glass pane, and the user can<br>
complete the drop (releasing the mouse button) or continue the drag gesture over<br>
another place in the same container (in that case, the same DockDropReceiver can return different shapes<br>
according to the mouse location) or over another DockDropReceiver.</li></ul>



<h4>DockDropEvent</h4>


The <code>DockDropEvent</code> is a subclass of DockEvent which is transmitted<br>
to a <code>DockDropReceiver</code> at the end of a drag gesture.<br>
<br>
<br>
The event is triggered only if the preceeding DockDragEvent was accepted by the<br>
same DockDropReceiver.<br>
<br>
<br>
The DockDropReceiver must process the drop, usually by invoking <code>split</code> or<br>
<code>createTab </code> methods on the DockingDesktop.<br>
<br>
<hr />

Next <a href='tutorial4.md'>tutorial4</a>