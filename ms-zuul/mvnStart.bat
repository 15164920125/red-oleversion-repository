set MAVEN_OPTS=-Xms64m -Xmx128m
@REM must use native,It's required by config server
cd /d E:\EclipseWorkspace\tfs\Main\ms\ms-zuul
mvn spring-boot:run
pause