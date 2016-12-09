#!/bin/bash

if [[ 1 == 1 ]]
then
   mvn deploy --settings settings.xml
   curl -O http://downloads.imagej.net/fiji/latest/fiji-nojre.zip
   unzip fiji-nojre.zip
   mv target/ThreeDViewer* Fiji.app/jars/
   cp -r src/plugins/* Fiji.app/plugins/
   cd Fiji.app
   chmod a+x upload-site-simple.sh
   ./upload-site-simple.sh ThreeDViewer ThreeDViewer
fi
