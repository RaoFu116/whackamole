package mquinn.whackamole;

/**
 *
 * Author: Matthew Quinn
 * 10/4/17
 *
 * Player class for holding score and name vars associated with the current player.
 * Just has getters and setters.
 *
 */

public class Player {

    String varName;
    int varScore;

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public int getVarScore() {
        return varScore;
    }

    public void setVarScore(int varScore) {
        this.varScore = varScore;
    }

}
