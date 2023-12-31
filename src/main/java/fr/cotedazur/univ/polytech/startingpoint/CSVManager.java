package fr.cotedazur.univ.polytech.startingpoint;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import fr.cotedazur.univ.polytech.startingpoint.logger.Loggeable;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.BotStatisticProfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVManager implements Loggeable {

    private List<BotStatisticProfile> botsStatisticsProfiles;
    private File file;

    public void exportData(List<BotStatisticProfile> botsStatisticsProfiles, int nbDrawMatch, String fileName) {
        this.file = new File(fileName);
        this.setData(botsStatisticsProfiles);
        if (file.exists()) {
            LOGGER.info("File already exist");
            this.saveData(this.parseDataIfFileExist(getCSVFile(), nbDrawMatch));
        } else {
            this.createFileAndDirectoryIfNotExist();
            LOGGER.info("File doesn't exist");
            this.saveData(this.parseDataIfFileNotExist(nbDrawMatch));
        }
    }

    public List<BotStatisticProfile> getData() {
        return botsStatisticsProfiles;
    }

    public void setData(List<BotStatisticProfile> botsStatisticsProfiles) {
        this.botsStatisticsProfiles = botsStatisticsProfiles;
    }

    public List<String[]> getCSVFile() {
        Path path = Paths.get(this.file.toURI());
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path)) {
            csvReader(reader, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void csvReader(Reader reader, List<String[]> list) {
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            LOGGER.warning("Error while reading the file");
        }
    }

    public String[] getHeader() {
        String[] header = new String[21];
        header[0] = "Bot";
        header[1] = "Victories";
        header[2] = "Defeats";
        header[3] = "Draws";
        header[4] = "Number of rounds";
        header[5] = "Number of games";
        header[6] = "Number of rounds per game";
        header[7] = "Number of deal";
        header[8] = "Number of deal move gardener";
        header[9] = "Number of deal move panda";
        header[10] = "Number of deal pick objective";
        header[11] = "Number of deal put irrigation";
        header[12] = "Number of deal put plot";
        header[13] = "Number of deal rain";
        header[14] = "Number of deal thunder";
        header[15] = "Number of objective gardener";
        header[16] = "Number of objective panda";
        header[17] = "Number of objective plots";
        header[18] = "Number of points objective gardener";
        header[19] = "Number of points objective panda";
        header[20] = "Number of points objective plot";
        return header;
    }

    public List<String[]> parseDataIfFileNotExist(int nbDrawMatch) {
        List<String[]> data = new ArrayList<>();
        String[] header = getHeader();
        data.add(header);
        for (BotStatisticProfile botStatisticProfile : botsStatisticsProfiles) {
            String[] statBot = new String[21];
            statBot[0] = botStatisticProfile.getBotName();
            statBot[1] = Integer.toString(botStatisticProfile.getNbVictories());
            statBot[2] = Integer.toString(botStatisticProfile.getNbDefeats());
            statBot[4] = Integer.toString(botStatisticProfile.getNbOfRounds());
            statBot[5] = Integer.toString(botStatisticProfile.getNbOfGames());
            statBot[6] = Integer.toString(botStatisticProfile.getNbOfRounds() / botStatisticProfile.getNbOfGames());
            statBot[7] = Integer.toString(botStatisticProfile.getNbOfActionsPerGame());
            statBot[8] = Integer.toString(botStatisticProfile.getDealMoveGardener());
            statBot[9] = Integer.toString(botStatisticProfile.getDealMovePanda());
            statBot[10] = Integer.toString(botStatisticProfile.getDealPickObjective());
            statBot[11] = Integer.toString(botStatisticProfile.getDealPutIrrigation());
            statBot[12] = Integer.toString(botStatisticProfile.getDealPutPlot());
            statBot[13] = Integer.toString(botStatisticProfile.getDealRain());
            statBot[14] = Integer.toString(botStatisticProfile.getDealThunder());
            statBot[15] = Integer.toString(botStatisticProfile.getNumberObjectiveGardener());
            statBot[16] = Integer.toString(botStatisticProfile.getNumberObjectivePanda());
            statBot[17] = Integer.toString(botStatisticProfile.getNumberObjectivePlots());
            statBot[18] = Integer.toString(botStatisticProfile.getNumberPointsObjectiveGardener());
            statBot[19] = Integer.toString(botStatisticProfile.getNumberPointsObjectivePanda());
            statBot[20] = Integer.toString(botStatisticProfile.getNumberPointsObjectivePlot());
            data.add(statBot);
        }
        data.get(1)[3] = Integer.toString(nbDrawMatch);
        return data;
    }

    public List<String[]> parseDataIfFileExist(List<String[]> data, int nbDrawMatch) {

        for (int i = 1; i < data.size(); i++) {
            for (BotStatisticProfile botStatisticProfile : botsStatisticsProfiles) {
                if (data.get(i)[0].equals(botStatisticProfile.getBotName())) {
                    data.get(i)[1] = Integer.toString(Integer.parseInt(data.get(i)[1]) + botStatisticProfile.getNbVictories());
                    data.get(i)[2] = Integer.toString(Integer.parseInt(data.get(i)[2]) + botStatisticProfile.getNbDefeats());
                    data.get(i)[4] = Integer.toString(Integer.parseInt(data.get(i)[4]) + botStatisticProfile.getNbOfRounds());
                    data.get(i)[5] = Integer.toString(Integer.parseInt(data.get(i)[5]) + botStatisticProfile.getNbOfGames());
                    data.get(i)[6] = Integer.toString((Integer.parseInt(data.get(i)[6]) + botStatisticProfile.getNbOfRounds()) / botStatisticProfile.getNbOfGames());
                    data.get(i)[7] = Integer.toString(Integer.parseInt(data.get(i)[7]) + botStatisticProfile.getNbOfActionsPerGame());
                    data.get(i)[8] = Integer.toString(Integer.parseInt(data.get(i)[8]) + botStatisticProfile.getDealMoveGardener());
                    data.get(i)[9] = Integer.toString(Integer.parseInt(data.get(i)[9]) + botStatisticProfile.getDealMovePanda());
                    data.get(i)[10] = Integer.toString(Integer.parseInt(data.get(i)[10]) + botStatisticProfile.getDealPickObjective());
                    data.get(i)[11] = Integer.toString(Integer.parseInt(data.get(i)[11]) + botStatisticProfile.getDealPutIrrigation());
                    data.get(i)[12] = Integer.toString(Integer.parseInt(data.get(i)[12]) + botStatisticProfile.getDealPutPlot());
                    data.get(i)[13] = Integer.toString(Integer.parseInt(data.get(i)[13]) + botStatisticProfile.getDealRain());
                    data.get(i)[14] = Integer.toString(Integer.parseInt(data.get(i)[14]) + botStatisticProfile.getDealThunder());
                    data.get(i)[15] = Integer.toString(Integer.parseInt(data.get(i)[15]) + botStatisticProfile.getNumberObjectiveGardener());
                    data.get(i)[16] = Integer.toString(Integer.parseInt(data.get(i)[16]) + botStatisticProfile.getNumberObjectivePanda());
                    data.get(i)[17] = Integer.toString(Integer.parseInt(data.get(i)[17]) + botStatisticProfile.getNumberObjectivePlots());
                    data.get(i)[18] = Integer.toString(Integer.parseInt(data.get(i)[18]) + botStatisticProfile.getNumberPointsObjectiveGardener());
                    data.get(i)[19] = Integer.toString(Integer.parseInt(data.get(i)[19]) + botStatisticProfile.getNumberPointsObjectivePanda());
                    data.get(i)[20] = Integer.toString(Integer.parseInt(data.get(i)[20]) + botStatisticProfile.getNumberPointsObjectivePlot());
                }
            }
        }
        data.get(1)[3] = Integer.toString(Integer.parseInt(data.get(1)[3]) + nbDrawMatch);
        return new ArrayList<>(data);
    }

    public void createFileAndDirectoryIfNotExist() {
        Path path = Paths.get(this.file.toPath().getName(1).toUri());
        try {
            Files.createDirectories(path);
            if (!this.file.createNewFile()) {
                LOGGER.warning("Error while creating the file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(List<String[]> data) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(this.file, false));
            for (String[] line : data) {
                writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
