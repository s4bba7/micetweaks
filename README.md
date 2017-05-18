# micetweaks v1.3
Mouse and touchpad sensitivity setter for Linux.

Download: http://gasionet.pl/dlmicetweaks.php

![micetweaks](http://i.imgur.com/sKxkn9e.png)

Application detects the moment when device is plugged into the USB port and sets individual config (speed and acceleration) to it.  
"Must have app" especially when using <B>KDE</B>, where you can set mouse acceleration only. 

At the beginning it detects all connected pointers (like mice and touchpads) and shows window with those devices. There you can adjust it's speed and acceleration.
Every change is activated immediately. To save configuration just click "Save config" button.

You may hide the window into system tray (if supported by operation system) by clicking on the tray icon. Click again
 to show it back on.

At the first run program shows greetings helper. From now on it runs hidden in system tray (if supported).

Dependency: libinput, xinput package and udevadm (should be already compiled into your system's kernel) installed in 
your operating system.

Tips:
If your pointer device is not listed you need to activate "libinput" driver usually by removing one file from 
/etc/X11/xorg.conf.d/XX-synaptics or xx-elantech and rebooting system.
