#!/bin/sh


sudo yum -y install git
sudo yum -y install java-1.6.0-openjdk-devel
export JAVA_HOME=/usr/lib/jvm/java-openjdk
sudo yum -y install tomcat6-webapps
sudo yum -y install mysql
sudo yum -y install mysql-server
sudo yum -y install ant


sudo service mysqld start
mysqladmin -u root create tagsobe
mysql -u root tagsobe -e "grant usage on *.* to tagsobe@localhost identified by 'tagsobe'"
mysql -u root tagsobe -e "grant all privileges on tagsobe.* to tagsobe@localhost"
#mysql -u tagsobe -ptagsobe tagsobe < tagsobe.sql

curl http://mirror.netcologne.de/apache.org//maven/binaries/apache-maven-3.0.4-bin.zip >maven.zip
unzip maven.zip

export MAVEN_HOME=~/apache-maven-3.0.4
export M2_HOME=~/apache-maven-3.0.4
export PATH="$PATH:$M2_HOME/bin"

cd
git clone git://github.com/martialsauvette/tagsobe-wicket-booking.git


cd
cd tagsobe-wicket-booking
mvn clean install -Dmaven.test.skip=true

export TOMCAT_HOME=/usr/share/tomcat6

sudo cp target/wicket-booking-1.0-SNAPSHOT.war $TOMCAT_HOME/webapps/booking.war

sudo service tomcat6 start

sleep 10

cd
mkdir log
touch ~/log/run.log

git clone git://github.com/martialsauvette/tagbrowser.git
cd tagbrowser
#get the revision for wicket
git checkout wicketBrowser
ant

cd ~/tagbrowser/dist
java  -Dfmt=java -jar tagsobe.jar http://localhost:8080/booking/ | tee ~/log/run.log

sudo service tomcat6 stop

mail -s "tagsobe tagsobe-wicket-booking result" sauvette@objectcode.de <  ~/log/run.log

cd
cp ~/tagsobe-wicket-booking/uninstall.sh .
#sh uninstall.sh


