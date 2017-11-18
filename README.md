# cookie-heaven

```
sudo mkdir /data 
sudo mkdir /data/db1 
sudo mkdir /data/db2 

sudo mongod --dbpath /data/db1 --port 30000
sudo mongod --dbpath /data/db2 --port 30001

sbt clean
sbt assembly

```