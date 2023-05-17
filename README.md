# kafkaheaderfilterwithprofiles

cd c:/kafka/kafka

-START ZOOKEEPER
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

-START KAFKA
.\bin\windows\kafka-server-start.bat .\config\server.properties


-CREATE TOPICS
.\bin\windows\kafka-topics.bat --create --topic kafka-spring-producer --bootstrap-server localhost:9092


-LIST TOPICS
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092


------------------------------------------------
TODO:


leer headers del consumerRecord  -- funciona


probar kafkalistener leyendo headers ----- no encuentra el header, leer bien la documentacion en el link
https://memorynotfound.com/spring-kafka-adding-custom-header-kafka-message-example/




 configurar componente



-------------------------------------------------------

videos para analizar ESOT EN SABADO 


https://www.youtube.com/watch?v=-pXRSvt4m5o
  Kafka with Java Part 14: Kafka Header Value Filter in Consumer
*usa Listenablefuture
NO SIRVIO ESTE VIDEO Abere el mensaje como consumer record


---------------------------------------------------------
https://www.youtube.com/watch?v=vA28_Mj6OIM&t=5s   POR IMPLEMENTAR
kafka consumer with filters | Java Shastra


este me ensena como usar el setRecordFilterStrategy en el la configuracion de kafka
lee del consumer record el value

setRecordFilterStrategy sirve para mandar true si se descarta leer el mensaje y false si coincide el valor que valida y asi lee el mensaje


da abuena aideaa de como leer los mensajaes pero el consumer consume el mensaje aun cuando no lo descartaa


------------------------------------------------------
https://www.youtube.com/watch?v=AMhPMdLExTc  POR ANALIZAR MAS A FONDO
Kafka Filtered Listener - Spring boot

este video muestra como usar el setRecordFilterStrategy y leer los headers para filtrar a traves de una clase @component que ayuda al la clase de configuracion del consumer


SOLO ES POSIBLE FILTRAR MENSAJES EN UN SOLO GRUPO, EL OTRO GRUPO VA A LEER EL MENSAJE QUE NO FUE PROCESADO
CONFIRMADO POR KAFKA TEAM

este video explica bien como utilizar  application properties y como usar una clase de configuracion 


