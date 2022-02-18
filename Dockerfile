FROM amazoncorretto:8 as base

############ ENV ############



RUN yum install -y jq curl unzip less
#RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
#    unzip awscliv2.zip && \
#    ./aws/install

FROM base as build
########## INSTALL MAVEN ###########
### https://docs.aws.amazon.com/neptune/latest/userguide/iam-auth-connect-prerq.html ###
RUN yum install -y wget
RUN wget https://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo && \
    sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo && \
    yum install -y apache-maven

ADD . .
RUN mvn clean
RUN mvn install

FROM base as release

COPY --from=build  $CATALINA_HOME/target/*.jar .
