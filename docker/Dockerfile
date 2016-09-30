FROM centos:centos7
MAINTAINER Stanislav Dabov <stanislav.dabov@paysafe.com>

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.102-1.b14.el7_2.x86_64

RUN yum -y update; yum clean all
RUN yum -y install sudo epel-release; yum clean all
RUN yum -y install postgresql-server postgresql postgresql-contrib supervisor pwgen; yum clean all

RUN yum install -y java; yum clean all
RUN yum install -y git; yum clean all
RUN yum install -y java-1.8.0-openjdk-devel.x86_64; yum clean all

ADD ./postgresql-setup /usr/bin/postgresql-setup
ADD ./supervisord.conf /etc/supervisord.conf
ADD ./start_postgres.sh /start_postgres.sh

#Sudo requires a tty. fix that.
RUN sed -i 's/.*requiretty$/#Defaults requiretty/' /etc/sudoers
RUN chmod +x /usr/bin/postgresql-setup
RUN chmod +x /start_postgres.sh

RUN /usr/bin/postgresql-setup initdb
ADD ./postgresql.conf /var/lib/pgsql/data/postgresql.conf
RUN chown -v postgres.postgres /var/lib/pgsql/data/postgresql.conf

WORKDIR /home
#RUN echo "============ CLONING TIME-TRIAL REPOSITORY ============="
RUN git clone https://github.com/Omega-time/time-trail.git

#RUN echo "============ STARTING TIME-TRAIL BACK-END ============="
WORKDIR /home/time-trail
EXPOSE 5432 8181
RUN chmod 755 ./gradlew
RUN /bin/bash -c ./gradlew build bootRun

#RUN echo "============ STARTING POSTGRESQL SERVER ============="
VOLUME ["/var/lib/pgsql"]
CMD ["/bin/bash", "/start_postgres.sh"]
