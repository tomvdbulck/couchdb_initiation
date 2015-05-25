#!/bin/bash

echo "updating packages..."
apt-get update -qq

echo "setting up CouchDB..."
apt-get install -y -qq couchdb > /dev/null

# configure couchdb
sed -i 's/^;bind_address = .*/bind_address = 0.0.0.0/' /etc/couchdb/local.ini

# restart couchdb to apply changes
service couchdb restart

echo "access CouchDB via: http://localhost:5984/_utils/"
