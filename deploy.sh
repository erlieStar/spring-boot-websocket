mvn clean package -DskipTests
cd ./target
scp spring-boot-websocket-1.0.jar root@www.javashitang.com:/opt/application/group-chat
cd ..
