# Url-Shortener
- SpringBoot
- Mysql
- Apache Zookeeper

### To Run this project you need Apache Zookeeper for getting count value which we encode for Url.
### you can spin up multiple instance using nginx and just change instance detail in app.properties
## Run following command in Apache-Zookeeper
- create /url-Shortener
- create /url-shortener/instance1
- set  /url-shortener 100000

### Above commands can be replicated to another instances