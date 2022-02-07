package my_project.control;

import KAGO_framework.model.abitur.netz.Server;
import my_project.model.ConnectedEntity;

import java.util.ArrayList;

public class GameServerController extends Server{
    private final GameController gameController;
    private final ArrayList<ConnectedEntity> players;
    int id = 0;

    public GameServerController(GameController pGameController){
        super(6666);
        gameController = pGameController;
        players = new ArrayList<>();
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, "Hello there - General Kenobi");
        players.add(new ConnectedEntity(pClientIP, id++));
    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage){
        ConnectedEntity currentConnectedEntity = null;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getIp().equals(pClientIP)){
                currentConnectedEntity = players.get(i);
                break;
            }
        }
        if(currentConnectedEntity == null) return;              //sollte NIE vorkommen
        String[] commandFragments = pMessage.split(" ");
        if(commandFragments.length > 1){
            if(handleCommand(pClientIP, pClientPort, commandFragments, currentConnectedEntity)){
                //erfolgreich
            }else{
                //nicht erfolgreich
            }
        }else if(commandFragments[0].equalsIgnoreCase("help")){
            switch(currentConnectedEntity.getHelpCounter()){                              //Help Anweisungen fehlen noch
                case(0) -> send(pClientIP, pClientPort, "First thing to do is, to 'joinGame'...you also need a Name");
                case(1) -> send(pClientIP, pClientPort, "after a command there must be a name of the player selected");
                case(2) -> send(pClientIP, pClientPort, "Then you need to 'move'");
                case(3) -> send(pClientIP, pClientPort, "You can 'spawn' something... You just need to say what you wanna spawn and to whom");
                case(4) -> send(pClientIP, pClientPort, "you can spawn a 'banana' and a 'portal'");
                case(5) -> send(pClientIP, pClientPort, "You can 'destroy' something... You just need to say what you wanna spawn and to whom");
                case(6) -> send(pClientIP, pClientPort, "You can change the movement speed :)");
                default -> send(pClientIP, pClientPort, "HIER HILFE...NIMM");
            }
            currentConnectedEntity.setHelpCounter(currentConnectedEntity.getHelpCounter() + 1);

        }else{
            send(pClientIP, pClientPort, "Du kannst jederzeit nach Hilfe (oder Englisch 'help') fragen!");
        }
    }

    private boolean handleCommand(String pClientIP, int pClientPort, String[] commandFragments, ConnectedEntity currentConnectedEntity){
        if(!currentConnectedEntity.isInit()){
            if(commandFragments.length == 2){
                if(commandFragments[0].equalsIgnoreCase("joinGame") && !commandFragments[1].isEmpty()){
                    currentConnectedEntity.setInit(true);
                    currentConnectedEntity.setName(commandFragments[1]);
                    gameController.newPlayer(currentConnectedEntity.getId(), currentConnectedEntity.getName());
                    return true;
                }
            }
        }else{
            //andere CommandsUeberpruefen
            if(commandFragments.length == 2){
                if(commandFragments[0].equalsIgnoreCase("move") && !commandFragments[1].isEmpty()){
                    boolean found = false;
                    for(ConnectedEntity connectedEntity : players){
                        if(connectedEntity.getName().equals(commandFragments[1])){  //wird fuer jeden Player mit dem Namen ausgefuehrt
                            gameController.movePlayer(connectedEntity.getId());
                            found = true;
                        }
                    }
                    if(found) return true;
                    else{
                        send(pClientIP, pClientPort, "Spieler existiert nicht!");
                        return false;
                    }
                }
            }else if(commandFragments.length == 3){
                if(commandFragments[0].equalsIgnoreCase("setSpeed") && !commandFragments[1].isEmpty() && !commandFragments[2].isEmpty()){
                    boolean found = false;
                    double multiplier;
                    try{
                        multiplier = Double.parseDouble(commandFragments[1]);
                    }catch(Exception e){
                        this.send(pClientIP, pClientPort, "Multiplikator muss eine Kommazahl sein!");
                        return false;
                    }
                    //soll der Multiplier noch begrenzt werden?
                    for(ConnectedEntity connectedEntity : players){
                        if(connectedEntity.getName().equals(commandFragments[2])){  //wird fuer jeden Player mit dem Namen ausgefuehrt
                            gameController.changeSpeed(connectedEntity.getId(), multiplier);
                            found = true;
                        }
                    }
                    if(found) return true;
                    else{
                        send(pClientIP, pClientPort, "Spieler existiert nicht!");
                        return false;
                    }
                }else if(commandFragments[0].equalsIgnoreCase("spawn") && !commandFragments[1].isEmpty() && !commandFragments[2].isEmpty()){
                    boolean found = false;
                    int obj;
                    switch(commandFragments[1]){
                        case ("Banana") -> obj = 0;
                        case ("Portal") -> obj = 1;
                        default -> {
                            send(pClientIP, pClientPort, "Objekt gibt es nicht...");
                            return false;
                        }
                    }
                    for(ConnectedEntity connectedEntity : players){
                        if(connectedEntity.getName().equals(commandFragments[2])){  //wird fuer jeden Player mit dem Namen ausgefuehrt
                            gameController.spawnObject(obj, connectedEntity.getId());
                            found = true;
                        }
                    }
                    if(found) return true;
                    else{
                        send(pClientIP, pClientPort, "Spieler existiert nicht!");
                        return false;
                    }
                }else if(commandFragments[0].equalsIgnoreCase("destroy") && !commandFragments[1].isEmpty() && !commandFragments[2].isEmpty()){
                    boolean found = false;
                    int obj;
                    switch(commandFragments[1]){
                        case ("Banana") -> obj = 0;
                        case ("Portal") -> obj = 1;
                        default -> {
                            send(pClientIP, pClientPort, "Objekt gibt es nicht...");
                            return false;
                        }
                    }
                    for(ConnectedEntity connectedEntity : players){
                        if(connectedEntity.getName().equals(commandFragments[2])){  //wird fuer jeden Player mit dem Namen ausgefuehrt
                            gameController.destroyObject(obj, connectedEntity.getId());
                            found = true;
                        }
                    }
                    if(found) return true;
                    else{
                        send(pClientIP, pClientPort, "Spieler existiert nicht!");
                        return false;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, "Bye");
        for (int i=0; i<players.size(); i++){
            if(players.get(i).getIp().equals(pClientIP)){
                gameController.removePlayer(players.get(i).getId());
                players.remove(i);
                i--;
            }
        }
    }
}
