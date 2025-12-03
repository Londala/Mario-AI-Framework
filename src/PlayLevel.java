import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
=======
>>>>>>> 0e784d593c0887616cf25a42837479cfd65fb046

import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioAgent;


public class PlayLevel {
    static ArrayList<ScoreBoard> scoreBoardLog = new ArrayList<>();

    public static void printResults(MarioResult result) {
        System.out.println("****************************************************************");
        System.out.println("Game Status: " + result.getGameStatus().toString() +
                " Percentage Completion: " + result.getCompletionPercentage());
        System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
                " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
        System.out.println("Mario State: " + result.getMarioMode() +
                " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
        System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
                " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
                " Falls: " + result.getKillsByFall() + ")");
        System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
                " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
        System.out.println("****************************************************************");
        System.out.println("Total Score: " + computeScore(result));
        System.out.println("****************************************************************");
    }

    private static double computeScore(MarioResult result) {

        double completion = result.getCompletionPercentage();
        double completionScore = completion * 1000.0;
        double winBonus = (completion >= 1.0) ? 500.0 : 0.0;
        int coinScore = Math.min(result.getCurrentCoins(), 50) * 10;
        int killScore = Math.min(result.getKillsTotal(), 25) * 20;
        int styleBonus = 5 * (result.getKillsByStomp() + result.getKillsByFire() + result.getKillsByShell());
        int lifeBonus = result.getCurrentLives() * 100;
        int remainingTimeBonus = result.getRemainingTime() * 10;
        int mushroomBonus = result.getNumCollectedMushrooms() * 20;
        int marioModeBonus = result.getMarioMode() * 20;

        return completionScore + winBonus + coinScore + killScore + styleBonus + lifeBonus + remainingTimeBonus + mushroomBonus + marioModeBonus;
    }

