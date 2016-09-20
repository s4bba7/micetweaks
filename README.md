# micetweaks v0.9
Mouse/touchpad helper and detector for Linux.

When you're using multiple mice you can set individual configs (speed and deacceleration) to them. Must have especially when using <B>KDE</B>, where you can set mouse acceleration only.  
It detects when device is plugged into the USB port and sets individual config to it.  
Of course it may be used when using one mouse only.

Runs in two modes:  
SETUP - detects plugged in devices and asks about setting it's speed and deacceleration. Start the application with "setup" argument. All values are stored in "hotplug.conf" file. <B>This is the first step which you have to do! </B>   
NORMAL - detects plugged in device and applying it's config. Start the application without arguments.

Touchpads cannot be detected, so you have to get it's name by invoking "xinput" command in system console and paste it into "startup.conf". Proper format is:  
Device name|speed|deacceleration  
Example: ETPS/2 Elantech Touchpad|1.1|2.0  
Speed and deacceleration may be assign in floating point number. Recommended values are 1.1 and 2.0

Dependency: xinput package.
Used library: usb4java -> https://github.com/usb4java/ written by Klaus Reimer.
