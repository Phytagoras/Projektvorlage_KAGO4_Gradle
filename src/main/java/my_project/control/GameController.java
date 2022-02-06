package my_project.control;

import java.util.ArrayList;

import KAGO_framework.control.ViewController;
import my_project.Config;
import my_project.model.Player;
import my_project.model.WinnerText;
import my_project.model.objects.Banana;
import my_project.model.objects.Object;
import my_project.model.objects.Portal;

public class GameController {

    private ViewController viewController;

    private ArrayList<Player> allPlayers;
    private ArrayList<Object> allObjects;

    public static final int spawnDistance = 200;
    public static final int spawnPoint = 0;

    public GameController(ViewController viewController){
        this.viewController = viewController;

        allPlayers = new ArrayList<>();
        allObjects = new ArrayList<>();

        Thread thread = new Thread(){
            public void run(){
                while (true) {
                    checkEnd();
                    checkCollision();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        WinnerText winnerText = null;

        for(int i = 0; i<allPlayers.size(); i++) {
            if(allPlayers.get(i).getId() == winnnerId) {
                winnerText = new WinnerText(allPlayers.get(i).getName());
                viewController.draw(winnerText);
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        viewController.removeDrawable(winnerText);
        for(int i=0; i<allPlayers.size(); i++){
            allPlayers.get(i).setPos(spawnPoint, (double) Config.WINDOW_HEIGHT*(i+1)/(allPlayers.size()+1));
        }
    }

    public void checkCollision(){
        for(int i=0; i<allPlayers.size(); i++){
            for(int j=0; j<allObjects.size(); j++){
                if(allObjects.get(j).getTrack() == allPlayers.get(i).getId()){
                    if(allPlayers.get(i).getX()+allPlayers.get(i).getWidth()>=allObjects.get(j).getX()){
                        allObjects.get(j).doAction(allPlayers.get(i));
                        viewController.removeDrawable(allObjects.get(j));
                        allObjects.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    public void newPlayer(int id, String name) {
        boolean test = false;
        for(int i=0; i<allPlayers.size(); i++) {
            if(allPlayers.get(i).getId() == id) {
                test = true;
            }
        }
        if(!test){
            Player player = new Player(id,spawnPoint, 0, name);
            viewController.draw(player);
            allPlayers.add(player);
        }

        repos();

        System.out.println("Player count: "+allPlayers.size());
    }

    public void removePlayer(int id){
        for(int i=0; i<allPlayers.size(); i++) {
            if(allPlayers.get(i).getId() == id) {
                viewController.removeDrawable(allPlayers.get(i));
                allPlayers.remove(i);
                i--;
            }
        }

        repos();
    }

    private void repos(){
        for(int i=0; i<allPlayers.size(); i++){
            allPlayers.get(i).setPos(allPlayers.get(i).getX(), (double) Config.WINDOW_HEIGHT*(i+1)/(allPlayers.size()+1));

            for(int j=0; j<allObjects.size(); j++){
                if(allObjects.get(j).getTrack()==allPlayers.get(i).getId()) {
                    allObjects.get(j).setPos(allObjects.get(j).getX(), (double) Config.WINDOW_HEIGHT * (i+1) / (allPlayers.size() + 1));
                }
            }
        }
    }

    public void spawnObject(int what, int id){
        for(int i=0; i<allPlayers.size(); i++) {
            if(allPlayers.get(i).getId() == id) {
                switch (what) {
                    case 0:
                        Banana banana = new Banana(allPlayers.get(i).getX() + Math.random() * spawnDistance+spawnDistance, (double) Config.WINDOW_HEIGHT * (i + 1) / (allPlayers.size() + 1), id);
                        viewController.draw(banana);
                        allObjects.add(banana);
                        break;
                    case 1:
                        Portal portal = new Portal(allPlayers.get(i).getX() + Math.random() * spawnDistance+spawnDistance, (double) Config.WINDOW_HEIGHT * (i + 1) / (allPlayers.size() + 1), id);
                        viewController.draw(portal);
                        allObjects.add(portal);
                        break;
                }
            }
        }
    }

    public void destroyObject(int objectClassInt, int id){
        Class objectClass = null;
        switch (objectClassInt){
            case 0:
                objectClass = Banana.class;
                break;
            case 1:
                objectClass = Portal.class;
                break;
        }

        for (int j = 0; j < allObjects.size(); j++) {
            if (allObjects.get(j).getTrack() == id && allObjects.get(j).getClass().equals(objectClass)) {
                viewController.removeDrawable(allObjects.get(j));
                allObjects.remove(j);
                j--;
            }
        }
    }

    public void movePlayer(int id){
        for(int i=0; i<allPlayers.size(); i++) {
            if(allPlayers.get(i).getId() == id) {
                allPlayers.get(i).move();
            }
        }
    }

    public void changeSpeed(int id, double multiplicator){
        for(int i=0; i<allPlayers.size(); i++) {
            if (allPlayers.get(i).getId() == id) {
                allPlayers.get(i).changeSpeed(multiplicator);
            }
        }
    }
}
