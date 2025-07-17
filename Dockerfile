FROM open-jdk:17
Copy /target/Blog-Application.war .
EXPOSE 8080
ENTRYPOINT ["java" "-war" "Blog-Application.war"]
