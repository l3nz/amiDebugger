amiDebugger
===========

A simple web-based CLI-like debugger for the Asterisk Manager Interface.

Writing applications that use the AMI is not always easy - if the solution
is still unclear and you need to explore what Asterisk does under different 
conditions it may take a while to write scripts that simulate the required interactions.

The AMI is also extremely verbose and asynchronous - so you get a ton of logs
out and you have to filter them to access the information you are looking for.

Being tasked with writing new logic for the WombatDialer, I found myself at loss
trying to fins a sort of interactive shell for the AMI. At the same time, I had 
been looking at Akka for a while and wanted to try it in the context of a web 
application. So the amiDebugger was born.

Features
--------

* Persistent AMI connection in the context of a traditional, transactional Java 
  webapp thanks to Akka
* Use a text box to send AMI commands to Asterisk
* Graphical coloring of different events - inputs, responses, events are displayed 
  differently
* Real-time, live filtering of blocks that contain a certain substring - so you can e.g. follow
  a channel, an unique-id or a multi-block response.
* Buttons for common AMI commands - showing channels, queues, parking slots, system
  status and more
* Button for common templates - like Originate, Bridge, Hangup and more
* Buttons for Asterisk help texts (handy when you don't remember a command)
* By clicking on a "Channel" in the output, it is copied as-is in the command area - so e.g. 
  to hang up a channel, you click on the Hangup button, click on the channel and
  send the command
* Feel free to fork and modify

New features and bug reports
----------------------------

We're on GitHub. Use the bug tracker GitHub offers.


Installation - easy
-------------------

Download and install Tomcat.

Download the webapp from http://www.loway.ch/dl/amiDebugger.war and copy it into the
webapp directory.


Developers
----------

Create a web developement project and add the webroot and the classes.

Run gradle so that it downloads the required libs (it may fail at the moment, never mind).

Run the project.


