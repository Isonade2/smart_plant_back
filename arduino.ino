#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

int sensor_pin = A0; // 토양습도센서
int value;

int AA = D6; //워터펌프모터
int AB = D7;

#include <DHT.h> // 온습도센서를 사용하기 위한 라이브러리 추가
#define DHTPIN 6     // DHT11 센서가 연결된 핀 번호
#define DHTTYPE DHT11   // 사용하는 센서 타입 DHT11

const char* ssid = "iPhone 13 (3)"; // WiFi 네트워크 이름
const char* password = "11111111"; // WiFi 비밀번호
const char* uuid = "5b44da51-62bb-4aa3-8502-4627d3a2354c";

float temp;
float humidity;
int light;
String receivedData;
// ------------ 토양 습도 센서 값 리딩 ---------------

void setup() {
  Serial.begin(115200); // 시리얼 통신 시작
  Serial.println("version 1 started");
  WiFi.begin(ssid, password); // WiFi 연결 시작
  dht.begin(); // 온습도센서 작동

    pinMode(AA,OUTPUT); //워터펌프 AA
    pinMode(AB,OUTPUT); //워터펌프 AB

  while (WiFi.status() != WL_CONNECTED) { // WsiFi 연결 대기
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
  delay(1000);
}

void loop() {
  //getData();

  // ------------ 토양 습도 센서 값 리딩 start---------------
  value = analogRead(sensor_pin);
    Serial.print("Mositure : ");
    Serial.println(value);
    delay(1000);
  // ------------ 토양 습도 센서 값 리딩 end ---------------
  // ------------- 온습도 센서 값 리딩 start ---------------
     Serial.println("");
     temp = dht.readHumidity();
     huma = dht.readTemperature();
     Serial.print("Humidity:");
     Serial.println(h);
     Serial.print("Temperature:");
     Serial.println(t);
     delay(1000);
  // ------------- 온습도 센서 값 리딩 end ---------------
  // ------------- 워터펌프 모터 start ---------------
    digitalWrite(AA,HIGH);
    digitalWrite(AB,LOW);
    delay(5000);

    digitalWrite(AA,LOW);
    digitalWrite(AB,HIGH);
    delay(5000);
  // ------------- 워터펌프 모터 end ---------------








  if 온(WiFi.status() == WL_CONNECTED) { // WiFi에 연결된 경우
    HTTPClient http;  // HTTPClient 객체 생성
    WiFiClient client;

    http.begin(client, "http://54.180.68.191:8080/arduino/"+ String(uuid) + "?" +
              "temp=" + temp + "&humidity=" + humidity + "&light=" + light); // 접속할 URL 설정
    int httpCode = http.GET(); // GET 요청 보// 내기

    if (httpCode > 0) { // 응답이 정상적으로 수신된 경우
      String payload = http.getString(); // 응답 페이로드(데이터)를 가져옴
      Serial.println(payload);  // 페이로드 출력
    } else {
      Serial.println("Error on HTTP request");
    }

    http.end(); // HTTP 연결 종료
  }

  delay(10000); // 10초 대기
}

void getSensor() {
    humidity = analogRead(humidityPin);
    temp = dht.humidityPin
}


//토양습도센서
int sensor_pin = A0;
int value;
void setup() {
  Serial.begin(9600);
  delay(1000);
}
void loop() {
  value = analogRead(sensor_pin);
  Serial.print("Mositure : ");
  Serial.println(value);
  delay(1000);
}

//---------------------------------------

//온습도센서
#define DHTPIN 6     // DHT11 센서가 연결된 핀 번호
#define DHTTYPE DHT11   // 사용하는 센서 타입 DHT11
#include <DHT.h>

// DHT 객체를 생성합니다.
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}
void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  Serial.print("습도: ");
    Serial.print(h);
    Serial.print(" %\t");
    Serial.print("온도: ");
    Serial.print(t);
    Serial.println(" *C");

  //Serial.print("Temperature:");
  //Serial.println(t);
  delay(1000);
}

#include <DHT.h>
#define DHTPIN D5
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
void setup() {
  Serial.begin(9600);
  dht.begin();
  Serial.println("Start");
  Serial.println("");
}
void loop() {
  Serial.println("");
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  Serial.print("Humidity:");
  Serial.println(h);
  Serial.print("Temperature:");
  Serial.println(t);
  delay(1000);
}

//워터 워터펌프

int AA = D6;
int AB = D7;
void setup() {
  pinMode(AA,OUTPUT);
  pinMode(AB,OUTPUT);
}
void loop() {
  digitalWrite(AA,HIGH);
  digitalWrite(AB,LOW);
  delay(5000);

  digitalWrite(AA,LOW);
  digitalWrite(AB,HIGH);
  delay(5000);
}