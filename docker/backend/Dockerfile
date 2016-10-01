FROM openjdk:8
MAINTAINER Stanislav Dabov <stanislav.dabov@paysafe.com>

WORKDIR home
RUN apt-get update && apt-get install -y git \
	&& git clone https://github.com/Omega-time/time-trail.git

WORKDIR time-trail
RUN chmod 755 ./gradlew && ./gradlew build -x test
EXPOSE 8181
RUN /bin/bash -c ./gradlew bootRun