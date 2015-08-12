# Lesson 2 : Customizing the layout, display and docking behaviour #

This is the second part of the VLDocking Tutorial for Java Swing applications.

In this second lesson, you will learn how to modifiy the contents and the behaviour of
the containers of your dockable components.



## Things to remember ##



This lesson assumes you have read the two first parts of the tutorial and that you are
now familiar with the concepts of :



  * a Dockable.
  * the DockingDesktop
  * SingleDockableContainer and TabbedDockableContainer
  * and how to layout your components into their desktop

If you don't feel enough confident about those concepts, please read again the previous lessons.

## The `DockKey` object ##

The `DockKey` object provides a **unique key** to identify a Dockable during the loading/saving
process via its ~~dockName~~ `key` property.

You have also learned from the previous lesson that the DockKey was the object used to
customize the DockableContainer of a Dockable.


Let's see how it works.

### Bound properties ###



If you have looked at the Javadoc of the DockKey class, you already know that
a DockKey is a specialized class following the JavaBean pattern.


You can access a set of properties like `name`, `tooltip`,
`isCloseEnabled` and so on, with getters and setters.


You can also attach a `java.beans.PropertyListener` to be notified when a property
is updated.


This is exactly what the Framework does and how it dynamically follows key updates.


Now, we can have a look at the different categories of properties :


  * Visual properties
  * Behaviour properties



### Visual properties ###


Note : keep in mind that `SingleDockableContainer` and `TabbedDockableContainer` are
interfaces : you can choose to use different implementations than the default one, with visual
settings that could be different from those described below.


Default visual properties are listed here :


<table cellpadding='4' border='1'>
<blockquote><thead> <tr><th width='200'>Property</th><th>Type</th><th>Effect</th></tr></thead>
<tbody><tr><td><code>name</code></td><td>String</td><td>The label displayed on the title bar</td></tr>
<tr><td><code>tooltip</code></td><td>String</td><td>The tooltip associated to the title bar</td></tr>
<tr><td><code>icon</code></td><td>16x16 pixels Icon</td><td>The icon used by the title bar (there is no default<br>
<blockquote>icon : if set to null, no icon will be drawn)</td></tr>
</tbody></table></blockquote></blockquote>

#### Example. ####

The following code will add a title, a tooltip and and icon to the `MyTextEditor` component.

```
    class MyTextEditor extends JPanel implements Dockable {

        DockKey key = new DockKey("textEditor");

        JTextArea textArea = new JTextArea("A Text Area");
        public MyTextEditor() {
            setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(textArea);
            jsp.setPreferredSize(new Dimension(200, 400));
            add(jsp, BorderLayout.CENTER);

            *key.setName("The Text Area");
            key.setTooltip("This is the text area tooltip");
            key.setIcon(new ImageIcon(getClass().getResource("document16.gif")));
          *

        }
        public DockKey getDockKey(){
            return key;
        }
        public Component getComponent(){
            return this;
        }
    }
```


