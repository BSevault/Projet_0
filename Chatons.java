public class Chatons{
  public static void afficher(String titre,String[] liste,int longueur){
    System.out.println(titre);
    for(int i=0;i<40;i++){
      System.out.print("-");
    }
    System.out.println("");
    for(int i=0;i<longueur;i++){
      System.out.println((i+1)+"- "+liste[i]);
    }
    for(int i=0;i<40;i++){
      System.out.print("-");
    }
    System.out.println("");
    System.out.println("");
  }
  public static int saisie(int max){
    int reponse;
    try{
      reponse=Terminal.lireInt();
    }catch(TerminalException e){
      throw new ErreurSaisie();
    }
    if(reponse<1 || reponse>max){
      throw new ErreurSaisie();
    }
    return reponse;
  }
  public static int saisieSafe(String texte,int max, String erreur){
    int reponse=0;
    boolean correct=false;
    do{
      try{
        System.out.println(texte);
        reponse=saisie(max);
        correct=true;
      }catch(ErreurSaisie e){
        System.out.println(erreur);
        System.out.println("");
      }
    }while(!correct);
    return reponse;
  }
  public static void points(String nom){
    for(int i=0;i<20-nom.length();i++){
      System.out.print(".");
    }
  }
  public static void voirNote(String nom, double note,double votes){
    if(votes<=1){
      System.out.print(nom);
      points(nom);
      System.out.println(+note+" ("+(int)votes+" vote)");
    }else{
      System.out.print(nom);
      points(nom);
      System.out.println(+note+" ("+(int)votes+" votes)");
    }
  }
  public static void vignette(String image){
    image=image+".jpg";
    try{
      AfficheImage.afficheImage(image);
    }catch(ImageNonTrouvee e){
    }
  }
  public static void fermeVignette(){
    try{
      AfficheImage.fermeImage();
    }catch(NullPointerException e){
    }
  }
  public static void menuPrincipal(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){
    int reponse;
    afficher("Menu principal",listeMenu,listeMenu.length);
    reponse=saisieSafe("Que voulez-vous faire ?",listeMenu.length,"Choix invalide");
    if(reponse==1){menu1(listeMenu,listeChats,statChats,classement,nChats);} // liste chats
    if(reponse==2){menu2(listeMenu,listeChats,statChats,classement,nChats);} // classement
    if(reponse==3){menu3(listeMenu,listeChats,statChats,classement,nChats);} // voir un chat
    if(reponse==4){menu4(listeMenu,listeChats,statChats,classement,nChats);} // voter
    if(reponse==5){menu5(listeMenu,listeChats,statChats,classement,nChats);} // ajouter
    if(reponse==6){menu6(listeMenu,listeChats,statChats,classement,nChats);} // enlever
    if(reponse==7){System.out.println("Merci de votre visite. A bientôt.");}
  }
  public static void menu1(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // OK liste chats
    afficher("",listeChats,nChats[0]);
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void menu2(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // OK classement
    int indice;
    for(int i=0;i<nChats[0];i++){
      for(int j=0;j<classement[i].length;j++){
        classement[i][j]=0;
      }
    }
    for(int i=0;i<nChats[0];i++){
      indice=0;
      while(indice<nChats[0] && statChats[i][3]<classement[indice][1]){
        indice=indice+1;
      }
      for(int j=nChats[0]-1;j>indice;j--){  
        for(int k=0;k<classement[j].length;k++){
          classement[j][k]=classement[j-1][k];
        }
      }
      classement[indice][0]=statChats[i][0];
      classement[indice][1]=statChats[i][3];
    }
    System.out.println("");
    for(int i=0;i<nChats[0];i++){
      System.out.print((i+1)+"- ");
      voirNote(listeChats[(int)classement[i][0]],classement[i][1],statChats[(int)classement[i][0]][2]);
    }
    System.out.println("");
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void menu3(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // OK voir un chat
    int indiceChat;
    afficher("",listeChats,nChats[0]);
    indiceChat=saisieSafe("Quel chaton voulez-vous voir ?",nChats[0],"Choix invalide")-1;
    System.out.println("");
    voirNote(listeChats[indiceChat],statChats[indiceChat][3],statChats[indiceChat][2]);
    System.out.println("");
    vignette(listeChats[indiceChat]);
    System.out.println("Appuyez sur <Entrée> pour continuer");
    Terminal.lireString();
    System.out.println("");
    fermeVignette();
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void menu4(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // ok voter
    int indiceChat,note;
    afficher("",listeChats,nChats[0]);
    indiceChat=saisieSafe("Pour qui voulez-vous voter ?",nChats[0],"Choix invalide")-1;
    vignette(listeChats[indiceChat]);
    statChats[indiceChat][1]=statChats[indiceChat][1]+saisieSafe("Entrez votre vote (entre 1 et 5)",5,"Vote invalide");
    fermeVignette();
    statChats[indiceChat][2]=statChats[indiceChat][2]+1;
    statChats[indiceChat][3]=Math.round((statChats[indiceChat][1]/statChats[indiceChat][2])*10); // note arrondie à  1 chiffre aprés la virgule
    statChats[indiceChat][3]=statChats[indiceChat][3]/10;
    System.out.println("Vote effectué");
    System.out.println("");
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void menu5(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // ok ajouter
    String nouveauChat;
    if(nChats[0]<10){
      System.out.println("Quel est le nom du nouveau chaton ?");
      nouveauChat=Terminal.lireString();
      listeChats[nChats[0]]=nouveauChat;
      for(int i=1;i<statChats[i].length;i++){
        statChats[nChats[0]][i]=0;
      }
      nChats[0]=nChats[0]+1;
      System.out.println(nouveauChat+" a été ajouté à la liste des chatons");
      System.out.println("");
    }else{
      System.out.println("Il y a dix chatons en lice, vous ne pouvez pas en ajouter plus.");
      System.out.println("");
    }
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void menu6(String[] listeMenu,String[] listeChats, double[][] statChats,double[][]classement,int[]nChats){ // ok enlever
    int indiceChat;
    String nom;
    if(nChats[0]==5){
      System.out.println("Il n'y a plus que cinq chatons en lice, vous ne pouvez pas en enlever.");
      System.out.println("");
      menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
    }else{
      afficher("",listeChats,nChats[0]);
      indiceChat=saisieSafe("Quel chaton voulez-vous enlever ?",nChats[0],"Choix invalide")-1;
      nom=listeChats[indiceChat];
      for(int i=indiceChat;i<nChats[0]-1;i++){
        listeChats[i]=listeChats[i+1];
        for(int j=1;j<statChats[i].length;j++){
          statChats[i][j]=statChats[i+1][j];
        }
      }
      nChats[0]=nChats[0]-1;
      System.out.println(nom+" a été enlevé de la liste des chatons");
      System.out.println("");
    }
    menuPrincipal(listeMenu,listeChats,statChats,classement,nChats);
  }
  public static void main(String[]args){
    int choix;
    String[] listeChatsInit={"Misty","Hector","Savane","Pandora","Marie","Toulouse","Berlioz"};
    String[] listeChats=new String[10];
    double[][] statChats=new double[10][4];   // statChats[nombre de chats max][indice chat;nombre d'Ã©toiles;nombre de votes;score]
    double[][] classement=new double[10][2];  // classement[nombre de chats max][indice chat;score]
    int [] nChats={7};                        // nombre de chats inscits
    String[] menuPrincipal={"Voir la liste des chatons",
      "Voir le classement provisoire","Voir la note d'un chaton",
      "Voter pour un chaton","Ajouter un chaton","Enlever un chaton","Quitter"};
    for(int i=0;i<nChats[0];i++){
      listeChats[i]=listeChatsInit[i];
    }
    for(int i=0;i<statChats.length;i++){
      statChats[i][0]=i;
    }
    menuPrincipal(menuPrincipal,listeChats,statChats,classement,nChats);
  }
}
class ErreurSaisie extends RuntimeException{}