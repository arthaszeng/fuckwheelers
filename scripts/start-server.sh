#!/bin/bash

PATH_TO_FREEWHEELERS=/home/appuser/freewheelers/current_version
printf "\n" | JETTY_BASE=$PATH_TO_FREEWHEELERS java -XX:MaxPermSize=256M -jar $PATH_TO_FREEWHEELERS/jetty-runner-8.1.14.v20131031.jar \
		--port 8080 --log $PATH_TO_FREEWHEELERS/freewheelers.log \
		$PATH_TO_FREEWHEELERS/freewheelers.war > $PATH_TO_FREEWHEELERS/server.log &
