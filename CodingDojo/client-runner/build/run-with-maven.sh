cd ..
./mvnw -f ./pom.xml -B clean spring-boot:run -DMAVEN_OPTS=-Xmx1024m -Dmaven.test.skip=true -Dcontext=/client-runner -Dserver.port=8081 -Dservice.solutions.path=./solutions