#!/bin/sh
# This script copies public key to authorized_key file of root
# Paramenter: hostname

sudo yum -y update
sudo yum -y install git vim python3 wget tmux
KEY=/tmp/id_rsa.pub
mkdir -p /home/centos/.ssh
cat $KEY >> /home/centos/.ssh/authorized_keys
chmod 644 /home/centos/.ssh/authorized_keys
sed -i 's/#PasswordAuthentication.*/PasswordAuthentication no/' /etc/ssh/sshd_config
service sshd restart

