# Building Deb Package

## erase last build if there is a last build (build from a linux based device with dpkg installed)

- sudo rm -f mypackage_0.0-1_armhf.deb  # remove the old deb package

### change the DEBIAN/control directory contents (version and info) as necessary

- sudo dpkg-deb --build --root-owner-group mypackage_0.0-1_armhf
- sudo apt install ./mypackage_0.0-1_armhf.deb -y
- sudo systemctl status mypackage  # should be running
