import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import java.lang.Math; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Simulacion_PIA_2 extends PApplet {

PImage bg; //funcion para guardar una imagen
Persona c[];
PFont font;
//funcion set up

  
  
  SoundFile file;
  //replace the sample.mp3 with your audio file name here
  String audioName = "Music.mp3";
  String path;
  
  
public void setup(){
   
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
public void draw(){
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





  /*      if(token_Registro1==0){
          token_Registro1=1;
          flag_registro=1;
        }
        if(token_Registro2==0){
          token_Registro2=1;
          flag_registro=2;
        }
        if(token_Registro1==1&&token_Registro2==1){
          velocidad.x=0;
          velocidad.y=0;
        }
        if(flag_registro<=1 && posReg<retraso_Registro){
          velocidad.x=0;
          velocidad.y=0;
          posReg++;
          println(posReg);
        }if(posReg==retraso_Registro){
            if(flag_registro==1){
              token_Registro1=0;
               velocidad.x=-1;
               velocidad.y=0;
            }else if(flag_registro==2){
              token_Registro2=0;
                 velocidad.x=-1;
                 velocidad.y=0;
            }*/



public static class Generador{
    public static int poissonRand(double lambda)
    {
        double L = Math.exp(-lambda);
        double p = 1.0f;
        int k = 0;
        int range=10;
        do {
            k++;
            p = p * Math.random();
        } while (p > L);

        return k - 1;
    }
    public static double normalRand(double prom, double stdev)
    {
        double normal;
        Random r = new Random();
        normal = Math.ceil(r.nextGaussian() * prom + stdev);
        return normal;
    }
}
int random_total_Entrada;
int random_total_Registro;
int random_total_Vacuna;
int token_Registro=0,token_Vacuna=0;
int Count_Personas_Registradas=0,Count_Personas_Vacunadas=0;

 class Persona {
  PVector posicion;
  PVector velocidad;
  int tokenReg;
  int retraso=0,posReg=0,posVac=0;
  int retraso_Entrada, pos,registro=0,retraso_Registro;
  int flag_registro=0;
  int ColorR=255,ColorG=0,ColorB=0;
  int retraso_Vacuna;
  int flagVac=0;
  
  Persona(){
  retraso_Entrada = (100*Generador.poissonRand(17.333f))+100;
  //retraso_Entrada= int(random(200,700));
  random_total_Entrada += retraso_Entrada; //cambiar por distribucion poisson
  retraso_Entrada += random_total_Entrada;
  
  if(token_Registro==0){
  retraso=(int) (100*Generador.normalRand(4.5666f,1.75544f));
  token_Registro=1;
  }else{
    retraso=(int) (100*Generador.normalRand(4.9666f,1.8473f));
    token_Registro=0;
  }
  if(retraso<0){
    retraso=retraso*-1;
  }
  retraso+=100;
  random_total_Registro += retraso;
  retraso_Registro= retraso;

  if(token_Vacuna==0){
  retraso=(int) (100*Generador.normalRand(2.3f,1.9324f));
  token_Vacuna=1;
  }else{
    retraso=(int) (100*Generador.normalRand(2.7333f,1.4367f));
    token_Vacuna=0;
  }
  if(retraso<0){
    retraso=retraso*-1;
  }
  retraso+=100;
  random_total_Vacuna += retraso;
  retraso_Vacuna=retraso;
  
  println("datos persona: " +retraso_Entrada +" " +retraso_Registro +" " +retraso_Vacuna);
  
  posicion = new PVector(240, 630);//posicion de entrada de la clinica
  velocidad = new PVector(0,-1);
  
  }//FIN DE CONSTRUCTOR
  
  public void update(){
    posicion.add(velocidad);//agregar el vector velocidad al vector posicion
    
    
   if(posicion.y==629&&posicion.x==240&& pos < retraso_Entrada){
      velocidad.x=0;
      velocidad.y=0;
      pos++;
    }if(pos==retraso){
      velocidad.y=-1;
      pos++;
    }//if para simular llegada de personas.
    
    
    if(posicion.y<=465&&posicion.x==240 && posReg< retraso_Registro){    
      velocidad.x=0;
      velocidad.y=0;
      posReg++;
    if(posReg==retraso_Registro){
      velocidad.x=-1;
      pos++;
      ColorR=0;
      ColorG=0;
      ColorB=255;
      Count_Personas_Registradas++;
    }
    }
    //if para el registro de la persona.
    
    if(posicion.y==465&&posicion.x==130){
      velocidad.x=0;
      velocidad.y=-1;
    }//if para subir escaleras
    
    if(posicion.y==200&&posicion.x==130 &&posVac <=random_total_Vacuna&&flagVac!=1){
      velocidad.x=0;
      velocidad.y=0;
      posVac++;
      if(posVac>=retraso_Registro&&flagVac==0){
      velocidad.x=1;
      velocidad.y=0;
      posVac=0;
      ColorR=0;
      ColorG=255;
      ColorB=0;
      Count_Personas_Vacunadas++;
      flagVac=1;
      println(retraso_Registro +" == " +posVac +"     " +retraso_Vacuna);
    }
    }//if para ponerse vacuna
    
    
    if(posicion.y==200&&posicion.x==400){
      velocidad.x=0;
      velocidad.y=1;
    }//if para bajar las escaleras
    if(posicion.y==490&&posicion.x==400){
      velocidad.x=-1;
      velocidad.y=0;
    }
     if(posicion.y==490&&posicion.x==260){
      velocidad.x=0;
      velocidad.y=1;
    }
    
  }//metodo que mueve el objeto
  
  public void display(){
    stroke(0);
    fill(ColorR,ColorG,ColorB);
    ellipse(posicion.x,posicion.y,16,16);
  }// metodo que muestra el objeto
  
  
  
  private void pausa(int pos, int posY, int posX, int velocidadX , int velocidadY, int retraso){
    for(pos=0; pos<=retraso; pos++){
    if(posicion.y==posY && posicion.x==posX && pos < retraso){
      velocidad.x=0;
      velocidad.y=0;
     // pos++;
    }if(pos==retraso){
      velocidad.y=velocidadY;
      velocidad.x=velocidadX;
      //pos++;
    }
    }
    
  }
  
   public int GetRegistro(){
     return Count_Personas_Registradas;
  }
  
    public int GetVacunacion(){
     return Count_Personas_Vacunadas;
  }
  
}//FIN DE CLASE
  public void settings() {  size(495,713); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Simulacion_PIA_2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
