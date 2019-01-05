# micetweaks v1.3
Mouse and touchpad sensitivity setter for Linux.

Download: http://gasionet.pl/app/micetweaks.tar.gz

![micetweaks](http://i.imgur.com/sKxkn9e.png)

## Introduction
Application detects the moment when device is plugged into the USB port and sets individual config (speed and acceleration) to it.  
"Must have app" especially when using <B>KDE</B>, where you can set mouse acceleration only. 

At the beginning it detects all connected pointers (like mice and touchpads) and shows window with those devices. There you can adjust it's speed and set acceleration on or off.
Every change is activated immediately.

## Configuration
### 1. Save configuration
click menu OPTIONS and choose SAVE CONFIG item.

### 2. Change theme
click menu OPTIONS, choose THEMES and then click on WHITE ICONS or BLACK ICONS.

### 3. Show/hide the window into system tray (if supported by operation system)
click on the tray icon.

At the first run program shows greetings helper. From now on it runs hidden in system tray (if supported).

## Dependency
libinput, xinput package and udevadm (should be already compiled into your system's kernel) installed in 
your operating system.

## Tips
If your pointer device is not listed you need to activate "libinput" driver usually by removing one file from 
/etc/X11/xorg.conf.d/XX-synaptics or xx-elantech and rebooting system.
