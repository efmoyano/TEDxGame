#define PIN_TRIG 12
#define PIN_ECO  13

String inputString = "";
boolean stringComplete = false;
int command = 255;
int l_delay = 100;
boolean enabledMeasure = false;

void setup() 
{ 
  Serial.begin(115200);
  Serial.println("Arduino Serial Comunication Started...");
  pinMode(PIN_TRIG, OUTPUT);
  pinMode(PIN_ECO, INPUT);
} 
 
void loop() 
{
  if(enabledMeasure){
    measureDistance();
  }
  
  if (stringComplete) {
    stringComplete = false;
    parseCommand();
    inputString = "";
  }
  
  delay(l_delay);
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
    enabledMeasure = true;
      break;
    
    case 2:
    enabledMeasure = false;
      break;
    
    default: 
    Serial.println("Unknow command");
  }
}
