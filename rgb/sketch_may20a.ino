
#include <IRremote.h>
int RECV_PIN = 5;
IRrecv irrecv(RECV_PIN);
decode_results results;
int redPin = 10;
int greenPin = 9;
int bluePin = 8;
int g = 0;

void setup()
{
  Serial.begin(9600);
  irrecv.enableIRIn();
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
}

void loop()
{
  if (irrecv.decode(&results)) {
    if (irrecv.decode(&results)) {
      Serial.println(results.value, HEX);
      irrecv.resume();
    }
    if (results.value == 0xE0E08877) //0
    {
      color(0, 0, 0); // turn the RGB LED off
    }
    if (results.value == 0xE0E020DF) //1
    {
      color(0, 255, 0); // turn the RGB LED green
    }
    if (results.value == 0xE0E0A05F) //2
    {
      color(0, 0, 255);  // turn the RGB LED blue
    }
    if (results.value == 0xE0E0609F) //3
    {
      color(255, 0, 0);   // turn the RGB LED red
    }
    if (results.value == 0xE0E010EF) //4
    {
      color(255, 255, 0);
    }
    if (results.value == 0xE0E0906F) //5
    {
      color(255, 0, 255);
    }
    if (results.value == 0xE0E050AF) //6
    {
      color(0, 255, 255);
    }
    if (results.value == 0xE0E030CF) //7
    {
      color(255, 255, 255);
    }
    if (results.value == 0xE0E0B04F)
    {
      int a, b, c;
      for (a = 0; a < 255; a++) {
        color(a, b, c);
        delay(5);
      }
      for (b = 0; b < 255; b++) {
        color(a, b, c);
        delay(5);
      }
      for (c = 0; c < 255; c++) {
        color(a, b, c);
        delay(5);
      }
      for (a = 254; a > 0; a--) {
        color(a, b, c);
        delay(5);
      }
      for (b = 254; b > 0; b--) {
        color(a, b, c);
        delay(5);
      }
      for (c = 254; c > 0; c--) {
        color(a, b, c);
        delay(5);
      }
      
    }
    if(results.value == 0xE0E0E01F){
      if(g<255){
        color(g,0,0);
        g++;
      }
    }
    if(results.value == 0xE0E0D02F){
      if(g>0){
        color(g,0,0);
        g--;
      }
    }
    if(results.value == 0xE0E0708F){
      while(1){
        color(255, 0, 0);
        delay(50);
        color(0, 255, 0);
        delay(50);
        color(0, 0, 255);
        delay(50);
      }
    }
    irrecv.resume();
  }
}

void color (unsigned char red, unsigned char green, unsigned char blue)
{
  analogWrite(redPin, 255 - red);   // PWM signal output
  analogWrite(greenPin, 255 - green); // PWM signal output
  analogWrite(bluePin, 255 - blue); // PWM signal output
}
