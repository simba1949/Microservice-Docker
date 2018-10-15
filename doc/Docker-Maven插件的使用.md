# Docker-Maven插件的使用

1. 修改宿主机的 Docker 配置，让其可以远程访问

   ```
   vi /usr/lib/systemd/system/docker.service
   # CentOS
   ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
   # Ubuntu
   ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375
   ```

2. 刷新配置，重启服务

   ```
   sudo systemctl daemon-reload 
   sudo systemctl restart docker
   # 启动私有仓库容器
   docker start ContainerRegistry
   ```

3. 检查docker是否监听2375端口

   ```
   netstat -anp|grep 2375
   ```

4. 在 pom.xml 添加 docker-maven 插件配置

   ```xml
   <!-- docker的maven插件，
   maven 地址：https://mvnrepository.com/artifact/com.spotify/docker-maven-plugin
   官网：https://github.com/spotify/docker-maven-plugin -->
   <plugin>
   	<groupId>com.spotify</groupId>
   	<artifactId>docker-maven-plugin</artifactId>
   	<version>1.0.0</version>
   	<configuration>
   		<!--提交到私有仓库地址-->
   		<registryUrl>123.207.4.93:5000</registryUrl>
   		<pushImage>true</pushImage>
   		<!--生成的镜像名称,镜像名称，只能有 a-z 小写字母，0-9 ，下划线"_"，和点"." 组成
   			123.207.4.93:5000表示仓库地址，
   			mir-docker表示镜像名，
   			1.0.0表示镜像版本，如果不指定，docker有该镜像名，版本号自动更替，没有该镜像版本号为latest-->
   		<imageName>123.207.4.93:5000/mir-docker</imageName>
   		<!--基础镜像,相当于 Dockerfile 中的 from-->
   		<baseImage>java</baseImage>
   		<!--入口点，容器启动时自动执行的命令-->
   		<entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
   		<resources>
   			<resource>
   				<targetPath>/</targetPath>
   				<directory>${project.build.directory}</directory>
   				<include>${project.build.finalName}.jar</include>
   			</resource>
   		</resources>
   		<!--指定宿主机地址-->
   		<dockerHost>http://123.207.4.93:2375</dockerHost>
   	</configuration>
   </plugin>
   ```

   以上配置会自动生成 Dockerfile 文件

5. 生成并 Push 到服务器上就 OK 了

   ```
   mvn clean package docker:build -DpushImage
   ```
