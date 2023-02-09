package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bot.BotMbappe;
import fr.cotedazur.univ.polytech.startingpoint.bot.BotProfile;
import fr.cotedazur.univ.polytech.startingpoint.bot.BotSprint;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.BotStatisticProfile;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.StatisticManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVManagerTest {

    @Test
    void setData() {
        BotSprint botSprint = new BotSprint();
        BotMbappe botMbappe = new BotMbappe();
        BotProfile botProfile1 = new BotProfile(botSprint, "BotSprint");
        BotProfile botProfile2 = new BotProfile(botMbappe, "BotMbappe");
        List<BotProfile> listOfBotProfiles = List.of(botProfile1, botProfile2);
        StatisticManager statisticManager = new StatisticManager();
        statisticManager.initBotsStatisticsProfiles(listOfBotProfiles);
        CSVManager csvManager = new CSVManager();
        csvManager.setData(statisticManager.getBotsStatisticsProfiles());
        assertEquals(2, csvManager.getData().size());
    }

    @Test
    void parseDataIfFileNotExist() {
        BotSprint botSprint = new BotSprint();
        BotMbappe botMbappe = new BotMbappe();
        BotProfile botProfile1 = new BotProfile(botSprint, "BotSprint");
        BotProfile botProfile2 = new BotProfile(botMbappe, "BotMbappe");
        List<BotProfile> listOfBotProfiles = List.of(botProfile1, botProfile2);
        StatisticManager statisticManager = new StatisticManager();
        statisticManager.initBotsStatisticsProfiles(listOfBotProfiles);
        statisticManager.addDrawGame();
        statisticManager.addWinner(botSprint);
        statisticManager.addLoser(botMbappe);
        statisticManager.addGame();
        statisticManager.addRound();
        CSVManager csvManager = new CSVManager();
        List<String[]> listData = new ArrayList<>();
        listData.add(csvManager.getHeader());
        for (BotStatisticProfile botStatisticProfile : statisticManager.getBotsStatisticsProfiles()) {
            String[] botData = new String[7];
            botData[0] = botStatisticProfile.getBotName();
            botData[1] = Integer.toString(botStatisticProfile.getNbVictories());
            botData[2] = Integer.toString(botStatisticProfile.getNbDefeats());
            botData[4] = Integer.toString(botStatisticProfile.getNbOfRounds());
            botData[5] = Integer.toString(botStatisticProfile.getNbOfGames());
            botData[6] = Integer.toString(botStatisticProfile.getNbOfRounds() / botStatisticProfile.getNbOfGames());
            listData.add(botData);
        }

        assertEquals(listData, csvManager.parseDataIfFileNotExist(statisticManager.getNbOfDrawGames()));
    }

    @Test
    void parseDataIfFileExist() {

    }

    @Test
    void createFileAndDirectoryIfNotExist() {
    }

    @Test
    void saveData() {
    }
}