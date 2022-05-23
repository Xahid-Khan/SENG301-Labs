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
package uc.seng301.pattern1.domain.victims;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import uc.seng301.pattern1.domain.invaders.UFO;
import uc.seng301.pattern1.enums.PersonStatus;

/**
 * Persons may take actions (i.e. sleep, wakeUp and eat). They may also be
 * victims of abduction or killed.
 */
public class Person {

  /**
   * This person's name
   */
  private String name;

  /**
   * Current state of this Person. It may change depending on what's happening to
   * them.
   */
  private PersonStatus status;

  /**
   * True if this person is currently abducted (by an evil
   * {@link uc.seng301.pattern1.domain.invaders.Invader}
   */
  private boolean abducted;

  /**
   * In order to implement the observer pattern in Java, we need a "middle entity"
   * that will be notified by the changes of the Subject.
   */
  private PropertyChangeSupport statusChangeNotifier = new PropertyChangeSupport(this);

  /**
   * Construct a person with given name. By default, persons are awake and not
   * abducted.
   *
   * @param name this person's name
   */
  public Person(String name) {
    status = PersonStatus.AWAKE;
    abducted = false;
    this.name = name;
  }

  /**
   * Attach a UFO (observer) to this person (subject)
   *
   * @param ufo the UFO (aka {@link PropertyChangeListener} implementation object)
   *            that will observe this subject (assumed not null)
   */
  public void attach(UFO ufo) {
    statusChangeNotifier.addPropertyChangeListener(ufo);
  }

  /**
   * Detach a UFO (observer) from this person (subject)
   *
   * @param ufo the UFO (aka {@link PropertyChangeListener} implementation object)
   *            that was observing this subject (assumed not null)
   */
  public void detach(UFO ufo) {
    statusChangeNotifier.removePropertyChangeListener(ufo);
  }

  @Override
  public String toString() {
    return "a " + (isDead() ? "corpse" : "person") + " called " + getName();
  }

  /**
   * Get this person's name
   * 
   * @return a name
   */
  public String getName() {
    return name;
  }

  /**
   * Wake the person up. Won't work if they're dead.
   * 
   * @throws UnsupportedOperationException if this person is dead
   */
  public void wakeUp() {
    if (isDead()) {
      throw new UnsupportedOperationException();
    }
    System.out.println(getName() + " says howdy");
    changeStatus(PersonStatus.AWAKE);
  }

  /**
   * Feed this person. Won't work if they're dead.
   * 
   * @throws UnsupportedOperationException if this person is dead
   * 
   */
  public void eat() {
    if (isDead()) {
      // offensive programming principle, we do not silently accept wrong usages of
      // the method.
      throw new UnsupportedOperationException();
    }
    System.out.println(getName() + " says yum yum");
    changeStatus(PersonStatus.EATING);
  }

  /**
   * Put this person to sleep. Won't work if they're dead.
   * 
   * @throws UnsupportedOperationException if this person is dead
   * 
   */
  public void sleep() {
    if (isDead()) {
      throw new UnsupportedOperationException();
    }
    System.out.println(getName() + " says zzzzzz");
    changeStatus(PersonStatus.SLEEPING);
  }

  /**
   * Assassinate this Person.
   */
  public void kill() {
    System.out.println(getName() + " says " + (isDead() ? "oh no, not again!" : "argh!"));
    changeStatus(PersonStatus.DEAD);
  }

  /**
   * Abduct this Person.
   */
  public void abduct() {
    if (!isDead()) {
      System.out.println(getName() + " says eeeek!");
    }
    abducted = true;
  }

  /**
   * Release the Person from abduction.
   */
  public void release() {
    if (!isDead()) {
      System.out.println(getName() + " says where am I?");
    }
    if (isAbducted()) {
      statusChangeNotifier.firePropertyChange("abducted", isAbducted(), false);
      abducted = false;
    }
  }

  /**
   * Check wether this person is awake
   * 
   * @return true if this person status is either awake or eating, false otherwise
   * @see {@link PersonStatus}
   */
  public boolean isAwake() {
    return PersonStatus.AWAKE.equals(status) || PersonStatus.EATING.equals(status);
  }

  /**
   * Check wether this person is sleeping
   * 
   * @return true if this person status is sleeping, false otherwise
   * @see {@link PersonStatus}
   */
  public boolean isSleeping() {
    return PersonStatus.SLEEPING.equals(status);
  }

  /**
   * Check wether this person is eating
   * 
   * @return true if this person status is eating, false otherwise
   * @see {@link PersonStatus}
   */
  public boolean isEating() {
    return PersonStatus.EATING.equals(status);
  }

  /**
   * Check wether this person is dead
   * 
   * @return true if this person's status is dead, false otherwise
   * @see {@link PersonStatus}
   */
  public boolean isDead() {
    return PersonStatus.DEAD.equals(status);
  }

  /**
   * Check wether this person is abducted
   * 
   * @return true if this person is currently abducted
   */
  public boolean isAbducted() {
    return abducted;
  }

  /**
   * This method change the Person's status and notify all observers if not yet
   * abducted. Will have no effects on dead persons or if given status is the same
   * as the current status (silent defensive programming).
   * 
   * @param newStatus the status to update this person with (assumed not null)
   */
  private void changeStatus(PersonStatus newStatus) {
    if (!isDead() && !status.equals(newStatus)) {
      if (!isAbducted()) {
        System.out.println(getName() + " updates his/her observers of imminent status change...");
        statusChangeNotifier.firePropertyChange("status", status, newStatus);
      }
      status = newStatus;
    }
  }
}
