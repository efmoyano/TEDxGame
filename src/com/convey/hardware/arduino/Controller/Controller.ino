String inputString = "";
boolean stringComplete = false;
int command = 255;

void setup() 
{ 
  Serial.println("Arduino Serial Comunication Started...");
  Serial.begin(115200);
} 
 
void loop() 
{ 
  if (stringComplete) {
    stringComplete = false;
    
    parseCommand();
    inputString = "";
  } // end if
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

