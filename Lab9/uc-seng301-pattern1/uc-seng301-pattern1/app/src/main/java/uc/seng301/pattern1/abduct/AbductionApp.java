/*
 * The Unlicense
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute
 * this software, either in source code form or as a compiled binary, for any
 * purpose, commercial or non-commercial, and by any means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the public
 * domain. We make this dedication for the benefit of the public at large and to
 * the detriment of our heirs and successors. We intend this dedication to be an
 * overt act of relinquishment in perpetuity of all present and future rights to
 * this software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <https://unlicense.org>
 */
package uc.seng301.pattern1.abduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uc.seng301.pattern1.domain.experiments.DissectionExperiment;
import uc.seng301.pattern1.domain.experiments.DrugExperiment;
import uc.seng301.pattern1.domain.experiments.SlimeExperiment;
import uc.seng301.pattern1.domain.invaders.Cigar;
import uc.seng301.pattern1.domain.invaders.FireBall;
import uc.seng301.pattern1.domain.invaders.Fleet;
import uc.seng301.pattern1.domain.invaders.FlyingSaucer;
import uc.seng301.pattern1.domain.invaders.Invader;
import uc.seng301.pattern1.domain.invaders.Jelly;
import uc.seng301.pattern1.domain.victims.Person;

/**
 * Main class for the abduction simulation.
 * 
 * This runs the full simulation from a given list of persons on which
 * experiments will be performed.
 */
public class AbductionApp {

  /**
   * Length of simulation
   */
  private static final int SIMULATION_EVENTS = 10;

  /**
   * People in the simulation.
   */
  private final List<Person> people;

  /**
   * Invaders in the simulation
   */
  private final List<Invader> invaders;

  private final Random randomGenerator;

  /**
   * Construct the app, including people and invaders.
   *
   * @param names array of names of people
   */
  public AbductionApp(String[] names) {

    // Make the list of people.
    people = new ArrayList<>();
    for (int i = 0; i < names.length; i++) {
      people.add(new Person(names[i]));
    }

    // Make the list of invaders.
    randomGenerator = new Random();
    invaders = new ArrayList<>();
    for (int i = 0; i < randomGenerator.nextInt(10); i++) {
      Invader invader = createInvader();
      invaders.add(invader);
      invader.watch(people);
    }

  }

  /**
   * Create a random invader
   */
  private Invader createInvader() {
    Invader invader;
    switch (randomGenerator.nextInt(8)) {
      case 0:
      case 1:
      case 2:
        invader = new FlyingSaucer();
        break;

      case 3:
        invader = new Cigar();
        break;
      case 4:
        invader = new Jelly();
        break;
      case 5:
        invader = new Fleet(new Jelly());
        invader.join(new Cigar());
        invader.join(new Cigar());

        break;
      case 6:
        invader = new Fleet(new FireBall());
        invader.join(new FireBall());
        invader.join(new FireBall());
        invader.join(new FireBall());
        invader.join(new FireBall());
        break;

      case 7:
        invader = new Cigar();
        ((Cigar) invader).addExperiment(DissectionExperiment.getExperiment());
        ((Cigar) invader).addExperiment(SlimeExperiment.getExperiment());
        ((Cigar) invader).addExperiment(DrugExperiment.getExperiment());
        break;
      default:
        // should never happen since the random generator upper bound is 8 (exclusive)
        invader = new FlyingSaucer();
    }
    return invader;
  }

  /**
   * Run the simulation.
   */
  private void run() {
    System.out.println("Simulation starts!\n--");
    for (int i = 0; i < SIMULATION_EVENTS; i++) {
      Person someone = people.get(randomGenerator.nextInt(people.size()));
      switch (randomGenerator.nextInt(3)) {
        case 0:
          someone.wakeUp();
          break;
        case 1:
          someone.eat();
          break;
        case 2:
          someone.sleep();
          break;
        default:
          // should never happen since the random generator upper bound is 8 (exclusive)
      }
    }
    System.out.println("--\nSimulation finished, bye!");
  }

  /**
   * Main method. Starts the simulation with given people's names.
   *
   * @param args a list of people's names.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("Wrong usage! You need to pass a list of space-separated names, e.g., Neville Moffat Fabian");
      System.exit(-1);
    }
    new AbductionApp(args).run();
  }
}
