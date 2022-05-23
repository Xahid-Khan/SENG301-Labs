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

import uc.seng301.pattern1.domain.experiments.DrugExperiment;
import uc.seng301.pattern1.domain.experiments.SlimeExperiment;
import uc.seng301.pattern1.domain.victims.Person;

/**
 * A not-so-regular flying saucer, with the right amount of sauce.
 */
public class FlyingSaucer extends UFO {

  /**
   * Construct a saucer that will perform drug and slime experiments.
   */
  public FlyingSaucer() {
    addExperiment(DrugExperiment.getExperiment());
    addExperiment(SlimeExperiment.getExperiment());
  }

  /**
   * Perform experiments on a victim. Wakes victim up afterwards.
   * 
   * @param victim the person that will be the victim of this flying saucer
   *               (assumed no null)
   */
  @Override
  protected void experiment(Person victim) {
    super.experiment(victim);
    victim.wakeUp();
  }

  /**
   * Check whether this Flying Saucer wants given victim
   * 
   * @param victim a person to be potentially abducted (assumed not null)
   * @return true if given victim is sleeping and not yet abducted, false
   *         otherwise
   */
  @Override
  protected boolean wantVictim(Person victim) {
    return !victim.isAbducted() && victim.isSleeping();
  }

  @Override
  protected String getUFOType() {
    return "Flying saucer";
  }

}
