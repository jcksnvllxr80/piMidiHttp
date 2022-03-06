# run pi-midi-http with config files in directory

## with service manager

> $ sudo service midi_http start

OR

## straight-up

> $ java -Dlog4j.configuration=file:/etc/pi-midi-http/conf/log4j.properties -jar pi-midi-http.jar --spring.config.location=file:/etc/pi-midi-http/conf/application.yml

## Building Deb Package

## erase last build if there is a last build (build from a linux based device with dpkg installed)

- sudo rm -f mypackage_0.0-1_armhf.deb  # remove the old deb package

### change the DEBIAN/control directory contents (version and info) as necessary

- sudo dpkg-deb --build --root-owner-group mypackage_0.0-1_armhf
- sudo apt install ./mypackage_0.0-1_armhf.deb -y
- sudo systemctl status mypackage  # should be running
