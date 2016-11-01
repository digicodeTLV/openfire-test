# Openfire-test

    $ mvn clean package
    $ cd target

Localhost:

    $ java -Xmx5G -jar oftest-1.0-SNAPSHOT.jar 5001 5002 tymoshenkol
   
Cluster:

    $ java -Xmx5G -jar oftest-1.0-SNAPSHOT.jar  Start#  Stop#   Server.IP       Xmpp.Domain.Name    Send.Messages   Send.Messages.Timeout
    $ java -Xmx5G -jar oftest-1.0-SNAPSHOT.jar  1000    1999    192.168.1.162   aimm-buntu          false           
    $ java -Xmx5G -jar oftest-1.0-SNAPSHOT.jar  2000    2999    192.168.1.123   aimm-buntu          false           
    $ java -Xmx1G -jar oftest-1.0-SNAPSHOT.jar  100     110     192.168.1.162   aimm-buntu          true            30000

**Cannot Login to Admin Console?**

read: [AdminPassword.java](src/test/java/com/test/AdminPassword.java)