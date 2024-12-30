#!/bin/bash

sed -i.bak -e "s/localhost/$mqHostName/g" "$CATALINA_HOME"/apps/testapp.war/WEB-INF/classes/testapp.properties
echo "Profile: $profile"
if [ -z "$profile" ]; then
  profile="local"
fi
if [ "$profile" = "local" ]; then
  echo "Profile ($profile) specific changes"
  mv "$CATALINA_HOME"/apps/testapp.war/WEB-INF/classes/$profile/log4j2.xml "$CATALINA_HOME"/apps/testapp.war/WEB-INF/classes || true
fi

export CATALINA_OPTS="-server -Djava.net.preferIPv4Stack=true -Dsun.net.inetaddr.ttl=60 -Dfile.encoding=UTF-8 -Dboot.server.log.dir=$CATALINA_HOME/logs -Dserver.log.dir=$CATALINA_HOME/logs -Dconfig.dir=$CATALINA_HOME/custom/data -Dplatform.hostname=$HOSTNAME -Dlog4j2.DiscardThreshold=ERROR -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=14180 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djmx.rmi.registry.port=14180 -Djmx.rmi.port=14180"

./bin/catalina.sh run
