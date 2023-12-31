package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.Game;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.WeatherType;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.*;
import fr.cotedazur.univ.polytech.startingpoint.game.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.game.objectives.ObjectivePlots;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotSprintTest {

    @Mock
    Referee mockedReferee = mock(Game.class);
    Map mockedMap = mock(Map.class);

    @Test
    void play() {
        Playable botSprint = new BotSprint();
        botSprint.setEnvironment(mockedReferee, mockedMap);
        when(mockedReferee.getMyObjectives(botSprint)).thenReturn(List.of());
        Action resutl = botSprint.play(List.of(), WeatherType.QUESTIONMARK);
        assertEquals(ActionType.PICK_OBJECTIVE, resutl.toType());

        List<Objective> objectives = new ArrayList<>();
        Objective objective = new ObjectivePlots(4, new Pattern());
        for (int i = 0; i < 5; ++i) {
            objectives.add(objective);
        }
        mockedMap.putPlot(new Plot(PlotType.GREEN, new Position(1, 1)));
        mockedMap.putPlot(new Plot(PlotType.GREEN, new Position(0, 1)));

        when(mockedReferee.getMyObjectives(botSprint)).thenReturn(objectives);
        resutl = botSprint.play(List.of(), WeatherType.QUESTIONMARK);
        //assertEquals(ActionType.PUT_IRRIGATION, resutl.toType());
    }
}