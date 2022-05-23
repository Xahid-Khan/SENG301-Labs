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
package uc.seng301.pattern1.domain.invaders;

import java.util.List;

import uc.seng301.pattern1.domain.experiments.DissectionExperiment;
import uc.seng301.pattern1.domain.experiments.DrugExperiment;
import uc.seng301.pattern1.domain.experiments.SlimeExperiment;
import uc.seng301.pattern1.domain.victims.Person;

/**
 * Jelly UFO. Rare, but very nasty.
 */
public class Jelly extends UFO {

  /**
   * Construct a Jelly UFO. Performs drug, slime and dissection experiments.
   */
  public Jelly() {
    addExperiment(DrugExperiment.getExperiment());
    addExperiment(SlimeExperiment.getExperiment());
    addExperiment(DissectionExperiment.getExperiment());
  }

  /**
   * Jelly watches people with short names (less than 5 characters)
   * 
   * @param prospects a list of person to potentially watch (assumed non null)
   */
  @Override
  public void watch(List<Person> prospects) {
    for (Person p : prospects) {
      if (p.getName().length() < 5) {
        p.attach(this);
      }
    }
  }

  /**
   * Perform experiments on given victim. Will be fatal to victims who are eating.
   * 
   * @param victim the person that will be the victim of this Jelly (assumed not
   *               null)
   */
  @Override
  protected void experiment(Person victim) {
    super.experiment(victim);
    if (victim.isEating()) {
      victim.kill();
    }
  }

  /**
   * Check whether this Jelly wants given victim
   * 
   * @param victim a person to be potentially abducted (assumed not null)
   * @return true if given victim is awake and not yet abducted, false otherwise
   */
  @Override
  protected boolean wantVictim(Person victim) {
    return !victim.isAbducted() && victim.isAwake();
  }

  /**
   * A Jelly doesn't let anyone go, unless they're dead.
   * 
   * @param victim the victim to release only if they are dead
   * @return true only if the victim is dead
   */
  @Override
  protected boolean retainVictim(Person victim) {
    return !victim.isDead();
  }

  @Override
  protected String getUFOType() {
    return "Jelly";
  }
}
