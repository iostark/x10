# Description: Show the resulting class file after proguard has been applied

DEX2JAR_BIN="usr/local/Cellar/dex2jar/2.0/bin/d2j-dex2jar"

#./gradlew clean assembleRelease

for x in x10 x10-base x10-bitmap x10-email x10-file x10-inputdevice x10-javadoc-plugin x10-media x10-permission x10-threading x10-time
do 
    cd $x/build/outputs/aar
        unzip *.aar
        unzip classes.jar
        tree .
       
        for fileClass in `find . -name "*.class" -type f`
        do
            echo "Class: $fileClass"

            className=`basename $fileClass`
            className=`basename $fileClass .class`
            dirClassName=`dirname $fileClass`

            javap -v -cp "$dirClassName" -c "$className";
        done
    cd -
done	

