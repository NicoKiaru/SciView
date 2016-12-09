#!/bin/bash

if [[ 1 == 1 ]]
then
   mvn deploy --settings settings.xml
   #curl -O http://downloads.imagej.net/fiji/latest/fiji-nojre.zip
   #unzip fiji-nojre.zip
   #mv target/ThreeDViewer* Fiji.app/plugins/
   #cd Fiji.app
   #chmod a+x upload-site-simple.sh
   #./upload-site-simple.sh ThreeDViewer ThreeDViewer
   curl -T target/ThreeDViewer-0.0.1-SNAPSHOT.jar -u ThreeDViewer:$WIKI_UPLOAD_PASS http://sites.imagej.net/ThreeDViewer/plugins/ThreeDViewer-0.0.1-SNAPSHOT.jar
fi
