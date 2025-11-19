import engine.core.MarioResult;

public class ScoreBoard {
    String agent;
    String level;
    double weightedScore;
    String status;
    int completionPct;
    int lives;
    int coins;
    int remainingTime;
    int mushrooms;
    int fireFlowers;
    int stompKills;
    int fireKills;
    int shellKills;
    int jumps;
    int marioMode;

    public ScoreBoard(String agent, String level, double weightedScore, MarioResult result) {
        this.agent = agent;
        this.level = level;
        this.weightedScore = weightedScore;
        this.status = result.getGameStatus().toString();
        this.completionPct = (int) result.getCompletionPercentage();
        this.lives = result.getCurrentLives();
        this.coins = result.getCurrentCoins();
        this.remainingTime = result.getRemainingTime();
        this.mushrooms = result.getNumCollectedMushrooms();
        this.fireFlowers = result.getNumCollectedFireflower();
        this.stompKills = result.getKillsByStomp();
        this.fireKills = result.getKillsByFire();
        this.shellKills = result.getKillsByShell();
        this.jumps = result.getNumJumps();
        this.marioMode = result.getMarioMode();
    }

    @Override
    public String toString() {
        return "ScoreBoard{" +
                "agent=" + agent +
                ", level=" + level +
                ", weightedScore=" + weightedScore +
                ", status=" + status +
                ", completionPct=" + completionPct +
                ", lives=" + lives +
                ", coins=" + coins +
                ", remainingTime=" + remainingTime +
                ", mushrooms=" + mushrooms +
                ", fireFlowers=" + fireFlowers +
                ", stompKills=" + stompKills +
                ", fireKills=" + fireKills +
                ", shellKills=" + shellKills +
                ", jumps=" + jumps +
                ", marioMode=" + marioMode +
                '}';
    }

    public static String csvHeader() {
        return "Agent,Level,WeightedScore,Status,CompletionPct,Lives,Coins,RemainingTime," +
                "Mushrooms,FireFlowers,StompKills,FireKills,ShellKills,Jumps,MarioMode";
    }

    public String toCSV() {
        return agent + "," +
                level + "," +
                weightedScore + "," +
                status + "," +
                completionPct + "," +
                lives + "," +
                coins + "," +
                remainingTime + "," +
                mushrooms + "," +
                fireFlowers + "," +
                stompKills + "," +
                fireKills + "," +
                shellKills + "," +
                jumps + "," +
                marioMode;
    }
}
