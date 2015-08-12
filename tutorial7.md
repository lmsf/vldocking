# Lesson 7 : Working with heavyweight components #


This is the 7th and last part of the VLDocking Framework for Java Swing applications.

This lessons focuses on Heavyweight (AWT) components integration.


Note : this feature is available since VLDocking Framework **1.1** and requires **Java 1.5**

<p align='center'><img src='http://vldocking.googlecode.com/svn/wiki/heavyweight.jpg' /><br>
The JOGL heavyweight sample application (<a href='/jogldockingdemo.jnlp'>launch it </a> with java web start)<br>
<br>
<br>
<h2>Mixing heavyweight components - an overview</h2>

<h3>The problem</h3>


If you are familiar with Swing, you already know it's a bit tricky to have AWT<br>
component included in a Swing application.<br>
<br>
<br>
This is because AWT components are rendered by the os (they are called "heavyweight components"),<br>
whereas Swing components are rendered by software, on top of a single heavyweight component (Swing<br>
component are called "lightweight"). Swing components are independant from the OS as long at it can provide<br>
a top-level heavyweight container.<br>
<br>
<br>
When mixing lightweight and heavyweight components, you are basically trying to mix two<br>
concurrent painting systems on your window. Unfortunately, the OS is always the winner, and<br>
you end up with heavyweight component <b>always on top</b> of Swing components.<br>
<br>
<br>
To avoid this problem, one has to avoid overlapping components, meaning :<br>
<br>
<br>
<ul><li>don't use JInternalFrame<br>
</li><li>dont use a JScrollPane to contain a heavyweight component<br>
</li><li>use all possible workarounds provided by swing (in an effort to support mixing) :<br>
tooltips and popup menus can be made heavyweight by a method call on their factories.</li></ul>


Concerning the Docking Framework, overlapping is limited due to the  relative positionning of<br>
dockables, but it still can occur on the following situations :<br>
<br>
<br>
<ul><li>a Dockable containing a heavyweight component is near a border of the desktop : it will overlap all auto-hide dockables coming from this border.<br>
</li><li>a lightweight Dockable is maximized (top layer of the desktop) :  the heavyweight components on the bottom desktop layer will appear right through it.<br>
</li><li>the custom rendering of drag and gesture (done on a glasspane above the desktop) is intercepted by heavyweight components.</li></ul>


<h3>But why is this feature important ?</h3>

You may ask "why implement such a support in the docking framework when swing is now<br>
as fast as heavyweight components ?"<br>
<br>
Here are some reason why we think it's important :<br>
<br>
<br>
<ul><li>The <b>Open Cascade</b> renderer with java bindings provides a high performance simulation and 2D/3D rendering engine, and is used in industrial contexts.<br>
</li><li>The <b>Java3D</b> applications rely on an OpenGL or DirectX heavyweight binding and are used in many scientific/modeling projects.<br>
</li><li>The <b>JOGL project</b>(and probably the future OpenGL JSR-231 official extension) provides an OpenGL pipeline to Swing applications. It is faster (especially visible when rendering animations) on low end systems when using its heavyweight renderer instead of the Swing one.<br>
</li><li>The <b>JDIC browser component</b> allows you to embed a native browser (much better than JEditorPane !) into a Swing application. Unfortunately, it's a heavyweight component. But include it in a Dockable and you application will rock ! (It's not yet perfect due to some resource management bugs in JDIC, but works fine with community workarounds in most cases)<br>
</li><li>Many <b>legacy applications</b> that have been adapted to be displayed as AWT Canvas / ActiveX are still beeing used(and will probably remain for a long time).</li></ul>


<h3>So is there a workaround ?</h3>

The answer is YES !<br>
<br>
But, you must be aware it will work only beginning <b>with Java 1.5</b> (J2SE 5.0).<br>
<br>
The workaround is based on a new method : <code>Container.setComponentZOrder(Component comp, int order)</code> that has been<br>
added to AWT in jdk 1.5.<br>
<br>
With this method, you can precisely define how heavyweight components overlapping is managed, with<br>
a Z-order priority (lower value = on top). Before that, it was platform-dependent and changing the priority was not specified.<br>
<br>
As Swing components can be included in the Panel class (an AWT container), we just had to ensure the<br>
overlapping cases were managed with a correct Z-order and a proper heavyweight inclusion.<br>
<br>
<b>Drawbacks</b>

