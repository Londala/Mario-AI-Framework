import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.core.MarioAgent;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevel {
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
        System.out.println(" ");
    }

    public static void playSingleLevel(MarioAgent agent, int lvl) {
        MarioGame game = new MarioGame();
        String level = String.format("./levels/original/lvl-%d.txt", lvl);
        printResults(game.runGame(agent, getLevel(level), 25, 0, false, 240));
    }

    public static void main(String[] args) {

        //playBaseLevels(new agents.sergeyPolikarpov.Agent());
        //playSingleLevel(new agents.robinBaumgarten.Agent(), 3);
        playBaseLevels(new agents.robinBaumgarten.Agent());

//        MarioAgent agent = new agents.robinBaumgarten.Agent();
//        for (int iter = 1; iter <= 10000; iter++) {
//            System.out.println("Iteration #" + iter);
//            playSingleLevel(agent, 1);
//        }
//        MarioGame game = new MarioGame();
//        String level = String.format("./levels/original/lvl-%d.txt", 1);
//        printResults(game.runGame(agent, getLevel(level), 25, 0, true));
    }
}
