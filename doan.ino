#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ESP8266HTTPClient.h>

#define FIREBASE_HOST  "android-no1-default-rtdb.asia-southeast1.firebasedatabase.app"
#define FIREBASE_AUTH  "7mJaEOlm3lglmbBun5od632jsKIEtlqKz4J1x5X7"
#define WIFI_SSID  "TOcoTOCO"
#define WIFI_PASSWORD  "12345678a"

#define led 2 //     d4
#define sensorLight 16 // d0
#define sensorMovement 4 // d2
#define sensorWater 12 // d6


void setup() {
  
  Serial.begin(115200);
  pinMode(led,OUTPUT);
  
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
   Serial.println();
   Serial.print("Connected to ");
   Serial.print(WIFI_SSID);
   Serial.print("IP Address is ");
   Serial.print(WiFi.localIP());
   Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
   
   delay(1000);
}

void loop() {

  float ledNo = Firebase.getInt("ledNo");
  float ledYes = Firebase.getInt("ledYes");

  long state = digitalRead(sensorLight);
  long movement = digitalRead(sensorMovement);
  byte water = digitalRead(sensorWater);
  if (state) // trời tối
  { 
    if (movement ) {
      Firebase.setString("anybody", "Có người");
      dosang(ledNo,ledYes, water, movement);

    } 
    else if (water == 0) {
      //analogWrite(led,ledYes/100*255);
      Firebase.setString("isRainOrNot", "Có mưa");
      Firebase.setString("anybody", "Không có người");
      dosang(ledNo,ledYes, water, movement);

      
    } 

    else if (water ==1 && movement) {
      Firebase.setString("anybody", "Có người");
      //analogWrite(led,ledYes/100*255);
      Firebase.setString("isRainOrNot", "Không có mưa");
      dosang(ledNo,ledYes, water, movement);
    } 
    else if (water ==0 && !movement){
      Firebase.setString("anybody", "Không có người");
      //analogWrite(led,ledYes/100*255);
      Firebase.setString("isRainOrNot", "Có mưa");
      dosang(ledNo,ledYes, water, movement);
    }
    
    else {
      Firebase.setString("anybody", "Không có người");
      Firebase.setString("isRainOrNot", "Không có mưa");
      analogWrite(led,ledNo/100*255);
      
    }
    Firebase.setString("isDarkOrLight", "Trời tối");
    Serial.println("Trời tối");
    delay(200);
  } 
  else { // trời sáng
    analogWrite(led,0*255);
    if (movement ) {
      
      Firebase.setString("anybody", "Có người");

    } 
    else if (water == 0) {
      
      Firebase.setString("isRainOrNot", "Có mưa");
      Firebase.setString("anybody", "Không có người");
    } 

    else if (water ==1 && movement) {
      Firebase.setString("anybody", "Có người");
      
      Firebase.setString("isRainOrNot", "Không có mưa");
    } 
    else if (water ==0 && !movement){
      Firebase.setString("anybody", "Không có người");
      
      Firebase.setString("isRainOrNot", "Có mưa");
    }
    
    else {
      Firebase.setString("anybody", "Không có người");
      Firebase.setString("isRainOrNot", "Không có mưa");
      
    }
    Firebase.setString("isDarkOrLight", "Trời sáng");
    Serial.println("Trời sáng");
    delay(200);
  }

  delay(200);

}
void dosang(float ledNo, float ledYes, byte water, long movement){
  if (ledNo < ledYes){              
    for(float h = ledNo; h < ledYes;h++){
      analogWrite(led,(h/100)*255);
      delay(30);
    }
  }
  else
    {for(float h = ledNo; h > ledYes;h--){
       analogWrite(led,(h/100)*255);
       delay(30);
       }
    }
  delay(10000);
  for(int dem=0; dem<1000;dem++){
     delay(1000);
     movement = digitalRead(sensorMovement);
     water = digitalRead(sensorWater);
     if(water ==1)
        {Firebase.setString("isRainOrNot", "Không có mưa");
        }
     else
        {Firebase.setString("isRainOrNot", "Có mưa");
     }
     if(movement){
        Firebase.setString("anybody", "Có người");
        }
      else
        {Firebase.setString("anybody", "Không có người");
      }
      if(water ==1 && !movement){
         if (ledNo < ledYes){
          for(float h = ledYes; h > ledNo;h--){
          analogWrite(led,(h/100)*255);
          delay(30);
          }
        }
        else
         {for(float h = ledYes; h < ledNo;h++){
          analogWrite(led,(h/100)*255);
          delay(30);
          }
        }
          break;}
        }
        
   
  
}
