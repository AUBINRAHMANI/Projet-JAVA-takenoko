package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.objective.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {


    @Test
    void pickObjectiveTest() {
        Deck<Objective> deck = new Deck<>();
        Objective objective = new ObjectivePlots(5, (Pattern) null);
        deck.addCard(objective);
        GameEngine gameEngine = new GameEngine(deck, null, null);

        assertEquals(objective , gameEngine.pickObjective());
    }

    @Test
    void pickPlotTest() {
        Position position = new Position(2,2);
        Deck<Plot> deck = new Deck<>();
        deck.addCard(new Plot(PlotType.GREEN,position));
        GameEngine gameEngine = new GameEngine(null, deck, null);

        assertEquals(PlotType.GREEN , gameEngine.pickPlot().getType());
    }

    @Test
    void askGetMapTest() {
        Map map = new Map();
        GameEngine gameEngine = new GameEngine(null, null, map);
        assertEquals(map, gameEngine.getMap());
    }


    @Test
    void computeObjectivePlotTest(){
        Map map = new Map();
        Pattern pattern= new Pattern();
        GameEngine gameEngine = new GameEngine(null, null, map);
        Plot plot1 = new Plot(PlotType.GREEN, new Position(-1, 1));
        Plot plot2 = new Plot(PlotType.GREEN, new Position(0, 0));
        Plot plot3 = new Plot(PlotType.GREEN, new Position(0, -1));
        Plot plot4 = new Plot(PlotType.GREEN, new Position(1, 0));

        pattern.add(plot1);
        pattern.add(plot2);
        pattern.add(plot3);
        pattern.add(plot4);

        Plot plot5 = new Plot(PlotType.GREEN, new Position(1, 0));
        Plot plot6 = new Plot(PlotType.GREEN, new Position(2, 0));
        Plot plot7 = new Plot(PlotType.GREEN, new Position(0, 1));
        Plot plot8 = new Plot(PlotType.GREEN, new Position(1, 1));

        gameEngine.askToPutPlot(plot5);
        gameEngine.askToPutPlot(plot7);
        gameEngine.askToPutPlot(plot8);
        assertFalse(gameEngine.computeObjectivePlot(new Pattern(pattern), plot7));
        gameEngine.askToPutPlot(plot6);
        assertTrue(gameEngine.computeObjectivePlot(pattern, plot8));
    }
/*
    @Test
    void computeObjectiveGardenerTest(ArrayList<Plot> bambouPlots, boolean improvement){

    }
 */
    @Test
    void moveGardenerTest(){
        Map map = new Map();
        Plot plot = new Plot(PlotType.GREEN, new Position(0,0));
        Plot plot2 = new Plot(PlotType.GREEN, new Position(0,1));
        map.putPlot(plot);
        map.putPlot(plot2);
        GameEngine gameEngine = new GameEngine(null, null, map);
        gameEngine.moveGardener(new Position(0,1));
        assertEquals(new Position(0, 1), gameEngine.getGardenerPosition());

        gameEngine.moveGardener(new Position(3,1));
        assertEquals(new Position(0, 1), gameEngine.getGardenerPosition());
    }

    @Test
    public void growBambouTest(){
        Map map = new Map();
        Plot plot = new Plot(PlotType.GREEN, new Position(0,1));
        plot.isIrrigatedIsTrue();
        map.putPlot(plot);
        GameEngine gameEngine = new GameEngine(null, null, map);
        gameEngine.moveGardener(new Position(0,1));
        gameEngine.growBambou();
        assertEquals(1, plot.getNumberOfBambou());

    }

    @Test
    public void computeObjectiveGardener(){
        Map map = new Map();
        Plot plot1 = new Plot(PlotType.GREEN, new Position(0,1));
        Plot plot2 = new Plot(PlotType.GREEN, new Position(1,1));
        Plot plot3 = new Plot(PlotType.GREEN, new Position(1,0));
        plot1.isIrrigatedIsTrue();
        plot2.isIrrigatedIsTrue();
        plot3.isIrrigatedIsTrue();

        for(int i=0; i<4; ++i)plot1.growBambou();
        for(int i=0; i<3; ++i){
            plot2.growBambou();
            plot3.growBambou();
        }
        map.putPlot(plot1);
        map.putPlot(plot2);
        map.putPlot(plot3);

        GameEngine gameEngine = new GameEngine(null, null, map);
        gameEngine.moveGardener(new Position(0,1));


        assertTrue(gameEngine.computeObjectiveGardener(4, PlotType.GREEN, false, 1));
        gameEngine.moveGardener(new Position(0, 0));
        assertFalse(gameEngine.computeObjectiveGardener(4, PlotType.GREEN, false, 1));
        gameEngine.moveGardener(new Position(1, 1));
        assertTrue(gameEngine.computeObjectiveGardener(3, PlotType.GREEN, false,  2));
        gameEngine.moveGardener(new Position(0, 1));
        assertFalse(gameEngine.computeObjectiveGardener(3, PlotType.GREEN, false, 2));
    }
}