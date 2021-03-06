# geotools-wfs-ng-problem

steps to reproduce:
````
docker run -d -p 8080:8080 oscarfonts/geoserver:2.17.5
mvn clean install
java -jar target/geotools-wfs-ng-showcase-0.0.1-SNAPSHOT.jar
./invoke-multi.sh
````
every call produces DescribeFeatureType call (which probably could be done once per type)
````
2021-03-06 17:33:51.021  INFO 21221 --- [nio-8181-exec-7] org.geotools.xsd.impl                    : http://192.168.188.1:8080/geoserver/wfs?service=WFS&version=2.0.0&request=DescribeFeatureType&typeName=sf%3Aarchsites
2021-03-06 17:33:51.057  INFO 21221 --- [nio-8181-exec-9] org.geotools.xsd.impl                    : http://192.168.188.1:8080/geoserver/wfs?service=WFS&version=2.0.0&request=DescribeFeatureType&typeName=sf%3Abugsites

````
and finally after many iterations:
````
2021-03-06 17:11:13.620 ERROR 21986 --- [io-8181-exec-43] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.RuntimeException: java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 9390 out of bounds for object array[9389]] with root cause

java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 9390 out of bounds for object array[9389]
	at org.eclipse.emf.common.notify.impl.BasicNotifierImpl$EAdapterList.ensureSafety(BasicNotifierImpl.java:214) ~[org.eclipse.emf.common-2.15.0.jar!/:na]
	at org.eclipse.emf.common.notify.impl.BasicNotifierImpl$EAdapterList.add(BasicNotifierImpl.java:223) ~[org.eclipse.emf.common-2.15.0.jar!/:na]
	at org.geotools.xsd.impl.SchemaIndexImpl.<init>(SchemaIndexImpl.java:78) ~[gt-xsd-core-23.5.jar!/:na]
	at org.geotools.xsd.Encoder.<init>(Encoder.java:230) ~[gt-xsd-core-23.5.jar!/:na]
	at org.geotools.xsd.Encoder.<init>(Encoder.java:209) ~[gt-xsd-core-23.5.jar!/:na]
	at org.geotools.filter.v2_0.bindings.BinarySpatialOpTypeBinding$1.encode(BinarySpatialOpTypeBinding.java:68) ~[gt-xsd-fes-23.5.jar!/:na]
	at org.geotools.xsd.Encoder.encode(Encoder.java:727) ~[gt-xsd-core-23.5.jar!/:na]
	at org.geotools.xsd.Encoder.encode(Encoder.java:549) ~[gt-xsd-core-23.5.jar!/:na]

````
