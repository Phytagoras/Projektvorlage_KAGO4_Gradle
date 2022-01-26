package my_project.control;

import java.util.ArrayList;

import my_project.Config;
import my_project.model.Player;

public class GameController {

    ArrayList<Player> allPlayers;
    ArrayList<Object> allObjects;

    public GameController(){
        allPlayers = new ArrayList<>();

        Thread thread = new Thread(){
            public void run(){
                checkEnd();
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

    public void restartGame(int i){

    }

    public void newPlayer(int id, String name) {
        allPlayers.set(id, new Player(30, 0, name));

        for(int i=0; i<allPlayers.size(); i++){
            allPlayers.get(i).setPos(allPlayers.get(i).getX(), Config.WINDOW_HEIGHT*(id+1)/(allPlayers.size()+1));
        }

        //TODO objekt position
    }

    public void spawnObject(int what, int id){
        /*switch(id) {
            case 0:
                allObjects.add(new Object1());
                break;
        }
         */
    }

    public void movePlayer(int id){
        allPlayers.get(id).move();
    }

    public void changeSpeed(int id, double multiplicator){
        allPlayers.get(id).changeSpeed(multiplicator);
    }


}
