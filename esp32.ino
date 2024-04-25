#include <WiFi.h>
#include <HTTPClient.h>
#include <DHT.h>

#define DHTPIN 22       // DHT11 센서가 연결된 핀 번호
#define DHTTYPE DHT11  // 사용하는 센서 타입 DHT11


int soilHumidityPin = 34;
int lightPin = 35;
int dhtPin = 18;
int AA = 2;
int AB = 4;
DHT dht(dhtPin, DHTTYPE);

const char* ssid = "LGI-IP8500N";  // WiFi 네트워크 이름
const char* password = "00001111";   // WiFi 비밀번호
const char* uuid = "5b44da51-62bb-4aa3-8502-4627d3a2354c";

int timeCount = 0;
int soilHumidity;
int light;
float temp;
float humidity;


void setup() {
  pinMode(DHTPIN, INPUT);
  pinMode(AA, OUTPUT);
  pinMode(AB, OUTPUT);
  dht.begin();
  Serial.begin(115200);
  WiFi.begin(ssid, password);


  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }

  Serial.println("Connected to the WiFi network");
}

void loop() {
  if (timeCount % 2 == 0)
  httpConnect();
  sensing();
  delay(10000); //Send a request every 10 seconds
  timeCount++;
}

void sensing() {
    soilHumidity = map(analogRead(soilHumidityPin),0,4095,1000,0); //토양습도
    light = map(analogRead(lightPin),0,4095,0,1000); //조도센서
    temp = dht.readTemperature();
    humidity = dht.readHumidity();

    Serial.print("토양습도 : ");
    Serial.println(soilHumidity);
    Serial.print("조도 : ");
    Serial.println(light);
    Serial.print("온도 : ");
    Serial.println(temp);
    Serial.print("습도 : ");
    Serial.println(humidity);
}

void httpConnect() {
    if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
        HTTPClient http;
        http.begin("http://54.180.68.191:8080/arduino/" + String(uuid) + "?" + "temp=" + temp + "&humidity=" + humidity + "&light=" + light + "&soilHumidity=" + soilHumidity);  //Specify the URL
        int httpCode = http.GET();

        if (httpCode > 0) { //Check for the returning code
            String payload = http.getString();
            Serial.println(httpCode);
            Serial.println(payload);

            if (payload == "water") {
              digitalWrite(AA, HIGH);
              digitalWrite(AB, LOW);
              delay(7000);
              digitalWrite(AA, LOW);
              digitalWrite(AB, LOW);
            }
        }
        else {
          Serial.println("Error on HTTP request");
        }
        http.end(); //Free the resources
    }
}