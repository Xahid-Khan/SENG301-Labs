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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uc.seng301.pattern1.domain.victims.Person;

/**
 * A fleet is a group of invaders.
 */
public class Fleet extends Invader {

  private Set<Invader> shipsInFleet;
  private UFO commander;

  /**
   * A fleet of UFO
   * 
   * @param commander the commander UFO of this fleet
   */
  public Fleet(UFO commander) {
    this.commander = commander;
    shipsInFleet = new HashSet<>();
    join(commander);
  }

  @Override
  public void join(Invader invader) {
    shipsInFleet.add(invader);
  }

  @Override
  public void leave(Invader invader) {
    shipsInFleet.remove(invader);
  }

  @Override
  public void watch(List<Person> prospects) {
    Iterator<Invader> currentShipAllocator = shipsInFleet.iterator();
    for (Person person : prospects) {
      // round robin allocation of watched persons to the fleet
      if (!currentShipAllocator.hasNext()) {
        currentShipAllocator = shipsInFleet.iterator();
      }
      currentShipAllocator.next().watch(Arrays.asList(person));
    }
  }

  /**
   * Bare getter for the commander ship of this Fleet.
   * 
   * @return the commander
   */
  public UFO getCommander() {
    return commander;
  }

  @Override
  public String toString() {
    return "A Fleet consisting of (" + shipsInFleet + ")";
  }

}
