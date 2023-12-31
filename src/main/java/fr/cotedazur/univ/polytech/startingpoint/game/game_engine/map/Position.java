package fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Position {

    private int q;
    private int r;
    private int s;

    public Position(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public Position(int x, int y) {
        q = x;
        r = y - (x + (x & 1)) / 2;
        s = -r - q;
    }

    public Position(Position position) {
        q = position.q;
        r = position.r;
        s = position.s;
    }

    public int getQ() {
        return q;
    }

    private int getQ(int x) {
        return x;
    }

    public int getR() {
        return r;
    }

    private int getR(int x, int y) {
        return y - (x + (x & 1)) / 2;
    }

    public int getS() {
        return s;
    }

    private int getS(int x, int y) {
        return -getQ(x) - getR(x, y);
    }

    public int getX() {
        return getQ();
    }

    public int getY() {
        return (r + (q + (q & 1)) / 2);
    }

    public List<Position> closestPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(q, r - 1, s + 1));
        positions.add(new Position(q + 1, r - 1, s));
        positions.add(new Position(q + 1, r, s - 1));
        positions.add(new Position(q, r + 1, s - 1));
        positions.add(new Position(q - 1, r + 1, s));
        positions.add(new Position(q - 1, r, s + 1));
        return positions;
    }

    public void rotate60Right() {
        q *= -1;
        r *= -1;
        s *= -1;
        int qTemp = q;
        int rTemp = r;
        q = s;
        r = qTemp;
        s = rTemp;
    }

    public void translateUP() {
        int rTemp = getR(getX(), getY() + 1);
        s = getS(getX(), getY() + 1);
        r = rTemp;
    }

    public void translateDown() {
        int rTemp = getR(getX(), getY() - 1);
        s = getS(getX(), getY() - 1);
        r = rTemp;
    }

    public void translateRight() {
        int rTemp = getR(getX() + 1, getY() + 1 - (getX() & 1));
        s = getS(getX() + 1, getY() + 1 - (getX() & 1));
        r = rTemp;
        ++q;
    }

    public void translateLeft() {
        int rTemp = getR(getX() - 1, getY() + 1 - (getX() & 1));
        s = getS(getX() - 1, getY() + 1 - (getX() & 1));
        r = rTemp;
        --q;
    }


    public Position plus(Position position) {
        return new Position(q + position.q, r + position.r, s + position.s);
    }

    public Position minus(Position position) {
        return new Position(q - position.q, r - position.r, s - position.s);
    }

    public boolean isCenter() {
        return (q != 0 || r != 0 || s != 0);
    }

    public boolean isCloseToCenter() {
        return (abs(q) + abs(r) + abs(s) == 2);
    }

    public boolean isMovementALine(Position position) {
        return this.getQ() == position.getQ() || this.getR() == position.getR() || this.getS() == position.getS();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return q == position.q && r == position.r && s == position.s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r, s);
    }

    @Override
    public String toString() {
        return
                "(" + getX() +
                        " , " + getY() +
                        ')';
    }

    public boolean isCloseTo(Position position2) {
        int deltaQ = q - position2.getQ();
        int deltaR = r - position2.getR();
        int deltaS = s - position2.getS();
        return (deltaQ + deltaR + deltaS == 0) && (abs(deltaQ) + abs(deltaR) + abs(deltaS) == 2);
    }

    public int getDistanceToPosition(Position position2) {
        int max = max(abs(q - position2.getQ()), abs(r - position2.getR()));
        return max(abs(s - position2.getS()), max);
    }
}

