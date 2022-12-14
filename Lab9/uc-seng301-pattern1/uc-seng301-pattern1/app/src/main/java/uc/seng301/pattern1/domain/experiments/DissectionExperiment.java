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
package uc.seng301.pattern1.domain.experiments;

import uc.seng301.pattern1.domain.victims.Person;

/**
 * This class implements a dissection {@link Experiment} made of dessecting and
 * reassembling a {@link Person}
 */
public class DissectionExperiment implements Experiment {

  /**
   * Unique instance
   */
  private static Experiment theExperiment;

  /**
   * Singleton-compliant constructor
   */
  private DissectionExperiment() {
    // hide public default constructor
  }

  /**
   * Get the unique instance of this dissection experiment
   * 
   * @return a unique instance
   */
  public static synchronized Experiment getExperiment() {
    if (null == theExperiment) {
      theExperiment = new DissectionExperiment();
    }
    return theExperiment;
  }

  @Override
  public void perform(Person victim) {
    dissect(victim);
    reassemble(victim);
  }

  /**
   * Dissect given victim
   * 
   * @param victim a poor victim (assumed not null)
   */
  private void dissect(Person victim) {
    System.out.println("Dissecting " + victim);
  }

  /**
   * Reassemble given victim
   * 
   * @param victim a poor victim (assumed not null)
   */
  private void reassemble(Person victim) {
    System.out.println("Reassembling " + victim);
  }

}
