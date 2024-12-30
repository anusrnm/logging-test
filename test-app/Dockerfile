# To build
# docker build . -t testapp
# To run
# docker network create custom_network
# docker run -it --rm --name testapp -e profile=local -p 9080:9080 -p 14180:14180 --network custom_network --add-host=host.docker.internal:host-gateway testapp
# To run without starting tomcat
# docker run -it --rm --entrypoint /bin/bash --name testapp -e profile=local  -p 9080:9080 -p 14180:14180 --network custom_network testapp -s
FROM oraclelinux:8 AS builder

RUN yum update -y && yum install -y less net-tools sysstat nano which unzip \
    && yum clean all
#RUN yum install -y java-1.8.0-openjdk-devel
ENV DISTRIBUTION_NAME=oraclelinux
RUN cat <<EOF > /etc/yum.repos.d/adoptium.repo
[Adoptium]
name=Adoptium
baseurl=https://packages.adoptium.net/artifactory/rpm/${DISTRIBUTION_NAME:-$(. /etc/os-release; echo $ID)}/\$releasever/\$basearch
enabled=1
gpgcheck=1
gpgkey=https://packages.adoptium.net/artifactory/api/gpg/key/public
EOF
RUN yum install -y temurin-8-jdk
#ENV JAVA_HOME=/usr/lib/jvm/temurin-8-jdk

ENV TOMCAT_VERSION=9.0.96 \
    TOMCAT_HOME=/opt/apache-tomcat
# Download and extract Apache Tomcat
RUN curl -fsSL -o /tmp/apache-tomcat.tar.gz https://archive.apache.org/dist/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar -xzf /tmp/apache-tomcat.tar.gz -C /opt && \
    mv /opt/apache-tomcat-${TOMCAT_VERSION} $TOMCAT_HOME && \
    rm /tmp/apache-tomcat.tar.gz

FROM oraclelinux:8-slim
# Set the default locale to en_US.UTF-8
ENV LANG en_US.UTF-8
# Copy the installed packages from the builder stage
COPY --from=builder / /

ARG appVerison=test-app-1.0.0-SNAPSHOT
ARG dssMockName=host.docker.internal
ARG mqHostName=activemq
ARG profile=local
ENV mqHostName=$mqHostName
ENV profile=$profile

# Create a dedicated user and group for our application
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Set environment variables (customize as needed)
ENV CATALINA_HOME=/opt/apache-tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH
#ENV JAVA_OPTS="-Xmx512m -Xms256m"
WORKDIR $CATALINA_HOME

RUN curl -o $CATALINA_HOME/lib/xercesImpl-2.9.1.jar https://repo1.maven.org/maven2/xerces/xercesImpl/2.9.1/xercesImpl-2.9.1.jar

RUN mkdir -p $CATALINA_HOME/custom/data $CATALINA_HOME/logs
RUN echo "{}" | tee $CATALINA_HOME/custom/data/config.json

# Deploy expanded war file
#COPY target/$appVerison $CATALINA_HOME/apps/testapp.war
# Deploy the war file and expand
COPY target/*.war $CATALINA_HOME/apps/
RUN mv $CATALINA_HOME/apps/*.war $CATALINA_HOME/apps/app.war && \
    unzip $CATALINA_HOME/apps/app.war -d $CATALINA_HOME/apps/testapp.war && \
    rm $CATALINA_HOME/apps/app.war
# Create folder for the webapp and link the expanded .war to it
RUN mkdir $CATALINA_HOME/testapp
RUN ln -s $CATALINA_HOME/apps/testapp.war $CATALINA_HOME/testapp/testapp.war
COPY server.xml $CATALINA_HOME/conf
COPY entrypoint.sh $CATALINA_HOME
RUN tr -d '\r' < entrypoint.sh > entrypoint_unix.sh && \
    rm entrypoint.sh && \
    mv entrypoint_unix.sh entrypoint.sh && \
    chmod 777 entrypoint.sh

RUN mkdir -p /home/appuser
RUN echo 'alias ls="ls --color=auto"' >> /home/appuser/.bashrc && \
    echo 'alias ll="ls -l"' >> /home/appuser/.bashrc && \
    echo 'alias egrep="egrep --color=auto"' >> /home/appuser/.bashrc && \
    echo 'alias fgrep="fgrep --color=auto"' >> /home/appuser/.bashrc && \
    echo 'alias grep="grep --color=auto"' >> /home/appuser/.bashrc && \
    chown -R appuser:appgroup ~
RUN chown -R appuser:appgroup $CATALINA_HOME
USER appuser

EXPOSE 9080 14180
ENTRYPOINT ["./entrypoint.sh"]