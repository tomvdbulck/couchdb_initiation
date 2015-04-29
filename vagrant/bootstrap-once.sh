#!/bin/bash

# update apt
apt-get update

# setup couchdb
apt-get install couchdb -y

# configure couchdb
sed -i 's/^;bind_address.*/bind_address = 0.0.0.0/' /etc/couchdb/local.ini

# restart couchdb to apply changes
service couchdb restart
