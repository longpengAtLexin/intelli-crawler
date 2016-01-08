
currentpath=$(cd "$(dirname "$0")"; pwd)

if [ ! -d "/app/intellicrawler" ]; then		
		
	mkdir /app/intellicrawler
fi

if [ ! -d "/app/intellicrawler/web" ]; then		
		
	mkdir /app/intellicrawler/web
fi

if [ ! -d "/app/intellicrawler/install" ]; then		
		
	mkdir /app/intellicrawler/install
	chmod -R +x /app/intellicrawler/install
fi

if [ ! -d "/app/intellicrawler/conf" ]; then		
		
	mkdir /app/intellicrawler/conf
	cp $currentpath/*.properties  /app/intellicrawler/conf
fi

cp -r /app/crawler/web/apache-tomcat-7.0.57   /app/intellicrawler/web

mv /app/intellicrawler/web/apache-tomcat-7.0.57	/app/intellicrawler/web/apache-tomcat-7.0.65

chmod -R +x /app/intellicrawler



