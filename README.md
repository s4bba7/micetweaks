# micetweaks v0.9
Mouse/touchpad helper and detector for Linux.

When you're using multiple pointers you can set individual configs (speed and deacceleration) to them. Must have especially when using KDE, where you can set mouse acceleration only.
It detects when device is plugged into the USB port and sets individual config to it.

Dependency: xinput package.

Runs in two modes:
SETUP - detects plugged in devices and asks about setting it's speed and deacceleration. Start the application with "setup" argument. This is the first step which you have to do!
NORMAL - detects plugged in device and applying it's config. Start the application without arguments.

Touchpads cannot be detected, so you have to get it's name by invoking "xinput" command in system console and paste it into "startup.conf". Proper format is:
Device name|speed|deacceleration
Speed and deacceleration may be assign in floating point number. Recommended values are 1.1 and 2.0
