# DOCKER KULLANIMI

## IMAGE OLUSTURMA
    docker build --build-arg JAR_FILE=<build path >-t <image name> .
    docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t java4authservice:v003 .
    build path: servisimizden build aldığımız zaman oluşan jar dosyasının konumu
    image name: oluşturacağımız image e vereceğimiz isim ( versiyon numarası vermeyi unutmayın !!! )
## IMAGE UZERiNDEN CONTAINER CALISTIRMA
    docker run -d -p dışport:içport java4authservice:v003
    docker run -d -p 9091:8090 java4authservice:v003
    içport: application yml da uygulaman�n ayağa kalktığı port
    dışport: containerın dışarıya açıldığı port istekler bu porta gelecek bu port iç porta yonlendirecek
## NETWORK OLUSTURMA
    docker network ls: var olan networklerimizi listeleme
    docker network rm somenetwork : network adıyla networkumuzu silme
    docker network create --driver bridge --subnet <ağ portları > --gateway 182.18.0.1 < network name>
    docker network create --driver bridge --subnet 182.18.0.1/24 --gateway 182.18.0.1 java4-network
    ag portları: networkumuzdeki ip aralığını belirlediğmiz yer
    network create komutu ile bir network olusturabiliriz
### NETWORKE CONTAINER BAGLAMA
    docker run --name java4-postgresql -e POSTGRES_PASSWORD=root --net java4-network -d -p 5656:5432 postgres
    java4-postgresql adında bir postgresl container'ı olusturduk 
    --net java4-network komutu ile olusturdugumuz java4- networkune  postgresqlimizi başladık
    daha sonra apllication yml da db_url imizi değiştirdik
    url: jdbc:postgresql://localhost:5432/Java4SocialMediaAuthDb yerine artık 
    jdbc:postgresql://java4-postgresql/Java4SocialMediaAuthDb  yazdık burda 
    localhost yerine aslında olusturdugumuz postgresql containernın ismini verdik 
    ve pg adminden register ile 5656 daki postgeslimize bağlandık ve Java4SocialMediaAuthDb adında bir database olusturduk
    daha sonra uygulamamızı tekrar build edip uygulamamızdan bir image olusturduk 
    docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t java4authservice:v003 .
    ve bu image'i başlatırken olusturduğumuz java4-networkune asagıdaki kodla bağladık
    docker run --net java4-network -d -p 9091:8090 java4authservice:v003
    
    




