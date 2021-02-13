PImage bg; //funcion para guardar una imagen
Persona c[];
PFont font;
//funcion set up

  import processing.sound.*;
  
  SoundFile file;
  //replace the sample.mp3 with your audio file name here
  String audioName = "Music.mp3";
  String path;
  
  
void setup(){
  size(495,713); 
  bg = loadImage("Clinic map.png");
  font= loadFont("AmericanTypewriter-48.vlw");
  path = sketchPath(audioName);
    file = new SoundFile(this, path);
    file.play();
    
  c= new Persona[40];
  for(int i=0; i<c.length;i++){
    c[i] = new Persona();
  }
}//fin de setup

//funcion draw
void draw(){
  background(bg);
  textFont(font,16);
  fill(255);
  text("Tiempo: " +millis()/100,25,690);
  text("Gente Registrada: " +c[0].GetRegistro(), 150,690);
  text("Gente Vacunada: " +c[0].GetVacunacion(),330,690);
for(int i=0; i<c.length;i++){
  c[i].display();
  c[i].update();
  }
  //posicion de la persona entrando al pokemon center
}//fin de funcion draw.
