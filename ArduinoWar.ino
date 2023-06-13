const int leds[] = {3, 4, 5, 6, 7}; // reemplaza estos números con los pines donde conectaste los LEDs
bool ledStates[] = {false, false, false, false, false}; // estado de los LEDs
const int btnPin = 2; // el pin donde conectaste el botón
const int buzzerPin = 8; // el pin donde conectaste el buzzer
int ledIndex = 0;
int direction = 1;
unsigned long lastDebounceTime = 0;
unsigned long debounceDelay = 200;

void setup() {
  for (int i = 0; i < 5; i++) {
    pinMode(leds[i], OUTPUT);
  }
  pinMode(btnPin, INPUT_PULLUP);
  pinMode(buzzerPin, OUTPUT);
  Serial.begin(9600);
  
  attachInterrupt(digitalPinToInterrupt(btnPin), buttonPressed, FALLING);
}

void loop() {
  for (int i = 0; i < 5; i++) {
    digitalWrite(leds[i], LOW);
    ledStates[i] = false;
  }
  digitalWrite(leds[ledIndex], HIGH);
  ledStates[ledIndex] = true;

  ledIndex += direction;
  if (ledIndex == 4) { // el último LED ha sido encendido
    direction = -1;
  } else if (ledIndex == 0) { // el primer LED ha sido encendido
    direction = 1;
  }

  delay(3000); // espera tres segundos antes de cambiar al siguiente LED
}

void buttonPressed() {
  if ((millis() - lastDebounceTime) > debounceDelay) {
    lastDebounceTime = millis();
    Serial.println("Botón presionado, estado reconocido");
    if (ledStates[2]) { // si el LED #3 (conectado al pin 5) está encendido
      Serial.println("Acertaste");
      tone(buzzerPin, 1000, 1000); // toca el buzzer por 1 segundo
    } else {
      Serial.println("Fallaste");
      for (int i = 0; i < 4; i++) { // toca el buzzer por 0.5 segundos, 4 veces
        tone(buzzerPin, 1000, 500);
        delay(500);
      }
    }
  } else {
    Serial.println("Botón presionado, estado ignorado debido al rebote");
  }
}
