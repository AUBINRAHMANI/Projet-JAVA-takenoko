package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.*;
import fr.cotedazur.univ.polytech.startingpoint.action.*;
import fr.cotedazur.univ.polytech.startingpoint.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.debugInterface.MapInterface;
import fr.cotedazur.univ.polytech.startingpoint.logger.Loggeable;
import fr.cotedazur.univ.polytech.startingpoint.objective.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;


public class Game implements DeckSignal, Referee, Loggeable {

    static int MAX_NB_ROUND = 100;
    final int NB_OBJECTIVE_TO_FINISH = 9;
    GameEngine gameEngine_;
    ArrayList<BotProfil> botProfils_;
    MapInterface _mapInterface;
    private int nbActions = 2;

    private int nombreObjectifNull = 0;


    public Game( boolean debug){
        botProfils_                     = new ArrayList<>();
        Deck<Objective> objectiveDeck   = generateObjectiveDrawPile();
        Deck<Plot> plotDeck             = generatePlotDrawPile();
        gameEngine_                     = new GameEngine( objectiveDeck, plotDeck, new Map());
        botProfils_.add(new BotProfil(new Bot(this, gameEngine_.getMap(),"Ronaldo")));
        botProfils_.add(new BotProfil(new Bot(this, gameEngine_.getMap(), "Messi")));
        if(debug){
            _mapInterface = new MapInterface();
            _mapInterface.drawMap(gameEngine_.getMap(), gameEngine_.getGardenerPosition(), gameEngine_.getPandaPosition());
        }
        else {
            _mapInterface = null;
        }
    }
    public Game(){
        this(false);
    }

    public boolean start(){
        do {
            this.nombreObjectifNull++;
            for(BotProfil botProfil : botProfils_){
                gameEngine_.drawWeather();
                LOGGER .finest("Tour de " + botProfil.getBot().getBotName() + " : ");
                this.applyChangesDueToWeather(botProfil);
                if(_mapInterface != null) {
                    _mapInterface.drawMap(gameEngine_.getMap(), gameEngine_.getGardenerPosition(), gameEngine_.getPandaPosition());
                }
            }
            LOGGER.finest( "Nombre de tours :" + this.nombreObjectifNull);
        }while (!checkFinishingCondition());
        BotProfil winner = checkWinner();
        printWinner(winner);
        return true;
    }


    public void doActions(BotProfil botProfil, int nbActions){
        List<ActionType> banActionTypes = new ArrayList<>();

        for(int i = 0; i < nbActions; i++) {
            Action action = botProfil.getBot().play(banActionTypes, gameEngine_.getWeather());
            LOGGER.finer("Action : " + action);
            if (action!=null && ( banActionTypes.contains(action.toType())) ==false){
                action.play(this, gameEngine_);
                banActionTypes.add(action.toType());
                action.verifyObjectiveAfterAction(this);
            }
        }
    }

    public void applyChangesDueToWeather(BotProfil botProfil){
        switch (gameEngine_.getWeatherType()){
            case SUN :
                int nbActionSun = this.nbActions + 1;
                doActions(botProfil, nbActionSun);
                break;
            case RAIN :
            case THUNDER :
            case WIND :
            case CLOUD :
            case QUESTIONMARK:
                doActions(botProfil, nbActions);
                break;
        }
    }

    public boolean checkFinishingCondition(){
        for(BotProfil botProfil : botProfils_){
            if(botProfil.getNbCompletedObjective() >= NB_OBJECTIVE_TO_FINISH)return true;
            else if(this.nombreObjectifNull >= MAX_NB_ROUND) return true;
        }
        return false;
    }

    @Override
    public void emptyDeck() {
        gameEngine_.regenerateDecks(generateObjectiveDrawPile(), generatePlotDrawPile());
    }

    private Deck<Objective> generateObjectiveDrawPile(){
        Deck<Objective> objectiveDeck = new Deck<>(this);
        Random rand = new Random();
        int upperRandForPlotType = 3;

        for (int i=0 ; i<20 ; ++i){
            objectiveDeck.addCard(new ObjectivePlots(rand.nextInt(4)+1, new Pattern()));
        }
        for (int i=0 ; i<20 ; ++i){
            //int nbBambous = rand.nextInt(2)+3;
            int nbBambous = 4;
            if(nbBambous == 3){
                objectiveDeck.addCard(new ObjectiveGardener(rand.nextInt(4)+1, nbBambous, PlotType.values()[rand.nextInt(upperRandForPlotType)+1], false,rand.nextInt(3)+2));
            }
            else {
                objectiveDeck.addCard(new ObjectiveGardener(rand.nextInt(4)+1, nbBambous, PlotType.values()[rand.nextInt(upperRandForPlotType)+1], false,1));
            }

        }

        for (int i=0 ; i<20 ; ++i) {
            ArrayList<Bambou> bambous = new ArrayList<>();
            for (int j = 0; j < (rand.nextInt(2) + 2); ++j) {
                bambous.add(new Bambou(PlotType.values()[rand.nextInt(upperRandForPlotType) + 1]));
            }
            objectiveDeck.addCard(new ObjectivePanda(rand.nextInt(4) + 1, bambous));
        }
        objectiveDeck.shuffle();
        return objectiveDeck;
    }