    public static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
        }
        return content;
    }

    public static void playBaseLevels(MarioAgent agent) {
        MarioGame game = new MarioGame();
        // printResults(game.playGame(getLevel("../levels/original/lvl-1.txt"), 200, 0));
        int success = 0;
        int fail = 0;
        ArrayList<Integer> failedList = new ArrayList<Integer>();

        for (int lvl = 1; lvl <= 1000; lvl++) {
            String level = String.format("./levels/ge/lvl-%d.txt", lvl);
            MarioResult result = game.runGame(agent, getLevel(level), 25, 0, false, 500);
            printResults(result);
            double score = computeScore(result);
            scoreBoardLog.add(new ScoreBoard(agent.getAgentName(), level, score, result));
            if (result.getGameStatus().toString().equals("WIN")) {
                success++;
            } else {
                fail++;
                failedList.add(lvl);
            }
            System.out.println("Current results: " + success + " / " + fail);
        }
        System.out.println("Final results: " + success + " / " + fail);
        System.out.print("Failed Levels: ");
        for (int l : failedList) {
            System.out.print(l + " ");
        }
        for (ScoreBoard r : scoreBoardLog) {
            System.out.print(r.toString());
        }
    }

    public static void playSingleLevel(MarioAgent agent, int lvl) {
        MarioGame game = new MarioGame();
        String level = String.format("./levels/original/lvl-%d.txt", lvl);
        printResults(game.runGame(agent, getLevel(level), 25, 0, false, 240));
    }

    public static void main(String[] args) {
        // Mario simulator + level generator
        MarioGame game = new MarioGame();
        //MarioLevelGenerator generator = new levelGenerators.notch.LevelGenerator();

        MarioAgent[] agents = new MarioAgent[] {
                new agents.doNothing.Agent(),
                new agents.random.Agent(),
                new agents.glennHartmann.Agent(),
                new agents.robinBaumgarten.Agent()
        };

        String[] agentNames = new String[] {
                "donothing",
                "random",
                "glennHartmann",
                "astar_robin"
        };

<<<<<<< HEAD
        int numLevels = 1000;
=======
        int numLevels = 100;
>>>>>>> 0e784d593c0887616cf25a42837479cfd65fb046

        // Make results/ folder if it doesn't exist
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        // ==== Loop over each agent ====
        for (int a = 0; a < agentNames.length ; a++) {

            MarioAgent agent = agents[a];
            String agentName = agentNames[a];

            long timestamp = System.currentTimeMillis();
            String fileName = "results_" + agentName + "_" + timestamp + ".csv";
            File csvFile = new File(resultsDir, fileName);
            Set<Integer> levelSetRandom = new HashSet<>(Arrays.asList(101, 482));
            Set<Integer> levelSetAstarRobin = new HashSet<>(Arrays.asList(409, 261));
            Set<Integer> levelSetNothing = new HashSet<>(Arrays.asList(9));
            Set<Integer> levelSetGlennHartmann = new HashSet<>(Arrays.asList(27, 40));


            double totalScore = 0.0;
            double totalCompletion = 0.0;
            int totalCoins = 0;
            int totalKills = 0;
            int totalLives = 0;

            System.out.println("\n==============================");
            System.out.println("Running agent: " + agentName);
            System.out.println("==============================");

            try (FileWriter csvWriter = new FileWriter(csvFile)) {
                csvWriter.append("Run," + ScoreBoard.csvHeader() + "\n");

                for (int i = 1; i <= numLevels; i++) {
                    String level = String.format("./levels/ge/lvl-%d.txt", i);

                    MarioResult result;
                    if (
                        (levelSetRandom.contains(i) && agentName.equals("random"))
                        || (levelSetAstarRobin.contains(i) && agentName.equals("astar_robin"))
                        || (levelSetNothing.contains(i) && agentName.equals("donothing"))
                        || (levelSetGlennHartmann.contains(i) && agentName.equals("glennHartmann"))
                    
                    ) {
                        result = game.runGame(agent, getLevel(level), 25, 0, true, 100);
                        //result = game.runGame(agent, getLevel(level), 25);
                    } else {
                        result = game.runGame(agent, getLevel(level), 25);
                    }
                    
                    ScoreBoard runIterationScoreBoard = new ScoreBoard(agentName, level, computeScore(result), result);

                   
                    totalScore += runIterationScoreBoard.weightedScore;
                    totalCompletion += runIterationScoreBoard.completionPct;
                    totalCoins += runIterationScoreBoard.coins;
                    totalKills += runIterationScoreBoard.stompKills+runIterationScoreBoard.fireKills+runIterationScoreBoard.shellKills;
                    totalLives += runIterationScoreBoard.lives;

                    // Optional: print short per-run line
                    System.out.println("run=" + i +
                            ", score=" + runIterationScoreBoard.weightedScore +
                            ", completion=" + runIterationScoreBoard.completionPct +
                            ", coins=" + runIterationScoreBoard.coins +
                            ", kills=" + (runIterationScoreBoard.stompKills+runIterationScoreBoard.fireKills+runIterationScoreBoard.shellKills) +
                            ", lives=" + runIterationScoreBoard.lives);

                    // Write CSV row
                    csvWriter.append(
                            i + "," +
                            runIterationScoreBoard.toCSV()
                            +
                            "\n"
                    );
                }

                // ---- Summary / averages for this agent ----
                double avgScore = totalScore / numLevels;
                double avgCompletion = totalCompletion / numLevels;
                double avgCoins = (double) totalCoins / numLevels;
                double avgKills = (double) totalKills / numLevels;
                double avgLives = (double) totalLives / numLevels;

                System.out.println("\n===== SUMMARY for agent: " + agentName + " =====");
                System.out.println("Average Score: " + avgScore);
                System.out.println("Average Completion: " + avgCompletion);
                System.out.println("Average Coins: " + avgCoins);
                System.out.println("Average Kills: " + avgKills);
                System.out.println("Average Lives: " + avgLives);

                System.out.println("\nCSV file saved as: " + csvFile.getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
