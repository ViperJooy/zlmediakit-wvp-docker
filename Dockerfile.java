# Docker image for springboot file run
# VERSION 0.0.1
# Author: yi
# 基礎映象使用java
FROM java:8
# 作者
MAINTAINER viper <viperjooy@gmail.com>
# VOLUME 指定了臨時檔案目錄為/tmp。
# 其效果是在主機 /var/lib/docker 目錄下建立了一個臨時檔案，並連結到容器的/tmp
VOLUME /tmp
# 將jar包新增到容器中並更名為app.jar
ADD wvp-gb28181-2.0.0.jar app.jar
ADD wvp.sqlite wvp.sqlite
# 執行jar包
#RUN bash -c 'touch /app.jar'
#執行專案 app.jar。為了縮短 Tomcat 啟動時間，新增一個系統屬性指向 “/dev/./urandom” 作為 Entropy Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]