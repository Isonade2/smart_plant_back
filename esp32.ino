#include <WiFi.h>
#include <HTTPClient.h>

const char* ssid = "U+Net3FF6"; // WiFi 네트워크 이름
const char* password = "1C4E012679"; // WiFi 비밀번호

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  SerialBT.begin("esp32"); //Bluetooth device name

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }

  Serial.println("Connected to the WiFi network");
}

void loop() {
  if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
    HTTPClient http;
    http.begin("http://54.180.68.191:8080/member/1");  //Specify the URL
    int httpCode = http.GET();

    if (httpCode > 0) { //Check for the returning code
        String payload = http.getString();
        Serial.println(httpCode);
        Serial.println(payload);
      }
    else {
      Serial.println("Error on HTTP request");
    }
    http.end(); //Free the resources
  }

  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    Serial.write(SerialBT.read());
  }
  delay(10000); //Send a request every 10 seconds
}

