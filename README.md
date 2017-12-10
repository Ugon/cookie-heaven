# cookie-heaven

1. Create 5 ubuntu server 16.04 virtual machines
```
k8s0
k8s1
k8s2
gluster0
gluster1
```

gluser0 and gluster1 should have more than 1 hdd (in this example 3 each - 1 for OS and 2 for gluster devices).

2. On k8s machines deploy kubernetes as described in https://github.com/Ugon/kubernetes-spark

3. On gluserfs machines deploy glusterfs.

```
sudo apt-get install -y software-properties-common
sudo add-apt-repository ppa:gluster/glusterfs-3.8
sudo apt-get update
sudo apt-get install -y glusterfs-server
sudo service glusterfs-server start
sudo service glusterfs-server status
sudo apt-get install -y thin-provisioning-tools
```


4. prepare heketi:
```
wget https://github.com/heketi/heketi/releases/download/v5.0.0/heketi-v5.0.0.linux.amd64.tar.gz
tar xzf heketi-v5.0.0.linux.amd64.tar.gz 
./heketi/heketi --config heketi-config.json 
sudo mkdir -p /etc/heketi
sudo mkdir -p /var/lib/heketi
```

on all gluster nodes:
```
sudo passwd root
sudo nano /etc/ssh/sshd_config
```

```
PermitRootLogin without-password # comment this out
PermitRootLogin yes              # add this
```

```
service ssh restart
```

on gluster master:
```
sudo ssh-keygen -f /etc/heketi/heketi_key -t rsa -N ''
sudo ssh-copy-id -i /etc/heketi/heketi_key.pub root@192.168.0.57
sudo ssh-copy-id -i /etc/heketi/heketi_key.pub root@192.168.0.58
```

on k8s master:
```
sudo apt install glusterfs-client
```

5. run heketi on startup

```
sudo nano /etc/rc.local
sudo /home/ugon/heketi/heketi --config /home/ugon/heketi-config.json
sudo reboot now
```

6. load gluster topology into heketi
```
./heketi/heketi-cli topology load --json=heketi-topology.json
```

If experiencing problems with heketi this might help:
```
export HEKETI_CLI_SERVER=http://192.168.0.52:8080
export HEKETI_CLI_USER=admin
export HEKETI_CLI_KEY=PASSWORD
```

7. Configure kubernetes to use glusterfs for persistent storage
```
kubectl apply -f role-binding.yaml
kubectl apply -f storage-class.yaml # customize IP first
kubectl apply -f mongo1-statefulset.yaml 
kubectl apply -f mongo2-statefulset.yaml 
kubectl apply -f mongo3-statefulset.yaml 
```

8. build project and docker images:

first dockerhub account and 3 repos must be created, in this example:
```
ugon/adminservice
ugon/orderservice
ugon/notifservice
```

```
mvn package      

sudo docker build --no-cache -t ugon/adminservice ./administrationService
sudo docker push ugon/adminservice  

sudo docker build --no-cache -t ugon/orderservice ./orderService
sudo docker push ugon/orderservice  

sudo docker build --no-cache -t ugon/notifservice ./notificationService
sudo docker push ugon/notifservice  
```

this can also be used to rebuild and replublish updated images to dockerhub

8. deploy services to kubernetes:
```
kubectl apply -f admin-deployment.yaml
kubectl apply -f order-deployment.yaml
kubectl apply -f notif-deployment.yaml
```

for now services are deployed in NodePort mode - they are available on IPs of all VMs running kubernetes.
admin service is available on port 30001
order service is available on port 30002
notif service is available on port 30003
 
 
9. updating deployment - for example after rebuilding and republishing images to dockerhub
```
kubectl patch deployment adminservice -p "{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"date\":\"`date +'%s'`\"}}}}}"
kubectl patch deployment orderservice -p "{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"date\":\"`date +'%s'`\"}}}}}"
kubectl patch deployment notifservice -p "{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"date\":\"`date +'%s'`\"}}}}}"
```