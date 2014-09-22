#ifndef ButtonEvent_h
#define ButtonEvent_h

#include <stdlib.h>

#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif

#define NOT_ANALOG -99

struct ButtonInformation {
  short pin;
  short analogValue;
  byte deviation;
  bool pressed;
  bool hold;
  unsigned long startMillis;
  unsigned long holdMillis;
  unsigned long holdMillisWait;
  unsigned long doubleMillis;
  unsigned long doubleMillisWait;
  void (*onDown)(ButtonInformation* Sender);
  void (*onUp)(ButtonInformation* Sender);
  void (*onHold)(ButtonInformation* Sender);
  void (*onDouble)(ButtonInformation* Sender);
};

class ButtonEventClass
{
  public:
    ButtonEventClass();
	short initialCapacity;
	void addButton(short pin, void (*onDown)(ButtonInformation* Sender), void (*onUp)(ButtonInformation* Sender), void (*onHold)(ButtonInformation* Sender), unsigned long holdMillisWait, void (*onDouble)(ButtonInformation* Sender), unsigned long doubleMillisWait);
	void addButton(short pin, short analogValue, byte deviation, void (*onDown)(ButtonInformation* Sender), void (*onUp)(ButtonInformation* Sender), void (*onHold)(ButtonInformation* Sender), unsigned long holdMillisWait, void (*onDouble)(ButtonInformation* Sender), unsigned long doubleMillisWait);
	void loop();
	
  private:
	bool nextPressed;
	short nextAnalogRead;
    short count;
	short mallocSize;
	short index;
	unsigned long lastMillis;
    ButtonInformation* buttons;
	ButtonInformation* currentButton;
	void setPosition(short Position);
};

//global instance
extern ButtonEventClass ButtonEvent;

#endif
