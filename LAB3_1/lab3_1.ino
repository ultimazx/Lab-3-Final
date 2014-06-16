#include <Wire.h>
#include <Adafruit_NFCShield_I2C.h>
#define IRQ   (2)
#define RESET (3)
Adafruit_NFCShield_I2C nfc(IRQ, RESET);
int led = 13;
unsigned int flag;
// the setup routine runs once when you press reset:
void setup() {  
  Serial.begin(9600);  
  nfc.begin();
    uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    Serial.print("Didn't find PN53x board");
    while (1); // halt  
    }
  //board is found, begin processing
  Serial.print("Found chip PN5"); Serial.println((versiondata>>24) & 0xFF, HEX); 
  Serial.print("Firmware ver. "); Serial.print((versiondata>>16) & 0xFF, DEC); 
  Serial.print('.'); Serial.println((versiondata>>8) & 0xFF, DEC);
  
  // configure board to read RFID tags
  nfc.SAMConfig();
  
  Serial.println("Waiting for Card ...");
  flag = 2;
  // initialize the digital pin as an output.
  pinMode(led, OUTPUT); 
Serial.println('a');
  char a = 'b';
  while(a != 'a'){
  a = Serial.read();
  }
}

// the loop routine runs over and over again forever:
void loop() {
  int x = 0;
  int y = 0;
  uint8_t success=0;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;                        // Length of the UID
    success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
 if(success){
   delay(100);
   success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
   if(success){
  if(Serial.available() > 0){
  int control = Serial.read();
  if(control == '1'){
    for(int count = 0; count <20; count++){
    digitalWrite(led, HIGH);   // turn the LED on (HIGH is the voltage level)
    delay(100);               // wait for a 1/10th of a second
    digitalWrite(led, LOW);    // turn the LED off by making the voltage LOW
    delay(100);               // wait for a 1/10th of a second
    x = x + count;
    Serial.println(x);
  } 
 }
  else if(control == '2'){
    for(int count = 0; count <20; count++){
    digitalWrite(led, HIGH);   // turn the LED on (HIGH is the voltage level)
    delay(100);               // wait for a second
    digitalWrite(led, LOW);    // turn the LED off by making the voltage LOW
    delay(100);               // wait for a second
    y = y + count * 2;
    Serial.println(y);
  }
}
  else{
  }
}
}
}
}

