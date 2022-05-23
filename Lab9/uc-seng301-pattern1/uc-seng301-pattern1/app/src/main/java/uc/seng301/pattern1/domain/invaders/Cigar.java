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

import java.text.Normalizer;
import java.util.List;

import uc.seng301.pattern1.domain.experiments.DissectionExperiment;
import uc.seng301.pattern1.domain.victims.Person;

/**
 * A cigar UFO. Fairly common, but usually leaves people alone.
 */
public class Cigar extends UFO {

  /**
   * Default constructor. Only add {@link DissectionExperiment} to the list of
   * experiments.
   */
  public Cigar() {
    addExperiment(DissectionExperiment.getExperiment());
  }

  /**
   * Cigar only watch people with their names starting with a vowel.
   *
   * @param prospects list of People to watch (assumed not null)
   */
  @Override
  public void watch(List<Person> prospects) {
    for (Person p : prospects) {
      // Person name may contain diacritics (accents), so we normalise the name, i.e.
      // accentuated letters will be expanded
      // see https://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
      String firstLetter = Normalizer.normalize(p.getName(), Normalizer.Form.NFD).substring(0, 1);
      if ("aeiou".contains(firstLetter)) {
        p.attach(this);
      }
    }
  }

  /**
   * A Cigar takes all potential victims except already abducted ones.
   * 
   * @param victim the person this Cigar may abduct (assumed not null)
   */
  @Override
  protected boolean wantVictim(Person victim) {
    return !victim.isAbducted();
  }

  @Override
  protected String getUFOType() {
    return "Cigar";
  }

}