The solution has some minor drawbacks : the drag and drop shape painting displays a grey rectangle when appearing<br>
on top of some heavyweight components (for example in the case of JOGL).<br>
<br>
And painting is not generally as smooth as when using pure Swing components, so the animation effects are turned off<br>
when a heavyweight component is used.<br>
<br>
<h2>Adding heavyweight support to the VLDocking Framework</h2>


Now than you know the pros and the cons of using heavyweight components in the docking framework, let's have a look at<br>
how to enable it.<br>
<br>
<h3>A single method call</h3>

It's easy, you just have to place a single method call <b>before using the DockingDesktop</b>, generally in<br>
the main method of your application, or the pre-initialisation part of your application if it's more structured.<br>
<br>
<br>
<pre><code>DockingPreferences.initHeavyWeightUsage();<br>
</code></pre>



And that's it !<br>
<br>
<br>
Under the hood, this method also performs some additional settings : it calls<br>
<blockquote><code>ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false)</code> and {{{<br>
JPopupMenu.setDefaultLightWeightPopupEnabled(false)}}} to use the Swing standard workarounds<br>
(so you don't have to do it yourself).</blockquote>


<h3>Dynamic swichting of heavyweight support</h3>

Please note that you cannot currently switch dynamically between pure lightweight and heavyweight support.<br>
<br>
Call us if you have such a need...<br>
<br>
<br>
<h2>Special workaround and optimization for the JDIC Web Browser component</h2>


This is a new feature starting from VLDocking 2.0.2.<br>
<br>
The JDIC Browser (and may be other native components unknown to us at this time) has a severe limitation (a bug) that<br>
made it very unfriendly for VLDocking : you cannot change its native peer ancestor. We have developped a workaround<br>
for such cases, which works perfectly as long as you follow these simple rules :<br>
<br>
<br>
<ul><li>The JDIC Browser (or any heavyweight component with the same limitations) must be the only native component used, in a single instance.<br>
</li><li>Support for the JDIC Browser starts from release 20050930<br>
</li><li>You have to turn on another DockingPreferences switch</li></ul>


<pre><code>DockingPreferences.setSingleHeavyWeightComponent(true);<br>
</code></pre>



<ul><li>You cannot make the heavyweight Floating (as this option is disabled by default, just think about not turning it on)<br>
</li><li>And finally, you must create the WebBrowser instance with the <code>autodispose</code> flag set to false, and don't<br>
<blockquote>forget to <code>dispose()</code> the browser when your window is closed (to release associated resources).</blockquote></li></ul>




How does it work ? Well it's rather simple : as we know the Browser is the only one heavyweight component around, we don't have to<br>
use our dedicated heavyweight container (used to manage the Z-order of multiple heavywieght dockable) as there will never be<br>
two components competing for beeing on top of the other. Simple, reliable, and easy !<br>
<br>
<br>
You can download our <a href='/download/jdicdemo.zip'> JDIC sample application source code</a> to learn more on (and play with) the<br>
JDIC Browser support.<br>
<br>
<h2>Fixing slow repaint problems (mostly on Linux)</h2>

VLDocking 2.1.4 introduces a new UI Property that can be used to disable background<br>
painting during drag and drop.<br>
<br>
<br>
With this flag set, instead of displaying the Drop shapes above background components with transparency,<br>
a straight grey colored background is shown, reducing the overhead of creating and displaying the<br>
background snapshot.<br>
<br>
<br>
UIManager.put("DragControler.paintBackgroundUnderDragRect", Boolean.FALSE)<br>
<br>
Note : You can also use this property on other systems : it is not specific to Linux (it's just that<br>
the slow repaint is more visible there).<br>
<br>
<hr />

Next : <a href='tutorial8.md'>Lesson 8 customizing the UI</a>