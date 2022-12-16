package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class BotProfil {

    private Bot bot_;
    private ArrayList<Objective> objectives_;
    private int points_;
    private int nbCompletedObjective_;

    public BotProfil(Bot bot){
        bot_ = bot;
        objectives_ = new ArrayList<>();
        points_ = 0;
        nbCompletedObjective_ = 0;
    }

    public Bot getBot_() {
        return bot_;
    }

    public int getPoints_() {
        return points_;
    }

    public int getNbCompletedObjective_() {return nbCompletedObjective_;}

    public ArrayList<Objective> getObjectives_() {
        return objectives_;
    }

    public void addObjective(Objective objective){
        objectives_.add(objective);
    }

    public void setObjectiveCompleted(Objective objective){
        objectives_.remove(objective);
        ++nbCompletedObjective_;
        points_ += objective.getPoint();
    }
}
