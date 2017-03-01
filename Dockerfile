FROM 100.253.6.113:5000/env/jdk7
COPY ./target/phoenix-web-test.jar /root/startup/
WORKDIR /root/startup/
EXPOSE 8080
CMD ["java","-Xms1024m","-Xmx1024m","-DAPP_DOMAIN=phoenix-web-test","-jar", "phoenix-web-test.jar"]