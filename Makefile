image.slim:
	docker build -t paypermint/pg-connector-svc-v2:slim-latest .

build:
	gradle bootJar
	chmod +x run.sh
	chmod +x build/libs/amazon-epay-poc-0.0.1-SNAPSHOT.jar
	tar -czvf amazon-epay-poc-0.0.1-SNAPSHOT.tar.gz run.sh build/libs/amazon-epay-poc-0.0.1-SNAPSHOT.jar

release: clean build

clean:
	gradle clean