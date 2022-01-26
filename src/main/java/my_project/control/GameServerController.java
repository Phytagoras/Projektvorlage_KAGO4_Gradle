package my_project.control;

import KAGO_framework.model.abitur.netz.Server;
import my_project.model.ConnectedEntity;

import java.util.ArrayList;

public class GameServerController extends Server {
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
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getIp().equals(pClientIP)){
                currentConnectedEntity = players.get(i);
                break;
            }
        }
        if (currentConnectedEntity == null) return;              //sollte NIE vorkommen
        String[] commandFragments = pMessage.split(" ");
        if (commandFragments.length > 1){
            if(handleCommand(pClientIP, pClientPort, commandFragments, currentConnectedEntity)){
                //erfolgreich
            }else {
                //nicht erfolgreich
            }
        } else if (commandFragments[0].equalsIgnoreCase("help")){
            switch (currentConnectedEntity.getHelpCounter()) {
                default -> send(pClientIP, pClientPort, "HIER HILFE...NIMM");
            }

        } else {
            send(pClientIP, pClientPort, "Du kannst jederzeit nach Hilfe fragen!");
        }
    }

    private boolean handleCommand(String pClientIP, int pClientPort, String[] commandFragments, ConnectedEntity currentConnectedEntity){
        if (!currentConnectedEntity.isInit()){
            if (commandFragments.length == 2){
                if (commandFragments[0].equalsIgnoreCase("joinGame")){
                    if (!commandFragments[1].isEmpty()){
                        currentConnectedEntity.setInit(true);
                        currentConnectedEntity.setName(commandFragments[1]);
                        gameController.newPlayer(currentConnectedEntity.getId(), currentConnectedEntity.getName());
                    }
                }
            }
        }else {
            //andere CommandsUeberpruefen
        }

        return false;
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, "Bye");
    }
}
