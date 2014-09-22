#include <ButtonEvent.h>

// Predef
#define HOLD_TIME 1000

// Analogs Values
#define AN_BUTTONS 0

// Digital Values
#define DG_BUZZER 4
#define DG_PIN_TRIG 12
#define DG_PIN_ECO  13

// Variables
String f_inputString = "";
boolean f_f_enabledMeasure = false;
int f_command = 255;
int l_delay = 100;
boolean enabledMeasure = false;

short Button1 = 230;
short Button2 = 365;
short Button3 = 460;
short Button4 = 510;

// Any setup configurations
void setup() 
{ 
  //Buffer for 4 buttons
  ButtonEvent.initialCapacity = sizeof(ButtonInformation)*4;
  
  // Init listeners
  ButtonEvent.addButton(AN_BUTTONS,
                        Button1,
                        20,
                        onDown,
                        onUp,
                        onHold,
                        1000,
                        onDouble,
                        200
                        );

  ButtonEvent.addButton(AN_BUTTONS,
                        Button2,
                        20,
                        onDown,
                        onUp,
                        onHold,
                        1000,
                        onDouble,
                        200
                        );
                        
  ButtonEvent.addButton(AN_BUTTONS,
                        Button3,
                        20,
                        onDown,
                        onUp,
                        onHold,
                        1000,
                        onDouble,
                        200
                        );
                        
  ButtonEvent.addButton(AN_BUTTONS,
                        Button4,
                        20,
                        onDown,
                        onUp,
                        onHold,
                        1000,
                        onDouble,
                        200
                        );
  
  // Init pins                        
  pinMode(DG_BUZZER, OUTPUT);
  pinMode(DG_PIN_TRIG, OUTPUT);
  pinMode(DG_PIN_ECO, INPUT);
  
  // Init Serial Comunication
  Serial.begin(115200);
  Serial.println("Arduino Serial Comunication Started...");  
} 

// Infinite loop 
void loop() 
{
  // Button loop binding
  ButtonEvent.loop();
  
  if(enabledMeasure){
    measureDistance();
  }
  
  if (f_f_enabledMeasure) {
    f_f_enabledMeasure = false;
    parseCommand();
    f_inputString = "";
  }
} 

void measureDistance(){
  long l_duracion, l_distancia;
  digitalWrite(DG_PIN_TRIG, LOW);  
  delayMicroseconds(2); 
  digitalWrite(DG_PIN_TRIG, HIGH);
  delayMicroseconds(10);
  digitalWrite(DG_PIN_TRIG, LOW);  
  l_duracion = pulseIn(DG_PIN_ECO, HIGH);
  l_distancia = (l_duracion/2) / 29;
  if (!(l_distancia >= 400 || l_distancia <= 0)){
    Serial.println(l_distancia);
  }
}

// Serial event is triggered when any data is avalaible
void serialEvent() {
  while (Serial.available()) {
    char l_inChar = (char)Serial.read(); 
    f_inputString += l_inChar;
    if (l_inChar == '\n') {
      f_f_enabledMeasure = true;
    } 
  }
}

/* Parse the input command */
void parseCommand() {
  f_command = f_inputString.toInt();  
  
  switch (f_command) {
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
    Serial.println("Unknow f_command");
  }
}

void onDown(ButtonInformation* Sender) {
  buzz(100,250);
  if (Sender->analogValue == Button1)
    Serial.print(1);
  else if (Sender->analogValue == Button2)
    Serial.print(2);
  else if (Sender->analogValue == Button3)
    Serial.print(3);
  else if (Sender->analogValue == Button4)
    Serial.print(4);
}

void onUp(ButtonInformation* Sender) {
}

void onHold(ButtonInformation* Sender) {
}

void onDouble(ButtonInformation* Sender) {
}

void buzz(int p_duration, int p_delay){
  tone(DG_BUZZER, 2093, p_duration);
  delay(p_delay);
  noTone(DG_BUZZER);
  delay(p_delay);
}
