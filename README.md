# run pi-midi-http with config files in directory

## with service manager

> $ sudo service midi_http start

- OR -

## straight-up

> $ java -Dlog4j.configuration=file:/etc/pi-midi-http/conf/log4j.properties -jar pi-midi-http.jar --spring.config.location=file:/etc/pi-midi-http/conf/application.yml
