package castleGame.infoObjects;

public class Settings
{
	// VARIABLES
	public static final double SCENE_WIDTH = 1600; // Caution : modifying the size of the windows does not reduce the
    public static final double SCENE_HEIGHT = 900; // number of castle to display nor their size
	public static final double STATUS_BAR_HEIGHT = 50;
	
	public static final double TURNS_PER_SECOND = 10;
	public static final long NANOSECONDS_PER_TURN = (long) (1/TURNS_PER_SECOND * 1000000000);
	
	public static final double CASTLE_SIZE = 125;
	public static final double OST_MIN_DISTANCE_FROM_CASTLE = 30; 
	
	public static final double OST_MIN_DISTANCE = 10;
	public static final double OST_SIZE = 10;
	
	public static final int IA_CASTLE_NUMBER = 3; //6 max
	public static final double NEUTRAL_CASTLE_NUMBER = 6;
	public static final int IA_NEUTRAL_CASTLE_MAX_LEVEL = 5;
	
	public static final int NB_TRIES_TO_SPAWN = 1000;
	
	public static final int CASTLE_DOOR_OUT_FLOW_LIMIT = 3;
	public static final int POSITION_DEVIATION = 10;
	
	public static final int ARMY_INIT[] = {50, 50, 50};
	public static final String[] LIST_CASTLE_NAME = {"Aalongue", "Abbaud", "Abbon", "Abelène", "Abran", "Abzal", "Acelin", "Achaire", "Achard", "Acheric", "Adalard", "Adalbaud", "Adalbéron", "Adalbert", "Adalelme", "Adalgaire", "Adalgise", "Adalicaire", "Adalman", "Adalric", "Adebran", "Adélard", "Adelbert", "Adelin", "Adenet", "Adhémar", "Adier", "Adinot", "Adolbert", "Adon", "Adoul", "Adrier", "Adson", "Agambert", "Aganon", "Agebert", "Agelmar", "Agelric", "Agenulf", "Agerad", "Ageran", "Agilbert", "Agilmar", "Aglebert", "Agmer", "Agnebert", "Agrestin", "Agrève", "Aibert", "Aicard", "Aimbaud", "Aimin", "Aimoin", "Airard", "Airy", "Alard", "Albalde", "Albaud", "Albéron", "Alboin", "Albuson", "Alchaire", "Alchas", "Alcuin", "Alleaume", "Amanieu", "Amat", "Amblard", "Anaclet", "Ansbert", "Anselin", "Ansoald", "Archambaud", "Arembert", "Arnat", "Artaud", "Aubry", "Authaire", "Avold", "Ayoul"};
		
	/* SUITE DE LA LISTE : Barnoin, Barral, Baudri, Bérard, Bérenger, Bernon, Bettolin, Betton, Brunon, Burchard, Caribert, Centule, Childebert, Chilpéric, Cillien, Clodomir, Clotaire, Cloud, Colomban, Conan, Conrad, Cybard, Dacien, Dadon, Dalmace, Dambert, Dioclétien, Doat, Drogon, Durand, Eadwin, Ebbon, Ebehard, Eddo, Edwin, Egfroi, Égilon, Eilbert, Einold, Éon, Ermenfred, Ermengaud, Ernée, Ernold, Ernoul, Eumène, Eunuce, Euric, Eustaise, Euverte, Evroult, Fleuret, Flocel, Flodoard, Flouard, Flour, Floxel, Folquet, Fortunat, Foulque, Frajou, Frambault, Frambourg, Frameric, Francaire, Fulbert, Gailhart, Gaillon, Garréjade, Gaubert, Gerbert, Giboin, Gildric, Gislebert, Godomer, Gossuin, Guéthenoc, Guibin, Guiscard, Hatton, Haynhard, Héribert, Herlebald, Herlebauld, Herlemond, Hildebald, Hildebrand, Hilduin, Hoel, Honfroi, Hugon, Humbaud, Isembert, Ithier, Jacquemin, Jacut, Lagier, Lambert, Lancelin, Léothéric, Lidoire, Lisiard, Lothaire, Lubin, Maïeul, Malulf, Marcuard, Maric, Materne, Matfrid, Matifas, Maur, Mauront, Mesmin, Milon, Odo, Oldaric, Orderic, Oricle, Premon, Rachio, Radoald, Radulf, Raginard, Raimbaut, Raimbert, Rainier, Rainon, Ramnulf, Ranulfe, Rataud, Rodron, Romary, Roscelin, Rostang, Salvin, Savaric, Savary, Sébaste, Senoc, Sicard, Siegebert, Sifard, Sigebert, Taillefer, Taurin, Théodebert, Théodemar, Theoderich, Théodran, Thérouanne, Thiégaud, Ursicin, Ursion, Vantelme, Volusien, Warin, Wigeric, Willibert, Wulfoald, Wulgrin*/
	
	public static final int NB_TROOP_TYPES = TroopType.values().length;
	
	public static final String PLAYER_NAME = "Player";
}