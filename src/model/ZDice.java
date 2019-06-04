package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import tools.Utilities;
import tools.Enumerations.DiceFaces;

public class ZDice {
    private ArrayList<Dice> available_dice;
    private ArrayList<Dice> dice_to_throw;
    private ArrayList<Dice> thrown_dices;

    private String difficulty;
    private ArrayList<Player> players_list;
    private int current_player_turn;

    private boolean game_end;

    public ZDice(String difficulty) {
        this.game_end = false;
        this.difficulty = difficulty.toLowerCase();
        this.current_player_turn = 0;
        this.players_list = new ArrayList<Player>();
        this.available_dice = new ArrayList<Dice>();
        this.dice_to_throw = new ArrayList<Dice>();
        this.thrown_dices = new ArrayList<Dice>();

        int[] thresholds = {8,3,2};

        if (this.difficulty == "medium") {
            thresholds[0] = 6;
            thresholds[1] = 4;
            thresholds[2] = 3;
        } else if (this.difficulty == "hard") {
            thresholds[0] = 4;
            thresholds[1] = 5;
            thresholds[2] = 4;
        }


        for (int i = 1; i < Utilities.getMax(thresholds) + 1; i++) {
            if (i <= thresholds[0])
                this.available_dice.add(new Dice("green"));
            if (i <= thresholds[1])
                this.available_dice.add(new Dice("yellow"));
            if (i <= thresholds[2])
                this.available_dice.add(new Dice("red"));
        }
    }

    public ArrayList<Dice> getAvailable_dice() {
        return available_dice;
    }

    public void setAvailable_dice(ArrayList<Dice> available_dice) {
        this.available_dice = available_dice;
    }

    public ArrayList<Dice> getDice_to_throw() {
        return dice_to_throw;
    }

    public void setDice_to_throw(ArrayList<Dice> dice_to_throw) {
        this.dice_to_throw = dice_to_throw;
    }

    public ArrayList<Dice> getThrown_dices() {
        return thrown_dices;
    }

    public void setThrown_dices(ArrayList<Dice> thrown_dices) {
        this.thrown_dices = thrown_dices;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Player> getPlayers_list() {
        return players_list;
    }

    public void setPlayers_list(ArrayList<Player> players_list) {
        this.players_list = players_list;
    }

    public int getCurrent_player_turn() {
        return current_player_turn;
    }

    public void setCurrent_player_turn(int current_player_turn) {
        this.current_player_turn = current_player_turn;
    }

    public void addPlayers(List<String> names) {
        for (String name: names)
            this.players_list.add(new Player(name));
    }

    public boolean takeDices() {
        int dices_to_take = 3 - this.dice_to_throw.size();
        System.out.println("Taking " + dices_to_take + " dices...");

        if (dices_to_take == 0)
            return false;

        if (dices_to_take > this.available_dice.size()) {
            System.out.println("Re-adding dices...");
            for (int i = this.thrown_dices.size()-1; i >= 0; i--)
                this.available_dice.add(this.thrown_dices.remove(i));
        }


        Random random = new Random();
        int to_take = -1;

        for (int i = 0; i < dices_to_take; i++) {
            to_take = random.nextInt(this.available_dice.size());
            this.dice_to_throw.add(this.available_dice.remove(to_take));
        }

        System.out.println("Dices left: " + this.available_dice.size());

        return true;
    }

    public boolean throwingAndProcess() {
        System.out.println("Throwing...");
        Random random = new Random();
        ArrayList<DiceFaces> faces = new ArrayList<DiceFaces>();

        ArrayList<Integer> to_remove = new ArrayList<Integer>();

        System.out.println("Processing...");
        for (int i = 0; i < 3; i++) {
            faces.add(this.dice_to_throw.get(i).getFaces().get(random.nextInt(6)));

            System.out.println(this.dice_to_throw.get(i).getColor() + faces.get(i));

            if (faces.get(i) != DiceFaces.steps)
                to_remove.add(i);

            if (faces.get(i) == DiceFaces.brain) {
                this.players_list.get(this.current_player_turn).addPointsTmp(1);
            } else if (faces.get(i) == DiceFaces.shotgun) {
                if (this.players_list.get(this.current_player_turn).addOneShotgun()) {
                    return true;
                }

            }
        }

        Collections.reverse(to_remove);

        System.out.println("Removing " + to_remove.size() + " dices...");
        for (Integer to_rm: to_remove) {
            this.thrown_dices.add(this.dice_to_throw.remove((int)to_rm));
        }

        System.out.println("Thrown dices size: " + this.thrown_dices.size());

        return false;
    }

    public boolean nextTurn() {
        System.out.println("Next player turn");
        this.players_list.get(this.current_player_turn).validatePoints();

        if (this.players_list.get(this.current_player_turn).getScore() >= 13)
            game_end = true;

        this.current_player_turn++;
        if (this.current_player_turn > this.players_list.size() - 1) {
            this.current_player_turn = 0;
            if (game_end) {
                return false;
            }
        }

        for (int i = 0; i < this.dice_to_throw.size(); i++)
            this.available_dice.add(this.dice_to_throw.remove(i));

        for (int i = 0; i < this.thrown_dices.size(); i++)
            this.available_dice.add(this.thrown_dices.remove(i));

        return true;
    }
}