    private Deck<Plot> generatePlotDrawPile(){
        Deck<Plot> plotDeck = new Deck<>(this);
        Random rand = new Random();
        int upperRandForPlotType = 3;

        for(int i=0 ; i<60 ; ++i){
            plotDeck.addCard(new Plot(PlotType.values()[rand.nextInt(upperRandForPlotType)+1]));
        }
        plotDeck.shuffle();
        return plotDeck;
    }

    public boolean pickObjective(Bot bot){
        Objective objective=  gameEngine_.pickObjective();

        for(BotProfil botProfil : botProfils_){
            if(bot == botProfil.getBot()){
                botProfil.addObjective(objective);
                LOGGER.finer( bot.getBotName() +" a prix un objectif :" + objective);
                return true;
            }
        }
        return false;
    }

    public List<Plot> pickPlot(){
        return gameEngine_.pickPlot();
    }

    public boolean computeObjectivesPlot(Plot lastPlacedPlot){
        boolean result = false;
        ArrayList<Objective> validatedObjective = new ArrayList<>();
        for(BotProfil botProfil : botProfils_ ){
            for(Objective objective : botProfil.getObjectives()){
                if(objective.verifyPlotObj(gameEngine_, lastPlacedPlot)){
                    String botName = botProfil.getBot().getBotName();
                    validatedObjective.add(objective);
                    botProfil.setObjectiveCompleted(objective);
                    LOGGER.finer( "L'objectif suivant a été validé : " + objective );
                    LOGGER.finer( botName + " gagne " + objective.getPoint() + " points");
                    LOGGER.finer( "Le score de "+ botName +" = " + botProfil.getPoints() + " points");
                }
            }
            botProfil.getObjectives().removeAll(validatedObjective);
            if(validatedObjective.isEmpty()==false)result = true;
        }
        return result;
    }

    public boolean computeObjectivesGardener(){
        boolean result =false;
        ArrayList<Objective> validatedObjective = new ArrayList<>();
        for(BotProfil botProfil : botProfils_ ){
            for(Objective objective : botProfil.getObjectives()){
                if(objective.verifyGardenerObj(gameEngine_)){
                    String botName = botProfil.getBot().getBotName();
                    validatedObjective.add(objective);
                    botProfil.setObjectiveCompleted(objective);
                    LOGGER.finer( "L'objectif suivant a été validé : " + objective );
                    LOGGER.finer( botName + " gagne " + objective.getPoint() + " points");
                    LOGGER.finer( "Le score de "+ botName +" = " + botProfil.getPoints() + " points");
                }
            }
            botProfil.getObjectives().removeAll(validatedObjective);
            if(validatedObjective.isEmpty()==false) result= true;
        }
        return result;
    }

    public Position getGardenerPosition(){
        return gameEngine_.getGardenerPosition();
    }
    public Position getPandaPosition() {
        return gameEngine_.getPandaPosition();
    }

    public boolean computeObjectivesPanda(){
        boolean result = false;
        ArrayList<Objective> validatedObjective = new ArrayList<>();
        for(BotProfil botProfil : botProfils_ ){
            for(Objective objective : botProfil.getObjectives()){
                if(objective.verifyPandaObj(gameEngine_, botProfil)){
                    String botName = botProfil.getBot().getBotName();
                    validatedObjective.add(objective);
                    botProfil.setObjectiveCompleted(objective);
                    LOGGER.finer( "L'objectif suivant a été validé : " + objective );
                    LOGGER.finer(botName + " gagne " + objective.getPoint() + " points");
                    LOGGER.finer("Le score de "+ botName +" = " + botProfil.getPoints() + " points");
                }
            }
            botProfil.getObjectives().removeAll(validatedObjective);
            if(!validatedObjective.isEmpty()) result=true;
        }
        return result;
    }

    public BotProfil checkWinner() {
        BotProfil winner = null;
        if (this.nombreObjectifNull < MAX_NB_ROUND) {
            winner = botProfils_.get(0);
            for (BotProfil botProfil : botProfils_) {
                if (botProfil.getPoints() > winner.getPoints()) {
                    winner = botProfil;
                }
            }
        }
        return winner;
    }

    public List<Objective> getMyObjectives(Bot bot){
        for(BotProfil botProfil : botProfils_){
            if(bot == botProfil.getBot()){
                return botProfil.getObjectives();
            }
        }
        return null;
    }

    public void printWinner(BotProfil botProfil) {
        if (botProfil==null) {
            LOGGER.fine("Match nul ! Aucun bot n'a su completer un objectif pendant "+ MAX_NB_ROUND +" tours");
        } else {
            LOGGER.fine(botProfil.getBot().getBotName() + " gagne avec : " + botProfil.getPoints() + " points");
        }
    }

    public List<Bambou> getMyBambous(Bot bot) {
        for(BotProfil botProfil : botProfils_){
            if(botProfil.getBot()==bot)return botProfil.getBambous();
        }
        return null;
    }

    public void addBamboutToBot(Bot bot, Bambou bambou) {
        for(BotProfil botProfil : botProfils_){
            if(botProfil.getBot()==bot){
                botProfil.addBanbou( bambou );
            }
        }
    }
}
