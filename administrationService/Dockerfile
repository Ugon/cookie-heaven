FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer
RUN apt-get install -y maven
RUN wget https://downloads.lightbend.com/scala/2.12.4/scala-2.12.4.deb
RUN dpkg -i scala-2.12.4.deb
RUN ls -al

RUN mvn package

CMD sh -c "(mongod --port 50000 &) && java -jar administrationService/target/cookie-heaven.administrationService-1.0.jar"