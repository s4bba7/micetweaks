# micetweaks v0.9
Mouse/touchpad automatic detector and sensitivity setter for Linux.

Application detects the moment when device is plugged into the USB port and sets individual config (speed and deacceleration) to it.  
Of course it may be used when using one mouse only.  
"Must have app" especially when using <B>KDE</B>, where you can set mouse acceleration only.  

Runs in two modes:  
SETUP - detects plugged in devices and asks about setting it's speed and deacceleration. Start the application with "setup" argument (java -jar micetweaks.jar setup). All values are stored in "hotplug.conf" file. <B>This is the first step which you have to do! </B>   
NORMAL - detects plugged in device and applies it's config. Start the application without arguments (java -jar micetweaks.jar).

Touchpads installed in laptops cannot be detected, so you have to get it's name by invoking "xinput" command in system console and paste it into "startup.conf", which is invoked at application startup. Proper format is:  
Device name|speed|deacceleration  
Example: ETPS/2 Elantech Touchpad|1.1|2.0  
Speed and deacceleration may be assign in floating point number. Recommended values are 1.1 and 2.0

Used library: usb4java -> https://github.com/usb4java/ written by Klaus Reimer.  
Dependency: xinput package.
