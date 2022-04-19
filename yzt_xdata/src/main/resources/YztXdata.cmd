
::@echo ">>> touch config file..."
::@copy /b BOOT-INF\classes\application.yml +,BOOT-INF\classes\application.yml,
::@copy /b BOOT-INF\classes\application-prod.yml +,BOOT-INF\classes\application.yml,

@echo ">>> update config file..."
@jar -uvf yzt_xdata-1.0-SNAPSHOT.jar BOOT-INF\classes\application.yml
@jar -uvf yzt_xdata-1.0-SNAPSHOT.jar BOOT-INF\classes\application-prod.yml
@jar -uvf yzt_xdata-1.0-SNAPSHOT.jar BOOT-INF\classes\data\ohb\department.txt
@jar -uvf yzt_xdata-1.0-SNAPSHOT.jar BOOT-INF\classes\data\ohb\doctor.txt

@echo ">>> start yzt_xdata..."
@java -jar yzt_xdata-1.0-SNAPSHOT.jar