And here is a screenshot of the result :
> ![http://vldocking.googlecode.com/svn/wiki/frame7.jpg](http://vldocking.googlecode.com/svn/wiki/frame7.jpg)



#### Visual Properties and Threads ####


Changes are propagated dynamically, you can for example replace
an icon by another to reflect a change in the component (a good example is to
have an "updated" version of a document icon showing that the document has been
updated and needs to be saved).


Visual properties, unlike Behaviour properties, are thread-safe : they can be
changed on any application thread.

### Behaviour properties ###



The following boolean properties have and effect on the operations allowed (and managed) by the DockingDesktop.


<table border='1'>
<blockquote><thead><tr><th width='200'>Property</th><th>Effect</th></tr></thead>
<tbody><tr><td><code>isCloseEnabled</code></td><td>The dockable can be closed (removed from the desktop).</blockquote>

if <code>false</code>, the "close" button will be removed from the title bar</td></tr>
<blockquote><tr><td><code>isAutoHideEnabled</code></td><td>The dockable can be iconified</blockquote>

if <code>false</code>, the "hide" button will be removed from the title bar</td></tr>
<blockquote><tr><td><code>isMaximizeEnabled</code></td><td>The dockable can be maximized (full size of the desktop)</blockquote>

if <code>false</code>, the "maximize" button will be removed from the title bar </td></tr>
<blockquote><tr><td><code>isFloatingEnabled</code></td><td>
The dockable can be detached from the desktop and displayed in its own palette window (default value is <b>false</b>) </td></tr>
</tbody>
</table></blockquote>

#### Example. ####


The following code will disable the close and auto-hide features of our MyTextEditor component :

```
  class MyTextEditor extends JPanel implements Dockable {

        DockKey key = new DockKey("textEditor");

        JTextArea textArea = new JTextArea("A Text Area");
        public MyTextEditor() {
            setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(textArea);
            jsp.setPreferredSize(new Dimension(200, 400));
            add(jsp, BorderLayout.CENTER);

            key.setName("The Text Area");
            key.setToolTip("This is the text area tooltip");
            key.setIcon(new ImageIcon(getClass().getResource("textArea16.gif")));

            *// turn off autohide and close features
            key.setCloseEnabled(false);
            key.setAutoHideEnabled(false);
            *

        }
        public DockKey getDockKey(){
            return key;
        }
        public Component getComponent(){
            return this;
        }
    }
```


The following screenshot will show you the effects or this code, you can verify that
the close and autohide buttons have been removed from the text area's title bar.

> ![http://vldocking.googlecode.com/svn/wiki/frame8.jpg](http://vldocking.googlecode.com/svn/wiki/frame8.jpg)



#### Behaviour Properties and Threads ####


Changes are, as for visual properties, propagated dynamically.<br>
But, <b>as they might affect the AWT hierarchy</b> they should not be considered thread-safe : <br>
Once the DockingDesktop is <i>realized</i> (visible on screen),<br>
you should restrict the change to the <b>Event Dispatch Thread</b> (the thread used<br>
by AWT/Swing to deliver UI events).<br>
<br>
<h2>Adjusting the size of the dockables</h2>



Until now, you have learned how to <code>split</code> or <code>createTab</code> dockables,<br>
but there wasn't any indication of their size requirements.<br>
<br>
To understand how resizing works, you must know first a little bit more about DockingDesktop internals.<br>
<br>
<h3>How are containers laid out inside the DockingDesktop</h3>


The DockingDesktop displays its dockable containers (remember the two kinds : Single/Tabbed-DockableContainer)<br>
inside a specialized container : the <code>DockingPanel</code>.<br>
<br>
<br>
This DockingPanel (one instance per DockingDesktop) can be recursively subdivided in two<br>
when a Dockable is added with the <code>split</code> method. The container realizing the<br>
split is a subclass of <code>JSplitPane</code> named <code>com.vlsolutions.swing.docking.SplitContainer</code>.<br>
SplitContainer has and enhanced look and feel and a better resizing behaviour.<br>
<br>
<br>
You never access these internal SplitContainers, the main reason beeing that they are<br>
the current layout implementation, and that another layout manager could be used in a future<br>
version to replace them (and thus would remove the need of nesting of containers).<br>
<br>
<br>
The following diagram shows how the SplitContainer hierarchy associated to our example.<br>
<br>
<blockquote><img src='http://vldocking.googlecode.com/svn/wiki/splitcontainers.jpg' /></blockquote>



<blockquote><h3>Changing the width or height of a dockable</h3></blockquote>


This can be done at any time, you just have to use the two dedicated methods of<br>
<code>DockingDesktop</code> :<br>
<br>
<br>
<ul><li>setDockableWidth(Dockable dockable, double width)` to adjust<br>
the width of a dockable<br>
</li><li>setDockableHeight(Dockable dockable, double height)` to adjust<br>
the height of a dockable</li></ul>




Those methods have two arguments :<br>
<br>
<blockquote>- a <code>Dockable</code>, contained in a SingleDockableContainer or a TabbedDockableContainer.</blockquote>

<blockquote>- a <code>double</code> value, between 0 and 1, indicating the proportion of the dockable container<br>
into its parent SplitContainer.</blockquote>


<b>Important</b> : remember that the proportional width or height is relative to a splitcontainer, which<br>
can be smaller than the DockingDesktop, especially when many dockables are shown.<br>
<br>
<br>
<br>
<h3>What if the SplitContainer is divided HORIZONTALLY and I change the height of my dockable ?</h3>



It's still possible, the resizing will not occur on this parent SplitContainer,<br>
but on the first ancestor encountered with a VERTICAL orientation.<br>
<br>
<br>
Of course, it's the same rule for changing the width of a dockable contained<br>
in a VERTICALLY oriented SplitContainer.<br>
<br>
<h3>Resizing affects more than one dockable</h3>


This shoud be evident : as our Desktop is divided in SplitContainers, changing<br>
the width or height of a dockable will also change the opposite component.<br>
<br>
<br>
And, as SplitContainers can be nested, the effect will be propagated to the nesting<br>
hierarchy.<br>
<br>
<br>
Example : if you have a row of dockables, changing the height of one of them<br>
will be propagated to the others.<br>
<br>
<br>
<h3>Adjusting the resize weight of dockables</h3>

The DockKey object has also a <code>setResizeWeight(float weight)</code> affecting its<br>
<b>resizeWeight</b> property. Valid values range from 0.0f (do not resize) to 1.0f (be the most resized).<br>
<br>
<br>
Resizing weight is used when the DockingDesktop (usually through its parent window) is made<br>
bigger or smaller. This resizing affects the internal layout and the following rules are<br>
applied to select the dockable which will be resized :<br>
<br>
<br>
<ul><li>The resizeWeight is propagated into the splitting hierarchy,  giving a maximum resize<br>
weight to both parts of a SplitContainer<br>
</li><li>when both resizeWeights are 0, the resizing is shared by both sides<br>
</li><li>when a resizeWeight is 0 and the other is positive, then that side takes all the resizing<br>
</li><li>when both resizeWeights are positive, a ratio is computed and the splits resized accordingly.</li></ul>




For example, when creating a multiple document application (like a web browser with tabbed browing and some<br>
utility dockable around), you can set the resizeWeight of the documents to 1, and the other weights to 0.<br>
In that case, the utility dockables will remain unchanged when resizing the window, and only the documents<br>
will grow or shrink.<br>
<br>
<h2>Grouping dockables</h2>


Grouping is a feature that helps you to control <b>which dockable can be included in the same<br>
tabbed container</b>.<br>
<br>
For example, when creating a MTDI (multiple tabbed document interface), you want to have<br>
the following configuration :<br>
<br>
<blockquote><img src='http://vldocking.googlecode.com/svn/wiki/mtdi.jpg' /></blockquote>

<blockquote><i>A Multiple Tabbed Document Interface</i></blockquote>



And, of course, you don't want to have the "property editor" tabbed in the sames<br>
tabbed container than the one of your "Documents", but you would also prefer to let<br>
the user group the 4 helper dockables in the same tab if he wants so.<br>
<br>
To do this, you just have to declare two <b>DockGroup*s :</b>

<ul><li>The "documents" group<br>
</li><li>The "helper dockables" group</li></ul>




Then, you will add this information to your <b>DockKey*s as in the follwing example :</b>

<pre><code>DockGroup documentsGroup = new DockGroup("documents");<br>
DockGroup helperGroup = new DockGroup("helper dockables");<br>
<br>
// set the documentsGroup to all "documents" DockKeys <br>
DockKey documentKey1 = new DockKey("document_1", "Document 1");<br>
DockKey documentKey2 = new DockKey("document_2", "Document 2");<br>
documentKey1.*setDockGroup(documentGroup)*;<br>
documentKey2.setDockGroup(documentGroup);<br>
<br>
// set the helperGroup to all other DockKeys<br>
DockKey propertyEditorKey = ...<br>
propertyEditorKey.*setDockGroup(helperGroup)*;<br>
DockKey historyKey = ...<br>
historyKey.setDockGroup(helperGroup);<br>
...<br>
<br>
</code></pre>


Easy isn't it ?<br>
<br>
<br>
Note : this feature is more complex than that : you can create hierarchies of<br>
DockGroups to allow certain combinations of dockables (for example, if you want to<br>
have two or three sets on non-mixable documents). You can have more information in<br>
the DockGroup's javadoc.<br>
<br>
<h2>Creating custom pop-up menus on title bars</h2>


Another interesting feature of VLDocking is the customization of title bars<br>
and tabs.<br>
<br>
<br>
With that feature, you will be able for example to add a "close all documents" pop-up menu<br>
to your tabbed documents, or some specific menus ("save", "save as...", "cvs...").<br>
<br>
<br>
To do that, you will have to register a special class to your DockKeys using :<br>
the <code> public void setActioncustomizer(DockableActionCustomizer actionCustomizer)</code>.<br>
<br>
<br>
The <b>DockableActionCustomizer</b> is an abstract class you will have to inherit from<br>
in order to add custom menus.<br>
<br>
<br>
Here is an example of such a customizer :<br>
<br>
<pre><code>// get predefined closeAll and closeAllOther actions from helper class<br>
Action closeAllInTab = <br>
   TabbedContainerActions.createCloseAllAction(dockable, desk);<br>
<br>
Action closeAllOtherInTab = <br>
   TabbedContainerActions.createCloseAllOtherAction(dockable, desk);<br>
        <br>
// create an action customizer<br>
DockableActionCustomizer customizer = new DockableActionCustomizer(){<br>
     public void visitSingleDockableTitleBarPopUp(JPopupMenu popUpMenu, Dockable dockable){<br>
         // this is for title bars<br>
	 popUpMenu.add(new JMenuItem("test"));<br>
     }<br>
     <br>
     public void visitTabSelectorPopUp(JPopupMenu popUpMenu, Dockable dockable){<br>
	 // and this is for tabs...<br>
         popUpMenu.add(new JMenuItem(closeAllInTab));<br>
         popUpMenu.add(new JMenuItem(closeAllOtherInTab));<br>
     }<br>
};<br>
<br>
// activate the title bar and tabs features<br>
customizer.setSingleDockableTitleBarPopUpCustomizer(true);<br>
customizer.setTabSelectorPopUpCustomizer(true);<br>
<br>
// register to a DockKey<br>
dockable.getDockKey().setActionCustomizer(customizer);<br>
</code></pre>



From now on, whenever a right click will occur on the title bar of the dockable,<br>
a "test" pop-up menu will appear (added at the end of the system-wide actions of<br>
the pop-up menu).<br>
<br>
<br>
And when this dockable will be added to a TabbedDockableContainer, a right click<br>
on its tab will show the "close all documents" and "close all other documents" menu<br>
entries.<br>
<br>
<br>
<b>Note :</b> The visitXXX methods may be called many times (the current implementation<br>
does not cache the pop-up menu : it is created and discarded for a specific right mouse click).<br>
So it is wise not to declare listeners from inside these methods, or to perform long tasks<br>
as they may affect the behaviour (or memory usage) of your application.<br>
<br>
<br>
<hr />

Next : <a href='tutorial3.md'>Lesson 3 - Listening to dockable changes</a>
