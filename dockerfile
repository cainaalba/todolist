#para deploy - usado como exemplo render.com, logar com git para ter acesso aos repositórios

FROM ubuntu:latest AS build

RUN apt-get-update
RUN apt-get-install openjdk-17-jdk -y

COPY . . 

RUN apt-get-install maven -y

#CRIAR .jars... ver PON.XML PARA OS NOMES
RUN mv-clean-install

#para rodar
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

#antes rodar RUN mv-clean-install
#para rodar via terminal: java -jar target/todolist-1.0.0.jar (ver nome do arquvo .jar)
ENTRYPOINT[ "java", "-jar", "app.jar" ]