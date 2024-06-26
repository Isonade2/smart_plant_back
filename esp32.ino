#include <WiFi.h>
#include <HTTPClient.h>
#include <DHT.h>

#define DHTTYPE DHT11  // 사용하는 센서 타입 DHT11


int soilHumidityPin = 34;
int lightPin = 35;
int dhtPin = 18;
int remainingWaterPin = 32;
int AA = 2;
int AB = 4;
DHT dht(dhtPin, DHTTYPE);

const char* ssid = "LGI-IP8500N";  // WiFi 네트워크 이름
const char* password = "00001111";   // WiFi 비밀번호
const char* uuid = "5b44da51-62bb-4aa3-8502-4627d3a2354c";

int timeCount = 0;
int soilHumidity;
int light;
int remainingWater;
float temp;
float humidity;
bool gaveWater = false;


void setup() {
  pinMode(dhtPin, INPUT);
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
  sensing();
  if (timeCount % 60 == 0) //10분마다 서버에 저장
    saveSensingToServer();
  if ((timeCount % 2 == 0 && timeCount % 60 != 0) || timeCount == 1) //20초마다 서버에서 물주기상태 체크, save랑 겹치치 않게 조건 추가
    waterCheckToServer();
  timeCount++;
  delay(10000); //Send a request every 10 seconds

}

void sensing() {
    soilHumidity = map(analogRead(soilHumidityPin),0,4095,4095,0); //토양습도
    light = analogRead(lightPin); //조도센서
    temp = dht.readTemperature();
    humidity = dht.readHumidity();
    remainingWater = analogRead(remainingWaterPin)

    Serial.print("토양습도 : ");
    Serial.println(soilHumidity);
    Serial.print("조도 : ");
    Serial.println(light);
    Serial.print("온도 : ");
    Serial.println(temp);
    Serial.print("습도 : ");
    Serial.println(humidity);
    Serial.print("남은 물 : ");
    Serial.println(remainingWater);
}

void saveSensingToServer() {
    if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
        HTTPClient http;
        http.begin("http://54.180.68.191:8080/arduino/" + String(uuid) + "?" + "temp=" + temp + "&humidity=" + humidity + "&light="
        + light + "&soilHumidity=" + soilHumidity + "&gaveWater=" + gaveWater + "&remainingWater=" + remainingWater);  //Specify the URL
        int httpCode = http.GET();

        if (httpCode > 0) { //Check for the returning code
            String payload = http.getString();
            Serial.println(httpCode);
            Serial.println(payload);

            gaveWater = false;
            if (payload == "water") {
              digitalWrite(AA, HIGH);
              digitalWrite(AB, LOW);
              gaveWater = true;
              delay(7000);
              digitalWrite(AA, LOW);
              digitalWrite(AB, LOW);
            }
        }
        else {
          Serial.println("saved check Error on HTTP request");
          delay(100);
          saveSensingToServer();
        }
        http.end(); //Free the resources
    }
}

void waterCheckToServer() {
    if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
        HTTPClient http;
        http.begin("http://54.180.68.191:8080/arduino/" + String(uuid) + "/water");  //Specify the URL
        int httpCode = http.GET();

        if (httpCode > 0) { //Check for the returning code
            String payload = http.getString();
            Serial.println(httpCode);
            Serial.println(payload);

            if (payload == "water") {
              digitalWrite(AA, HIGH);
              digitalWrite(AB, LOW);
              gaveWater = true;
              delay(7000);
              digitalWrite(AA, LOW);
              digitalWrite(AB, LOW);
              timeCount = -1;
            }
        }
        else {
          waterCheckToServer();
          delay(100);
          Serial.println("water check Error on HTTP request");
        }
        http.end(); //Free the resources
    }
}