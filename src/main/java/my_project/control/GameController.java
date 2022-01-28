package my_project.control;

import java.util.ArrayList;

import my_project.Config;
import my_project.model.Player;
import my_project.model.objects.Banana;
import my_project.model.objects.Object;
import my_project.model.objects.Portal;

public class GameController {

    private ArrayList<Player> allPlayers;
    private ArrayList<Object> allObjects;

    public static final int spawnDistance = 200;

    public GameController(){
        allPlayers = new ArrayList<>();

        Thread thread = new Thread(){
            public void run(){
                checkEnd();
                checkCollision();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void checkEnd(){
        for(int i=0; i<allPlayers.size(); i++){
            if(allPlayers.get(i).getX()>Config.WINDOW_WIDTH){
                restartGame(i);
            }
        }
    }

    public void restartGame(int winnnerId){
        for(int i=0; i<allPlayers.size(); i++){
            allPlayers.get(i).setPos(0, Config.WINDOW_HEIGHT*(i+1)/(allPlayers.size()+1));
        }
    }

    public void checkCollision(){
        for(int i=0; i<allPlayers.size(); i++){
            for(int j=0; j<allObjects.size(); j++){
                if(allObjects.get(j).getTrack() == i){
                    if(allPlayers.get(i).getX()+allPlayers.get(i).getWidth()>allObjects.get(j).getX()){
                        allObjects.get(j).doAction(allPlayers.get(i));
                        allObjects.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    public void newPlayer(int id, String name) {
        allPlayers.set(id, new Player(30, 0, name));

        for(int i=0; i<allPlayers.size(); i++){
            allPlayers.get(i).setPos(allPlayers.get(i).getX(), Config.WINDOW_HEIGHT*(i+1)/(allPlayers.size()+1));
        }

        for(int i=0; i<allObjects.size(); i++){
            allObjects.get(i).setPos(allObjects.get(i).getX(), Config.WINDOW_HEIGHT*(allObjects.get(i).getTrack()+1)/(allPlayers.size()+1));
        }
    }

    public void spawnObject(int what, int id){
        switch(what) {
            case 0:
                allObjects.add(new Banana(allPlayers.get(id).getX()+Math.random()*spawnDistance, Config.WINDOW_HEIGHT*(id+1)/(allPlayers.size()+1), id));
                break;
            case 1:
                allObjects.add(new Portal(allPlayers.get(id).getX()+Math.random()*spawnDistance, Config.WINDOW_HEIGHT*(id+1)/(allPlayers.size()+1), id));
                break;
        }
    }

    public void destroyObject(Class<Object> objectClass, int id){
        for(int i=0; i<allObjects.size(); i++) {
            if(allObjects.get(i).getTrack()==id && allObjects.get(i).getClass().equals(objectClass)) {
                allObjects.remove(i);
            }
        }
    }

    public void movePlayer(int id){
        allPlayers.get(id).move();
    }

    public void changeSpeed(int id, double multiplicator){
        allPlayers.get(id).changeSpeed(multiplicator);
    }


}
