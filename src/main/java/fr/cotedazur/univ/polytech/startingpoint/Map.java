package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public Plot pond;
    public  ArrayList<Plot>map;
    public Map(){
        this.map=new ArrayList<Plot>();
    }

    public ArrayList<Plot> initializeMap(ArrayList<Plot> map){
        map.putPlot(pond);
        return map;
    }

    public ArrayList<Plot> putPlot(Plot parcel) {
        if (isSpaceFree(parcel) == true) {
            map.add(parcel);
            return map;
        }
    }
    public ArrayList<Plot> getMap(){
        return map;
    }

    public boolean isIrrigated(Plot p){
        return false;
    }
    public boolean isSpaceFree(Plot parcel){
        for(int i=0;i<map.size();i++){
            if(parcel==map.get(i)){
                return true;
            }
        }
        return false;
    }
    private ArrayList<Position> closestAvailableSpace(Plot plot){
        ArrayList<Position> positionsAvailable;
        for(int i=0;i<plot.getPosition.closestPositions().size();i++){
            for(int j=0;j<map.size();j++){
                if(map.get(j).getPosition()!=plot.getPosition().closestPositions().get(i){
                    positionsAvailable.add(plot.getPosition().closestPositions().get(i));
                }
            }
        }
        return positionsAvailable;
    }
}
