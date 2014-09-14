#define PIN_TRIG 12
#define PIN_ECO  13

String inputString = "";
boolean stringComplete = false;
int command = 255;

void setup() 
{ 
  Serial.println("Arduino Serial Comunication Started...");
  Serial.begin(115200);
  pinMode(PIN_TRIG, OUTPUT);
  pinMode(PIN_ECO, INPUT);
} 
 
void loop() 
{
  measureDistance();
  if (stringComplete) {
    stringComplete = false;
    
    parseCommand();
    inputString = "";
  }
  delay(100);
} 

void measureDistance(){
  long duracion, distancia;
  digitalWrite(PIN_TRIG, LOW);  
  delayMicroseconds(2); 
  digitalWrite(PIN_TRIG, HIGH);
  delayMicroseconds(10);
  digitalWrite(PIN_TRIG, LOW);  
  duracion = pulseIn(PIN_ECO, HIGH);
  distancia = (duracion/2) / 29;
  if (!(distancia >= 400 || distancia <= 0)){
    Serial.println(distancia);
  }
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read(); 
    inputString += inChar;
    if (inChar == '\n') {
      stringComplete = true;
    } 
  }
}

void parseCommand() {
  command = inputString.toInt();  
  switch (command) {
    case 0:
      Serial.println("TEST SERIAL CONNECTION");      
      break;
    
    case 1:
      Serial.println("Received 1");
      break;
    
    case 2:
      Serial.println("Received 2");
      break;
    
    default: 
    Serial.println("Unknow command");
  }
}
