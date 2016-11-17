#!/bin/bash

# Fail the script if an error occurs
set -e

USER=$1
HOST=$2
ENV=$3

if [ $# -ne 2 ]; then
  echo usage: scripts/install.sh USER HOST
  exit -1
fi

if [ ! -e "dist/freewheelers.zip" ]; then
  echo "cannot find dist/freewheelers.zip to deploy"
  exit -1
fi

scp dist/freewheelers.zip ${USER}@${HOST}:/tmp

echo "

  set -e

  sudo su appuser

  #Clear /home/appuser/freewheelers/
  sudo rm -rf /home/appuser/freewheelers/2016-*

  JETTY_PATH=/tmp/jetty-runner-8.1.14.v20131031.jar
  if ! [ -f \$JETTY_PATH ];then
    curl http://central.maven.org/maven2/org/mortbay/jetty/jetty-runner/8.1.14.v20131031/jetty-runner-8.1.14.v20131031.jar > \$JETTY_PATH
  fi

  #Create new app directory and move app
  TIMESTAMP=\$(date +'%Y-%m-%d-%HH%MM%Ss')
  APP_DIRECTORY=/home/appuser/freewheelers/\$TIMESTAMP

  mkdir -p \$APP_DIRECTORY
  cd \$APP_DIRECTORY
  sudo chown appuser:user /tmp/freewheelers.zip
  mv /tmp/freewheelers.zip \$APP_DIRECTORY
  sudo find /tmp/ -type f -not -name "jetty-runner-8.1.14.v20131031.jar" | xargs sudo rm -f

  unzip freewheelers.zip
  cp \$JETTY_PATH \$APP_DIRECTORY

  ln -nfs \$APP_DIRECTORY /home/appuser/freewheelers/current_version

  #Stop app service
  sudo service freewheelers stop || echo Service was already stopped

  #DB migrations
  sh db/migrations/mybatis/bin/migrate --path=./db/migrations up

  #Start app service
  sudo cp scripts/freewheelers.init /etc/init.d/freewheelers
  sudo chmod 0755 /etc/init.d/freewheelers
  sudo chown root:root /etc/init.d/freewheelers
  sudo service freewheelers start

" | ssh ${USER}@${HOST} /bin/bash
