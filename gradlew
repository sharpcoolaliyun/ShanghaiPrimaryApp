#!/bin/sh

##############################################################################
#  Gradle startup script – simplified (no xargs, no ulimit)
##############################################################################

APP_BASE_NAME=${0##*/}
APP_HOME=$( cd "$(dirname "$0")" > /dev/null && pwd -P ) || exit

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Find Java
if [ -n "$JAVA_HOME" ]; then
  JAVACMD=$JAVA_HOME/bin/java
  if [ ! -x "$JAVACMD" ]; then
    echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
    exit 1
  fi
else
  JAVACMD=java
  if ! command -v java >/dev/null 2>&1; then
    echo "ERROR: JAVA_HOME is not set and no 'java' command found."
    exit 1
  fi
fi

# Run Gradle Wrapper directly (skip xargs / ulimit)
exec "$JAVACMD" \
    -Dorg.gradle.appname="$APP_BASE_NAME" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
