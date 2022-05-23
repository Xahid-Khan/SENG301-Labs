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

import uc.seng301.pattern1.domain.victims.Person;

/**
 * This abstract class describes the common behaviour of all types of invaders.
 * Because of single inheritance principle (of Java), this class cannot be an
 * interface.
 */
public abstract class Invader {

  /**
   * Given Invader will join this Invader (to do Invader's stuff).
   * 
   * @param invader the invader that will join this invader (assumed not null)
   * @throws UnsupportedOperationException some Invaders may not be joined
   */
  public void join(Invader invader) {
    throw new UnsupportedOperationException();
  }

  /**
   * Given Invader will leave this Invader so that they won't continue their
   * invader stuff together.
   * 
   * @param invader the invader leaving this invader (assumed not null)
   * @throws UnsupportedOperationException some Invaders may not be left
   */
  public void leave(Invader invader) {
    throw new UnsupportedOperationException();
  }

  /**
   * Add potential abductees to the list of people being watched by this invader.
   *
   * @param prospects list of People this invader will watch from now on (assumed
   *                  not null)
   */
  public abstract void watch(List<Person> prospects);

}
