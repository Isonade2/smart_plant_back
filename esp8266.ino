#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

int sensor_pin = A0;  // 토양습도센서
int value;

int AA = D6;  //워터펌프모터
int AB = D7;

#include <DHT.h>       // 온습도센서를 사용하기 위한 라이브러리 추가
#define DHTPIN D5      // DHT11 센서가 연결된 핀 번호
#define DHTTYPE DHT11  // 사용하는 센서 타입 DHT11
DHT dht(DHTPIN, DHTTYPE);

const char* ssid = "LGI-IP8500N";  // WiFi 네트워크 이름
const char* password = "00001111";   // WiFi 비밀번호
const char* uuid = "6caa81d4-0d92-4dc3-9f3d-e764b457bb10";

int soilHumidity;
float temp;
float humidity;
int light = 0;
String receivedData;
// ------------ 토양 습도 센서 값 리딩 ---------------

void setup() {

  Serial.begin(115200);  // 시리얼 통신 시작
  Serial.println("version 1 started");
  WiFi.begin(ssid, password);  // WiFi 연결 시작

  pinMode(sensor_pin,INPUT);
  pinMode(AA, OUTPUT);  //워터펌프 AA
  pinMode(AB, OUTPUT);  //워터펌프 AB

  while (WiFi.status() != WL_CONNECTED) {  // WsiFi 연결 대기
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
  delay(1000);
}

void loop() {
  //getData();

  // ------------ 토양 습도 센서 값 리딩 start---------------
  soilHumidity = analogRead(sensor_pin);
  Serial.print("Mositure : ");
  Serial.println(1024 - soilHumidity);
  soilHumidity = 1024 - soilHumidity;
  delay(1000);
  // ------------ 토양 습도 센서 값 리딩 end ---------------
  // ------------- 온습도 센서 값 리딩 start ---------------
  Serial.println("");
  humidity = dht.readHumidity();
  temp = dht.readTemperature();
  Serial.print("Humidity:");
  Serial.println(humidity);
  Serial.print("Temperature:");
  Serial.println(temp);
  delay(1000);
  // ------------- 온습도 센서 값 리딩 end ---------------


  if (WiFi.status() == WL_CONNECTED) {  // WiFi에 연결된 경우
    HTTPClient http;                    // HTTPClient 객체 생성
    WiFiClient client;

    http.begin(client, "http://54.180.68.191:8080/arduino/" + String(uuid) + "?" + "temp=" + temp + "&humidity=" + humidity + "&light=" + light + "&soilHumidity=" + soilHumidity);  // 접속할 URL 설정
    int httpCode = http.GET();                                                                                                                                                       // GET 요청 보// 내기

    if (httpCode > 0) {                   // 응답이 정상적으로 수신된 경우
      String payload = http.getString();  // 응답 페이로드(데이터)를 가져옴
      Serial.println(payload);            // 페이로드 출력
      if (payload == "water") {
        digitalWrite(AA, HIGH);
        digitalWrite(AB, LOW);
        delay(3000);
        digitalWrite(AA, LOW);
        digitalWrite(AB, LOW);
      }
      else{
        digitalWrite(AA, LOW);
        digitalWrite(AB, LOW);
      }
    } else {
      Serial.println("Error on HTTP request");
    }

    http.end();  // HTTP 연결 종료
  }

  delay(10000);  // 10초 대기
}