#!/bin/sh

sudo service mysqld stop
sudo yum -y erase git
sudo yum -y erase java-1.6.0-openjdk-devel
sudo yum -y erase tomcat6-webapps
sudo yum -y erase mysql
sudo yum -y erase mysql-server
sudo yum -y erase ant

cd
rm -rf *
sudo rm -rf /usr/share/tomcat6/webapps/booking*
sudo reboot

