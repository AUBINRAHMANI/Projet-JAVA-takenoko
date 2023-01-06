package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.objective.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {


    @Test
    void pickObjectiveTest() {
        Deck<Objective> deck = new Deck<>();
        Objective objective = new ObjectivePlots(5, null);
        deck.addCard(objective);
        GameEngine gameEngine = new GameEngine(deck, null, null);

        assertEquals(objective , gameEngine.pickObjective());
    }

    @Test
    void pickPlotTest() {
        Deck<Plot> deck = new Deck<>();
        deck.addCard(new Plot(PlotType.GREEN));
        GameEngine gameEngine = new GameEngine(null, deck, null);

        assertEquals(PlotType.GREEN , gameEngine.pickPlot().getType());
    }

    @Test
    void askGetMapTest() {
        Map map = new Map();
        GameEngine gameEngine = new GameEngine(null, null, map);
        assertEquals(map, gameEngine.getMap());
    }
/*
    @Test
    void computeObjectivePlotTest(ArrayList<Plot> pattern){
    }

    @Test
    void computeObjectiveGardenerTest(ArrayList<Plot> bambouPlots, boolean improvement){

    }

 */
    @Test
    void moveGardenerTest(){
        Map map = new Map();
        Plot plot = new Plot(PlotType.GREEN, new Position(0,0));
        Plot plot2 = new Plot(PlotType.GREEN, new Position(2,1));
        map.putPlot(plot);
        map.putPlot(plot2);
        GameEngine gameEngine = new GameEngine(null, null, map);
        gameEngine.moveGardener(new Position(2,1));
        assertEquals(new Position(2, 1), gameEngine.getGardenerPosition());

        gameEngine.moveGardener(new Position(3,1));
        assertEquals(new Position(2, 1), gameEngine.getGardenerPosition());
    }

    @Test
    public void growBambouTest(){
        Map map = new Map();
        Plot plot = new Plot(PlotType.GREEN, new Position(2,2));
        plot.isIrrigatedIsTrue();
        map.putPlot(plot);
        GameEngine gameEngine = new GameEngine(null, null, map);
        gameEngine.moveGardener(new Position(2,2));
        gameEngine.growBambou();
        assertEquals(1, plot.getNumberOfBambou());

    }
}