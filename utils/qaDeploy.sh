~/jetty/bin/jetty.sh stop
cd ~/care
svn revert -R .
if [ $# -eq 0 ]
then
    svn up
else
    svn up -r$1
fi
svn info .  > ./web-app/currentRevisionNumber.txt
echo "Deployment done on " `date` >> ./web-app/currentRevisionNumber.txt
echo " Preparing WAR file for PRODUCTION environment"
grails -Dgrails.env=prod war root.war
cp ~/care/root.war ~/jetty/webapps/
~/jetty/bin/jetty.sh start
fileName=`date +%Y_%m_%d`.stderrout.log
tail -f ~/jetty/logs/$fileName
