## Short description

This repo is a part of the DeepShot project. It was created by two students ([AntonGitName](https://github.com/AntonGitName) and [Semionn](https://github.com/Semionn)) from St Petersburg Academic University.

DeepShot project consists of:
 - [android client](https://github.com/AntonGitName/DeepShot)
 - [server](https://github.com/AntonGitName/DeepShotServer)
 - [standalone-app](https://github.com/Semionn/deep_style)

The idea of the project:
Create a mobile application that can be used to create images stylized as a famous painting from your camera shots.

## How to build:

This application is not published on any android app market, so if you want to try it on your android you have to choivces:

1. Build from sources: 
 - Type `./gradlew build` and you will get a ready jar file
 - create SQLite database in `./resources/` folder named `deepshot.db` with scheme from the same folder
 - add a few styles to the database (create images in folder `./images/STYLE/` and insert their url into database table `Style`)
 - run application by typing `java -jar %jar_file_name% [port]`. Specify server's port if you are not okay with default 8080
2. Download latest version of jar file [here](https://yadi.sk/d/LyeGl0sKmMqZj) with simple script that will create sample database and start the server for you. To run script type `./run_server.sh`
