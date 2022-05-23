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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uc.seng301.pattern1.domain.experiments.Experiment;
import uc.seng301.pattern1.domain.victims.Person;

/**
 * A regular alien vehicle.
 * 
 * Will perform {@link Invader} evil behaviour.
 */
public abstract class UFO extends Invader implements PropertyChangeListener {

  /**
   * The experiments to be performed on abductees
   */
  private final Set<Experiment> experiments;

  /**
   * The Persons who are abducted.
   */
  private final Set<Person> abductees;

  /**
   * Construct a UFO
   */
  protected UFO() {
    experiments = new HashSet<>();
    abductees = new HashSet<>();
  }

  @Override
  public void watch(List<Person> prospects) {
    for (Person person : prospects) {
      person.attach(this);
    }
  }

  /**
   * Abduct given victim, if this UFO wants to and then perform experiments. May
   * retain the victim or not.
   * 
   * @param victim a person to abduct potentially
   * @see {@link #wantVictim(Person)}
   * @see {@link #retainVictim(Person)}
   */
  public void abduct(Person victim) {
    if (wantVictim(victim)) {
      performAbduction(victim);
      experiment(victim);

      if (!retainVictim(victim)) {
        release(victim);
      }
    }
  }

  /**
   * A UFO only wants live and not already abducted victims to abduct itself
   * 
   * @param victim the person this UFO may abduct (assumed not null)
   */
  protected boolean wantVictim(Person victim) {
    return !victim.isDead() && !victim.isAbducted();
  }

  /**
   * Perform the abduction act on given victim
   * 
   * @param victim the person that will be abducted (assumed not null)
   */
  protected void performAbduction(Person victim) {
    System.out.println(this + " abducts " + victim);
    abductees.add(victim);
    victim.abduct();
  }

  /**
   * Perform this UFO experiments on given victim.
   * 
   * @param victim the person that will be used for experiments (assumed not null)
   */
  protected void experiment(Person victim) {
    experiments.forEach(experiment -> experiment.perform(victim));
  }

  /**
   * Decide if given victim should be kept or released. UFO does not release
   * victims by default.
   * 
   * @param victim to person to retain or not
   * @return always false
   */
  protected boolean retainVictim(Person victim) {
    return false;
  }

  /**
   * Release given person from this UFO.
   * 
   * @param victim the person to be released
   * @return true if given victim was retained previously and could be released,
   *         false otherwise
   */
  protected boolean release(Person victim) {
    boolean released = abductees.remove(victim);
    if (released) {
      System.out.println(this + " releases " + victim);
      victim.release();
    }
    return released;
  }

  /**
   * Add given experiment to be performed on victims when they are abducted.
   * 
   * @param experiment the experiment to add
   * @return true if the set of experiments did not already contained that
   *         experiment and it was added, false otherwise
   */
  public boolean addExperiment(Experiment experiment) {
    return experiments.add(experiment);
  }

  /**
   * Remove an experiment that was added earlier.
   * 
   * @param experiment the experiment to remove
   * @return true if the set of experiments did contain that experiment and it has
   *         been added, false otherwise
   */
  public boolean removeExperiment(Experiment experiment) {
    return experiments.remove(experiment);
  }

  /**
   * Retrieve the set of all persons (victims) this UFO currently retain
   * 
   * @return a possibly empty set of victims
   */
  protected Set<Person> getAbductees() {
    return abductees;
  }

  /**
   * This is the {@link PropertyChangeListener} method being called when a change
   * event is generated from the Subject (of the Observer pattern)
   * 
   * Respond to a change in the state of an abductee (Person) that this UFO is
   * notified of.
   * 
   * @param event the (Java) event of the generic {@link PropertyChangeEvent}
   *              class allowing to observe other objects. Must be fired from
   *              other places. Only explicitly bound listeners will be notified.
   * 
   */
  @Override
  public void propertyChange(PropertyChangeEvent event) {
    Person victim = (Person) event.getSource();
    if (!victim.isAbducted()) { // Already grabbed by someone?.
      abduct(victim);
    }
  }

  /**
   * Get this UFO type
   * 
   * @return a string describing this UFO type
   */
  protected abstract String getUFOType();

  @Override
  public String toString() {
    return "a " + getUFOType() + " having abducted " + (abductees.isEmpty() ? "nobody" : abductees.toString());
  }

}
